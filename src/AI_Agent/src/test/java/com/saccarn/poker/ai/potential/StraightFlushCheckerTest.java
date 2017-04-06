package com.saccarn.poker.ai.potential;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Neil on 06/04/2017.
 */
public class StraightFlushCheckerTest {

    private StraightFlushChecker sfc;

    @Test
    public void testNumStraightsFlopForTwoPotentialStraights() {
        String [] boardCards = {"7s", "8h", "9d"};
        sfc = new StraightFlushChecker("4s", "6d", boardCards);

        int numStraights = sfc.numStraightOn();
        Assert.assertEquals("Flop: Number of potential straights should equal 4 for (4 x 6 7 8 9)", 4, numStraights);
    }

    @Test
    public void testNumStraightsFlopForOnePotentialStraight() {
        String [] boardCards = {"7s", "8h", "9d"};
        sfc = new StraightFlushChecker("Ad", "6d", boardCards);
        int numStraights = sfc.numStraightOn();
        Assert.assertEquals("Flop: Number of potential straights should equal 4 for pattern such as ( 6 7 8 9)", 4, numStraights);
    }

    @Test
    public void testAceLowFlopStraightOn() {
        String [] boardCards = {"8s", "3h", "4d"};
        sfc = new StraightFlushChecker("Ad", "Td", boardCards);
        int numStraights = sfc.numStraightOn();
        Assert.assertEquals("Flop: Ace should be included in a low potential straight (i.e. A 2 3 4)", 1, numStraights);
    }

    @Test
    public void testFlopNoStraightsOn() {
        String [] boardCards = {"8s", "3h", "Kd"};
        sfc = new StraightFlushChecker("Ad", "9d", boardCards);
        int numStraights = sfc.numStraightOn();
        Assert.assertEquals("Flop: No potential straights on", 0, numStraights);
    }


    @Test
    public void testTurnNoStraightsOn() {
        String [] boardCards = {"8s", "Th", "3s", "Kh"};
        sfc = new StraightFlushChecker("Ad", "9d", boardCards);
        int numStraights = sfc.numStraightOn();
        Assert.assertEquals("Turn: No potential straights on", 0, numStraights);
    }


    @Test
    public void testTurnThreeStraightsOn() {
        String [] boardCards = {"8s", "Th", "Qs", "7h"};
        sfc = new StraightFlushChecker("Ad", "9d", boardCards);
        int numStraights = sfc.numStraightOn();
        Assert.assertEquals("Turn: Three potential straights on", 3, numStraights);
    }


    @Test
    public void testTurnTwoStraightsOn() {
        String [] boardCards = {"8s", "Th", "2s", "7h"};
        sfc = new StraightFlushChecker("Ad", "9d", boardCards);
        int numStraights = sfc.numStraightOn();
        Assert.assertEquals("Turn: Three potential straights on", 2, numStraights);
    }

    @Test
    public void testTurnAceLowStraightOn() {
        String [] boardCards = {"3s", "4h", "5s", "Th"};
        sfc = new StraightFlushChecker("Ad", "9d", boardCards);
        int numStraights = sfc.numStraightOn();
        Assert.assertEquals("Turn: Three potential straights on", 1, numStraights);
    }


    @Test
    public void testTurnAceHighStraightOn() {
        String [] boardCards = {"4s", "Ah", "5s", "Th"};
        sfc = new StraightFlushChecker("Kd", "Jd", boardCards);
        int numStraights = sfc.numStraightOn();
        Assert.assertEquals("Turn: Three potential straights on", 1, numStraights);
    }

    @Test
    public void testFlopFlushIsOnThreeCardsSameSuit() {
        String [] boardCards = {"8s", "3h", "Kd"};
        sfc = new StraightFlushChecker("As", "9s", boardCards);
        int numStraights = sfc.numFlushOn();
        Assert.assertEquals("Flop: Potential Flush is on (3 / 5 cards same suit)", 1, numStraights);
    }


    @Test
    public void testFlopFlushIsOnFourCardsSameSuit() {
        String [] boardCards = {"8s", "3s", "Kd"};
        sfc = new StraightFlushChecker("As", "9s", boardCards);
        int numStraights = sfc.numFlushOn();
        Assert.assertEquals("Flop: Potential Flush is on (4 / 5 cards same suit)", 1, numStraights);
    }


    @Test
    public void testTurnFlushIsOnFourCardsSameSuit() {
        String [] boardCards = {"2s", "3h", "Qs", "6h"};
        sfc = new StraightFlushChecker("As", "9s", boardCards);
        int numStraights = sfc.numFlushOn();
        Assert.assertEquals("Turn: Potential Flush is on (4 of 5 cards same suit)", 1, numStraights);
    }


    @Test
    public void testTurnNoFlushIsOn() {
        String [] boardCards = {"2s", "9c", "Qc", "Kc"};
        sfc = new StraightFlushChecker("As", "9d", boardCards);
        int numStraights = sfc.numFlushOn();
        Assert.assertEquals("Turn: No potential Flush is on ( >4 of 5 cards same suit)", 0, numStraights);
    }

    @Test
    public void testFlopNoFlushIsOn() {
        String [] boardCards = {"8s", "3h", "Kd"};
        sfc = new StraightFlushChecker("As", "9d", boardCards);
        int numFlushes = sfc.numFlushOn();
        Assert.assertEquals("Flop: No Potential Flush is on ( < 3 of 5 cards same suit)", 0, numFlushes);
    }

    @Test
    public void testRiverNoPotentialFlushOn() {
        String [] boardCards = {"8s", "3h", "Kd", "4s", "9h"};
        sfc = new StraightFlushChecker("As", "9d", boardCards);
        int numFlushes = sfc.numFlushOn();
        Assert.assertEquals("River: No Potential Flush is on (all five cards have been played)", 0, numFlushes);
    }

    @Test
    public void testRiverNoPotentialStraightOn() {
        String [] boardCards = {"8s", "3s", "Kd", "4s"};
        sfc = new StraightFlushChecker("As", "9d", boardCards);
        int numStraightsFlushes = sfc.numStraightOn();
        Assert.assertEquals("River: No Potential Straight is on (all five cards have been played)", 0, numStraightsFlushes);
    }

    @Test
    public void testFlopTotalNumStraightFlushOn() {
        String [] boardCards = {"8s", "3h", "4s"};
        sfc = new StraightFlushChecker("As", "9d", boardCards);
        int numStraightsFlushes = sfc.numStraightOrFlushOn();
        Assert.assertEquals("Flop: Total num Straight Flushes should be equal to num of straights + num of flushes", sfc.numFlushOn()+sfc.numStraightOn(), numStraightsFlushes);
    }

    @Test
    public void testTurnTotalNumStraightFlushOn() {
        String [] boardCards = {"8s", "3h", "Kd", "4s"};
        sfc = new StraightFlushChecker("As", "9s", boardCards);
        int numStraightsFlushes = sfc.numStraightOrFlushOn();
        Assert.assertEquals("Turn: Total num Straight Flushes should be equal to num of straights + num of flushes", sfc.numFlushOn()+sfc.numStraightOn(), numStraightsFlushes);
    }

    @Test
    public void testRiverTotalNumStraightFlushOn() {
        String [] boardCards = {"8s", "3h", "Kd", "4s", "9h"};
        sfc = new StraightFlushChecker("As", "9d", boardCards);
        int numStraightsFlushes = sfc.numStraightOn();
        Assert.assertEquals("River: Total num Straight Flushes should be equal to num of straights + num of flushes", sfc.numFlushOn()+sfc.numStraightOn(), numStraightsFlushes);
    }

}
