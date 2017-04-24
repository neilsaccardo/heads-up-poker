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
import java.util.Scanner;

/**
 * Created by Neil on 21/04/2017.
 */
public class Harnass {

    public static void main(String [] args) throws IOException, URISyntaxException {
        int totalIterations = 10000;
        int i = 0;
        BetPassActionValues bpvTest1 = new BetPassValuesTest1();
        AiAgent playerOne = new AiAgent(bpvTest1);
        AiAgent playerTwo = new AiAgent();
        long start = System.currentTimeMillis();
        Map<String, Double> defaultOpponentModel = Harnass.getDefaultOpponentModel();

        while (i < totalIterations) {
            boolean firstPlay = (i%2==0);
            Game game = new Game(playerOne, playerTwo, firstPlay, defaultOpponentModel, defaultOpponentModel);
            game.playHand();
            System.out.println(game.getBigBlindAmount());
            System.out.println(game.getPlayerOneStack());
            System.out.println(game.getPlayerTwoStack());
            i++;
        }
        long stop = System.currentTimeMillis();
        long timeTaken = stop-start;
        System.out.println("finished");
        System.out.println("Seconds taken: " + timeTaken/1000);
    }

    private static Map<String,Double> getDefaultOpponentModel() {
        Map<String, Double>  model = new HashMap<>();
        model.put(DataLoaderStrings.PRE_FLOP_FOLDED_RATIO, 0.30429600503908655);
        model.put(DataLoaderStrings.FLOP_FOLDED_RATIO, 0.07954173703910809);
        model.put(DataLoaderStrings.TURN_FOLDED_RATIO, 0.03401239437996795);
        model.put(DataLoaderStrings.RIVER_FOLDED_RATIO, 0.011076351136104537);
        return model;
    }
}
