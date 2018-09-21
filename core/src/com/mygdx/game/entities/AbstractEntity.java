package com.mygdx.game.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Application;
import com.mygdx.game.entities.EntityPropertiesMap.EntityProperties;

/**
 * AbstractEntity provides a default constructor and base implementation of some methods.
 */
public abstract class AbstractEntity implements Entity {

    protected Application app;
    protected World world;

    protected Rectangle area = new Rectangle();
    private EntityType type;

    protected float width;
    protected float height;

    /**
     * Creates new entity
     *
     * @param app   main application class
     * @param world world to create entity in
     */
    public AbstractEntity(Application app, World world) {
        this.app = app;
        this.world = world;
    }

    @Override
    public void initialize(EntityType type) {
        this.type = type;
        width = EntityPropertiesMap.getEntityProperty(type, EntityProperties.WIDTH);
        height = EntityPropertiesMap.getEntityProperty(type, EntityProperties.HEIGHT);
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public Rectangle getArea() {
        if (area == null) {
            area = new Rectangle(getX(), getY(), getWidth(), getHeight());
        } else {
            area.x = getX();
            area.y = getY();
            area.width = getWidth();
            area.height = getHeight();
        }

        return area;
    }

    @Override
    public EntityType getType() {
        return type;
    }
}
