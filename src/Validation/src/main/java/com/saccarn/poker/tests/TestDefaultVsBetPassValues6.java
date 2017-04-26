package com.saccarn.poker.tests;

import com.saccarn.poker.ai.betpassdeterminer.BetPassActionValues;
import com.saccarn.poker.betpassvalues.BetPassValuesTest5;
import com.saccarn.poker.betpassvalues.BetPassValuesTest6;
import com.saccarn.poker.betpassvalues.DefaultValues;
import com.saccarn.poker.harnass.Harness;

/**
 * Created by Neil on 26/04/2017.
 */
public class TestDefaultVsBetPassValues6 {


    public static void main(String [] args) {
        BetPassActionValues defaultValues = new DefaultValues();
        BetPassActionValues bpv6 = new BetPassValuesTest6();
        Harness harness = new Harness(defaultValues, bpv6);
        harness.playOutHands();
    }
}
