package com.mygdx.game.managers;

import com.mygdx.game.Application;
import com.mygdx.game.screens.AbstractScreen;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.LoadingScreen;
import com.mygdx.game.screens.MainMenuScreen;
import com.mygdx.game.screens.SplashScreen;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ScreenManager {

    private final Application app;
    private Map<State, AbstractScreen> screens;

    public enum State {
        LOADING,
        SPLASH,
        MENU,
        GAME
    }

    public ScreenManager(Application app) {
        this.app = app;
        screens = new HashMap<State, AbstractScreen>();
    }

    public void setScreen(State state) {
        app.setScreen(getScreen(state));
    }

    public void dispose() {
        for (AbstractScreen screen : screens.values()) {
            if (screen != null) {
                screen.dispose();
            }
        }
    }

    private AbstractScreen getScreen(State state) {
        if (screens.get(state) == null) {
            return createScreen(state);
        } else {
            return screens.get(state);
        }
    }

    private AbstractScreen createScreen(State state) {
        switch (state) {
            case LOADING:
                return new LoadingScreen(app);
            case SPLASH:
                return new SplashScreen(app);
            case MENU:
                return new MainMenuScreen(app);
            case GAME:
                return new GameScreen(app);
        }

        return null;
    }
}
