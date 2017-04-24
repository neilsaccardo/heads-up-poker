package com.saccarn.poker.dbprocessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Neil on 23/02/2017.
 */
public class Matrix {

    private List<List<Double>> matrix = new ArrayList<>();
    private int counter = 0;
    private int dimension = 0;

    public Matrix(int num) {
        dimension = num;
    }

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


    public double getValue(int x, int y) {
        return  matrix.get(y).get(x);
    }

    public void setValue(int x, int y, double v) {
        if (x > dimension || matrix.size() < y) {
            throw new IndexOutOfBoundsException();
        }
        matrix.get(y).set(x, v);
        matrix.get(x).set(y, v);
    }

    public int xDimensionSize() {
        return dimension;
    }

    public int yDimensionSize() {
        return matrix.size();
    }

}
