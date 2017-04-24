package com.saccarn.poker.harnass;

import cards.Card;
import cards.CardSet;
import cards.HandEval;
import com.saccarn.poker.ai.ActionStrings;
import com.saccarn.poker.ai.AiAgent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Neil on 21/04/2017.
 */
public class Game {

    private AiAgent aiAgentPlayerOne = new AiAgent();
    private AiAgent aiAgentPlayerTwo= new AiAgent();


    private Card [] playerOneCards = new Card[7];
    private Card [] playerTwoCards = new Card[7];
    private Card [] fullDeckArray;
    private int deckPointer = 0;


    private Map<String, Double> playerOneModel;
    private Map<String, Double> playerTwoModel;



    private boolean isPlayerOneSmallBlind = false;
    private int playerOneStack = 10000;
    private int playerTwoStack = 10000;
    private int bigBlindAmount = 100;
    private int pot = 0;

    private boolean playerOneTurn = true;
    private Map<String, Integer> roundMapToBoardCards = new HashMap<>();

    public Game() {
        fillRounds();
    }

    public Game(AiAgent playerOne, AiAgent playerTwo, boolean whosFirst, Map<String, Double> oppModelPlayerOne,Map<String, Double> oppModelPlayerTwo) {
        aiAgentPlayerTwo = playerTwo;
        aiAgentPlayerOne = playerOne;
        playerOneTurn = whosFirst;
        playerOneModel = oppModelPlayerOne;
        playerTwoModel = oppModelPlayerTwo;
        fillRounds();
    }

