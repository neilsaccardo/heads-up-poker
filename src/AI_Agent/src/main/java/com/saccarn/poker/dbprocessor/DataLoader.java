package com.saccarn.poker.dbprocessor;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.saccarn.poker.dataprocessing.GameAnalyser;
import com.saccarn.poker.dataprocessing.GamePlayerRecord;
import com.saccarn.poker.dataprocessing.Player;
import org.bson.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Neil on 21/02/2017.
 */
public class DataLoader {

    private List<GamePlayerRecord> getListOfRecords() throws FileNotFoundException {
        GameAnalyser ga = new GameAnalyser();
        List<GamePlayerRecord> gprs = ga.analyse();
        return gprs;
    }


    public void saveClusterCentroids(List<Vector<Double>> clusterCentroids) {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase(DataLoaderStrings.DB_NAME);
        MongoCollection<Document> playerCollection = database.getCollection(DataLoaderStrings.CLUSTER_COLLECTION);
        Map<Integer, String> docObjectKeys = fillDocObjectMap();

        for (int i = 0; i < clusterCentroids.size(); i++) {
            Document docCluster = new Document();
            for (int j = 0; j < clusterCentroids.get(i).size(); j++) {
                docCluster.put(docObjectKeys.get(i), clusterCentroids.get(i));
            }
            playerCollection.insertOne(docCluster);
        }
    }

    private Map<Integer, String> fillDocObjectMap() {
        Map<Integer, String> docObjectKeysBetRaises = new HashMap<>();
        docObjectKeysBetRaises.put(0, DataLoaderStrings.PRE_FLOP_BET_ACTION_RATIO);
        docObjectKeysBetRaises.put(1, DataLoaderStrings.FLOP_BET_ACTION_RATIO);
        docObjectKeysBetRaises.put(2, DataLoaderStrings.TURN_BET_ACTION_RATIO);
        docObjectKeysBetRaises.put(3, DataLoaderStrings.RIVER_BET_ACTION_RATIO);
        docObjectKeysBetRaises.put(4, DataLoaderStrings.PRE_FLOP_FOLDED_RATIO);
        docObjectKeysBetRaises.put(5, DataLoaderStrings.FLOP_FOLDED_RATIO);
        docObjectKeysBetRaises.put(6, DataLoaderStrings.TURN_FOLDED_RATIO);
        docObjectKeysBetRaises.put(7, DataLoaderStrings.RIVER_FOLDED_RATIO);
        return docObjectKeysBetRaises;
    }

    public void loadDataIntoMongo() throws InterruptedException {
        List<GamePlayerRecord> gprs;
        try {
            gprs = getListOfRecords();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error finding files/data folders");
            return;
        }

        int id = 1;
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase(DataLoaderStrings.DB_NAME);
        MongoCollection<Document> playerCollection = database.getCollection(DataLoaderStrings.PLAYER_COLLECTION);

        for (GamePlayerRecord gpr : gprs) {
            Document doc = new Document();
            List<Document> p1List = new LinkedList<>();
            List<Document> p2List = new LinkedList<>();
            doc.put("id", id);
            doc.put("preflop", gpr.getPlayerActionsPreFlop());
            doc.put("flop", gpr.getPlayerActionsFlop());
            doc.put("preturn", gpr.getPlayerActionsTurn());
            doc.put("preriver", gpr.getPlayerActionsRiver());
            doc.put("playerOneId", gpr.getPlayerNames().get(0).toString());
            doc.put("playerTwoId", gpr.getPlayerNames().get(1).toString());

            Player p1 = gpr.getPlayerOne();
            Player p2 = gpr.getPlayerTwo();
            BsonDocument p1Filter = new BsonDocument(DataLoaderStrings.NAME, new BsonString(p1.getName()));
            BsonDocument p2Filter = new BsonDocument(DataLoaderStrings.NAME, new BsonString(p2.getName()));
            playerCollection.find(p1Filter).limit(1).into(p1List);
            playerCollection.find(p2Filter).limit(1).into(p2List);
            if (p1List.size() == 0) {
                playerCollection.insertOne(addNewDocument(p1));
            }
            else { // already a record of the player in db
                playerCollection.findOneAndReplace(p1Filter, getUpdatedDocument(p1List.get(0), p1));
            }
            if (p2List.size() == 0) {
                playerCollection.insertOne(addNewDocument(p2));
            }
            else { // already a record of the player in db
                playerCollection.findOneAndReplace(p2Filter, getUpdatedDocument(p2List.get(0), p2));
            }
        }
    }

