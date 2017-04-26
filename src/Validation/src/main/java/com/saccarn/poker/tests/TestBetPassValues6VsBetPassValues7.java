package com.saccarn.poker.tests;

import com.saccarn.poker.ai.betpassdeterminer.BetPassActionValues;
import com.saccarn.poker.betpassvalues.BetPassValuesTest5;
import com.saccarn.poker.betpassvalues.BetPassValuesTest6;
import com.saccarn.poker.betpassvalues.BetPassValuesTest7;
import com.saccarn.poker.harnass.Harness;

/**
 * Created by Neil on 26/04/2017.
 */
public class TestBetPassValues6VsBetPassValues7 {

    public static void main(String [] args) {
        BetPassActionValues bpv6 = new BetPassValuesTest6();
        BetPassActionValues bpv7 = new BetPassValuesTest7();
        Harness harness = new Harness(bpv6, bpv7);
        harness.playOutHands();
    }

}
