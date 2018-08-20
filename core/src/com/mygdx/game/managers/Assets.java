package com.mygdx.game.managers;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygdx.game.utils.R;

public class Assets extends AssetManager {

    public static final AssetDescriptor<TextureAtlas> characterRunning =
            new AssetDescriptor<TextureAtlas>(R.character.running_atlas, TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> characterStanding =
            new AssetDescriptor<TextureAtlas>(R.character.standing_atlas, TextureAtlas.class);

    public static final AssetDescriptor<Texture> texture =
            new AssetDescriptor<Texture>(R.images.texture, Texture.class);
    public static final AssetDescriptor<Texture> drake =
            new AssetDescriptor<Texture>(R.images.drake, Texture.class);

    public static final AssetDescriptor<TextureAtlas> skinDefaultAtlas =
            new AssetDescriptor<TextureAtlas>(R.ui.skin_default_atlas, TextureAtlas.class);

    public static final AssetDescriptor<TiledMap> map0 =
            new AssetDescriptor<TiledMap>(R.maps.map0, TiledMap.class);
    public static final AssetDescriptor<TiledMap> map1 =
            new AssetDescriptor<TiledMap>(R.maps.map1, TiledMap.class);

    public void initLoad() {
        load(characterRunning);
        load(characterStanding);

        load(texture);
        load(drake);

        load(skinDefaultAtlas);

        setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        load(map0);
        load(map1);
    }
}
