package com.mygdx.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Application;
import com.mygdx.game.entities.EntityPropertiesMap.EntityProperties;
import com.mygdx.game.utils.Direction;

import static com.mygdx.game.utils.Direction.DOWN;

public abstract class ActiveEntity extends StatefulEntity {

    protected Direction direction = DOWN;
    protected Vector2 speed = new Vector2();
    protected float maxSpeed;

    public ActiveEntity(Application app, World world) {
        super(app, world);
    }

    @Override
    public void initialize(EntityType type) {
        super.initialize(type);
        maxSpeed = EntityPropertiesMap.getEntityProperty(type, EntityProperties.SPEED);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        updateDirection(delta);
        updateSpeed(delta);
        updatePosition(delta);
    }

    private void updateSpeed(float delta) {
        speed.set(direction.getVector());
        speed.nor().scl(maxSpeed);
    }

    protected abstract void updateDirection(float delta);

    protected abstract void updatePosition(float delta);

    public Direction getDirection() {
        return direction;
    }
}
