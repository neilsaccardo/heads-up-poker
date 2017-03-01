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
        MongoDatabase database = client.getDatabase("test1");
        MongoCollection<Document> playerCollection = database.getCollection("clusters");
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
        Map<Integer, String> docObjectKeys = new HashMap<>();
        docObjectKeys.put(0, "totalActions");
        docObjectKeys.put(1, "totalBetRaises");
        docObjectKeys.put(2, "preFlopBetRaises");
        docObjectKeys.put(3, "flopBetRaises");
        docObjectKeys.put(4, "turnBetRaises");
        docObjectKeys.put(5, "riverBetRaises");
        return docObjectKeys;
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
        MongoDatabase database = client.getDatabase("test1");
        MongoCollection<Document> playerCollection = database.getCollection("players");

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
            BsonDocument p1Filter = new BsonDocument("name", new BsonString(p1.getName()));
            BsonDocument p2Filter = new BsonDocument("name", new BsonString(p2.getName()));
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
        int nw = (int) document.get("numWins");
        int totalbr = (int) document.get("totalBetRaises");
        int pfbr = (int) document.get("preFlopBetRaises");
        int fbr= (int) document.get("flopBetRaises");
        int tbr = (int) document.get("turnBetRaises");
        int rbr = (int) document.get("riverBetRaises");
        int totalActions = (int) document.get("totalActions");
        document.put("numWins", (nw +1));
        document.put("totalBetRaises", totalbr + p.getTotalNumBetRaises());
        document.put("preFlopBetRaises",  pfbr + p.getNumBetRaisesPreFlop());
        document.put("flopBetRaises",  fbr + p.getNumBetRaisesFlop());
        document.put("turnBetRaises",  tbr + p.getNumBetRaisesTurn());
        document.put("riverBetRaises",  rbr + p.getNumBetRaisesRiver());
        document.put("totalActions",  totalActions + p.getTotalActions());
        document.put("name", p.getName());
        return document;
    }

    //for adding first instance of player to mongo.
    private Document addNewDocument(Player p) {
        Document playerDoc = new Document();
        playerDoc.put("totalBetRaises",  p.getTotalNumBetRaises());
        playerDoc.put("preFlopBetRaises",  p.getNumBetRaisesPreFlop());
        playerDoc.put("flopBetRaises",  p.getNumBetRaisesFlop());
        playerDoc.put("turnBetRaises",  p.getNumBetRaisesTurn());
        playerDoc.put("riverBetRaises",  p.getNumBetRaisesRiver());
        playerDoc.put("totalActions",  p.getTotalActions());

        System.out.println("Hello  " + p.getName());
        if (p.isWinner()) {
            playerDoc.put("numWins", 1);
        } else {
            playerDoc.put("numWins", 0);
        }
        playerDoc.put("name", p.getName());
        return playerDoc;
    }


    public Map<String, Vector<Integer>> retrieveVectorsForEveryPlayer() {
        Map<String, Vector<Integer>> mappedVectors = new HashMap<>();
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("test1");
        MongoCollection<Document> playerCollection = database.getCollection("players");
        FindIterable<Document> playerDocs = playerCollection.find();
        for (Document doc : playerDocs) {
            Map<Integer, String> docObjectKeys = fillDocObjectMap();
            Vector<Integer> v = new Vector<>();
            String playerName = (String) doc.get("name");
            for (int i = 0; i < docObjectKeys.size(); i++) {
                v.add((int) doc.get(docObjectKeys.get(i)));
            }
            mappedVectors.put(playerName, v);
        }
        System.out.println(mappedVectors);
        return mappedVectors;
    }


    public static  void main(String [] args) throws InterruptedException {
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
