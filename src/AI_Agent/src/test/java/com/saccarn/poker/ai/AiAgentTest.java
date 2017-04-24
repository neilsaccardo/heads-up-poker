package com.saccarn.poker.ai;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Neil on 04/04/2017.
 */
public class AiAgentTest {

    private AiAgent agent;
    private String [] validActions = {  ActionStrings.ACTION_FOLD, ActionStrings.ACTION_BET2, ActionStrings.ACTION_BET1,
                                        ActionStrings.ACTION_BET3,
                                        ActionStrings.ACTION_RAISE1, ActionStrings.ACTION_RAISE2, ActionStrings.ACTION_RAISE3,
                                        ActionStrings.ACTION_ALLIN,
                                        ActionStrings.ACTION_CHECK, ActionStrings.ACTION_CALL};
    private List<String> validActionList = Arrays.asList(validActions);

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

    @Test
    public void testGetCorrectOutputActionFlopPlayerHasRaised() {
        String action = agent.getCorrectOutputAction(ActionStrings.ACTION_PASS, 0, AiAgent.FLOP, ActionStrings.ACTION_RAISE1);
        Assert.assertEquals("Flop : Action should be CALL as player RAISE  and AI wants to PASS.", ActionStrings.ACTION_CALL, action);

        action = agent.getCorrectOutputAction(ActionStrings.ACTION_BET1, 0, AiAgent.FLOP, ActionStrings.ACTION_RAISE1);
        Assert.assertEquals("Flop : Action should be RAISE as player has RAISE and AI wants to BET .", ActionStrings.ACTION_RAISE1, action);
    }


    @Test
    public void testGetCorrectOutputActionFlopPlayerHasCalled() {
        String action = agent.getCorrectOutputAction(ActionStrings.ACTION_PASS, 0, AiAgent.FLOP, ActionStrings.ACTION_CALL);
        Assert.assertEquals("Flop : Action should be CHECK as player CALLED  and AI wants to PASS.", ActionStrings.ACTION_CHECK, action);

        action = agent.getCorrectOutputAction(ActionStrings.ACTION_BET1, 0, AiAgent.FLOP, ActionStrings.ACTION_CALL);
        Assert.assertEquals("Flop : Action should be BET as player has CALLED and AI wants to BET .", ActionStrings.ACTION_BET1, action);
    }

    @Test
    public void testNoUnNecessaryFoldsPreFlopPlayerCheck() {
        String action = agent.noUnNecessaryFolds(ActionStrings.ACTION_FOLD, 0, AiAgent.PRE_FLOP, ActionStrings.ACTION_CHECK);
        Assert.assertEquals("Preflop: Action should not be fold as player has just checked before them. The AI should Check",
                ActionStrings.ACTION_CHECK, action);
    }

    @Test
    public void testNoUnNecessaryFoldsFlopPlayerCheck() {
        String action = agent.noUnNecessaryFolds(ActionStrings.ACTION_FOLD, 0, AiAgent.FLOP, ActionStrings.ACTION_CHECK);
        Assert.assertEquals("Flop: Action should not be fold as player has just checked before them. The AI should Check",
                ActionStrings.ACTION_CHECK, action);
    }

    @Test
    public void testNoUnNecessaryFoldsTurnPlayerCheck() {
        String action = agent.noUnNecessaryFolds(ActionStrings.ACTION_FOLD, 0, AiAgent.FLOP, ActionStrings.ACTION_CHECK);
        Assert.assertEquals("Turn: Action should not be fold as player has just checked before them. The AI should Check",
                ActionStrings.ACTION_CHECK, action);
    }

    @Test
    public void testNoUnNecessaryFoldsRiverPlayerCheck() {
        String action = agent.noUnNecessaryFolds(ActionStrings.ACTION_FOLD, 0, AiAgent.FLOP, ActionStrings.ACTION_CHECK);
        Assert.assertEquals("River: Action should not be fold as player has just checked before them. The AI should Check",
                ActionStrings.ACTION_CHECK, action);
    }

    @Test
    public void testNoUnNecessaryFoldsDoesNotAffectNonFoldActions() {
        String action = agent.noUnNecessaryFolds(ActionStrings.ACTION_CALL, 0, AiAgent.FLOP, ActionStrings.ACTION_CHECK);
        Assert.assertEquals("Action should not be affected as the action is not FOLD",
                ActionStrings.ACTION_CALL, action);
        action = agent.noUnNecessaryFolds(ActionStrings.ACTION_CALL, 0, AiAgent.FLOP, ActionStrings.ACTION_CHECK);
        Assert.assertEquals("CALL Action should not be affected as the action is not FOLD",
                ActionStrings.ACTION_CALL, action);
        action = agent.noUnNecessaryFolds(ActionStrings.ACTION_BET1, 0, AiAgent.FLOP, ActionStrings.ACTION_CHECK);
        Assert.assertEquals("BET Action should not be affected as the action is not FOLD",
                ActionStrings.ACTION_BET1, action);
        action = agent.noUnNecessaryFolds(ActionStrings.ACTION_RAISE2, 0, AiAgent.FLOP, ActionStrings.ACTION_CHECK);
        Assert.assertEquals("RAISE Action should not be affected as the action is not FOLD",
                ActionStrings.ACTION_RAISE2, action);
        action = agent.noUnNecessaryFolds(ActionStrings.ACTION_CHECK, 0, AiAgent.FLOP, ActionStrings.ACTION_CHECK);
        Assert.assertEquals("CHECK Action should not be affected as the action is not FOLD",
                ActionStrings.ACTION_CHECK, action);
    }


    @Test
    public void testAiAgentGetActionFlop() {
        String [] bcs3 = {"2c", "4d", "Ad"};

        String action = agent.getAction(AiAgent.FLOP, "As", "5d", bcs3, 2000, 400, 0, 0,100, 0, 2000, ActionStrings.ACTION_CALL);
        Assert.assertEquals("AI Agent should output a valid action from the valid action list:" + validActionList,
                true, validActionList.contains(action));
    }

    @Test
    public void testAiAgentGetActionTurn() {
        String [] bcs4 = {"2c", "4d", "Ad", "3s"};

        String action = agent.getAction(AiAgent.TURN, "As", "5d", bcs4, 2000, 400, 0, 0,100, 0, 2000, ActionStrings.ACTION_CALL);
        Assert.assertEquals("AI Agent should output a valid action from the valid action list:" + validActionList,
                true, validActionList.contains(action));
    }

    @Test
    public void testAiAgentGetActionRiver() {
        String [] bcs5 = {"2c", "4d", "Ad", "3s", "7d"};

        String action = agent.getAction(AiAgent.RIVER, "As", "5d", bcs5, 2000, 400, 0, 0,100, 0, 2000, ActionStrings.ACTION_CALL);
        Assert.assertEquals("AI Agent should output a valid action from the valid action list:" + validActionList,
                true, validActionList.contains(action));
    }


    @Test
    public void testAiAgentGetActionPreFlop() {
        String [] bcs0 = {};
        String action = agent.getAction(AiAgent.PRE_FLOP, "As", "Ac", bcs0, 2000, 400, 0, 0,100, 0, 2000, ActionStrings.ACTION_CALL);
        Assert.assertEquals("AI Agent should output a valid action from the valid action list:" + validActionList,
                true, validActionList.contains(action));
    }

}
