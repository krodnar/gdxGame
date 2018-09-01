package com.mygdx.game.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Application;

public abstract class StatefulEntity extends B2DEntity {

    protected int state;

    public StatefulEntity(Application app, World world) {
        super(app, world);
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }
}
