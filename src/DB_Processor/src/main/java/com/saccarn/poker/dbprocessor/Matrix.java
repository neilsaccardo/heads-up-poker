package com.saccarn.poker.dbprocessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Neil on 23/02/2017.
 */
public class Matrix {

    private int horizontal;
    private int vertical;
    private List<List<Double>> matrix = new ArrayList<List<Double>>();
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

    public void print() {
        for (int i = 0; i < matrix.size(); i++) {
            for(int j = 0; j < matrix.get(i).size(); j++) {
                System.out.print(matrix.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    public double getValue(int x, int y) {
        return  matrix.get(y).get(x);
    }

    public static void main(String [] args) {

        Matrix m = new Matrix(3);
        m.put(2);
        m.put(3);
        m.put(4);
        m.put(5);
        m.put(6);
        m.put(7);
        m.print();
    }
}
