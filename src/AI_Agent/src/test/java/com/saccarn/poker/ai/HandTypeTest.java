package com.saccarn.poker.ai;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Neil on 15/03/2017.
 */
public class HandTypeTest {

    @Test
    public void testHandTypeStraightFlush() {
        HandType ht = new HandType();
        String c1 = "Js";
        String c2 = "Qs";
        String c3 = "Ks";
        String c4 = "Ts";
        String c5 = "As";
        int rank = ht.calculateHandRanking(c1, c2, c3, c4, c5);
        Assert.assertEquals("5 cards should evaluate to a flush straight (rank 0)", 0, rank);
    }

    @Test
    public void testHandAceBottomTypeStraightFlush() {
        HandType ht = new HandType();
        String c1 = "2s";
        String c2 = "4s";
        String c3 = "As";
        String c4 = "3s";
        String c5 = "5s";
        int rank = ht.calculateHandRanking(c1, c2, c3, c4, c5);
        Assert.assertEquals("5 cards should evaluate to a flush straight (rank 0)", 0, rank);
    }
    @Test
    public void testHandTypeFourOfAKind() {
        HandType ht = new HandType();
        String c1 = "Ks";
        String c2 = "Kd";
        String c3 = "Kc";
        String c4 = "Kh";
        String c5 = "3s";
        int rank = ht.calculateHandRanking(c1, c2, c3, c4, c5);
        Assert.assertEquals("5 cards should evaluate to a four of a kind (rank 1)", 1, rank);
    }

    @Test
    public void testHandTypeFullHouse() {
        HandType ht = new HandType();
        String c1 = "8h";
        String c2 = "8d";
        String c3 = "8c";
        String c4 = "4d";
        String c5 = "4c";
        int rank = ht.calculateHandRanking(c1, c2, c3, c4, c5);
        Assert.assertEquals("5 cards should evaluate to a full house (rank 2)", 2, rank);
    }


    @Test
    public void testHandTypeFlush() {
        HandType ht = new HandType();
        String c1 = "Js";
        String c2 = "Qs";
        String c3 = "Ks";
        String c4 = "2s";
        String c5 = "3s";
        int rank = ht.calculateHandRanking(c1, c2, c3, c4, c5);
        Assert.assertEquals("5 cards should evaluate to a flush (rank 3)", 3, rank);
    }

    @Test
    public void testHandTypeStraight() {
        HandType ht = new HandType();
        String c1 = "8h";
        String c2 = "4d";
        String c3 = "6c";
        String c4 = "7d";
        String c5 = "5c";
        int rank = ht.calculateHandRanking(c1, c2, c3, c4, c5);
        Assert.assertEquals("5 cards should evaluate to a straight (rank 4)", 4, rank);
    }

    @Test
    public void testHandAceBottomTypeStraight() {
        HandType ht = new HandType();
        String c1 = "2s";
        String c2 = "4d";
        String c3 = "Ac";
        String c4 = "3d";
        String c5 = "5c";
        int rank = ht.calculateHandRanking(c1, c2, c3, c4, c5);
        Assert.assertEquals("5 cards should evaluate to a straight (rank 4)", 4, rank);
    }

    @Test
    public void testHandTypeThreeOfAKind() {
        HandType ht = new HandType();
        String c1 = "Js";
        String c2 = "Jd";
        String c3 = "Jc";
        String c4 = "2s";
        String c5 = "3s";
        int rank = ht.calculateHandRanking(c1, c2, c3, c4, c5);
        Assert.assertEquals("5 cards should evaluate to a three of a kind (rank 5)", 5, rank);
    }

    @Test
    public void testHandTypeTwoPair() {
        HandType ht = new HandType();
        String c1 = "Js";
        String c2 = "Jd";
        String c3 = "Ac";
        String c4 = "As";
        String c5 = "3s";
        int rank = ht.calculateHandRanking(c1, c2, c3, c4, c5);
        Assert.assertEquals("5 cards should evaluate to a two pair (rank 6)", 6, rank);
    }

    @Test
    public void testHandTypeAceHighPair() {
        HandType ht = new HandType();
        String c1 = "Js";
        String c2 = "Qd";
        String c3 = "Ac";
        String c4 = "As";
        String c5 = "4s";
        int rank = ht.calculateHandRanking(c1, c2, c3, c4, c5);
        Assert.assertEquals("5 cards should evaluate to a ace high pair (rank 7)", 7, rank);
    }


    @Test
    public void testHandTypeKingHighPair() {
        HandType ht = new HandType();
        String c1 = "Ks";
        String c2 = "Kh";
        String c3 = "6c";
        String c4 = "2d";
        String c5 = "4s";
        int rank = ht.calculateHandRanking(c1, c2, c3, c4, c5);
        Assert.assertEquals("5 cards should evaluate to a king high pair (rank 7)", 8, rank);
    }

