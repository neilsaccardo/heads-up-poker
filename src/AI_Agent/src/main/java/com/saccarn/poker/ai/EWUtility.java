package com.saccarn.poker.ai;

/**
 * The Estimated Winnings Utility.
 *
 * Created by Neil on 19/03/2017.
 */
public class EWUtility {

    private PotPredictor potPredictor = new PotPredictor();
    private double totalOdds = 1.0; //could change to percentage easily.
    private double beliefInWinning = 0.5;
    private double beliefInLosing = 0.5;

    /**
     * Constructor with no belief arg - belief will be set to 0.5
     * @param po pot predictor object
     */
    public EWUtility(PotPredictor po) {
        potPredictor = po;
        beliefInLosing = totalOdds - beliefInWinning;
    }


    /**
     * Constructor with belief argument. Belief will affect the out come of the estimated winnings per action.
     * @param po pot predictor object
     * @param beliefInWinning0 the belief in winning at show down
     */
    public EWUtility(PotPredictor po, double beliefInWinning0) {
        beliefInWinning = beliefInWinning0;
        potPredictor = po;
        beliefInLosing = totalOdds - beliefInWinning;
        System.out.println("THE BELIEF IN WINNING IS: " + beliefInWinning);
        System.out.println("THE BELIEF IN LOSING IS: " + beliefInLosing);
    }

    /**
     * Returns the combined estimated winnings when the action is BET
     *
     * @return estimated winnings when action is bet.
     */

    public int getUtilityBet() {
        return (int) Math.round(((getUtilityBetWin() * beliefInWinning) )
                + (getUtilityBetLose() * beliefInLosing));
    }


    /**
     * Returns estimated winnings when the action is bet and the outcome is WIN
     *
     * @return estimated winnings when action is bet and outcome is win.
     */
    public int getUtilityBetWin() {
        return (potPredictor.getFinalPotBet() - potPredictor.getFutureContributionBet());
    }

    /**
     * Returns estimated winnings when the action is bet and the outcome is LOSE
     *
     * @return estimated winnings when action is bet and outcome is lose.
     */
    public int getUtilityBetLose() {
        return -potPredictor.getFutureContributionBet();
    }

    /**
     * Returns estimated winnings when the action is check / call
     *
     * @return estimated winnings when action is check/call.
     */
    public int getUtilityPass() { //check or call
        return (int) Math.round((getUtilityPassWin()* beliefInWinning)
                    + (getUtilityPassLose() * beliefInLosing));
    }
    /**
     * Returns estimated winnings when the action is check / call and the outcome is WIN
     *
     * @return estimated winnings when action is check / call and outcome is win.
     */
    public int getUtilityPassWin() { //check or call
        return potPredictor.getFinalPotPass() - potPredictor.getFutureContributionPass();
    }

    /**
     * Returns estimated winnings when the action is check / call and the outcome is LOSE
     *
     * @return estimated winnings when action is check / call and outcome is lose.
     */
    public int getUtilityPassLose() { //check or call
        return - (potPredictor.getFutureContributionPass());
    }

    /**
     * Returns estimated winnings when the action is FOLD
     *
     * @return estimated winnings when action is fold
     */
    public int getUtilityFold() {
        return getFoldUtilityLose() + getFoldUtilityWin();
    }

    /**
     * Returns estimated winnings when the action is FOLD and the outcome is WIN
     *
     * @return estimated winnings when action is fold and the out come is win
     */
    public int getFoldUtilityWin() {
        return 0;
    }

    /**
     * Returns estimated winnings when the action is FOLD and the outcome is LOSE
     *
     * @return estimated winnings when action is fold and the out come is lose
     */
    public int getFoldUtilityLose() {
        return 0;
    }

    public int getBetSize() {
        return potPredictor.getBetSize();
    }

}
