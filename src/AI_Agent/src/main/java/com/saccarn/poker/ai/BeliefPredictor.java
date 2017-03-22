package com.saccarn.poker.ai;

import java.util.Map;

/**
 * Created by Neil on 22/03/2017.
 */
public class BeliefPredictor { //gonna need hole cards, board cards, round, opponent model, hand so far.

    private String holeCardOne;
    private String holeCardTwo;
    private String [] boardCards;

    private Map<String, Double> opponentModel;
    private Map<String, Double> handSoFar;

    public BeliefPredictor() {}

    public double calculateBeliefInWinning() {
        double probabilityOfLosingAtShowdown = calculateProbabilityOfLossAtShowdown();
        double opponentProb = calculateOpponent();
        return 1.0 - probabilityOfLosingAtShowdown;
    }

    private double calculateOpponent() {


        return 0.0;
    }

    private double calculateProbabilityOfLossAtShowdown() {
        int rank;
        HandType ht = new HandType();
        if (boardCards.length == 3) {
            rank = ht.calculateHandRanking(holeCardOne, holeCardTwo, boardCards[0], boardCards[1],
                                                        boardCards[2]);
        }
        if (boardCards.length == 4) {
            rank = ht.calculateHandRanking(holeCardOne, holeCardTwo, boardCards[0], boardCards[1],
                                                        boardCards[2], boardCards[3]);
        }
        else { // boardCards.length == 5
            rank = ht.calculateHandRanking(holeCardOne, holeCardTwo, boardCards[0],
                                                        boardCards[1], boardCards[2], boardCards[3], boardCards[4]);
        }
        return ht.calculateProbOfCardRanksBetterThan(rank);
    }

}
