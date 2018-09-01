package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Application;
import com.mygdx.game.utils.BodyBuilder;
import com.mygdx.game.utils.Direction;

import static com.mygdx.game.utils.Constants.PPM;
import static com.mygdx.game.utils.Direction.DOWN;
import static com.mygdx.game.utils.Direction.DOWN_LEFT;
import static com.mygdx.game.utils.Direction.DOWN_RIGHT;
import static com.mygdx.game.utils.Direction.LEFT;
import static com.mygdx.game.utils.Direction.RIGHT;
import static com.mygdx.game.utils.Direction.UNDEFINED;
import static com.mygdx.game.utils.Direction.UP;
import static com.mygdx.game.utils.Direction.UP_LEFT;
import static com.mygdx.game.utils.Direction.UP_RIGHT;

public class Player extends ActiveEntity {

    public static final int STOPPED = 0;
    public static final int MOVING = 1;

    private float angle = 0;

    private Direction[][] dirMatrix = new Direction[][]{
            {UP_LEFT, UP, UP_RIGHT},
            {LEFT, UNDEFINED, RIGHT},
            {DOWN_LEFT, DOWN, DOWN_RIGHT}
    };

    public Player(Application application, World world) {
        super(application, world);
        initialize(EntityType.PLAYER);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        angleUpdate(delta);
    }

    @Override
    public void createBodies() {
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

    @Override
    public void updateDirection(float delta) {
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

        if (i == 1 && j == 1) {
            state = STOPPED;
            return;
        }

        state = MOVING;
        direction = dirMatrix[j][i];
    }

    @Override
    public void updatePosition(float delta) {
        if (state != MOVING) {
            body.setLinearVelocity(0, 0);
            return;
        }

        body.setLinearVelocity(speed);
    }

    //region Get/Set
    public void setPosition(Vector2 position) {
        body.setTransform(position, body.getAngle());
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    //endregion
}
