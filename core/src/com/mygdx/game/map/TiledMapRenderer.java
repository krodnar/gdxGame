package com.mygdx.game.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TiledMapRenderer extends OrthogonalTiledMapRenderer {

    private int[] backgroundLayer;
    private int[] foregroundLayer;

    public TiledMapRenderer(TiledMap map) {
        super(map);
        getMapLayersIndices();
    }

    public TiledMapRenderer(TiledMap map, Batch batch) {
        super(map, batch);
        getMapLayersIndices();
    }

    public TiledMapRenderer(TiledMap map, float unitScale) {
        super(map, unitScale);
        getMapLayersIndices();
    }

    public TiledMapRenderer(TiledMap map, float unitScale, Batch batch) {
        super(map, unitScale, batch);
        getMapLayersIndices();
    }

    public void renderBackground() {
        render(backgroundLayer);
    }

    public void renderForeground() {
        render(foregroundLayer);
    }

    private void getMapLayersIndices() {
        MapLayers layers = map.getLayers();
        for (int i = 0; i < layers.size(); i++) {
            String name = layers.get(i).getName();

            if (name.equalsIgnoreCase("background")) {
                backgroundLayer = new int[]{i};
            } else if (name.equalsIgnoreCase("foreground")) {
                foregroundLayer = new int[]{i};
            }
        }
    }
}