    private void fillRounds() {
        roundMapToBoardCards.put(AiAgent.PRE_FLOP, 0);
        roundMapToBoardCards.put(AiAgent.FLOP, 3);
        roundMapToBoardCards.put(AiAgent.TURN, 4);
        roundMapToBoardCards.put(AiAgent.RIVER, 5);
    }

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
            action = aiAgentPlayerOne.getAction(AiAgent.PRE_FLOP, playerOneCards[0].toString(), playerOneCards[1].toString(), boardCards, playerOneStack, pot, playerTwoModel, 0, bigBlindAmount, 0, playerTwoStack, "CHECK");
            System.out.println("PREFLOP:: action1: :: " + action);
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerOneStack);
            if (checkFold(action, true)) {
                System.out.println("folded");
                return;// true if the action is from playerOne. false if from playerTwo
            }
            addToPotPlayerOne(amount);
        }
        else {
            action = getPlayerTwoAction(AiAgent.PRE_FLOP, 0, "CALL");
            System.out.println("PREFLOP:: player 2 action is: " + action);
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerTwoStack);
            if (checkFold(action, false)) {
                System.out.println("folded");
                return;// true if the action is from playerOne. false if from playerTwo
            }
            addToPotPlayerTwo(amount);
        }

        if (!isPlayerOneSmallBlind) {
            action = getPlayerTwoAction(AiAgent.PRE_FLOP, amount, action);
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerTwoStack);
            System.out.println("PREFLOP:: player 2 action is: " + action);
            if (checkFold(action, false)) {
                System.out.println("folded");
                return;// true if the action is from playerOne. false if from playerTwo
            }

            playerOneTurn = true;
            addToPotPlayerTwo(amount);
        }
        else {
            action = getPlayerOneAction(AiAgent.PRE_FLOP, amount, action);
            System.out.println("PREFLOP:: player 1 action is: " + action);
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerOneStack);
            if (checkFold(action, true)) {
                System.out.println("folded");
                return;// true if the action is from playerOne. false if from playerTwo
            }
            playerOneTurn = false;
            addToPotPlayerOne(amount);
        }

        if (action.equals(ActionStrings.ACTION_CHECK)) {
            flop(isPlayerOneSmallBlind);
            return;
        }
        if (stackCheck()) {
            flopOutCards();
            turnOutCards();
            riverOutCards();
            showDown(true);
            return;
        }
        while((!action.equals(ActionStrings.ACTION_CALL))) {
            if (playerOneTurn) {
                action = getPlayerOneAction(AiAgent.PRE_FLOP, amount, action);
                if (checkFold(action, true)) {
                    System.out.println("folded");
                    return;// true if the action is from playerOne. false if from playerTwo
                }
                amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerOneStack);
                System.out.println("PREFLOP:: player 1 action is: " + action);
                playerOneTurn = false;
                addToPotPlayerOne(amount);
            }
            else {
                action = getPlayerTwoAction(AiAgent.PRE_FLOP, amount, action);
                if (checkFold(action, false)) {
                    System.out.println("folded");
                    return;// true if the action is from playerOne. false if from playerTwo
                }
                amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerTwoStack);
                System.out.println("PREFLOP:: player 2 action is: " + action);
                playerOneTurn = true;
                addToPotPlayerTwo(amount);
            }
            if (stackCheck()) {
                flopOutCards();
                turnOutCards();
                riverOutCards();
                showDown(true);
                return;
            }
        }
        flop(isPlayerOneSmallBlind);
    }

    private void addToPotPlayerTwo(int amount) {
        int calledAmount = (playerTwoStack - playerOneStack) + amount;
        playerTwoStack = playerTwoStack - calledAmount;
        pot = pot + calledAmount;
    }

    private void addToPotPlayerOne(int amount) {
        int calledAmount = (playerOneStack- playerTwoStack) + amount;

        System.out.println("calledAmount :  " + calledAmount);
        playerOneStack = playerOneStack - calledAmount;
        pot = pot + calledAmount;
    }

    private String getPlayerTwoAction(String stageOfPlay, int amountBet, String previousAction) {
        System.out.println(roundMapToBoardCards.get(stageOfPlay));
        String [] boardCards = new String [roundMapToBoardCards.get(stageOfPlay)];
        for (int i = 0; i < boardCards.length; i++) {
            boardCards [i] = playerTwoCards [i+2].toString();
        }
        return aiAgentPlayerTwo.getAction(stageOfPlay, playerTwoCards[0].toString(), playerTwoCards[1].toString(), boardCards, playerTwoStack, pot, playerOneModel, 0, bigBlindAmount, amountBet, playerOneStack, previousAction);
    }


    private String getPlayerOneAction(String stageOfPlay, int amountBet, String previousAction) {
        System.out.println(roundMapToBoardCards.get(stageOfPlay));
        String [] boardCards = new String [roundMapToBoardCards.get(stageOfPlay)];
        for (int i = 0; i < boardCards.length; i++) {
            boardCards [i] = playerOneCards[i+2].toString();
        }
        return aiAgentPlayerOne.getAction(stageOfPlay, playerTwoCards[0].toString(), playerTwoCards[1].toString(), boardCards, playerTwoStack, pot, playerTwoModel, 0, bigBlindAmount, amountBet, playerOneStack, previousAction);
    }


    private void flop(boolean isPlayerOneSmallBlind) {
        System.out.println("FLOP");
        flopOutCards();
        String action;
        int amount;
        if (isPlayerOneSmallBlind) {
            action = getPlayerTwoAction(AiAgent.FLOP, 0, "CALL");
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerTwoStack);
            System.out.println("ON the FLOP:: player two action is " + action);
            playerOneTurn = true;
            addToPotPlayerTwo(amount);
        }
        else {
            action = getPlayerOneAction(AiAgent.FLOP, 0, "CALL");
            System.out.println("ON the FLOP:: player one action is " + action);
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerOneStack);
            playerOneTurn = false;
            addToPotPlayerOne(amount);
        }

        if (playerOneTurn) {
            action = getPlayerOneAction(AiAgent.FLOP, amount, action);
            System.out.println("ON the FLOP:: player one action is " + action);
            if (checkFold(action, true)) {
                System.out.println("folded");
                return;// true if the action is from playerOne. false if from playerTwo
            }
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerOneStack);
            playerOneTurn = false;
            addToPotPlayerOne(amount);
        }
        else {
            action = getPlayerTwoAction(AiAgent.FLOP, amount, action);
            System.out.println("ON the FLOP:: player two action is " + action);
            if (checkFold(action, false)) {
                System.out.println("folded");
                return;// true if the action is from playerOne. false if from playerTwo
            }
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerTwoStack);
            playerOneTurn = true;
            addToPotPlayerTwo(amount);
        }
        if (action.equals(ActionStrings.ACTION_CHECK)) {
            turn(isPlayerOneSmallBlind);
            return;
        }
        if (stackCheck()) {
            turnOutCards();
            riverOutCards();
            showDown(true);
            return;
        }

        while ((!action.equals(ActionStrings.ACTION_CALL))) {
            if (playerOneTurn) {
                action = getPlayerOneAction(AiAgent.FLOP, amount, action);
                if (checkFold(action, playerOneTurn)) {
                    System.out.println("folded");
                    return;// true if the action is from playerOne. false if from playerTwo
                }
                System.out.println("ON the FLOP:: player one action is " + action);
                amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerOneStack);
                playerOneTurn = false;
                addToPotPlayerOne(amount);
            }
            else {
                action = getPlayerTwoAction(AiAgent.FLOP, amount, action);
                System.out.println("ON the FLOP:: player two action is " + action);
                if (checkFold(action, playerOneTurn)) {
                    System.out.println("folded");
                    return;// true if the action is from playerOne. false if from playerTwo
                }
                amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerTwoStack);
                playerOneTurn = true;
                addToPotPlayerTwo(amount);
            }
            if (stackCheck()) {
                turnOutCards();
                riverOutCards();
                showDown(true);
                return;
            }
        }
        turn(playerOneTurn);
    }

    private void turn(boolean isPlayerOneSmallBlind) {
        System.out.println("TURN");
        turnOutCards();
        String action;
        int amount;
        if (isPlayerOneSmallBlind) {
            action = getPlayerTwoAction(AiAgent.TURN, 0, "CALL");
            System.out.println("ON the TURN:: player two action is " + action);
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerTwoStack);
            playerOneTurn = true;
            addToPotPlayerTwo(amount);
        }
        else {
            action = getPlayerOneAction(AiAgent.TURN, 0, "CALL");
            System.out.println("ON the TURN:: player one action is " + action);
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerOneStack);
            playerOneTurn = false;
            addToPotPlayerOne(amount);
        }

        if (playerOneTurn) {
            action = getPlayerOneAction(AiAgent.TURN, amount, action);
            System.out.println("ON the TURN:: player one action is " + action);
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerOneStack);
            playerOneTurn = false;
            addToPotPlayerOne(amount);
        }
        else {
            action = getPlayerTwoAction(AiAgent.TURN, amount, action);
            System.out.println("ON the TURN:: player two action is " + action);
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerTwoStack);
            playerOneTurn = true;
            addToPotPlayerTwo(amount);
        }
        if (action.equals(ActionStrings.ACTION_CHECK)) {
            river(isPlayerOneSmallBlind);
            return;
        }
        if (checkFold(action, !playerOneTurn)) {
            System.out.println("folded");
            return;// true if the action is from playerOne. false if from playerTwo
        }
        if (stackCheck()) {
            riverOutCards();
            showDown(true);
            return;
        }
        while ((!action.equals(ActionStrings.ACTION_CALL))) {
            if (playerOneTurn) {
                action = getPlayerOneAction(AiAgent.TURN, amount, action);
                System.out.println("ON the TURN:: player one action is " + action);
                if (checkFold(action, playerOneTurn)) {
                    System.out.println("folded");
                    return;// true if the action is from playerOne. false if from playerTwo
                }
                amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerOneStack);
                playerOneTurn = false;
                addToPotPlayerOne(amount);
            }
            else {
                action = getPlayerTwoAction(AiAgent.TURN, amount, action);
                System.out.println("ON the TURN:: player two action is " + action);
                if (checkFold(action, playerOneTurn)) {
                    System.out.println("folded");
                    return;// true if the action is from playerOne. false if from playerTwo
                }
                amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerTwoStack);
                playerOneTurn = true;
                addToPotPlayerTwo(amount);
            }
            if (stackCheck()) {
                riverOutCards();
                showDown(true);
                return;
            }
        }
        river(playerOneTurn);

    }

    private void river(boolean playerOneTurn) {
        System.out.println("RIVER");
        riverOutCards();
        String action;
        int amount;
        if (isPlayerOneSmallBlind) {
            action = getPlayerTwoAction(AiAgent.RIVER, 0, "CALL");
            System.out.println("ON the RIVER:: player two action is " + action);
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerTwoStack);
            playerOneTurn = true;
            addToPotPlayerTwo(amount);
        }
        else {
            action = getPlayerOneAction(AiAgent.RIVER, 0, "CALL");
            System.out.println("ON the RIVER:: player one action is " + action);
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerOneStack);
            playerOneTurn = false;
            addToPotPlayerOne(amount);
        }
        checkFold(action, (!isPlayerOneSmallBlind));

        if (playerOneTurn) {
            action = getPlayerOneAction(AiAgent.RIVER, amount, action);
            System.out.println("ON the RIVER:: player one action is " + action);
            if (checkFold(action, true)) {
                System.out.println("folded");
                return;// true if the action is from playerOne. false if from playerTwo
            }
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerOneStack);
            playerOneTurn = false;
            addToPotPlayerOne(amount);
        }
        else {
            action = getPlayerTwoAction(AiAgent.RIVER, amount, action);
            System.out.println("ON the RIVER:: player two action is " + action);
            if (checkFold(action, false)) {
                System.out.println("folded");
                return;// true if the action is from playerOne. false if from playerTwo
            }
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerTwoStack);
            playerOneTurn = true;
            addToPotPlayerTwo(amount);
        }
        if (action.equals(ActionStrings.ACTION_CHECK)) {
            showDown(isPlayerOneSmallBlind);
            return;
        }
        if (stackCheck()) {
            showDown(true);
            return;
        }
        while ((!action.equals(ActionStrings.ACTION_CALL))) {
            if (playerOneTurn) {
                action = getPlayerOneAction(AiAgent.RIVER, amount, action);
                System.out.println("ON the RIVER:: player one action is " + action);
                if (checkFold(action, true)) {
                    System.out.println("folded");
                    return;// true if the action is from playerOne. false if from playerTwo
                }
                amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerOneStack);
                playerOneTurn = false;
                addToPotPlayerOne(amount);
            }
            else {
                action = getPlayerTwoAction(AiAgent.RIVER, amount, action);
                System.out.println("ON the RIVER:: player two action is " + action);
                if (checkFold(action, false)) {
                    System.out.println("folded");
                    return;// true if the action is from playerOne. false if from playerTwo
                }

                amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerTwoStack);
                playerOneTurn = true;
                addToPotPlayerTwo(amount);
            }
            if (stackCheck()) {
                showDown(true);
                return;
            }
        }
        showDown(playerOneTurn);
    }

    private boolean stackCheck() {
        boolean areAllIn = (playerOneStack  < 1) && (playerTwoStack < 1);
        if (areAllIn) {
            playerTwoStack = 0;
            playerOneStack = 0;
            pot = 20000;
        }
        return areAllIn;
    }

    private void riverOutCards() {
        playerOneCards[6] = fullDeckArray[deckPointer];
        playerTwoCards[6] = fullDeckArray[deckPointer];
    }

    private void flopOutCards() {

        playerOneCards[2] = fullDeckArray[deckPointer];
        playerTwoCards[2] = fullDeckArray[deckPointer];
        deckPointer++;
        playerOneCards[3] = fullDeckArray[deckPointer];
        playerTwoCards[3] = fullDeckArray[deckPointer];
        deckPointer++;
        playerOneCards[4] = fullDeckArray[deckPointer];
        playerTwoCards[4] = fullDeckArray[deckPointer];
        deckPointer++;
    }

    private void turnOutCards() {
        playerOneCards[5] = fullDeckArray[deckPointer];
        playerTwoCards[5] = fullDeckArray[deckPointer];
        deckPointer++;
    }


    private void showDown(boolean playerOneTurn) {
        System.out.println("SHOWDOWN");
        checkWinner();
    }

    private void checkWinner() {
        long enHand1 = HandEval.encode(playerOneCards);
        long enHand2 = HandEval.encode(playerTwoCards);
        int rank1 = HandEval.hand7Eval(enHand1);
        int rank2 = HandEval.hand7Eval(enHand2);
        if (rank1 < rank2) {
            System.out.println("player one wins");
            addPotToPlayerOneStack();
            //player one wins
        }
        else {
            System.out.println("playertwo wins");
            addPotToPlayerTwoStack();
            //player two wins
        }
    }

    private void payBlinds() {
        if (isPlayerOneSmallBlind) {
            playerOneStack -= bigBlindAmount/2;
            System.out.println("PLAYERONESTACK " + playerOneStack);
            playerTwoStack -= bigBlindAmount;
            System.out.println("PLAYERTWOSTACK " + playerTwoStack);
        }
        else { // player pays big blind.
            playerTwoStack -= bigBlindAmount/2;
            playerOneStack -= bigBlindAmount;
        }
        pot = bigBlindAmount + (bigBlindAmount/2);
        System.out.println("POT " + pot);
    }

    private boolean checkFold(String action, boolean isPlayerOne) { //isPlayerOne should be true if the action is from player one.
        System.out.println("PLAYER ONE STACK " + playerOneStack + " PLAYER TWO STACK " + playerTwoStack + ".. POT " + pot);
        if (action.equals(ActionStrings.ACTION_FOLD)) {
            if (isPlayerOne) {
                System.out.println("PLAYER ONE FOLDED");
                System.out.println("POT: " + pot);
                addPotToPlayerTwoStack();
                System.out.println(":: PLAYER 2 STACK " + playerTwoStack);
            }
            else {
                System.out.println("PLAYER TWO FOLDED");
                System.out.println("POT: " + pot);
                addPotToPlayerOneStack();
                System.out.println(":: PLAYER 1 STACK " + playerOneStack);
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


    public int getPlayerOneStack() {
        return playerOneStack;
    }

    public int getPlayerTwoStack() {
        return playerTwoStack;
    }

    public int getBigBlindAmount() {
        return playerTwoStack;
    }


}
