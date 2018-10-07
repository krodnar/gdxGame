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
import com.badlogic.gdx.utils.Array;

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
 * A renderer for {@link TiledMap} with functionality for separate rendering of background,
 * foreground and middle layer with entities between them.
 * <p>
 * This requires map layers to have specific names - "background", "middleground" and "foreground".
 * Layers other then these will be ignored. Main principle of creating map layers is to make
 * three group layers with corresponding names and all necessary layers in there.
 * <p>
 * The background is always displayed behind everything and the foreground is displayed in front
 * of everything. The middle layer is objects that can be behind or in-front of entities
 * depending on their position.
 * <p>
 * Such rendering is achieved by simply rendering row by row with entity views on a top of each
 * other instead of drawing layer by layer.
 *
 * @see EntityViewsManager
 * @see com.mygdx.game.views.entities.EntityView
 */
public class TiledMapRenderer extends BatchTiledMapRenderer {

    private EntityViewsManager entityViewsManager;

    private int backLayerIndex = -1;
    private int foreLayerIndex = -1;
    private int midLayerIndex = -1;

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
     * Creates new TiledMapRenderer with its own {@link com.badlogic.gdx.graphics.g2d.SpriteBatch}.
     *
     * @param map map to render
     */
    public TiledMapRenderer(TiledMap map) {
        super(map);
        setMapLayersIndices();
    }

    /**
     * Creates new TiledMapRenderer with given batch.
     *
     * @param map   map to render
     * @param batch batch that is to be used for rendering
     */
    public TiledMapRenderer(TiledMap map, Batch batch) {
        super(map, batch);
        setMapLayersIndices();
    }

    /**
     * Creates new TiledMapRenderer with own batch and rendering scaled to given scale unit.
     *
     * @param map       map to render
     * @param unitScale scaling unit of render
     */
    public TiledMapRenderer(TiledMap map, float unitScale) {
        super(map, unitScale);
        setMapLayersIndices();
    }

    /**
     * Creates new TiledMapRenderer with given batch and scale unit.
     *
     * @param map       map to render
     * @param unitScale scaling unit of render
     * @param batch     batch that is to be used for rendering
     */
    public TiledMapRenderer(TiledMap map, float unitScale, Batch batch) {
        super(map, unitScale, batch);
        setMapLayersIndices();
    }

    /**
     * Renders background, foreground and middle layer with entity views between them.
     */
    @Override
    public void render() {
        MapLayer backLayer = map.getLayers().get(backLayerIndex);
        MapLayer foreLayer = map.getLayers().get(foreLayerIndex);
        MapLayer midLayer = map.getLayers().get(midLayerIndex);
        Array<TiledMapTileLayer> midLayers = getTileLayers(midLayer);

        beginRender();
        renderMapLayer(backLayer);
        if (entityViewsManager != null && !entityViewsManager.isEmpty()) {
            renderLayersAndViews(midLayers);
        } else {
            renderMapLayer(midLayer);
        }
        renderMapLayer(foreLayer);
        endRender();
    }

    /**
     * Renders background.
     */
    public void renderBackground() {
        if (backLayerIndex == -1) {
            Gdx.app.error("TiledMapRenderer", "Background layer is not set");
            return;
        }

        render(new int[]{backLayerIndex});
    }

    /**
     * Renders foreground.
     */
    public void renderForeground() {
        if (foreLayerIndex == -1) {
            Gdx.app.error("TiledMapRenderer", "Foreground layer is not set");
            return;
        }

        render(new int[]{foreLayerIndex});
    }

    /**
     * Renders middle layer.
     */
    public void renderMiddleground() {
        if (midLayerIndex == -1) {
            Gdx.app.error("TiledMapRenderer", "Middleground layer is not set");
            return;
        }

        if (entityViewsManager == null || entityViewsManager.isEmpty()) {
            render(new int[]{midLayerIndex});
            return;
        }

        MapLayer midLayer = map.getLayers().get(midLayerIndex);
        Array<TiledMapTileLayer> tileLayers = getTileLayers(midLayer);

        beginRender();
        renderLayersAndViews(tileLayers);
        endRender();
    }

    /**
     * Renders a tile layer.
     *
     * @param layer layer to render
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
     * @return {@link EntityViewsManager entity views manager} of this renderer
     */
    public EntityViewsManager getEntityViewsManager() {
        return entityViewsManager;
    }

