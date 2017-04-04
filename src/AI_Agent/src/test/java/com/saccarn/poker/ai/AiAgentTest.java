package com.saccarn.poker.ai;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Neil on 04/04/2017.
 */
public class AiAgentTest {

    private AiAgent agent;

    @Before
    public void setUp() {
        agent = new AiAgent();
    }

    @Test
    public void testGetCorrectOutputActionPreFlopCall() {
        // String action, int position, String stageOfPlay, String previousAction
        String action = agent.getCorrectOutputAction(ActionStrings.ACTION_PASS, 0, AiAgent.PRE_FLOP, ActionStrings.ACTION_CALL);
        Assert.assertEquals("Action should be check when previous action is call on the preflop", ActionStrings.ACTION_CHECK, action);
    }


    @Test
    public void testGetCorrectOutputActionPreFlopRaise() {
        // String action, int position, String stageOfPlay, String previousAction
        //Check pass
        String action = agent.getCorrectOutputAction(ActionStrings.ACTION_PASS, 0, AiAgent.PRE_FLOP, ActionStrings.ACTION_RAISE1);
        Assert.assertEquals("Action should be check when previous action is raise on the preflop", ActionStrings.ACTION_CALL, action);
        //Check bet1
        action = agent.getCorrectOutputAction(ActionStrings.ACTION_BET1, 0, AiAgent.PRE_FLOP, ActionStrings.ACTION_RAISE1);
        Assert.assertEquals("Action should be a raise when previous action is raise on the preflop", ActionStrings.ACTION_RAISE1, action);
        //Check bet2
        action = agent.getCorrectOutputAction(ActionStrings.ACTION_BET2, 0, AiAgent.PRE_FLOP, ActionStrings.ACTION_RAISE1);
        Assert.assertEquals("Action should be a raise when previous action is raise on the preflop", ActionStrings.ACTION_RAISE2, action);
        //Check bet3
        action = agent.getCorrectOutputAction(ActionStrings.ACTION_BET3, 0, AiAgent.PRE_FLOP, ActionStrings.ACTION_RAISE1);
        Assert.assertEquals("Action should be a raise when previous action is raise on the preflop", ActionStrings.ACTION_RAISE3, action);
    }

    @Test
    public void testGetCorrectOutputActionFlopPlayerHasBet() {
        String action = agent.getCorrectOutputAction(ActionStrings.ACTION_BET1, 0, AiAgent.FLOP, ActionStrings.ACTION_RAISE1);
        Assert.assertEquals("Flop : Action should be raise as player BET  and AI wants to BET.", ActionStrings.ACTION_RAISE1, action );

        action =  agent.getCorrectOutputAction(ActionStrings.ACTION_PASS, 0, AiAgent.FLOP, ActionStrings.ACTION_BET1);
        Assert.assertEquals("Flop : Action should be call as player has BET and AI wants to PASS .", ActionStrings.ACTION_CALL, action);
    }

    @Test
    public void testGetCorrectOutputActionFlopPlayerHasChecked() {
        String action = agent.getCorrectOutputAction(ActionStrings.ACTION_PASS, 0, AiAgent.FLOP, ActionStrings.ACTION_CHECK);
        Assert.assertEquals("Flop : Action should be CHECK as player CHECK  and AI wants to PASS.", ActionStrings.ACTION_CHECK, action);

        action = agent.getCorrectOutputAction(ActionStrings.ACTION_BET1, 0, AiAgent.FLOP, ActionStrings.ACTION_CHECK);
        Assert.assertEquals("Flop : Action should be call as player has CHECKED and AI wants to BET .", ActionStrings.ACTION_BET1, action);
    }

}
