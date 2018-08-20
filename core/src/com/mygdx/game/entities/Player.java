package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Application;
import com.mygdx.game.utils.BodyBuilder;

import static com.mygdx.game.entities.Player.Direction.*;
import static com.mygdx.game.utils.Constants.PPM;

public class Player extends AbstractEntity {

    private Body body;
    private float angle;
    private Direction direction;

    private Direction[][] dirMatrix = new Direction[][]{
            {UP_LEFT, UP, UP_RIGHT},
            {LEFT, UNDEFINED, RIGHT},
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
        UNDEFINED
    }

    public Player(Application application, World world) {
        super(application, world);
        createBody();
    }

    @Override
    public void update(float delta) {
        angleUpdate(delta);
        directionUpdate(delta);
        positionUpdate(delta);
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
            case UNDEFINED:
                break;
        }

        body.setLinearVelocity(horizontalForce * 5, verticalForce * 5);
    }

    //region Get/Set
    public void setPosition(Vector2 position) {
        body.setTransform(position, body.getAngle());
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
    //endregion
}
