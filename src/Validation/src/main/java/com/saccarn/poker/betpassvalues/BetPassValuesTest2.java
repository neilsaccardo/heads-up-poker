package com.saccarn.poker.betpassvalues;

import com.saccarn.poker.ai.betpassdeterminer.BetPassActionValues;

/**
 * Created by Neil on 25/04/2017.
 */
public class BetPassValuesTest2 extends BetPassActionValues {


    public double getPassConst() {
        return 0.1;
    }

    public double getBet1Const() {
        return 0.0;
    }

    public double getBet2Const() {
        return 0.8;
    }

    public double getBet3Const() {
        return 0.7;
    }

    public double getAllinConst() {
        return 0.9;
    }
}
