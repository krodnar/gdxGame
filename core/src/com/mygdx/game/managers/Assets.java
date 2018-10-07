package com.mygdx.game.managers;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

/**
 * A class that manages assets and provides the way of organizing and loading them.
 */
public class Assets implements Disposable {

    public static final AssetDescriptor<TextureAtlas> characterAtlas =  new AssetDescriptor<>("character/character.atlas", TextureAtlas.class);
    public static final AssetDescriptor<Texture> texture =              new AssetDescriptor<>("images/texture.png", Texture.class);
    public static final AssetDescriptor<Texture> drake =                new AssetDescriptor<>("images/drake.jpg", Texture.class);
    public static final AssetDescriptor<Skin> skinDefaultAtlas =        new AssetDescriptor<>("ui/skins/uiskin.json", Skin.class);
    public static final AssetDescriptor<TiledMap> map0 =                new AssetDescriptor<>("maps/map_v0.tmx", TiledMap.class);

    public final AssetManager manager;

    public Assets() {
        manager = new AssetManager();
    }

    /**
     * Initializes loading of all assets for the game. Note that this method only puts assets in a
     * queue and to keep loading them you should use {@link #update()}.
     */
    public void initLoad() {
        manager.load(characterAtlas);

        manager.load(texture);
        manager.load(drake);

        manager.load(skinDefaultAtlas);

        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load(map0);
    }

    /**
     * Loads a small part of queued assets.
     *
     * @return true if all queued assets are loaded, false otherwise
     */
    public boolean update() {
        return manager.update();
    }

    /**
     * Returns progress of loading of all assets in percents.
     *
     * @return progress in percents
     */
    public float getProgress() {
        return manager.getProgress();
    }

    /**
     * Returns an asset by its file name.
     *
     * @param filename the assets file name
     * @return the asset
     */
    public <T> T get(String filename) {
        return manager.get(filename);
    }

    /**
     * Returns an asset of specified type by its file name.
     *
     * @param filename the asset file name
     * @param type     the asset type
     * @return the asset
     */
    public <T> T get(String filename, Class<T> type) {
        return manager.get(filename, type);
    }

    /**
     * Returns an asset by its {@link AssetDescriptor descriptor}.
     *
     * @param assetDescriptor the asset descriptor
     * @return the asset
     */
    public <T> T get(AssetDescriptor<T> assetDescriptor) {
        return manager.get(assetDescriptor);
    }

    public void unload(AssetDescriptor assetDescriptor) {
        manager.unload(assetDescriptor.fileName);
    }

    @Override
    public void dispose() {
        manager.dispose();
    }
}