    @Test
    public void testHandTypeQueenHighPair() {
        HandType ht = new HandType();
        String c1 = "As";
        String c2 = "2h";
        String c3 = "Qc";
        String c4 = "8d";
        String c5 = "Qh";
        int rank = ht.calculateHandRanking(c1, c2, c3, c4, c5);
        Assert.assertEquals("5 cards should evaluate to a queen high pair (rank 9)", 9, rank);
    }

    @Test
    public void testHandTypeJackHighPair() {
        HandType ht = new HandType();
        String c1 = "Js";
        String c2 = "2h";
        String c3 = "Jc";
        String c4 = "8d";
        String c5 = "Qh";
        int rank = ht.calculateHandRanking(c1, c2, c3, c4, c5);
        Assert.assertEquals("5 cards should evaluate to a jack high pair (rank 10)", 10, rank);
    }

    @Test
    public void testHandTypeTenHighPair() {
        HandType ht = new HandType();
        String c1 = "Js";
        String c2 = "2h";
        String c3 = "Kc";
        String c4 = "Td";
        String c5 = "Th";
        int rank = ht.calculateHandRanking(c1, c2, c3, c4, c5);
        Assert.assertEquals("5 cards should evaluate to a ten high pair (rank 11)", 11, rank);
    }

    @Test
    public void testHandType9HighPair() {
        HandType ht = new HandType();
        String c1 = "Qs";
        String c2 = "9h";
        String c3 = "Jc";
        String c4 = "9d";
        String c5 = "Th";
        int rank = ht.calculateHandRanking(c1, c2, c3, c4, c5);
        Assert.assertEquals("5 cards should evaluate to a nine high pair (rank 12)", 12, rank);
    }

    @Test
    public void testHandType8HighPair() {
        HandType ht = new HandType();
        String c1 = "8d";
        String c2 = "8h";
        String c3 = "Jc";
        String c4 = "9d";
        String c5 = "Th";
        int rank = ht.calculateHandRanking(c1, c2, c3, c4, c5);
        Assert.assertEquals("5 cards should evaluate to a seven high pair (rank 13)", 13, rank);
    }


    @Test
    public void testHandType7HighPair() {
        HandType ht = new HandType();
        String c1 = "7d";
        String c2 = "7h";
        String c3 = "Jc";
        String c4 = "9d";
        String c5 = "Th";
        int rank = ht.calculateHandRanking(c1, c2, c3, c4, c5);
        Assert.assertEquals("5 cards should evaluate to a seven high pair (rank 14)", 14, rank);
    }


    @Test
    public void testHandType6HighPair() {
        HandType ht = new HandType();
        String c1 = "6d";
        String c2 = "7h";
        String c3 = "Jc";
        String c4 = "6d";
        String c5 = "Th";
        int rank = ht.calculateHandRanking(c1, c2, c3, c4, c5);
        Assert.assertEquals("5 cards should evaluate to a six high pair (rank 15)", 15, rank);
    }

    @Test
    public void testHandType5HighPair() {
        HandType ht = new HandType();
        String c1 = "6d";
        String c2 = "5h";
        String c3 = "Jc";
        String c4 = "5c";
        String c5 = "Th";
        int rank = ht.calculateHandRanking(c1, c2, c3, c4, c5);
        Assert.assertEquals("5 cards should evaluate to a five high pair (rank 16)", 16, rank);
    }


    @Test
    public void testHandType4HighPair() {
        HandType ht = new HandType();
        String c1 = "4d";
        String c2 = "5h";
        String c3 = "Jc";
        String c4 = "4c";
        String c5 = "Th";
        int rank = ht.calculateHandRanking(c1, c2, c3, c4, c5);
        Assert.assertEquals("5 cards should evaluate to a four high pair (rank 17)", 17, rank);
    }

    @Test
    public void testHandType3HighPair() {
        HandType ht = new HandType();
        String c1 = "4d";
        String c2 = "5h";
        String c3 = "3c";
        String c4 = "3c";
        String c5 = "Th";
        int rank = ht.calculateHandRanking(c1, c2, c3, c4, c5);
        Assert.assertEquals("5 cards should evaluate to a four high pair (rank 18)", 18, rank);
    }

    @Test
    public void testHandType2HighPair() {
        HandType ht = new HandType();
        String c1 = "4d";
        String c2 = "5h";
        String c3 = "2c";
        String c4 = "3c";
        String c5 = "2h";
        int rank = ht.calculateHandRanking(c1, c2, c3, c4, c5);
        Assert.assertEquals("5 cards should evaluate to a four high pair (rank 18)", 19, rank);
    }

    @Test
    public void testHandTypeAceHighCard() {
        HandType ht = new HandType();
        String c1 = "Js";
        String c2 = "Qd";
        String c3 = "Ac";
        String c4 = "Ts";
        String c5 = "4s";
        int rank = ht.calculateHandRanking(c1, c2, c3, c4, c5);
        Assert.assertEquals("5 cards should evaluate to a ace high (rank 20)", 20, rank);
    }

