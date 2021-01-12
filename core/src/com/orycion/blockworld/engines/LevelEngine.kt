package com.orycion.blockworld.engines

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.orycion.blockworld.components.*
import com.orycion.blockworld.maps.LevelMap
import com.orycion.blockworld.systems.*

class LevelEngine(private val map: LevelMap, private val camera: OrthographicCamera, private val viewport: ExtendViewport) : PooledEngine() {
    companion object {
        const val FIXED_DELTA_TIME = 1 / 60f
    }

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
        addSystem(MovementSystem())
        addSystem(GravitySystem())
        addSystem(CollisionSystem())
        addSystem(CameraSystem(map.size))
    }

    private fun addCamera() {
        val cameraEntity = createEntity()
        val cameraComponent = createComponent(CameraComponent::class.java)
        cameraComponent.camera = camera
        cameraComponent.viewport = viewport
        cameraEntity.add(cameraComponent)
        addEntity(cameraEntity)
    }

    private fun addPlayers() {
        val player: Entity = createEntity()
        val positionComponent = createComponent(PositionComponent::class.java)
        positionComponent.position.set(map.startPosition).sub(0.5f, 0.5f)
        player.add(positionComponent)
        player.add(createComponent(VelocityComponent::class.java))
        player.add(createComponent(TrackedComponent::class.java))
        val sizeComponent = createComponent(SizeComponent::class.java)
        sizeComponent.size.set(90 / 96f, 1.5f)
        player.add(sizeComponent)
        player.add(createComponent(ControllableComponent::class.java))
        addEntity(player)

        val player2: Entity = createEntity()
        val positionComponent2 = createComponent(PositionComponent::class.java)
        positionComponent2.position.set(map.startPosition).sub(0.5f, 0.5f)
        player2.add(positionComponent2)
        player2.add(createComponent(VelocityComponent::class.java))
        player2.add(createComponent(TrackedComponent::class.java))
        val sizeComponent2 = createComponent(SizeComponent::class.java)
        sizeComponent2.size.set(90 / 96f, 1.5f)
        player2.add(sizeComponent2)
        val controllableComponent = createComponent(ControllableComponent::class.java)
        controllableComponent.jump = Input.Keys.W
        controllableComponent.left = Input.Keys.A
        controllableComponent.right = Input.Keys.D
        player2.add(controllableComponent)
        addEntity(player2)
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
