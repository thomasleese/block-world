package com.orycion.blockworld.systems

import com.badlogic.ashley.core.*
import com.badlogic.ashley.systems.*
import com.orycion.blockworld.components.*

class GravitySystem : IteratingSystem(Family.all(VelocityComponent::class.java).get()) {
    private val vm = ComponentMapper.getFor(VelocityComponent::class.java)

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val velocity = vm[entity].velocity
        velocity.y -= 0.1f
    }
}
