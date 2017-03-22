package com.saccarn.poker.ai;

import com.saccarn.poker.dbprocessor.DataLoaderStrings;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Neil on 07/03/2017.
 */
public class AiAgent {

    public static final String PRE_FLOP = "preflop";
    public static final String FLOP = "flop";
    public static final String TURN = "turn";
    public static final String RIVER = "river";

    private Random randomGenerator = new Random();
    private final int randomTopLimit = 100;
    private final int randomisationThreshold = 95;
    public String getAction(String stageOfPlay, String holeCard1, String holeCard2, String [] boardCards,
                            int stackSize, int potSize, int playerType, int position, int minBet, String theirAction, int opponentStackSize) {
        String cards = HandRankings.transformCardsForHandRanking(holeCard1, holeCard2);
        Map<String, Double> playerCluster = new HashMap<>();//PlayerCluster.getPlayerInfo(playerType);
        if (stageOfPlay.equals(AiAgent.PRE_FLOP)) {
            return preFlopAction(cards, stackSize, potSize, position, theirAction, playerCluster, minBet, opponentStackSize);
        }
        if (stageOfPlay.equals(AiAgent.FLOP)) {
            return flopAction(cards, stackSize, potSize, position, theirAction, playerCluster, minBet, opponentStackSize);
        }
        if (stageOfPlay.equals(AiAgent.TURN)) {
            return turnAction(cards, stackSize, potSize, position, theirAction, playerCluster, minBet, opponentStackSize);
        }
        if (stageOfPlay.equals((AiAgent.RIVER))) {
            return riverAction(cards, stackSize, potSize, position, theirAction, playerCluster, minBet, opponentStackSize);
        }
        else {
            return "FOLD";
        }
//        return "CHECK";
    }

    private String riverAction(String cards, int stackSize, int potSize, int position, String theirAction, Map<String, Double> playerCluster, int minBet, int opponentStackSize) {
        return "FOLD";
    }

    private String turnAction(String cards, int stackSize, int potSize, int position, String theirAction, Map<String, Double> playerCluster, int minBet, int opponentStackSize) {
        return "FOLD";
    }

    private String flopAction(String cards, int stackSize, int potSize, int position, String theirAction, Map<String, Double> playerCluster, int minBet, int opponentStackSize) {
        return "FOLD";
    }

    private String preFlopAction(String cards, int stackSize, int potSize, int position, String theirAction, Map<String, Double> playerCluster,
                                 int minBet, int opponentStackSize) {
        HandRankings hrs = new HandRankings(); //(100-playerCluster.get(DataLoaderStrings.FOLDED_AT_PRE_FLOP))
        if (hrs.getEVRankOfCardPair(cards) < 70) {
            if (position == 0) { //first - bet check etc.
                if (randomGenerator.nextInt(randomTopLimit) < randomisationThreshold) {
                    int randomNum = randomGenerator.nextInt(randomTopLimit);
                    if (randomNum < randomTopLimit / 3 && stackSize > (2*minBet)) {
                        return "BET2";
                    } else if (randomNum < ((randomTopLimit / 3) * 2) && stackSize > (minBet)) {
                        return "BET1";
                    }
                    else {
                        return "CHECK";
                    }
                } else {
                    return "FOLD";
                }
            } else {
                if (randomGenerator.nextInt(randomTopLimit) < randomisationThreshold) {
                    int randomNum = randomGenerator.nextInt(randomTopLimit);
                    if (randomNum < randomTopLimit / 3 && stackSize > (2*minBet)) {
                        return "RAISE2";
                    } else if (randomNum < ((randomTopLimit / 3) * 2) && stackSize > (minBet)) {
                        return "RAISE1";
                    }
                    else {
                        return "CALL";
                    }
                } else {
                    return "FOLD";
                }
            }
        }
        return "";
    }


}
