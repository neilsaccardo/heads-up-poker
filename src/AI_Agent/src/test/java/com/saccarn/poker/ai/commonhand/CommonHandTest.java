package com.saccarn.poker.ai.commonhand;

import com.saccarn.poker.ai.HandType;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Neil on 18/04/2017.
 */
public class CommonHandTest {

    @Test
    public void testCommonHandTwoPair() {
        String c1 = "Js";
        String c2 = "Jd";
        String c3 = "Ac";
        String c4 = "As";
        String c5 = "3s";
        String [] boardCards = {c1, c2, c3, c4, c5};
        CommonHand ch = new CommonHand(6, boardCards);
        boolean actual = ch.isCommonHand();
        Assert.assertEquals("5 cards should evaluate to a two pair (rank 6). Should be false, two pair should not be identifed as common", false, actual);
    }

    @Test
    public void testCommonHandAceHighPair() {
        String c1 = "Js";
        String c2 = "Qd";
        String c3 = "Ac";
        String c4 = "As";
        String c5 = "4s";
        String [] boardCards = {c1, c2, c3, c4, c5};
        CommonHand ch = new CommonHand(7, boardCards);
        boolean actual = ch.isCommonHand();
        Assert.assertEquals("5 cards should evaluate to a ace high pair (rank 7). Should be true, should be identifed as common", true, actual);
    }


    @Test
    public void testCommonHandKingHighPair() {
        String c1 = "Ks";
        String c2 = "Kh";
        String c3 = "6c";
        String c4 = "2d";
        String c5 = "4s";
        String [] boardCards = {c1, c2, c3, c4, c5};
        CommonHand ch = new CommonHand(8, boardCards);
        boolean actual = ch.isCommonHand();
        Assert.assertEquals("5 cards should evaluate to a king high pair (rank 8). Should be true, should be identified as common", actual, true);
    }

    @Test
    public void testCommonHandQueenHighPair() {
        String c1 = "As";
        String c2 = "2h";
        String c3 = "Qc";
        String c4 = "8d";
        String c5 = "Qh";
        String [] boardCards = {c1, c2, c3, c4, c5};
        CommonHand ch = new CommonHand(9, boardCards);
        boolean actual = ch.isCommonHand();
        Assert.assertEquals("5 cards should evaluate to a queen high pair (rank 9). Should be true, should be identified as common", true, actual);
    }

    @Test
    public void testCommonHandJackHighPair() {
        String c1 = "Js";
        String c2 = "2h";
        String c3 = "Jc";
        String c4 = "8d";
        String c5 = "Qh";
        String [] boardCards = {c1, c2, c3, c4, c5};
        CommonHand ch = new CommonHand(10, boardCards);
        boolean actual = ch.isCommonHand();
        Assert.assertEquals("5 cards should evaluate to a jack high pair (rank 10). Should be true, should be identified as common", true, actual);
    }

    @Test
    public void testCommonHandTenHighPair() {
        String c1 = "Js";
        String c2 = "2h";
        String c3 = "Kc";
        String c4 = "Td";
        String c5 = "Th";
        String [] boardCards = {c1, c2, c3, c4, c5};
        CommonHand ch = new CommonHand(11, boardCards);
        boolean actual = ch.isCommonHand();
        Assert.assertEquals("5 cards should evaluate to a ten high pair (rank 11). Should be true, should be identified as common", true, actual);
    }

    @Test
    public void testCommonHand9HighPair() {
        String c1 = "Qs";
        String c2 = "9h";
        String c3 = "Jc";
        String c4 = "9d";
        String c5 = "Th";
        String [] boardCards = {c1, c2, c3, c4, c5};
        CommonHand ch = new CommonHand(12, boardCards);
        boolean actual = ch.isCommonHand();
        Assert.assertEquals("5 cards should evaluate to a nine high pair (rank 12). Should be true, should be identified as common", true, actual);
    }

    @Test
    public void testCommonHand8HighPair() {
        String c1 = "8d";
        String c2 = "8h";
        String c3 = "Jc";
        String c4 = "9d";
        String c5 = "Th";
        String [] boardCards = {c1, c2, c3, c4, c5};
        CommonHand ch = new CommonHand(13, boardCards);
        boolean actual = ch.isCommonHand();
        Assert.assertEquals("5 cards should evaluate to a eight high pair (rank 13). Should be true, should be identified as common", true, actual);
    }


    @Test
    public void testCommonHand7HighPair() {
        String c1 = "7d";
        String c2 = "7h";
        String c3 = "Jc";
        String c4 = "9d";
        String c5 = "Th";
        String [] boardCards = {c1, c2, c3, c4, c5};
        CommonHand ch = new CommonHand(14, boardCards);
        boolean actual = ch.isCommonHand();
        Assert.assertEquals("5 cards should evaluate to a seven high pair (rank 14). Should be true, should be identified as common", true, actual);
    }


    @Test
    public void testCommonHand6HighPair() {
        String c1 = "6d";
        String c2 = "7h";
        String c3 = "Jc";
        String c4 = "6d";
        String c5 = "Th";
        String [] boardCards = {c1, c2, c3, c4, c5};
        CommonHand ch = new CommonHand(15, boardCards);
        boolean actual = ch.isCommonHand();
        Assert.assertEquals("5 cards should evaluate to a six high pair (rank 15). Should be true, should be identified as common", true, actual);
    }

