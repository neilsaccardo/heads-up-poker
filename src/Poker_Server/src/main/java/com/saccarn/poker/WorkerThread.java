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
            OutputStream output = clientSocket.getOutputStream();
            DataOutputStream out = new DataOutputStream(output);
            System.out.println();
            int amountBet = WorkerThread.getAmountBet(splitInput[0]);
            String round = WorkerThread.getRound(splitInput[1]);
            String cardOne = WorkerThread.getCard(splitInput[2]);
            String cardTwo = WorkerThread.getCard(splitInput[3]);
            String [] boardCards = WorkerThread.getBoardCards(round, splitInput);
            int stackSize = WorkerThread.getStack(splitInput[5]);
            int minBet = WorkerThread.getMinBet(splitInput[4]);
            int potSize = WorkerThread.getPotSize(splitInput[6]);
            AiAgent ai = new AiAgent();

            System.out.println("Round: " + round +
            " cardOne:  " + cardOne +
            " cardTwo: " + cardTwo +
            " boardCards.legnth: " + boardCards.length +
            " stack size: " + stackSize +
            " min bet " + minBet +
            " a mount bet: " + amountBet);
                                                //"Js", "9s"       //need to add position and oppponent model.
            String action = ai.getAction(round, cardOne, cardTwo, boardCards, stackSize, potSize, 1, 0, minBet, amountBet, stackSize);
            long time = System.currentTimeMillis();
            System.out.println(action);
            System.out.println("Request processed: " + time);
            out.writeUTF(splitInput[splitInput.length-1] + " " + action + " " + " - response");

        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
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
