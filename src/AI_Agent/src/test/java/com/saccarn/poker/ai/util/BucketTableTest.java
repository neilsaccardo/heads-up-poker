package com.saccarn.poker.ai.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Neil on 20/04/2017.
 */
public class BucketTableTest {

    @Test
    public void testBucketTableTest() {
        BucketTable bt = new BucketTable();
        double d = bt.get(3);
        Assert.assertEquals("Bucket Table should return a double between (0 and 100) as value ", true, ((d > 0.0) && (d < 100.0)));
    }

    @Test (expected = NullPointerException.class)
    public void testBucketTableTestUnderLowerBound() {
        BucketTable bt = new BucketTable();
        double d = bt.get(-1);
        Assert.assertEquals("Bucket Table should throw null pointer exception when key < 0.", false, true);
    }

    @Test (expected = NullPointerException.class)
    public void testBucketTableTestAboveUpperBound() {
        BucketTable bt = new BucketTable();
        double d = bt.get(25);
        Assert.assertEquals("Bucket Table should throw null pointer exception when key > 24.", false, true);
    }

}
