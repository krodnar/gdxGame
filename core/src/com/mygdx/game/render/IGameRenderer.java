package com.mygdx.game.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface IGameRenderer {

    void update(float delta);

    void render(float delta);

    void dispose();

    SpriteBatch getBatch();
}
