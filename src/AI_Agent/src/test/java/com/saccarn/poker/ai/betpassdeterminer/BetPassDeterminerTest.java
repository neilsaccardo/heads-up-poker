package com.saccarn.poker.ai.betpassdeterminer;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Neil on 12/04/2017.
 */
public class BetPassDeterminerTest {

    @Test
    public void testCalculateBetFunctionOutput1() {
        BetPassDeterminer bpd = new BetPassDeterminer(0.2, 0.8);
        double d = bpd.calculateBetFunction();
        Assert.assertEquals("The Bet function taking two arguments: random=0.6, passProb=0.6 should output ~0.1", 0.8, d, 0.01);
    }

    @Test
    public void testCalculateBetFunctionOutput2() {
        BetPassDeterminer bpd = new BetPassDeterminer(0.99, 0.99);
        double d = bpd.calculateBetFunction();
        Assert.assertEquals("The Bet function taking two arguments: random=0.99, passProb=0.99 should output ~0.1", -97.1, d, 0.1);
    }

    @Test
    public void testCalculateBetFunctionOutput3() {
        BetPassDeterminer bpd = new BetPassDeterminer(0.01, 0.99);
        double d = bpd.calculateBetFunction();
        Assert.assertEquals("The Bet function taking two arguments: random=0.01, passProb=0.99 should output ~0.1", 0.99, d, 0.01);
    }

    @Test
    public void testCalculateBetFunctionOutput4() {
        BetPassDeterminer bpd = new BetPassDeterminer(0.05, 0.01);
        double d = bpd.calculateBetFunction();
        Assert.assertEquals("The Bet function taking two arguments: random=0.05, passProb=0.01 should output ~0.1", 0.99, d, 0.01);
    }

    @Test
    public void testCalculateBetFunctionNegativeOutput() {
        BetPassDeterminer bpd = new BetPassDeterminer(0.9, 0.3);
        double d = bpd.calculateBetFunction();
        Assert.assertEquals("The Bet function taking two arguments: random=0.9, passProb=0.3 should output(negative) ~-0.16", -0.16, d, 0.1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testCalculateBetFunctionUpperBoundsRandomNum() {
        BetPassDeterminer bpd = new BetPassDeterminer(1.02, 0.3);
        double d = bpd.calculateBetFunction();
        Assert.assertEquals("The Bet constructor should throw an illegal argument exception if the random number is greater than 1.0", true, false);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testCalculateBetFunctionUpperBoundsPassProb() {
        BetPassDeterminer bpd = new BetPassDeterminer(0.5, 1.04);
        double d = bpd.calculateBetFunction();
        Assert.assertEquals("The Bet constructor should throw an illegal argument exception if the pass probability is greater than 1.0", true, false);
    }


    @Test (expected = IllegalArgumentException.class)
    public void testCalculateBetFunctionLowerBoundsRandomNum() {
        BetPassDeterminer bpd = new BetPassDeterminer(-0.01, 1.04);
        double d = bpd.calculateBetFunction();
        Assert.assertEquals("The Bet constructor should throw an illegal argument exception if the random number is less than than 0.0", true, false);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testCalculateBetFunctionLowerBoundsPassProb() {
        BetPassDeterminer bpd = new BetPassDeterminer(0.3, -0.04);
        double d = bpd.calculateBetFunction();
        Assert.assertEquals("The Bet constructor should throw an illegal argument exception if the pass probability is less than than 0.0", true, false);
    }
}
