package com.saccarn.poker.ai;

import com.saccarn.poker.ai.util.BucketTable;
import com.saccarn.poker.ai.util.CardValueTable;
import com.saccarn.poker.ai.util.PairTable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * HandType is a class which encapsulates the hand buckets.
 *
 * Created by Neil on 15/03/2017.
 */
public class HandType {

    private BucketTable handTypes = new BucketTable();
    private CardValueTable cardValues = new CardValueTable();
    private PairTable pairTable = new PairTable();

    /**
     * Default no args constructor
     */
    public HandType() { }

    /**
     * Returns probability of opponent having cards that beat the card rank.
     *
     * @param rank rank of cards based on bucket table
     * @return probabilty of opponent having cards that beat the card rank.
     */

    public double calculateProbOfCardRanksBetterThan(int rank) {
        double probability = 0.0;
        for (int i = 0; i < rank; i++) {
            probability = handTypes.get(i) + probability;
        }
        return probability;
    }

    /**
     * Returns rank of 2 card hand.
     *
     * This method takes in two hole cards, and calculates the hand ranking as per the bucket table. Not used in the system.
     * @param card1 hole card one
     * @param card2 hole card 2
     * @return hand ranking as per bucket table
     */
    // inputted 'Js' '4d' 'Ac' 'Th' etc
    public int calculateHandRanking(String card1, String card2) {
        int rank;
        if (card1.length() != 2 || card2.length() != 2 ) {
            throw new IllegalArgumentException();
        }
        if (card1.charAt(0) == card2.charAt(0)) {  //pair
            if (card1.charAt(0) == 'A') {
                rank = 7;
            } else if(card1.charAt(0) == 'K') {
                rank = 8;
            } else if(card1.charAt(0) == 'Q') {
                rank = 9;
            } else if(card1.charAt(0) == 'T' || card1.charAt(0) == 'J') {
                rank = 10;
            } else {
                rank = 11;
            }
        } else { //high cards
            if (card1.charAt(0) == 'A') {
                rank = 12;
            } else if(card1.charAt(0) == 'K') {
                rank = 13;
            } else if(card1.charAt(0) == 'Q') {
                rank = 14;
            } else if(card1.charAt(0) == 'T' || card1.charAt(0) == 'J') {
                rank = 15;
            } else {
                rank = 16;
            }
        }
        return rank;
    }

    /**
     * Returns card rank as per the bucket table
     *
     * Method is for when stage is TURN
     * @param holeCard1 hole card one
     * @param holeCard2 hole card two
     * @param boardCard1 board card one
     * @param boardCard2 board card two
     * @param boardCard3 board card three
     * @param boardCard4 board card four
     * @return rank as per the bucket table
     */

    public int calculateHandRanking(String holeCard1, String holeCard2, String boardCard1,
                                    String boardCard2, String boardCard3, String boardCard4) {

        int rank1 = calculateHandRanking(holeCard1, holeCard2, boardCard1, boardCard2, boardCard3);
        int rank2 = calculateHandRanking(holeCard1, holeCard2, boardCard1, boardCard2, boardCard4);
        int rank3 = calculateHandRanking(holeCard1, holeCard2, boardCard1, boardCard3, boardCard4);
        int rank4 = calculateHandRanking(holeCard1, holeCard2, boardCard2, boardCard3, boardCard4);
        int rank5 = calculateHandRanking(holeCard1, boardCard1, boardCard2, boardCard3, boardCard4);
        int rank6 = calculateHandRanking(holeCard2, boardCard1, boardCard2, boardCard3, boardCard4);

        return Math.min(rank1, Math.min(rank2, Math.min(rank3, Math.min(rank4, Math.min(rank5, rank6)))));
    }

    /**
     * Returns card rank as per the bucket table
     *
     * Method is for when stage is RIVER
     * @param holeCard1 hole card one
     * @param holeCard2 hole card two
     * @param boardCard1 board card one
     * @param boardCard2 board card two
     * @param boardCard3 board card three
     * @param boardCard4 board card four
     * @param boardCard5 board card five
     * @return rank as per the bucket table
     */

