package com.mygdx.game.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.mygdx.game.Application;
import com.mygdx.game.IGameWorld;
import com.mygdx.game.entities.Player;
import com.mygdx.game.map.TiledMapRenderer;

import static com.mygdx.game.utils.Constants.PPM;

public class GameRenderer implements IGameRenderer {

    private Application app;

    private IGameWorld world;

    private TiledMapRenderer mapRenderer;
    private Box2DDebugRenderer debugRenderer;
    private PlayerRenderer playerRenderer;

    private boolean debug = false;

    public GameRenderer(Application app, IGameWorld world) {
        this.world = world;
        this.app = app;

        mapRenderer = new TiledMapRenderer(world.getMap(), app.batch);
        debugRenderer = new Box2DDebugRenderer();
        playerRenderer = new PlayerRenderer(app);

        app.camera.setToOrtho(false, Application.V_WIDTH, Application.V_HEIGHT);
        app.batch.setProjectionMatrix(app.camera.combined);
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render(float delta) {
        cameraUpdate(delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            debug = !debug;
        }

        mapRenderer.setView(app.camera);
        app.batch.setProjectionMatrix(app.camera.combined);

        mapRenderer.renderBackground();

        app.batch.begin();
        playerRenderer.render(world.getPlayer(), this, delta);
        app.batch.end();

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
