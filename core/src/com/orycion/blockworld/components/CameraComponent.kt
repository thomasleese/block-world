package com.orycion.blockworld.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.viewport.ExtendViewport

class CameraComponent : Component {
    lateinit var camera: OrthographicCamera
    lateinit var viewport: ExtendViewport
}
