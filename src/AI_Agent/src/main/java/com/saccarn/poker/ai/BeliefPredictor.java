package com.saccarn.poker.ai;

import com.saccarn.poker.ai.commonhand.CommonHand;
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

    private boolean checkCommonCards;

    private final int FLOP = 3;
    private final int TURN = 4;
    private final int RIVER = 5;
    private static final double TOTAL = 1.0;
    private static final double ALPHA = 0.5;
    private boolean affectPotential;

    public BeliefPredictor(String holeCard1, String holeCard2, String[] boardCards, Map<String, Double> opponentModel, int round, boolean checkCommonCards, boolean affectPotential) {
        this.holeCardOne = holeCard1;
        this.holeCardTwo = holeCard2;
        this.boardCards = boardCards;
        this.opponentModel = opponentModel;
        this.round = round;
        this.checkCommonCards = checkCommonCards;
        this.affectPotential = affectPotential;
    }


    public double calculateBeliefInWinning() {
        double probabilityOfLosingAtShowdown = calculateProbabilityOfLossAtShowdown();
        double doubt = calculateDoubt();
        double beliefInWinning = BeliefPredictor.TOTAL - probabilityOfLosingAtShowdown - doubt;
        if (beliefInWinning < 0.0) {
            return 0.01;
        }
        else {
            return beliefInWinning;
        }

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
            roundDoubt = opponentModel.get(DataLoaderStrings.PRE_FLOP_FOLDED_RATIO);
        }
        if (round == 1) {
            roundDoubt = opponentModel.get(DataLoaderStrings.PRE_FLOP_FOLDED_RATIO)
                            + opponentModel.get(DataLoaderStrings.FLOP_FOLDED_RATIO);
        }
        if (round == 2) {
            roundDoubt = opponentModel.get(DataLoaderStrings.PRE_FLOP_FOLDED_RATIO)
                            + opponentModel.get(DataLoaderStrings.FLOP_FOLDED_RATIO)
                            + opponentModel.get(DataLoaderStrings.TURN_FOLDED_RATIO);
        }
        System.out.println("The round doubt for " + round + " is: " + (roundDoubt * BeliefPredictor.ALPHA));
        return (roundDoubt * BeliefPredictor.ALPHA);
    }

    private double calculateProbabilityOfLossAtShowdown() {
        int rank;
        double probOfWinning;
        double probOfCardRanksBetterThanCurrentRank;
        HandType ht = new HandType();
        if (boardCards.length == FLOP) {
            rank = ht.calculateHandRanking(holeCardOne, holeCardTwo, boardCards[0], boardCards[1],
                                                        boardCards[2]);
            HandPotential hp = new HandPotential(holeCardOne, holeCardTwo, boardCards[0], boardCards[1],
                    boardCards[2]);
            CommonHand ch = new CommonHand(rank, boardCards);
            probOfCardRanksBetterThanCurrentRank = ht.calculateProbOfCardRanksBetterThan(rank);
            probOfCardRanksBetterThanCurrentRank = modifyBeliefIfBestCardsAreCommunal(ch, probOfCardRanksBetterThanCurrentRank, FLOP);
            probOfWinning = hp.calculateHandPotential(probOfCardRanksBetterThanCurrentRank, affectPotential);
        }
        else if (boardCards.length == TURN) {
            rank = ht.calculateHandRanking(holeCardOne, holeCardTwo, boardCards[0], boardCards[1],
                                                        boardCards[2], boardCards[3]);
            probOfCardRanksBetterThanCurrentRank = ht.calculateProbOfCardRanksBetterThan(rank);
            CommonHand ch = new CommonHand(rank, boardCards);
            HandPotential hp = new HandPotential(holeCardOne, holeCardTwo, boardCards[0], boardCards[1],
                    boardCards[2], boardCards[3]);
            probOfCardRanksBetterThanCurrentRank = modifyBeliefIfBestCardsAreCommunal(ch, probOfCardRanksBetterThanCurrentRank, TURN);
            probOfWinning = hp.calculateHandPotential(probOfCardRanksBetterThanCurrentRank, affectPotential);
        }
        else { // boardCards.length == 5
            rank = ht.calculateHandRanking(holeCardOne, holeCardTwo, boardCards[0],
                                                        boardCards[1], boardCards[2], boardCards[3], boardCards[4]);
            probOfCardRanksBetterThanCurrentRank = ht.calculateProbOfCardRanksBetterThan(rank);
            CommonHand ch = new CommonHand(rank, boardCards);
            probOfCardRanksBetterThanCurrentRank = modifyBeliefIfBestCardsAreCommunal(ch, probOfCardRanksBetterThanCurrentRank, RIVER);
            probOfWinning = probOfCardRanksBetterThanCurrentRank; // no potential on the river.
        }
        System.out.println("rank:           " + rank);
        System.out.println("Probability of Losing at showdown: " + probOfWinning);
        return probOfWinning;
    }

    private double modifyBeliefIfBestCardsAreCommunal(CommonHand ch, double probOfCardRanksBetterThanCurrentRank, int round) {
        if (checkCommonCards && ch.isCommonHand()) {
            double moddedProb = probOfCardRanksBetterThanCurrentRank * (double)round;
            return (moddedProb > 0.99) ? 0.99 : moddedProb;
        }
        else {
            return probOfCardRanksBetterThanCurrentRank;
        }
    }

}