    @Test
    public void testHandTypeKingHighCard() {
        HandType ht = new HandType();
        String c1 = "Js";
        String c2 = "Qd";
        String c3 = "6c";
        String c4 = "Kh";
        String c5 = "4s";
        int rank = ht.calculateHandRanking(c1, c2, c3, c4, c5);
        Assert.assertEquals("5 cards should evaluate to a king high (rank 21)", 21, rank);
    }

    @Test
    public void testHandTypeQueenHighCard() {
        HandType ht = new HandType();
        String c1 = "Js";
        String c2 = "Qd";
        String c3 = "2c";
        String c4 = "Ts";
        String c5 = "4s";
        int rank = ht.calculateHandRanking(c1, c2, c3, c4, c5);
        Assert.assertEquals("5 cards should evaluate to a queen high pair (rank 22)", 22, rank);
    }


    @Test
    public void testHandTypeMedHighCard() {
        HandType ht = new HandType();
        String c1 = "Js";
        String c2 = "3d";
        String c3 = "2c";
        String c4 = "Ts";
        String c5 = "4s";
        int rank = ht.calculateHandRanking(c1, c2, c3, c4, c5);
        Assert.assertEquals("5 cards should evaluate to a med high card (rank 23)", 23, rank);

        c1 = "4s";
        c2 = "3h";
        c3 = "2c";
        c4 = "Td";
        c5 = "7h";
        rank = ht.calculateHandRanking(c1, c2, c3, c4, c5);
        Assert.assertEquals("5 cards should evaluate to a med high card (rank 23)", 23, rank);

    }
    @Test
    public void testHandTypeLowHighCard() {
        HandType ht = new HandType();
        String c1 = "4s";
        String c2 = "3d";
        String c3 = "2c";
        String c4 = "6d";
        String c5 = "7h";
        int rank = ht.calculateHandRanking(c1, c2, c3, c4, c5);
        Assert.assertEquals("5 cards should evaluate to a low high card(rank 24)", 24, rank);
    }

    @Test
    public void testTwoCardHandLowHighCard() {
        HandType ht = new HandType();
        String c1 = "4s";
        String c2 = "3d";
        int rank = ht.calculateHandRanking(c1, c2);
        Assert.assertEquals("2 cards should evaluate to a low high card (rank 16)", 16, rank);
    }
    @Test
    public void testTwoCardHandPair() {
        HandType ht = new HandType();
        String c1 = "4s";
        String c2 = "4d";
        int rank = ht.calculateHandRanking(c1, c2);
        Assert.assertEquals("2 cards should evaluate to a low pair (rank 11)", 11, rank);
        c1 = "Ts";
        c2 = "Th";
        rank = ht.calculateHandRanking(c1, c2);
        Assert.assertEquals("2 cards should evaluate to a medium pair (rank 10)", 10, rank);
        c1 = "As";
        c2 = "Ah";
        rank = ht.calculateHandRanking(c1, c2);
        Assert.assertEquals("2 cards should evaluate to a ace high pair (rank 11)", 7, rank);
    }

    @Test
    public void test6cards() {
        String c1 = "4s";
        String c2 = "4d";
        String c3 = "3s";
        String c4 = "3d";
        String c5 = "As";
        String c6 = "Ad";
        HandType ht = new HandType();
        int rank = ht.calculateHandRanking(c1, c2, c3, c4, c5, c6);
        Assert.assertEquals("6 cards should evaluate to a two pair (rank 6)", 6, rank);
    }

    @Test
    public void test7cards() {
        String c1 = "4s";
        String c2 = "4d";
        String c3 = "3s";
        String c4 = "3d";
        String c5 = "As";
        String c6 = "Ad";
        String c7 = "Qs";
        HandType ht = new HandType();
        int rank = ht.calculateHandRanking(c1, c2, c3, c4, c5, c6, c7);
        Assert.assertEquals("7 cards should evaluate to a two pair (rank 6)", 6, rank);
    }

    @Test
    public void test7cardsFlushStraight() { //As 2s 3s 4s 5s Ad 5d
        String c1 = "2s";
        String c2 = "3s";
        String c3 = "4s";
        String c4 = "5d";
        String c5 = "5s";
        String c6 = "Ad";
        String c7 = "As";
        HandType ht = new HandType();
        int rank = ht.calculateHandRanking(c1, c2, c3, c4, c5, c6, c7);
        Assert.assertEquals("7 cards should evaluate to a flush straight (rank 0)", 0, rank);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testCalculateHandRankingTwoCardBadInput() {
        String c1 = "2s21";
        String c2 = "3s";
        HandType ht = new HandType();
        ht.calculateHandRanking(c1, c2);
        Assert.assertEquals("IllegalArgumentException should be thrown when input is bad", true, false);
    }
}
