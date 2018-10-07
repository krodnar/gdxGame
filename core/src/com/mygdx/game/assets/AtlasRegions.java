package com.mygdx.game.assets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * AtlasRegions is a helper class for textures retrieved from {@link TextureAtlas}.
 * <p>
 * It creates {@link TextureRegion}s from passed in {@link TextureAtlas} based on given namings
 * mapped to specified key. This way {@link TextureAtlas#findRegion(String)} is called only once
 * and its results are stored and retrieved in convenient way.
 *
 * @param <K> class of a key for regions
 */
public class AtlasRegions<K> {

    private final ObjectMap<K, String> regionNamings;
    private final ObjectMap<K, TextureRegion> textureRegions = new ObjectMap<>();

    private String regionPrefix = "";

    /**
     * Creates new AtlasRegions with specified region namings.
     *
     * @param atlas         atlas from which to get texture regions
     * @param regionNamings names of regions
     */
    public AtlasRegions(TextureAtlas atlas, ObjectMap<K, String> regionNamings) {
        this.regionNamings = regionNamings;

        buildRegions(atlas);
    }

    /**
     * Creates new AtlasRegions with specified region namings with a prefix.
     *
     * @param atlas         atlas from which to get texture regions
     * @param regionNamings names of regions
     * @param regionPrefix  prefix for a region namings
     */
    public AtlasRegions(TextureAtlas atlas, ObjectMap<K, String> regionNamings, String regionPrefix) {
        this.regionNamings = regionNamings;
        this.regionPrefix = regionPrefix;

        buildRegions(atlas);
    }

    private void buildRegions(TextureAtlas atlas) {
        for (ObjectMap.Entry<K, String> entry : regionNamings) {
            K key = entry.key;
            String regionNaming = regionPrefix + entry.value;

            TextureRegion textureRegion = atlas.findRegion(regionNaming);
            textureRegions.put(key, textureRegion);
        }
    }

    /**
     * Returns {@link TextureRegion} that was mapped to a specified key.
     *
     * @param key key to which desired region was mapped
     * @return {@link TextureRegion} for the specified key
     */
    public TextureRegion getRegion(K key) {
        return textureRegions.get(key);
    }

    /**
     * Sets new {@link TextureRegion} for a specified key.
     *
     * @param key           key to which texture will be mapped
     * @param textureRegion texture to be mapped to the key
     */
    public void setRegion(K key, TextureRegion textureRegion) {
        textureRegions.put(key, textureRegion);
    }
}
