package com.saccarn.poker.testshandpotential;

import com.saccarn.poker.commonhandbools.CommonHandValues;
import com.saccarn.poker.handpotentialflushstraightvalues.HandPotentialValues;
import com.saccarn.poker.harnass.Harness;

/**
 * Created by Neil on 28/04/2017.
 */
public class TestHandPotentialTrueVsHandPotentialFalse {


    public static void main(String [] args) {

        //int added to constructor to differentiate between common hand values and this, as both take booleans.
        int potential = 0;
        // Ai Agent with hand potential class influencing belief vs Ai agent with no hand potential class influencing belief.
        Harness harness = new Harness(HandPotentialValues.HAND_POTENTIAL_TRUE, HandPotentialValues.HAND_POTENTIAL_FALSE, potential);
        harness.playOutHands();
    }

}
