package com.mygdx.game.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Application;

public abstract class StatefulEntity extends B2DEntity {

    private int state;
    private float stateTime;

    public StatefulEntity(Application app, World world) {
        super(app, world);
    }

    @Override
    public void update(float delta) {
        stateTime += delta;
    }

    public void setState(int state) {
        if (this.state != state) {
            this.state = state;
            stateTime = 0;
        }
    }

    public float getStateTime() {
        return stateTime;
    }

    public int getState() {
        return state;
    }
}
