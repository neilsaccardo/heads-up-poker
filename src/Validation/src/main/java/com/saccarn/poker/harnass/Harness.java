package com.saccarn.poker.harnass;

import betpassvalues.BetPassValuesTest1;
import betpassvalues.BetPassValuesTest2;
import com.saccarn.poker.ai.AiAgent;
import com.saccarn.poker.ai.betpassdeterminer.BetPassActionValues;
import com.saccarn.poker.dbprocessor.DataLoaderStrings;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Neil on 21/04/2017.
 */
public class Harness {

    private int totalIterations = 1000;
    private AiAgent playerOne;
    private AiAgent playerTwo;
    private Map<String, Double> defaultOpponentModel;

    public Harness(BetPassActionValues bpv) {
        playerOne = new AiAgent();
        playerTwo = new AiAgent(bpv);
        defaultOpponentModel = getDefaultOpponentModel();
    }


    public Harness(BetPassActionValues bpv1, BetPassActionValues bpv2) {
        playerOne = new AiAgent(bpv1);
        playerTwo = new AiAgent(bpv2);
        defaultOpponentModel = getDefaultOpponentModel();
    }

    public void playOutHands() {
        int i = 0;
        long start = System.currentTimeMillis();
        int totalPlayerOne = 0;
        int totalPlayerTwo = 0;
        while (i < totalIterations) {
            boolean firstPlay = (i % 2 == 0);
            Game game = new Game(playerOne, playerTwo, firstPlay, defaultOpponentModel, defaultOpponentModel, firstPlay);
            game.playHand();

            totalPlayerOne = totalPlayerOne + ((game.getPlayerOneStack() - game.getStartingStack()) / game.getBigBlindAmount());
            totalPlayerTwo = totalPlayerTwo + ((game.getPlayerTwoStack() - game.getStartingStack()) / game.getBigBlindAmount());
            i++;
        }
        long stop = System.currentTimeMillis();
        long timeTaken = stop-start;
        System.out.println("finished");
        System.out.println("Seconds taken: " + timeTaken/1000);

        System.out.println("Player One winnings: " + totalPlayerOne);
        System.out.println("Player Two winnings: " + totalPlayerTwo);

    }

    private Map<String,Double> getDefaultOpponentModel() {
        Map<String, Double>  model = new HashMap<>();
        model.put(DataLoaderStrings.PRE_FLOP_FOLDED_RATIO, 0.30429600503908655);
        model.put(DataLoaderStrings.FLOP_FOLDED_RATIO, 0.07954173703910809);
        model.put(DataLoaderStrings.TURN_FOLDED_RATIO, 0.03401239437996795);
        model.put(DataLoaderStrings.RIVER_FOLDED_RATIO, 0.011076351136104537);
        return model;
    }

    private static Map<String,Double> getTestOpponentModel() {
        Map<String, Double>  model = new HashMap<>();
        model.put(DataLoaderStrings.PRE_FLOP_FOLDED_RATIO, 0.30429600503908655);
        model.put(DataLoaderStrings.FLOP_FOLDED_RATIO, 0.40954173703910809);
        model.put(DataLoaderStrings.TURN_FOLDED_RATIO, 0.06401239437996795);
        model.put(DataLoaderStrings.RIVER_FOLDED_RATIO, 0.031076351136104537);
        return model;
    }


    public static void main(String [] args) {
        BetPassActionValues bpv2 = new BetPassValuesTest1();

        Harness harness = new Harness(bpv2);
        harness.playOutHands();
    }

}
