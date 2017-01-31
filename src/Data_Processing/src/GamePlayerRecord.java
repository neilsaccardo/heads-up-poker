import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Neil on 15/12/2016.
 */
public class GamePlayerRecord {
    private LinkedList<LinkedList> playerActions = new LinkedList<>();
    private HashMap<String,PokerAction> mappedActions = new HashMap<>();

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
    public void addPlayerAction(String playerName, String charAction) {
        PokerAction action = mappedActions.get(charAction);
        if (action != null) {
            addPlayerAction(playerName, action);
        }
        else {
            //HANDLE ERROR
        }
    }



    public String toString() {
        return "Actions: " + playerActions;
    }
}
