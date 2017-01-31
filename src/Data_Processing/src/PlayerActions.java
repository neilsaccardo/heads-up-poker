import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Neil on 15/12/2016.
 */
public class PlayerActions {

    private String pdb = "C:\\Data\\pdb\\pdb.";

    public void getAction(String name1, String name2, String gameID) throws FileNotFoundException {
        Scanner inPlayerOne = new Scanner(new File(pdb + name1));
        Scanner inPlayerTwo = new Scanner(new File(pdb + name2));
        String strGameID = gameID + "";
        String playerOneLine = inPlayerOne.nextLine();
        String playerTwoLine = "";
        while(inPlayerOne.hasNextLine() && !(playerOneLine.contains(strGameID))) {
            playerOneLine = inPlayerOne.nextLine();
        }
        inPlayerOne.close();
        if(!playerOneLine.contains(strGameID)) {
            System.out.println("ERror finding game for this player.");
            return;
        }
        while(inPlayerTwo.hasNextLine() && (!playerTwoLine.contains(strGameID))) {
            playerTwoLine = inPlayerTwo.nextLine();
        }
        inPlayerTwo.close();
        if(!playerTwoLine.contains(strGameID)) {
            System.out.println("ERror finding game for this player.");
            return;
        }
        System.out.println(playerOneLine);
        System.out.println(playerTwoLine);
        addActions(playerOneLine, playerTwoLine);
    }

    private void addActions(String playerOneLine, String playerTwoLine) {
        String [] playerOneList = playerOneLine.trim().split("\\s+");
        String [] playerTwoList = playerTwoLine.trim().split("\\s+");
        if(playerOneList[3].equals("1")) {
            addActionsToRecord(playerOneList, playerTwoList);
        } else {
            addActionsToRecord(playerTwoList, playerOneList);
        }
    }

    private void addActionsToRecord(String[] playerOneList, String[] playerTwoList) {
        GamePlayerRecord gpr = new GamePlayerRecord();
        for (int i = 4; i < 8; i++) {
            String [] charsPlayerOne = playerOneList[i].split("");
            String [] charsPlayerTwo = playerTwoList[i].split("");
            for(int j = 0; j < charsPlayerOne.length; j++) {
                gpr.addPlayerAction(playerOneList[0], charsPlayerOne[j]);
                gpr.addPlayerAction(playerTwoList[0], charsPlayerTwo[j]);
            }
        }
        System.out.println(gpr);
    }
}
