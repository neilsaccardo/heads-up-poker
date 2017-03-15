package com.saccarn.poker.ai;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Neil on 15/03/2017.
 */
public class HandType {

    private HashMap<Integer, Double> handTypes = new HashMap<>();
    private HashMap<Character, Integer> cardValues = new HashMap<>();

    public HandType() {
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
        handTypes.put(10, 0.0649163); //Medium Pair - 10 J
        handTypes.put(11, 0.2599887); //Pair low - < 10
        handTypes.put(12, 0.193495); // busted ace
        handTypes.put(13, 0.1292463); // busted king
        handTypes.put(14, 0.082212); //busted queen
        handTypes.put(15, 0.07631); //busted med - 10 J
        handTypes.put(16, 0.0203227); //busted low-  < 10
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

    public int calculateHandRanking(String holeCard1, String holeCard2, String boardCard1, String boardCard2, String boardCard3, String boardCard4, String boardCard5) {
        int rank1 = calculateHandRanking(holeCard1, holeCard2, boardCard1, boardCard2, boardCard3);
        int rank2 = calculateHandRanking(holeCard1, holeCard2, boardCard1, boardCard2, boardCard4);
        int rank3 = calculateHandRanking(holeCard1, holeCard2, boardCard1, boardCard2, boardCard5);
        int minRank1 = Math.min(rank1, (Math.min(rank1, rank2)));
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


    public int calculateHandRanking(String holeCard1, String holeCard2, String boardCard1, String boardCard2, String boardCard3) {
        int rank = 0;
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
        else if (isPairAce(cards)) {
            rank = 7;
        }
        else if (isPairKing(cards)) {
            rank = 8;
        }
        else if (isPairQueen(cards)) {
            rank = 9;
        }
        else if (isPairMed(cards)) {
            rank = 10;
        }
        else if (isPairLow(cards)) {
            rank = 11;
        }
        else if (isAceHighCard(cards)) {
            rank = 12;
        }
        else if (isKingHighCard(cards)) {
            rank = 13;
        }
        else if (isQueenHighCard(cards)) {
            rank = 14;
        }
        else if (isMedHighCard(cards)) {
            rank  = 15;
        }
        else { //low card
            rank = 16;
        }
        return rank;
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

    private boolean isPairLow(String[] cards) {
        char [] charValues = {'9', '8', '7', '6', '5', '4', '3', '2'};
        for (int i = 0; i < charValues.length; i++) {
            if (isPairInCards(cards, charValues[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean isPairMed(String[] cards) {
        return isPairInCards(cards, 'J')
                || isPairInCards(cards, 'T');
    }

    private boolean isPairQueen(String[] cards) {
        return isPairInCards(cards, 'Q');
    }

    private boolean isPairKing(String[] cards) {
        return isPairInCards(cards, 'K');
    }

    private boolean isPairAce(String[] cards) {
        return isPairInCards(cards, 'A');
    }

    private boolean isHighCardInCards(String[] cards, char c) {
        int count = 0;
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
