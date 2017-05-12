package com.saccarn.poker.preflopvalues;

import com.saccarn.poker.ai.preflop.PreFlopValues;

/**
 * Created by Neil on 12/05/2017.
 */
public class PreFlopValuesTest5  extends PreFlopValues{

    public int getRankThreshold() {
        return 37;
    }

    public int getFoldThreshold() {
        return 90;
    }
}
