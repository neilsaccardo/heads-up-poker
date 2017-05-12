package com.saccarn.poker.preflopvalues;

import com.saccarn.poker.ai.preflop.PreFlopValues;

/**
 * Created by Neil on 12/05/2017.
 */
public class PreFlopValuesTest4 extends PreFlopValues {

    public int getRankThreshold() {
        return 42;
    }

    public int getFoldThreshold() {
        return 90;
    }

}
