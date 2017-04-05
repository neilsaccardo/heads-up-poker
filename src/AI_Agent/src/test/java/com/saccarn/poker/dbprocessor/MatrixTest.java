package com.saccarn.poker.dbprocessor;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Neil on 05/04/2017.
 */
public class MatrixTest {

    @Test
    public void testMatrixYDimensionSize() {
        Matrix m = new Matrix(3);
        m.put(2);
        m.put(3);
        m.put(4);
        m.put(5);
        m.put(6);
        m.put(7);
        m.put(8);
        Assert.assertEquals("Matrix y dimension should be the integer value of (total points) over xdimension size", 3, m.yDimensionSize());
    }

    @Test
    public void testMatrixXDimensionSize() {
        Matrix m = new Matrix(3);
        m.put(2);
        m.put(3);
        m.put(4);
        m.put(5);
        Assert.assertEquals("Matrix X dimension should be the value specified in matrix " +
                "constructor", 3, m.xDimensionSize());
    }

    @Test
    public void testMatrixGetValues() {
        Matrix m = new Matrix(3);
        m.put(2);
        m.put(3);
        m.put(4);
        m.put(5);
        m.put(6);
        m.put(7);
        m.put(8);
        double d1 = m.getValue(2, 0);
        double d2 = m.getValue(0, 2);
        double delta = 0.1; //deal with floating point errors
        Assert.assertEquals("Matrix should get values in accordance with the order they were inserted and x dimension size", 4.0, d1, delta);
        Assert.assertEquals("Matrix should get values in accordance with the order they were inserted and x dimension size", 8.0, d2, delta);
    }

    @Test
    public void testMatrixSetValues() {
        Matrix m = new Matrix(3);
        m.put(2);
        m.put(3);
        m.put(4);
        m.put(5);
        m.put(6);
        m.put(7);
        m.put(8);
        double actual1 = 100.0;
        m.setValue(2, 0, actual1);
        double d1 = m.getValue(2, 0);
        double delta = 0.1; //deal with floating point errors
        Assert.assertEquals("Matrix should set values per their co-ordinates specified", actual1, d1, delta);
    }


    @Test (expected = IndexOutOfBoundsException.class)
    public void throwIndexOutOfBoundsWhenSettingInvalidValue() {
        Matrix m = new Matrix(3);
        m.put(2);
        m.put(3);
        m.put(4);
        m.put(5);
        m.put(6);
        m.put(7);
        m.put(8);
        m.setValue(4, 4, 1002);
        Assert.assertEquals("Should throw an Index Out of Bounds Exception when setting a value that does not exist in the matrix", true, false);

    }
}