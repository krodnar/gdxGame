package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * A class that provides useful methods for assets.
 */
public class AtlasUtils {

    /**
     * Creates a map of sprites binded to the keys from {@link TextureAtlas} based on passed in
     * keys and region namings.
     *
     * @param atlas         atlas from which to get the textures
     * @param regionNamings namings of regions and their keys
     * @param regionPrefix  prefix for region namings
     * @param <K>           class of a key
     * @return sprites map
     */
    public static <K> ObjectMap<K, Sprite> getAtlasRegions(TextureAtlas atlas, ObjectMap<K, String> regionNamings, String regionPrefix) {
        ObjectMap<K, Sprite> atlasRegions = new ObjectMap<>();

        for (ObjectMap.Entry<K, String> entry : regionNamings) {
            K key = entry.key;
            String regionNaming = regionPrefix + entry.value;

            TextureRegion textureRegion = atlas.findRegion(regionNaming);
            Sprite sprite = new Sprite(textureRegion);
            atlasRegions.put(key, sprite);
        }

        return atlasRegions;
    }

    /**
     * @see #getAtlasRegions(TextureAtlas, ObjectMap, String)
     */
    public static <K> ObjectMap<K, Sprite> getAtlasRegions(TextureAtlas atlas, ObjectMap<K, String> regionNamings) {
        return getAtlasRegions(atlas, regionNamings, "");
    }

    /**
     * Creates a map of {@link Animation animations} binded to the key from {@link TextureAtlas}
     * based on passed in keys and region namings.
     *
     * @param atlas         atlas from which to get the textures
     * @param regionNamings namings of regions and their keys
     * @param parameters    parameters for getting sprites and creating an animation from atlas
     * @param <K>           class of a key
     * @return animations map
     */
    public static <K> ObjectMap<K, Animation<Sprite>> getAtlasAnimations(TextureAtlas atlas, ObjectMap<K, String> regionNamings, AnimationParameters parameters) {
        ObjectMap<K, Animation<Sprite>> animations = new ObjectMap<>();

        for (ObjectMap.Entry<K, String> entry : regionNamings) {
            K key = entry.key;
            String regionNaming = parameters.regionPrefix + entry.value;

            Array<Sprite> sprites = new Array<>();
            Array<TextureAtlas.AtlasRegion> regions = atlas.findRegions(regionNaming);

            for (TextureAtlas.AtlasRegion region : regions) {
                sprites.add(new Sprite(region));
            }

            Animation<Sprite> animation = new Animation<>(parameters.frameDuration, sprites, parameters.playMode);
            animations.put(key, animation);
        }

        return animations;
    }

    /**
     * @see #getAtlasAnimations(TextureAtlas, ObjectMap, AnimationParameters)
     */
    public static <K> ObjectMap<K, Animation<Sprite>> getAtlasAnimations(TextureAtlas atlas, ObjectMap<K, String> regionNamings) {
        return getAtlasAnimations(atlas, regionNamings, new AnimationParameters());
    }

    public static ObjectMap<Direction, Animation<Sprite>> getAtlasDirectionAnimation(TextureAtlas atlas, AnimationParameters parameters) {
        return getAtlasAnimations(atlas, getDirectionRegionNamings(), parameters);
    }

    /**
     * Returns a map of {@link Direction}s and region namings. All region namings are identical
     * to direction naming in lower case, in example {@link Direction#DOWN_RIGHT DOWN_RIGHT}
     * will be binded to "down_right".
     *
     * @return map of directions and region namings
     */
    private static ObjectMap<Direction, String> getDirectionRegionNamings() {
        ObjectMap<Direction, String> regionNamings = new ObjectMap<>();

        regionNamings.put(Direction.UP, "up");
        regionNamings.put(Direction.UP_RIGHT, "up_right");
        regionNamings.put(Direction.RIGHT, "right");
        regionNamings.put(Direction.DOWN_RIGHT, "down_right");
        regionNamings.put(Direction.DOWN, "down");
        regionNamings.put(Direction.DOWN_LEFT, "down_left");
        regionNamings.put(Direction.LEFT, "left");
        regionNamings.put(Direction.UP_LEFT, "up_left");
        regionNamings.put(Direction.UNDEFINED, "down_right");

        return regionNamings;
    }

    /**
     * Class for parameters of {@link Animation} from {@link TextureAtlas} like region prefix,
     * frame duration and play mode.
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
