package com.mygdx.game.views.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ObjectMap;
import com.mygdx.game.Application;
import com.mygdx.game.entities.StatefulEntity;

/**
 * A stateful view for entity with static sprites.
 *
 * @param <T> class of the entity
 */
public abstract class StaticEntityView<T extends StatefulEntity> extends StatefulEntityView<T> {

    protected ObjectMap<Integer, Sprite> sprites;

    /**
     * Creates new StaticEntityView and initializes it.
     *
     * @param app    main app class
     * @param entity entity for initialization
     */
    public StaticEntityView(Application app, T entity) {
        super(app, entity);
    }

    @Override
    public void initialize(T entity) {
        super.initialize(entity);
        sprites = buildSprites(entity);
    }

    /**
     * This method should return {@link Sprite sprites} mapped to the states of the entity.
     *
     * @param entity entity to build sprites for
     * @return map of sprites
     */
    protected abstract ObjectMap<Integer, Sprite> buildSprites(T entity);

    @Override
    protected Sprite getStateSprite(int state) {
        return sprites.get(state);
    }
}
