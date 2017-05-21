package com.saccarn.poker.testspreflopvalues;

import com.saccarn.poker.ai.preflop.PreFlopValues;
import com.saccarn.poker.harnass.Harness;
import com.saccarn.poker.preflopvalues.DefaultValues;
import com.saccarn.poker.preflopvalues.PreFlopValuesTest10;
import com.saccarn.poker.preflopvalues.PreFlopValuesTest11;

/**
 * Created by Neil on 12/05/2017.
 */
public class TestDefaultVsPreFlopValuesTest11 {


    public static void main(String [] args) {
        PreFlopValues defaultPFV = new DefaultValues();
        PreFlopValues pfv = new PreFlopValuesTest11();
        Harness harness = new Harness(defaultPFV, pfv);
        harness.playOutHands();
    }
}
