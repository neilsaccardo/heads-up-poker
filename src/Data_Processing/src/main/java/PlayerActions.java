import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Neil on 15/12/2016.
 */
public class PlayerActions {

    private File pdb = new File("C:\\Data\\pdb\\");
    private HashMap<Integer, PokerStage> mappedIntegersToPokerStage = new HashMap<>();


    public PlayerActions(File pdbFilePath) {
        pdb = pdbFilePath;
        mappedIntegersToPokerStage.put(4, PokerStage.PREFLOP);
        mappedIntegersToPokerStage.put(5, PokerStage.FLOP);
        mappedIntegersToPokerStage.put(6, PokerStage.TURN);
        mappedIntegersToPokerStage.put(7, PokerStage.RIVER);
    }




    public void getAction(String name1, String name2, String gameID) throws FileNotFoundException {
        Scanner inPlayerOne = new Scanner(new File(pdb, "pdb." + name1));
        Scanner inPlayerTwo = new Scanner(new File(pdb, "pdb." + name2));
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
//        for (int i = 0; i < playerOneList.length; i++) {
//            System.out.println(i + " : " + playerOneList[i]);
//        }
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
            int j;
            for (j = 0; j < charsPlayerOne.length && j < charsPlayerTwo.length; j++) {
                gpr.addPlayerAction(playerOneList[0], charsPlayerOne[j], mappedIntegersToPokerStage.get(i));
                gpr.addPlayerAction(playerTwoList[0], charsPlayerTwo[j], mappedIntegersToPokerStage.get(i));
            }
            // CASE where one player performs more actions in one stage than the other player.
            if (charsPlayerTwo.length < charsPlayerOne.length) {
                gpr.addPlayerAction(playerOneList[0], charsPlayerOne[j], mappedIntegersToPokerStage.get(i));
            }
            else if (charsPlayerTwo.length > charsPlayerOne.length) {
                gpr.addPlayerAction(playerTwoList[0], charsPlayerTwo[j], mappedIntegersToPokerStage.get(i));
            }
        }
        if(playerOneList.length > 11) {
            gpr.addCardsPlayerOne(playerOneList[11], playerOneList[12]);
            gpr.addCardsPlayerTwo(playerTwoList[11], playerTwoList[12]);
        }
        System.out.println(gpr);
    }
}
