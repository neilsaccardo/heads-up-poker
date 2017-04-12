package com.saccarn.poker.ai;

import java.util.Map;
import java.util.Random;

/**
 * Created by Neil on 22/03/2017.
 */
public class ActionDeterminer {

    private final String holeCard1;
    private final String holeCard2;
    private final String[] boardCards;
    private final int stackSize;
    private final int opponentStackSize;
    private final int potSize;
    private final int numChipsBet;
    private final int minBet;
    private final Map<String, Double> playerCluster;
    private final int round;

    private Random randomGenerator = new Random();

    private final static double TOTAL = 1.0;
    private final static double BET1_CONST = 2;
    private final static double BET2_CONST = 3;
    private final static double BET3_CONST = 4;


    public ActionDeterminer(String holeCard1, String holeCard2, String[] boardCards, int stackSize, int opponentStackSize, int potSize, int numChipsBet, int minBet, Map<String, Double> playerCluster, int round) {
        this.holeCard1 = holeCard1;
        this.holeCard2 = holeCard2;
        this.boardCards = boardCards;
        this.stackSize = stackSize;
        this.opponentStackSize = opponentStackSize;
        this.potSize = potSize;
        this.numChipsBet = numChipsBet;
        this.minBet = minBet;
        this.playerCluster = playerCluster;
        this.round = round;
    }

    public String getAction() {
        BeliefPredictor bp = new BeliefPredictor(holeCard1, holeCard2, boardCards, playerCluster, round);
        double belief = bp.calculateBeliefInWinning();
        PotPredictor potPredictor = new PotPredictor(minBet, stackSize, opponentStackSize, potSize, round, numChipsBet);
        ActionProbability ap = new ActionProbability(potPredictor, belief);
        double foldProb = ap.getFoldProbability();

        if (determineShouldFold(foldProb)) {
            return ActionStrings.ACTION_FOLD;
        }
        else {
            double passProbability = ap.getPassProbability();
            return determinePassOrBetAction(passProbability);
        }
    }

    private String determinePassOrBetAction(double passProbability) {
        return determinePassOrBetAction(passProbability, randomGenerator.nextDouble());
    }

    public String determinePassOrBetAction(double passProbability, double randomDouble) {
        if (randomDouble < passProbability) {
            return ActionStrings.ACTION_PASS;
        }
        if (randomDouble < (passProbability * ActionDeterminer.BET1_CONST)) {
            return ActionStrings.ACTION_BET1;
        }
        if (randomDouble < (passProbability * ActionDeterminer.BET2_CONST)) {
            return ActionStrings.ACTION_BET2;
        }
        if (randomDouble < (passProbability * ActionDeterminer.BET3_CONST)) {
            return ActionStrings.ACTION_BET3;
        }
        if (randomDouble > 0.96) {
            return ActionStrings.ACTION_ALLIN;
        }
        else {
            return ActionStrings.ACTION_BET2;
        }
    }

    private boolean determineShouldFold(double foldProb) {
        return determineShouldFold(foldProb, randomGenerator.nextDouble());
    }

    public boolean determineShouldFold(double foldProb, double random) {
        System.out.println("Determine should fold : Fold Prob= " + foldProb + ". Random = " + random);
        return foldProb > random;
    }
}
