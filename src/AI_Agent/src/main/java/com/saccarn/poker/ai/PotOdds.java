package com.saccarn.poker.ai;

import java.util.Map;

/**
 * Created by Neil on 15/03/2017.
 */
public class PotOdds {

    private double alphaRaiseCall = 2.145;
    private double alphaBetCheck = 1.38;

    private Map<Integer, Integer> mappedRoundsLeft;

    public PotOdds(){
        mappedRoundsLeft.put(0, 2); // flop
        mappedRoundsLeft.put(1, 1); // turn
        mappedRoundsLeft.put(2, 0); // river
    }

    public int calculateFinalPot(int minBet, int stackSize, int opponentStackSize, int potSize, int round, boolean toBet, int numChipsBet) {
        int futurePot = 0;
        if (toBet) {
            futurePot = calculateFinalContributionsPotBetOrCheck(minBet, stackSize, potSize, round) +
                        calculateOpponentFutureContributionsBetOrCheck(minBet, stackSize, potSize, round);
        }
        else {
            futurePot = calculateFinalContributionsRaiseOrCall(minBet, stackSize, potSize, round, numChipsBet) +
                    calculateOpponentFutureContributionsRaiseOrCall(minBet, stackSize, potSize, round);
        }
        return futurePot + potSize;
    }

    private int calculateOpponentFutureContributionsRaiseOrCall(int minBet, int stackSize, int potSize, int round) {
        int betSize = determineBetSize(minBet, potSize, stackSize);
        int roundsLeft = mappedRoundsLeft.get(round);
        double b = ((betSize * roundsLeft) * alphaRaiseCall);
        return (int)(Math.round(b));
    }

    private int calculateOpponentFutureContributionsBetOrCheck(int minBet, int stackSize, int potSize, int round) {
        int betSize = determineBetSize(minBet, potSize, stackSize);
        int roundsLeft = mappedRoundsLeft.get(round);
        double b = betSize + ((betSize * roundsLeft) * alphaRaiseCall);
        return (int)(Math.round(b));
    }

    private int calculateFinalContributionsPotBetOrCheck(int minBet, int stackSize, int potSize, int round) {
        int betSize = determineBetSize(minBet, potSize, stackSize);
        int roundsLeft = mappedRoundsLeft.get(round);
        double b = betSize + ((betSize * roundsLeft) * alphaRaiseCall);
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
