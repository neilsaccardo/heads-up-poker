package com.saccarn.poker.dbprocessor;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Neil on 05/04/2017.
 */
public class PointTest {

    @Test
    public void testGetX() {
        Point p = new Point(2, 4);
        Assert.assertEquals("X co-ordinate of Point should be first parameter in Point Constructor", 2, p.getX());
    }

    @Test
    public void testGetY() {
        Point p = new Point(2, 4);
        Assert.assertEquals("Y co-ordinate of Point should be second parameter in Point Constructor", 4, p.getY());
    }

    @Test
    public void testPointToString() {
        Point p = new Point(2, 4);
        String actual = "(2, 4)";
        Assert.assertEquals("ToString should return a string representation of a point : (x, y)", actual, p.toString() );
    }
}
