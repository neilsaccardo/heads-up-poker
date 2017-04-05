package com.saccarn.poker.ai;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Neil on 05/04/2017.
 */
public class ActionProbabilityTest {


    private ActionProbability ap = new ActionProbability(new PotPredictor());
                                    //avoid null pointer exceptions

    @Before
    public void setUp() {
        PotPredictor po = new PotPredictor(); //1, 75, 75, 2, 0, 30
        po.calculatePotAndFutureContribution(1, 75, 75, 30, 0, 0);
        ActionProbability ap = new ActionProbability(po);
    }

    @Test
    public void testLowerBoundaryFoldProbability() {
        double prob = ap.getFoldProbability();
        Assert.assertEquals("Action Probability for FoldPass should not be < 0", false, (prob < 0));
    }

    @Test
    public void testUpperBoundaryFoldProbability() {
        double prob = ap.getFoldProbability();
        Assert.assertEquals("Action Probability for FoldPass should not be > 1", false, (prob > 1.0));
    }
    @Test
    public void testLowerBoundaryPassBetProbability() {
        double prob = ap.getPassProbability();
        Assert.assertEquals("Action Probability for PassBet should not be < 0", false, (prob < 0));
    }

    @Test
    public void testUpperBoundaryPassBetProbability() {
        double prob = ap.getPassProbability();
        Assert.assertEquals("Action Probability for PassBet should not be > 1", false, (prob > 1.0));
    }


}

