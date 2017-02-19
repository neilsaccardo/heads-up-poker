package com.saccarn.poker.dataprocessing.unit;

import com.saccarn.poker.dataprocessing.GamePlayerRecord;
import com.saccarn.poker.dataprocessing.Player;
import com.saccarn.poker.dataprocessing.PokerAction;
import com.saccarn.poker.dataprocessing.PokerStage;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Neil on 15/02/2017.
 */
public class GamePlayerRecordTest {

    @Test
    public void testPreFlopPreComputationValuesWhenNothingHasBeenAddedToGamePlayerRecord() {
        GamePlayerRecord gpr = new GamePlayerRecord();
        gpr.doPreComputations();
        Player p1 = gpr.getPlayerOne();
        Player p2 = gpr.getPlayerTwo();
        Assert.assertEquals(0, p1.getNumBetRaisesPreFlop());
        Assert.assertEquals(0, p2.getNumBetRaisesPreFlop());
    }

    @Test
    public void  testFlopPreComputationValuesWhenNothingHasBeenAddedToGamePlayerRecord() {
        GamePlayerRecord gpr = new GamePlayerRecord();
        gpr.doPreComputations();
        Player p1 = gpr.getPlayerOne();
        Player p2 = gpr.getPlayerTwo();
        Assert.assertEquals(0, p1.getNumBetRaisesFlop());
        Assert.assertEquals(0, p2.getNumBetRaisesFlop());
    }

    @Test
    public void testTurnPreComputationValuesWhenNothingHasBeenAddedToGamePlayerRecord() {
        GamePlayerRecord gpr = new GamePlayerRecord();
        gpr.doPreComputations();
        Player p1 = gpr.getPlayerOne();
        Player p2 = gpr.getPlayerTwo();
        Assert.assertEquals(0, p1.getNumBetRaisesTurn());
        Assert.assertEquals(0, p2.getNumBetRaisesTurn());
    }

    @Test
    public void testRiverPreComputationValuesWhenNothingHasBeenAddedToGamePlayerRecord() {
        GamePlayerRecord gpr = new GamePlayerRecord();
        gpr.doPreComputations();
        Player p1 = gpr.getPlayerOne();
        Player p2 = gpr.getPlayerTwo();
        Assert.assertEquals(0, p1.getNumBetRaisesRiver());
        Assert.assertEquals(0, p2.getNumBetRaisesRiver());
    }

    @Test
    public void testTotalPreComputationValuesWhenNothingHasBeenAddedToGamePlayerRecord() {
        GamePlayerRecord gpr = new GamePlayerRecord();
        gpr.doPreComputations();
        Player p1 = gpr.getPlayerOne();
        Player p2 = gpr.getPlayerTwo();
        Assert.assertEquals(0, p1.getTotalNumBetRaises());
        Assert.assertEquals(0, p2.getTotalNumBetRaises());
    }

    @Test
    public void testAddCardsPlayerOne() {
        GamePlayerRecord gpr = new GamePlayerRecord();
        gpr.addCardsPlayerOne("6h", "9d");
        List expectedCardsList = new LinkedList();
        expectedCardsList.add("6h");
        expectedCardsList.add("9d");
        Assert.assertEquals(expectedCardsList, gpr.getCardPairPlayerOne());
    }
    @Test
    public void testAddCardsPlayerTwo() {
        GamePlayerRecord gpr = new GamePlayerRecord();
        gpr.addCardsPlayerTwo("6h", "9d");
        List expectedCardsList = new LinkedList();
        expectedCardsList.add("6h");
        expectedCardsList.add("9d");
        Assert.assertEquals(expectedCardsList, gpr.getCardPairPlayerTwo());
    }

    @Test
    public void testAddCardsPlayerOneAndAddCardsPlayerTwoAreNotAffectingEachOther() {
        GamePlayerRecord gpr = new GamePlayerRecord();
        gpr.addCardsPlayerOne("4s", "2s");
        List expectedCardsListP1 = new LinkedList();
        expectedCardsListP1.add("4s");
        expectedCardsListP1.add("2s");
        gpr.addCardsPlayerTwo("3c", "9s");
        List expectedCardsListP2 = new LinkedList();
        expectedCardsListP2.add("3c");
        expectedCardsListP2.add("9s");
        Assert.assertNotEquals(expectedCardsListP2, gpr.getCardPairPlayerOne());
        Assert.assertNotEquals(expectedCardsListP1, gpr.getCardPairPlayerTwo());
    }

    @Test
    public void testAddingActionToPreFlopGamePlayerRecord() {
        GamePlayerRecord gpr = new GamePlayerRecord();
        gpr.addPlayerAction("TestName", "k", PokerStage.PREFLOP);
        gpr.getPlayerActionsPreFlop();
        List expected = new LinkedList();
        expected.add("TestName");
        expected.add(PokerAction.CHECK);
        Assert.assertEquals(expected, gpr.getPlayerActionsPreFlop().get(0));
    }

    @Test
    public void testAddingActionToFlopGamePlayerRecord() {
        GamePlayerRecord gpr = new GamePlayerRecord();
        gpr.addPlayerAction("TestName", "k", PokerStage.FLOP);
        gpr.getPlayerActionsFlop();
        List expected = new LinkedList();
        expected.add("TestName");
        expected.add(PokerAction.CHECK);
        Assert.assertEquals(expected, gpr.getPlayerActionsFlop().get(0));
    }

    @Test
    public void testAddingActionToTurnGamePlayerRecord() {
        GamePlayerRecord gpr = new GamePlayerRecord();
        gpr.addPlayerAction("TestName", "k", PokerStage.TURN);
        gpr.getPlayerActionsTurn();
        List expected = new LinkedList();
        expected.add("TestName");
        expected.add(PokerAction.CHECK);
        Assert.assertEquals(expected, gpr.getPlayerActionsTurn().get(0));
    }

    @Test
    public void testAddingActionToRiverGamePlayerRecord() {
        GamePlayerRecord gpr = new GamePlayerRecord();
        gpr.addPlayerAction("TestName", "k", PokerStage.RIVER);
        gpr.getPlayerActionsTurn();
        List expected = new LinkedList();
        expected.add("TestName");
        expected.add(PokerAction.CHECK);
        Assert.assertEquals(expected, gpr.getPlayerActionsRiver().get(0));
    }

    @Test
    public void testAddingBigBlindAndCheckingThatPlayerNameWasAdded() {
        GamePlayerRecord gpr = new GamePlayerRecord();
        gpr.addPlayerAction("TestName1", "B", PokerStage.PREFLOP);
        gpr.addPlayerAction("TestName2", "B", PokerStage.PREFLOP);
        Assert.assertTrue(gpr.getPlayerNames().get(0).equals("TestName1"));
        Assert.assertTrue(gpr.getPlayerNames().get(1).equals("TestName2"));
    }

    @Test
    public void testAddingActionOtherThanBigBlindAndCheckingThatPlayerNameWasAdded() {
        GamePlayerRecord gpr = new GamePlayerRecord();
        gpr.addPlayerAction("TestName1", "k", PokerStage.PREFLOP);
        gpr.addPlayerAction("TestName2", "r", PokerStage.PREFLOP);
        Assert.assertTrue(gpr.getPlayerNames().size() == 0);
    }
}
