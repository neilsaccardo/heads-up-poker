package com.saccarn.poker.testscommonhandvalues;

import com.saccarn.poker.commonhandbools.CommonHandValues;
import com.saccarn.poker.harnass.Harness;

/**
 * Created by Neil on 28/04/2017.
 */
public class TestCommonHandTrueVsCommonHandFalse {

    public static void main(String [] args) {
        // Ai Agent with commonhand class influencing belief vs Ai agent with no commonhand class influencing belief.
        Harness harness = new Harness(CommonHandValues.COMMON_HAND_TRUE, CommonHandValues.COMMON_HAND_FALSE);
        harness.playOutHands();
    }

}
