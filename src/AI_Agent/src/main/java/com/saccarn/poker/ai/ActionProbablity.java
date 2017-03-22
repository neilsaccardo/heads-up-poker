package com.saccarn.poker.ai;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Neil on 22/03/2017.
 */
public class ActionProbablity {

    private EWUtility ewu;
    private PotPredictor pp;
    private int round;
    private static final int ALPHA = 1;
    private Map<Integer, Double> fpParams;
    private Map<Integer, Double> cpParams;

    public ActionProbablity(PotPredictor p) {
        pp = p;
        fillFPParams();
        fillCPParams();
        ewu = new EWUtility(pp);
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


    public double getFoldProbablity() {
        double ew = (ewu.getUtilityPass() - ewu.getUtilityFold())/((double)ewu.getBetSize());
        double result = ActionProbablity.ALPHA / (ActionProbablity.ALPHA + (fpParams.get(round) * Math.exp(ew)));
        return result;
    }

    public double getPassProbablity() {
        double ew = (ewu.getUtilityBet() - ewu.getUtilityPass())/((double)ewu.getBetSize());
        double result = ActionProbablity.ALPHA / (ActionProbablity.ALPHA + (cpParams.get(round)* Math.exp(ew)));
        return result;
    }


    public static void main(String [] args) {
        PotPredictor po = new PotPredictor(); //1, 75, 75, 2, 0, 30
        po.calculatePotAndFutureContribution(1, 75, 75, 30, 0, 0);
        ActionProbablity ap = new ActionProbablity(po);
        System.out.println(ap.getPassProbablity());
        System.out.println(ap.getFoldProbablity());
    }
}
