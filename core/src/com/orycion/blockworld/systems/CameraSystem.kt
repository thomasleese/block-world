package com.orycion.blockworld.systems

import com.badlogic.ashley.core.*
import com.badlogic.ashley.systems.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.math.collision.BoundingBox
import com.orycion.blockworld.components.*

class CameraSystem(private val levelSize: Vector2) : IteratingSystem(Family.all(CameraComponent::class.java).get()) {
    private val pm = ComponentMapper.getFor(PositionComponent::class.java)
    private val sm = ComponentMapper.getFor(SizeComponent::class.java)
    private val cm = ComponentMapper.getFor(CameraComponent::class.java)

    private lateinit var trackedEntities: ImmutableArray<Entity>

    val trackedBoundingBox = BoundingBox()
    private var tmpTrackedBoundingBoxCenter = Vector3()

    override fun addedToEngine(engine: Engine) {
        super.addedToEngine(engine)
        trackedEntities = engine.getEntitiesFor(Family.all(TrackedComponent::class.java, PositionComponent::class.java, SizeComponent::class.java).get())
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val camera = cm[entity].camera
        val viewport = cm[entity].viewport

        updateTrackedBoundingBox()

        trackedBoundingBox.getCenter(camera.position)

        viewport.minWorldWidth = trackedBoundingBox.width
        viewport.minWorldHeight = trackedBoundingBox.height
        viewport.update(Gdx.graphics.width, Gdx.graphics.height)
    }

    private fun updateTrackedBoundingBox() {
        trackedBoundingBox.inf()

        extendBoundingBox()
        constrainBoundingBox()

        trackedBoundingBox.set(trackedBoundingBox.min, trackedBoundingBox.max)
    }

    private fun extendBoundingBox() {
        for (entity in trackedEntities) {
            val position = pm[entity].position
            val size = sm[entity].size
            tmpTrackedBoundingBoxCenter.set(position.x + size.x / 2f, position.y + size.y / 2f, 0f)
            trackedBoundingBox.ext(tmpTrackedBoundingBoxCenter, 10f)
        }
    }

    private fun constrainBoundingBox() {
        if (trackedBoundingBox.min.x < 0) {
            trackedBoundingBox.min.x = 0f
        }

        if (trackedBoundingBox.min.y < 0) {
            trackedBoundingBox.min.y = 0f
        }

        if (trackedBoundingBox.max.x > levelSize.x) {
            trackedBoundingBox.max.x = levelSize.x
        }

        if (trackedBoundingBox.max.y > levelSize.y) {
            trackedBoundingBox.max.y = levelSize.y
        }
    }
}
