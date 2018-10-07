package com.mygdx.game.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * An Entity is any objects that exists in the game world.
 */
public interface Entity {

    /**
     * Updates the entity according to its properties.
     *
     * @param delta time passed after the last frame
     */
    void update(float delta);

    /**
     * Gets the x coordinate in world units.
     *
     * @return the x coordinate
     */
    float getX();

    /**
     * Gets the y coordinate in world units.
     *
     * @return the y coordinate
     */
    float getY();

    /**
     * Gets a width of the entity.
     *
     * @return width
     */
    float getWidth();

    /**
     * Gets a height of the entity.
     *
     * @return height
     */
    float getHeight();

    /**
     * Gets a position of the entity in world units.
     *
     * @return position of the entity
     */
    Vector2 getPosition();

    /**
     * Gets an area of the entity based on its position and size.
     *
     * @return area of the entity
     */
    Rectangle getArea();

    /**
     * Gets type of the entity.
     *
     * @return type of the entity
     */
    EntityType getType();

    /**
     * Initializes entity accordingly to its type.
     *
     * @param type type of the entity
     */
    void initialize(EntityType type);
}
