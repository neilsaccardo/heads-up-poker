package com.saccarn.poker.ai;


import com.saccarn.poker.ai.potential.StraightFlushChecker;

import java.util.Random;

/**
 * Created by Neil on 05/04/2017.
 */
public class HandPotential {

    private final int LUCK_MAX = 5;
    private final int LUCK_MIN = 1;

    private final int TURN_CARD_AMOUNT = 4;
    private final int FLOP_CARD_AMOUNT = 3;

    private double luck; //How much luck do you think we'll have?

    private String holeCardOne;
    private String holeCardTwo;
    private String [] boardCards;


    public HandPotential(String holeCardOne, String holeCardTwo, String boardCard1, String boardCard2, String boardCard3, String boardCard4) {
        this.boardCards = new String [TURN_CARD_AMOUNT];
        this.holeCardOne = holeCardOne;
        this.holeCardTwo = holeCardTwo;
        boardCards[0] = boardCard1;
        boardCards[1] = boardCard2;
        boardCards[2] = boardCard3;
        boardCards[3] = boardCard4;
    }

    public HandPotential(String holeCardOne, String holeCardTwo, String boardCard1, String boardCard2, String boardCard3) {
        this.boardCards = new String [FLOP_CARD_AMOUNT];
        this.holeCardOne = holeCardOne;
        this.holeCardTwo = holeCardTwo;
        boardCards[0] = boardCard1;
        boardCards[1] = boardCard2;
        boardCards[2] = boardCard3;
    }

    public double calculateHandPotential(double probOfCardRanksBetterThanCurrentRank, boolean affectPotential) {
        if (!affectPotential) { //no hand potential influence on belief.
            return probOfCardRanksBetterThanCurrentRank;
        }

        luck = generateLuckValue();
        double potentialProb = isStraightFlushOn();
        if (probOfCardRanksBetterThanCurrentRank - potentialProb < 0.0) {
            return 0.0;
        }
        else {
            return probOfCardRanksBetterThanCurrentRank - potentialProb;
        }
    }


    private double isStraightFlushOn() {
        StraightFlushChecker sfc = new StraightFlushChecker(holeCardOne, holeCardTwo, boardCards);
        int numStraightOn = sfc.numStraightOn();
        double probStraightHappening = numStraightOn * luck;
        int numFlushOn = sfc.numFlushOn();
        double probFlushHappening = numFlushOn * luck;
        return probFlushHappening + probStraightHappening;
    }

    private double generateLuckValue() {
        double randomDouble = new Random().nextDouble();
        return ((randomDouble * (LUCK_MAX-LUCK_MIN)) + LUCK_MIN) / 100;
    }
}
