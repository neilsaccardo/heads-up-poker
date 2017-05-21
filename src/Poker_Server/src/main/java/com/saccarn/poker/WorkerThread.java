package com.saccarn.poker;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.saccarn.poker.ai.AiAgent;
import com.saccarn.poker.dbprocessor.DataLoaderStrings;

/**
 * Worker Thread deals with incoming input. Creates an Ai_Agent object based on data it receives, and outputs back
 * to client socket
 * Created by Neil on 08/03/2017.
 */
public class WorkerThread implements Runnable {

    private Socket clientSocket = null;
    private String inputLine   = "";


    public WorkerThread(Socket clientSocket0, String s) {
        clientSocket = clientSocket0;
        inputLine = s;
    }

    /**
     * This method is where the input data is dealt with - opponent model, cards, board cards, stack size, pot size, id, previous action, amount bet, min bet.
     * It creates an AiAgent object based on this and uses it to return an action. It then outputs this action along with the id to the client socket.
     */
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
            String action = "";
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

    /**
     * Creates opponent model based on input
     * @param model model represented as a string array.
     * @return the opponent model as a Map<String, Double> for use by the Ai_Agent object
     */
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

    /**
     * Creates action based on input
     * @param action input action
     * @return upper case action for use by Ai_Agent
     */
    private static String getPreviousAction(String action) {
        return action.toUpperCase();
    }

    /**
     * Used to get id of client
     * @param id id of client
     * @return id of client
     */
    private static String getID(String id) {
        return id;
    }

    /**
     * Used to get the previous amount bet.
     * @param s string representation of amount bet.
     * @return integer representation of amount bet
     */
    private static int getAmountBet(String s) {
        return Integer.parseInt(s);
    }

    /**
     * Used to get the pot size.
     * @param s string representation of pot size.
     * @return integer representation of pot size.
     */
    private static int getPotSize(String s) {
        return Integer.parseInt(s);
    }

    /**
     *  Used to get card value
     * @param s card value
     * @return card value for use of Ai_Agent
     */
    private static String getCard(String s) {
        return s;
    }

    /**
     * Used to get the minimum bet size.
     * @param minBetString string representation of minimum bet size.
     * @return integer representation of minimum bet size.
     */
    private static int getMinBet(String minBetString) {
        return Integer.parseInt(minBetString);
    }

    /**
     * Used to get the size of user stack.
     * @param stackString string representation of user stack size.
     * @return integer representation of user stack size.
     */
    private static int getStack(String stackString) {
        return Integer.parseInt(stackString);
    }

    /**
     * Gets the board cards for use my age agent
     * @param round what round/stage of play - flop, turn, river
     * @param splitInput the board cards
     * @return board cards as an array of Strings
     */
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

    /**
     * Gets the round for Ai_Agent
     * @param roundNumber intager representation of round number- 0, 1, 2, 3 representing preflop, flop, turn, river.
     * @return returns string representation of round/stage of play.
     */
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
