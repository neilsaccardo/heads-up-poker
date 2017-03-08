package com.saccarn.poker.ai;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;


/**
 * Created by Neil on 07/03/2017.
 */
public class HandRankings {

    private int totalNumPocketCards = 115591080;

    private Map<String, Double> mappedCardsToEV = new LinkedHashMap<>();
    private Map<String, Double> mappedCardsToEVPercentagePosition = new LinkedHashMap<>();
    private Map<String, Double> mappedCardsToDistribution = new LinkedHashMap<>();
    private Map<String, Integer> mappedEVRankToCards = new LinkedHashMap<>();
    public void getHandRankings() {
        Document handRankingPage;
        try {
            handRankingPage = getDocument();
        } catch (IOException e) {
            System.out.println("Problem loading EVs");
            e.printStackTrace();
            return;
        }
        Elements handRankings = handRankingPage.select(".handrankings");
        int i = 1;
        for (Element e : handRankings) {
            Elements tableRows = e.select("tr");
            for (Element tr : tableRows) {
                if(tr.child(0).is("th")) {
                    System.out.println("TH"); //the table headers contain string values that should not be used.
                    continue;
                }
                String cardHand = tr.child(0).text();
                double occurrences = Double.parseDouble(tr.child(2).text().trim().replace(",", ""));
                mappedCardsToEV.put(cardHand, Double.parseDouble(tr.child(1).text().trim()));
                mappedCardsToDistribution.put(cardHand, (occurrences/totalNumPocketCards));
                mappedCardsToEVPercentagePosition.put(cardHand, (double) i);
                mappedEVRankToCards.put(cardHand, i);
                i++; // increment card counter
            }
        }
        System.out.println(mappedCardsToEV);
        System.out.println(mappedCardsToDistribution);
        calculatePercentagesForMappedCardsToEVPercentage();
    }

    private Document getDocument() throws IOException {
        return Jsoup.connect("https://www.tightpoker.com/poker_hands.html").get();
    }

    public double getEVRankOfCardPair(String card) {
        if (mappedEVRankToCards.get(card) == null) {
            return -1;
        } else {
            return  mappedEVRankToCards.get(card);
        }
    }


    private void calculatePercentagesForMappedCardsToEVPercentage() {
        int size = mappedCardsToEVPercentagePosition.size();
        int multiplier = 100;
        for (String card: mappedCardsToEVPercentagePosition.keySet()) {
            System.out.println(mappedCardsToEVPercentagePosition.get(card));
            mappedCardsToEVPercentagePosition.put(card, (mappedCardsToEVPercentagePosition.get(card) / size) * multiplier);
        }
        System.out.println(mappedCardsToEVPercentagePosition);
    }

    //cards are inputted as: 'Jh', '7d', '8s', '5s', '4c'
    public static String transformCardsForHandRanking(String cardOne, String cardTwo) {
        Character [] arraySpecialChars = {'T', 'J', 'Q', 'K', 'A'};
        List<Character> specialChars = Arrays.asList(arraySpecialChars);
        if (cardOne.length() != 2 || cardTwo.length() != 2) {
            throw new IllegalArgumentException("Incorrect format for card hand rank evaluation");
        }
        char characterCardOne = cardOne.charAt(0);
        char characterCardTwo = cardTwo.charAt(0);
        String suited = "";
        if (cardOne.charAt(1) == cardTwo.charAt(1)) {
            suited = " s";
        }
        int charIndex1 = specialChars.indexOf(characterCardOne);
        int charIndex2 = specialChars.indexOf(characterCardTwo);
        if (charIndex1 == -1 && charIndex2 == -1) {
            if (characterCardOne < characterCardTwo) {
                return "" + characterCardTwo + characterCardOne + suited;
            } else {
                return "" + characterCardOne + characterCardTwo + suited;
            }
        }
        else {
            if (charIndex1 > charIndex2) {
                return "" + characterCardOne + characterCardTwo + suited;
            }
            else {
                return "" + characterCardTwo + characterCardOne + suited;
            }
        }
    }


    public static void main(String [] args) throws IOException {
        HandRankings hrs = new HandRankings();
        hrs.getHandRankings();
    }
}
