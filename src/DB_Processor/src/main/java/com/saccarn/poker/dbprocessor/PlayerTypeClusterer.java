package com.saccarn.poker.dbprocessor;

import com.saccarn.poker.dataprocessing.Player;
import sun.awt.image.ImageWatched;

import java.awt.geom.Point2D;
import java.util.*;

/**
 * Created by Neil on 22/02/2017.
 */
public class PlayerTypeClusterer {

    private Map<Integer, List<String>> mappedIdsToNames = new HashMap<>();
    private Map<Integer, List<Integer>> mappedIDsToClusters = new HashMap<>();
    private Matrix distMatrix;

    public Map<String, Vector<Integer>> getPlayerVectors() {
        return new DataLoader().retrieveVectorsForEveryPlayer();
    }

    public void computeDistanceMatrix() {
        Map<String, Vector<Integer>> playerVectors = getPlayerVectors();
        distMatrix= new Matrix(playerVectors.size());
        Set<String> pvSet = playerVectors.keySet();
        int i = 0;
        for(String s1 : pvSet) {
            List li = new LinkedList<>();
            List liCluster = new LinkedList<>();
            li.add(s1);
            liCluster.add(i);
            mappedIdsToNames.put(i, li);
            mappedIDsToClusters.put(i, liCluster);
            i++;
            for (String s2 : pvSet) {
                distMatrix.put(computeDistance(playerVectors.get(s1), playerVectors.get(s2)));
            }
        }
        distMatrix.print();
        getNClusters(2);
    }


    public void getNClusters(int n) {
        if (n > mappedIDsToClusters.size()) {
            throw new IllegalArgumentException(); //Maybe some other exception might be more appropriate?
        }
        while(mappedIDsToClusters.size() != n) {
            Point p = getMinDistPointFromMatrix();
            System.out.println(mappedIDsToClusters);
            System.out.println(p);
            int x = p.getX();
            int y = p.getY();
            if (x < y) {
                List ly = mappedIDsToClusters.get(y);
                mappedIDsToClusters.get(x).addAll(ly);
                mappedIDsToClusters.remove(y);
            } else {
                List lx = mappedIDsToClusters.get(x);
                mappedIDsToClusters.get(y).addAll(lx);
                mappedIDsToClusters.remove(x);
            }
            updateMatrix(p);
            distMatrix.print();
            System.out.println(mappedIDsToClusters);
        }
    }


    public void updateMatrix(Point p) {
        int x  = p.getX();
        int y = p.getY();
        // using shortest distance
        for (int i = 0; i < distMatrix.xDimensionSize(); i++) {
            double d1 = distMatrix.getValue(i, x);
            double d2 = distMatrix.getValue(i, y);
            if (d1 < d2) {
                distMatrix.setValue(y, i , d1);
            } else {
                distMatrix.setValue(x, i, d2);
            }
        }
    }

    public Point getMinDistPointFromMatrix() {
        double minDist = Double.MAX_VALUE;
        System.out.println(minDist);
        int x = 0, y = 0;
        for (int i = 0; i < mappedIdsToNames.size(); i++) {
            for (int j = i; j < mappedIdsToNames.size(); j++) {
                if (distMatrix.getValue(i, j) < minDist && distMatrix.getValue(i, j) != 0) {
                    System.out.println("HERE minDist is " + distMatrix.getValue(i, j));
                    minDist = distMatrix.getValue(i, j);
                    x = i;
                    y = j;
                }
            }
        }
        return new Point(x, y);
    }


    public double computeDistance(Vector<Integer> xVector, Vector<Integer> yVector) {
        int dist = 0;
        for (int i = 0; i < xVector.size(); i++) { //manhattan distance
            dist += Math.abs(xVector.get(i) - yVector.get(i));
        }
        return dist;
    }

    public static void main(String [] args) {
        PlayerTypeClusterer ptc = new PlayerTypeClusterer();
        ptc.computeDistanceMatrix();
    }
}
