package com.mygdx.game.utils;

import com.badlogic.gdx.math.Vector2;

/**
 * Directions and a corresponding vectors in 2D.
 */
public enum Direction {
    UP          (new Vector2(0, 1)),
    UP_RIGHT    (new Vector2(1, 1)),
    RIGHT       (new Vector2(1, 0)),
    DOWN_RIGHT  (new Vector2(1, -1)),
    DOWN        (new Vector2(0, -1)),
    DOWN_LEFT   (new Vector2(-1, -1)),
    LEFT        (new Vector2(-1, 0)),
    UP_LEFT     (new Vector2(-1, 1)),
    UNDEFINED   (new Vector2(0, 0));

    private final Vector2 vector;

    Direction(Vector2 vector) {
        this.vector = vector;
    }

    public Vector2 getVector() {
        return vector;
    }
}

