import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

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
        if(action == PokerAction.ALL_IN) {
            System.out.println("WE'RE HERE: " + stageOfPlay);
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
                + " Turn " + playerActionsTurn + " River: " + playerActionsRiver;
    }

    public void addCardsPlayerOne(String s, String s1) {
        cardPairPlayerOne.add(s);
        cardPairPlayerOne.add(s1);
    }

    public void addCardsPlayerTwo(String s, String s1) {
        cardPairPlayerTwo.add(s);
        cardPairPlayerTwo.add(s1);
    }
}
