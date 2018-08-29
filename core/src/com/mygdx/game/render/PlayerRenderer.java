package com.mygdx.game.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Application;
import com.mygdx.game.entities.Player;
import com.mygdx.game.utils.R;

import static com.mygdx.game.utils.Constants.PPM;

public class PlayerRenderer implements TiledEntityRenderer {

    private Player player;

    private float frameDuration = 0.03f;
    private float stateTime = 0;
    private Application app;
    private TextureAtlas atlas;

    private Animation<TextureRegion> runningUp;
    private Animation<TextureRegion> runningUpLeft;
    private Animation<TextureRegion> runningLeft;
    private Animation<TextureRegion> runningDownLeft;
    private Animation<TextureRegion> runningDown;
    private Animation<TextureRegion> runningDownRight;
    private Animation<TextureRegion> runningRight;
    private Animation<TextureRegion> runningUpRight;
    private Animation<TextureRegion> currentAnimation;
    private TextureRegion currentFrame;

    public PlayerRenderer(Application app, Player player) {
        this.app = app;
        this.player = player;

        atlas = app.assets.get(R.character.running_atlas);

        runningUp = getAnimation(atlas, "up");
        runningUpLeft = getAnimation(atlas, "up left");
        runningLeft = getAnimation(atlas, "left");
        runningDownLeft = getAnimation(atlas, "down left");
        runningDown = getAnimation(atlas, "down");
        runningDownRight = getAnimation(atlas, "down right");
        runningRight = getAnimation(atlas, "right");
        runningUpRight = getAnimation(atlas, "up right");

        currentAnimation = runningUpLeft;
        currentFrame = currentAnimation.getKeyFrame(stateTime);
    }

    @Override
    public void render(IGameRenderer renderer) {
        stateTime += Gdx.graphics.getDeltaTime();

        animationUpdate(player);

        float x = player.getPosition().x * PPM;
        float y = player.getPosition().y * PPM;

        currentFrame = currentAnimation.getKeyFrame(stateTime);
        app.batch.draw(currentFrame, x - 12, y - 4);
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
}
