package com.mygdx.game.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public interface Entity {

    void update(float delta);

    float getX();

    float getY();

    float getWidth();

    float getHeight();

    Vector2 getPosition();

    Rectangle getArea();

    EntityType getType();

    void initialize(EntityType type);
}