    @Test
    public void testCommonHand5HighPair() {
        String c1 = "6d";
        String c2 = "5h";
        String c3 = "Jc";
        String c4 = "5c";
        String c5 = "Th";
        String [] boardCards = {c1, c2, c3, c4, c5};
        CommonHand ch = new CommonHand(16, boardCards);
        boolean actual = ch.isCommonHand();
        Assert.assertEquals("5 cards should evaluate to a five high pair (rank 16). Should be true, should be identified as common", true, actual);
    }


    @Test
    public void testCommonHand4HighPair() {
        String c1 = "4d";
        String c2 = "5h";
        String c3 = "Jc";
        String c4 = "4c";
        String c5 = "Th";
        String [] boardCards = {c1, c2, c3, c4, c5};
        CommonHand ch = new CommonHand(17, boardCards);
        boolean actual = ch.isCommonHand();
        Assert.assertEquals("5 cards should evaluate to a four high pair (rank 17). Should be true, should be identified as common", true, actual);
    }

    @Test
    public void testCommonHand3HighPair() {
        String c1 = "4d";
        String c2 = "5h";
        String c3 = "3c";
        String c4 = "3c";
        String c5 = "Th";
        String [] boardCards = {c1, c2, c3, c4, c5};
        CommonHand ch = new CommonHand(18, boardCards);
        boolean actual = ch.isCommonHand();
        Assert.assertEquals("5 cards should evaluate to a three high pair (rank 18). Should be true, should be identified as common", true, actual);
    }

    @Test
    public void testCommonHand2HighPair() {
        String c1 = "4d";
        String c2 = "5h";
        String c3 = "2c";
        String c4 = "3c";
        String c5 = "2h";
        String [] boardCards = {c1, c2, c3, c4, c5};
        CommonHand ch = new CommonHand(19, boardCards);
        boolean actual = ch.isCommonHand();
        Assert.assertEquals("5 cards should evaluate to a two high pair (rank 19). Should be true, should be identified as common", true, actual);
    }

    @Test
    public void testCommonHandAceHighCard() {
        String c1 = "Js";
        String c2 = "Qd";
        String c3 = "Ac";
        String c4 = "Ts";
        String c5 = "4s";
        String [] boardCards = {c1, c2, c3, c4, c5};
        CommonHand ch = new CommonHand(20, boardCards);
        boolean actual = ch.isCommonHand();
        Assert.assertEquals("5 cards should evaluate to a ace high (rank 20). Should be true, should be identified as common", true, actual);
    }

    @Test
    public void testCommonHandKingHighCard() {
        String c1 = "Js";
        String c2 = "Qd";
        String c3 = "6c";
        String c4 = "Kh";
        String c5 = "4s";
        String [] boardCards = {c1, c2, c3, c4, c5};
        CommonHand ch = new CommonHand(21, boardCards);
        boolean actual = ch.isCommonHand();
        Assert.assertEquals("5 cards should evaluate to a king high (rank 21). Should be true, should be identified as common", true, actual);
    }

    @Test
    public void testCommonHandQueenHighCard() {
        String c1 = "Js";
        String c2 = "Qd";
        String c3 = "2c";
        String c4 = "Ts";
        String c5 = "4s";
        String [] boardCards = {c1, c2, c3, c4, c5};
        CommonHand ch = new CommonHand(22, boardCards);
        boolean actual = ch.isCommonHand();
        Assert.assertEquals("5 cards should evaluate to a ace high (rank 22). Should be true, should be identified as common", true, actual);
    }


    @Test
    public void testCommonHandMedHighCard() {
        String c1 = "Js";
        String c2 = "3d";
        String c3 = "2c";
        String c4 = "Ts";
        String c5 = "4s";
        String [] boardCards = {c1, c2, c3, c4, c5};
        CommonHand ch = new CommonHand(23, boardCards);
        boolean actual = ch.isCommonHand();
        Assert.assertEquals("5 cards should evaluate to a med high (rank 23). Should be true, should be identified as common", true, actual);
        c1 = "4s";
        c2 = "3h";
        c3 = "2c";
        c4 = "Td";
        c5 = "7h";
        boardCards = new String[]{c1, c2, c3, c4, c5};
        ch = new CommonHand(23, boardCards);
        actual = ch.isCommonHand();
        Assert.assertEquals("5 cards should evaluate to a med high (rank 23). Should be true, should be identified as common", true, actual);
    }

    @Test
    public void testCommonHandLowHighCard() {
        String c1 = "4s";
        String c2 = "3d";
        String c3 = "2c";
        String c4 = "6d";
        String c5 = "7h";
        String [] boardCards = {c1, c2, c3, c4, c5};
        CommonHand ch = new CommonHand(24, boardCards);
        boolean actual = ch.isCommonHand();
        Assert.assertEquals("5 cards should evaluate to a low high card(rank 24). Should be true, should be identified as common", true, actual);
    }

}
