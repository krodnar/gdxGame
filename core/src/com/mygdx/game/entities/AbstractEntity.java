package com.mygdx.game.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Application;

public abstract class AbstractEntity implements Entity {

    protected Application app;
    protected World world;

    public AbstractEntity(Application app, World world) {
        this.app = app;
        this.world = world;
    }
}
