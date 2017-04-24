package com.saccarn.poker.harnass;

import cards.Card;
import cards.CardSet;
import cards.HandEval;
import com.saccarn.poker.ai.AiAgent;

import java.io.*;
import java.net.URISyntaxException;
import java.util.Scanner;

/**
 * Created by Neil on 21/04/2017.
 */
public class Harnass {

    public static void main(String [] args) throws IOException, URISyntaxException {

        AiAgent aiPlayerOne = new AiAgent();
        AiAgent aiPlayerTwo = new AiAgent();

//        File file = new File(Harnass.class.getResource("/testresource.txt").toURI());
//        Scanner in = new Scanner(file);
//
//        PrintWriter pw = new PrintWriter(new File("test1234.txt"));
//        pw.println("trest");
//        pw.close();
//
//        while(in.hasNextLine()) {
//            System.out.println(in.next());
//        }

        CardSet cs = CardSet.shuffledDeck();
        Card [] fullDeckArray = cs.toArray();
        Card [] holeCards1 = new Card[7];
        Card [] holeCards2 = new Card[7];
        int deckPointer = 0;
        for (deckPointer = 0; deckPointer < 2; deckPointer++) {
            holeCards1[deckPointer] = fullDeckArray[deckPointer];
            holeCards2[deckPointer] = fullDeckArray[deckPointer + 2];
        }
        deckPointer = 4;

        for (int i = 0; i < 5; i++) {
            holeCards1[i+2] = fullDeckArray[deckPointer + i];
            holeCards2[i+2] = fullDeckArray[deckPointer + i];
        }
        System.out.println();
        for (int i = 0; i < holeCards1.length; i++) {
            System.out.print(holeCards1[i]);
        }
        System.out.println();

        for (int i = 0; i < holeCards2.length; i++) {
            System.out.print(holeCards2[i]);
        }
        System.out.println();
        long enHand1 = HandEval.encode(holeCards1);
        long enHand2 = HandEval.encode(holeCards2);
        System.out.println(enHand1);
        System.out.println(enHand2);
        System.out.println(cs);
        int rank1 = HandEval.hand7Eval(enHand1);
        int rank2 = HandEval.hand7Eval(enHand2);
        System.out.println(rank1);
        System.out.println(rank2);
    }
}
