package com.saccarn.poker.ai.util;

import java.util.HashMap;

/**
 * Created by Neil on 18/04/2017.
 */
public class CardValueTable {

    private HashMap<Character, Integer> cardValues = new HashMap<>();

    public CardValueTable() {
        cardValues.put('2', 0); //0
        cardValues.put('3', 1); //1
        cardValues.put('4', 2); //2
        cardValues.put('5', 3); //3
        cardValues.put('6', 4); //4
        cardValues.put('7', 5);  //5
        cardValues.put('8', 6);  //6
        cardValues.put('9', 7);  //7
        cardValues.put('T', 8);  //8
        cardValues.put('J', 9);  //9
        cardValues.put('Q', 10);  //10
        cardValues.put('K', 11);  //11
        cardValues.put('A', 12); //12
    }

    public int get(char key) {
        return cardValues.get(key);
    }
}