    private Document getUpdatedDocument(Document document, Player p) {
        int nw = (int) document.get(DataLoaderStrings.NUM_WINS);
        int totalbr = (int) document.get(DataLoaderStrings.TOTAL_BET_RAISES);
        int pfbr = (int) document.get(DataLoaderStrings.PRE_FLOP_BET_RAISES);
        int fbr= (int) document.get(DataLoaderStrings.FLOP_BET_RAISES);
        int tbr = (int) document.get(DataLoaderStrings.TURN_BET_RAISES);
        int rbr = (int) document.get(DataLoaderStrings.RIVER_BET_RAISES);
        int totalActions = (int) document.get(DataLoaderStrings.TOTAL_ACTIONS);
        int totalActionsPreFlop = (int) document.get(DataLoaderStrings.TOTAL_PRE_FLOP_ACTIONS);
        int totalActionsFlop = (int) document.get(DataLoaderStrings.TOTAL_FLOP_ACTIONS);
        int totalActionsTurn = (int) document.get(DataLoaderStrings.TOTAL_TURN_ACTIONS);
        int totalActionsRiver = (int) document.get(DataLoaderStrings.TOTAL_RIVER_ACTIONS);

        int foldedAtPreFlop = (int) document.get(DataLoaderStrings.FOLDED_AT_PRE_FLOP);
        int foldedAtFlop = (int) document.get(DataLoaderStrings.FOLDED_AT_FLOP);
        int foldedAtTurn = (int) document.get(DataLoaderStrings.FOLDED_AT_TURN);
        int foldedAtRiver = (int) document.get(DataLoaderStrings.FOLDED_AT_RIVER);
        int numHandsPlayed = (int) document.get(DataLoaderStrings.TOTAL_HANDS_PLAYED);

        document.put(DataLoaderStrings.NUM_WINS, (nw +1));
        document.put(DataLoaderStrings.TOTAL_BET_RAISES, totalbr + p.getTotalNumBetRaises());
        document.put(DataLoaderStrings.PRE_FLOP_BET_RAISES,  pfbr + p.getNumBetRaisesPreFlop());
        document.put(DataLoaderStrings.FLOP_BET_RAISES,  fbr + p.getNumBetRaisesFlop());
        document.put(DataLoaderStrings.TURN_BET_RAISES,  tbr + p.getNumBetRaisesTurn());
        document.put(DataLoaderStrings.RIVER_BET_RAISES,  rbr + p.getNumBetRaisesRiver());
        document.put(DataLoaderStrings.TOTAL_ACTIONS,  totalActions + p.getTotalActions());

        document.put(DataLoaderStrings.TOTAL_PRE_FLOP_ACTIONS, totalActionsPreFlop + p.getTotalNumActionsPreFlop());
        document.put(DataLoaderStrings.TOTAL_FLOP_ACTIONS, totalActionsFlop + p.getTotalNumActionsFlop());
        document.put(DataLoaderStrings.TOTAL_TURN_ACTIONS, totalActionsTurn + p.getTotalNumActionsTurn());
        document.put(DataLoaderStrings.TOTAL_RIVER_ACTIONS, totalActionsRiver +  p.getTotalNumActionsRiver());
        document.put(DataLoaderStrings.NAME, p.getName());

        document.put(DataLoaderStrings.FOLDED_AT_PRE_FLOP, foldedAtPreFlop + p.getFoldAtPreFlop());
        document.put(DataLoaderStrings.FOLDED_AT_FLOP, foldedAtFlop + p.getFoldAtFlop());
        document.put(DataLoaderStrings.FOLDED_AT_TURN, foldedAtTurn + p.getFoldAtTurn());
        document.put(DataLoaderStrings.FOLDED_AT_RIVER, foldedAtRiver + p.getFoldAtRiver());
        document.put(DataLoaderStrings.TOTAL_HANDS_PLAYED, numHandsPlayed + 1); //played one more hand


        computeFoldAtStagesRatios(document);
        computeActionBetRaisesRatios(document);
        return document;
    }

