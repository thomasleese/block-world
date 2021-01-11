package com.orycion.blockworld.engines

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.OrthographicCamera
import com.orycion.blockworld.components.*
import com.orycion.blockworld.maps.LevelMap
import com.orycion.blockworld.systems.*

class LevelEngine(private val map: LevelMap, private val camera: OrthographicCamera) : PooledEngine() {
    companion object {
        const val FIXED_DELTA_TIME = 1 / 60f
    }

    val player: Entity = createEntity()
    private var accumulator = 0.0

    init {
        addSystems()
        addCamera()
        addPlayers()
        addWalls()
    }

    override fun update(deltaTime: Float) {
        accumulator += deltaTime

        while (accumulator >= FIXED_DELTA_TIME) {
            super.update(FIXED_DELTA_TIME)
            accumulator -= deltaTime
        }
    }

    private fun addSystems() {
        addSystem(ControllerSystem())
        addSystem(PhysicsSystem())
        addSystem(CollisionSystem())
        addSystem(CameraSystem())
    }

    private fun addCamera() {
        val cameraEntity = createEntity()
        val cameraComponent = createComponent(CameraComponent::class.java)
        cameraComponent.camera = camera
        cameraEntity.add(cameraComponent)
        addEntity(cameraEntity)
    }

    private fun addPlayers() {
        val positionComponent = createComponent(PositionComponent::class.java)
        positionComponent.position.set(map.startPosition).sub(0.5f, 0.5f)
        player.add(positionComponent)
        player.add(createComponent(VelocityComponent::class.java))
        player.add(createComponent(TrackedComponent::class.java))
        val sizeComponent = createComponent(SizeComponent::class.java)
        sizeComponent.size.set(1f, 1f)
        player.add(sizeComponent)
        player.add(createComponent(ControllableComponent::class.java))
        addEntity(player)
    }

    private fun addWalls() {
        for (wall in map.walls) {
            val entity = createEntity()
            val positionComponent = createComponent(PositionComponent::class.java)
            positionComponent.position.set(wall.x, wall.y)
            entity.add(positionComponent)
            val sizeComponent = createComponent(SizeComponent::class.java)
            sizeComponent.size.set(wall.width, wall.height)
            entity.add(sizeComponent)
            entity.add(createComponent(CollideableComponent::class.java))
            addEntity(entity)
        }
    }
}
