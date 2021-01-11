package com.orycion.blockworld.screens

import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.utils.viewport.ExtendViewport

class LevelScreen(assetManager: AssetManager, fileName: String): Screen {
    private val map = assetManager.get<TiledMap>("levels/$fileName.xml")

    private val camera = OrthographicCamera()
    private val viewport = ExtendViewport(20f, 20f, camera)
    private val spriteBatch = SpriteBatch()
    private val mapRenderer = OrthogonalTiledMapRenderer(map, 1 / 96f, spriteBatch)

    override fun dispose() {
        mapRenderer.dispose()
        spriteBatch.dispose()
        map.dispose()
    }

    override fun render(delta: Float) {
        viewport.apply()

        mapRenderer.setView(camera)
        mapRenderer.render()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

    override fun show() {

    }

    override fun hide() {

    }

    override fun resume() {

    }

    override fun pause() {

    }
}