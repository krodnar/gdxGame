package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Application;

public class DesktopLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Application.V_WIDTH;
        config.height = Application.V_HEIGHT;
        config.title = Application.TITLE + " v" + Application.VERSION;
        config.backgroundFPS = 60;
        config.foregroundFPS = 60;
        new LwjglApplication(new Application(), config);
    }
}
