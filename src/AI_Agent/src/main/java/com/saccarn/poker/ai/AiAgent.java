package com.saccarn.poker.ai;

import com.saccarn.poker.ai.betpassdeterminer.BetPassActionValues;
import com.saccarn.poker.ai.preflop.HandRankings;
import com.saccarn.poker.ai.preflop.PreFlopDeterminer;

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

    private BetPassActionValues bpv;

    public AiAgent() {
        bpv = new BetPassActionValues();
    }

    public AiAgent(BetPassActionValues bpv) {
        this.bpv = bpv;
    }

    public String getAction(String stageOfPlay, String holeCard1, String holeCard2, String [] boardCards,
                            int stackSize, int potSize, Map<String, Double> playerCluster, int position,
                            int minBet, int amountBet, int opponentStackSize, String previousAction) {

        if (stageOfPlay.equals(AiAgent.PRE_FLOP)) {
            String action = preFlopAction(holeCard1, holeCard2, stackSize, potSize, position, amountBet, playerCluster, minBet, opponentStackSize);
            String a = getCorrectOutputAction(action, position, stageOfPlay, previousAction);
            return noUnNecessaryFolds(a, position, stageOfPlay, previousAction);
        }
        if (stageOfPlay.equals(AiAgent.FLOP)) {
            String action = flopAction(holeCard1, holeCard2, boardCards, stackSize, potSize, position, amountBet, playerCluster, minBet, opponentStackSize);
            String a = getCorrectOutputAction(action, position, stageOfPlay, previousAction);
            return noUnNecessaryFolds(a, position, stageOfPlay, previousAction);
        }
        if (stageOfPlay.equals(AiAgent.TURN)) {
            String action = turnAction(holeCard1, holeCard2, boardCards, stackSize, potSize, position, amountBet, playerCluster, minBet, opponentStackSize);
            String a = getCorrectOutputAction(action, position, stageOfPlay, previousAction);
            return noUnNecessaryFolds(a, position, stageOfPlay, previousAction);
        }
        if (stageOfPlay.equals((AiAgent.RIVER))) {
            String action = riverAction(holeCard1, holeCard2, boardCards, stackSize, potSize, position, amountBet, playerCluster, minBet, opponentStackSize);
            String a = getCorrectOutputAction(action, position, stageOfPlay, previousAction);
            return noUnNecessaryFolds(a, position, stageOfPlay, previousAction);
        }
        else {
            return ActionStrings.ACTION_FOLD;
        }


    }

    public String getAction(String stageOfPlay, String holeCard1, String holeCard2, String [] boardCards,
                            int stackSize, int potSize, int playerType, int position, int minBet, int amountBet, int opponentStackSize, String previousAction) {
        Map<String, Double> playerCluster = PlayerCluster.getPlayerInfo(playerType);
        return getAction(stageOfPlay, holeCard1, holeCard2, boardCards,
                stackSize, potSize, playerCluster, position, minBet, amountBet, opponentStackSize, previousAction);
    }

    private String riverAction(String holeCard1, String holeCard2, String[] boardCards, int stackSize, int potSize, int position, int amountBet, Map<String, Double> playerCluster, int minBet, int opponentStackSize) {
        int round = 2;
        ActionDeterminer ad = new ActionDeterminer(holeCard1, holeCard2, boardCards, stackSize, opponentStackSize, potSize, amountBet, minBet, playerCluster, round, bpv);
        return ad.getAction();
    }

    private String turnAction(String holeCard1, String holeCard2, String [] boardCards, int stackSize, int potSize, int position, int amountBet, Map<String, Double> playerCluster, int minBet, int opponentStackSize) {
        int round = 1;
        ActionDeterminer ad = new ActionDeterminer(holeCard1, holeCard2, boardCards, stackSize, opponentStackSize, potSize, amountBet, minBet, playerCluster, round, bpv);
        return ad.getAction();
    }

    private String flopAction(String holeCard1, String holeCard2, String [] boardCards, int stackSize, int potSize, int position, int amountBet, Map<String, Double> playerCluster, int minBet, int opponentStackSize) {
        int round = 0;
        ActionDeterminer ad = new ActionDeterminer(holeCard1, holeCard2, boardCards, stackSize, opponentStackSize, potSize, amountBet, minBet, playerCluster, round, bpv);
        return ad.getAction();
    }


    private String preFlopAction(String holeCard1, String holeCard2, int stackSize, int potSize, int position, int amountBet , Map<String, Double> playerCluster,
                                 int minBet, int opponentStackSize) {
        PreFlopDeterminer pfd = new PreFlopDeterminer();
        return pfd.preFlopAction(holeCard1, holeCard2, stackSize, potSize, position, amountBet, playerCluster, minBet, opponentStackSize);
    }

    public String noUnNecessaryFolds(String action, int position, String stageOfPlay, String previousAction) {
        if (!action.equals(ActionStrings.ACTION_FOLD)) { // if its no fold then no need to check
            return action;
        }
        if (previousAction.equals(ActionStrings.ACTION_CHECK)) {
           return ActionStrings.ACTION_CHECK;
        }
        else if (previousAction.equals(ActionStrings.ACTION_CALL)) {
            return ActionStrings.ACTION_CHECK;
        }
        else {
            return action;
        }
    }


    public String getCorrectOutputAction(String action, int position, String stageOfPlay, String previousAction) {
        if (stageOfPlay.equals(AiAgent.PRE_FLOP)) {
            if (previousAction.equals(ActionStrings.ACTION_CHECK) || previousAction.equals(ActionStrings.ACTION_CALL)) {
                switch (previousAction) {
                    case ActionStrings.ACTION_CHECK:
                        return ActionStrings.ACTION_CALL;
                    case ActionStrings.ACTION_CALL:
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
}
