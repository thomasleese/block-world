package com.orycion.blockworld.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool

class VelocityComponent : Component, Pool.Poolable {
    var x = 0f
    var y = 0f

    override fun reset() {
        x = 0f
        y = 0f
    }
}
