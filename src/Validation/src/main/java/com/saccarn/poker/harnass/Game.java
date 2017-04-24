package com.saccarn.poker.harnass;

import cards.Card;
import cards.CardSet;
import com.saccarn.poker.ai.ActionStrings;
import com.saccarn.poker.ai.AiAgent;
import com.saccarn.poker.ai.util.PairTable;

import java.util.HashMap;

/**
 * Created by Neil on 21/04/2017.
 */
public class Game {

    private AiAgent aiAgentPlayerOne = new AiAgent();
    private AiAgent aiAgentPlayerTwo= new AiAgent();


    private int numHandsWonPlayerOne = 0;
    private int numHandsWonPlayerTwo = 0;
    private Card [] playerOneCards = new Card[7];
    private Card [] playerTwoCards = new Card[7];
    private Card [] fullDeckArray;
    private int deckPointer = 0;


    private HashMap<String, Double> playerOneModel;
    private HashMap<String, Double> playerTwoModel;

    private boolean isPlayerOneSmallBlind = false;
    private int playerOneStack = 10000;
    private int playerTwoStack = 10000;
    private int bigBlindAmount = 100;
    private int pot = 0;


    boolean playerOneTurn = true;
    public void playHand() {
        if (checkGameOver()) {
            return;
        }

        dealOutCards();
        String action;
        determineSmallBlind();
        payBlinds();
        String [] boardCards = {};
        if (isPlayerOneSmallBlind) {                                                                                        /// check out what those nums mean. no idea. fix up the util method to getAction
            action = aiAgentPlayerOne.getAction(AiAgent.PRE_FLOP, playerOneCards[0].toString(), playerOneCards[1].toString(), boardCards, playerOneStack, pot, playerTwoModel, 0, bigBlindAmount, 0, playerTwoStack, "CalL");
            checkFold(action, true);    // true if the action is from playerOne. false if from playerTwo
        }
        else {
            action = getPlayerTwoAction(AiAgent.PRE_FLOP, 0, 0, "CALL");
            action = aiAgentPlayerTwo.getAction();
            checkFold(action, false);    // true if the action is from playerOne. false if from playerTwo
        }

        if (isPlayerOneSmallBlind) {
            action = aiAgentPlayerTwo.getAction();
            checkFold(action, false);    // true if the action is from playerOne. false if from playerTwo
            playerOneTurn = true;
        }
        else {
            action = aiAgentPlayerTwo.getAction();
            checkFold(action, true);    // true if the action is from playerOne. false if from playerTwo
            playerOneTurn = false;
        }
        checkFold(action, !playerOneTurn);
        if (action.equals(ActionStrings.ACTION_CHECK)) {
            flop(isPlayerOneSmallBlind);
        }
        while((!action.equals(ActionStrings.ACTION_CALL))) {
            if (playerOneTurn) {
                action = aiAgentPlayerOne.getAction();
                playerOneTurn = false;
            }
            else {
                action = aiAgentPlayerTwo.getAction();
                playerOneTurn = true;
            }
            checkFold(action, !playerOneTurn);
        }
        flop(isPlayerOneSmallBlind);

    }

    private String getPlayerTwoAction(String stageOfPlay, int amountBet, int i1, String previousAction, String [] boardCards) {
//        action = aiAgentPlayerOne.getAction(AiAgent.PRE_FLOP, playerOneCards[0].toString(), playerOneCards[1].toString(), boardCards, playerOneStack, pot, opponentModelPlayerTwo, 0, bigBlindAmount, 0, playerTwoStack, "CalL");
        return aiAgentPlayerTwo.getAction(stageOfPlay, playerTwoCards[0].toString(), playerTwoCards[1].toString(), boardCards, playerTwoStack, pot, playerOneModel, 0, bigBlindAmount, bigBlindAmount, );
    }


    private void flop(boolean isPlayerOneSmallBlind) {
        flopOutCards();
        String action;
        if (isPlayerOneSmallBlind) {
            action = aiAgentPlayerTwo.getAction();
            playerOneTurn = true;
        }
        else {
            action = aiAgentPlayerOne.getAction();
            playerOneTurn = false;
        }
        checkFold(action, (!isPlayerOneSmallBlind));

        if (playerOneTurn) {
            action = aiAgentPlayerOne.getAction();
            playerOneTurn = false;
        }
        else {
            action = aiAgentPlayerTwo.getAction();
            playerOneTurn = true;
        }
        if (action.equals(ActionStrings.ACTION_CHECK)) {
            turn(isPlayerOneSmallBlind);
        }

        while ((!action.equals(ActionStrings.ACTION_CALL))) {
            if (playerOneTurn) {
                action = aiAgentPlayerOne.getAction();
                playerOneTurn = false;
            }
            else {
                action = aiAgentPlayerTwo.getAction();
                playerOneTurn = true;
            }
        }
        turn(playerOneTurn);
    }

