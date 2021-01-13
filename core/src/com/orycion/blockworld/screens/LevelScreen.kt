package com.orycion.blockworld.screens

import com.badlogic.ashley.core.Family
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.orycion.blockworld.components.ControllableComponent
import com.orycion.blockworld.components.PositionComponent
import com.orycion.blockworld.components.SizeComponent
import com.orycion.blockworld.engines.LevelEngine
import com.orycion.blockworld.maps.LevelMap
import com.orycion.blockworld.systems.CameraSystem

class LevelScreen(assetManager: AssetManager, fileName: String) : ScreenAdapter() {
    private val map = assetManager.get<TiledMap>("levels/$fileName.xml")

    private val camera = OrthographicCamera()
    private val viewport = ScreenViewport(camera)
    private val spriteBatch = SpriteBatch()
    private val mapRenderer = OrthogonalTiledMapRenderer(map, LevelMap.UNIT_SCALE, spriteBatch)
    private val shapeRenderer = ShapeRenderer()
    private val engine = LevelEngine(LevelMap(map), camera)

    init {
        viewport.unitsPerPixel = LevelMap.UNIT_SCALE
    }

    override fun dispose() {
        shapeRenderer.dispose()
        mapRenderer.dispose()
        spriteBatch.dispose()
        map.dispose()
    }

    override fun render(deltaTime: Float) {
        engine.update(deltaTime)

        mapRenderer.setView(camera)
        mapRenderer.render()

        shapeRenderer.projectionMatrix = camera.combined

        for (player in engine.getEntitiesFor(Family.all(ControllableComponent::class.java).get())) {
            val position = player.getComponent(PositionComponent::class.java).position
            val size = player.getComponent(SizeComponent::class.java).size
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
            shapeRenderer.rect(position.x, position.y, size.x, size.y)
            shapeRenderer.end()
        }

        val cameraSystem = engine.getSystem(CameraSystem::class.java)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        val minCorner = Vector3()
        cameraSystem.boundingBox.getMin(minCorner)
        val maxCorner = Vector3()
        cameraSystem.boundingBox.getMax(maxCorner)
        shapeRenderer.rect(minCorner.x, minCorner.y, maxCorner.x - minCorner.x, maxCorner.y - minCorner.y)
        shapeRenderer.end()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }
}
