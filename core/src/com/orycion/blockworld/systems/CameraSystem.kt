package com.orycion.blockworld.systems

import com.badlogic.ashley.core.*
import com.badlogic.ashley.systems.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.math.collision.BoundingBox
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

    override fun addedToEngine(engine: Engine) {
        super.addedToEngine(engine)
        trackedEntities = engine.getEntitiesFor(Family.all(TrackedComponent::class.java, PositionComponent::class.java, SizeComponent::class.java).get())
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        updateBoundingBox()
        updateCamera(cm[entity].camera)
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
            boundingBox.ext(tmpBoundingBoxPoint, 10f)
        }
    }

    private fun clampBoundingBox() {
        boundingBox.min.x = max(boundingBox.min.x, 0f)
        boundingBox.min.y = max(boundingBox.min.y, 0f)
        boundingBox.max.x = min(boundingBox.max.x, levelSize.x)
        boundingBox.max.y = min(boundingBox.max.y, levelSize.y)
    }

    private fun updateCamera(camera: OrthographicCamera) {
        boundingBox.getCenter(camera.position)
        camera.zoom = boundingBox.height / camera.viewportHeight
        camera.update()
    }
}
