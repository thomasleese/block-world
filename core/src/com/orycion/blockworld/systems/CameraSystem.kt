package com.orycion.blockworld.systems

import com.badlogic.ashley.core.*
import com.badlogic.ashley.systems.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.math.collision.BoundingBox
import com.orycion.blockworld.components.*

class CameraSystem : IteratingSystem(Family.all(CameraComponent::class.java).get()) {
    private val pm = ComponentMapper.getFor(PositionComponent::class.java)
    private val cm = ComponentMapper.getFor(CameraComponent::class.java)
    private lateinit var players: ImmutableArray<Entity>

    private val playersBoundingBox = BoundingBox()
    private var tmpPlayersBoundingBoxCenter = Vector3()

    override fun addedToEngine(engine: Engine) {
        super.addedToEngine(engine)
        players = engine.getEntitiesFor(Family.all(PlayerComponent::class.java, PositionComponent::class.java).get())
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val camera = cm[entity].camera

        updatePlayersBoundingBox()
        playersBoundingBox.getCenter(camera.position)
        camera.update()
    }

    private fun updatePlayersBoundingBox() {
        playersBoundingBox.clr()

        for (entity in players) {
            val position = pm[entity]
            tmpPlayersBoundingBoxCenter.set(position.x, position.y, 0f)
            playersBoundingBox.ext(tmpPlayersBoundingBoxCenter, 10f)
        }
    }
}
