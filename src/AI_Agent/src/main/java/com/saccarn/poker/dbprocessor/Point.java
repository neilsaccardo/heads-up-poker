package com.saccarn.poker.dbprocessor;

/**
 * Point is used to encapsulate x and y co ordinates.
 *
 * Created by Neil on 24/02/2017.
 */
public class Point {
    private int x, y;

    /**
     * Full args constructor
     * @param x1 x cord
     * @param y1 y cord
     */
    public Point(int x1, int y1) {
        x = x1;
        y = y1;
    }

    /**
     *  Returns x co-ordinate
     * @return x co-ordinate
     */
    public int getX() {
        return x;
    }

    /**
     *  Returns y co-ordinate
     * @return y co-ordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Returns string representation of point
     * @return string representation of point
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
