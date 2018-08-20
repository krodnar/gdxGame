package com.mygdx.game.map;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.utils.BoundaryTracer;

import java.util.LinkedList;
import java.util.List;

import static com.mygdx.game.utils.Constants.PPM;

public class RectanglesCollisionParser {

    public static void parseCollision(World world, Rectangle[][] rectangles) {
        long startTime = System.nanoTime();
        System.out.println("Start world collision parsing...");

        Vector2[][] points = getRectanglesPoints(rectangles);

        long endTime = System.nanoTime();
        System.out.println("World collision parsed in " + (endTime - startTime) / 1000000 + " ms");

        List<Vector2> vertices = BoundaryTracer.trace(points, true);
        ChainShape chain = new ChainShape();
        chain.createChain(vertices.toArray(new Vector2[]{}));

        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bd);
        body.createFixture(chain, 1);
    }

    private static Vector2[][] getRectanglesPoints(Rectangle[][] rectangles) {
        List<Vector2> points = new LinkedList<Vector2>();
        for (Rectangle[] row : rectangles) {
            for (Rectangle rectangle : row) {
                if (rectangle == null) {
                    continue;
                }

                points.add(new Vector2(rectangle.x, rectangle.y));
                points.add(new Vector2(rectangle.x, rectangle.y + rectangle.height));
                points.add(new Vector2(rectangle.x + rectangle.width, rectangle.y));
                points.add(new Vector2(rectangle.x + rectangle.width, rectangle.y + rectangle.height));
            }
        }

        points = removeUnnecessaryPoints(points);

        int mapSize = rectangles.length + 1;

        Vector2[][] pointsArray = new Vector2[mapSize][mapSize];
        for (Vector2 point : points) {
            int i = (int) (point.x / PPM);
            int j = (int) (point.y / PPM);
            pointsArray[i][j] = point;
        }

        return pointsArray;
    }

    private static List<Vector2> removeUnnecessaryPoints(List<Vector2> points) {
        List<Vector2> doublePoints = new LinkedList<Vector2>();

        for (int i = points.size() - 1; i >= 0; i--) {
            Vector2 point = points.get(i);
            int count = getCopiesAmount(points, point);
            if (count == 2 || count == 3) {
                doublePoints.add(point);
            }
        }

        return doublePoints;
    }

    private static int getCopiesAmount(List<Vector2> points, Vector2 point) {
        int copies = 0;
        for (Vector2 point2 : points) {
            if (pointsAreEqual(point, point2)) {
                copies += 1;
            }
        }
        return copies;
    }

    private static boolean pointsAreEqual(Vector2 v1, Vector2 v2) {
        return !(v1.x != v2.x) && !(v1.y != v2.y);
    }
}
