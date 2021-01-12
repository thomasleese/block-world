package com.orycion.blockworld.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.orycion.blockworld.components.VelocityComponent

class GravitySystem : IteratingSystem(Family.all(VelocityComponent::class.java).get()) {
    private val vm = ComponentMapper.getFor(VelocityComponent::class.java)

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val velocity = vm[entity].velocity
        velocity.y -= 1f
    }
}
