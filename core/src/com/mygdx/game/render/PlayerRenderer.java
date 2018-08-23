package com.mygdx.game.render;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Application;
import com.mygdx.game.entities.Player;
import com.mygdx.game.utils.R;

import static com.mygdx.game.utils.Constants.PPM;

public class PlayerRenderer implements EntityRenderer<Player> {

    private float frameDuration = 0.05f;
    private float stateTime = 0;
    private Application app;
    private TextureAtlas runningAtlas;
    private TextureAtlas standingAtlas;

    private Animation<TextureRegion> runningUp;
    private Animation<TextureRegion> runningUpLeft;
    private Animation<TextureRegion> runningLeft;
    private Animation<TextureRegion> runningDownLeft;
    private Animation<TextureRegion> runningDown;
    private Animation<TextureRegion> runningDownRight;
    private Animation<TextureRegion> runningRight;
    private Animation<TextureRegion> runningUpRight;

    private TextureRegion standingUp;
    private TextureRegion standingUpLeft;
    private TextureRegion standingLeft;
    private TextureRegion standingDownLeft;
    private TextureRegion standingDown;
    private TextureRegion standingDownRight;
    private TextureRegion standingRight;
    private TextureRegion standingUpRight;

    private Animation<TextureRegion> currentAnimation;
    private TextureRegion currentFrame;

    public PlayerRenderer(Application app) {
        this.app = app;

        runningAtlas = app.assets.get(R.character.running_atlas);
        standingAtlas = app.assets.get(R.character.standing_atlas);

        initAnimations();
    }

    @Override
    public void render(Player player, IGameRenderer renderer, float delta) {
        stateTime += delta;

        float x = player.getPosition().x * PPM;
        float y = player.getPosition().y * PPM;

        currentFrame = getCurrentFrame(player, stateTime);
        float width = currentFrame.getRegionWidth();
        float height = currentFrame.getRegionHeight();

        app.batch.draw(currentFrame, x - width / 2, y - height / 2);
    }

    private TextureRegion getCurrentFrame(Player player, float stateTime) {
        if (player.isMoving()) {
            animationUpdate(player);
            return currentAnimation.getKeyFrame(stateTime, false);
        } else {
            return getStandingFrame(player);
        }
    }

    @Override
    public void dispose() {
        runningAtlas.dispose();
        standingAtlas.dispose();
    }

    private TextureRegion getStandingFrame(Player player) {
        switch (player.getDirection()) {

            case UP:
                return standingUp;
            case UP_RIGHT:
                return standingUpRight;
            case RIGHT:
                return standingRight;
            case DOWN_RIGHT:
                return standingDownRight;
            case DOWN:
                return standingDown;
            case DOWN_LEFT:
                return standingDownLeft;
            case LEFT:
                return standingLeft;
            case UP_LEFT:
                return standingUpLeft;
            case UNDEFINED:
                return standingDown;
            default:
                return standingDown;
        }
    }

    private void animationUpdate(Player player) {
        switch (player.getDirection()) {

            case UP:
                currentAnimation = runningUp;
                break;
            case UP_RIGHT:
                currentAnimation = runningUpRight;
                break;
            case RIGHT:
                currentAnimation = runningRight;
                break;
            case DOWN_RIGHT:
                currentAnimation = runningDownRight;
                break;
            case DOWN:
                currentAnimation = runningDown;
                break;
            case DOWN_LEFT:
                currentAnimation = runningDownLeft;
                break;
            case LEFT:
                currentAnimation = runningLeft;
                break;
            case UP_LEFT:
                currentAnimation = runningUpLeft;
                break;
            case UNDEFINED:
                break;
        }
    }

    private Animation<TextureRegion> getAnimation(TextureAtlas atlas, String regionNaming) {
        return new Animation<TextureRegion>(frameDuration, atlas.findRegions(regionNaming), Animation.PlayMode.LOOP);
    }

    private TextureRegion getTexture(TextureAtlas standingAtlas, String regionNaming) {
        return standingAtlas.findRegion(regionNaming);
    }

    private void initAnimations() {
        runningUp = getAnimation(runningAtlas, "up");
        runningUpLeft = getAnimation(runningAtlas, "up left");
        runningLeft = getAnimation(runningAtlas, "left");
        runningDownLeft = getAnimation(runningAtlas, "down left");
        runningDown = getAnimation(runningAtlas, "down");
        runningDownRight = getAnimation(runningAtlas, "down right");
        runningRight = getAnimation(runningAtlas, "right");
        runningUpRight = getAnimation(runningAtlas, "up right");

        standingUp = getTexture(standingAtlas, "up");
        standingUpLeft = getTexture(standingAtlas, "up left");
        standingLeft = getTexture(standingAtlas, "left");
        standingDownLeft = getTexture(standingAtlas, "down left");
        standingDown = getTexture(standingAtlas, "down");
        standingDownRight = getTexture(standingAtlas, "down right");
        standingRight = getTexture(standingAtlas, "right");
        standingUpRight = getTexture(standingAtlas, "up right");

        currentAnimation = runningUpLeft;
    }

    public float getFrameDuration() {
        return frameDuration;
    }

    public void setFrameDuration(float frameDuration) {
        this.frameDuration = frameDuration;
    }
}
