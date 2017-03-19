package com.saccarn.poker.ai;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Neil on 15/03/2017.
 */
public class PotOdds {

    private double alphaRaiseCall = 2.145; //alpha'
    private double alphaBetCheck = 1.38; //alpha

    private int finalPotBet;
    private int finalPotPass;
    private int futureContributionBet;
    private int futureContributionPass;

    private Map<Integer, Integer> mappedRoundsLeft = new HashMap<>();

    public PotOdds(){
        mappedRoundsLeft.put(0, 2); // flop
        mappedRoundsLeft.put(1, 1); // turn
        mappedRoundsLeft.put(2, 0); // river
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

    public void calculatePotAndFutureContribution(int minBet, int stackSize, int opponentStackSize, int potSize, int round, int numChipsBet) {
        futureContributionBet = calculateFinalContributionsPotBetOrCheck(minBet, stackSize, potSize, round);
        finalPotBet =  futureContributionBet  + potSize
                       + calculateOpponentFutureContributionsBetOrCheck(minBet, stackSize, potSize, round);
        futureContributionPass = calculateFinalContributionsRaiseOrCall(minBet, stackSize, potSize, round, numChipsBet);
        finalPotPass = futureContributionPass + potSize
                + calculateOpponentFutureContributionsRaiseOrCall(minBet, stackSize, potSize, round);
    }

    private int calculateOpponentFutureContributionsRaiseOrCall(int minBet, int stackSize, int potSize, int round) {
        int betSize = determineBetSize(minBet, potSize, stackSize);
        int roundsLeft = mappedRoundsLeft.get(round);
        double b = betSize + ((betSize * roundsLeft) * alphaRaiseCall);
        return (int)(Math.round(b));
    }

    private int calculateOpponentFutureContributionsBetOrCheck(int minBet, int stackSize, int potSize, int round) {
        int betSize = determineBetSize(minBet, potSize, stackSize);
        int roundsLeft = mappedRoundsLeft.get(round);
        double b = ((betSize * roundsLeft) * alphaBetCheck);
        return (int)(Math.round(b));
    }

    private int calculateFinalContributionsPotBetOrCheck(int minBet, int stackSize, int potSize, int round) {
        int betSize = determineBetSize(minBet, potSize, stackSize);
        int roundsLeft = mappedRoundsLeft.get(round);
        double b = betSize + ((betSize * roundsLeft) * alphaBetCheck);
        return (int)(Math.round(b));
    }

    private int calculateFinalContributionsRaiseOrCall(int minBet, int stackSize, int potSize, int round, int numChipsBet) {
        int betSize = determineBetSize(minBet, potSize, stackSize);
        int roundsLeft = mappedRoundsLeft.get(round);
        double b = numChipsBet + betSize + ((betSize * roundsLeft) * alphaRaiseCall);
        return (int)(Math.round(b));
    }

    private int determineBetSize(int minBet, int potSize, int stackSize) {
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