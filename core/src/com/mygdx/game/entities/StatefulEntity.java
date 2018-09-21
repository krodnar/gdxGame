package com.mygdx.game.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Application;

/**
 * An entity that can have multiple states.
 */
public abstract class StatefulEntity extends B2DEntity {

    protected int state;

    /**
     * Creates new StatefulEntity.
     *
     * @param app   main app class
     * @param world world to create entity in
     */
    public StatefulEntity(Application app, World world) {
        super(app, world);
    }

    /**
     * Sets current entity state.
     *
     * @param state entity state
     */
    public void setState(int state) {
        this.state = state;
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
