package com.mygdx.game.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Application;
import com.mygdx.game.entities.Player;
import com.mygdx.game.managers.Assets;
import com.mygdx.game.assets.AtlasRegions;
import com.mygdx.game.assets.AtlasAnimation;
import com.mygdx.game.utils.AssetsUtils;
import com.mygdx.game.utils.Direction;

import static com.mygdx.game.utils.Constants.PPM;

public class PlayerRenderer implements TiledEntityRenderer {

    public static final float DEFAULT_FRAME_DURATION = 0.05f;

    private Player player;

    private float frameDuration = DEFAULT_FRAME_DURATION;
    private float stateTime = 0;
    private Application app;
    private TextureAtlas atlas;

    private AtlasAnimation<Direction> runningAnimation;
    private AtlasRegions<Direction> standingFrame;

    private Animation<TextureRegion> currentAnimation;
    private TextureRegion currentFrame;

    public PlayerRenderer(Application app, Player player) {
        this.app = app;
        this.player = player;

        this.atlas = app.assets.get(Assets.characterAtlas);
        standingFrame = AssetsUtils.createDirectionTexture(atlas, "standing_");

        AtlasAnimation.AnimationParameters parameters = new AtlasAnimation.AnimationParameters();
        parameters.regionPrefix = "running_";
        parameters.frameDuration = frameDuration;
        parameters.playMode = Animation.PlayMode.LOOP;
        runningAnimation = AssetsUtils.createDirectionAnimation(atlas, parameters);

        currentAnimation = runningAnimation.getAnimation(Direction.DOWN_RIGHT);
        currentFrame = standingFrame.getRegion(Direction.DOWN);
    }

    @Override
    public void render(GameRenderer renderer) {
        stateTime += Gdx.graphics.getDeltaTime();

        animationUpdate(player);

        float x = player.getPosition().x * PPM;
        float y = player.getPosition().y * PPM;

        currentFrame = getCurrentFrame(player, stateTime);
        float width = currentFrame.getRegionWidth();
        float height = currentFrame.getRegionHeight();

        app.batch.draw(currentFrame, x - width / 2, y - height / 2);
    }

    private TextureRegion getCurrentFrame(Player player, float stateTime) {
        if (player.getState() == Player.MOVING) {
            animationUpdate(player);
            return currentAnimation.getKeyFrame(stateTime, false);
        } else {
            return getStandingFrame(player);
        }
    }

    @Override
    public Vector2 getEntityBottom() {
        Vector2 bottom = new Vector2(player.getPosition());
        TextureRegion frame = currentAnimation.getKeyFrame(stateTime);
        bottom.set(player.getPosition().x, player.getPosition().y - frame.getRegionHeight());
        return bottom;
    }

    @Override
    public void dispose() {
        atlas.dispose();
    }

    private TextureRegion getStandingFrame(Player player) {
        return standingFrame.getRegion(player.getDirection());
    }

    private void animationUpdate(Player player) {
        currentAnimation = runningAnimation.getAnimation(player.getDirection());
    }

    private TextureRegion getTexture(TextureAtlas standingAtlas, String regionNaming) {
        return standingAtlas.findRegion(regionNaming);
    }

    public float getFrameDuration() {
        return frameDuration;
    }

    public void setFrameDuration(float frameDuration) {
        this.frameDuration = frameDuration;
    }
}
