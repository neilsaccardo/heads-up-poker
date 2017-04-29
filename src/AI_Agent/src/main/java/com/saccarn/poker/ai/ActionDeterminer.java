package com.saccarn.poker.ai;

import com.saccarn.poker.ai.betpassdeterminer.BetPassActionValues;
import com.saccarn.poker.ai.betpassdeterminer.BetPassDeterminer;

import java.util.Map;
import java.util.Random;

/**
 *  The ActionDeterminer implements the betting curves in order to output an action.
 *  @author Neil
 *  Created by Neil on 22/03/2017.
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
    private final BetPassActionValues bpv;

    private boolean checkCommonCards;
    private boolean affectPotential;

    private Random randomGenerator = new Random();

    /**
     * Sole constructor of ActionDeterminer class, takes in inputs to determine action to take.
     *
     * @param holeCard1 First Hole Card
     * @param holeCard2 Second Hole Card
     * @param boardCards Board Cards, if there are any
     * @param stackSize Size of the stack currently
     * @param opponentStackSize Size of the opponent Stack currently
     * @param potSize Size of the pot currently
     * @param numChipsBet Number of chips bet in previous action
     * @param minBet The minimum bet of the hand
     * @param playerCluster The opponent model
     * @param round Round of play represented as a int - flop, turn, river
     * @param bpv The BetPassValues specifed used to determine betting action in betting curve
     * @param checkCommonCards If true, include CommonHand functionality to affect belief, if false do not.
     * @param affectPotential If true, include HandPotential functionality to affect belief, if false, do not.
     */

    public ActionDeterminer(String holeCard1, String holeCard2, String[] boardCards, int stackSize, int opponentStackSize,
                            int potSize, int numChipsBet, int minBet, Map<String, Double> playerCluster, int round,
                            BetPassActionValues bpv, boolean checkCommonCards, boolean affectPotential) {
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
        this.bpv = bpv;
        this.checkCommonCards = checkCommonCards;
        this.affectPotential = affectPotential;
    }

    /**
     * This methods determines the action the Agent should take.
     *
     * <p>This method makes heavy use of {@link BeliefPredictor} and {@link ActionProbability} classes. Uses the betting
     *    functions to determine action to carry out.</p>
     * @return An action to carry out.
     */

    public String getAction() {
        BeliefPredictor bp = new BeliefPredictor(holeCard1, holeCard2, boardCards, playerCluster, round, checkCommonCards, affectPotential);
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

    /**
     * Returns an action based on the passProbability and random number passed.
     *
     * <p>
     *     This method uses the betting function to determine an action.
     * </p>
     * @param passProbability the passProbability obtained from the check/Call/Bet Function
     * @param randomDouble a randomly generated number, should be between 0 and 1.0
     * @return an action to carry out
     */
    public String determinePassOrBetAction(double passProbability, double randomDouble) {
        BetPassDeterminer bpd = new BetPassDeterminer(randomDouble, passProbability);
        double passBetActionDouble = bpd.calculateBetFunction();
        System.out.println("PASS OR BET: Random= " + randomDouble + " PassProb= " + passProbability + " action value=" + passBetActionDouble);
        if (passBetActionDouble < bpv.getPassConst()) {
            System.out.println("< PASS_CONST");
            return ActionStrings.ACTION_PASS;
        }
        else if (passBetActionDouble > bpv.getAllinConst()&& passProbability > 0.8) {
            System.out.println("> ALL_IN");
            return ActionStrings.ACTION_ALLIN;
        }
        else if (passBetActionDouble > bpv.getBet3Const()) {
            System.out.println("> BET3");
            return ActionStrings.ACTION_BET3;
        }
        else if (passBetActionDouble < bpv.getBet2Const()) {
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

    /**
     * Returns boolean as to whether the action should be fold.
     *  <p>This method makes use of the fold probability value and the random value
     *  passed to determine whether to fold or not.</p>
     * @param foldProb The Fold probability determined by fold/check/call function
     * @param random Random number between 0 and 1.0
     * @return true if should fold, false if not.
     */
    public boolean determineShouldFold(double foldProb, double random) {
        System.out.println("Determine should fold : Fold Prob= " + foldProb + ". Random = " + random);
        return foldProb > random;
    }
}
