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
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Application;
import com.mygdx.game.IGameWorld;
import com.mygdx.game.entities.Player;

import java.util.LinkedList;
import java.util.List;

import static com.mygdx.game.utils.Constants.PPM;

public class MapParser {

    public static Array<Body> parseObjectsCollision(World world, Map map, String layerNaming) {
        MapObjects objects = getLayerObjects(map, layerNaming);
        return MapObjectsCollisionParser.parseObjectsCollision(world, objects);
    }

    public static void parseWorldCollision(World world, Map map, String layerNaming, CollisionType collisionType) {
        MapObjects objects = getLayerObjects(map, layerNaming);

        if (collisionType == CollisionType.RECTANGLES) {
            int width = (Integer) map.getProperties().get("width");
            Rectangle[][] rectangles = new Rectangle[width][width];

            for (MapObject object : objects) {
                if (!(object instanceof RectangleMapObject)) {
                    continue;
                }

                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                int x = (int) (rectangle.x / PPM);
                int y = (int) (rectangle.y / PPM);
                rectangles[y][x] = rectangle;
            }

            RectanglesCollisionParser.parseCollision(world, rectangles);

        } else if (collisionType == CollisionType.POLYLINES) {
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
    }

    public static void parseCustomObjects(Application app, IGameWorld world, Map map, String layerNaming) {
        MapObjects objects = getLayerObjects(map, layerNaming);
        MapObjectsParser.parse(app, world, objects);
    }

    private static MapObjects getLayerObjects(Map map, String layerNaming) {
        MapLayer layer = map.getLayers().get(layerNaming);

        if (layer == null) {
            throw new MapParserException("Layer \"" + layerNaming + "\" not found");
        }

        return getLayerObjects(layer, new MapObjects());
    }

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

    public enum CollisionType {
        RECTANGLES, POLYLINES
    }
}
