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
        Assert.assertEquals("Matrix y dimension should be the integer value of (total points) over xdimension size", 3, m.yDimensionSize());
    }

}

/*
        Matrix m = new Matrix(3);


 */
