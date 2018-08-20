package com.mygdx.game.render;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Application;
import com.mygdx.game.entities.Player;
import com.mygdx.game.utils.R;

import static com.mygdx.game.utils.Constants.PPM;

public class PlayerRenderer implements EntityRenderer<Player> {

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

    public PlayerRenderer(Application app) {
        this.app = app;

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
    }

    @Override
    public void render(Player player, IGameRenderer renderer, float delta) {
        stateTime += delta;

        animationUpdate(player, delta);

        float x = player.getPosition().x * PPM;
        float y = player.getPosition().y * PPM;

        TextureRegion frame = currentAnimation.getKeyFrame(stateTime, false);
        app.batch.draw(frame, x - 12, y - 16);
    }

    @Override
    public void dispose() {
        atlas.dispose();
    }

    private void animationUpdate(Player player, float delta) {
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
