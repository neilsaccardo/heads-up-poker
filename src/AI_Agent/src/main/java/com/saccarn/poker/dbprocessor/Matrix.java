package com.saccarn.poker.dbprocessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Matrix used to keep track of distances between players in clustering.
 * Created by Neil on 23/02/2017.
 */
public class Matrix {

    private List<List<Double>> matrix = new ArrayList<>();
    private int counter = 0;
    private int dimension = 0;

    /**
     * Full args matrix with dimension of matrix specified.
     * @param num dimension of matrix
     */
    public Matrix(int num) {
        dimension = num;
    }


    /**
     * Add distance to matrix
     * @param d to be added.
     */
    public void put(double d) {
        if (counter % dimension == 0) {
            List<Double> listds = new ArrayList();
            listds.add(d);
            matrix.add(listds);
        } else {
            matrix.get(counter / dimension).add(d);
        }
        counter++;
    }


    /**
     * Returns distance at x and y co ord.
     * @param x x co-ord
     * @param y y co-ord
     * @return distance at (x,y)
     */
    public double getValue(int x, int y) {
        return  matrix.get(y).get(x);
    }

    /**
     * Sets distance at x and y co ord
     * @param x x co-ord
     * @param y y co-ord
     * @param v distance at (x,y)
     */
    public void setValue(int x, int y, double v) {
        if (x > dimension || matrix.size() < y) {
            throw new IndexOutOfBoundsException();
        }
        matrix.get(y).set(x, v);
        matrix.get(x).set(y, v);
    }

    /**
     * Returns matrix dimension size
     * @return x dimension size
     */
    public int xDimensionSize() {
        return dimension;
    }

    /**
     * Returns matrix dimension size
     * @return y dimension size
     */
    public int yDimensionSize() {
        return matrix.size();
    }

}
