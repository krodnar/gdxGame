package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Application;
import com.mygdx.game.managers.ScreenManager;

public class LoadingScreen extends AbstractScreen {

    private ShapeRenderer renderer;
    private float progress;
    private boolean loaded = false;

    public LoadingScreen(Application app) {
        super(app);
    }

    @Override
    public void show() {
        loadAssets();
        renderer = new ShapeRenderer();
    }

    private void loadAssets() {
        app.assets.initLoad();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.WHITE);
        renderer.rect(0, 32, progress * stage.getWidth(), 4);
        renderer.end();
    }

    public void update(float delta) {
        if (app.assets.update() && !loaded) {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    app.setScreen(ScreenManager.State.SPLASH);
                }
            }, 0.5f);

            loaded = true;
        }

        progress = MathUtils.lerp(progress, app.assets.getProgress(), 0.2f);
    }

    @Override
    public void dispose() {
        super.dispose();
        renderer.dispose();
    }
}
