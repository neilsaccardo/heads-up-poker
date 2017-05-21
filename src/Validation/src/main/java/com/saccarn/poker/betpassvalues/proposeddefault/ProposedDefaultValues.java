package com.saccarn.poker.betpassvalues.proposeddefault;

import com.saccarn.poker.ai.betpassdeterminer.BetPassActionValues;

/**
 * Created by Neil on 26/04/2017.
 */
public class ProposedDefaultValues extends BetPassActionValues {


    /* Default */
    public double getPassConst() {
        return 0.1;
    }

    public double getBet1Const() {
        return 0.0;
    }

    /* BetPassValuesTest8 */
    public double getBet2Const() {
        return 0.65;
    }

    /* Default*/
    public double getBet3Const() {
        return 0.93;
    }

    /* BetPassValues16 */
    public double getAllinConst() {
        return 0.99;
    }

}
