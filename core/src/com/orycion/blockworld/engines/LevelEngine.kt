package com.orycion.blockworld.engines

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.TiledMap
import com.orycion.blockworld.components.*
import com.orycion.blockworld.maps.LevelMap
import com.orycion.blockworld.systems.CameraSystem
import com.orycion.blockworld.systems.GravitySystem
import com.orycion.blockworld.systems.MovementSystem

class LevelEngine(map: LevelMap, camera: OrthographicCamera) : PooledEngine() {
    val player = createEntity()

    init {
        addSystem(GravitySystem())
        addSystem(MovementSystem())
        addSystem(CameraSystem())

        val cameraEntity = createEntity()
        val cameraComponent = createComponent(CameraComponent::class.java)
        cameraComponent.camera = camera
        cameraEntity.add(cameraComponent)
        addEntity(cameraEntity)

        val positionComponent = createComponent(PositionComponent::class.java)
        positionComponent.position.set(map.startPosition).sub(0.5f, 0.5f)
        player.add(positionComponent)
        player.add(createComponent(VelocityComponent::class.java))
        player.add(createComponent(PlayerComponent::class.java))
        val sizeComponent = createComponent(SizeComponent::class.java)
        sizeComponent.size.set(1f, 1f)
        player.add(sizeComponent)
        addEntity(player)
    }
}
