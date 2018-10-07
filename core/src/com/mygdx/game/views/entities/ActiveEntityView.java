package com.mygdx.game.views.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.mygdx.game.Application;
import com.mygdx.game.entities.ActiveEntity;
import com.mygdx.game.utils.Direction;

/**
 * A view for {@link ActiveEntity active entity} that has states and directions.
 *
 * @param <T> class of the entity
 */
public abstract class ActiveEntityView<T extends ActiveEntity> extends StatefulEntityView<T> {

    private IntMap<Animation<Sprite>> animations = new IntMap<>();

    /**
     * Creates a new ActiveEntityView and initializes it.
     *
     * @param app    main app class
     * @param entity entity for this view
     */
    public ActiveEntityView(Application app, T entity) {
        super(app, entity);
    }

    @Override
    public void initialize(T entity) {
        super.initialize(entity);
        buildAnimations(entity);
    }

    @Override
    protected Sprite getStateSprite(int state) {
        return animations.get(getMapKey(state, getDirection())).getKeyFrame(getStateTime());
    }

    /**
     * Adds a directional animation for specified state.
     *
     * @param state         state of the entity for animation
     * @param animationsMap directional animation
     */
    public void addDirectionAnimation(int state, ObjectMap<Direction, Animation<Sprite>> animationsMap) {
        for (ObjectMap.Entry<Direction, Animation<Sprite>> entry : animationsMap) {
            Direction direction = entry.key;
            Animation<Sprite> animation = entry.value;

            int key = getMapKey(state, direction);
            animations.put(key, animation);
        }
    }

    /**
     * Builds required animations for this view.
     * <p>
     * All animations should be added by using the {@link #addDirectionAnimation(int, ObjectMap)}
     * method.
     *
     * @param entity entity for which animations are for
     */
    protected abstract void buildAnimations(T entity);

    /**
     * @return direction of the entity
     */
    protected Direction getDirection() {
        return entity.getDirection();
    }

    /**
     * Returns an integer based on state and direction so that there is no collisions for any
     * combination of them.
     * <p>
     * In example, if there are two states and four directions then keys would be:
     * <pre>
     * State | Dir-n |          Key
     * ------+-------+--------------
     *      0|      0| 0 * 4 + 0 = 0
     *      0|      1| 0 * 4 + 1 = 1
     *      0|      2| 0 * 4 + 2 = 2
     *      0|      3| 0 * 4 + 3 = 3
     *      1|      0| 1 * 4 + 0 = 4
     *      1|      1| 1 * 4 + 1 = 5
     *      1|      2| 1 * 4 + 2 = 6
     *      1|      3| 1 * 4 + 3 = 7
     * </pre>
     *
     * @param state     state of the entity
     * @param direction direction of the entity
     * @return map key
     */
    private int getMapKey(int state, Direction direction) {
        return state * Direction.values().length + direction.getIndex();
    }
}
