package com.mygdx.game.map;

import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.LinkedList;
import java.util.List;

import static com.mygdx.game.utils.Constants.PPM;

public class PolylinesCollisionParser {

    public static void parseCollision(World world, List<Polyline> polylines) {
        List<Vector2> vertices = new LinkedList<Vector2>();

        Polyline currentPolyline;
        Vector2 currentPoint = null;

        while (!polylines.isEmpty()) {

            currentPolyline = getNextPolyline(polylines, currentPoint);

            if (currentPolyline == null) {
                if (!vertices.isEmpty()) {
                    createChain(world, vertices.toArray(new Vector2[]{}));
                    vertices.clear();
                }

                currentPolyline = polylines.get(0);
                currentPoint = getFirstPoint(currentPolyline);
                vertices.add(currentPoint);
            }

            currentPoint = getNextPoint(currentPolyline, currentPoint);
            vertices.add(currentPoint);
            polylines.remove(currentPolyline);
        }

        createChain(world, vertices.toArray(new Vector2[]{}));
    }

    private static void createChain(World world, Vector2[] vertices) {
        ChainShape chain = new ChainShape();
        chain.createChain(vertices);

        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bd);
        body.createFixture(chain, 1);

        chain.dispose();
    }

    private static Polyline getNextPolyline(List<Polyline> polylines, Vector2 point) {
        if (point == null) {
            return null;
        }

        for (Polyline polyline : polylines) {
            if (lineContainsPoint(polyline, point)) {
                return polyline;
            }
        }

        return null;
    }

    private static Vector2 getNextPoint(Polyline polyline, Vector2 point) {
        Vector2 second;
        Vector2 pointA = getFirstPoint(polyline);
        Vector2 pointB = getSecondPoint(polyline);

        if (point.equals(pointA)) {
            second = pointB;
        } else {
            second = pointA;
        }

        return second;
    }

    private static boolean lineContainsPoint(Polyline polyline, Vector2 point) {
        Vector2 pointA = getFirstPoint(polyline);
        Vector2 pointB = getSecondPoint(polyline);

        return pointA.equals(point) || pointB.equals(point);
    }

    private static Vector2 getFirstPoint(Polyline polyline) {
        float[] points = polyline.getTransformedVertices();
        return new Vector2(points[0] / PPM, points[1] / PPM);
    }

    private static Vector2 getSecondPoint(Polyline polyline) {
        float[] points = polyline.getTransformedVertices();
        return new Vector2(points[2] / PPM, points[3] / PPM);
    }
}
