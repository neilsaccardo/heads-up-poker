package com.saccarn.poker.harnass;

import com.saccarn.poker.cards.Card;
import com.saccarn.poker.cards.CardSet;
import com.saccarn.poker.cards.HandEval;
import com.saccarn.poker.ai.ActionStrings;
import com.saccarn.poker.ai.AiAgent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Game simulates a hand between two ai agent players. Each player starts by default with 10000 chips and the big blind is 100 chips (i.e. 100 big blinds).
 * Created by Neil on 21/04/2017.
 */
public class Game {

    private AiAgent aiAgentPlayerOne;
    private AiAgent aiAgentPlayerTwo;

    private Card [] playerOneCards = new Card[7];
    private Card [] playerTwoCards = new Card[7];
    private Card [] fullDeckArray;
    private int deckPointer = 0;


    private Map<String, Double> playerOneModel;
    private Map<String, Double> playerTwoModel;

    private boolean isPlayerOneSmallBlind;
    private int startingStack = 10000;
    private int playerOneStack = 10000;
    private int playerTwoStack = 10000;
    private int bigBlindAmount = 100;
    private int pot = 0;

    private boolean playerOneTurn = true;
    private Map<String, Integer> roundMapToBoardCards = new HashMap<>();

    public Game() {
        fillRounds();
    }

    public Game(AiAgent playerOne, AiAgent playerTwo, boolean whosFirst, Map<String, Double> oppModelPlayerOne,Map<String, Double> oppModelPlayerTwo, boolean isSmallBlind) {
        aiAgentPlayerTwo = playerTwo;
        aiAgentPlayerOne = playerOne;
        playerOneTurn = whosFirst;
        playerOneModel = oppModelPlayerOne;
        playerTwoModel = oppModelPlayerTwo;
        isPlayerOneSmallBlind = isSmallBlind;
        fillRounds();
    }

    private void fillRounds() {
        roundMapToBoardCards.put(AiAgent.PRE_FLOP, 0);
        roundMapToBoardCards.put(AiAgent.FLOP, 3);
        roundMapToBoardCards.put(AiAgent.TURN, 4);
        roundMapToBoardCards.put(AiAgent.RIVER, 5);
    }

