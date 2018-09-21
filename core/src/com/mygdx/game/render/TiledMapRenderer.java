package com.mygdx.game.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.mygdx.game.render.TiledRenderersController;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.graphics.g2d.Batch.C1;
import static com.badlogic.gdx.graphics.g2d.Batch.C2;
import static com.badlogic.gdx.graphics.g2d.Batch.C3;
import static com.badlogic.gdx.graphics.g2d.Batch.C4;
import static com.badlogic.gdx.graphics.g2d.Batch.U1;
import static com.badlogic.gdx.graphics.g2d.Batch.U2;
import static com.badlogic.gdx.graphics.g2d.Batch.U3;
import static com.badlogic.gdx.graphics.g2d.Batch.U4;
import static com.badlogic.gdx.graphics.g2d.Batch.V1;
import static com.badlogic.gdx.graphics.g2d.Batch.V2;
import static com.badlogic.gdx.graphics.g2d.Batch.V3;
import static com.badlogic.gdx.graphics.g2d.Batch.V4;
import static com.badlogic.gdx.graphics.g2d.Batch.X1;
import static com.badlogic.gdx.graphics.g2d.Batch.X2;
import static com.badlogic.gdx.graphics.g2d.Batch.X3;
import static com.badlogic.gdx.graphics.g2d.Batch.X4;
import static com.badlogic.gdx.graphics.g2d.Batch.Y1;
import static com.badlogic.gdx.graphics.g2d.Batch.Y2;
import static com.badlogic.gdx.graphics.g2d.Batch.Y3;
import static com.badlogic.gdx.graphics.g2d.Batch.Y4;

/**
 * A map renderer that provides a way to render objects in-between map layers.
 * <p>
 * TiledMapRenderer provides methods for drawing separately background, middleground and foreground.
 * Background is always under any objects, foreground is vice versa. Middleground consists of
 * objects that can be in front or behind any entities. Renderer draws entities in front any object
 * if its y coordinate is lower and vice versa. Additional check of z-index is under consideration.
 * <p>
 * For proper work of renderer a map should consist of three layers that are named "background",
 * "middleground" and "foreground". This layers can be {@link MapGroupLayer} but inherited
 * group layers currently are not supported.
 */
public class TiledMapRenderer extends BatchTiledMapRenderer {

    private TiledRenderersController renderersManager;

    private int backgroundLayer = -1;
    private int foregroundLayer = -1;
    private int middleLayer = -1;

    private float color;

    private int layerWidth;
    private int layerHeight;

    private float layerTileWidth;
    private float layerTileHeight;

    private float layerOffsetX;
    private float layerOffsetY;

    private int rowStart;
    private int rowEnd;

    private int colStart;
    private int colEnd;

    /**
     * Creates TiledMapRenderer for a specific map and own batch.
     *
     * @param map tiled map to render
     */
    public TiledMapRenderer(TiledMap map) {
        super(map);
        getMapLayersIndices();
    }

    /**
     * Creates TiledMapRenderer for a map using specified batch.
     *
     * @param map   tiled map to render
     * @param batch batch to use for rendering
     */
    public TiledMapRenderer(TiledMap map, Batch batch) {
        super(map, batch);
        getMapLayersIndices();
    }

    /**
     * Creates TiledMapRenderer for a map scaled to passed unit.
     *
     * @param map       tiled map to render
     * @param unitScale scale coefficient
     */
    public TiledMapRenderer(TiledMap map, float unitScale) {
        super(map, unitScale);
        getMapLayersIndices();
    }

    /**
     * Creates TiledMapRenderer for a map scaled to passed unit using specified batch.
     *
     * @param map       tiled map to render
     * @param unitScale scale coefficient
     * @param batch     batch to use for rendering
     */
    public TiledMapRenderer(TiledMap map, float unitScale, Batch batch) {
        super(map, unitScale, batch);
        getMapLayersIndices();
    }

    /**
     * Renders background layer(s).
     */
    public void renderBackground() {
        if (backgroundLayer == -1) {
            Gdx.app.error("TiledMapRenderer", "Background layer is not set");
            return;
        }

        render(new int[]{backgroundLayer});
    }

    /**
     * Renders foreground layer(s).
     */
    public void renderForeground() {
        if (foregroundLayer == -1) {
            Gdx.app.error("TiledMapRenderer", "Foreground layer is not set");
            return;
        }

        render(new int[]{foregroundLayer});
    }

