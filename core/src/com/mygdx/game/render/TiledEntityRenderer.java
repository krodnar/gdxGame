package com.mygdx.game.render;

import com.badlogic.gdx.math.Vector2;

/**
 * A renderer that is used to render entities between multiple map layers.
 */
public interface TiledEntityRenderer extends EntityRenderer {

    /**
     * Gets entity bottom point.
     *
     * @return entity bottom
     */
    Vector2 getEntityBottom();
}