    /**
     * Plays out one hand.
     */
    public void playHand() {
        dealOutCards();
        String action;
        payBlinds();
        String [] boardCards = {};
        int amount;
        if (isPlayerOneSmallBlind) {
            action = aiAgentPlayerOne.getAction(AiAgent.PRE_FLOP, playerOneCards[0].toString(), playerOneCards[1].toString(), boardCards, playerOneStack, pot, playerTwoModel, 0, bigBlindAmount, 0, playerTwoStack, "CHECK");
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerOneStack);
            if (checkFold(action, true)) {
                return;// true if the action is from playerOne. false if from playerTwo
            }
            addToPotPlayerOne(amount);
        }
        else {
            action = getPlayerTwoAction(AiAgent.PRE_FLOP, 0, "CALL");
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerTwoStack);
            if (checkFold(action, false)) {
                return;// true if the action is from playerOne. false if from playerTwo
            }
            addToPotPlayerTwo(amount);
        }

        if (!isPlayerOneSmallBlind) {
            action = getPlayerTwoAction(AiAgent.PRE_FLOP, amount, action);
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerTwoStack);
            if (checkFold(action, false)) {
                return;// true if the action is from playerOne. false if from playerTwo
            }
            playerOneTurn = true;
            addToPotPlayerTwo(amount);
        }
        else {
            action = getPlayerOneAction(AiAgent.PRE_FLOP, amount, action);
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerOneStack);
            if (checkFold(action, true)) {
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
            if (!playerOneTurn) {
                action = getPlayerOneAction(AiAgent.PRE_FLOP, amount, action);
                if (checkFold(action, true)) {
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
        int calledAmount = (playerOneStack - playerTwoStack) + amount;
        playerOneStack = playerOneStack - calledAmount;
        pot = pot + calledAmount;
    }

    private String getPlayerTwoAction(String stageOfPlay, int amountBet, String previousAction) {
        String [] boardCards = new String [roundMapToBoardCards.get(stageOfPlay)];
        for (int i = 0; i < boardCards.length; i++) {
            boardCards [i] = playerTwoCards [i+2].toString();
        }
        return aiAgentPlayerTwo.getAction(stageOfPlay, playerTwoCards[0].toString(), playerTwoCards[1].toString(), boardCards, playerTwoStack, pot, playerOneModel, 0, bigBlindAmount, amountBet, playerOneStack, previousAction);
    }


    private String getPlayerOneAction(String stageOfPlay, int amountBet, String previousAction) {
        String [] boardCards = new String [roundMapToBoardCards.get(stageOfPlay)];
        for (int i = 0; i < boardCards.length; i++) {
            boardCards [i] = playerOneCards[i+2].toString();
        }
        return aiAgentPlayerOne.getAction(stageOfPlay, playerTwoCards[0].toString(), playerTwoCards[1].toString(), boardCards, playerTwoStack, pot, playerTwoModel, 0, bigBlindAmount, amountBet, playerOneStack, previousAction);
    }


    private void flop(boolean isPlayerOneSmallBlind) {
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

        if (!playerOneTurn) {
            action = getPlayerOneAction(AiAgent.FLOP, amount, action);
            System.out.println("ON the FLOP:: player one action is " + action);
            if (checkFold(action, true)) {
                return;// true if the action is from playerOne. false if from playerTwo
            }
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerOneStack);
            playerOneTurn = false;
            addToPotPlayerOne(amount);
        }
        else {
            action = getPlayerTwoAction(AiAgent.FLOP, amount, action);
                System.out.println("ON the FLOP:: THIS SHOULD APPEAR HALF player two action is " + action);
            if (checkFold(action, false)) {
//                System.out.println("folded");
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
            if (!playerOneTurn) {
                action = getPlayerOneAction(AiAgent.FLOP, amount, action);
                if (checkFold(action, playerOneTurn)) {
                    return;// true if the action is from playerOne. false if from playerTwo
                }
                amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerOneStack);
                playerOneTurn = false;
                addToPotPlayerOne(amount);
            }
            else {
                action = getPlayerTwoAction(AiAgent.FLOP, amount, action);
                if (checkFold(action, playerOneTurn)) {
                    return; // true if the action is from playerOne. false if from playerTwo
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
        turn(isPlayerOneSmallBlind);
    }

    private void turn(boolean isPlayerOneSmallBlind) {
        System.out.println("TURN:::::-- " );
        if (isPlayerOneSmallBlind) {
            System.out.println("PLAYER TWO IS FIRST");
        }
        else {
            System.out.println("PLAYER ONE IS FIRST");
        }
        turnOutCards();
        String action;
        int amount;
        if (isPlayerOneSmallBlind) {
            action = getPlayerTwoAction(AiAgent.TURN, 0, "CALL");
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerTwoStack);
            playerOneTurn = true;
            addToPotPlayerTwo(amount);
        }
        else {
            action = getPlayerOneAction(AiAgent.TURN, 0, "CALL");
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerOneStack);
            playerOneTurn = false;
            addToPotPlayerOne(amount);
        }

        if (!playerOneTurn) {
            action = getPlayerOneAction(AiAgent.TURN, amount, action);
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerOneStack);
            playerOneTurn = false;
            addToPotPlayerOne(amount);
        }
        else {
            action = getPlayerTwoAction(AiAgent.TURN, amount, action);
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerTwoStack);
            playerOneTurn = true;
            addToPotPlayerTwo(amount);
        }
        if (action.equals(ActionStrings.ACTION_CHECK)) {
            river(isPlayerOneSmallBlind);
            return;
        }
        if (stackCheck()) {
            riverOutCards();
            showDown(true);
            return;
        }
        while ((!action.equals(ActionStrings.ACTION_CALL))) {
            if (!playerOneTurn) {
                action = getPlayerOneAction(AiAgent.TURN, amount, action);
                if (checkFold(action, playerOneTurn)) {
                    return;// true if the action is from playerOne. false if from playerTwo
                }
                amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerOneStack);
                playerOneTurn = false;
                addToPotPlayerOne(amount);
            }
            else {
                action = getPlayerTwoAction(AiAgent.TURN, amount, action);
                if (checkFold(action, playerOneTurn)) {
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
        river(isPlayerOneSmallBlind);

    }

    private void river(boolean isPlayerOneSmallBlind) {
        System.out.println("RIVER");
        if (isPlayerOneSmallBlind) {
            System.out.println("PLAYER TWO IS FIRST");
        }
        else {
            System.out.println("PLAYER ONE IS FIRST");
        }
        riverOutCards();
        String action;
        int amount;
        if (isPlayerOneSmallBlind) {
            action = getPlayerTwoAction(AiAgent.RIVER, 0, "CALL");
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerTwoStack);
            playerOneTurn = true;
            addToPotPlayerTwo(amount);
        }
        else {
            action = getPlayerOneAction(AiAgent.RIVER, 0, "CALL");
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerOneStack);
            playerOneTurn = false;
            addToPotPlayerOne(amount);
        }
        if (!playerOneTurn) {
            action = getPlayerOneAction(AiAgent.RIVER, amount, action);
            if (checkFold(action, true)) {
                return;// true if the action is from playerOne. false if from playerTwo
            }
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerOneStack);
            playerOneTurn = false;
            addToPotPlayerOne(amount);
        }
        else {
            action = getPlayerTwoAction(AiAgent.RIVER, amount, action);
            if (checkFold(action, false)) {
                return;// true if the action is from playerOne. false if from playerTwo
            }
            amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerTwoStack);
            playerOneTurn = true;
            addToPotPlayerTwo(amount);
            System.out.println("PLAYER TWO ACTION IS HERE");
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
            if (!playerOneTurn) {
                action = getPlayerOneAction(AiAgent.RIVER, amount, action);
                if (checkFold(action, true)) {
                    return;// true if the action is from playerOne. false if from playerTwo
                }
                amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerOneStack);
                playerOneTurn = false;
                addToPotPlayerOne(amount);
            }
            else {
                action = getPlayerTwoAction(AiAgent.RIVER, amount, action);
                if (checkFold(action, false)) {
                    return;// true if the action is from playerOne. false if from playerTwo
                }
                amount = BetRaiseAmount.calculateBetRaiseAmount(action, pot, playerTwoStack);
                playerOneTurn = true;
                addToPotPlayerTwo(amount);
                System.out.println("PLAYER TWO @@@@@@@@@@@@@@@@@@");
            }
            if (stackCheck()) {
                showDown(true);
                return;
            }
        }
        showDown(playerOneTurn);
    }

    /**
     * Checks if either players is all in
     * @return if either player has no chips left.
     */
    private boolean stackCheck() {
        boolean areAllIn = (playerOneStack  < 1) && (playerTwoStack < 1);
        if (areAllIn) {
            playerTwoStack = 0;
            playerOneStack = 0;
            pot = 2 * startingStack;
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
        System.out.println("SHOWDOWN: PLAYER ONE:: " + Arrays.asList(playerOneCards) + " PLAYER TWO:: " + Arrays.asList(playerTwoCards));
        if (rank1 == rank2) {
            System.out.println("DRAW POT");
            drawPot();
            return;
        }
        if (rank1 > rank2) {
            System.out.println("player one wins");
            addPotToPlayerOneStack();
            //player one wins
        }
        else {
            System.out.println("player two wins");
            addPotToPlayerTwoStack();
            //player two wins
        }
    }

    private void drawPot() {
        playerOneStack = startingStack;
        playerTwoStack = startingStack;
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

    private boolean checkFold(String action, boolean isPlayerOne) { // isPlayerOne should be true if the action is from player one.
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
        if (playerOneStack < 0) {
            playerOneStack = 0;
        }
        playerTwoStack = pot + playerTwoStack;
    }

    private void addPotToPlayerOneStack() {
        if (playerTwoStack < 0) {
            playerTwoStack = 0;
        }
        playerOneStack = pot + playerOneStack;
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
        if (playerOneStack < 0) {
            return 0;
        }
        return playerOneStack;
    }

    public int getPlayerTwoStack() {
        if (playerTwoStack < 0) {
            return 0;
        }
        return playerTwoStack;
    }

    public int getBigBlindAmount() {
        return bigBlindAmount;
    }

    public int getStartingStack() { return startingStack; }
}
