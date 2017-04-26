package com.saccarn.poker.tests;

import com.saccarn.poker.ai.betpassdeterminer.BetPassActionValues;
import com.saccarn.poker.betpassvalues.BetPassValuesTest5;
import com.saccarn.poker.betpassvalues.BetPassValuesTest7;
import com.saccarn.poker.harnass.Harness;

/**
 * Created by Neil on 26/04/2017.
 */
public class TestBetPassValues5VsBetPassValues7 {

    public static void main(String [] args) {
        BetPassActionValues bpv5 = new BetPassValuesTest5();
        BetPassActionValues bpv7 = new BetPassValuesTest7();
        Harness harness = new Harness(bpv5, bpv7);
        harness.playOutHands();
    }
}
