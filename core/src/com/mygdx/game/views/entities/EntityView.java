package com.mygdx.game.views.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.render.IGameRenderer;

/**
 * A generic view for an entity that is to be rendered.
 *
 * @param <T> class of the entity
 */
public interface EntityView<T extends Entity> extends Disposable {

    /**
     * Renders an entity on a screen.
     *
     * @param gameRenderer main game renderer
     */
    void render(IGameRenderer gameRenderer);

    /**
     * Initializes this view for a specific entity.
     *
     * @param entity entity of this view
     */
    void initialize(T entity);

    /**
     * Returns width and height of the entity at its coordinates.
     *
     * @return width and height of the entity at its coordinates
     */
    Rectangle getArea();

    /**
     * Returns drawing area of the entity which can be different from default area.
     *
     * @return width and height of drawing area of the entity
     */
    Rectangle getDrawingArea();

    /**
     * Checks if entity is visible at some view bounds.
     *
     * @param viewBounds view bounds to check in
     * @return true if entity is visible, false otherwise
     */
    boolean isVisibleAt(Rectangle viewBounds);

    /**
     * @return entity for this view
     */
    T getEntity();

    /**
     * @return width of the entity sprite
     */
    float getWidth();

    /**
     * @return height of the entity sprite
     */
    float getHeight();

    /**
     * @return coordinates of the entity in game world
     */
    Vector2 getCoordinates();

    /**
     * @return x coordinate of the entity on a screen
     */
    float getX();

    /**
     * @return y coordinate of the entity on a screen
     */
    float getY();
}
