package com.saccarn.poker.dbprocessor;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by Neil on 05/04/2017.
 */
public class PlayerTypeClustererTest {

    @Ignore    @Test
    public void testGetCentroidsCorrectAmountSaved() { // Kind of a 'God' test - a lot of stuff happening
        PlayerTypeClusterer ptc = new PlayerTypeClusterer();
        int n = 4;
        ptc.getClusters(n); //save into DB
        DataLoader dl = new DataLoader();
        List<Map<String, Double>> centroids = dl.getCentroids();
        Assert.assertEquals("Should save the correct number of centroids into correct mongo db collection", n, centroids.size() );
    }
}
