package com.saccarn.poker.ai.preflop;

import com.saccarn.poker.ai.ActionStrings;

import java.util.Map;
import java.util.Random;

/**
 * This class is used to determine what action to carry out at the pre flop stage.
 *
 * Created by Neil on 19/04/2017.
 */
public class PreFlopDeterminer {

    private Random randomGenerator = new Random();
    private final int RANDOM_TOP_LIMIT = 100;
    private final int FOLD_THRESHOLD = 90;
    private final int RANK_THRESHOLD = 40;
    private int minBet;
    private int stackSize;
    private int position;

    /**
     * Returns action to carry out.
     *
     * Uses mixture of randomisation and card EVs to work out what action to carry out.
     *
     * @param holeCard1 hole card one
     * @param holeCard2 hole card two
     * @param stackSize size of stack
     * @param potSize size of pot
     * @param position position relative to betting
     * @param amountBet the amount bet in previous action
     * @param playerCluster the opponent model
     * @param minBet the minimum bet
     * @param opponentStackSize the size of the opponent stack
     * @return action to carry out
     */
    public String preFlopAction(String holeCard1, String holeCard2, int stackSize, int potSize, int position, int amountBet, Map<String, Double> playerCluster,
                                int minBet, int opponentStackSize) {
        this.minBet = minBet;
        this.stackSize = stackSize;
        this.position = position;
        String cards = HandRankings.transformCardsForHandRanking(holeCard1, holeCard2);
        HandRankings hrs = new HandRankings(); //(100-playerCluster.get(DataLoaderStrings.FOLDED_AT_PRE_FLOP))
        double rank = hrs.getEVRankOfCardPair(cards);
        int randomNum = randomGenerator.nextInt(RANDOM_TOP_LIMIT);
        return action(rank, randomNum);
    }

    public String action(double rank, int randomNumFoldOrContinue) {
        // fold x% of the time when rank is above certain value.
        if (rank > RANK_THRESHOLD && randomNumFoldOrContinue > FOLD_THRESHOLD) {
            return ActionStrings.ACTION_FOLD;
        }

        int randomNum = randomGenerator.nextInt(RANDOM_TOP_LIMIT);
        if (randomNum < ((RANDOM_TOP_LIMIT / 3) * 2)) {
            return ActionStrings.ACTION_PASS;
        }
        else if (randomNum < ((RANDOM_TOP_LIMIT / 4) * 3)) {
            return ActionStrings.ACTION_BET2;
        }
        else {
            return ActionStrings.ACTION_BET1;
        }
    }
}
