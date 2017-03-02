package com.saccarn.poker.dataprocessing;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Neil on 15/12/2016.
 */
public class GamePlayerRecord {
    private LinkedList<LinkedList> playerActions = new LinkedList<>();
    private HashMap<String, PokerAction> mappedActions = new HashMap<>();
    private HashMap<PokerStage, LinkedList> mappedStagesToList = new HashMap<>();
    private LinkedList<String> cardPairPlayerOne = new LinkedList<>();
    private LinkedList<String> cardPairPlayerTwo = new LinkedList<>();

    private LinkedList<LinkedList> playerActionsPreFlop = new LinkedList<>();
    private LinkedList<LinkedList> playerActionsFlop = new LinkedList<>();

    private LinkedList<LinkedList> playerActionsTurn = new LinkedList<>();
    private LinkedList<LinkedList> playerActionsRiver = new LinkedList<>();

    private List<String> playerNames = new LinkedList<>();
    private final int playerIndexInList = 0;
    private final int actionIndexInList = 1;

    private Player playerOne = new Player();
    private Player playerTwo = new Player();
    private int totalActionsPos1;
    private int totalActionsPos2;


    public List<String> getPlayerNames() {
        return playerNames;
    }

    public LinkedList<LinkedList> getPlayerActionsPreFlop() {
        return playerActionsPreFlop;
    }

    public LinkedList<LinkedList> getPlayerActionsFlop() {
        return playerActionsFlop;
    }

    public LinkedList<LinkedList> getPlayerActionsTurn() {
        return playerActionsTurn;
    }

