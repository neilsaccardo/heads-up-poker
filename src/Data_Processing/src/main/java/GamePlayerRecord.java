import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Neil on 15/12/2016.
 */
public class GamePlayerRecord {
    private LinkedList<LinkedList> playerActions = new LinkedList<>();
    private HashMap<String,PokerAction> mappedActions = new HashMap<>();
    private HashMap<PokerStage, LinkedList> mappedStagesToList = new HashMap<>();
    private LinkedList<String> cardPairPlayerOne = new LinkedList<>();
    private LinkedList<String> cardPairPlayerTwo = new LinkedList<>();

    private LinkedList<LinkedList> playerActionsPreFlop = new LinkedList<>();
    private LinkedList<LinkedList> playerActionsFlop = new LinkedList<>();
    private LinkedList<LinkedList> playerActionsTurn = new LinkedList<>();
    private LinkedList<LinkedList> playerActionsRiver = new LinkedList<>();

    private List<String> playerNames = new LinkedList<>();

    private int numBetRaisesPreFlopPos1 = 0;
    private int numBetRaisesFlopPos1 = 0;
    private int numBetRaisesTurnPos1 = 0;
    private int numBetRaisesRiverPos1 = 0;
    private int numBetRaisesTotalPos1 = 0;

    private int numBetRaisesPreFlopPos2 = 0;
    private int numBetRaisesFlopPos2 = 0;
    private int numBetRaisesTurnPos2 = 0;
    private int numBetRaisesRiverPos2 = 0;
    private int numBetRaisesTotalPos2 = 0;

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

    private void addPlayerAction(String playerName, PokerAction action) {
        LinkedList l = new LinkedList();
        l.add(playerName);
        l.add(action);
        if(action == PokerAction.ALL_IN) {
            playerActions.remove(playerActions.size()-2);
            playerActions.add(playerActions.size()-1, l);
        } else {
            playerActions.add(l);
        }
    }

    private void addPlayerAction(String playerName, PokerAction action, LinkedList stageOfPlay) {
        LinkedList l = new LinkedList();
        l.add(playerName);
        l.add(action);
        if(action == PokerAction.BIG_BLIND) {
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

    public void addPlayerAction(String playerName, String charAction) {
        PokerAction action = mappedActions.get(charAction);
        if (action != null) {
            addPlayerAction(playerName, action);
        }
        else {
            //HANDLE ERROR
        }
    }

    public void addPlayerAction(String playerName, String charAction, LinkedList stageOfPlay) {
        PokerAction action = mappedActions.get(charAction);
        if (action != null) {
            addPlayerAction(playerName, action, stageOfPlay);
        }
        else {
            //HANDLE ERROR
        }
    }

    public void addPlayerActionPreFlop(String playerName, String charAction) {
        addPlayerAction(playerName, charAction, playerActionsPreFlop);
    }

    public void addPlayerActionFlop(String playerName, String charAction) {
        System.out.println("FLOP");
        addPlayerAction(playerName, charAction, playerActionsFlop);
        System.out.println(playerActionsFlop);
    }

    public void addPlayerActionTurn(String playerName, String charAction) {
        addPlayerAction(playerName, charAction, playerActionsTurn);
    }

    public void addPlayerActionRiver(String playerName, String charAction) {
        addPlayerAction(playerName, charAction, playerActionsRiver);
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
        int playerIndexInList = 0;
        int actionIndexInList = 1;
        doPreFlopPreComputations();
        doFlopPreComputations();
        doTurnComputations();
        doRiverComputations();
        System.out.println(playerNames.get(0) + "Player 1bet/raises: " + numBetRaisesFlopPos1);
        System.out.println(playerNames.get(1)+ " Player 2bet/raises: " + numBetRaisesPreFlopPos2);
    }

    private void doRiverComputations() {
        for (int i = 0; i < playerActionsRiver.size(); i++) {
            LinkedList list = playerActionsRiver.get(i);
            if(list.get(1) == PokerAction.BET || list.get(1) == PokerAction.RAISE) {
                //increment appropriate thing
                if (i % 2 == 0) { //player 2
                    numBetRaisesRiverPos1++;
                } else {
                    numBetRaisesRiverPos2++;
                }
            }
        }

    }

    private void doTurnComputations() {
        for (int i = 0; i < playerActionsTurn.size(); i++) {
            LinkedList list = playerActionsTurn.get(i);
            if(list.get(1) == PokerAction.BET || list.get(1) == PokerAction.RAISE) {
                //increment appropriate thing
                if (i % 2 == 0) { //player 2
                    numBetRaisesTurnPos1++;
                } else {
                    numBetRaisesTurnPos2++;
                }
            }
        }


    }

    private void doFlopPreComputations() {
        for (int i = 0; i < playerActionsFlop.size(); i++) {
            LinkedList list = playerActionsFlop.get(i);
            if(list.get(1) == PokerAction.BET || list.get(1) == PokerAction.RAISE) {
                //increment appropriate thing
                if (i % 2 == 0) { //player 2
                    numBetRaisesFlopPos1++;
                } else {
                    numBetRaisesFlopPos2++;
                }
            }
        }
    }

    private void doPreFlopPreComputations() {
        for (int i = 0; i < playerActionsPreFlop.size(); i++) {
            LinkedList list = playerActionsPreFlop.get(i);
            if(list.get(1) == PokerAction.BET || list.get(1) == PokerAction.RAISE) {
                //increment appropriate thing
                if (i % 2 == 0) { //player 2
                    numBetRaisesFlopPos1++;
                } else {
                    numBetRaisesPreFlopPos2++;
                }
            }
        }
    }
}
