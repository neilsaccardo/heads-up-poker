package com.saccarn.poker.tests;

import com.saccarn.poker.ai.betpassdeterminer.BetPassActionValues;
import com.saccarn.poker.betpassvalues.BetPassValuesTest11;
import com.saccarn.poker.betpassvalues.BetPassValuesTest12;
import com.saccarn.poker.betpassvalues.DefaultValues;
import com.saccarn.poker.harnass.Harness;

/**
 * Created by Neil on 26/04/2017.
 */
public class TestDefaultVsBetPassValues12 {

    public static void main(String [] args) {
        BetPassActionValues defaultValues = new DefaultValues();
        BetPassActionValues bpv12 = new BetPassValuesTest12();
        Harness harness = new Harness(defaultValues, bpv12);
        harness.playOutHands();
    }
}
