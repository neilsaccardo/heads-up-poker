package com.saccarn.poker.opponentmodels;

import com.saccarn.poker.dbprocessor.DataLoaderStrings;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Neil on 26/04/2017.
 */
public class OpponentModel {

    public static Map<String,Double> getDefaultOpponentModel() {
        Map<String, Double>  model = new HashMap<>();
        model.put(DataLoaderStrings.PRE_FLOP_FOLDED_RATIO, 0.30429600503908655);
        model.put(DataLoaderStrings.FLOP_FOLDED_RATIO, 0.07954173703910809);
        model.put(DataLoaderStrings.TURN_FOLDED_RATIO, 0.03401239437996795);
        model.put(DataLoaderStrings.RIVER_FOLDED_RATIO, 0.011076351136104537);
        return model;
    }


    public static Map<String,Double> getTightOpponentModel() {
        Map<String, Double>  model = new HashMap<>();
        model.put(DataLoaderStrings.PRE_FLOP_FOLDED_RATIO, 0.25);
        model.put(DataLoaderStrings.FLOP_FOLDED_RATIO, 0.5);
        model.put(DataLoaderStrings.TURN_FOLDED_RATIO, 0.05);
        model.put(DataLoaderStrings.RIVER_FOLDED_RATIO, 0.05);
        return model;
    }

    public static Map<String,Double> getLooseOpponentModel() {
        Map<String, Double>  model = new HashMap<>();
        model.put(DataLoaderStrings.PRE_FLOP_FOLDED_RATIO, 0.05);
        model.put(DataLoaderStrings.FLOP_FOLDED_RATIO, 0.03);
        model.put(DataLoaderStrings.TURN_FOLDED_RATIO, 0.03);
        model.put(DataLoaderStrings.RIVER_FOLDED_RATIO, 0.03);
        return model;
    }


    public static Map<String,Double> getUnusualOpponentModel() {
        Map<String, Double>  model = new HashMap<>();
        model.put(DataLoaderStrings.PRE_FLOP_FOLDED_RATIO, 0.2);
        model.put(DataLoaderStrings.FLOP_FOLDED_RATIO, 0.05);
        model.put(DataLoaderStrings.TURN_FOLDED_RATIO, 0.3);
        model.put(DataLoaderStrings.RIVER_FOLDED_RATIO, 0.2);
        return model;
    }


    public static Map<String,Double> getOpponentModelObtainedFromClustering1() {
        Map<String, Double>  model = new HashMap<>();
        model.put(DataLoaderStrings.PRE_FLOP_FOLDED_RATIO, 0.25);
        model.put(DataLoaderStrings.FLOP_FOLDED_RATIO, 0.0);
        model.put(DataLoaderStrings.TURN_FOLDED_RATIO, 0.0);
        model.put(DataLoaderStrings.RIVER_FOLDED_RATIO, 0.16666666666666666);
        return model;
    }


    public static Map<String,Double> getOpponentModelObtainedFromClustering2() {
        Map<String, Double>  model = new HashMap<>();
        model.put(DataLoaderStrings.PRE_FLOP_FOLDED_RATIO, 0.25);
        model.put(DataLoaderStrings.FLOP_FOLDED_RATIO, 0.2857142857142857);
        model.put(DataLoaderStrings.TURN_FOLDED_RATIO, 0.0);
        model.put(DataLoaderStrings.RIVER_FOLDED_RATIO, 0.25);
        return model;
    }


    public static Map<String,Double> getOpponentModelObtainedFromClustering3() {
        Map<String, Double>  model = new HashMap<>();
        model.put(DataLoaderStrings.PRE_FLOP_FOLDED_RATIO, 0.06493506493506493);
        model.put(DataLoaderStrings.FLOP_FOLDED_RATIO, 0.1232876712328767);
        model.put(DataLoaderStrings.TURN_FOLDED_RATIO, 0.0625);
        model.put(DataLoaderStrings.RIVER_FOLDED_RATIO, 0.10344827586206896);
        return model;
    }


}
