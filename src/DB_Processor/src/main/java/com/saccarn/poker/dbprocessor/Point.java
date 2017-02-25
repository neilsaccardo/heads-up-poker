package com.saccarn.poker.dbprocessor;

/**
 * Created by Neil on 24/02/2017.
 */
public class Point {
    private int x, y;

    public Point(int x1, int y1) {
        x = x1;
        y = y1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
