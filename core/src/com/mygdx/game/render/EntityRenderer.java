package com.mygdx.game.render;

import com.badlogic.gdx.utils.Disposable;

/**
 * An EntityRenderer is used to render a specific entity.
 */
public interface EntityRenderer extends Disposable {

    /**
     * Renders an entity.
     *
     * @param renderer main game renderer
     */
    void render(GameRenderer renderer);
}
