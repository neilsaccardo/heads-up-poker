package com.saccarn.poker.ai.preflop;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Neil on 07/05/2017.
 */
public class PreFlopValuesTest {

    @Test
    public void testGetRankThreshold() {
        PreFlopValues pfv = new PreFlopValues();

        Assert.assertEquals("The default value for PreflopValues.rankThreshold should be : 40", 40, pfv.getRankThreshold());
    }

    @Test
    public void testGetFoldThreshold() {
        PreFlopValues pfv = new PreFlopValues();
        Assert.assertEquals("The default value for PreflopValues.foldThreshold should be : 90", 90, pfv.getFoldThreshold());
    }

}
