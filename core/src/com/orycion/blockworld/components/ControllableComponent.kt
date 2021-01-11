package com.orycion.blockworld.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.Input

class ControllableComponent : Component {
    val jump = Input.Keys.UP
    val left = Input.Keys.LEFT
    val right = Input.Keys.RIGHT
    val duck = Input.Keys.DOWN
}
