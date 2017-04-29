package com.saccarn.poker.ai;

import com.saccarn.poker.ai.betpassdeterminer.BetPassActionValues;
import com.saccarn.poker.ai.preflop.PreFlopDeterminer;
import java.util.Map;

/**
 * The main class to be used when looking to return an action.
 * Created by Neil on 07/03/2017.
 */
public class AiAgent {

    public static final String PRE_FLOP = "preflop";
    public static final String FLOP = "flop";
    public static final String TURN = "turn";
    public static final String RIVER = "river";


    private BetPassActionValues bpv;
    private boolean checkCommonCards = true; // true by default.
    private boolean affectPotential = true; // true by default


    /**
     * Default no args constructor. Will use default {@link BetPassActionValues} class and
     *  will use CommonHand and HandPotential functionality
     */
    public AiAgent() {
        bpv = new BetPassActionValues();
    }

    /**
     *  Constructor with boolean values to include functionality to CommonHand and HandPotential.
     *  Will use default {@link BetPassActionValues} class.
     * @param checkCommonCards if true, CommonHand will affect belief, false if not
     * @param affectPotential if true, HandPotential will affect belief, false if not
     */
    public AiAgent(boolean checkCommonCards, boolean affectPotential) {
        bpv = new BetPassActionValues();
        this.checkCommonCards = checkCommonCards;
        this.affectPotential = affectPotential;
    }


    /**
     *  Constructor with custom {@link BetPassActionValues} class.
     *  Will use default values (true) for HandPotential and CommonHand.
     *
     *  @param bpv custom BetPassValues class
     */
    public AiAgent(BetPassActionValues bpv) {
        this.bpv = bpv;
    }


    /**
     *  Constructor with one boolean argument representing whether to include CommonHand functionality or not.
     *  Will use default {@link BetPassActionValues} class and will use HandPotential functionality.
     *
     * @param checkCommonCards if true, CommonHand will affect belief, false if not
     * */
    public AiAgent(boolean checkCommonCards) {
        this.bpv = new BetPassActionValues();
        this.checkCommonCards = checkCommonCards;
    }

    /**
     *  Returns an action based on inputs specified as parameters.
     *
     *  This method is specified for there is an opponent model already formed.
     *
     * @param stageOfPlay one of PRE_FLOP, FLOP, TURN, RIVER
     * @param holeCard1 Hole Card 1. Represented as string. Examples: 'Js', '4d', 'Ah', 'Tc'
     * @param holeCard2 Hole Card 2.
     * @param boardCards Array of cards represted as strings, if any.
     * @param stackSize Size of stack currently
     * @param potSize Size of Pot currently
     * @param playerCluster The Opponent Model.
     * @param position First to bet, or second.
     * @param minBet Minimum bet of hand
     * @param amountBet Amount bet with previous action
     * @param opponentStackSize The stack size of the opponent
     * @param previousAction The previous action of the opponent.
     * @return an action to carry out. See {@link ActionStrings}.
     */
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

    /**
     *  Returns an action based on inputs specified as parameters.
     *
     *  This method is specified for there is no opponent model formed, and the default opponent model is needed.
     *
     * @param stageOfPlay one of PRE_FLOP, FLOP, TURN, RIVER
     * @param holeCard1 Hole Card 1. Represented as string. Examples: 'Js', '4d', 'Ah', 'Tc'
     * @param holeCard2 Hole Card 2.
     * @param boardCards Array of cards represted as strings, if any.
     * @param stackSize Size of stack currently
     * @param potSize Size of Pot currently
     * @param playerType int to represent to use the default opponent model.
     * @param position First to bet, or second.
     * @param minBet Minimum bet of hand
     * @param amountBet Amount bet with previous action
     * @param opponentStackSize The stack size of the opponent
     * @param previousAction The previous action of the opponent.
     * @return an action to carry out. See {@link ActionStrings}.
     */

    public String getAction(String stageOfPlay, String holeCard1, String holeCard2, String [] boardCards,
                            int stackSize, int potSize, int playerType, int position, int minBet, int amountBet, int opponentStackSize, String previousAction) {
        Map<String, Double> playerCluster = PlayerCluster.getPlayerInfo(playerType);
        return getAction(stageOfPlay, holeCard1, holeCard2, boardCards,
                stackSize, potSize, playerCluster, position, minBet, amountBet, opponentStackSize, previousAction);
    }

    private String riverAction(String holeCard1, String holeCard2, String[] boardCards, int stackSize, int potSize, int position, int amountBet, Map<String, Double> playerCluster, int minBet, int opponentStackSize) {
        int round = 2;
        ActionDeterminer ad = new ActionDeterminer(holeCard1, holeCard2, boardCards, stackSize, opponentStackSize, potSize, amountBet, minBet, playerCluster, round, bpv, checkCommonCards, affectPotential);
        return ad.getAction();
    }

    private String turnAction(String holeCard1, String holeCard2, String [] boardCards, int stackSize, int potSize, int position, int amountBet, Map<String, Double> playerCluster, int minBet, int opponentStackSize) {
        int round = 1;
        ActionDeterminer ad = new ActionDeterminer(holeCard1, holeCard2, boardCards, stackSize, opponentStackSize, potSize, amountBet, minBet, playerCluster, round, bpv, checkCommonCards, affectPotential);
        return ad.getAction();
    }

    private String flopAction(String holeCard1, String holeCard2, String [] boardCards, int stackSize, int potSize, int position, int amountBet, Map<String, Double> playerCluster, int minBet, int opponentStackSize) {
        int round = 0;
        ActionDeterminer ad = new ActionDeterminer(holeCard1, holeCard2, boardCards, stackSize, opponentStackSize, potSize, amountBet, minBet, playerCluster, round, bpv, checkCommonCards, affectPotential);
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

    /**
     * Returns an action to carry out.
     *
     * This method is used to ensure that there are no unnecessary folds by the system, and to ensure that the action is syntactically correct.
     *
     * @param action The proposed action by the system
     * @param position The position in betting
     * @param stageOfPlay The round of play - PREFLOP, FLOP, TURN, RIVER
     * @param previousAction The previous action carried out.
     * @return an action to carry out.
     */
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
