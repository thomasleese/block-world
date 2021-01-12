package com.orycion.blockworld.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.Input
import com.badlogic.gdx.utils.Pool

class ControllableComponent : Component {
    var jump = Input.Keys.UP
    var left = Input.Keys.LEFT
    var right = Input.Keys.RIGHT
}
