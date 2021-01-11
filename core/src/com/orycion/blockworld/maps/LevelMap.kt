package com.orycion.blockworld.maps

import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Vector2

class LevelMap(tiledMap: TiledMap) {
    companion object {
        const val UNIT_SCALE = 1 / 96f
    }

    data class Wall(val x: Float, val y: Float, val width: Float, val height: Float)

    var startPosition = Vector2()
    val walls = mutableListOf<Wall>()

    init {
        for (layer in tiledMap.layers) {
            layer.objects.forEach { parseObject(it) }
        }
    }

    private fun parseObject(obj: MapObject) = when(obj.properties["type"]) {
        "start" -> parseStartObject(obj)
        "wall" -> parseWallObject(obj)
        else -> null
    }

    private fun parseStartObject(obj: MapObject) {
        startPosition.x = obj.properties["x"].toString().toFloat() * UNIT_SCALE
        startPosition.y = obj.properties["y"].toString().toFloat() * UNIT_SCALE
    }

    private fun parseWallObject(obj: MapObject) {
        walls.add(Wall(
            obj.properties["x"].toString().toFloat() * UNIT_SCALE,
            obj.properties["y"].toString().toFloat() * UNIT_SCALE,
            obj.properties["width"].toString().toFloat() * UNIT_SCALE,
            obj.properties["height"].toString().toFloat() * UNIT_SCALE
        ))
    }
}
