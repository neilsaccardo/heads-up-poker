import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Neil on 15/12/2016.
 */
public class GameMaker {

    private String pdb = "pdb.";
    private File hdb = new File("C:\\Data\\hdb");
    private File hroster = new File("C:\\Data\\hroster");

    private Scanner inHroster = new Scanner(hroster);

    public GameMaker() throws FileNotFoundException {
    }

    public void readAllGames() throws FileNotFoundException {
        while(inHroster.hasNextLine()) {
            String gameId = inHroster.next();
            int numPlayers = Integer.parseInt(inHroster.next());
            if (numPlayers == 2) {
                readGame(gameId, numPlayers);
            }
            inHroster.nextLine();
        }
    }

    public void readGame(String gameId, int numPlayers) throws FileNotFoundException {
//        String gameId = inHroster.next();
//        int numPlayers = Integer.parseInt(inHroster.next());
        String [] players = new String[numPlayers];
        for(int i = 0; i < numPlayers; i++) {
            players[i] = inHroster.next();
            System.out.println(players[i]);
        }
        System.out.println(gameId + " " + numPlayers);
        getPlayerActions(players[0], players[1], gameId);
    }

    public void getPlayerActions(String name1, String name2,String gameId) throws FileNotFoundException {
        PlayerActions pa = new PlayerActions();
        pa.getAction(name1, name2, gameId);
    }

    public  static void main(String [] args) throws FileNotFoundException {
        GameMaker gm = new GameMaker();
        gm.readAllGames();
    }
}
