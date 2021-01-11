package com.orycion.blockworld.engines

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.maps.tiled.TiledMap
import com.orycion.blockworld.components.PositionComponent
import com.orycion.blockworld.components.VelocityComponent
import com.orycion.blockworld.systems.GravitySystem
import com.orycion.blockworld.systems.MovementSystem

class LevelEngine(map: TiledMap) : PooledEngine() {
    val player = createEntity()

    init {
        addSystem(GravitySystem())
        addSystem(MovementSystem())

        player.add(createComponent(PositionComponent::class.java))
        player.add(createComponent(VelocityComponent::class.java))
        addEntity(player)
    }
}