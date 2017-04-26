package com.saccarn.poker.tests;

import com.saccarn.poker.ai.betpassdeterminer.BetPassActionValues;
import com.saccarn.poker.betpassvalues.BetPassValuesTest13;
import com.saccarn.poker.betpassvalues.BetPassValuesTest14;
import com.saccarn.poker.betpassvalues.DefaultValues;
import com.saccarn.poker.harnass.Harness;

/**
 * Created by Neil on 26/04/2017.
 */
public class TestDefaultVsBetPassValues14 {

    public static void main(String [] args) {
        BetPassActionValues defaultValues = new DefaultValues();
        BetPassActionValues bpv14 = new BetPassValuesTest14();
        Harness harness = new Harness(defaultValues, bpv14);
        harness.playOutHands();
    }
}
