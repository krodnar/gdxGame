package com.mygdx.game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.contacts.WorldContactListener;
import com.mygdx.game.contacts.ContactManager;
import com.mygdx.game.managers.Assets;
import com.mygdx.game.entities.Player;
import com.mygdx.game.map.MapParser;

/**
 * A GameWorld contains all game data and logic, like a Box2D world or map, and updates it.
 */
public class GameWorld implements Disposable {

    private Application app;
    private Player player;
    private World world;
    private TiledMap map;

    /**
     * Creates new GameWorld with provided application.
     *
     * @param app main application class
     */
    public GameWorld(Application app) {
        this.app = app;

        world = new World(new Vector2(0, 0), false);
        map = app.assets.get(Assets.map0);
        player = new Player(app, world);

        MapParser.parseObjectsCollision(world, map, "collision");
        MapParser.parseWorldCollision(world, map, "world_collision");
        MapParser.parseCustomObjects(app, this, map, "objects");

        world.setContactListener(new WorldContactListener(new ContactManager()));
    }

    /**
     * Updates the world and every entity in it.
     *
     * @param delta time passed after the last frame
     */
    public void update(float delta) {
        world.step(1f / Application.APP_FPS, 6, 2);
        player.update(delta);
    }

    public World getWorld() {
        return world;
    }

    public Player getPlayer() {
        return player;
    }

    public TiledMap getMap() {
        return map;
    }

    @Override
    public void dispose() {
        world.dispose();
        map.dispose();
    }
}
