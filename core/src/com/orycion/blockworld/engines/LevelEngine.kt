package com.orycion.blockworld.engines

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.TiledMap
import com.orycion.blockworld.components.CameraComponent
import com.orycion.blockworld.components.PlayerComponent
import com.orycion.blockworld.components.PositionComponent
import com.orycion.blockworld.components.VelocityComponent
import com.orycion.blockworld.systems.CameraSystem
import com.orycion.blockworld.systems.GravitySystem
import com.orycion.blockworld.systems.MovementSystem

class LevelEngine(map: TiledMap, camera: OrthographicCamera) : PooledEngine() {
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

        player.add(createComponent(PositionComponent::class.java))
        player.add(createComponent(VelocityComponent::class.java))
        player.add(createComponent(PlayerComponent::class.java))
        addEntity(player)
    }
}
