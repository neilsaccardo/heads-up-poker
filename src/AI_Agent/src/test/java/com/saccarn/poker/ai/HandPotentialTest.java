package com.saccarn.poker.ai;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Neil on 07/04/2017.
 */
public class HandPotentialTest {

    @Test
    public void testFlopCalculateHandPotentialUpperBoundaryOf1() {
        String c1 = "2s";
        String c2 = "3s";
        String c3 = "4s";
        String c4 = "5s";
        String c5 = "6s";

        HandPotential hp = new HandPotential(c1, c2, c3, c4, c5);
        double hpVal = hp.calculateHandPotential(1);
        Assert.assertEquals("The upper boundary for hand potential should be 1.0", false, (hpVal>1.0));
    }

    @Test
    public void testFlopCalculateHandPotentialLowerBoundaryOf0() {
        String c1 = "2s";
        String c2 = "3s";
        String c3 = "Ad";
        String c4 = "5s";
        String c5 = "6s";

        HandPotential hp = new HandPotential(c1, c2, c3, c4, c5);
        double hpVal = hp.calculateHandPotential(0);
        Assert.assertEquals("The lower boundary for hand potential should be 0.0", false, (hpVal<0.0));
    }

    @Test
    public void testTurnCalculateHandPotentialUpperBoundaryOf1() {
        String c1 = "2s";
        String c2 = "3s";
        String c3 = "4s";
        String c4 = "5s";
        String c5 = "6s";
        String c6 = "4h";
        HandPotential hp = new HandPotential(c1, c2, c3, c4, c5, c6);
        double hpVal = hp.calculateHandPotential(1);
        Assert.assertEquals("The upper boundary for hand potential should be 1.0", false, (hpVal>1.0));
    }

    @Test
    public void testTurnCalculateHandPotentialLowerBoundaryOf0() {
        String c1 = "2s";
        String c2 = "3s";
        String c3 = "Ad";
        String c4 = "5s";
        String c5 = "6s";
        String c6 = "4h";
        HandPotential hp = new HandPotential(c1, c2, c3, c4, c5, c6);
        double hpVal = hp.calculateHandPotential(0);
        Assert.assertEquals("The lower boundary for hand potential should be 0.0", false, (hpVal<0.0));
    }


}
