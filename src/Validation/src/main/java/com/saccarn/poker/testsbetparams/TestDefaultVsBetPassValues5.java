package com.saccarn.poker.testsbetparams;

import com.saccarn.poker.ai.betpassdeterminer.BetPassActionValues;
import com.saccarn.poker.betpassvalues.BetPassValuesTest5;
import com.saccarn.poker.betpassvalues.DefaultValues;
import com.saccarn.poker.harnass.Harness;

/**
 * Created by Neil on 26/04/2017.
 */
public class TestDefaultVsBetPassValues5 {

    public static void main(String [] args) {
        BetPassActionValues defaultValues = new DefaultValues();
        BetPassActionValues bpv5 = new BetPassValuesTest5();
        Harness harness = new Harness(defaultValues, bpv5);
        harness.playOutHands();
    }
}
