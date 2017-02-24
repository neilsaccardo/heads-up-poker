package com.saccarn.poker.dbprocessor;

import com.saccarn.poker.dataprocessing.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 * Created by Neil on 22/02/2017.
 */
public class PlayerTypeClusterer {

    private Map<Integer, String> mappedIdsToNames = new HashMap<>();

    public Map<String, Vector<Integer>> getPlayerVectors() {
        return new DataLoader().retrieveVectorsForEveryPlayer();
    }



    public void computeDistanceMatrix() {
        Map<String, Vector<Integer>> playerVectors = getPlayerVectors();
        Matrix distMatrix = new Matrix(playerVectors.size());
        Set<String> pvSet = playerVectors.keySet();
        int i = 0;
        for(String s1 : pvSet) {
            mappedIdsToNames.put(i, s1);
            for (String s2 : pvSet) {
                distMatrix.put(computeDistance(playerVectors.get(s1), playerVectors.get(s2)));
            }
        }
        distMatrix.print();
    }


    public double computeDistance(Vector<Integer> xVector, Vector<Integer> yVector) {
        int dist = 0;
        for (int i = 0; i < xVector.size(); i++) {
            dist += Math.abs( xVector.get(i) - yVector.get(i));
        }
        return dist;
    }

    public static void main(String [] args) {
        PlayerTypeClusterer ptc = new PlayerTypeClusterer();
        ptc.computeDistanceMatrix();
    }
}
