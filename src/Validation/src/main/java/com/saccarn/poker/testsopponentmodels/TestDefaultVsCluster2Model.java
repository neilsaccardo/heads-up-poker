package com.saccarn.poker.testsopponentmodels;

import com.saccarn.poker.harnass.Harness;
import com.saccarn.poker.opponentmodels.OpponentModel;

import java.util.Map;

/**
 * Created by Neil on 26/04/2017.
 */
public class TestDefaultVsCluster2Model {

    public static void main(String [] args) {
        Map<String, Double> defaultOpponentModel = OpponentModel.getDefaultOpponentModel();
        Map<String, Double> cluster2Model = OpponentModel.getOpponentModelObtainedFromClustering2();
        Harness harness = new Harness(defaultOpponentModel, cluster2Model);
        harness.playOutHands();
    }

}
