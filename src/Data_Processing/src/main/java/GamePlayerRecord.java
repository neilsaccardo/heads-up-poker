import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Neil on 15/12/2016.
 */
public class GamePlayerRecord {
    private LinkedList<LinkedList> playerActions = new LinkedList<>();
    private HashMap<String,PokerAction> mappedActions = new HashMap<>();

    private LinkedList<String> cardPairPlayerOne = new LinkedList<>();
    private LinkedList<String> cardPairPlayerTwo = new LinkedList<>();

    private LinkedList<LinkedList> playerActionsPreFlop = new LinkedList<>();
    private LinkedList<LinkedList> playerActionsFlop = new LinkedList<>();
    private LinkedList<LinkedList> playerActionsTurn = new LinkedList<>();
    private LinkedList<LinkedList> playerActionsRiver = new LinkedList<>();


    public GamePlayerRecord() {
        mappedActions.put("-", PokerAction.NO_ACTION);
        mappedActions.put("B", PokerAction.BIG_BLIND);
        mappedActions.put("f", PokerAction.FOLD);
        mappedActions.put("k", PokerAction.CHECK);
        mappedActions.put("r", PokerAction.RAISE);
        mappedActions.put("A", PokerAction.ALL_IN);
        mappedActions.put("Q", PokerAction.QUIT);
        mappedActions.put("K", PokerAction.QUIT);
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
            stageOfPlay.remove(stageOfPlay.size()-2);
            stageOfPlay.add(stageOfPlay.size()-1, l);
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
        addPlayerAction(playerName, charAction, playerActionsFlop);
    }

    public void addPlayerActionTurn(String playerName, String charAction) {
        addPlayerAction(playerName, charAction, playerActionsTurn);
    }

    public void addPlayerActionRiver(String playerName, String charAction) {
        addPlayerAction(playerName, charAction, playerActionsRiver);
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
