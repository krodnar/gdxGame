package com.mygdx.game.managers;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.Application;
import com.mygdx.game.screens.AbstractScreen;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.LoadingScreen;
import com.mygdx.game.screens.MainMenuScreen;
import com.mygdx.game.screens.SplashScreen;

import java.util.HashMap;
import java.util.Map;

/**
 * A screen manager that provides convenient way of switching screens. The manager uses
 * {@link AbstractScreen} and provides lazy initialization for them.
 */
public class ScreenManager implements Disposable {

    private final Application app;
    private Map<State, AbstractScreen> screens;

    /**
     * State of an application. Every state has corresponding {@link Screen}, i.e. {@link LoadingScreen}.
     */
    public enum State {
        LOADING,
        SPLASH,
        MENU,
        GAME
    }

    /**
     * Creates a ScreenManager with provided {@link Application}, which will be passed to screens.
     *
     * @param app application main class
     */
    public ScreenManager(Application app) {
        this.app = app;
        screens = new HashMap<>();
    }

    /**
     * Sets current screen accordingly to the specified state.
     * {@link Screen#hide()} is called on old screen, and {@link Screen#show()} is called on the
     * new screen. Uses lazy initialization.
     *
     * @param state state of an application to switch on
     */
    public void setScreen(State state) {
        app.setScreen(getScreen(state));
    }

    @Override
    public void dispose() {
        for (AbstractScreen screen : screens.values()) {
            if (screen != null) {
                screen.dispose();
            }
        }
    }

    private AbstractScreen getScreen(State state) {
        if (screens.get(state) == null) {
            AbstractScreen screen = createScreen(state);
            screens.put(state, screen);
            return screen;
        }

        return screens.get(state);
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
