package com.orycion.blockworld.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Pool

class VelocityComponent : Component, Pool.Poolable {
    var velocity = Vector2()

    override fun reset() {
        velocity.set(0f, 0f)
    }
}
