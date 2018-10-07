package com.mygdx.game.render;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.views.entities.EntityView;

/**
 * A manager of views for rendering certain {@link EntityView} based on their position on map.
 * <p>
 * Basically this class provides convenient way of drawing entities row by row so renderer can
 * render them behind or in front of other layers since every next row is rendered on top of
 * previous.
 *
 * @see EntityView
 * @see TiledMapRenderer
 */
public class EntityViewsManager {

    private final GameRenderer gameRenderer;

    private float tileHeight = -1;

    private Array<EntityView> views;
    private Array<Array<EntityView>> rows;

    /**
     * Creates new EntityViewsManager without any {@link EntityView entity views}.
     *
     * @param gameRenderer renderer that will be used for rendering
     */
    public EntityViewsManager(GameRenderer gameRenderer) {
        this.gameRenderer = gameRenderer;
    }

    /**
     * Creates new EntityViewsManager with given {@link EntityView entity views}.
     *
     * @param gameRenderer renderer that will be used for rendering
     * @param views        entity views to render
     */
    public EntityViewsManager(GameRenderer gameRenderer, Array<EntityView> views) {
        this(gameRenderer);
        this.views = views;
    }

    /**
     * Renders all necessary {@link EntityView}s for the row.
     * <p>
     * Note that {@link #updateSizes(float, float, float, float)} should be called once before
     * rendering.
     *
     * @param rowY y coordinate of the row
     */
    public void render(float rowY) {
        if (tileHeight == -1) {
            throw new IllegalStateException("Tile and layer heights are not set");
        }

        int index = (int) Math.floor(rowY / tileHeight);
        Array<EntityView> row = rows.get(index);

        for (EntityView view : row) {
            view.render(gameRenderer);
        }
    }

    /**
     * Resets current state and arranges {@link EntityView}s on rows on which they should be
     * rendered.
     *
     * @param tileHeight  height of tiles of layer on which entities will be rendered
     * @param layerHeight height of layer on which entities will be rendered
     * @param xStart      start of the row
     * @param xEnd        end of the row
     */
    public void updateSizes(float tileHeight, float layerHeight, float xStart, float xEnd) {
        this.tileHeight = tileHeight;

        rows = new Array<>((int) layerHeight);

        for (int i = 0; i < layerHeight; i++) {
            rows.add(new Array<EntityView>());
        }

        updateRows(new Rectangle(xStart, 0, xEnd - xStart, tileHeight * layerHeight));
    }

    /**
     * Updates all {@link EntityView}s arranging them on right rows.
     *
     * @param viewBounds bounds of current area that is being rendered
     */
    private void updateRows(Rectangle viewBounds) {
        clearRows();

        if (views == null) {
            throw new IllegalStateException("Renderers are not set");
        }

        for (EntityView view : views) {
            if (!view.isVisibleAt(viewBounds)) {
                continue;
            }

            arrangeView(view);
        }
    }

    /**
     * Sets a {@link EntityView} on right row.
     *
     * @param view view to arrange
     */
    private void arrangeView(EntityView view) {
        int index = (int) Math.floor(view.getY() / tileHeight);

        Array<EntityView> row = rows.get(index);
        row.add(view);
    }

    /**
     * Removes {@link EntityView}s from all rows.
     */
    private void clearRows() {
        for (Array<EntityView> row : rows) {
            row.clear();
        }
    }

    /**
     * Adds one more {@link EntityView} for rendering.
     *
     * @param entityView view to render
     */
    public void addView(EntityView entityView) {
        views.add(entityView);
        arrangeView(entityView);
    }

    /**
     * @return {@link EntityView}s used in this manager
     */
    public Array<EntityView> getViews() {
        return views;
    }

    /**
     * Sets {@link EntityView}s for rendering.
     *
     * @param views views to render
     */
    public void setViews(Array<EntityView> views) {
        this.views = views;
    }

    /**
     * @return true if there are no views, false otherwise
     */
    public boolean isEmpty() {
        return views.size == 0;
    }
}
