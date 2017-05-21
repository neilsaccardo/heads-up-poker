package com.saccarn.poker;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.saccarn.poker.ai.AiAgent;
import com.saccarn.poker.dbprocessor.DataLoaderStrings;

/**
 * Created by Neil on 08/03/2017.
 */
public class WorkerThread implements Runnable {

    private Socket clientSocket = null;
    private String inputLine   = "";


    public WorkerThread(Socket clientSocket0, String s) {
        clientSocket = clientSocket0;
        inputLine = s;
    }

    public void run() {
        try {
            String [] splitInputsAndModel = inputLine.split(":");
            String [] splitInput = splitInputsAndModel[0].split(" ");
            String [] model = splitInputsAndModel[1].trim().split(" ");
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
            System.out.println();
            String prevAction = WorkerThread.getPreviousAction(splitInput[0]);
            int amountBet = WorkerThread.getAmountBet(splitInput[1]);
            String round = WorkerThread.getRound(splitInput[2]);
            String cardOne = WorkerThread.getCard(splitInput[3]);
            String cardTwo = WorkerThread.getCard(splitInput[4]);
            int minBet = WorkerThread.getMinBet(splitInput[5]);
            int stackSize = WorkerThread.getStack(splitInput[6]);
            int potSize = WorkerThread.getPotSize(splitInput[7]);
            String id = WorkerThread.getID(splitInput[8]);
            String [] boardCards = WorkerThread.getBoardCards(round, splitInput);

            AiAgent ai = new AiAgent();

            System.out.println("Round: " + round +
            " cardOne:  " + cardOne +
            " cardTwo: " + cardTwo +
            " boardCards.legnth: " + boardCards.length +
            " stack size: " + stackSize +
            " min bet " + minBet +
            " amount bet: " + amountBet +
            " previous action: " + prevAction);
            System.out.println(model.length);
            String action = "";                                //"Js", "9s"       //need to add position and oppponent model.
            if (model.length == 1) {
                action = ai.getAction(round, cardOne, cardTwo, boardCards, stackSize, potSize, 1, 0, minBet, amountBet, stackSize, prevAction);
            }
            else {
                System.out.println("HERE");
                for (int i = 0; i < model.length; i++) {
                    System.out.print(model[i]+ ' ');
                }
                System.out.println("END HERE");
                Map<String, Double> modelMap = WorkerThread.createOpponentMap(model);
                action = ai.getAction(round, cardOne, cardTwo, boardCards, stackSize, potSize, modelMap, 0, minBet, amountBet, stackSize, prevAction);
            }
            long time = System.currentTimeMillis();
            System.out.println(id + " " + action);
            System.out.println("Request processed: " + time);
            out.writeUTF(id + " " + action);

        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }

    public static Map<String,Double> createOpponentMap(String[] model) {
        Map<String, Double> map = new HashMap<String, Double>();
        int numHandsPlayed = Integer.parseInt(model[4]);
        int numFoldedAtPreFlop = Integer.parseInt(model[0]);
        int numFoldedAtFlop = Integer.parseInt(model[1]);
        int numFoldedAtTurn = Integer.parseInt(model[2]);
        int numFoldedAtRiver = Integer.parseInt(model[3]);

        map.put(DataLoaderStrings.PRE_FLOP_FOLDED_RATIO, numFoldedAtPreFlop/((double)numHandsPlayed));
        map.put(DataLoaderStrings.FLOP_FOLDED_RATIO, numFoldedAtFlop/(((double)numHandsPlayed  - numFoldedAtPreFlop) + 1));
        map.put(DataLoaderStrings.TURN_FOLDED_RATIO, numFoldedAtTurn/(((double)numHandsPlayed  - numFoldedAtPreFlop - numFoldedAtFlop) + 1));
        map.put(DataLoaderStrings.RIVER_FOLDED_RATIO, numFoldedAtRiver/(((double)numHandsPlayed  - numFoldedAtPreFlop - numFoldedAtFlop - numFoldedAtTurn) + 1));
        return map;
    }


    private static String getPreviousAction(String action) {
        return action.toUpperCase();
    }

    private static String getID(String id) {
        return id;
    }

    private static int getAmountBet(String s) {
        return Integer.parseInt(s);
    }

    private static int getPotSize(String s) {
        return Integer.parseInt(s);
    }

    private static String getCard(String s) {
        return s;
    }

    private static int getMinBet(String minBetString) {
        return Integer.parseInt(minBetString);
    }

    private static int getStack(String stackString) {
        return Integer.parseInt(stackString);
    }

    private static String [] getBoardCards(String round, String [] splitInput) {
        String [] boardsCards;
        int numBoardCards;
        if (round.equals("preflop")) {
            boardsCards = new String[0];
            return boardsCards;
        }
        else if (round.equals("flop")) {
            numBoardCards = 3;
        }
        else if (round.equals("turn")) {
            numBoardCards = 4;
        }
        else { //"river"
            numBoardCards = 5;
        }
        boardsCards = new String[numBoardCards];
        for (int i = 0; i < boardsCards.length; i++) {
            boardsCards[i] = splitInput[splitInput.length-numBoardCards+i];
        }
        return boardsCards;
    }

    private static String getRound(String roundNumber) {
        if (roundNumber.equals("0")) {
            return "preflop";
        }
        if (roundNumber.equals("1")) {
            return "flop";
        }
        if (roundNumber.equals("2")) {
            return "turn";
        }
        else { //equals 3
            return "river";
        }
    }
}
