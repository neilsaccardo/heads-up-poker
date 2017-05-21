package com.saccarn.poker.testsbetparams;

import com.saccarn.poker.ai.betpassdeterminer.BetPassActionValues;
import com.saccarn.poker.betpassvalues.BetPassValuesTest5;
import com.saccarn.poker.betpassvalues.BetPassValuesTest8;
import com.saccarn.poker.harnass.Harness;

/**
 * Created by Neil on 26/04/2017.
 */

public class TestBetPassValues5VsBetPassValues8 {

    public static void main(String [] args) {
        BetPassActionValues bpv5 = new BetPassValuesTest5();
        BetPassActionValues bpv8 = new BetPassValuesTest8();
        Harness harness = new Harness(bpv5, bpv8);
        harness.playOutHands();
    }
}
