package com.saccarn.poker.ai;

/**
 * Created by Neil on 19/03/2017.
 */
public class EWUtility {

    private PotOdds potOdds = new PotOdds();
    private double totalOdds = 1.0; //could change to percentage easily.
    private double beliefInWinning;

    public EWUtility(PotOdds po, double belief) {
        potOdds = po;
        beliefInWinning = belief;
    }

    public int getUtilityBet() {
        return (int) Math.round(((getUtilityBetWin()) * beliefInWinning)
                + (getUtilityBetLose() * (totalOdds-beliefInWinning)));
    }

    public int getUtilityBetWin() {
        return (potOdds.getFinalPotBet() - potOdds.getFutureContributionBet());
    }

    public int getUtilityBetLose() {
        return -potOdds.getFutureContributionBet();
    }

    public int getUtilityPass() { //check or call
        return (int) Math.round((getUtilityPassWin())* beliefInWinning
                    + (getUtilityPassLose() * (totalOdds - beliefInWinning)));
    }

    public int getUtilityPassWin() { //check or call
        return potOdds.getFinalPotPass() - potOdds.getFutureContributionPass();
    }

    public int getUtilityPassLose() { //check or call
        return - (potOdds.getFutureContributionPass());
    }

    public int getFoldUtility() {
        return getFoldUtilityLose() + getFoldUtilityWin();
    }

    public int getFoldUtilityWin() {
        return 0;
    }

    public int getFoldUtilityLose() {
        return 0;
    }
}
