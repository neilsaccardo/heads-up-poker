package com.saccarn.poker.testsbetparams;

import com.saccarn.poker.ai.betpassdeterminer.BetPassActionValues;
import com.saccarn.poker.betpassvalues.*;
import com.saccarn.poker.harnass.Harness;

/**
 * Created by Neil on 26/04/2017.
 */
public class TestBetPassValues8VsBetPassValues9 {

    public static void main(String [] args) {
        BetPassActionValues bpv8 = new BetPassValuesTest8();
        BetPassActionValues bpv9 = new BetPassValuesTest9();
        Harness harness = new Harness(bpv8, bpv9);
        harness.playOutHands();
    }
}
