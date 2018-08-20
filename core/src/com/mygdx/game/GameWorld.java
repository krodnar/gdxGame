package com.mygdx.game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.contacts.WorldContactListener;
import com.mygdx.game.managers.ContactManager;
import com.mygdx.game.utils.R;
import com.mygdx.game.entities.Player;
import com.mygdx.game.map.MapParser;

public class GameWorld implements IGameWorld {

    private Application app;
    private Player player;
    private World world;
    private TiledMap map;

    public GameWorld(Application app) {
        this.app = app;

        world = new World(new Vector2(0, 0), false);
        map = app.assets.get(R.maps.map0);
        player = new Player(app, world);

        MapParser.parseObjectsCollision(world, map, "collision");
        MapParser.parseWorldCollision(world, map, "world_collision", MapParser.CollisionType.POLYLINES);
        MapParser.parseCustomObjects(app, this, map, "objects");

        world.setContactListener(new WorldContactListener(new ContactManager()));
    }

    public void update(float delta) {
        world.step(1f / Application.APP_FPS, 6, 2);
        player.update(delta);
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public TiledMap getMap() {
        return map;
    }

    @Override
    public void dispose() {
        world.dispose();
        map.dispose();
    }
}
