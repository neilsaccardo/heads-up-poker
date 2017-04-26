package com.saccarn.poker.tests;

import com.saccarn.poker.ai.betpassdeterminer.BetPassActionValues;
import com.saccarn.poker.betpassvalues.BetPassValuesTest10;
import com.saccarn.poker.betpassvalues.BetPassValuesTest15;
import com.saccarn.poker.betpassvalues.BetPassValuesTest16;
import com.saccarn.poker.betpassvalues.BetPassValuesTest8;
import com.saccarn.poker.harnass.Harness;

/**
 * Created by Neil on 26/04/2017.
 */
public class TestBetPassValues15VsBetPassValues16 {

    public static void main(String [] args) {
        BetPassActionValues bpv15 = new BetPassValuesTest15();
        BetPassActionValues bpv16 = new BetPassValuesTest16();
        Harness harness = new Harness(bpv15, bpv16);
        harness.playOutHands();
    }
}
