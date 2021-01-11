package com.orycion.blockworld.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Pool

class SizeComponent : Component, Pool.Poolable {
    val size = Vector2()

    override fun reset() {
        size.set(0f, 0f)
    }
}
