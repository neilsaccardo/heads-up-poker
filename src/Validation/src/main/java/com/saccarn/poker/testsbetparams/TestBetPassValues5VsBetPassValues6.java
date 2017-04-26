package com.saccarn.poker.testsbetparams;

import com.saccarn.poker.ai.betpassdeterminer.BetPassActionValues;
import com.saccarn.poker.betpassvalues.BetPassValuesTest5;
import com.saccarn.poker.betpassvalues.BetPassValuesTest6;
import com.saccarn.poker.harnass.Harness;

/**
 * Created by Neil on 26/04/2017.
 */
public class TestBetPassValues5VsBetPassValues6 {


    public static void main(String [] args) {
        BetPassActionValues bpv5 = new BetPassValuesTest5();
        BetPassActionValues bpv6 = new BetPassValuesTest6();
        Harness harness = new Harness(bpv5, bpv6);
        harness.playOutHands();
    }
}
