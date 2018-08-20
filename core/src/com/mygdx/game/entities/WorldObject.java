package com.mygdx.game.entities;

public class WorldObject {

    public enum Type {
        COLLISION
    }

    private Type type;

    public WorldObject(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
