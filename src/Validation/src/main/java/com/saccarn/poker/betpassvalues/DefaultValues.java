package com.saccarn.poker.betpassvalues;

import com.saccarn.poker.ai.betpassdeterminer.BetPassActionValues;

/**
 * Created by Neil on 26/04/2017.
 */
public class DefaultValues extends BetPassActionValues {

    /* Contains the initial default values used */

    public double getPassConst() {
        return 0.1;
    }

    public double getBet1Const() {
        return 0.0;
    }

    public double getBet2Const() {
        return 0.6;
    }

    public double getBet3Const() {
        return 0.93;
    }

    public double getAllinConst() {
        return 0.97;
    }
}
