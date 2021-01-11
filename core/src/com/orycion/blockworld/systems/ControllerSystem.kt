package com.orycion.blockworld.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Interpolation
import com.orycion.blockworld.components.ControllableComponent
import com.orycion.blockworld.components.VelocityComponent

class ControllerSystem : IteratingSystem(Family.all(ControllableComponent::class.java, VelocityComponent::class.java).get()) {
    private val cm = ComponentMapper.getFor(ControllableComponent::class.java)
    private val vm = ComponentMapper.getFor(VelocityComponent::class.java)

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val controllable = cm[entity]
        val velocity = vm[entity].velocity

        if (Gdx.input.isKeyJustPressed(controllable.jump)) {
            velocity.y = 2f
        }

        if (Gdx.input.isKeyJustPressed(controllable.left)) {
            velocity.x = -2f
        }

        if (Gdx.input.isKeyJustPressed(controllable.right)) {
            velocity.x = 2f
        }

        velocity.x = Interpolation.linear.apply(velocity.x, 0f, deltaTime)
    }
}
