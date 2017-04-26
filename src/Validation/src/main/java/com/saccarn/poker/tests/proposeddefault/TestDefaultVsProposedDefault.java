package com.saccarn.poker.tests.proposeddefault;

import com.saccarn.poker.ai.betpassdeterminer.BetPassActionValues;
import com.saccarn.poker.betpassvalues.BetPassValuesTest14;
import com.saccarn.poker.betpassvalues.DefaultValues;
import com.saccarn.poker.betpassvalues.proposeddefault.ProposedDefaultValues;
import com.saccarn.poker.harnass.Harness;

/**
 * Created by Neil on 26/04/2017.
 */
public class TestDefaultVsProposedDefault {

    public static void main(String [] args) {
        BetPassActionValues defaultValues = new DefaultValues();
        BetPassActionValues proposedDefaultValues = new ProposedDefaultValues();
        Harness harness = new Harness(defaultValues, proposedDefaultValues);
        harness.playOutHands();
    }
}
