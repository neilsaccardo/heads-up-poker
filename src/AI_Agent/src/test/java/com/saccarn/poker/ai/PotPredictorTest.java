package com.saccarn.poker.ai;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Neil on 04/04/2017.
 */
public class PotPredictorTest {

    private PotPredictor potPredictor;

    @Before
    public void setUp() {
        potPredictor = new PotPredictor(1, 75, 75, 30, 0, 10);
    }

    @Test
    public void testFinalPotBetGreaterThanFinalPotPass() {
        boolean actual = potPredictor.getFinalPotBet() > potPredictor.getFinalPotPass();
        Assert.assertEquals("FinalPotBet should always be greater than FinalPotPass", actual, true);
    }

    @Test
    public void testFutureContributionBetGreaterThanFutureContributionPass() {
        boolean actual = potPredictor.getFutureContributionBet() > potPredictor.getFutureContributionPass();
        Assert.assertEquals("Future Contribution Bet should always be greater than Future Contribution Pass", actual, true);
    }

    @Test
    public void testGetRound() {
        Assert.assertEquals("Round should equal 0 - flop", potPredictor.getRound(), 0);
    }


    @Test
    public void testGetBetSize() {
        boolean betsizeConditions = potPredictor.getBetSize() < 75 && potPredictor.getBetSize() > 1;
        Assert.assertEquals("Bet size should be at least the minimum bet and at most should bestack size - flop", betsizeConditions, true);
    }

    @Test
    public void testDeterminingBetSizeBigMinBet() {
        potPredictor = new PotPredictor(30, 75, 75, 15, 0, 10);
        boolean betsizeConditions = potPredictor.getBetSize() < 75 && potPredictor.getBetSize() >= 30;
        Assert.assertEquals("Bet size should be at least the minimum bet and at most should be stack size - flop", betsizeConditions, true);
    }

    @Test
    public void testDeterminingBetSizeSmallStack() {
        potPredictor = new PotPredictor();
        potPredictor.calculatePotAndFutureContribution(30, 20, 75, 15, 0, 10);
        boolean betsizeConditions = potPredictor.getBetSize() == 20;
        Assert.assertEquals("Bet size should be at least the minimum bet and at most should be stack size - flop", betsizeConditions, true);
    }


}
