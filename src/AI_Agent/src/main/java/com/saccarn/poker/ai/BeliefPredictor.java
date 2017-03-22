package com.saccarn.poker.ai;

import com.saccarn.poker.dbprocessor.DataLoaderStrings;

import java.util.Map;

/**
 * Created by Neil on 22/03/2017.
 */
public class BeliefPredictor {  //gonna need hole cards, board cards, round, opponent model, hand so far.

    private String holeCardOne;
    private String holeCardTwo;
    private String [] boardCards;
    private final Map<String, Double> opponentModel;
    private int round;


    private static final double TOTAL = 1.0;
    private static final double ALPHA = 0.5;

    public BeliefPredictor(String holeCard1, String holeCard2, String[] boardCards, Map<String, Double> opponentModel, int round) {
        this.holeCardOne = holeCard1;
        this.holeCardTwo = holeCard2;
        this.boardCards = boardCards;
        this.opponentModel = opponentModel;
        this.round = round;
    }

    public double calculateBeliefInWinning() {
        double probabilityOfLosingAtShowdown = calculateProbabilityOfLossAtShowdown();
        double doubt = calculateDoubt();
        return BeliefPredictor.TOTAL - probabilityOfLosingAtShowdown - doubt;
    }

    private double calculateDoubt() {
        double roundDoubt = checkFoldPercentagesPerRound();
        double distanceFromAverageBettingPattern = calculateDistanceFromAverageBettingPattern();
        return roundDoubt + distanceFromAverageBettingPattern;
    }

    private double calculateDistanceFromAverageBettingPattern() {

        return 0.0;
    }

    private double checkFoldPercentagesPerRound() {
        double roundDoubt = 0;
        if (round == 0) {
            roundDoubt = opponentModel.get(DataLoaderStrings.FOLDED_AT_PRE_FLOP);
        }
        if (round == 1) {
            roundDoubt = opponentModel.get(DataLoaderStrings.FOLDED_AT_PRE_FLOP)
                            + opponentModel.get(DataLoaderStrings.FOLDED_AT_FLOP);
        }
        if (round == 2) {
            roundDoubt = opponentModel.get(DataLoaderStrings.FOLDED_AT_PRE_FLOP)
                            + opponentModel.get(DataLoaderStrings.FOLDED_AT_FLOP)
                            + opponentModel.get(DataLoaderStrings.FOLDED_AT_TURN);
        }
        return (roundDoubt * BeliefPredictor.ALPHA*(round+1));
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
