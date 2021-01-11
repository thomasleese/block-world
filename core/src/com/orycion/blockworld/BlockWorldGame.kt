package com.orycion.blockworld

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.orycion.blockworld.screens.LevelScreen

class BlockWorldGame : Game() {
    private val assetManager = AssetManager()

    override fun create() {
        assetManager.setLoader(TiledMap::class.java, TmxMapLoader(InternalFileHandleResolver()))
        assetManager.load("levels/first.xml", TiledMap::class.java)
        assetManager.finishLoading()

        screen = LevelScreen(assetManager, "first")
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        super.render()
    }

    override fun dispose() {
        super.dispose()
        assetManager.dispose()
    }
}