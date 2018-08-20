package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Application;
import com.mygdx.game.utils.R;
import com.mygdx.game.managers.ScreenManager;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class SplashScreen extends AbstractScreen {

    private Image splashImage;

    public SplashScreen(Application app) {
        super(app);
        stage.setViewport(new FitViewport(Application.V_WIDTH, Application.V_HEIGHT));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Texture texture = app.assets.get(R.images.drake);
        splashImage = new Image(texture);
        splashImage.addAction(sequence(
                moveTo(-stage.getWidth() / 2 - splashImage.getWidth(), -splashImage.getHeight() / 2),
                moveTo(-splashImage.getWidth() / 2, -splashImage.getHeight() / 2, 0.5f, Interpolation.pow5Out),
                delay(.25f),
                run(new Runnable() {
                    @Override
                    public void run() {
                        app.setScreen(ScreenManager.State.MENU);
                    }
                })
        ));

        stage.addActor(splashImage);
    }
}
