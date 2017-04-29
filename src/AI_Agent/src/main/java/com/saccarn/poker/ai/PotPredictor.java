package com.saccarn.poker.ai;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Neil on 15/03/2017.
 */
public class PotPredictor {

    private double alphaBet = 2.145; //alpha'
    private double alphaPass = 1.38; //alpha

    private int finalPotBet;
    private int finalPotPass;
    private int futureContributionBet;
    private int futureContributionPass;

    private int betSize = 0;

    private Map<Integer, Integer> mappedRoundsLeft = new HashMap<>();
    private int round;

    /**
     * Default no args constructor. If using this, need to use calculatePotAndFutureContribution
     * with all args to get results
     *
     */
    public PotPredictor(){
        fillRoundTable();
    }

    /**
     * Full args constructor
     * @param minBet the minimum bet of the hand
     * @param stackSize stack size
     * @param opponentStackSize opponent stack size
     * @param potSize the size of the pot currently
     * @param round0 the round of play - FLOP TURN RIVER
     * @param numChipsBet the number of chips bet in previous action.
     */
    public PotPredictor(int minBet, int stackSize, int opponentStackSize, int potSize, int round0, int numChipsBet) {
        fillRoundTable();
        calculatePotAndFutureContribution(minBet, stackSize, opponentStackSize, potSize, round0, numChipsBet);
    }

    private void fillRoundTable() {
        mappedRoundsLeft.put(0, 2); // flop
        mappedRoundsLeft.put(1, 1); // turn
        mappedRoundsLeft.put(2, 0); // river
    }

    /**
     * Returns the round of play
     * @return round of play - FLOP TURN RIVER
     */
    public int getRound() {
        return round;
    }

    /**
     * Returns the final value of final pot if the next is action is BET
     * @return final value of pot if action is BET
     */
    public int getFinalPotBet() {
        return finalPotBet;
    }

    /**
     * Returns the value of final pot if the next is action is CHECK OR CALL
     * @return final value of pot if action is CHECK or CALL
     */
    public int getFinalPotPass() {
        return finalPotPass;
    }

    /**
     * Returns the value of future contribution if the next is action is BET
     * @return final value of future contribution if action is BeT
     */
    public int getFutureContributionBet() {
        return futureContributionBet;
    }

    /**
     * Returns the value of future contribution if the next is action is CHECK or CALl
     * @return final value of future contribution if action is Check or call
     */
    public int getFutureContributionPass() {
        return futureContributionPass;
    }

    /**
     * Returns the bet size used - 3/4 of current pot.
     *  @return bet size used when calculating future contr. and final pot
     */
    public int getBetSize() {
        return betSize;
    }

    /**
     * Method with all inputs used if no args constructor is used.
     *
     * @param minBet the minimum bet of the hand
     * @param stackSize stack size
     * @param opponentStackSize opponent stack size
     * @param potSize the size of the pot currently
     * @param round0 the round of play - FLOP TURN RIVER
     * @param numChipsBet the number of chips bet in previous action.
     */

    public void calculatePotAndFutureContribution(int minBet, int stackSize, int opponentStackSize, int potSize, int round0, int numChipsBet) {
        round = round0;
        betSize = determineBetSize(minBet, stackSize, potSize);

        futureContributionBet = calculateFinalContributionsPotBet(round, numChipsBet);
        finalPotBet =  futureContributionBet  + potSize
                       + calculateOpponentFutureContributionsBet(round);
        futureContributionPass = calculateFinalContributionsPass(round, numChipsBet);
        finalPotPass = futureContributionPass + potSize
                + calculateOpponentFutureContributionsPass(round);
    }

    private int calculateOpponentFutureContributionsPass(int round) {
        int roundsLeft = mappedRoundsLeft.get(round);
        double b = ((betSize * roundsLeft) * alphaPass);
        return (int)(Math.round(b));
    }

    private int calculateOpponentFutureContributionsBet(int round) {
        int roundsLeft = mappedRoundsLeft.get(round);
        double b = betSize + ((betSize * roundsLeft) * alphaBet); //delta in this case becomes betSize
        return (int)(Math.round(b));
    }

    private int calculateFinalContributionsPotBet(int round, int numChips) {
        int roundsLeft = mappedRoundsLeft.get(round);
        double b = numChips + betSize + ((betSize * roundsLeft) * alphaBet);
        return (int)(Math.round(b));
    }

    private int calculateFinalContributionsPass(int round, int numChipsBet) {
        int roundsLeft = mappedRoundsLeft.get(round);
        double b = numChipsBet + ((betSize * roundsLeft) * alphaPass);
        return (int)(Math.round(b));
    }

    private int determineBetSize(int minBet, int stackSize, int potSize) {
        int betSize = ((potSize/4)*3);
        if (betSize < minBet) {
            betSize = minBet;
        }
        if (stackSize < betSize) {
            betSize = stackSize;
        }
        return betSize;
    }
}