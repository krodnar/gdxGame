package com.mygdx.game.render;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.game.utils.Constants.PPM;

/**
 * A class that manages multiple {@link TiledEntityRenderer}'s to draw their entities at right
 * layer.
 */
public class TiledRenderersController {

    private GameRenderer gameRenderer;

    private float tileHeight = -1;

    private List<TiledEntityRenderer> renderers;
    private List<List<TiledEntityRenderer>> rows;

    /**
     * Creates new TiledRenderersController.
     *
     * @param gameRenderer main game renderer
     */
    public TiledRenderersController(GameRenderer gameRenderer) {
        this.gameRenderer = gameRenderer;
    }

    /**
     * Creates new TiledRenderersController with specified renderers.
     *
     * @param gameRenderer main game renderer
     * @param renderers    list of renderers to control
     */
    public TiledRenderersController(GameRenderer gameRenderer, List<TiledEntityRenderer> renderers) {
        this(gameRenderer);
        this.renderers = renderers;
    }

    /**
     * Create new TiledRenderersController with specified renderers and height of a tile and a layer.
     *
     * @param gameRenderer main game renderer
     * @param renderers    list of renderers to control
     * @param layerHeight  height of a layer
     * @param tileHeight   height of a tile
     */
    public TiledRenderersController(GameRenderer gameRenderer, List<TiledEntityRenderer> renderers, float layerHeight, float tileHeight) {
        this(gameRenderer, renderers);
        resetSizes(tileHeight, layerHeight);
    }

    /**
     * Renders entities of controlled renderers if needed.
     *
     * @param layerY the y coordinate of a layer to render on
     */
    public void render(float layerY) {
        if (tileHeight == -1) {
            throw new IllegalStateException("Tile and layer heights are not set");
        }

        int index = (int) Math.floor(layerY / tileHeight);
        List<TiledEntityRenderer> row = rows.get(index);

        for (TiledEntityRenderer renderer : row) {
            renderer.render(gameRenderer);
        }
    }

    /**
     * Recalculates sizes and restructures renderers.
     *
     * @param tileHeight  height of a tile
     * @param layerHeight height of a layer
     */
    public void resetSizes(float tileHeight, float layerHeight) {
        this.tileHeight = tileHeight;

        rows = new ArrayList<List<TiledEntityRenderer>>((int) layerHeight);

        for (int i = 0; i < layerHeight; i++) {
            rows.add(new ArrayList<TiledEntityRenderer>());
        }
    }

    /**
     * Prepares renderers for render. Should be called once before going through every row of
     * a layer.
     *
     * @param xStart x coordinate of beginning of row
     * @param xEnd   x coordinate of end of row
     */
    public void updateRows(float xStart, float xEnd) {
        clearRows();

        if (renderers == null) {
            throw new IllegalStateException("Renderers are not set");
        }

        for (TiledEntityRenderer renderer : renderers) {
            Vector2 position = renderer.getEntityBottom();
            float entityY = position.y * PPM;
            int index = (int) Math.floor(entityY / tileHeight) + 32;

            position.scl(PPM);
            if (position.x < xStart || position.x > xEnd) {
                continue;
            }

            List<TiledEntityRenderer> row = rows.get(index);
            row.add(renderer);
        }
    }

    private void clearRows() {
        for (List<TiledEntityRenderer> row : rows) {
            row.clear();
        }
    }

    public List<TiledEntityRenderer> getRenderers() {
        return renderers;
    }

    public void setRenderers(List<TiledEntityRenderer> renderers) {
        this.renderers = renderers;
    }
}
