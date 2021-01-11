package com.orycion.blockworld.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.math.collision.BoundingBox
import com.orycion.blockworld.components.*
import org.w3c.dom.css.Rect

class CollisionSystem : IteratingSystem(Family.all(PositionComponent::class.java, SizeComponent::class.java, VelocityComponent::class.java).exclude(CollideableComponent::class.java).get()) {
    private val pm = ComponentMapper.getFor(PositionComponent::class.java)
    private val sm = ComponentMapper.getFor(SizeComponent::class.java)
    private val vm = ComponentMapper.getFor(VelocityComponent::class.java)
    private val cm = ComponentMapper.getFor(CollideableComponent::class.java)

    private lateinit var collideableEntities: ImmutableArray<Entity>

    private val tmpRectanglePlayer = Rectangle()
    private val tmpRectangleCollidable = Rectangle()
    private val tmpRectangleIntersection = Rectangle()

    override fun addedToEngine(engine: Engine) {
        super.addedToEngine(engine)
        collideableEntities = engine.getEntitiesFor(Family.all(PositionComponent::class.java, SizeComponent::class.java, CollideableComponent::class.java).get())
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val velocity = vm[entity].velocity
        val position = pm[entity].position
        val size = sm[entity].size

        tmpRectanglePlayer.set(position.x, position.y, size.x, size.y)

        for (collideableEntity in collideableEntities) {
            val collideablePosition = pm[collideableEntity].position
            val collideableSize = sm[collideableEntity].size
            tmpRectangleCollidable.set(collideablePosition.x, collideablePosition.y, collideableSize.x, collideableSize.y)

            if (!Intersector.intersectRectangles(tmpRectanglePlayer, tmpRectangleCollidable, tmpRectangleIntersection)) {
                continue
            }

            if (tmpRectangleIntersection.height < tmpRectangleIntersection.width) {
                if (position.y >= tmpRectangleIntersection.y - tmpRectangleIntersection.height) {
                    position.y = collideablePosition.y + collideableSize.y
                    velocity.y = 0f
                } else {
                    position.y = collideablePosition.y - size.y
                    velocity.y = 0f
                }
            } else {
                if (position.x >= tmpRectangleIntersection.x - tmpRectangleIntersection.width) {
                    position.x = collideablePosition.x + collideableSize.x
                    velocity.x = 0f
                } else {
                    position.x = collideablePosition.x - size.x
                    velocity.x = 0f
                }
            }
        }
    }
}
