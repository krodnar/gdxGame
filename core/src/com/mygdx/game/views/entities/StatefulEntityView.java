package com.mygdx.game.views.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Application;
import com.mygdx.game.entities.StatefulEntity;

/**
 * Basic implementation of stateful view that changes its entity sprite based on current state
 * of the entity.
 *
 * @param <T>  class of the entity
 */
public abstract class StatefulEntityView<T extends StatefulEntity> extends AbstractEntityView<T> {

    /**
     * Creates new StatefulEntityView for specific entity.
     *
     * @param app    main app class
     * @param entity entity for initialization
     */
    public StatefulEntityView(Application app, T entity) {
        super(app, entity);
    }

    /**
     * @return current state of the entity
     */
    protected int getState() {
        return entity.getState();
    }

    /**
     * @return current state time of the entity
     */
    protected float getStateTime() {
        return entity.getStateTime();
    }

    /**
     * Returns sprite of the entity based on current state of the entity.
     *
     * @param state current state of the entity
     * @return sprite of the entity
     */
    protected abstract Sprite getStateSprite(int state);

    @Override
    protected Sprite getSprite() {
        return getStateSprite(getState());
    }
}
