package com.saccarn.poker.ai;

import com.saccarn.poker.ai.betpassdeterminer.BetPassActionValues;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

/**
 * Created by Neil on 23/03/2017.
 */
@Ignore
public class ActionDeterminerTest {

    //holeCard1, holeCard2, boardCards, stackSize, opponentStackSize, potSize, amountBet, minBet, playerCluster, round
    private ActionDeterminer ad;
    private BetPassActionValues bpv = new BetPassActionValues();
    private boolean checkCommonCards = true;
    private boolean affectPotential = true;

    @Before
    public void setUp() {
        String [] bcs = {"2c", "4d", "10s"};
        ad = new ActionDeterminer("As", "Ac", bcs, 2, 2, 20, 15, 3, new HashMap<String, Double>(), 0, bpv, checkCommonCards, affectPotential);
    }

    @Test
    public void testActionDeterminerDetermineShouldFold() {
        boolean shouldFold = ad.determineShouldFold(0.5, 0.6);
        Assert.assertEquals("Should determine to fold as passProbability is < random value(0.6)", true, shouldFold);
    }

    @Test
    public void testActionDeterminerDetermineShouldNotFold() {
        boolean shouldFold = ad.determineShouldFold(0.2, 0.1);
        Assert.assertEquals("Should determine action not fold (pass) as passProbability is > random value(0.1)",false, shouldFold);
    }

    @Test
    public void testActionDeterminerDeterminePass() {
        String action = ad.determinePassOrBetAction(0.5, 0.4);
        Assert.assertEquals("Should determine action PASS - random number is > probability than  (0.5)", ActionStrings.ACTION_PASS, action);
    }


    @Test
    public void testActionDeterminerDetermineBet1() {
        String action = ad.determinePassOrBetAction(0.3, 0.5);
        Assert.assertEquals("Should detemine action BET1 - random number(0.5) > passBetProbability and random number < passBetProbability * BET1_CONST(2)", ActionStrings.ACTION_BET1, action);
    }

    @Test
    public void testActionDeterminerDetermineBet2() {
        String action = ad.determinePassOrBetAction(0.25, 0.6);
        Assert.assertEquals("Should determine action BET1 - random number(0.6) > passBetProbability and random number < passBetProbability * BET2_CONST(3)", ActionStrings.ACTION_BET2, action);
    }

    @Test
    public void testActionDeterminerDetermineBet3() {
        String action = ad.determinePassOrBetAction(0.2, 0.61);
        Assert.assertEquals("Should determine action BET1 - random number(0.61) > passBetProbability and random number < passBetProbability * BET3_CONST(4)", ActionStrings.ACTION_BET3, action);
    }

    @Test
    public void testActionDeterminerDetermineBet1EdgeCase() {
        String action = ad.determinePassOrBetAction(0.2, 0.9);
        Assert.assertEquals("Should determine action BET1 - random number(0.61) > passBetProbability and random number < passBetProbability * BET3_CONST(4)", ActionStrings.ACTION_BET1, action);
    }



    @Test
    public void testActionDeterminerDetermineAllIn() {
        String action = ad.determinePassOrBetAction(0.2, 1.0);
        Assert.assertEquals("Should determine all in - when random number is 1.0 and random > passBetProbability*BET3_CONST", ActionStrings.ACTION_ALLIN, action);

        action = ad.determinePassOrBetAction(0.25, 1.0);
        Assert.assertEquals("Should determine all in - when random number is 1.0 and random > passBetProbability*BET3_CONST", ActionStrings.ACTION_ALLIN, action);

        action = ad.determinePassOrBetAction(0.26, 1.0);
        Assert.assertNotEquals("Should NOT determine all in - when random number is 1.0and random < passBetProbability*BET3_CONST", ActionStrings.ACTION_ALLIN, action);
    }

    @Test
    public void testActionDeterminerGetActionAlwaysReturnsAValidActionFlop() {
        String [] bcs3 = {"2c", "4d", "Ad"};
        Map<String, Double> playerCluster = PlayerCluster.getPlayerInfo(0);
        ad = new ActionDeterminer("As", "Ac", bcs3, 2, 2, 20, 15, 3, playerCluster, 0, bpv, !checkCommonCards, affectPotential);
        String [] validActions = {ActionStrings.ACTION_FOLD, ActionStrings.ACTION_PASS, ActionStrings.ACTION_BET2, ActionStrings.ACTION_BET1, ActionStrings.ACTION_BET3, ActionStrings.ACTION_ALLIN};
        List<String> listOfValidActions = Arrays.asList(validActions);
        int totalTimes = 15;
        int i = 0;
        String action = ad.getAction();
        while(i < totalTimes && listOfValidActions.contains(action)) {
            i++;
            ad = new ActionDeterminer("As", "Ac", bcs3, 2, 2, 20, 15, 3, playerCluster, 0, bpv, checkCommonCards, affectPotential); // i% 3 to go through flop, turn, river
            action = ad.getAction();
        }
        Assert.assertEquals("Flop: Action Determiner should always return a valid action string", totalTimes, i );
    }


    @Test
    public void testActionDeterminerGetActionAlwaysReturnsAValidActionTurn() {
        String [] bcs4 = {"2c", "4d", "Ad", "3s"};
        Map<String, Double> playerCluster = PlayerCluster.getPlayerInfo(0);
        ad = new ActionDeterminer("As", "Ac", bcs4, 2, 2, 20, 15, 3, playerCluster, 0, bpv, checkCommonCards, affectPotential);
        String [] validActions = {ActionStrings.ACTION_FOLD, ActionStrings.ACTION_PASS, ActionStrings.ACTION_BET2, ActionStrings.ACTION_BET1, ActionStrings.ACTION_BET3, ActionStrings.ACTION_ALLIN};
        List<String> listOfValidActions = Arrays.asList(validActions);
        int totalTimes = 15;
        int i = 0;
        String action = ad.getAction();
        while(i < totalTimes && listOfValidActions.contains(action)) {
            i++;
            ad = new ActionDeterminer("As", "Ac", bcs4, 2, 2, 20, 15, 3, playerCluster, 1, bpv, checkCommonCards, affectPotential);
            action = ad.getAction();
        }
        Assert.assertEquals("Turn: Action Determiner should always return a valid action string", totalTimes, i );
    }

    @Test
    public void testActionDeterminerGetActionAlwaysReturnsAValidActionTurnRiver() {
        String [] bcs5 = {"2c", "4d", "Ad", "3s", "7d"};
        Map<String, Double> playerCluster = PlayerCluster.getPlayerInfo(0);
        ad = new ActionDeterminer("As", "Ac", bcs5, 2, 2, 20, 15, 3, playerCluster, 0, bpv, checkCommonCards, affectPotential);
        String [] validActions = {ActionStrings.ACTION_FOLD, ActionStrings.ACTION_PASS, ActionStrings.ACTION_BET2, ActionStrings.ACTION_BET1, ActionStrings.ACTION_BET3, ActionStrings.ACTION_ALLIN};
        List<String> listOfValidActions = Arrays.asList(validActions);
        int totalTimes = 15;
        int i = 0;
        String action = ad.getAction();
        while(i < totalTimes && listOfValidActions.contains(action)) {
            i++;
            ad = new ActionDeterminer("As", "Ac", bcs5, 2, 2, 20, 15, 3, playerCluster, 2, bpv, checkCommonCards, affectPotential);
            action = ad.getAction();
        }
        Assert.assertEquals("River: Action Determiner should always return a valid action string", totalTimes, i );
    }

}