    private void turn(boolean isPlayerOneSmallBlind) {
        turnOutCards();
        String action;
        if (isPlayerOneSmallBlind) {
            action = aiAgentPlayerTwo.getAction();
            playerOneTurn = true;
        }
        else {
            action = aiAgentPlayerOne.getAction();
            playerOneTurn = false;
        }
        checkFold(action, (!isPlayerOneSmallBlind));

        if (playerOneTurn) {
            action = aiAgentPlayerOne.getAction();
            playerOneTurn = false;
        }
        else {
            action = aiAgentPlayerTwo.getAction();
            playerOneTurn = true;
        }
        if (action.equals(ActionStrings.ACTION_CHECK)) {
            river(isPlayerOneSmallBlind);
        }

        while ((!action.equals(ActionStrings.ACTION_CALL))) {
            if (playerOneTurn) {
                action = aiAgentPlayerOne.getAction();
                playerOneTurn = false;
            }
            else {
                action = aiAgentPlayerTwo.getAction();
                playerOneTurn = true;
            }
        }
        river(playerOneTurn);

    }

    private void river(boolean playerOneTurn) {
        riverOutCards();
        String action;
        if (isPlayerOneSmallBlind) {
            action = aiAgentPlayerTwo.getAction();
            playerOneTurn = true;
        }
        else {
            action = aiAgentPlayerOne.getAction();
            playerOneTurn = false;
        }
        checkFold(action, (!isPlayerOneSmallBlind));

        if (playerOneTurn) {
            action = aiAgentPlayerOne.getAction();
            playerOneTurn = false;
        }
        else {
            action = aiAgentPlayerTwo.getAction();
            playerOneTurn = true;
        }
        if (action.equals(ActionStrings.ACTION_CHECK)) {
            showDown(isPlayerOneSmallBlind);
        }

        while ((!action.equals(ActionStrings.ACTION_CALL))) {
            if (playerOneTurn) {
                action = aiAgentPlayerOne.getAction();
                playerOneTurn = false;
            }
            else {
                action = aiAgentPlayerTwo.getAction();
                playerOneTurn = true;
            }
        }
        showDown(playerOneTurn);

    }

    private void riverOutCards() {
        playerOneCards[6] = fullDeckArray[deckPointer];
        playerTwoCards[6] = fullDeckArray[deckPointer];
    }

    private void flopOutCards() {
        playerOneCards[3] = fullDeckArray[deckPointer];
        playerTwoCards[3] = fullDeckArray[deckPointer];
        deckPointer++;
        playerOneCards[4] = fullDeckArray[deckPointer];
        playerTwoCards[4] = fullDeckArray[deckPointer];
        deckPointer++;
        playerOneCards[5] = fullDeckArray[deckPointer];
        playerTwoCards[5] = fullDeckArray[deckPointer];
        deckPointer++;

    }


    private void turnOutCards() {
        playerOneCards[5] = fullDeckArray[deckPointer];
        playerTwoCards[5] = fullDeckArray[deckPointer];
        deckPointer++;
    }


    private void showDown(boolean playerOneTurn) {
        //do the card evaluation stuff.
        playHand();
    }

    private void payBlinds() {
        if (isPlayerOneSmallBlind) {
            playerOneStack =- bigBlindAmount/2;
            playerTwoStack =- bigBlindAmount;
        }
        else { // player pays big blind.
            playerTwoStack =- bigBlindAmount/2;
            playerOneStack =- bigBlindAmount;
        }

    }

    private boolean checkFold(String action, boolean isPlayerOne) { //isPlayerOne should be true if the action is from player one.
        if (action.equals(ActionStrings.ACTION_FOLD)) {
            if (isPlayerOne) {
                addPotToPlayerTwoStack();
            }
            else {
                addPotToPlayerOneStack();
            }
            return true;
        }
        else {
            return false;
        }
    }

    private void addPotToPlayerTwoStack() {
        playerTwoStack += pot;
    }

    private void addPotToPlayerOneStack() {
        playerOneStack += pot;
    }

    private boolean checkGameOver() {
        return playerOneStack < bigBlindAmount
                || playerTwoStack < bigBlindAmount;
    }

    private void determineSmallBlind() {
        isPlayerOneSmallBlind = (!isPlayerOneSmallBlind); //switch dealer
    }

    private void dealOutCards() {
        CardSet cs = CardSet.shuffledDeck();
        fullDeckArray = cs.toArray();
        playerOneCards = new Card[7];
        playerTwoCards = new Card[7];

        for (deckPointer = 0; deckPointer < 2; deckPointer++) {
            playerOneCards[deckPointer] = fullDeckArray[deckPointer];
            playerTwoCards[deckPointer] = fullDeckArray[deckPointer + 2];
        }
        deckPointer = 4;
    }

}
