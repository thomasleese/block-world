package com.orycion.blockworld.systems

import com.badlogic.ashley.core.*
import com.badlogic.ashley.systems.*
import com.orycion.blockworld.components.*

class MovementSystem : IteratingSystem(Family.all(PositionComponent::class.java, VelocityComponent::class.java).get()) {
    private val pm = ComponentMapper.getFor(PositionComponent::class.java)
    private val vm = ComponentMapper.getFor(VelocityComponent::class.java)

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val position = pm[entity]
        val velocity = vm[entity]
        position.x += velocity.x * deltaTime
        position.y += velocity.y * deltaTime
    }
}
