package com.saccarn.poker.ai;

import java.util.Map;

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

        return "FOLD";
    }
}