    public int calculateHandRanking(String holeCard1, String holeCard2, String boardCard1, String boardCard2, String boardCard3, String boardCard4, String boardCard5) {
        int rank1 = calculateHandRanking(holeCard1, holeCard2, boardCard1, boardCard2, boardCard3);
        int rank2 = calculateHandRanking(holeCard1, holeCard2, boardCard1, boardCard2, boardCard4);
        int rank3 = calculateHandRanking(holeCard1, holeCard2, boardCard1, boardCard2, boardCard5);

        int minRank1 = Math.min(rank1, (Math.min(rank2, rank3)));

        int rank4 = calculateHandRanking(holeCard1, holeCard2, boardCard2, boardCard3, boardCard4);
        int rank5 = calculateHandRanking(holeCard1, holeCard2, boardCard2, boardCard3, boardCard5);
        int rank6 = calculateHandRanking(holeCard1, holeCard2, boardCard2, boardCard4, boardCard5);
        int minRank2 = Math.min(rank4, (Math.min(rank6, rank5)));
        int rank7 = calculateHandRanking(holeCard1, holeCard2, boardCard1, boardCard3, boardCard4);
        int rank8 = calculateHandRanking(holeCard1, holeCard2, boardCard1, boardCard3, boardCard5);
        int rank9 = calculateHandRanking(holeCard1, holeCard2, boardCard1, boardCard4, boardCard5);

        int minRank3 = Math.min(rank7, (Math.min(rank8, rank9)));

        int rank10 = calculateHandRanking(holeCard1, boardCard1, boardCard2, boardCard3, boardCard4);
        int rank11 = calculateHandRanking(holeCard1, boardCard1, boardCard2, boardCard3, boardCard5);
        int rank12 = calculateHandRanking(holeCard1, boardCard1, boardCard2, boardCard4, boardCard5);
        int minRank4 = Math.min(rank10, (Math.min(rank11, rank12)));

        int rank13 = calculateHandRanking(holeCard1, holeCard2, boardCard3, boardCard4, boardCard5);
        int rank14 = calculateHandRanking(holeCard1, holeCard2, boardCard1, boardCard4, boardCard5);
        int rank15 = calculateHandRanking(holeCard1, holeCard2, boardCard1, boardCard3, boardCard5);

        int minRank5 = Math.min(rank13, (Math.min(rank14, rank15)));

        int rank16 = calculateHandRanking(holeCard2, boardCard1, boardCard2, boardCard3, boardCard4);
        int rank17 = calculateHandRanking(holeCard2,  boardCard1, boardCard2, boardCard3, boardCard5);
        int rank18 = calculateHandRanking(holeCard2,  boardCard1, boardCard2, boardCard4, boardCard5);

        int minRank6 = Math.min(rank16, (Math.min(rank17, rank18)));

        int rank19 = calculateHandRanking(boardCard1, boardCard2, boardCard3, boardCard4, boardCard5);
        int rank20 = calculateHandRanking(holeCard2, boardCard2, boardCard3, boardCard4, boardCard5);
        int rank21 = calculateHandRanking(holeCard1, boardCard2, boardCard3, boardCard4, boardCard5);

        int minRank7 = Math.min(rank19, (Math.min(rank20, rank21)));

        return Math.min(minRank1, Math.min(minRank2, Math.min(minRank3,
                Math.min(minRank4, Math.min(minRank5, Math.min(minRank6, minRank7))))));
    }


    /**
     * Returns card rank as per the bucket table
     *
     * Method is for when stage is FLOP
     * @param holeCard1 hole card one
     * @param holeCard2 hole card two
     * @param boardCard1 board card one
     * @param boardCard2 board card two
     * @param boardCard3 board card three
     * @return rank as per the bucket table
     *
     */

    public int calculateHandRanking(String holeCard1, String holeCard2, String boardCard1, String boardCard2, String boardCard3) {
        int rank;
        String [] cards = {holeCard1, holeCard2, boardCard3, boardCard2, boardCard1};
        if (isFlush(cards)) {
            if (isStraight(cards)) {
                rank = 0; //Flush Straight!
            }
            else {
                rank = 3; //Standard Flush
            }
        }
        else if(isStraight(cards)) {
            rank = 4;
        }
        else if (isFourOfAKind(cards)) {
            rank = 1;
        } else if (isFullHouse(cards)) {
            rank = 2;
        }
        else if (isThreeOfAKind(cards)) {
            rank = 5;
        }
        else if (isTwoPair(cards)) {
            rank = 6;
        }
        else if (isPair(cards) != -1) {
            rank = isPair(cards);
        }
        else if (isAceHighCard(cards)) {
            rank = 20;
        }
        else if (isKingHighCard(cards)) {
            rank = 21;
        }
        else if (isQueenHighCard(cards)) {
            rank = 22;
        }
        else if (isMedHighCard(cards)) {
            rank  = 23;
        }
        else { //low card
            rank = 24;
        }
        return rank;
    }

