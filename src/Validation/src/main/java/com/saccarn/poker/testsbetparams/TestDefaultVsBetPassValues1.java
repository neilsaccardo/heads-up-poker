package com.saccarn.poker.testsbetparams;

import com.saccarn.poker.ai.betpassdeterminer.BetPassActionValues;
import com.saccarn.poker.betpassvalues.BetPassValuesTest1;
import com.saccarn.poker.harnass.Harness;

/**
 * Created by Neil on 26/04/2017.
 */
public class TestDefaultVsBetPassValues1 {

    public static void main(String [] args) {
        BetPassActionValues bpv1 = new BetPassValuesTest1();
        Harness harness = new Harness(bpv1);
        harness.playOutHands();
    }

}
