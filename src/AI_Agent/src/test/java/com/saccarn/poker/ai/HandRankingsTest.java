package com.saccarn.poker.ai;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Neil on 08/03/2017.
 */
public class HandRankingsTest {

    @Test
    public void testTransformCardsForHandRanking() {
        String cardpair1 = HandRankings.transformCardsForHandRanking("Tc", "4d");
        Assert.assertEquals("T4", cardpair1);
        String cardpair2 = HandRankings.transformCardsForHandRanking("As", "2h");
        Assert.assertEquals("A2", cardpair2);
        String cardpair3 = HandRankings.transformCardsForHandRanking("Qs", "Qd");
        Assert.assertEquals("QQ", cardpair3);
    }

    @Test
    public void testTransCardsForHandRankingWithSameSuits() {
        String cardpairSameSuit1 = HandRankings.transformCardsForHandRanking("Tc", "8c");
        Assert.assertEquals("T8 s", cardpairSameSuit1);
        String cardpairSameSuit2 = HandRankings.transformCardsForHandRanking("Ad", "3d");
        Assert.assertEquals("A3 s", cardpairSameSuit2);
        String cardpairSameSuit3 = HandRankings.transformCardsForHandRanking("Js", "9s");
        Assert.assertEquals("J9 s", cardpairSameSuit3);
    }

    @Test
    public void testTransformCardsForHandRankingsWithCardsInDifferentOrder() {
        String cardpairSameSuit1 = HandRankings.transformCardsForHandRanking("4c", "Kc");
        Assert.assertEquals("K4 s", cardpairSameSuit1);
        String cardpairSameSuit2 = HandRankings.transformCardsForHandRanking("Qd", "As");
        Assert.assertEquals("AQ", cardpairSameSuit2);
        String cardpairSameSuit3 = HandRankings.transformCardsForHandRanking("Js", "Td");
        Assert.assertEquals("JT", cardpairSameSuit3);
        String cardpairSameSuit4 = HandRankings.transformCardsForHandRanking("4s", "2d");
        Assert.assertEquals("42", cardpairSameSuit4);
        String cardpairSameSuit5 = HandRankings.transformCardsForHandRanking("2c", "5c");
        Assert.assertEquals("52 s", cardpairSameSuit5);
        String cardpairSameSuit6 = HandRankings.transformCardsForHandRanking("Ts", "Jd");
        Assert.assertEquals("JT", cardpairSameSuit6);
    }

    @Test
    public void testGetEVRankOfACardPair() {
        HandRankings hr = new HandRankings();
        double evRank = hr.getEVRankOfCardPair("JJ");
        Assert.assertEquals(4.0, evRank, 0.1);
    }
}
