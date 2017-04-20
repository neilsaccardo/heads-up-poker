package com.saccarn.poker.ai.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Neil on 20/04/2017.
 */
public class CardValueTest {

    @Test
    public void testGetCardValueMinValue() {
        CardValueTable cvt = new CardValueTable();
        int rank = cvt.get('2');
        Assert.assertEquals("2 in the CardValueTable should be ranked as the minimum value of 0. ", 0, rank);
    }

    @Test
    public void testGetCardValueAce() {
        CardValueTable cvt = new CardValueTable();
        int rank = cvt.get('A');
        Assert.assertEquals("A in the CardValueTable should be ranked as the minimum value of 0. ", 12, rank);
    }

    @Test
    public void testGetCardValueKing() {
        CardValueTable cvt = new CardValueTable();
        int rank = cvt.get('K');
        Assert.assertEquals("K in the CardValueTable should be ranked as the minimum value of 0. ", 11, rank);
    }

    @Test
    public void testGetCardValueQueen() {
        CardValueTable cvt = new CardValueTable();
        int rank = cvt.get('Q');
        Assert.assertEquals("Q in the CardValueTable should be ranked as the minimum value of 0. ", 10, rank);
    }

    @Test
    public void testGetCardValueJack() {
        CardValueTable cvt = new CardValueTable();
        int rank = cvt.get('J');
        Assert.assertEquals("J in the CardValueTable should be ranked as the minimum value of 0. ", 9, rank);
    }

    @Test
    public void testGetCardValueTen() {
        CardValueTable cvt = new CardValueTable();
        int rank = cvt.get('T');
        Assert.assertEquals("T in the CardValueTable should be ranked as the minimum value of 0. ", 8, rank);
    }


}
