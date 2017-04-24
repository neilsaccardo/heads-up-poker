package com.saccarn.poker.ai;

import org.junit.Test;

/**
 * Created by Neil on 05/04/2017.
 */
public class EWUtilityTest {


    @Test
    public void testEWUtility() {
        //int minBet, int stackSize, int opponentStackSize, int potSize, int round0, int numChipsBet
        PotPredictor pp = new PotPredictor(100, 5000, 5000, 200, 0, 0);
        EWUtility ewu = new EWUtility(pp,  0.05);
        double betSize = ewu.getBetSize();
//        System.out.println(betSize);
//        System.out.println(ewu.getUtilityBet());
//        System.out.println(ewu.getUtilityPass());

        double ewFold = (ewu.getUtilityPass() - ewu.getUtilityFold())/((double)ewu.getBetSize());

        double ewBet = (ewu.getUtilityBet() - ewu.getUtilityPass())/((double)ewu.getBetSize());

        System.out.println("ewFold: " + ewFold);
        System.out.println("ewBet: " + ewBet);

        ActionProbability ap = new ActionProbability(pp, 0.05);
        double foldProb = ap.getFoldProbability();
        double passProb = ap.getPassProbability();
    }
}