    private void computeFoldAtStagesRatios(Document document) {
        double numFoldedAtPreFlop = ((Integer) document.get(DataLoaderStrings.FOLDED_AT_PRE_FLOP)).doubleValue();
        double numFoldedAtFlop = ((Integer) document.get(DataLoaderStrings.FOLDED_AT_FLOP)).doubleValue();
        double numFoldedAtTurn = ((Integer) document.get(DataLoaderStrings.FOLDED_AT_TURN)).doubleValue();
        double numFoldedAtRiver = (((Integer) document.get(DataLoaderStrings.FOLDED_AT_RIVER))).doubleValue();
        double numHandsPlayed = (((Integer) document.get(DataLoaderStrings.TOTAL_HANDS_PLAYED))).doubleValue();

        document.put(DataLoaderStrings.PRE_FLOP_FOLDED_RATIO, numFoldedAtPreFlop / numHandsPlayed);
        document.put(DataLoaderStrings.FLOP_FOLDED_RATIO, numFoldedAtFlop / numHandsPlayed);
        document.put(DataLoaderStrings.TURN_FOLDED_RATIO, numFoldedAtTurn / numHandsPlayed);
        document.put(DataLoaderStrings.RIVER_FOLDED_RATIO, numFoldedAtRiver / numHandsPlayed);
    }

    private void computeActionBetRaisesRatios(Document document) {
        ///(double) document.get(DataLoaderStrings.TOTAL_PRE_FLOP_ACTIONS)
        double preFlopBetRaises = ((Integer) document.get(DataLoaderStrings.PRE_FLOP_BET_RAISES)).doubleValue();
        double flopBetRaises = ((Integer) document.get(DataLoaderStrings.FLOP_BET_RAISES)).doubleValue();
        double turnBetRaises = ((Integer) document.get(DataLoaderStrings.TURN_BET_RAISES)).doubleValue();
        double riverBetRaises = (((Integer) document.get(DataLoaderStrings.RIVER_BET_RAISES))).doubleValue();

        double totalPreFlopActions = ((Integer)document.get(DataLoaderStrings.TOTAL_PRE_FLOP_ACTIONS)).doubleValue();
        double totalFlopActions = ((Integer)document.get(DataLoaderStrings.TOTAL_FLOP_ACTIONS)).doubleValue();
        double totalTurnActions = ((Integer)document.get(DataLoaderStrings.TOTAL_TURN_ACTIONS)).doubleValue();
        double totalRiverActions = ((Integer)document.get(DataLoaderStrings.TOTAL_RIVER_ACTIONS)).doubleValue();

        if (totalPreFlopActions != 0) { //  avoid 'divide by 0' errors!
            document.put(DataLoaderStrings.PRE_FLOP_BET_ACTION_RATIO, preFlopBetRaises / totalPreFlopActions);
        }
        if (totalFlopActions != 0) {
            document.put(DataLoaderStrings.FLOP_BET_ACTION_RATIO, flopBetRaises/totalFlopActions);
        }
        if (totalTurnActions != 0) {
            document.put(DataLoaderStrings.TURN_BET_ACTION_RATIO, turnBetRaises/totalTurnActions);
        }
        if (totalRiverActions != 0) {
            document.put(DataLoaderStrings.RIVER_BET_ACTION_RATIO, riverBetRaises/totalRiverActions);
        }
    }

