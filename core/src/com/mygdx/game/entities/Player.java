package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Application;
import com.mygdx.game.IGameRenderer;
import com.mygdx.game.utils.R;
import com.mygdx.game.utils.BodyBuilder;

import static com.mygdx.game.entities.Player.Direction.*;
import static com.mygdx.game.utils.Constants.PPM;

public class Player extends AbstractEntity {

    private Body body;
    private Texture texture;
    private Sprite sprite;

    private float stateTime = 0;

    private Animation<TextureRegion> runningUp;
    private Animation<TextureRegion> runningUpLeft;
    private Animation<TextureRegion> runningLeft;
    private Animation<TextureRegion> runningDownLeft;
    private Animation<TextureRegion> runningDown;
    private Animation<TextureRegion> runningDownRight;
    private Animation<TextureRegion> runningRight;
    private Animation<TextureRegion> runningUpRight;
    private Animation<TextureRegion> currentAnimation;

    private float angle;
    private Direction direction;

    private Direction[][] dirMatrix = new Direction[][]{
            {UP_LEFT, UP, UP_RIGHT},
            {LEFT, STOP, RIGHT},
            {DOWN_LEFT, DOWN, DOWN_RIGHT}
    };

    public enum Direction {
        UP,
        UP_RIGHT,
        RIGHT,
        DOWN_RIGHT,
        DOWN,
        DOWN_LEFT,
        LEFT,
        UP_LEFT,
        STOP
    }

    public Player(Application application, World world) {
        super(application, world);
        createBody();

        texture = app.assets.get(R.images.texture);
        sprite = new Sprite(texture);

        float frameDuration = 0.03f;
        TextureAtlas atlas = app.assets.get(R.character.running_atlas);

        runningUp = new Animation<TextureRegion>(frameDuration, atlas.findRegions("up"), Animation.PlayMode.LOOP);
        runningUpLeft = new Animation<TextureRegion>(frameDuration, atlas.findRegions("up left"), Animation.PlayMode.LOOP);
        runningLeft = new Animation<TextureRegion>(frameDuration, atlas.findRegions("left"), Animation.PlayMode.LOOP);
        runningDownLeft = new Animation<TextureRegion>(frameDuration, atlas.findRegions("down left"), Animation.PlayMode.LOOP);
        runningDown = new Animation<TextureRegion>(frameDuration, atlas.findRegions("down"), Animation.PlayMode.LOOP);
        runningDownRight = new Animation<TextureRegion>(frameDuration, atlas.findRegions("down right"), Animation.PlayMode.LOOP);
        runningRight = new Animation<TextureRegion>(frameDuration, atlas.findRegions("right"), Animation.PlayMode.LOOP);
        runningUpRight = new Animation<TextureRegion>(frameDuration, atlas.findRegions("up right"), Animation.PlayMode.LOOP);

        currentAnimation = runningUpLeft;
    }

    public void setPosition(Vector2 position) {
        body.setTransform(position, body.getAngle());
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    @Override
    public void update(float delta) {
        angleUpdate(delta);
        directionUpdate(delta);
        positionUpdate(delta);
        animationUpdate(delta);
    }

    @Override
    public void render(IGameRenderer renderer, float delta) {
        spriteUpdate(delta);
        // sprite.draw(app.batch);


        float x = this.getPosition().x * PPM;
        float y = this.getPosition().y * PPM;

        TextureRegion frame = currentAnimation.getKeyFrame(stateTime, false);
        app.batch.draw(frame, x - 12, y - 16);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    private void createBody() {
        BodyBuilder builder = new BodyBuilder(world);
        builder
                .setBodyType(BodyDef.BodyType.DynamicBody)
                .setPosition(0, 0)
                .setFixedRotation(true)
                .setBoxShape(8f, 16f);

        body = builder.build();
        body.setUserData(this);
    }

    private void angleUpdate(float delta) {
        Vector3 mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        mousePosition = app.camera.unproject(mousePosition);

        float x = this.getPosition().x * PPM;
        float y = this.getPosition().y * PPM;

        angle = MathUtils.atan2(mousePosition.y - y, mousePosition.x - x);
    }

    private void animationUpdate(float delta) {
        stateTime += delta;

        switch (direction) {

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
            case STOP:
                break;
        }
    }

    private void spriteUpdate(float delta) {
        float x = this.getPosition().x * PPM;
        float y = this.getPosition().y * PPM;

        sprite.setPosition(x - (texture.getWidth() / 2f), y - (texture.getHeight() / 2f));
        sprite.setRotation((angle + MathUtils.PI / 2) * MathUtils.radDeg);
    }

    private void directionUpdate(float delta) {
        int i = 1;
        int j = 1;

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            i += 1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            i -= 1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            j -= 1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            j += 1;
        }

        direction = dirMatrix[j][i];
    }

    private void positionUpdate(float delta) {
        int verticalForce = 0;
        int horizontalForce = 0;

        switch (direction) {

            case UP:
                verticalForce += 1;
                break;
            case UP_RIGHT:
                horizontalForce += 1;
                verticalForce += 1;
                break;
            case RIGHT:
                horizontalForce += 1;
                break;
            case DOWN_RIGHT:
                verticalForce -= 1;
                horizontalForce += 1;
                break;
            case DOWN:
                verticalForce -= 1;
                break;
            case DOWN_LEFT:
                horizontalForce -= 1;
                verticalForce -= 1;
                break;
            case LEFT:
                horizontalForce -= 1;
                break;
            case UP_LEFT:
                horizontalForce -= 1;
                verticalForce += 1;
                break;
            case STOP:
                break;
        }

        body.setLinearVelocity(horizontalForce * 5, verticalForce * 5);
    }
}
