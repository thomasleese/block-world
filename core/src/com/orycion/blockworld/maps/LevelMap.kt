package com.orycion.blockworld.maps

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Vector2

class LevelMap(tiledMap: TiledMap) {
    companion object {
        const val UNIT_SCALE = 1 / 96f
    }

    var startPosition = Vector2()

    init {
        for (layer in tiledMap.layers) {
            for (obj in layer.objects) {
                if (obj.properties["type"] == "start") {
                    startPosition.x = obj.properties["x"].toString().toFloat() * UNIT_SCALE
                    startPosition.y = obj.properties["y"].toString().toFloat() * UNIT_SCALE
                }
            }
        }
    }
}
