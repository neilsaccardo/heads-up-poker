package com.saccarn.poker.dbprocessor;

import org.junit.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Neil on 05/04/2017.
 */
public class PlayerTypeClustererTest {

    private PlayerTypeClusterer ptc;
    private int numClusters = 4;
    private List<Map<String, Double>> centroids;
    private DataLoader dl;

    @Before
    public void setUp() {
        ptc = new PlayerTypeClusterer();
        ptc.getClusters(numClusters); //save into DB
        dl = new DataLoader();
        centroids = dl.getCentroids();

    }

    @Test
    public void testGetCentroidsCorrectAmountSaved() {
        Assert.assertEquals("Should save the correct number of centroids into correct mongo db collection", numClusters, centroids.size() );
    }


    @Test
    public void testGetCentroidsHaveCorrectOpponentModelLayout() {
        boolean clusterHaveKeyValuesForOppModel = true;
        for (int i = 0; i < centroids.size(); i++) {
            clusterHaveKeyValuesForOppModel = clusterHaveKeyValuesForOppModel && hasOppModelKeys(centroids.get(i));
        }
        Assert.assertEquals("The cluster have the correct opponent model layout (correct keys for use of AI_Agent)",
                true, clusterHaveKeyValuesForOppModel);
    }

    private boolean hasOppModelKeys(Map<String, Double> oppModel) {
        return oppModel.containsKey(DataLoaderStrings.PRE_FLOP_FOLDED_RATIO)
                && oppModel.containsKey(DataLoaderStrings.RIVER_FOLDED_RATIO)
                && oppModel.containsKey(DataLoaderStrings.TURN_FOLDED_RATIO)
                && oppModel.containsKey(DataLoaderStrings.FLOP_FOLDED_RATIO);
    }
}
