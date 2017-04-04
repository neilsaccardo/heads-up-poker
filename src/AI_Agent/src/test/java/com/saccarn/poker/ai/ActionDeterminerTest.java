package com.saccarn.poker.ai;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by Neil on 23/03/2017.
 */
public class ActionDeterminerTest {
    //holeCard1, holeCard2, boardCards, stackSize, opponentStackSize, potSize, amountBet, minBet, playerCluster, round
    private ActionDeterminer ad;

    @Before
    public void setUp() {
        String [] bcs = {};
        ad = new ActionDeterminer("As", "Ac", bcs, 2, 2, 20, 15, 3, new HashMap<String, Double>(), 0);
    }

    @Test
    public void testActionDeterminerDetermineShouldFold() {
        boolean shouldFold = ad.determineShouldFold(0.5, 0.6);
        Assert.assertEquals(true, shouldFold);
    }

    @Test
    public void testActionDeterminerDetermineShouldNotFold() {
        boolean shouldFold = ad.determineShouldFold(0.2, 0.1);
        Assert.assertEquals(false, shouldFold);
    }

    @Test
    public void testActionDeterminerDeterminePass() {
        String action = ad.determinePassOrBetAction(0.5, 0.4);
        Assert.assertEquals(ActionStrings.ACTION_PASS, action);
    }


    @Test
    public void testActionDeterminerDetermineBet1() {
        String action = ad.determinePassOrBetAction(0.3, 0.5);
        Assert.assertEquals(ActionStrings.ACTION_BET1, action);
    }

    @Test
    public void testActionDeterminerDetermineBet2() {
        String action = ad.determinePassOrBetAction(0.25, 0.6);
        Assert.assertEquals(ActionStrings.ACTION_BET2, action);
    }

    @Test
    public void testActionDeterminerDetermineBet3() {
        String action = ad.determinePassOrBetAction(0.2, 0.61);
        Assert.assertEquals(ActionStrings.ACTION_BET3, action);
    }

    @Test
    public void testActionDeterminerDetermineAllIn() {
        String action = ad.determinePassOrBetAction(0.2, 1.0);
        Assert.assertEquals(ActionStrings.ACTION_ALLIN, action);

        action = ad.determinePassOrBetAction(0.25, 1.0);
        Assert.assertEquals(ActionStrings.ACTION_ALLIN, action);

        action = ad.determinePassOrBetAction(0.26, 1.0);
        Assert.assertNotEquals(ActionStrings.ACTION_ALLIN, action);
    }
}
