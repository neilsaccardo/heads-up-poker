package com.saccarn.poker.ai.preflop;

import com.saccarn.poker.ai.ActionStrings;

import java.util.Map;
import java.util.Random;

/**
 * Created by Neil on 19/04/2017.
 */
public class PreFlopDeterminer {

    private Random randomGenerator = new Random();
    private final int randomTopLimit = 100;
    private final int randomisationThreshold = 95;


    public String preFlopAction(String holeCard1, String holeCard2, int stackSize, int potSize, int position, int amountBet , Map<String, Double> playerCluster,
                                 int minBet, int opponentStackSize) {
        String cards = HandRankings.transformCardsForHandRanking(holeCard1, holeCard2);
        HandRankings hrs = new HandRankings(); //(100-playerCluster.get(DataLoaderStrings.FOLDED_AT_PRE_FLOP))
        if (hrs.getEVRankOfCardPair(cards) < 70) {
            if (position == 0) { //first - bet check etc.
                if (randomGenerator.nextInt(randomTopLimit) < randomisationThreshold) {
                    int randomNum = randomGenerator.nextInt(randomTopLimit);
                    if (randomNum < randomTopLimit / 3 && stackSize > (2*minBet)) {
                        return ActionStrings.ACTION_BET2;
                    } else if (randomNum < ((randomTopLimit / 3) * 2) && stackSize > (minBet)) {
                        return ActionStrings.ACTION_BET1;
                    }
                    else {
                        return ActionStrings.ACTION_PASS;
                    }
                } else {
                    return ActionStrings.ACTION_FOLD;
                }
            } else {
                if (randomGenerator.nextInt(randomTopLimit) < randomisationThreshold) {
                    int randomNum = randomGenerator.nextInt(randomTopLimit);
                    if (randomNum < randomTopLimit / 3 && stackSize > (2*minBet)) {
                        return ActionStrings.ACTION_RAISE2;
                    } else if (randomNum < ((randomTopLimit / 3) * 2) && stackSize > (minBet)) {
                        return ActionStrings.ACTION_RAISE1;
                    }
                    else {
                        return ActionStrings.ACTION_PASS;
                    }
                } else {
                    return ActionStrings.ACTION_FOLD;
                }
            }
        }
        return ActionStrings.ACTION_PASS;
    }
}
