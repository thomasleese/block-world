package com.orycion.blockworld.systems

import com.badlogic.ashley.core.*
import com.badlogic.ashley.systems.*
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.orycion.blockworld.components.*

class MovementSystem : IteratingSystem(Family.all(PositionComponent::class.java, VelocityComponent::class.java).get()) {
    private val pm = ComponentMapper.getFor(PositionComponent::class.java)
    private val vm = ComponentMapper.getFor(VelocityComponent::class.java)

    private var tmp = Vector2()

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val position = pm[entity].position
        val velocity = vm[entity].velocity
        position.add(tmp.set(velocity).scl(deltaTime))
    }
}
