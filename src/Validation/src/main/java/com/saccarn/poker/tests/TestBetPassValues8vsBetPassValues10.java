package com.saccarn.poker.tests;

import com.saccarn.poker.ai.betpassdeterminer.BetPassActionValues;
import com.saccarn.poker.betpassvalues.BetPassValuesTest10;
import com.saccarn.poker.betpassvalues.BetPassValuesTest8;
import com.saccarn.poker.harnass.Harness;

/**
 * Created by Neil on 26/04/2017.
 */
public class TestBetPassValues8vsBetPassValues10 {

    public static void main(String [] args) {
        BetPassActionValues bpv8 = new BetPassValuesTest8();
        BetPassActionValues bpv10 = new BetPassValuesTest10();
        Harness harness = new Harness(bpv8, bpv10);
        harness.playOutHands();
    }
}
