package com.saccarn.poker.dbprocessor;

import java.util.*;

/**
 * Created by Neil on 22/02/2017.
 */
public class PlayerTypeClusterer {

    private Map<Integer, String> mappedIdsToNames = new HashMap<>();
    private Map<Integer, List<Integer>> mappedIDsToClusters = new HashMap<>();
    private Matrix distMatrix;
    private Map<String, Vector<Double>> playerVectors = new HashMap<>();

    private Map<String, Vector<Double>> getPlayerVectors() {
        return new DataLoader().retrieveVectorsForEveryPlayer();
    }

    private void computeDistanceMatrix() {
        playerVectors = getPlayerVectors();
        distMatrix= new Matrix(playerVectors.size());
        Set<String> pvSet = playerVectors.keySet();
        int i = 0;
        for(String s1 : pvSet) {
            List liCluster = new LinkedList<>();
            liCluster.add(i);
            mappedIdsToNames.put(i, s1);
            mappedIDsToClusters.put(i, liCluster);
            i++;
            for (String s2 : pvSet) {
                distMatrix.put(computeDistance(playerVectors.get(s1), playerVectors.get(s2)));
            }
        }
    }


    public void getClusters() {
        getClusters(4);
    }

    public void getClusters(int n) {
        loadPlayerDB();
        computeDistanceMatrix();
        mergeVectorsWhereDistanceIsZero();
        getNClusters(n);
        List<Vector<Double>> lvectors = computeClusterCentroids();
        saveCentroidsIntoDB(lvectors);
    }

    private void loadPlayerDB() {
        try {
            new DataLoader().loadDataIntoMongo();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Vector<Double>> computeClusterCentroids() {
        Set<Integer> clusterIDs = mappedIDsToClusters.keySet();
        List<Vector<Double>> clusterCentroids = new LinkedList<>();
        for (Integer id : clusterIDs) {
            List<Integer> li = mappedIDsToClusters.get(id);
            clusterCentroids.add(computeCentroid(li));
        }
        return clusterCentroids;
    }

    private void saveCentroidsIntoDB(List<Vector<Double>> centroids) {
        new DataLoader().saveClusterCentroids(centroids);
    }

    public static List<Map<String, Double>> getCentroids() {
        return new DataLoader().getCentroids();
    }

    public static Map<String, Double> getCentroid(int n) {
        List<Map<String, Double>> listmap = getCentroids();
        if (n > listmap.size()) {
            throw new IllegalArgumentException();
        }
        else {
            return listmap.get(n);
        }
    }

    private Vector<Double> computeCentroid(List<Integer> li) {
        Vector<Double> centroidVector = new Vector<>();
        for (int i = 0; i < li.size(); i++) {
            String name = mappedIdsToNames.get(li.get(i));
            Vector<Double> playerVector = playerVectors.get(name);
            if (centroidVector.size() == 0) {
                for (int j = 0; j < playerVector.size(); j++) {
                    centroidVector.add(0.0 + playerVector.get(j));
                }
            } else {
                for (int j = 0; j < playerVector.size(); j++) {
                    centroidVector.set(j, centroidVector.get(j) + playerVector.get(j));
                }
            }
        }
        for (int i = 0; i < centroidVector.size(); i++) {
            centroidVector.set(i, centroidVector.get(i) / li.size());
        }
        return centroidVector;

    }

    private void getNClusters(int n) {
        if (n > mappedIDsToClusters.size()) {
            throw new IllegalArgumentException(); //Maybe some other exception might be more appropriate?
        }
        while(mappedIDsToClusters.size() != n) {
            Point p = getMinDistPointFromMatrix();
            int x = p.getX();
            int y = p.getY();
            if (x < y) {
                updateMapIdToClusters(x, y);
            } else {
                updateMapIdToClusters(y, x);
            }
            updateMatrix(p);
        }
    }
    // method is used to deal when the distance between two points
    // (i.e. two players who have played the exact same no of times, bet preflop, flop etc).
    private void mergeVectorsWhereDistanceIsZero() {
        List<Integer> listOfMergedVectors = new LinkedList<>();
        for (int i = 0; i < distMatrix.xDimensionSize(); i++) {
            for (int j = i; j < distMatrix.yDimensionSize(); j++) {
                if(distMatrix.getValue(i, j) == 0.0 && i != j && (!listOfMergedVectors.contains(j))) {
                    updateMatrix(new Point(i, j));
                    if (i < j) {
                        updateMapIdToClusters(i, j);
                        listOfMergedVectors.add(j);
                    } else {
                        updateMapIdToClusters(j, i);
                        listOfMergedVectors.add(i);
                    }
                }
            }
        }
    }



    private void updateMapIdToClusters(int small, int large) {
        List ly = mappedIDsToClusters.get(large);
        if(ly == null) { //these two are already clustered together.
            return;
        }
        mappedIDsToClusters.get(small).addAll(ly);
        mappedIDsToClusters.remove(large);
    }

    private void updateMatrix(Point p) {
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

    private Point getMinDistPointFromMatrix() {
        double minDist = Double.MAX_VALUE;
        int x = 0, y = 0;
        for (int i = 0; i < mappedIdsToNames.size(); i++) {
            for (int j = i; j < mappedIdsToNames.size(); j++) {
                if (distMatrix.getValue(i, j) < minDist && distMatrix.getValue(i, j) != 0) {
                    minDist = distMatrix.getValue(i, j);
                    x = i;
                    y = j;
                }
            }
        }
        return new Point(x, y);
    }

    private double computeDistance(Vector<Double> xVector, Vector<Double> yVector) {
        double dist = 0;

        for ( int i = 0; i < xVector.size(); i++) {
            dist = i * (yVector.get(i) - xVector.get(i)) * (yVector.get(i) - xVector.get(i));
        }
        return Math.sqrt(dist);

//
//        for (int i = 0; i < xVector.size(); i++) { //manhattan distance
//            dist += (Math.abs(xVector.get(i) - yVector.get(i)));
//        }
//        return dist;
    }

    public static void main(String [] args) {
        PlayerTypeClusterer ptc = new PlayerTypeClusterer();
        ptc.getClusters();
    }
}
