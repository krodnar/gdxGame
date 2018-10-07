package com.mygdx.game.views.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ObjectMap;
import com.mygdx.game.Application;
import com.mygdx.game.entities.StatefulEntity;

/**
 * An animated view for the entity.
 *
 * @param <T> class of the entity
 */
public abstract class AnimatedEntityView<T extends StatefulEntity> extends StatefulEntityView<T> {

    protected ObjectMap<Integer, Animation<Sprite>> animations;

    private float frameDuration = 0.05f;
    private Animation.PlayMode playMode = Animation.PlayMode.LOOP;

    /**
     * Creates new AnimatedEntityView and initializes it.
     *
     * @param app    main app class
     * @param entity entity for initialization
     */
    public AnimatedEntityView(Application app, T entity) {
        super(app, entity);
    }

    @Override
    public void initialize(T entity) {
        super.initialize(entity);
        animations = buildAnimations(entity);
    }

    @Override
    protected Sprite getStateSprite(int state) {
        return animations.get(state).getKeyFrame(getStateTime());
    }

    /**
     * This method should return {@link Animation}s mapped to the states of the entity.
     *
     * @param entity entity to build animations for
     * @return map of animations
     */
    protected abstract ObjectMap<Integer, Animation<Sprite>> buildAnimations(T entity);

    /**
     * @return frame duration of animations
     */
    public float getFrameDuration() {
        return frameDuration;
    }

    /**
     * Sets frame duration of all animations for this entity.
     *
     * @param frameDuration new frame duration
     */
    public void setFrameDuration(float frameDuration) {
        this.frameDuration = frameDuration;

        for (Animation<Sprite> animation : animations.values()) {
            animation.setFrameDuration(frameDuration);
        }
    }

    /**
     * @return {@link Animation.PlayMode PlayMode} of animations for this entity
     */
    public Animation.PlayMode getPlayMode() {
        return playMode;
    }

    /**
     * Sets {@link Animation.PlayMode PlayMode} of all animations for this entity.
     *
     * @param playMode new play mode
     */
    public void setPlayMode(Animation.PlayMode playMode) {
        this.playMode = playMode;

        for (Animation<Sprite> animation : animations.values()) {
            animation.setPlayMode(playMode);
        }
    }
}
