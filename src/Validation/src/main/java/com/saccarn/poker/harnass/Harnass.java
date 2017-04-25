package com.saccarn.poker.harnass;

import betpassvalues.BetPassValuesTest1;
import com.saccarn.poker.ai.ActionStrings;
import com.saccarn.poker.ai.AiAgent;
import com.saccarn.poker.ai.betpassdeterminer.BetPassActionValues;
import com.saccarn.poker.dbprocessor.DataLoaderStrings;

import java.io.*;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Neil on 21/04/2017.
 */
public class Harnass {

    public static void main(String [] args) throws IOException, URISyntaxException {
        int totalIterations = 1000;
        int i = 0;
        BetPassActionValues bpvTest1 = new BetPassValuesTest1();
        AiAgent playerOne = new AiAgent(bpvTest1);
        AiAgent playerTwo = new AiAgent(bpvTest1);
        long start = System.currentTimeMillis();
        Map<String, Double> defaultOpponentModel = Harnass.getDefaultOpponentModel();
        Map<String, Double> testOpponentModel = Harnass.getDefaultOpponentModel();
        long totalPlayerOne = 0;
        long totalPlayerTwo = 0;
        while (i < totalIterations) {
            boolean firstPlay = (i%2==0);
            Game game = new Game(playerOne, playerTwo, firstPlay, defaultOpponentModel, testOpponentModel);
            game.playHand();
            System.out.println(game.getBigBlindAmount());
            System.out.println(game.getPlayerOneStack());
            System.out.println(game.getPlayerTwoStack());
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

    private static Map<String,Double> getDefaultOpponentModel() {
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

}
