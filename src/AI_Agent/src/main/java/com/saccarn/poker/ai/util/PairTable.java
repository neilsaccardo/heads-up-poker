package com.saccarn.poker.ai.util;

import java.io.CharConversionException;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Neil on 18/04/2017.
 */
public class PairTable {

    private HashMap<Character, Integer> pairTable = new HashMap<>();

    public PairTable() {
        pairTable.put('2', 19);
        pairTable.put('3', 18);
        pairTable.put('4', 17);
        pairTable.put('5', 16);
        pairTable.put('6', 15);
        pairTable.put('7', 14);
        pairTable.put('8', 13);
        pairTable.put('9', 12);
        pairTable.put('T', 11);
        pairTable.put('J', 10);
        pairTable.put('Q', 9);
        pairTable.put('K', 8);
        pairTable.put('A', 7);
    }

    public int get(char key) {
        return pairTable.get(key);
    }

    public Set<Character> keySet() {
        return pairTable.keySet();
    }
}
