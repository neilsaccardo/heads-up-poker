package com.saccarn.poker.ai.potential;

import java.util.*;

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
    private final int NUM_CARDS_FLOP = 3;
    private final int NUM_CARDS_TURN = 4;
    private final int NUM_CARDS_RIVER = 5;

    public StraightFlushChecker(String holeCardOne, String holeCardTwo, String[] boardCards0) {
        holeCard1 = holeCardOne;
        holeCard2 = holeCardTwo;
        boardCards = boardCards0;
        allCards = new String [boardCards.length+2];
        fillAllCards();
        fillCardValues();
        fillSuitValues();
    }

    private Map<Character, Integer> fillSuitValues() {
        Map<Character, Integer> suitValues = new HashMap<>();
        suitValues.put('h', 0);
        suitValues.put('d', 0);
        suitValues.put('c', 0);
        suitValues.put('s', 0);
        return suitValues;
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
            allCards[i] = boardCards[i - startPositionAllCards];
        }
    }

    public int numStraightOrFlushOn() {
        return numFlushOn() + numStraightOn();
    }

    public int numStraightOn() {
        //check straight
        List<Boolean> straightValues = new ArrayList<>(13);
        for (int i = 0 ; i < 13; i++) {
            straightValues.add(false);
        }
        for (String card : allCards) {
            straightValues.set(cardValues.get(card.charAt(VALUE_CHAR_POSITION)), true);
        }
        int straightCount;
        if (boardCards.length == NUM_CARDS_TURN) {
            int numStraightOnOneMissing = numStraightOnOneMissing(straightValues);
            straightCount = numStraightOnOneMissing;
        }
        else if (boardCards.length == NUM_CARDS_FLOP) {
            int numStraightOnTwoMissing = numStraightOnTwoMissing(straightValues);
            int numStraightOnOneMissing = numStraightOnOneMissing(straightValues);
            straightCount = numStraightOnOneMissing + numStraightOnTwoMissing;
        }
        else {
            straightCount = 0;
        }
        return straightCount;
    }

    public int numFlushOn() {
        Map<Character, Integer> suitValues = fillSuitValues();
        for (String card : allCards) {
            if (suitValues.get(card.charAt(SUIT_CHAR_POSITION)) != null) {
                suitValues.put(card.charAt(SUIT_CHAR_POSITION), suitValues.get(card.charAt(SUIT_CHAR_POSITION))+1);
            }
        }
        int flushCount = 0;
        if (boardCards.length == NUM_CARDS_TURN) {
            int numFlushOnOneMissing = numFlushOnOneMissing(suitValues);
            flushCount = numFlushOnOneMissing;
        }
        else if (boardCards.length == NUM_CARDS_FLOP) {
            int numFlushOnTwoMissing = numFlushOnTwoMissing(suitValues);
            int numFlushOnOneMissing = numFlushOnOneMissing(suitValues);
            flushCount = numFlushOnOneMissing + numFlushOnTwoMissing;
        }
        else {
            flushCount = 0;
        }
        return flushCount;
    }

    private int numFlushOnTwoMissing(Map<Character, Integer> flushMap) {
        for (Character suitChar : flushMap.keySet()) {
            if (flushMap.get(suitChar) != null && flushMap.get(suitChar) == 3) {
                return 1;
            }
        }
        return 0;
    }

    private int numFlushOnOneMissing(Map<Character, Integer> flushMap) {
        for (Character suitChar : flushMap.keySet()) {
            if (flushMap.get(suitChar) != null && flushMap.get(suitChar) == 4) {
                return 1;
            }
        }
        return 0;
    }

    private int numStraightOnOneMissing(List<Boolean> straightValues) {
        int count = 0;
        List<Boolean> aceLowStraightList = getAceLowStraightList(straightValues);

        List<Boolean> oneMissing1 = Arrays.asList(new Boolean[]{false, true, true, true, true});
        List<Boolean> oneMissing2 = Arrays.asList(new Boolean[]{true, false, true, true, true});
        List<Boolean> oneMissing3 = Arrays.asList(new Boolean[]{true, true, false, true, true});
        List<Boolean> oneMissing4 = Arrays.asList(new Boolean[]{true, true, true, false, true});
        List<Boolean> oneMissing5 = Arrays.asList(new Boolean[]{true, true, true, true, false});

        List<List<Boolean>> patterns = Arrays.asList(oneMissing1, oneMissing2, oneMissing3, oneMissing4, oneMissing5);
        for (int i = 0; i < patterns.size(); i++) {
            if (Collections.indexOfSubList(straightValues, patterns.get(i)) != -1) {
                count++;
            }
            if (patterns.get(i).equals(aceLowStraightList)) {
                count++;
            }
        }
        return count;
    }

    private List<Boolean> getAceLowStraightList(List<Boolean> straightValues) {
        List<Boolean> lowList = new ArrayList<>();
        lowList.add(straightValues.get(12));
        for (int i = 0; i < 4; i++) {
            lowList.add(straightValues.get(i));
        }
        return lowList;
    }

    private int numStraightOnTwoMissing(List<Boolean> straightValues) {
        List<Boolean> aceLowStraightList = getAceLowStraightList(straightValues);

        List<Boolean> twoMissing1 = Arrays.asList(new Boolean[]{false, false, true, true, true});
        List<Boolean> twoMissing2 = Arrays.asList(new Boolean[]{true, true, true, false, false});
        List<Boolean> twoMissing3 = Arrays.asList(new Boolean[]{false, true, true, true, false});
        List<Boolean> twoMissing4 = Arrays.asList(new Boolean[]{true, false, true, false, true});
        List<Boolean> twoMissing5 = Arrays.asList(new Boolean[]{true, true, false, true, false});
        List<Boolean> twoMissing6 = Arrays.asList(new Boolean[]{false, true, true, false, true});
        List<Boolean> twoMissing7 = Arrays.asList(new Boolean[]{true, false, true, true, false});
        List<Boolean> twoMissing8 = Arrays.asList(new Boolean[]{false, true, true, true, false});
        List<Boolean> twoMissing9 = Arrays.asList(new Boolean[]{true, false, false, true, true});
        List<Boolean> twoMissing10 = Arrays.asList(new Boolean[]{true, true, false, false, true});
        List<List<Boolean>> patterns = Arrays.asList(twoMissing1, twoMissing2, twoMissing3, twoMissing4, twoMissing5
                , twoMissing6, twoMissing7, twoMissing8, twoMissing9, twoMissing10);
        int count = 0;
        for (int i = 0; i < patterns.size(); i++) {
            if (Collections.indexOfSubList(straightValues, patterns.get(i)) != -1) {
                count++;
            }
            if (patterns.get(i).equals(aceLowStraightList)) {
                count++;
            }
        }
        return count;
    }
}
