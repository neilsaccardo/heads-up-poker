package com.saccarn.poker.testsbetparams;

import com.saccarn.poker.ai.betpassdeterminer.BetPassActionValues;
import com.saccarn.poker.betpassvalues.BetPassValuesTest14;
import com.saccarn.poker.betpassvalues.BetPassValuesTest16;
import com.saccarn.poker.harnass.Harness;

/**
 * Created by Neil on 26/04/2017.
 */
public class TestBetPassValues14VsBetPassValues16 {

    public static void main(String [] args) {
        BetPassActionValues bpv14 = new BetPassValuesTest14();
        BetPassActionValues bpv16 = new BetPassValuesTest16();
        Harness harness = new Harness(bpv14, bpv16);
        harness.playOutHands();
    }
}
