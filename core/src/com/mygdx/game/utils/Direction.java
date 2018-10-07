package com.mygdx.game.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public enum Direction {
    UP          (0, new Vector2(0, 1)),
    UP_RIGHT    (1, new Vector2(1, 1)),
    RIGHT       (2, new Vector2(1, 0)),
    DOWN_RIGHT  (3, new Vector2(1, -1)),
    DOWN        (4, new Vector2(0, -1)),
    DOWN_LEFT   (5, new Vector2(-1, -1)),
    LEFT        (6, new Vector2(-1, 0)),
    UP_LEFT     (7, new Vector2(-1, 1)),
    UNDEFINED   (8, new Vector2(0, 0));

    private final int index;
    private final Vector2 vector;

    Direction(int index, Vector2 vector) {
        this.index = index;
        this.vector = vector;
    }

    public static Direction random() {
        return Direction.values()[MathUtils.random(values().length) - 1];
    }

    public int getIndex() {
        return index;
    }

    public Vector2 getVector() {
        return vector;
    }
}

