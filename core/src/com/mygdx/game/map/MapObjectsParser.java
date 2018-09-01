package com.mygdx.game.map;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Application;
import com.mygdx.game.IGameWorld;
import com.mygdx.game.entities.InvisiblePortal;
import com.mygdx.game.entities.Player;

import java.util.HashMap;
import java.util.Map;

import static com.mygdx.game.utils.Constants.PPM;

public class MapObjectsParser {

    private static Map<String, Vector2> destinations;
    private static Map<String, InvisiblePortal> portalDestinations;

    public static void parse(Application app, IGameWorld world, MapObjects objects) {
        destinations = new HashMap<String, Vector2>();
        portalDestinations = new HashMap<String, InvisiblePortal>();

        for (MapObject object : objects) {
            String type = object.getProperties().get("type", String.class);

            if (type.equals("playerPosition")) {
                parsePlayerPosition(object, world.getPlayer());
            } else if (type.equals("portal")) {
                parsePortal(app, world.getWorld(), object);
            } else if (type.equals("portDestination")) {
                parseDestination(object);
            }
        }

        postProcess();
    }

    private static void postProcess() {
        for (String destination : portalDestinations.keySet()) {
            Vector2 position = destinations.get(destination);
            InvisiblePortal invisiblePortal = portalDestinations.get(destination);

            if (position != null && invisiblePortal != null) {
                invisiblePortal.setPortDestination(position.scl(1 / PPM));
            }
        }
    }

    private static void parseDestination(MapObject object) {
        String name = object.getName();
        Vector2 coordinates = getPosition(object);
        destinations.put(name, coordinates);
    }

    private static void parsePortal(Application app, World world, MapObject object) {
        Shape shape = MapObjectsCollisionParser.getObjectCollision(object);
        InvisiblePortal invisiblePortal = new InvisiblePortal(app, world, shape);

        String destination = object.getProperties().get("portDestination", String.class);
        portalDestinations.put(destination, invisiblePortal);
    }

    private static void parsePlayerPosition(MapObject object, Player player) {
        Vector2 coordinates = getPosition(object);
        player.setPosition(coordinates.scl(1 / PPM));
    }

    private static Vector2 getPosition(MapObject object) {
        float x = object.getProperties().get("x", Float.class);
        float y = object.getProperties().get("y", Float.class);
        return new Vector2(x, y);
    }
}
