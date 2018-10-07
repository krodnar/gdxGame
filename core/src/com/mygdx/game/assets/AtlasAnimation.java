package com.mygdx.game.assets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ObjectMap;
import com.mygdx.game.utils.Constants;

/**
 * AtlasAnimation is a helper class for {@link Animation}s based on a {@link TextureAtlas}.
 * <p>
 * It creates multiple animations based on a given region namings and maps them to a passed in keys.
 * This way slow {@link TextureAtlas#findRegions(String)} is called once for each animation.
 *
 * @param <K> class of a key for animations
 */
public class AtlasAnimation<K> {

    private final ObjectMap<K, String> regionNamings;
    private final ObjectMap<K, Animation<TextureRegion>> animations = new ObjectMap<>();

    private float frameDuration = Constants.DEFAULT_FRAME_DURATION;
    private String regionPrefix = "";
    private Animation.PlayMode playMode = Animation.PlayMode.LOOP;

    /**
     * Creates new AtlasAnimation using regions from passed in atlas based on region namings.
     *
     * @param atlas         atlas from which to get texture regions for animations
     * @param regionNamings region namings for every animation
     */
    public AtlasAnimation(TextureAtlas atlas, ObjectMap<K, String> regionNamings) {
        this.regionNamings = regionNamings;

        buildAnimations(atlas);
    }

    /**
     * Creates new AtlasAnimation using regions from passed in atlas based on region namings with
     * a prefix.
     *
     * @param atlas         atlas from which to get texture regions for animations
     * @param regionNamings region namings for every animation
     * @param regionPrefix  prefix for region namings
     */
    public AtlasAnimation(TextureAtlas atlas, ObjectMap<K, String> regionNamings, String regionPrefix) {
        this.regionNamings = regionNamings;
        this.regionPrefix = regionPrefix;

        buildAnimations(atlas);
    }

    /**
     * Creates new AtlasAnimation with specified {@link AtlasAnimation.AnimationParameters
     * animation parameters}.
     *
     * @param atlas         atlas from which to get texture regions for animations
     * @param regionNamings region namings for every animation
     * @param parameters    parameters for animations
     */
    public AtlasAnimation(TextureAtlas atlas, ObjectMap<K, String> regionNamings, AnimationParameters parameters) {
        this.regionNamings = regionNamings;
        this.frameDuration = parameters.frameDuration;
        this.regionPrefix = parameters.regionPrefix;
        this.playMode = parameters.playMode;

        buildAnimations(atlas);
    }

    private void buildAnimations(TextureAtlas atlas) {

        for (ObjectMap.Entry<K, String> entry : regionNamings) {
            K direction = entry.key;
            String regionNaming = regionPrefix + entry.value;

            Animation<TextureRegion> animation = new Animation<TextureRegion>(frameDuration, atlas.findRegions(regionNaming), playMode);
            setAnimation(direction, animation);
        }
    }

    /**
     * Returns animation for the specified key.
     *
     * @param key key to which desired animation was mapped
     * @return animation for the specified key or null if there are none
     */
    public Animation<TextureRegion> getAnimation(K key) {
        return animations.get(key);
    }

    public void setAnimation(K key, Animation<TextureRegion> animation) {
        animations.put(key, animation);
    }

    /**
     * @return frame duration of animations
     */
    public float getFrameDuration() {
        return frameDuration;
    }

    /**
     * Sets frame duration for every animation.
     *
     * @param frameDuration frame duration in seconds
     */
    public void setFrameDuration(float frameDuration) {
        this.frameDuration = frameDuration;
        for (Animation<TextureRegion> animation : animations.values()) {
            animation.setFrameDuration(frameDuration);
        }
    }

    /**
     * @return play mode of animations
     */
    public Animation.PlayMode getPlayMode() {
        return playMode;
    }

    /**
     * Sets play mode for every animation
     *
     * @param playMode {@link Animation.PlayMode play mode} for animation
     */
    public void setPlayMode(Animation.PlayMode playMode) {
        this.playMode = playMode;
        for (Animation<TextureRegion> animation : animations.values()) {
            animation.setPlayMode(playMode);
        }
    }

    /**
     * Class for parameters of AtlasAnimation like region prefix, frame duration and play mode.
     */
    public static class AnimationParameters {

        public String regionPrefix = "";
        public float frameDuration = Constants.DEFAULT_FRAME_DURATION;
        public Animation.PlayMode playMode = Animation.PlayMode.LOOP;

        public AnimationParameters() {
        }

        public AnimationParameters(String regionPrefix) {
            this.regionPrefix = regionPrefix;
        }

        public AnimationParameters(String regionPrefix, float frameDuration) {
            this.regionPrefix = regionPrefix;
            this.frameDuration = frameDuration;
        }

        public AnimationParameters(String regionPrefix, Animation.PlayMode playMode) {
            this.regionPrefix = regionPrefix;
            this.playMode = playMode;
        }

        public AnimationParameters(String regionPrefix, float frameDuration, Animation.PlayMode playMode) {
            this.regionPrefix = regionPrefix;
            this.frameDuration = frameDuration;
            this.playMode = playMode;
        }
    }
}
