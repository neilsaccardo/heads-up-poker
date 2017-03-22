package com.saccarn.poker.ai;

import com.saccarn.poker.dbprocessor.PlayerTypeClusterer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Neil on 07/03/2017.
 */
public class PlayerCluster {

    private List<Map<String, Double>> clusters;

    public static Map<String, Double> getPlayerInfo(int playerType) {
        return PlayerTypeClusterer.getCentroid(playerType);
    }



}
