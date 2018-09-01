package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;

import java.util.HashMap;
import java.util.Map;

public final class EntityPropertiesMap {

    public enum EntityProperties {
        WIDTH, HEIGHT, SPEED
    }

    private static Map<EntityType, Properties> properties = new HashMap<EntityType, Properties>();

    static {
        init();
    }

    public static float getEntityProperty(EntityType type, EntityProperties property) {
        Properties props = getProperties(type);

        switch (property) {
            case WIDTH:
                return props.width;
            case HEIGHT:
                return props.height;
            case SPEED:
                return props.speed;
        }

        return properties.get(type).width;
    }

    private static Properties getProperties(EntityType type) {
        Properties props = properties.get(type);
        if (props == null) {
            Gdx.app.error("EntityPropertiesMap", "No properties for " + type);
            return new Properties(0, 0);
        }

        return props;
    }

    private static void init() {
        add(EntityType.PLAYER, 8, 16, 5);
        add(EntityType.PORTAL, 32, 32);
        add(EntityType.INVISIBLE_PORTAL, 0, 0);
    }

    private static void add(EntityType type, float width, float height) {
        Properties props = new Properties(width, height);
        properties.put(type, props);
    }

    private static void add(EntityType type, float width, float height, float speed) {
        Properties properties = new Properties(width, height, speed);
        EntityPropertiesMap.properties.put(type, properties);
    }

    private static class Properties {

        float width;
        float height;
        float speed;

        Properties(float width, float height) {
            this.width = width;
            this.height = height;
        }

        Properties(float width, float height, float speed) {
            this.width = width;
            this.height = height;
            this.speed = speed;
        }
    }
}
