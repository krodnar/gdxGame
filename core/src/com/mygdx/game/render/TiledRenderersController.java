package com.mygdx.game.render;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.game.utils.Constants.PPM;

public class TiledRenderersController {

    private IGameRenderer gameRenderer;

    private float tileHeight = -1;

    private List<TiledEntityRenderer> renderers;
    private List<List<TiledEntityRenderer>> rows;

    public TiledRenderersController(IGameRenderer gameRenderer) {
        this.gameRenderer = gameRenderer;
    }

    public TiledRenderersController(IGameRenderer gameRenderer, List<TiledEntityRenderer> renderers) {
        this(gameRenderer);
        this.renderers = renderers;
    }

    public TiledRenderersController(IGameRenderer gameRenderer, List<TiledEntityRenderer> renderers, float layerHeight, float tileHeight) {
        this(gameRenderer, renderers);
        resetSizes(tileHeight, layerHeight);
    }

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

    public void resetSizes(float tileHeight, float layerHeight) {
        this.tileHeight = tileHeight;

        rows = new ArrayList<List<TiledEntityRenderer>>((int) layerHeight);

        for (int i = 0; i < layerHeight; i++) {
            rows.add(new ArrayList<TiledEntityRenderer>());
        }
    }

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
