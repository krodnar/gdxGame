package com.mygdx.game.entities;

/**
 * An objects existing in Box2D world that does not relate to any entity.
 * @deprecated Any WorldObject should be replaced by appropriate {@link Entity}
 */
@Deprecated
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
