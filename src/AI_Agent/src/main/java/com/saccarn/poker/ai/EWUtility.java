package com.saccarn.poker.ai;

/**
 * Created by Neil on 19/03/2017.
 */
public class EWUtility {

    private PotPredictor potPredictor = new PotPredictor();
    private double totalOdds = 1.0; //could change to percentage easily.
    private double beliefInWinning = 0.5;
    private double beliefInLosing = 0.5;

    public EWUtility(PotPredictor po) {
        potPredictor = po;
        beliefInLosing = totalOdds - beliefInWinning;
    }

    public EWUtility(PotPredictor po, double beliefInWinning0) {
        beliefInWinning = beliefInWinning0;
        potPredictor = po;
        beliefInLosing = totalOdds - beliefInWinning;
        System.out.println("THE BELIEF IN WINNING IS: " + beliefInWinning);
        System.out.println("THE BELIEF IN LOSING IS: " + beliefInLosing);
    }

    public int getUtilityBet() {
        return (int) Math.round(((getUtilityBetWin() * beliefInWinning) )
                + (getUtilityBetLose() * beliefInLosing));
    }

    public int getUtilityBetWin() {
        return (potPredictor.getFinalPotBet() - potPredictor.getFutureContributionBet());
    }

    public int getUtilityBetLose() {
        return -potPredictor.getFutureContributionBet();
    }

    public int getUtilityPass() { //check or call
        return (int) Math.round((getUtilityPassWin()* beliefInWinning)
                    + (getUtilityPassLose() * beliefInLosing));
    }

    public int getUtilityPassWin() { //check or call
        return potPredictor.getFinalPotPass() - potPredictor.getFutureContributionPass();
    }

    public int getUtilityPassLose() { //check or call
        return - (potPredictor.getFutureContributionPass());
    }

    public int getUtilityFold() {
        return getFoldUtilityLose() + getFoldUtilityWin();
    }

    public int getFoldUtilityWin() {
        return 0;
    }

    public int getFoldUtilityLose() {
        return 0;
    }

    public int getBetSize() {
        return potPredictor.getBetSize();
    }

}
