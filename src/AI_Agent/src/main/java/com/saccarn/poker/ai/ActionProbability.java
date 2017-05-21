package com.saccarn.poker.ai;

import java.util.HashMap;
import java.util.Map;

/**
 *  This class makes use of the Estimated Winnings utility to implement the betting curves for fold/(check/call) and (check/call)/bet
 *  Created by Neil on 22/03/2017.
 */
public class ActionProbability {

    private EWUtility ewu;
    private PotPredictor pp;
    private int round;
    private static final int ALPHA = 1;
    private Map<Integer, Double> fpParams;
    private Map<Integer, Double> cpParams;

    /**
     *  Constructor with only pot predictor object.
     *
     *  Belief will be set exactly 0.5 if none supplied
     * @param p pot predictor object used for estimated winnings utility.
     */

    public ActionProbability(PotPredictor p) {
        pp = p;
        fillFPParams();
        fillCPParams();
        ewu = new EWUtility(pp);
        round = pp.getRound();
    }

    /**
     *  Full args constructor with pot predictor class and belief value
     * @param p pot predictor object used for estimated winnings utility.
     * @param belief represents the belief in winning at showdown
     */

    public ActionProbability(PotPredictor p, double belief) {
        pp = p;
        fillFPParams();
        fillCPParams();
        ewu = new EWUtility(pp, belief);
        round = pp.getRound();
    }


    // These values were obtained from Carltons work.
    private void fillCPParams() {
        cpParams = new HashMap<>();
        cpParams.put(0, 1.150054);
        cpParams.put(1, 2.630548);
        cpParams.put(2, 3.099426);
    }

    // These values were obtained from Carltons work.
    private void fillFPParams() {
        fpParams = new HashMap<>();
        fpParams.put(0, 1.675825);
        fpParams.put(1, 1.675825);
        fpParams.put(2, 1.765005);
    }

    /**
     * Returns value of fold/check/call function given the estimated winnings for fold and check/call action pair.
     *
     * This method implements the fold/check/call function.
     * @return value for fold/check/call function
     */

    public double getFoldProbability() {
        double ew = (ewu.getUtilityPass() - ewu.getUtilityFold())/((double)ewu.getBetSize());
        System.out.println("The FOLD EW probability is :" + ew);
        double result = ActionProbability.ALPHA / (ActionProbability.ALPHA + ( Math.exp(fpParams.get(round) * ew)));
        System.out.println("Result is " + result);
        return result;
    }

    /**
     * Returns value of (check/call)/bet function given the estimated winnings for check/call and bet action pair.
     *
     * This method implements the (check/call/bet) function.
     * @return value for (check/call/bet) function
     */

    public double getPassProbability() {
        double ew = (ewu.getUtilityBet() - ewu.getUtilityPass())/((double)ewu.getBetSize());
        System.out.println("The PASS EW probability is :" + ew);
        double result = ActionProbability.ALPHA / (ActionProbability.ALPHA + ( Math.exp(ew * cpParams.get(round))));
        System.out.println("Result is " + result);
        return result;
    }
}
