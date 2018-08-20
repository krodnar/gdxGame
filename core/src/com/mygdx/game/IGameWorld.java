package com.mygdx.game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.entities.Player;

public interface IGameWorld extends Disposable {

    void update(float delta);

    Player getPlayer();

    TiledMap getMap();

    World getWorld();
}