    /**
     * Renders middleground layer(s) and entities.
     */
    public void renderMiddleground() {
        if (middleLayer == -1) {
            Gdx.app.error("TiledMapRenderer", "Middleground layer is not set");
            return;
        }

        if (renderersManager == null) {
            render(new int[]{middleLayer});
            return;
        }

        MapLayer layer = map.getLayers().get(middleLayer);
        MapGroupLayer groupLayer;

        List<TiledMapTileLayer> tileLayers = new ArrayList<TiledMapTileLayer>();

        if (layer instanceof TiledMapTileLayer) {
            tileLayers.add((TiledMapTileLayer) layer);
        } else if (layer instanceof MapGroupLayer) {
            groupLayer = (MapGroupLayer) layer;

            for (MapLayer mapLayer : groupLayer.getLayers()) {
                if (mapLayer instanceof TiledMapTileLayer) {
                    tileLayers.add((TiledMapTileLayer) mapLayer);
                }
            }
        }

        if (tileLayers.isEmpty()) {
            Gdx.app.error("TiledMapRenderer", "There is no tile layers in middleground layer");
            return;
        }

        batch.begin();
        renderLayersAndObjects(tileLayers);
        batch.end();
    }

    /**
     * Renders specific area of a tile layer.
     *
     * @param layer tile layer for rendering
     */
    @Override
    public void renderTileLayer(TiledMapTileLayer layer) {
        setSizes(layer);

        float y = rowEnd * layerTileHeight + layerOffsetY;
        float xStart = colStart * layerTileWidth + layerOffsetX;

        for (int row = rowEnd; row >= rowStart; row--) {
            float x = xStart;
            for (int col = colStart; col < colEnd; col++) {
                final TiledMapTileLayer.Cell cell = layer.getCell(col, row);
                if (cell == null) {
                    x += layerTileWidth;
                    continue;
                }
                final TiledMapTile tile = cell.getTile();

                if (tile != null) {
                    renderTile(cell, x, y, color);
                }
                x += layerTileWidth;
            }
            y -= layerTileHeight;
        }
    }

    /**
     * Renders multiple layers and objects that can be in-front or behind them.
     *
     * @param layers layers for rendering
     */
    private void renderLayersAndObjects(List<TiledMapTileLayer> layers) {
        TiledMapTileLayer layer = layers.get(0);

        setSizes(layer);

        float y = rowEnd * layerTileHeight + layerOffsetY;
        float xStart = colStart * layerTileWidth + layerOffsetX;
        float xEnd = colEnd * layerTileWidth + layerOffsetX;

        renderersManager.resetSizes(layerTileHeight, layerHeight);
        renderersManager.updateRows(xStart, xEnd);

        for (int row = rowEnd; row >= rowStart; row--) {

            float x = xStart;
            renderersManager.render(y);

            for (int col = colStart; col < colEnd; col++) {

                for (TiledMapTileLayer tileLayer : layers) {
                    TiledMapTileLayer.Cell cell = tileLayer.getCell(col, row);

                    if (cell == null) {
                        continue;
                    }

                    final TiledMapTile tile = cell.getTile();

                    if (tile != null) {
                        renderTile(cell, x, y, color);
                    }
                }
                x += layerTileWidth;
            }
            y -= layerTileHeight;
        }
    }

    /**
     * Defines sizes and offsets of layer that are being used during the render.
     *
     * @param layer layer to get sizes from
     */
    private void setSizes(TiledMapTileLayer layer) {
        final Color batchColor = batch.getColor();
        color = Color.toFloatBits(batchColor.r, batchColor.g, batchColor.b, batchColor.a * layer.getOpacity());

        layerWidth = layer.getWidth();
        layerHeight = layer.getHeight();

        layerTileWidth = layer.getTileWidth() * unitScale;
        layerTileHeight = layer.getTileHeight() * unitScale;

        layerOffsetX = layer.getRenderOffsetX() * unitScale;
        // offset in tiled is y down, so we flip it
        layerOffsetY = -layer.getRenderOffsetY() * unitScale;

        colStart = Math.max(0, (int)((viewBounds.x - layerOffsetX) / layerTileWidth));
        colEnd = Math.min(layerWidth,
                (int)((viewBounds.x + viewBounds.width + layerTileWidth - layerOffsetX) / layerTileWidth));

        rowStart = Math.max(0, (int)((viewBounds.y - layerOffsetY) / layerTileHeight));
        rowEnd = Math.min(layerHeight,
                (int)((viewBounds.y + viewBounds.height + layerTileHeight - layerOffsetY) / layerTileHeight));
    }

