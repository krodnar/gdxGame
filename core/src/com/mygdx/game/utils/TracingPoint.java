package com.mygdx.game.utils;

public class TracingPoint {

    public static final int TOP = 0;
    public static final int RIGHT = 1;
    public static final int BOTTOM = 2;
    public static final int LEFT = 3;

    private int col;
    private int row;
    private int direction;

    private int[][] data;

    public TracingPoint(int[][] data) {
        this.data = data;
    }

    public TracingPoint(int col, int row, int[][] data) {
        this(data);
        this.col = col;
        this.row = row;
        this.direction = TOP;
    }

    public TracingPoint(int col, int row, int direction, int[][] data) {
        this(col, row, data);
        this.direction = direction;
    }

    public void turnLeft() {
        direction = (direction + 4 - 1) % 4;
    }

    public void turnRight() {
        direction = (direction + 1) % 4;
    }

    public void goForward() {
        row = getForwardRow();
        col = getForwardCol();
    }

    @SuppressWarnings("Duplicates")
    public int getForwardRow() {
        if (direction == LEFT) {
            return row - 1;
        } else if (direction == RIGHT) {
            return row + 1;
        } else {
            return row;
        }
    }

    @SuppressWarnings("Duplicates")
    public int getForwardCol() {
        if (direction == TOP) {
            return col - 1;
        } else if (direction == BOTTOM) {
            return col + 1;
        } else {
            return col;
        }
    }

    public void goLeft() {
        row = getLeftRow();
        col = getLeftCol();

        turnLeft();
    }

    public int getLeftRow() {
        if (direction == TOP || direction == LEFT) {
            return row - 1;
        } else {
            return row + 1;
        }
    }

    public int getLeftCol() {
        if (direction == TOP || direction == RIGHT) {
            return col - 1;
        } else {
            return col + 1;
        }
    }

    public void goRight() {
        row = getRightRow();
        col = getRightCol();
    }

    public int getRightRow() {
        if (direction == TOP || direction == RIGHT) {
            return row + 1;
        } else {
            return row - 1;
        }
    }

    public int getRightCol() {
        if (direction == TOP || direction == LEFT) {
            return col - 1;
        } else {
            return col + 1;
        }
    }

    public boolean checkForward() {
        int x = getForwardRow();
        int y = getForwardCol();

        if (x < 0 || y < 0 || x >= data.length || y >= data[0].length) {
            return false;
        }

        return data[y][x] == 0;
    }

    public boolean checkLeft() {
        int x = getLeftRow();
        int y = getLeftCol();

        if (x < 0 || y < 0 || x >= data.length || y >= data[0].length) {
            return false;
        }

        return data[y][x] == 0;
    }

    public boolean checkRight() {
        int x = getRightRow();
        int y = getRightCol();

        if (x < 0 || y < 0 || x >= data.length || y >= data[0].length) {
            return false;
        }

        return data[y][x] == 0;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int x) {
        this.col = x;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int y) {
        this.row = y;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
