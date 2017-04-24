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

    public PotPredictor(){
        fillRoundTable();
    }

    public PotPredictor(int minBet, int stackSize, int opponentStackSize, int potSize, int round0, int numChipsBet) {
        fillRoundTable();
        calculatePotAndFutureContribution(minBet, stackSize, opponentStackSize, potSize, round0, numChipsBet);
    }

    private void fillRoundTable() {
        mappedRoundsLeft.put(0, 2); // flop
        mappedRoundsLeft.put(1, 1); // turn
        mappedRoundsLeft.put(2, 0); // river
    }

    public int getRound() {
        return round;
    }

    public int getFinalPotBet() {
        return finalPotBet;
    }

    public int getFinalPotPass() {
        return finalPotPass;
    }

    public int getFutureContributionBet() {
        return futureContributionBet;
    }

    public int getFutureContributionPass() {
        return futureContributionPass;
    }

    public int getBetSize() {
        return betSize;
    }

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