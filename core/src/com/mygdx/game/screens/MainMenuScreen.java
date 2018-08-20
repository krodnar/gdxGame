package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Application;
import com.mygdx.game.utils.R;
import com.mygdx.game.managers.ScreenManager;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class MainMenuScreen extends AbstractScreen {

    private Skin skin;
    private TextButton buttonPlay;
    private TextButton buttonExit;

    public MainMenuScreen(Application app) {
        super(app);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        stage.clear();

        skin = new Skin();
        skin.addRegions(app.assets.get(R.ui.skin_default_atlas, TextureAtlas.class));
        skin.load(Gdx.files.internal(R.ui.skin_default_json));

        initButtons();
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            app.setScreen(ScreenManager.State.GAME);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    private void initButtons() {
        float centerX = stage.getWidth() / 2;
        float centerY = stage.getHeight() / 2;

        buttonPlay = new TextButton("Play", skin);
        buttonPlay.setSize(200, 50);
        buttonPlay.setPosition(centerX - buttonPlay.getWidth() / 2, centerY + 30);
        buttonPlay.addAction(getButtonsAction());
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(ScreenManager.State.GAME);
            }
        });

        buttonExit = new TextButton("Exit", skin);
        buttonExit.setSize(200, 50);
        buttonExit.setPosition(centerX - buttonExit.getWidth() / 2, centerY - 30);
        buttonExit.addAction(getButtonsAction());
        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        stage.addActor(buttonPlay);
        stage.addActor(buttonExit);
    }

    private Action getButtonsAction() {
        return sequence(alpha(0), parallel(
                fadeIn(0.25f),
                moveBy(0, -20, 0.25f, Interpolation.pow5Out)
        ));
    }

    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
    }
}
