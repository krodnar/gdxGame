package com.mygdx.game.views.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.Application;
import com.mygdx.game.entities.Player;
import com.mygdx.game.managers.Assets;
import com.mygdx.game.utils.AtlasUtils;

public class PlayerView extends ActiveEntityView<Player> {

    private TextureAtlas atlas;
    private TextureAtlas stoppedAtlas;

    public PlayerView(Application app, Player entity) {
        super(app, entity);
    }

    @Override
    public void preload() {
        super.preload();
        atlas = app.assets.get(Assets.characterRunning);
        stoppedAtlas = app.assets.get(Assets.characterStanding);
    }

    @Override
    protected void buildAnimations(Player entity) {
        AtlasUtils.AnimationParameters parameters = new AtlasUtils.AnimationParameters();
        parameters.frameDuration = 0.05f;
        parameters.playMode = Animation.PlayMode.LOOP;

        addDirectionAnimation(Player.STOPPED, AtlasUtils.getAtlasDirectionAnimation(stoppedAtlas, parameters));
        addDirectionAnimation(Player.MOVING, AtlasUtils.getAtlasDirectionAnimation(atlas, parameters));
    }

    @Override
    public void dispose() {
        app.assets.unload(Assets.characterRunning.fileName);
    }
}
