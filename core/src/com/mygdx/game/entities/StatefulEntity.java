package com.mygdx.game.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Application;

/**
 * An entity that can have multiple states.
 */
public abstract class StatefulEntity extends B2DEntity {

    private int state;
    private float stateTime;

    /**
     * Creates new StatefulEntity.
     *
     * @param app   main app class
     * @param world world to create entity in
     */
    public StatefulEntity(Application app, World world) {
        super(app, world);
    }

    @Override
    public void update(float delta) {
        stateTime += delta;
    }

    /**
     * Sets current entity state.
     *
     * @param state entity state
     */
    public void setState(int state) {
        if (this.state != state) {
            this.state = state;
            stateTime = 0;
        }
    }

    public float getStateTime() {
        return stateTime;
    }

    /**
     * Gets entity state.
     *
     * @return entity state
     */
    public int getState() {
        return state;
    }
}
