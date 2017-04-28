package com.saccarn.poker.harnass;

import com.saccarn.poker.ai.AiAgent;
import com.saccarn.poker.ai.betpassdeterminer.BetPassActionValues;
import com.saccarn.poker.dbprocessor.DataLoaderStrings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Neil on 21/04/2017.
 */
public class Harness {

    private int totalIterations = 1000;
    private AiAgent playerOne;
    private AiAgent playerTwo;
    private Map<String, Double> playerOneOpponentModel;
    private Map<String, Double> playerTwoOpponentModel;
    private List<Double> totalsPlayerOne = new ArrayList<>();
    private List<Double> totalsPlayerTwo = new ArrayList<>();

    public Harness(BetPassActionValues bpv) {
        playerOne = new AiAgent(bpv);
        playerTwo = new AiAgent();
        playerOneOpponentModel = getDefaultOpponentModel();
        playerTwoOpponentModel = getDefaultOpponentModel();
    }


    public Harness(boolean commonHandPlayerOne, boolean commonHandPlayerTwo) {
        playerOne = new AiAgent(commonHandPlayerOne);
        playerTwo = new AiAgent(commonHandPlayerTwo);
        playerOneOpponentModel = getDefaultOpponentModel();
        playerTwoOpponentModel = getDefaultOpponentModel();
    }

    public Harness(BetPassActionValues bpv1, BetPassActionValues bpv2) {
        playerOne = new AiAgent(bpv1);
        playerTwo = new AiAgent(bpv2);
        playerOneOpponentModel = getDefaultOpponentModel();
        playerTwoOpponentModel = getDefaultOpponentModel();
    }

    public Harness(Map<String, Double> p2OppModel) {
        playerOne = new AiAgent();
        playerTwo = new AiAgent();
        playerTwoOpponentModel = p2OppModel;
    }

    public Harness(Map<String, Double> p1OppModel, Map<String, Double> p2OppModel) {
        playerOne = new AiAgent();
        playerTwo = new AiAgent();
        playerTwoOpponentModel = p1OppModel;
        playerOneOpponentModel = p2OppModel;
    }

    //int 'potential' is added solely to differentiate the harness constructors between common hand and hand potential
    public Harness(boolean handPotentialP1, boolean handPotentialP2, int potential) {
        playerOne = new AiAgent(true, handPotentialP1);
        playerTwo = new AiAgent(true, handPotentialP2);
        playerOneOpponentModel = getDefaultOpponentModel();
        playerTwoOpponentModel = getDefaultOpponentModel();
    }

    public void playOutHands1() {
        int i = 0;
        long start = System.currentTimeMillis();
        int totalPlayerOne = 0;
        int totalPlayerTwo = 0;
        while (i < totalIterations) {
            boolean firstPlay = (i % 2 == 0);
            Game game = new Game(playerOne, playerTwo, firstPlay, playerOneOpponentModel, playerTwoOpponentModel, firstPlay);
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



        totalsPlayerOne.add(totalPlayerOne/(double)totalIterations);

        totalsPlayerTwo.add(totalPlayerTwo/(double)totalIterations);

        System.out.println("Player One winnings: " + totalPlayerOne/(double)totalIterations);
        System.out.println("Player Two winnings: " + totalPlayerTwo/(double)totalIterations);
    }

    public void playOutHands() {

        for (int i = 0; i < 20; i++) {
            playOutHands1();
        }
        System.out.println(totalsPlayerOne);
        System.out.println(totalsPlayerTwo);
        double mean = mean(totalsPlayerOne);
        double variance = variance(totalsPlayerOne);
        System.out.println("MEAN: " + mean);
        System.out.println("VARIANCE: " + variance);
    }

    private double mean(List<Double> li) {
        double total = 0;
        for (int i = 0; i < li.size(); i++) {
            total = total + li.get(i);
        }
        return  total / li.size();
    }

    private double variance(List<Double> li) {
        double mean = mean(li);
        double totalDiffSquared = 0;
        for (int i = 0; i < li.size(); i++) {
            double d = li.get(i);
            totalDiffSquared = ((mean - d) * (mean-d)) + totalDiffSquared;
        }
        return totalDiffSquared / li.size();
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



}
