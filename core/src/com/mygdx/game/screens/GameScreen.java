package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.game.Application;
import com.mygdx.game.GameWorld;
import com.mygdx.game.managers.ScreenManager;

public class GameScreen extends AbstractScreen {

    private GameWorld world;
    private GameRenderer renderer;

    public GameScreen(Application app) {
        super(app);
        world = new GameWorld(app);
        renderer = new GameRenderer(app, world);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.setViewport(new FillViewport(
                Application.V_WIDTH,
                Application.V_HEIGHT,
                app.camera
        ));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        renderer.render(delta);
    }

    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            app.setScreen(ScreenManager.State.MENU);
        }

        world.update(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
        world.dispose();
        renderer.dispose();
    }
}
