package com.mygdx.game.map;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Application;
import com.mygdx.game.GameWorld;

import java.util.LinkedList;
import java.util.List;

import static com.mygdx.game.utils.Constants.PPM;

/**
 * A MapParser provides several methods for parsing {@link MapObject} in different ways.
 *
 * @see Map
 */
public class MapParser {

    /**
     * Parses every object in a map layer and creates corresponding body in a world.
     *
     * @param world       world to create bodies in
     * @param map         map from which to get layer
     * @param layerNaming layer naming from which to get objects
     * @return list of created bodies
     * @see World
     * @see Map
     */
    public static Array<Body> parseObjectsCollision(World world, Map map, String layerNaming) {
        MapObjects objects = getLayerObjects(map, layerNaming);
        return MapObjectsCollisionParser.parseObjectsCollision(world, objects);
    }

    /**
     * Connects all polylines in a map layer into one or multiple {@link ChainShape chainshapes}.
     * <p>
     * It is special method for providing a convenient way of creating a big {@link ChainShape}
     * which is often used as a boundary of a world. For method to work properly polylines
     * should start at the end of another polyline. Such lines will be connected into one.
     * <p>
     * It gives an easy way of outlining big map structures because a map editors usually
     * have "automapping" feature that can create multiple polylines based on some rule instead
     * of creating all of them manually.
     * <p>
     * Any other objects from a layer will be ignored.
     *
     * @param world       world to create chain shape(s) in
     * @param map         map from which to get layer
     * @param layerNaming layer naming from which to get polylines
     */
    public static void parseWorldCollision(World world, Map map, String layerNaming) {
        MapObjects objects = getLayerObjects(map, layerNaming);

        List<Polyline> polylines = new LinkedList<Polyline>();

        for (MapObject object : objects) {
            if (!(object instanceof PolylineMapObject)) {
                continue;
            }

            Polyline polyline = ((PolylineMapObject) object).getPolyline();
            polylines.add(polyline);
        }

        PolylinesCollisionParser.parseCollision(world, polylines);
    }

    /**
     * Parses map objects for in game entities and creates them in a world.
     *
     * @param app         main application class that is passed over to entities
     * @param world       world to create entities in
     * @param map         map from which to get layer
     * @param layerNaming layer naming from which to get objects
     */
    public static void parseCustomObjects(Application app, GameWorld world, Map map, String layerNaming) {
        MapObjects objects = getLayerObjects(map, layerNaming);
        MapObjectsParser.parse(app, world, objects);
    }

    private static MapObjects getLayerObjects(Map map, String layerNaming) {
        MapLayer layer = map.getLayers().get(layerNaming);

        if (layer == null) {
            throw new IllegalArgumentException("Layer \"" + layerNaming + "\" not found");
        }

        return getLayerObjects(layer, new MapObjects());
    }

    /**
     * Returns all objects from a map layer. Recursively gets all objects from
     * {@link MapGroupLayer group layers}, if any.
     *
     * @param layer      map layer from which to get objects
     * @param mapObjects collections to store found objects
     * @return list of map objects
     */
    private static MapObjects getLayerObjects(MapLayer layer, MapObjects mapObjects) {
        if (layer instanceof MapGroupLayer) {

            MapLayers layers = ((MapGroupLayer) layer).getLayers();
            for (MapLayer groupLayer : layers) {
                getLayerObjects(groupLayer, mapObjects);
            }
        } else {

            MapObjects layerObjects = layer.getObjects();
            for (MapObject layerObject : layerObjects) {
                mapObjects.add(layerObject);
            }

            return mapObjects;
        }

        return mapObjects;
    }

    private static int getTileSize(Map map) {
        return (int) (Integer) map.getProperties().get("tilewidth");
    }
}
