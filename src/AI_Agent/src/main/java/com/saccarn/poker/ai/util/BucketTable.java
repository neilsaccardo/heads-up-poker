package com.saccarn.poker.ai.util;

import java.util.HashMap;

/**
 * Created by Neil on 18/04/2017.
 */
public class BucketTable {
    private HashMap<Integer, Double> handTypes = new HashMap<>();

    public BucketTable() {
        handTypes.put(0, 0.000014); //straight flush
        handTypes.put(1, 0.0002347); //four of a kind
        handTypes.put(2, 0.0014023); //full house
        handTypes.put(3, 0.0020007); //flush
        handTypes.put(4, 0.0038647); //straight
        handTypes.put(5, 0.0211247); //3 of a kind
        handTypes.put(6, 0.0474187); //two pair
        handTypes.put(7, 0.0324047); //pair A
        handTypes.put(8, 0.0325547); //pair K
        handTypes.put(9, 0.03249); // Pair Q
        handTypes.put(10, 0.0325); //Medium Pair - J
        handTypes.put(11, 0.0325); //Medium Pair - 10
        handTypes.put(12, 0.0325); //Low Pair - 9
        handTypes.put(13, 0.0325); //Low Pair - 8
        handTypes.put(14, 0.0325); //Low Pair - 7
        handTypes.put(15, 0.0325); //Low Pair - 6
        handTypes.put(16, 0.0325); //Low Pair - 5
        handTypes.put(17, 0.0325); //Low Pair - 4
        handTypes.put(18, 0.0325); //Low Pair - 3
        handTypes.put(19, 0.0325); //Low Pair - 2
        handTypes.put(20, 0.193495); // busted ace
        handTypes.put(21, 0.1292463); // busted king
        handTypes.put(22, 0.082212); //busted queen
        handTypes.put(23, 0.07631); //busted med - 10 J
        handTypes.put(24, 0.0203227); //busted low-  < 10
    }

    public double get(int key) {
        return handTypes.get(key);
    }
}
