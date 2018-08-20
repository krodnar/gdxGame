package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.managers.Assets;
import com.mygdx.game.managers.ScreenManager;
import com.mygdx.game.managers.ScreenManager.State;

public class Application extends Game {

    public static final String TITLE = "Mess";
    public static final String VERSION = "0.3.0";
    public static final int APP_WIDTH = 1920;
    public static final int APP_HEIGHT = 1080;
    public static final int APP_FPS = 60;

    public static final int V_WIDTH = 800;
    public static final int V_HEIGHT = 600;

    public OrthographicCamera camera;
    public SpriteBatch batch;

    public Assets assets;
    private ScreenManager screenManager;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();

        assets = new Assets();
        screenManager = new ScreenManager(this);

        screenManager.setScreen(State.LOADING);
    }

    @Override
    public void render() {
        super.render();
    }

    public void setScreen(State state) {
        screenManager.setScreen(state);
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        assets.dispose();
        screenManager.dispose();
    }

}
