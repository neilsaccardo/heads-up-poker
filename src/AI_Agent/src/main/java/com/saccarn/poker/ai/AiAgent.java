package com.saccarn.poker.ai;

import com.saccarn.poker.dbprocessor.DataLoaderStrings;

import java.awt.*;
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
                            int stackSize, int potSize, int playerType, int position, int minBet, int amountBet, int opponentStackSize, String previousAction) {
        String cards = HandRankings.transformCardsForHandRanking(holeCard1, holeCard2);
        Map<String, Double> playerCluster = PlayerCluster.getPlayerInfo(playerType);
        if (stageOfPlay.equals(AiAgent.PRE_FLOP)) {
            String action = preFlopAction(cards, stackSize, potSize, position, amountBet, playerCluster, minBet, opponentStackSize);
            return getCorrectOutputAction(action, position, stageOfPlay, previousAction);
        }
        if (stageOfPlay.equals(AiAgent.FLOP)) {
            String action = flopAction(holeCard1, holeCard2, boardCards, stackSize, potSize, position, amountBet, playerCluster, minBet, opponentStackSize);
            return getCorrectOutputAction(action, position, stageOfPlay, previousAction);
        }
        if (stageOfPlay.equals(AiAgent.TURN)) {
            String action = turnAction(holeCard1, holeCard2, boardCards, stackSize, potSize, position, amountBet, playerCluster, minBet, opponentStackSize);
            return getCorrectOutputAction(action, position, stageOfPlay, previousAction);
        }
        if (stageOfPlay.equals((AiAgent.RIVER))) {
            String action = riverAction(holeCard1, holeCard2, boardCards, stackSize, potSize, position, amountBet, playerCluster, minBet, opponentStackSize);
            return getCorrectOutputAction(action, position, stageOfPlay, previousAction);
        }
        else {
            return ActionStrings.ACTION_FOLD;
        }
    }

    private String riverAction(String holeCard1, String holeCard2, String[] boardCards, int stackSize, int potSize, int position, int amountBet, Map<String, Double> playerCluster, int minBet, int opponentStackSize) {
        int round = 2;
        ActionDeterminer ad = new ActionDeterminer(holeCard1, holeCard2, boardCards, stackSize, opponentStackSize, potSize, amountBet, minBet, playerCluster, round);
        return ad.getAction();
    }

    private String turnAction(String holeCard1, String holeCard2, String [] boardCards, int stackSize, int potSize, int position, int amountBet, Map<String, Double> playerCluster, int minBet, int opponentStackSize) {
        int round = 1;
        ActionDeterminer ad = new ActionDeterminer(holeCard1, holeCard2, boardCards, stackSize, opponentStackSize, potSize, amountBet, minBet, playerCluster, round);
        return ad.getAction();
    }

    private String flopAction(String holeCard1, String holeCard2, String [] boardCards, int stackSize, int potSize, int position, int amountBet, Map<String, Double> playerCluster, int minBet, int opponentStackSize) {
        int round = 0;
        ActionDeterminer ad = new ActionDeterminer(holeCard1, holeCard2, boardCards, stackSize, opponentStackSize, potSize, amountBet, minBet, playerCluster, round);
        return ad.getAction();
    }


    // TODO : Abstract this out to another Pre Flop action class.
    private String preFlopAction(String cards, int stackSize, int potSize, int position, int amountBet , Map<String, Double> playerCluster,
                                 int minBet, int opponentStackSize) {
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


    public String getCorrectOutputAction(String action, int position, String stageOfPlay, String previousAction) {
        if (stageOfPlay.equals(AiAgent.PRE_FLOP)) {
            if (previousAction.equals(ActionStrings.ACTION_CHECK) || previousAction.equals(ActionStrings.ACTION_CALL)) {
                switch (action) {
                    case ActionStrings.ACTION_PASS:
                        return ActionStrings.ACTION_CHECK;
                    default:
                        return action;
                }
            }
            else {
                switch (action) {
                    case ActionStrings.ACTION_PASS:
                        return ActionStrings.ACTION_CALL;
                    case ActionStrings.ACTION_BET1:
                        return ActionStrings.ACTION_RAISE1;
                    case ActionStrings.ACTION_BET2:
                        return ActionStrings.ACTION_RAISE2;
                    case ActionStrings.ACTION_BET3:
                        return ActionStrings.ACTION_RAISE3;
                    default:
                        return action;
                }
            }
        }
        else {
            if (previousAction.equals(ActionStrings.ACTION_CHECK) || previousAction.equals(ActionStrings.ACTION_CALL)) {
                switch (action) {
                    case ActionStrings.ACTION_PASS:
                        return ActionStrings.ACTION_CHECK;
                    default:
                        return action;
                }
            }
            else {
                switch (action) {
                    case ActionStrings.ACTION_PASS:
                        return ActionStrings.ACTION_CALL;
                    case ActionStrings.ACTION_BET1:
                        return ActionStrings.ACTION_RAISE1;
                    case ActionStrings.ACTION_BET2:
                        return ActionStrings.ACTION_RAISE2;
                    case ActionStrings.ACTION_BET3:
                        return ActionStrings.ACTION_RAISE3;
                    default:
                        return action;
                }
            }
        }
    }

    private String getCorrectOutputAction(String action, int position) {
        if (action.equals(ActionStrings.ACTION_PASS)) {
            return (position == 0) ? ActionStrings.ACTION_CHECK : ActionStrings.ACTION_CALL;
        }
        if (position == 0) {
            return action;
        }
        else {
            switch (action) {
                case ActionStrings.ACTION_BET1:
                    return ActionStrings.ACTION_RAISE1;
                case ActionStrings.ACTION_BET2:
                    return ActionStrings.ACTION_RAISE2;
                case ActionStrings.ACTION_BET3:
                    return ActionStrings.ACTION_RAISE3;
                default:
                    return ActionStrings.ACTION_FOLD;
            }
        }
    }
}
