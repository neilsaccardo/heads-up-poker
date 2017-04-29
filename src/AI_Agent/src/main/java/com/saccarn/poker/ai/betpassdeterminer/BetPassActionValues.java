package com.saccarn.poker.ai.betpassdeterminer;

/**
 * This class contains values for betting curve- extend this class and it can be supplied when creating AI_Agent object.
 * Created by Neil on 12/04/2017.
 */
public class BetPassActionValues {

//    public static final double PASS_CONST = 0.1;
//    public static final double BET1_CONST = 0.0;
//    public static final double BET2_CONST = 0.6;
//    public static final double BET3_CONST = 0.93;
//    public static final double ALLIN_CONST = 0.97;

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
