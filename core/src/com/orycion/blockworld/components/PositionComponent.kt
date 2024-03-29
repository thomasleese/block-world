package com.orycion.blockworld.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Pool

class PositionComponent : Component, Pool.Poolable {
    var position = Vector2()

    override fun reset() {
        position.set(0f, 0f)
    }
}
