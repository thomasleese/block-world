package com.orycion.blockworld.screens

import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.orycion.blockworld.components.PositionComponent
import com.orycion.blockworld.engines.LevelEngine

class LevelScreen(assetManager: AssetManager, fileName: String) : ScreenAdapter() {
    private val map = assetManager.get<TiledMap>("levels/$fileName.xml")

    private val camera = OrthographicCamera()
    private val viewport = ExtendViewport(20f, 20f, camera)
    private val spriteBatch = SpriteBatch()
    private val mapRenderer = OrthogonalTiledMapRenderer(map, 1 / 96f, spriteBatch)
    private val shapeRenderer = ShapeRenderer()
    private val engine = LevelEngine(map, camera)

    override fun dispose() {
        shapeRenderer.dispose()
        mapRenderer.dispose()
        spriteBatch.dispose()
        map.dispose()
    }

    override fun render(deltaTime: Float) {
        engine.update(deltaTime)

        viewport.apply()

        mapRenderer.setView(camera)
        mapRenderer.render()

        shapeRenderer.projectionMatrix = camera.combined
        val position = engine.player.getComponent(PositionComponent::class.java)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.rect(position.x, position.y, 1f, 1f)
        shapeRenderer.end()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }
}
