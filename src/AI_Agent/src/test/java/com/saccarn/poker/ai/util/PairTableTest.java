package com.saccarn.poker.ai.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Neil on 20/04/2017.
 */
public class PairTableTest {

    @Test
    public void testPairTableTest() {
        PairTable bt = new PairTable();
        double d = bt.get('4');
        Assert.assertEquals("Pair Table should return a int between  (2 and 19 inclusive) as value ", true, ((d > 1) && (d < 20)));
    }

    @Test (expected = NullPointerException.class)
    public void testBucketTableTestUnderLowerBound() {
        BucketTable bt = new BucketTable();
        double d = bt.get('1');
        Assert.assertEquals("Pair Table should throw null pointer exception when key < 2.", false, true);
    }

    @Test (expected = NullPointerException.class)
    public void testBucketTableTestAboveUpperBound() {
        BucketTable bt = new BucketTable();
        double d = bt.get('w');
        Assert.assertEquals("Bucket Table should throw null pointer exception when key not in table.", false, true);
    }


}