    /*
     * Returns -1 if no pair is found
     * Returns pair rank from pairTable if found. Not guaranteed to return highest or lowest pair.
     */
    private int isPair(String[] cards) {
        int pairRank = -1;
        Set<Character> values = pairTable.keySet();
        for (Character c : values) {
            if (isPairInCards(cards, c)) {
                pairRank = pairTable.get(c);
                break;
            }
        }
        return pairRank;
    }

    private boolean isFlush(String[] cards) {
        int count = 0;
        char suit = cards[0].charAt(1);
        for (int i = 0; i < cards.length; i++) {
            if (cards[i].charAt(1) == suit) {
                count++;
            }
        }
        return count == cards.length;
    }

    private boolean isMedHighCard(String[] cards) {
        return  isHighCardInCards(cards, 'J')
                || isHighCardInCards(cards, 'T');
    }

    private boolean isQueenHighCard(String[] cards) {
        return isHighCardInCards(cards, 'Q');
    }

    private boolean isKingHighCard(String[] cards) {
        return isHighCardInCards(cards, 'K');
    }

    private boolean isAceHighCard(String[] cards) {
        return isHighCardInCards(cards, 'A');
    }

    private boolean isHighCardInCards(String[] cards, char c) {
        for (int i = 0; i < cards.length; i++) {
            if (cards[i].charAt(0) == c) {
                return true; //its here
            }
        }
        return false;
    }


    private boolean isPairInCards(String[] cards, char c) {
        int count = 0;
        for (int i = 0; i < cards.length; i++) {
            if (cards[i].charAt(0) == c) {
                count++;
            }
        }
        return count == 2;
    }


    private boolean isTwoPair(String[] cards) {
        Map<Character, Integer> twoPairCounts = fillMapCardValueOccurrences(cards);
        int pairCount = 0;
        for (Character c : twoPairCounts.keySet()) {
            if (twoPairCounts.get(c) == 2) { //two occurances for this value
                pairCount++;
            }
        }
        return pairCount == 2; //two pairs
    }


    private Map<Character, Integer> fillMapCardValueOccurrences(String [] cards) {
        Map<Character, Integer> counts = new HashMap<>();
        for (int i = 0; i < cards.length; i++) {
            if (counts.get(cards[i].charAt(0)) == null) {
                counts.put(cards[i].charAt(0), 1);
            }
            else {
                counts.put(cards[i].charAt(0), counts.get(cards[i].charAt(0))+1);
            }
        }
        return counts;
    }

    private boolean isThreeOfAKind(String[] cards) {
        Map<Character, Integer> threeOfAKindCounts = fillMapCardValueOccurrences(cards);
        for (Character c : threeOfAKindCounts.keySet()) {
            if (threeOfAKindCounts.get(c) == 3) {
                return true;
            }
        }
        return false;
    }

    private boolean isFullHouse(String[] cards) {
        Map<Character, Integer> counts = fillMapCardValueOccurrences(cards);
        boolean threeOfAKind = false;
        boolean pair = false;
        for (Character c : counts.keySet()) {
            if (counts.get(c) == 3) {
                threeOfAKind = true;
            }
            if (counts.get(c) == 2) {
                pair = true;
            }
        }
        return threeOfAKind && pair;
    }

    private boolean isFourOfAKind(String[] cards) {
        Map<Character, Integer> counts = fillMapCardValueOccurrences(cards);
        for(Character c : counts.keySet()) {
            if (counts.get(c) == 4) {
                return true;
            }
        }
        return false;
    }

    private boolean isStraight(String[] cards) {
        boolean [] straightValues = new boolean [13];
        for (String card : cards) {
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
