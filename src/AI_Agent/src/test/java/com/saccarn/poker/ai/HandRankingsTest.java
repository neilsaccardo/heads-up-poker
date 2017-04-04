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
        Assert.assertEquals("Should take first character from each card represented as strings and concatonate them", "T4", cardpair1);
        String cardpair2 = HandRankings.transformCardsForHandRanking("As", "2h");
        Assert.assertEquals("Should take first character from each card represented as strings and concatonate them", "A2", cardpair2);
        String cardpair3 = HandRankings.transformCardsForHandRanking("Qs", "Qd");
        Assert.assertEquals("Should take first character from each card represented as strings and concatonate them", "QQ", cardpair3);
    }

    @Test
    public void testTransCardsForHandRankingWithSameSuits() {
        String cardpairSameSuit1 = HandRankings.transformCardsForHandRanking("Tc", "8c");
        Assert.assertEquals("Cards of the same suit should have the first characters of the cards followed by  \'s\'","T8 s", cardpairSameSuit1);
        String cardpairSameSuit2 = HandRankings.transformCardsForHandRanking("Ad", "3d");
        Assert.assertEquals("Cards of the same suit should have the first characters of the cards followed by  \'s\'","A3 s", cardpairSameSuit2);
        String cardpairSameSuit3 = HandRankings.transformCardsForHandRanking("Js", "9s");
        Assert.assertEquals("Cards of the same suit should have the first characters of the cards followed by  \'s\'","J9 s", cardpairSameSuit3);
    }

    @Test
    public void testTransformCardsForHandRankingsWithCardsInDifferentOrder() {
        String cardpairSameSuit1 = HandRankings.transformCardsForHandRanking("4c", "Kc");
        Assert.assertEquals("Cards should have the characters in order of card rank e.g. K4 not 4k", "K4 s", cardpairSameSuit1);
        String cardpairSameSuit2 = HandRankings.transformCardsForHandRanking("Qd", "As");
        Assert.assertEquals("Cards should have the characters in order of card rank e.g. AQ not QA", "AQ", cardpairSameSuit2);
        String cardpairSameSuit3 = HandRankings.transformCardsForHandRanking("Js", "Td");
        Assert.assertEquals("Cards should have the characters in order of card rank e.g. JT not TJ","JT", cardpairSameSuit3);
        String cardpairSameSuit4 = HandRankings.transformCardsForHandRanking("4s", "2d");
        Assert.assertEquals("Cards should have the characters in order of card rank e.g. 42 not 42", "42", cardpairSameSuit4);
        String cardpairSameSuit5 = HandRankings.transformCardsForHandRanking("2c", "5c");
        Assert.assertEquals("Cards should have the characters in order of card rank e.g. 52 not 25", "52 s", cardpairSameSuit5);
        String cardpairSameSuit6 = HandRankings.transformCardsForHandRanking("Ts", "Jd");
        Assert.assertEquals("Cards should have the characters in order of card rank e.g. JT not TJ","JT", cardpairSameSuit6);
    }

    @Test
    public void testGetEVRankOfACardPair() {
        HandRankings hr = new HandRankings();
        double evRank = hr.getEVRankOfCardPair("JJ");
        Assert.assertEquals("The EV rank of JJ should be 4.0",4.0, evRank, 0.1);
    }
}
