package com.saccarn.poker.testspreflopvalues;

import com.saccarn.poker.ai.preflop.PreFlopValues;
import com.saccarn.poker.harnass.Harness;
import com.saccarn.poker.preflopvalues.DefaultValues;
import com.saccarn.poker.preflopvalues.PreFlopTest1;

/**
 * Created by Neil on 12/05/2017.
 */
public class TestDefaultVsPreFlopValuesTest1 {

    public static void main(String [] args) {
        PreFlopValues defaultPFV = new DefaultValues();
        PreFlopValues pfv1 = new PreFlopTest1();
        Harness harness = new Harness(defaultPFV, pfv1);
        harness.playOutHands();
    }
}
