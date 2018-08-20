package com.mygdx.game.entities;

import com.mygdx.game.IGameRenderer;

public interface Entity {

    void update(float delta);

    void render(IGameRenderer renderer, float delta);

    void dispose();
}
