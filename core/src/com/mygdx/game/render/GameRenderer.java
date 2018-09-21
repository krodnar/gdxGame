package com.mygdx.game.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.Application;
import com.mygdx.game.GameWorld;
import com.mygdx.game.entities.Player;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.game.utils.Constants.PPM;

/**
 * Main renderer that manages rendering of all {@link GameWorld } components
 * such as map, world and entities. Also it controls a camera.
 */
public class GameRenderer implements Disposable {

    private final Application app;
    private final GameWorld world;

    private TiledMapRenderer mapRenderer;
    private Box2DDebugRenderer debugRenderer;
    private PlayerRenderer playerRenderer;

    private boolean debug = false;

    /**
     * Creates new GameRenderer for specific GameWorld.
     *
     * @param app main app class
     * @param world a world to render
     */
    public GameRenderer(Application app, GameWorld world) {
        this.world = world;
        this.app = app;

        playerRenderer = new PlayerRenderer(app, world.getPlayer());

        List<TiledEntityRenderer> renderers = new ArrayList<TiledEntityRenderer>();
        renderers.add(playerRenderer);

        TiledRenderersController renderersManager = new TiledRenderersController(this, renderers);
        mapRenderer = new TiledMapRenderer(world.getMap(), app.batch);
        mapRenderer.setRenderersManager(renderersManager);

        debugRenderer = new Box2DDebugRenderer();

        app.camera.setToOrtho(false, Application.V_WIDTH, Application.V_HEIGHT);
        app.batch.setProjectionMatrix(app.camera.combined);
    }

    /**
     * Renders all components of world.
     *
     * @param delta time passed after the last frame
     */
    public void render(float delta) {
        cameraUpdate(delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            debug = !debug;
        }

        mapRenderer.setView(app.camera);
        app.batch.setProjectionMatrix(app.camera.combined);

        mapRenderer.renderBackground();
        mapRenderer.renderMiddleground();
        mapRenderer.renderForeground();

        if (debug) {
            debugRenderer.render(world.getWorld(), app.camera.combined.cpy().scl(PPM));
        }
    }

    @Override
    public void dispose() {
        mapRenderer.dispose();
        debugRenderer.dispose();
        playerRenderer.dispose();
    }

    /**
     * Sets camera to player position using linear interpolation for smooth movement.
     *
     * @param delta time passed after the last frame
     */
    private void cameraUpdate(float delta) {
        Player player = world.getPlayer();
        float lerp = 0.2f;

        Vector3 position = app.camera.position;
        position.x = MathUtils.lerp(app.camera.position.x, player.getPosition().x * PPM, lerp);
        position.y = MathUtils.lerp(app.camera.position.y, player.getPosition().y * PPM, lerp);
        app.camera.position.set(position);

        app.camera.update();
    }
}