    /**
     * Sets new {@link EntityViewsManager entity views manager} for this renderer
     *
     * @param entityViewsManager
     */
    public void setEntityViewsManager(EntityViewsManager entityViewsManager) {
        this.entityViewsManager = entityViewsManager;
    }

    /**
     * Renders given layers as middle layers and entities.
     * <p>
     * Instead of default rendering that goes layer by layer this method renders all layers
     * rows one by one which allows to render entities behind or in front some objects on map.
     *
     * @param layers layers to render
     */
    protected void renderLayersAndViews(Array<TiledMapTileLayer> layers) {
        TiledMapTileLayer layer = layers.get(0);

        setSizes(layer);

        float y = rowEnd * layerTileHeight + layerOffsetY;
        float xStart = colStart * layerTileWidth + layerOffsetX;
        float xEnd = colEnd * layerTileWidth + layerOffsetX;

        entityViewsManager.updateSizes(layerTileHeight, layerHeight, xStart, xEnd);
        // entityViewsManager.update(xStart, xEnd);

        for (int row = rowEnd; row >= rowStart; row--) {

            float x = xStart;
            entityViewsManager.render(y);

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
     * Sets new sizes of layer and calculates all additional parameters.
     *
     * @param layer layer from which to get sizes
     */
    protected void setSizes(TiledMapTileLayer layer) {
        final Color batchColor = batch.getColor();
        color = Color.toFloatBits(batchColor.r, batchColor.g, batchColor.b, batchColor.a * layer.getOpacity());

        layerWidth = layer.getWidth();
        layerHeight = layer.getHeight();

        layerTileWidth = layer.getTileWidth() * unitScale;
        layerTileHeight = layer.getTileHeight() * unitScale;

        layerOffsetX = layer.getRenderOffsetX() * unitScale;
        // offset in tiled is y down, so we flip it
        layerOffsetY = -layer.getRenderOffsetY() * unitScale;

        colStart = Math.max(0, (int) ((viewBounds.x - layerOffsetX) / layerTileWidth));
        colEnd = Math.min(layerWidth,
                (int) ((viewBounds.x + viewBounds.width + layerTileWidth - layerOffsetX) / layerTileWidth));

        rowStart = Math.max(0, (int) ((viewBounds.y - layerOffsetY) / layerTileHeight));
        rowEnd = Math.min(layerHeight,
                (int) ((viewBounds.y + viewBounds.height + layerTileHeight - layerOffsetY) / layerTileHeight));
    }

    /**
     * Renders one tile of a map.
     *
     * @param cell  cell from which to get tile
     * @param x     x coordinate of tile
     * @param y     y coordinate of tile
     * @param color batch current color
     */
    protected void renderTile(TiledMapTileLayer.Cell cell, float x, float y, float color) {
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

    /**
     * @return mid layers
     */
    private Array<TiledMapTileLayer> getTileLayers(MapLayer layer) {
        return getTileLayers(layer, new Array<TiledMapTileLayer>());
    }

    /**
     * Recursively searches and returns tile layers from given layer.
     *
     * @param layer      layer from which to get layers
     * @param tileLayers collection to fill in with found layers
     * @return all found tile layers
     */
    private Array<TiledMapTileLayer> getTileLayers(MapLayer layer, Array<TiledMapTileLayer> tileLayers) {
        if (layer instanceof MapGroupLayer) {
            MapGroupLayer groupLayer = ((MapGroupLayer) layer);
            for (MapLayer childLayer : groupLayer.getLayers()) {
                getTileLayers(childLayer, tileLayers);
            }
        } else if (layer instanceof TiledMapTileLayer) {
            tileLayers.add((TiledMapTileLayer) layer);
        }

        return tileLayers;
    }

    /**
     * Sets map back- fore- and middleground layers indices based on their names.
     */
    private void setMapLayersIndices() {
        MapLayers layers = map.getLayers();
        for (int i = 0; i < layers.size(); i++) {
            String name = layers.get(i).getName();

            if (name.equalsIgnoreCase("background")) {
                backLayerIndex = i;
            } else if (name.equalsIgnoreCase("foreground")) {
                foreLayerIndex = i;
            } else if (name.equalsIgnoreCase("middleground")) {
                midLayerIndex = i;
            }
        }
    }
}
