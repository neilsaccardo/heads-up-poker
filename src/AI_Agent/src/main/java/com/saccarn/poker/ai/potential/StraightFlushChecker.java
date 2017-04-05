package com.saccarn.poker.ai.potential;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Neil on 05/04/2017.
 */
public class StraightFlushChecker {

    private String holeCard1;
    private String holeCard2;
    private String [] boardCards;
    private String [] allCards;
    private Map<Character, Integer> cardValues = new HashMap<>();

    private final int SUIT_CHAR_POSITION = 1;
    private final int VALUE_CHAR_POSITION = 0;

    public StraightFlushChecker(String holeCardOne, String holeCardTwo, String[] boardCards0) {
        holeCard1 = holeCardOne;
        holeCard2 = holeCardTwo;
        boardCards = boardCards0;
        allCards = new String [boardCards.length+2];
        fillAllCards();
        fillCardValues();
    }

    private void fillCardValues() {
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

    private void fillAllCards() {
        allCards[0] = holeCard1;
        allCards[1] = holeCard2;
        int startPositionAllCards = 2;
        for (int i = startPositionAllCards; i < allCards.length; i++) {
            allCards[i] = boardCards[startPositionAllCards - i];
        }
    }

    public boolean isStraightFlushOn() {
        //check straight
        // check if straight is flush.

        boolean [] straightValues = new boolean [13];
        for (String card : allCards) {
            straightValues [cardValues.get(card.charAt(0))] = true;
        }
        int straightCount = 0;
        if (straightValues[12]) {//handle ace case 'A 2 3 4 5'
            straightCount++;
        }
        for (int i = 0; i < straightValues.length; i++) {
            if (straightValues[i]) {
                straightCount++; //continue on the staright count
            }
            else {
                straightCount = 0; //reset if not present
            }
            if (straightCount == cards.length) { //we've got a straight.
                return true;
            }
        }

        return false;
    }
}
