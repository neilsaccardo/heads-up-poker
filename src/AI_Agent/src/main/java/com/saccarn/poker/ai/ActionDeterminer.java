package com.saccarn.poker.ai;

import com.saccarn.poker.ai.betpassdeterminer.BetPassActionValues;
import com.saccarn.poker.ai.betpassdeterminer.BetPassDeterminer;

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
        BetPassDeterminer bpd = new BetPassDeterminer(randomDouble, passProbability);
        double passBetActionDouble = bpd.calculateBetFunction();
        System.out.println("PASS OR BET: Random= " + randomDouble + " PassProb= " + passProbability + " action value=" + passBetActionDouble);
        if (passBetActionDouble < BetPassActionValues.PASS_CONST) {
            System.out.println("< PASS_CONST");
            return ActionStrings.ACTION_PASS;
        }
        else if (passBetActionDouble > BetPassActionValues.ALLIN_CONST && passProbability > 0.8) {
            System.out.println("> ALL_IN");
            return ActionStrings.ACTION_ALLIN;
        }
        else if (passBetActionDouble > BetPassActionValues.BET3_CONST) {
            System.out.println("> BET3");
            return ActionStrings.ACTION_BET3;
        }
        else if (passBetActionDouble < BetPassActionValues.BET2_CONST) {
            System.out.println("> BET2");
            return ActionStrings.ACTION_BET2;
        }
        else {      // < BetPassActionValues.ALLIN_CONST
            System.out.println("> BET1");
            return ActionStrings.ACTION_BET1;
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
