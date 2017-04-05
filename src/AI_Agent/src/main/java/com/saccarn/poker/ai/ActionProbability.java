package com.saccarn.poker.ai;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Neil on 22/03/2017.
 */
public class ActionProbability {

    private EWUtility ewu;
    private PotPredictor pp;
    private int round;
    private static final int ALPHA = 1;
    private Map<Integer, Double> fpParams;
    private Map<Integer, Double> cpParams;

    public ActionProbability(PotPredictor p) {
        pp = p;
        fillFPParams();
        fillCPParams();
        ewu = new EWUtility(pp);
        round = pp.getRound();
    }

    public ActionProbability(PotPredictor p, double belief) {
        pp = p;
        fillFPParams();
        fillCPParams();
        ewu = new EWUtility(pp, belief);
        round = pp.getRound();
    }

    private void fillCPParams() {
        cpParams = new HashMap<>();
        cpParams.put(0, 1.150054);
        cpParams.put(1, 2.630548);
        cpParams.put(2, 3.099426);
    }

    private void fillFPParams() {
        fpParams = new HashMap<>();
        fpParams.put(0, 1.675825);
        fpParams.put(1, 1.675825);
        fpParams.put(2, 1.765005);
    }

    public double getFoldProbability() {
        double ew = (ewu.getUtilityPass() - ewu.getUtilityFold())/((double)ewu.getBetSize());
        double result = ActionProbability.ALPHA / (ActionProbability.ALPHA + (fpParams.get(round) * Math.exp(ew)));
        return result;
    }

    public double getPassProbability() {
        double ew = (ewu.getUtilityBet() - ewu.getUtilityPass())/((double)ewu.getBetSize());
        double result = ActionProbability.ALPHA / (ActionProbability.ALPHA + (cpParams.get(round)* Math.exp(ew)));
        return result;
    }
}
