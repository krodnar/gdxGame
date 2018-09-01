package com.mygdx.game.entities;

public enum EntityType {
    PLAYER("player"),
    PORTAL("portal"),
    INVISIBLE_PORTAL("portal", "invisible");

    private String type;
    private String subtype;

    EntityType(String type) {
        this.type = type;
        this.subtype = null;
    }

    EntityType(String type, String subtype) {
        this.type = type;
        this.subtype = subtype;
    }

    public boolean isTypeOf(EntityType entityType) {
        return type.equals(entityType.getType());
    }

    public boolean isSubtypeOf(EntityType entityType) {
        return type.equals(entityType.getType()) && !subtype.equals(entityType.getSubtype());
    }

    public String getType() {
        return type;
    }

    public String getSubtype() {
        return subtype;
    }

    @Override
    public String toString() {
        return type + (subtype == null ? "" : ": " + subtype);
    }
}
