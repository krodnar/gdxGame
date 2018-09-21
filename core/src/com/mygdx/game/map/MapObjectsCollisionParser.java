package com.mygdx.game.map;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.WorldObject;

import static com.mygdx.game.utils.Constants.PPM;

/**
 * A class with methods for creating bodies in a Box2D world and getting body shape from a
 * {@link MapObject}.
 */
public class MapObjectsCollisionParser {

    /**
     * Creates corresponding body for every map object, except {@link TextureMapObject}.
     *
     * @param world   world to create bodies in
     * @param objects list of map objects to parse
     * @return list of created bodies
     */
    public static Array<Body> parseObjectsCollision(World world, MapObjects objects) {
        Array<Body> bodies = new Array<Body>();

        for (MapObject object : objects) {

            if (object instanceof TextureMapObject) {
                continue;
            }

            Shape shape = getObjectCollision(object);

            BodyDef bd = new BodyDef();
            bd.type = BodyDef.BodyType.StaticBody;
            Body body = world.createBody(bd);
            body.createFixture(shape, 1);
            body.setUserData(new WorldObject(WorldObject.Type.COLLISION));

            bodies.add(body);

            shape.dispose();
        }

        return bodies;
    }

    /**
     * Creates a {@link Shape} that corresponds to the passed {@link MapObject}.
     *
     * @param object map object for which to create shape
     * @return corresponding shape or null if map object class is not supported
     */
    public static Shape getObjectCollision(MapObject object) {
        Shape shape;

        if (object instanceof RectangleMapObject) {
            shape = getRectangle((RectangleMapObject) object);
        } else if (object instanceof PolygonMapObject) {
            shape = getPolygon((PolygonMapObject) object);
        } else if (object instanceof PolylineMapObject) {
            shape = getPolyline((PolylineMapObject) object);
        } else if (object instanceof CircleMapObject) {
            shape = getCircle((CircleMapObject) object);
        } else {
            shape = null;
        }

        return shape;
    }

    private static PolygonShape getRectangle(RectangleMapObject rectangleObject) {
        Rectangle rectangle = rectangleObject.getRectangle();
        PolygonShape polygon = new PolygonShape();
        Vector2 size = new Vector2(
                (rectangle.x + rectangle.width * 0.5f) / PPM,
                (rectangle.y + rectangle.height * 0.5f) / PPM);
        polygon.setAsBox(
                rectangle.width * 0.5f / PPM,
                rectangle.height * 0.5f / PPM,
                size,
                0.0f);
        return polygon;
    }

    private static CircleShape getCircle(CircleMapObject circleObject) {
        Circle circle = circleObject.getCircle();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(circle.radius / PPM);
        circleShape.setPosition(new Vector2(circle.x / PPM, circle.y / PPM));
        return circleShape;
    }

    private static PolygonShape getPolygon(PolygonMapObject polygonObject) {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();

        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {
            worldVertices[i] = vertices[i] / PPM;
        }

        polygon.set(worldVertices);
        return polygon;
    }

    private static ChainShape getPolyline(PolylineMapObject polylineObject) {
        float[] vertices = polylineObject.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; ++i) {
            worldVertices[i] = new Vector2();
            worldVertices[i].x = vertices[i * 2] / PPM;
            worldVertices[i].y = vertices[i * 2 + 1] / PPM;
        }

        ChainShape chain = new ChainShape();
        chain.createChain(worldVertices);
        return chain;
    }
}
