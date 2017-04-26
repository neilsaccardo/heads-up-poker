package com.saccarn.poker.testsbetparams;

import com.saccarn.poker.ai.betpassdeterminer.BetPassActionValues;
import com.saccarn.poker.betpassvalues.BetPassValuesTest13;
import com.saccarn.poker.betpassvalues.DefaultValues;
import com.saccarn.poker.harnass.Harness;

/**
 * Created by Neil on 26/04/2017.
 */
public class TestDefaultVsBetPassValues13 {

    public static void main(String [] args) {
        BetPassActionValues defaultValues = new DefaultValues();
        BetPassActionValues bpv13 = new BetPassValuesTest13();
        Harness harness = new Harness(defaultValues, bpv13);
        harness.playOutHands();
    }
}
