package com.orycion.blockworld.systems

import com.badlogic.ashley.core.*
import com.badlogic.ashley.systems.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.math.collision.BoundingBox
import com.orycion.blockworld.components.*

class CameraSystem : IteratingSystem(Family.all(CameraComponent::class.java).get()) {
    private val pm = ComponentMapper.getFor(PositionComponent::class.java)
    private val sm = ComponentMapper.getFor(SizeComponent::class.java)
    private val cm = ComponentMapper.getFor(CameraComponent::class.java)

    private lateinit var trackedEntities: ImmutableArray<Entity>

    private val trackedBoundingBox = BoundingBox()
    private var tmpTrackedBoundingBoxCenter = Vector3()

    override fun addedToEngine(engine: Engine) {
        super.addedToEngine(engine)
        trackedEntities = engine.getEntitiesFor(Family.all(TrackedComponent::class.java, PositionComponent::class.java, SizeComponent::class.java).get())
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val camera = cm[entity].camera

        updateTrackedBoundingBox()
        trackedBoundingBox.getCenter(camera.position)
        camera.update()
    }

    private fun updateTrackedBoundingBox() {
        trackedBoundingBox.clr()

        for (entity in trackedEntities) {
            val position = pm[entity].position
            val size = sm[entity].size
            tmpTrackedBoundingBoxCenter.set(position.x + size.x / 2f, position.y + size.y / 2f, 0f)
            trackedBoundingBox.ext(tmpTrackedBoundingBoxCenter, 10f)
        }
    }
}
