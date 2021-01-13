package com.orycion.blockworld.systems

import com.badlogic.ashley.core.*
import com.badlogic.ashley.systems.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.math.collision.BoundingBox
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.orycion.blockworld.components.*
import kotlin.math.max
import kotlin.math.min

class CameraSystem(private val levelSize: Vector2) : IteratingSystem(Family.all(CameraComponent::class.java).get()) {
    private val pm = ComponentMapper.getFor(PositionComponent::class.java)
    private val sm = ComponentMapper.getFor(SizeComponent::class.java)
    private val cm = ComponentMapper.getFor(CameraComponent::class.java)

    private lateinit var trackedEntities: ImmutableArray<Entity>

    val boundingBox = BoundingBox()
    private var tmpBoundingBoxPoint = Vector3()
    private var tmpTopRight = Vector3()
    private var tmpBottomLeft = Vector3()

    override fun addedToEngine(engine: Engine) {
        super.addedToEngine(engine)
        trackedEntities = engine.getEntitiesFor(Family.all(TrackedComponent::class.java, PositionComponent::class.java, SizeComponent::class.java).get())
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        updateBoundingBox()
        updateCamera(cm[entity].camera, deltaTime)
    }

    private fun updateBoundingBox() {
        boundingBox.inf()
        extendBoundingBox()
        clampBoundingBox()
        boundingBox.set(boundingBox.min, boundingBox.max)
    }

    private fun extendBoundingBox() {
        for (entity in trackedEntities) {
            val position = pm[entity].position
            val size = sm[entity].size
            tmpBoundingBoxPoint.set(position.x + size.x / 2f, position.y + size.y / 2f, 0f)
            boundingBox.ext(tmpBoundingBoxPoint, 12f)
        }
    }

    private fun clampBoundingBox() {
        boundingBox.min.x = max(boundingBox.min.x, 0f)
        boundingBox.min.y = max(boundingBox.min.y, 0f)
        boundingBox.max.x = min(boundingBox.max.x, levelSize.x)
        boundingBox.max.y = min(boundingBox.max.y, levelSize.y)
    }

    private fun updateCamera(camera: OrthographicCamera, deltaTime: Float) {
        zoomCameraToBoundingBox(camera, deltaTime)
        positionCameraToBoundingBox(camera)
        fitCameraToLevel(camera)
    }

    private fun zoomCameraToBoundingBox(camera: OrthographicCamera, deltaTime: Float) {
        val yZoom = boundingBox.height / camera.viewportHeight
        val xZoom = boundingBox.width / camera.viewportWidth
        camera.zoom = Interpolation.linear.apply(camera.zoom, max(xZoom, yZoom), deltaTime)
    }

    private fun positionCameraToBoundingBox(camera: OrthographicCamera) {
        boundingBox.getCenter(camera.position)
        camera.update()
    }

    private fun fitCameraToLevel(camera: OrthographicCamera) {
        val bottomLeft = camera.unproject(tmpBottomLeft.set(0f, Gdx.graphics.height.toFloat(), 0f))
        val topRight = camera.unproject(tmpTopRight.set(Gdx.graphics.width.toFloat(), 0f, 0f))

        if (levelSize.x >= camera.viewportWidth * camera.zoom) {
            if (bottomLeft.x < 0) {
                camera.position.x -= bottomLeft.x
            }
            if (topRight.x > levelSize.x) {
                camera.position.x += levelSize.x - topRight.x
            }
        } else {
            camera.position.x = levelSize.x / 2f
        }

        if (levelSize.y >= camera.viewportHeight * camera.zoom) {
            if (bottomLeft.y < 0) {
                camera.position.y -= bottomLeft.y
            }
            if (topRight.y > levelSize.y) {
                camera.position.y += levelSize.y - topRight.y
            }
        } else {
            camera.position.y = levelSize.y / 2f
        }

        camera.update()
    }
}
