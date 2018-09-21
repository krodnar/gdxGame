package com.mygdx.game.entities;

/**
 * Entity types and their subtypes.
 */
public enum EntityType {
    PLAYER("player"),
    PORTAL("portal"),
    INVISIBLE_PORTAL("portal", "invisible");

    private String type;
    private String subtype;

    /**
     * Adds new entity main type.
     *
     * @param type entity type
     */
    EntityType(String type) {
        this.type = type;
        this.subtype = null;
    }

    /**
     * Adds new entity type based on some main type.
     *
     * @param type    main type
     * @param subtype subtype of a main type
     */
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
