package com.mygdx.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Application;

/**
 * An entity that exists in Box2D world and keeps reference to its body.
 */
public abstract class B2DEntity extends AbstractEntity {

    protected Body body;

    /**
     * Creates new entity and a body for it. When using this constructor {@link #createBodies()}
     * should be implemented.
     *
     * @param app   main app class
     * @param world world to create entity in
     */
    public B2DEntity(Application app, World world) {
        super(app, world);
        createBodies();
    }

    /**
     * Creates new entity and a body with specified shape for it. When using this constructor
     * {@link #createBodies(Shape)} should be implemented.
     *
     * @param app   main app class
     * @param world world to create entity in
     * @param shape shape of a body
     */
    public B2DEntity(Application app, World world, Shape shape) {
        this(app, world);
        createBodies(shape);
    }

    @Override
    public float getX() {
        return getBody().getPosition().x;
    }

    @Override
    public float getY() {
        return getBody().getPosition().y;
    }

    @Override
    public Vector2 getPosition() {
        return getBody().getPosition();
    }

    /**
     * Creates a default body for the entity.
     */
    protected void createBodies() {

    }

    /**
     * Creates a body with specified shape for the entity.
     *
     * @param shape shape of a body
     */
    protected void createBodies(Shape shape) {

    }

    /**
     * Gets a body of the entity.
     *
     * @return body of the entity
     */
    public Body getBody() {
        return body;
    }
}
