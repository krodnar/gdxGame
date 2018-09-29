package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ObjectMap;
import com.mygdx.game.assets.AtlasAnimation;
import com.mygdx.game.assets.AtlasRegions;

/**
 * A class that provides useful methods for assets.
 */
public class AssetsUtils {

    /**
     * Creates {@link AtlasRegions} which regions are binded to {@link Direction}s. Note that
     * region namings are taken from {@link #getDirectionRegions()}. Region prefix can be used
     * to differentiate regions in one atlas.
     *
     * @param atlas        atlas from which to get the textures
     * @param regionPrefix prefix for region namings
     * @return AtlasRegions with regions binded to Directions
     */
    public static AtlasRegions<Direction> createDirectionTexture(TextureAtlas atlas, String regionPrefix) {
        return new AtlasRegions<>(atlas, getDirectionRegions(), regionPrefix);
    }

    /**
     * Creates {@link AtlasAnimation} which regions are binded to {@link Direction}s. Note that
     * region namings are taken from {@link #getDirectionRegions()}. Region prefix can be used
     * to differentiate regions in one atlas.
     *
     * @param atlas        atlas from which to get the textures
     * @param regionPrefix prefix for region namings
     * @return AtlasRegions with regions binded to Directions
     */
    public static AtlasAnimation<Direction> createDirectionAnimation(TextureAtlas atlas, String regionPrefix) {
        return new AtlasAnimation<>(atlas, getDirectionRegions(), regionPrefix);
    }

    /**
     * Creates {@link AtlasAnimation} which regions are binded to {@link Direction}s with
     * specified {@link AtlasAnimation.AnimationParameters parameters}. Note that region namings
     * are taken from {@link #getDirectionRegions()}.
     *
     * @param atlas      atlas from which to get the textures
     * @param parameters parameters of the animation
     * @return AtlasRegions with regions binded to Directions
     * @see AtlasAnimation.AnimationParameters
     */
    public static AtlasAnimation<Direction> createDirectionAnimation(TextureAtlas atlas, AtlasAnimation.AnimationParameters parameters) {
        return new AtlasAnimation<>(atlas, getDirectionRegions(), parameters);
    }

    /**
     * Returns a map of {@link Direction}s and region namings. All region namings are identical
     * to direction naming but in lower case, in example {@link Direction#DOWN_RIGHT DOWN_RIGHT}
     * will be binded to "down_right".
     *
     * @return map of directions and region namings
     */
    private static ObjectMap<Direction, String> getDirectionRegions() {
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
}
