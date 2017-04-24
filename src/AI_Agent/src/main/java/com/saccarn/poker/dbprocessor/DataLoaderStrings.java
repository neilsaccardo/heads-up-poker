package com.saccarn.poker.dbprocessor;

/**
 * Created by Neil on 06/03/2017.
 */
public class DataLoaderStrings {

    private DataLoaderStrings() {} //No need to initialise this class

    public final static String PLAYER_COLLECTION = "players";
    public final static String DB_NAME = "test1";
    public final static String CLUSTER_COLLECTION = "clusters";

    public final static String NAME = "name";
    public final static String TOTAL_HANDS_PLAYED = "totalHandsPlayed";
    public final static String NUM_WINS = "numWins";
    public final static String TOTAL_BET_RAISES = "totalBetRaises";
    public final static String PRE_FLOP_BET_RAISES = "preFlopBetRaises";
    public final static String FLOP_BET_RAISES = "flopBetRaises";
    public final static String TURN_BET_RAISES = "turnBetRaises";
    public final static String RIVER_BET_RAISES = "riverBetRaises";

    public final static String TOTAL_ACTIONS = "totalActions";
    public final static String TOTAL_PRE_FLOP_ACTIONS = "totalPreFlopActions";
    public final static String TOTAL_FLOP_ACTIONS = "totalFlopActions";
    public final static String TOTAL_TURN_ACTIONS = "totalTurnActions";
    public final static String TOTAL_RIVER_ACTIONS = "totalRiverActions";

    public final static String PRE_FLOP_FOLDED_RATIO = "preFlopFoldedRatio";
    public final static String FLOP_FOLDED_RATIO = "flopFoldedRatio";
    public final static String TURN_FOLDED_RATIO = "turnFoldedRatio";
    public final static String RIVER_FOLDED_RATIO = "riverFoldedRatio";

    public final static String FOLDED_AT_PRE_FLOP = "foldedAtPreFlop";
    public final static String FOLDED_AT_FLOP = "foldedAtFlop";
    public final static String FOLDED_AT_TURN = "foldedAtTurn";
    public final static String FOLDED_AT_RIVER = "foldedAtRiver";

    public final static String TURN_BET_ACTION_RATIO = "riverBetActionRatio";
    public final static String RIVER_BET_ACTION_RATIO = "turnBetActionRatio";
    public final static String FLOP_BET_ACTION_RATIO = "flopBetActionRatio";
    public final static String PRE_FLOP_BET_ACTION_RATIO = "preFlopBetActionRatio";
}
