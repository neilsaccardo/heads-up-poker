package com.saccarn.poker.harnass;

import cards.Card;
import cards.CardSet;
import com.saccarn.poker.ai.ActionStrings;
import com.saccarn.poker.ai.AiAgent;
import com.saccarn.poker.ai.betpassdeterminer.BetPassActionValues;
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
        int amount;
        if (isPlayerOneSmallBlind) {
            action = aiAgentPlayerOne.getAction(AiAgent.PRE_FLOP, playerOneCards[0].toString(), playerOneCards[1].toString(), boardCards, playerOneStack, pot, playerTwoModel, 0, bigBlindAmount, 0, playerTwoStack, "CalL");
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerOneStack);
            checkFold(action, true);    // true if the action is from playerOne. false if from playerTwo
        }
        else {
            action = getPlayerTwoAction(AiAgent.PRE_FLOP, 0, "CALL");
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerTwoStack);
            checkFold(action, false);    // true if the action is from playerOne. false if from playerTwo
        }

        if (!isPlayerOneSmallBlind) {
            action = getPlayerTwoAction(AiAgent.PRE_FLOP, amount, action);
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerTwoStack);
            checkFold(action, false);    // true if the action is from playerOne. false if from playerTwo
            playerOneTurn = true;
        }
        else {
            action = getPlayerOneAction(AiAgent.PRE_FLOP, amount, action);
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerOneStack);
            checkFold(action, true);    // true if the action is from playerOne. false if from playerTwo
            playerOneTurn = false;
        }

        if (action.equals(ActionStrings.ACTION_CHECK)) {
            flop(isPlayerOneSmallBlind);
        }
        while((!action.equals(ActionStrings.ACTION_CALL))) {
            if (playerOneTurn) {
                amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerOneStack);
                action = getPlayerOneAction(AiAgent.PRE_FLOP, amount, action);
                playerOneTurn = false;
            }
            else {
                action = getPlayerTwoAction(AiAgent.PRE_FLOP, amount, action);
                amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerTwoStack);
                playerOneTurn = true;
            }
            checkFold(action, !playerOneTurn);
        }
        flop(isPlayerOneSmallBlind);
    }

    private String getPlayerTwoAction(String stageOfPlay, int amountBet, String previousAction) {
        String [] boardCards = new String [playerTwoCards.length-2];
        for (int i = 0; i < boardCards.length; i++) {
            boardCards [i] = playerTwoCards [i-2].toString();
        }
        return aiAgentPlayerTwo.getAction(stageOfPlay, playerTwoCards[0].toString(), playerTwoCards[1].toString(), boardCards, playerTwoStack, pot, playerOneModel, 0, bigBlindAmount, amountBet, playerOneStack, previousAction);
    }


    private String getPlayerOneAction(String stageOfPlay, int amountBet, String previousAction) {
        String [] boardCards = new String [playerOneCards.length-2];
        for (int i = 0; i < boardCards.length; i++) {
            boardCards [i] = playerOneCards[i-2].toString();
        }
        return aiAgentPlayerOne.getAction(stageOfPlay, playerTwoCards[0].toString(), playerTwoCards[1].toString(), boardCards, playerTwoStack, pot, playerOneModel, 0, bigBlindAmount, amountBet, playerOneStack, previousAction);
    }


    private void flop(boolean isPlayerOneSmallBlind) {
        flopOutCards();
        String action;
        int amount;
        if (isPlayerOneSmallBlind) {
            action = getPlayerTwoAction(AiAgent.FLOP, 0, "CALL");
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerTwoStack);
            playerOneTurn = true;
        }
        else {
            action = getPlayerOneAction(AiAgent.FLOP, 0, "CALL");
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerOneStack);
            playerOneTurn = false;
        }

        if (playerOneTurn) {
            action = getPlayerOneAction(AiAgent.FLOP, amount, action);
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerOneStack);
            playerOneTurn = false;
        }
        else {
            action = getPlayerTwoAction(AiAgent.FLOP, 0, action);
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerTwoStack);
            playerOneTurn = true;
        }
        if (action.equals(ActionStrings.ACTION_CHECK)) {
            turn(isPlayerOneSmallBlind);
        }

        while ((!action.equals(ActionStrings.ACTION_CALL))) {
            if (playerOneTurn) {
                action = getPlayerOneAction(AiAgent.FLOP, amount, action);
                amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerOneStack);
                playerOneTurn = false;
            }
            else {
                action = getPlayerTwoAction(AiAgent.FLOP, amount, action);
                amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerTwoStack);
                playerOneTurn = true;
            }
        }
        turn(playerOneTurn);
    }

    private void turn(boolean isPlayerOneSmallBlind) {
        turnOutCards();
        String action;
        int amount;
        if (isPlayerOneSmallBlind) {
            action = getPlayerTwoAction(AiAgent.TURN, 0, "CALL");
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerTwoStack);
            playerOneTurn = true;
        }
        else {
            action = getPlayerOneAction(AiAgent.TURN, 0, "CALL");
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerOneStack);
            playerOneTurn = false;
        }

        if (playerOneTurn) {
            action = getPlayerOneAction(AiAgent.TURN, amount, action);
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerOneStack);
            playerOneTurn = false;
        }
        else {
            action = getPlayerTwoAction(AiAgent.TURN, amount, action);
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerTwoStack);
            playerOneTurn = true;
        }
        if (action.equals(ActionStrings.ACTION_CHECK)) {
            river(isPlayerOneSmallBlind);
        }

        while ((!action.equals(ActionStrings.ACTION_CALL))) {
            if (playerOneTurn) {
                action = getPlayerOneAction(AiAgent.TURN, amount, action);
                amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerOneStack);
                playerOneTurn = false;
            }
            else {
                action = getPlayerTwoAction(AiAgent.TURN, 0, action);
                amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerTwoStack);
                playerOneTurn = true;
            }
        }
        river(playerOneTurn);

    }

    private void river(boolean playerOneTurn) {
        riverOutCards();
        String action;
        int amount;
        if (isPlayerOneSmallBlind) {
            action = getPlayerTwoAction(AiAgent.RIVER, 0, "CALL");
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerTwoStack);
            playerOneTurn = true;
        }
        else {
            action = getPlayerOneAction(AiAgent.RIVER, 0, "CALL");
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerOneStack);
            playerOneTurn = false;
        }
        checkFold(action, (!isPlayerOneSmallBlind));

        if (playerOneTurn) {
            action = getPlayerOneAction(AiAgent.RIVER, 0, action);
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerOneStack);
            playerOneTurn = false;
        }
        else {
            action = getPlayerTwoAction(AiAgent.RIVER, 0, action);
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerTwoStack);
            playerOneTurn = true;
        }
        if (action.equals(ActionStrings.ACTION_CHECK)) {
            showDown(isPlayerOneSmallBlind);
        }

        while ((!action.equals(ActionStrings.ACTION_CALL))) {
            if (playerOneTurn) {
                action = getPlayerOneAction(AiAgent.RIVER, 0, action);
                playerOneTurn = false;
            }
            else {
                action = getPlayerTwoAction(AiAgent.RIVER, 0, action);
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
