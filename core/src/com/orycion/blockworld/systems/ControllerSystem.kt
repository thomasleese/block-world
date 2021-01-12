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
            velocity.y = 19f
        }

        if (Gdx.input.isKeyPressed(controllable.left)) {
            velocity.x = Interpolation.linear.apply(velocity.x, -6f, deltaTime * 30f)
        }

        if (Gdx.input.isKeyPressed(controllable.right)) {
            velocity.x = Interpolation.linear.apply(velocity.x, 6f, deltaTime * 30f)
        }

        velocity.x = Interpolation.linear.apply(velocity.x, 0f, deltaTime * 10f)
    }
}
