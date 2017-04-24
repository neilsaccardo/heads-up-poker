package com.saccarn.poker.harnass;

import java.io.*;
import java.net.URISyntaxException;
import java.util.Scanner;

/**
 * Created by Neil on 21/04/2017.
 */
public class Harnass {

    public static void main(String [] args) throws IOException, URISyntaxException {
        int totalIterations = 1000;
        int i = 0;
        while (i < totalIterations) {
            Game game = new Game();
            game.playHand();
            System.out.println("finished");
            System.out.println(game.getBigBlindAmount());
            System.out.println(game.getPlayerOneStack());
            System.out.println(game.getPlayerTwoStack());
            i++;
        }
    }
}