    //for adding first instance of player to mongo.
    private Document addNewDocument(Player p) {
        Document playerDoc = new Document();
        playerDoc.put(DataLoaderStrings.TOTAL_BET_RAISES, p.getTotalNumBetRaises());
        playerDoc.put(DataLoaderStrings.PRE_FLOP_BET_RAISES,  p.getNumBetRaisesPreFlop());
        playerDoc.put(DataLoaderStrings.FLOP_BET_RAISES,  p.getNumBetRaisesFlop());
        playerDoc.put(DataLoaderStrings.TURN_BET_RAISES,  p.getNumBetRaisesTurn());
        playerDoc.put(DataLoaderStrings.RIVER_BET_RAISES,  p.getNumBetRaisesRiver());
        playerDoc.put(DataLoaderStrings.TOTAL_ACTIONS,  p.getTotalActions());
        playerDoc.put(DataLoaderStrings.TOTAL_PRE_FLOP_ACTIONS, p.getTotalNumActionsPreFlop());
        playerDoc.put(DataLoaderStrings.TOTAL_FLOP_ACTIONS, p.getTotalNumActionsFlop());
        playerDoc.put(DataLoaderStrings.TOTAL_TURN_ACTIONS, p.getTotalNumActionsTurn());
        playerDoc.put(DataLoaderStrings.TOTAL_RIVER_ACTIONS, p.getTotalNumActionsRiver());
        System.out.println("Hello  " + p.getName());
        if (p.isWinner()) {
            playerDoc.put(DataLoaderStrings.NUM_WINS, 1);
        } else {
            playerDoc.put(DataLoaderStrings.NUM_WINS, 0);
        }
        playerDoc.put(DataLoaderStrings.FOLDED_AT_PRE_FLOP, p.getFoldAtPreFlop());
        playerDoc.put(DataLoaderStrings.FOLDED_AT_FLOP, p.getFoldAtFlop());
        playerDoc.put(DataLoaderStrings.FOLDED_AT_TURN, p.getFoldAtTurn());
        playerDoc.put(DataLoaderStrings.FOLDED_AT_RIVER, p.getFoldAtRiver());
        playerDoc.put(DataLoaderStrings.TOTAL_HANDS_PLAYED, 1);
        playerDoc.put(DataLoaderStrings.NAME, p.getName());
        playerDoc.put(DataLoaderStrings.PRE_FLOP_BET_ACTION_RATIO, 0.0);
        playerDoc.put(DataLoaderStrings.FLOP_BET_ACTION_RATIO, 0.0);
        playerDoc.put(DataLoaderStrings.TURN_BET_ACTION_RATIO, 0.0);
        playerDoc.put(DataLoaderStrings.RIVER_BET_ACTION_RATIO, 0.0);
        playerDoc.put(DataLoaderStrings.PRE_FLOP_FOLDED_RATIO, 0.0);
        playerDoc.put(DataLoaderStrings.FLOP_FOLDED_RATIO, 0.0);
        playerDoc.put(DataLoaderStrings.TURN_FOLDED_RATIO, 0.0);
        playerDoc.put(DataLoaderStrings.RIVER_FOLDED_RATIO, 0.0);
        computeActionBetRaisesRatios(playerDoc);
        computeFoldAtStagesRatios(playerDoc);
        return playerDoc;
    }


    public Map<String, Vector<Double>> retrieveVectorsForEveryPlayer() {
        Map<String, Vector<Double>> mappedVectors = new HashMap<>();
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase(DataLoaderStrings.DB_NAME);
        MongoCollection<Document> playerCollection = database.getCollection(DataLoaderStrings.PLAYER_COLLECTION);
        FindIterable<Document> playerDocs = playerCollection.find();
        for (Document doc : playerDocs) {
            Map<Integer, String> docObjectKeys = fillDocObjectMap();
            Vector<Double> v = new Vector<>();
            String playerName = (String) doc.get(DataLoaderStrings.NAME);
            System.out.println(playerName);
            for (int i = 0; i < docObjectKeys.size(); i++) {
                System.out.println(docObjectKeys.get(i));
                double value = (double) doc.get(docObjectKeys.get(i));
                v.add(value);
            }
            mappedVectors.put(playerName, v);
        }
        System.out.println(mappedVectors);
        return mappedVectors;
    }


    public static void main(String [] args) throws InterruptedException {
        DataLoader dl = new DataLoader();
        dl.loadDataIntoMongo();
        dl.retrieveVectorsForEveryPlayer();
        try {
            Thread.sleep(300);
            System.out.println("dsdsds2");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
