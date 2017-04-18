package com.saccarn.poker.ai.commonhand;

import com.saccarn.poker.ai.util.CardValueTable;
import com.saccarn.poker.ai.util.PairTable;

import java.util.Set;

/**
 * Created by Neil on 18/04/2017.
 */
public class CommonHand {

    private PairTable pairTable = new PairTable();
    private CardValueTable cardValueTable = new CardValueTable();

    private String [] boardCards;
    private int cardRank;
    
    public CommonHand(int cardRank0, String [] boardCards0) {
        cardRank = cardRank0;
        boardCards = boardCards0;
    }

    public boolean isCommonHand() {
        if (cardRank < 7) {
            return false;
        }
        int pairRankInBoardCards = isPair();
        if (cardRank == pairRankInBoardCards) {
            return true;
        }
        int highCardRankInBoardCards = isHighCard();
        return (cardRank == highCardRankInBoardCards);
    }

    private int isHighCard() {
        if (containsAceHigh()) {
            return 20;
        }
        else if (containsKingHigh()) {
            return 21;
        }
        else if (containsQueenHigh()) {
            return 22;
        }
        else if (containsMedHigh()) {
            return 23;
        } else {
            return 24;
        }

    }

    private boolean containsMedHigh() {
        return isHighCardInCards(boardCards, 'J') ||
                isHighCardInCards(boardCards, 'T');
    }

    private boolean containsQueenHigh() {
        return isHighCardInCards(boardCards, 'Q');
    }

    private boolean containsKingHigh() {
        return isHighCardInCards(boardCards, 'K');
    }

    private boolean containsAceHigh() {
        return isHighCardInCards(boardCards, 'A');
    }

    private int isPair() {
        int pairRank = -1;
        Set<Character> values = pairTable.keySet();
        for (Character c : values) {
            if (isPairInCards(boardCards, c)) {
                pairRank = pairTable.get(c);
                break;
            }
        }
        return pairRank;
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

    private boolean isHighCardInCards(String[] cards, char c) {
        for (int i = 0; i < cards.length; i++) {
            if (cards[i].charAt(0) == c) {
                return true; //its here
            }
        }
        return false;
    }


}
