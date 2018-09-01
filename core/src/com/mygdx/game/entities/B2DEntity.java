package com.mygdx.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Application;

public abstract class B2DEntity extends AbstractEntity {

    protected Body body;

    public B2DEntity(Application app, World world) {
        super(app, world);
        createBodies();
    }

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

    protected void createBodies() {

    }

    protected void createBodies(Shape shape) {

    }

    public Body getBody() {
        return body;
    }
}