    public LinkedList<LinkedList> getPlayerActionsRiver() {
        return playerActionsRiver;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public LinkedList<String> getCardPairPlayerOne() {
        return cardPairPlayerOne;
    }

    public LinkedList<String> getCardPairPlayerTwo() {
        return cardPairPlayerTwo;
    }

    public GamePlayerRecord() {
        mappedActions.put("-", PokerAction.NO_ACTION);
        mappedActions.put("B", PokerAction.BIG_BLIND);
        mappedActions.put("b", PokerAction.BET);
        mappedActions.put("c", PokerAction.CALL);
        mappedActions.put("f", PokerAction.FOLD);
        mappedActions.put("k", PokerAction.CHECK);
        mappedActions.put("r", PokerAction.RAISE);
        mappedActions.put("A", PokerAction.ALL_IN);
        mappedActions.put("Q", PokerAction.QUIT);

        mappedStagesToList.put(PokerStage.PREFLOP, playerActionsPreFlop);
        mappedStagesToList.put(PokerStage.FLOP, playerActionsFlop);
        mappedStagesToList.put(PokerStage.TURN, playerActionsTurn);
        mappedStagesToList.put(PokerStage.RIVER, playerActionsRiver);
    }

    private void addPlayerAction(String playerName, PokerAction action, LinkedList stageOfPlay) {
        LinkedList l = new LinkedList();
        l.add(playerName);
        l.add(action);
        if(action == PokerAction.BIG_BLIND || playerNames.size() == 1) {
            playerNames.add(playerName);
        }
        if(action == PokerAction.ALL_IN) {
//            System.out.println("WE'RE HERE: " + stageOfPlay);
            stageOfPlay.remove(stageOfPlay.size() - 2);
            stageOfPlay.add(stageOfPlay.size() - 1, l);
        } else {
            stageOfPlay.add(l);
        }
    }

    private void addPlayerAction(String playerName, String charAction, LinkedList stageOfPlay) {
        PokerAction action = mappedActions.get(charAction);
        if (action != null) {
            addPlayerAction(playerName, action, stageOfPlay);
        }
        else {
            //HANDLE ERROR
        }
    }

    public void addPlayerAction(String playerName, String charAction, PokerStage stage) {
        addPlayerAction(playerName, charAction, mappedStagesToList.get(stage));
    }

    public String toString() {
        return "Actions: " + "Preflop: " + playerActionsPreFlop + " Flop: " + playerActionsFlop
                + " Turn " + playerActionsTurn + " River: " + playerActionsRiver
                + "Cards Player 1: " + cardPairPlayerOne + " Cards Player 2 " + cardPairPlayerTwo;
    }

    public void addCardsPlayerOne(String s, String s1) {
        cardPairPlayerOne.add(s);
        cardPairPlayerOne.add(s1);
    }

    public void addCardsPlayerTwo(String s, String s1) {
        cardPairPlayerTwo.add(s);
        cardPairPlayerTwo.add(s1);
    }

    public void doPreComputations() {
        if (playerNames.size() == 0) {
            return;
        }
        playerOne.setName(playerNames.get(0));
        playerTwo.setName(playerNames.get(1));
        doPreFlopPreComputations();
        doFlopPreComputations();
        doTurnComputations();
        doRiverComputations();
        playerOne.setTotalActions(totalActionsPos1);
        playerTwo.setTotalActions(totalActionsPos2);
    }

    private void doRiverComputations() {
        int numBetRaisesRiverPos1 = 0;
        int numBetRaisesRiverPos2 = 0;
        int numTotalActionsRiverPos1 = 0;
        int numTotalActionsRiverPos2 = 0;
        for (int i = 0; i < playerActionsRiver.size(); i++) {
            LinkedList list = playerActionsRiver.get(i);
            if(list.get(actionIndexInList) == PokerAction.BET || list.get(actionIndexInList) == PokerAction.RAISE) {
                //increment appropriate thing
                if (i % 2 == 0) { //player 2
                    numBetRaisesRiverPos1++;
                } else {
                    numBetRaisesRiverPos2++;
                }
            }
            if (list.get(actionIndexInList) != PokerAction.NO_ACTION ) {
                incrementTotalActions(i);
                numTotalActionsRiverPos1++;
                numTotalActionsRiverPos2++;
            }
        }
        playerOne.setNumBetRaisesRiver(numBetRaisesRiverPos1);
        playerTwo.setNumBetRaisesRiver(numBetRaisesRiverPos2);
        playerOne.setTotalNumActionsRiver(numTotalActionsRiverPos1);
        playerTwo.setTotalNumActionsRiver(numTotalActionsRiverPos2);
    }

    private void doTurnComputations() {
        int numBetRaisesTurnPos1 = 0;
        int numBetRaisesTurnPos2 = 0;
        int numTotalActionsTurnPos1 = 0;
        int numTotalActionsTurnPos2 = 0;
        for (int i = 0; i < playerActionsTurn.size(); i++) {
            LinkedList list = playerActionsTurn.get(i);
            if(list.get(actionIndexInList) == PokerAction.BET || list.get(actionIndexInList) == PokerAction.RAISE) {
                //increment appropriate thing
                if (i % 2 == 0) { //player 2
                    numBetRaisesTurnPos1++;
                } else {
                    numBetRaisesTurnPos2++;
                }
            }
            if (list.get(actionIndexInList) != PokerAction.NO_ACTION ) {
                incrementTotalActions(i);
                numTotalActionsTurnPos1++;
                numTotalActionsTurnPos2++;
            }
        }
        playerOne.setNumBetRaisesTurn(numBetRaisesTurnPos1);
        playerTwo.setNumBetRaisesTurn(numBetRaisesTurnPos2);
        playerOne.setTotalNumActionsTurn(numTotalActionsTurnPos1);
        playerTwo.setTotalNumActionsTurn(numTotalActionsTurnPos2);
    }

    private void incrementTotalActions(int i) {
        //increment appropriate thing
        if (i % 2 == 0) { //player 2
            totalActionsPos1++;
        } else {
            totalActionsPos2++;
        }
    }

    private void doFlopPreComputations() {
        int numBetRaisesFlopPos1 = 0;
        int numBetRaisesFlopPos2 = 0;
        int numTotalActionsFlopPos1 = 0;
        int numTotalActionsFlopPos2 = 0;
        for (int i = 0; i < playerActionsFlop.size(); i++) {
            LinkedList list = playerActionsFlop.get(i);
            if(list.get(actionIndexInList) == PokerAction.BET || list.get(actionIndexInList) == PokerAction.RAISE) {
                //increment appropriate thing
                if (i % 2 == 0) { //player 2
                    numBetRaisesFlopPos1++;
                } else {
                    numBetRaisesFlopPos2++;
                }
            }
            if (list.get(actionIndexInList) != PokerAction.NO_ACTION ) {
                incrementTotalActions(i);
                numTotalActionsFlopPos1++;
                numTotalActionsFlopPos2++;
            }
        }
        playerOne.setNumBetRaisesFlop(numBetRaisesFlopPos1);
        playerTwo.setNumBetRaisesFlop(numBetRaisesFlopPos2);
        playerOne.setTotalNumActionsFlop(numTotalActionsFlopPos1);
        playerTwo.setTotalNumActionsFlop(numTotalActionsFlopPos2);
    }

    private void doPreFlopPreComputations() {
        int numBetRaisesPreFlopPos1 = 0;
        int numBetRaisesPreFlopPos2 = 0;
        int numTotalActionsPreFlopPos1 = 0;
        int numTotalActionsPreFlopPos2 = 0;
        for (int i = 0; i < playerActionsPreFlop.size(); i++) {
            LinkedList list = playerActionsPreFlop.get(i);
            if(list.get(actionIndexInList) == PokerAction.BET || list.get(actionIndexInList) == PokerAction.RAISE) {
                //increment appropriate thing
                if (i % 2 == 0) { //player 2
                    numBetRaisesPreFlopPos1++;
                } else {
                    numBetRaisesPreFlopPos2++;
                }
            }
            if (list.get(actionIndexInList) != PokerAction.NO_ACTION ) {
                incrementTotalActions(i);
                numTotalActionsPreFlopPos1++;
                numTotalActionsPreFlopPos2++;
            }
        }
        playerOne.setNumBetRaisesPreFlop(numBetRaisesPreFlopPos1);
        playerTwo.setNumBetRaisesPreFlop(numBetRaisesPreFlopPos2);
        playerOne.setTotalNumActionsPreFlop(numTotalActionsPreFlopPos1);
        playerTwo.setTotalNumActionsPreFlop(numTotalActionsPreFlopPos2);
    }

    public void setPlayerOneWinner() {
        playerOne.setWinner();
        playerTwo.setLoser();
    }

    public void setPlayerTwoWinner() {
        playerTwo.setWinner();
        playerOne.setLoser();
    }
}