    /**
     * Defines indices of back- middle- and foreground layers.
     */
    private void getMapLayersIndices() {
        MapLayers layers = map.getLayers();
        for (int i = 0; i < layers.size(); i++) {
            String name = layers.get(i).getName();

            if (name.equalsIgnoreCase("background")) {
                backgroundLayer = i;
            } else if (name.equalsIgnoreCase("foreground")) {
                foregroundLayer = i;
            } else if (name.equalsIgnoreCase("middleground")) {
                middleLayer = i;
            }
        }
    }

    /**
     * Renders one tile of tile layers
     *
     * @param cell  cell from which to get tile
     * @param x     the x coordinate
     * @param y     the y coordinate
     * @param color current batch color
     */
    private void renderTile(TiledMapTileLayer.Cell cell, float x, float y, float color) {
        final boolean flipX = cell.getFlipHorizontally();
        final boolean flipY = cell.getFlipVertically();
        final int rotations = cell.getRotation();

        TiledMapTile tile = cell.getTile();
        TextureRegion region = tile.getTextureRegion();

        float x1 = x + tile.getOffsetX() * unitScale;
        float y1 = y + tile.getOffsetY() * unitScale;
        float x2 = x1 + region.getRegionWidth() * unitScale;
        float y2 = y1 + region.getRegionHeight() * unitScale;

        float u1 = region.getU();
        float v1 = region.getV2();
        float u2 = region.getU2();
        float v2 = region.getV();

        vertices[X1] = x1;
        vertices[Y1] = y1;
        vertices[C1] = color;
        vertices[U1] = u1;
        vertices[V1] = v1;

        vertices[X2] = x1;
        vertices[Y2] = y2;
        vertices[C2] = color;
        vertices[U2] = u1;
        vertices[V2] = v2;

        vertices[X3] = x2;
        vertices[Y3] = y2;
        vertices[C3] = color;
        vertices[U3] = u2;
        vertices[V3] = v2;

        vertices[X4] = x2;
        vertices[Y4] = y1;
        vertices[C4] = color;
        vertices[U4] = u2;
        vertices[V4] = v1;

        if (flipX) {
            float temp = vertices[U1];
            vertices[U1] = vertices[U3];
            vertices[U3] = temp;
            temp = vertices[U2];
            vertices[U2] = vertices[U4];
            vertices[U4] = temp;
        }
        if (flipY) {
            float temp = vertices[V1];
            vertices[V1] = vertices[V3];
            vertices[V3] = temp;
            temp = vertices[V2];
            vertices[V2] = vertices[V4];
            vertices[V4] = temp;
        }
        if (rotations != 0) {
            switch (rotations) {
                case TiledMapTileLayer.Cell.ROTATE_90: {
                    float tempV = vertices[V1];
                    vertices[V1] = vertices[V2];
                    vertices[V2] = vertices[V3];
                    vertices[V3] = vertices[V4];
                    vertices[V4] = tempV;

                    float tempU = vertices[U1];
                    vertices[U1] = vertices[U2];
                    vertices[U2] = vertices[U3];
                    vertices[U3] = vertices[U4];
                    vertices[U4] = tempU;
                    break;
                }
                case TiledMapTileLayer.Cell.ROTATE_180: {
                    float tempU = vertices[U1];
                    vertices[U1] = vertices[U3];
                    vertices[U3] = tempU;
                    tempU = vertices[U2];
                    vertices[U2] = vertices[U4];
                    vertices[U4] = tempU;
                    float tempV = vertices[V1];
                    vertices[V1] = vertices[V3];
                    vertices[V3] = tempV;
                    tempV = vertices[V2];
                    vertices[V2] = vertices[V4];
                    vertices[V4] = tempV;
                    break;
                }
                case TiledMapTileLayer.Cell.ROTATE_270: {
                    float tempV = vertices[V1];
                    vertices[V1] = vertices[V4];
                    vertices[V4] = vertices[V3];
                    vertices[V3] = vertices[V2];
                    vertices[V2] = tempV;

                    float tempU = vertices[U1];
                    vertices[U1] = vertices[U4];
                    vertices[U4] = vertices[U3];
                    vertices[U3] = vertices[U2];
                    vertices[U2] = tempU;
                    break;
                }
            }
        }
        batch.draw(region.getTexture(), vertices, 0, NUM_VERTICES);
    }

    public void setRenderersManager(TiledRenderersController renderersManager) {
        this.renderersManager = renderersManager;
    }

    public TiledRenderersController getRenderersManager() {
        return renderersManager;
    }
}
