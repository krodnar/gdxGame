package com.mygdx.game.views.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.Application;
import com.mygdx.game.entities.Player;
import com.mygdx.game.managers.Assets;
import com.mygdx.game.utils.AtlasUtils;

public class PlayerView extends ActiveEntityView<Player> {

    private TextureAtlas atlas;

    public PlayerView(Application app, Player entity) {
        super(app, entity);
    }

    @Override
    public void preload() {
        super.preload();
        atlas = app.assets.get(Assets.characterAtlas);
    }

    @Override
    public void initialize(Player entity) {
        super.initialize(entity);
        setYOffset(10);
    }

    @Override
    protected void buildAnimations(Player entity) {
        AtlasUtils.AnimationParameters parameters = new AtlasUtils.AnimationParameters();
        parameters.frameDuration = 0.05f;
        parameters.playMode = Animation.PlayMode.LOOP;

        parameters.regionPrefix = "standing_";
        addDirectionAnimation(Player.STOPPED, AtlasUtils.getAtlasDirectionAnimation(atlas, parameters));
        parameters.regionPrefix = "running_";
        addDirectionAnimation(Player.MOVING, AtlasUtils.getAtlasDirectionAnimation(atlas, parameters));
    }

    @Override
    public void dispose() {
        app.assets.unload(Assets.characterAtlas);
    }
}
