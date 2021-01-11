package com.orycion.blockworld

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.utils.viewport.ExtendViewport

class BlockWorldGame : ApplicationAdapter() {
    private val assetManager = AssetManager()
    private val camera = OrthographicCamera()
    private val viewport = ExtendViewport(20f, 20f, camera)
    private lateinit var spriteBatch: SpriteBatch
    private lateinit var mapRenderer: OrthogonalTiledMapRenderer

    override fun create() {
        assetManager.setLoader(TiledMap::class.java, TmxMapLoader(InternalFileHandleResolver()))
        assetManager.load("levels/first.xml", TiledMap::class.java)
        assetManager.finishLoading()

        spriteBatch = SpriteBatch()

        val map = assetManager.get<TiledMap>("levels/first.xml")
        mapRenderer = OrthogonalTiledMapRenderer(map, 1 / 96f, spriteBatch)
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        viewport.apply()

        mapRenderer.setView(camera)
        mapRenderer.render()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

    override fun dispose() {
        mapRenderer.dispose()
        assetManager.dispose()
    }
}