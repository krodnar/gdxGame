package com.mygdx.game.render;

import com.mygdx.game.entities.Entity;

public interface EntityRenderer<V extends Entity> {

    void render(V entity, IGameRenderer renderer, float delta);

    void dispose();
}
