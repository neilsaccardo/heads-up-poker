package com.saccarn.poker.ai.preflop;

import com.saccarn.poker.ai.ActionStrings;
import org.junit.Assert;
import org.junit.Test;

import javax.swing.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Neil on 19/04/2017.
 */
public class PreFlopDeterminerTest {

    private String [] possibleActions = {ActionStrings.ACTION_FOLD, ActionStrings.ACTION_PASS, ActionStrings.ACTION_BET1,
                                        ActionStrings.ACTION_BET2, ActionStrings.ACTION_BET3};
    private List<String> possibleActionList = Arrays.asList(possibleActions);

    /* Test 1 2 3 are essentially same test just switching up card hand rankings*/
    @Test
    public void testPreFlopActionOutput1() {
        PreFlopDeterminer pfd = new PreFlopDeterminer();
        String action = pfd.preFlopAction("As", "4d", 200, 200 , 200, 200,new HashMap<String, Double>(), 50, 200);
        boolean isActionValidAction = possibleActionList.contains(action);
        Assert.assertEquals("Pre Flop determiner should only output certain subset of actions: FOLD, PASS, BET1, BET2, BET3", true, isActionValidAction);
    }

    @Test
    public void testPreFlopActionOutput2() {
        PreFlopDeterminer pfd = new PreFlopDeterminer();
        String action = pfd.preFlopAction("2s", "3d", 200, 200 , 200, 200,new HashMap<String, Double>(), 50, 200);
        boolean isActionValidAction = possibleActionList.contains(action);
        Assert.assertEquals("Pre Flop determiner should only output certain subset of actions: FOLD, PASS, BET1, BET2, BET3", true, isActionValidAction);
    }

    @Test
    public void testPreFlopActionOutput3() {
        PreFlopDeterminer pfd = new PreFlopDeterminer();
        String action = pfd.preFlopAction("2s", "9h", 200, 200 , 200, 200,new HashMap<String, Double>(), 50, 200);
        boolean isActionValidAction = possibleActionList.contains(action);
        Assert.assertEquals("Pre Flop determiner should only output certain subset of actions: FOLD, PASS, BET1, BET2, BET3", true, isActionValidAction);
    }

    @Test
    public void testActionOutputFold() {
        PreFlopDeterminer pfd = new PreFlopDeterminer();
        String action = pfd.action(80, 91);
        System.out.println(action);
        Assert.assertEquals("When the hand ranking and random num are above a given threshold, then the Preflop should decide to FOLD", ActionStrings.ACTION_FOLD, action);
    }
}
