package com.mygdx.game.utils;

import com.badlogic.gdx.math.Vector2;

import java.util.LinkedList;
import java.util.List;

public class BoundaryTracer {

    public static List<Vector2> trace(int[][] data) {
        List<Vector2> vertices = new LinkedList<Vector2>();

        Vector2 startPoint = getStartPoint(data);
        if (startPoint == null) {
            System.out.println("Given data does not contain positive points");
            return vertices;
        }

        vertices.add(startPoint);

        TracingPoint tp = new TracingPoint((int) startPoint.x, (int) startPoint.y, data);
        while (true) {

            // printDebug(data, vertices);
            boolean add = false;
            int row;
            int col;

            if (tp.checkLeft()) {
                add = true;
                tp.goLeft();
            } else if (tp.checkForward()) {
                add = true;
                tp.goForward();
            } else if (tp.checkRight()) {
                add = true;
                tp.goRight();
            } else {
                tp.turnRight();
            }

            row = tp.getRow();
            col = tp.getCol();

            if (add) {
                if (row == startPoint.y && col == startPoint.x) {
                    break;
                }
                vertices.add(new Vector2(col, row));
            }

        }

        vertices.add(startPoint);

        return vertices;
    }

    public static List<Vector2> trace(Vector2[][] data, boolean ignoreBoundaries) {
        int[][] binary = getBinaryImage(data);

        if (ignoreBoundaries) {
            binary = clearBoundaries(binary);
        }

        return trace(binary);
    }

    private static int[][] clearBoundaries(int[][] data) {
        for (int i = 0; i < data.length; i++) {
            data[0][i] = 1;
            data[i][0] = 1;
            data[data.length - 1][i] = 1;
            data[i][data.length - 1] = 1;
        }

        return data;
    }

    private static Vector2 getStartPoint(int[][] data) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length; j++) {
                if (data[i][j] == 0) {
                    return new Vector2(i, j);
                }
            }
        }

        return null;
    }

    private static int[][] getBinaryImage(Object[][] data) {
        int[][] binary = new int[data.length][data[0].length];

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length; j++) {
                if (data[i][j] != null) {
                    binary[i][j] = 0;
                } else {
                    binary[i][j] = 1;
                }
            }
        }

        return binary;
    }

    private static void printDebug(int[][] data, List<Vector2> vertices) {
        for (int i = 0; i < data.length; i++) {
            System.out.print(i + "\t");
            for (int j = 0; j < data.length; j++) {
                if (vertices.contains(new Vector2(i, j))) {
                    System.out.print("* ");
                } else if (data[i][j] == 0) {
                    System.out.print("0 ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
