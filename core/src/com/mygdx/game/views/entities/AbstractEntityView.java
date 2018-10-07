package com.mygdx.game.views.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Application;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.render.IGameRenderer;
import com.mygdx.game.utils.Constants;

import static com.mygdx.game.utils.Constants.PPM;

/**
 * A class that provides partial generic implementation of {@link EntityView} for most common
 * methods that can be overridden in next implementations.
 *
 * @param <T> class of the entity
 */
public abstract class AbstractEntityView<T extends Entity> implements EntityView<T> {

    protected Application app;

    protected T entity;
    protected Sprite sprite;

    protected float xOffset;
    protected float yOffset;

    private final Rectangle area = new Rectangle();

    /**
     * Creates an AbstractEntityView and initializes it for specified entity.
     *
     * @param app    main application class
     * @param entity entity for initialization
     * @see #initialize(Entity)
     */
    public AbstractEntityView(Application app, T entity) {
        this.app = app;
        preload();
        initialize(entity);
    }

    /**
     * Preloads all necessary assets for this view.
     */
    public void preload() {

    }

    /**
     * Returns {@link Sprite} of the entity for rendering at some point of time based on
     * its properties, like state, speed and so forth.
     *
     * @return sprite that needs to be rendered
     */
    protected abstract Sprite getSprite();

    /**
     * Initializes this view for a specified entity.
     *
     * @param entity entity of this view
     */
    @Override
    public void initialize(T entity) {
        this.entity = entity;
    }

    /**
     * Renders this entity sprite considering offsets.
     *
     * @param gameRenderer main game renderer
     */
    @Override
    public void render(IGameRenderer gameRenderer) {
        sprite = getSprite();
        gameRenderer.getBatch().draw(sprite,
                getX() - getWidth() / 2 + getXOffset(),
                getY() - getHeight() / 2 + getYOffset());
    }

    @Override
    public Rectangle getArea() {
        area.set(getX() + getXOffset(), getY() + getYOffset(), getWidth(), getHeight());
        return area;
    }

    @Override
    public Rectangle getDrawingArea() {
        return getArea();
    }

    @Override
    public boolean isVisibleAt(Rectangle viewBounds) {
        return viewBounds.overlaps(getDrawingArea());
    }

    @Override
    public T getEntity() {
        return entity;
    }

    @Override
    public float getWidth() {
        if (sprite != null ){
            return sprite.getWidth();
        }

        return Constants.PPM;
    }

    @Override
    public float getHeight() {
        if (sprite != null ){
            return sprite.getHeight();
        }

        return Constants.PPM;
    }

    @Override
    public Vector2 getCoordinates() {
        return entity.getPosition();
    }

    @Override
    public float getX() {
        return entity.getPosition().x * PPM;
    }

    @Override
    public float getY() {
        return entity.getPosition().y * PPM;
    }

    /**
     * @return X axis offset for sprite
     */
    protected float getXOffset() {
        return xOffset;
    }

    /**
     * Sets X axis offset for sprite.
     *
     * @param xOffset offset value
     */
    protected void setXOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    /**
     * @return Y axis offset for sprite
     */
    protected float getYOffset() {
        return yOffset;
    }

    /**
     * Sets Y axis offset for sprite.
     *
     * @param yOffset offset value
     */
    protected void setYOffset(float yOffset) {
        this.yOffset = yOffset;
    }
}
