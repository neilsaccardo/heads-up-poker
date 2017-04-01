package com.saccarn.poker;

import java.io.*;
import java.net.Socket;

import com.saccarn.poker.ai.AiAgent;
/**
 * Created by Neil on 08/03/2017.
 */
public class WorkerThread implements Runnable {

    private Socket clientSocket = null;
    private String input   = "HERE WE ARE";


    public WorkerThread(Socket clientSocket0, String s) {
        clientSocket = clientSocket0;
        input = s;
    }


    public void run() {
        try {
            String [] splitInput = input.split(" ");
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
            " a mount bet: " + amountBet);
                                                //"Js", "9s"       //need to add position and oppponent model.
            String action = ai.getAction(round, cardOne, cardTwo, boardCards, stackSize, potSize, 1, 0, minBet, amountBet, stackSize, prevAction);
            long time = System.currentTimeMillis();
            System.out.println(id + " " + action);
            System.out.println("Request processed: " + time);
            out.writeUTF(id + " " + action);

        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }

    private static String getPreviousAction(String action) {
        return action;
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
