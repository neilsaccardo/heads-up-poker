package com.saccarn.poker.dataprocessing;

/**
 * Created by Neil on 16/02/2017.
 */
public class Player {
    private String name = "";

    private int numBetRaisesTurn = 0;
    private int numBetRaisesRiver = 0;
    private int numBetRaisesFlop = 0;
    private int numBetRaisesPreFlop = 0;
    private int totalActions = 0;
    private int totalNumActionsFlop = 0;
    private int totalNumActionsPreFlop = 0;
    private int totalNumActionsTurn = 0;
    private int totalNumActionsRiver = 0;
    private boolean winner = false;

    private int foldAtPreFlop = 0;
    private int foldAtFlop = 0;
    private int foldAtRiver = 0;
    private int foldAtTurn = 0;

    private int reachedPreFlop = 0;
    private int reachedFlop = 0;
    private int reachedTurn = 0;
    private int reachedRiver = 0;


    public int getTotalNumActionsFlop() {
        return totalNumActionsFlop;
    }

    public int getTotalNumActionsPreFlop() {
        return totalNumActionsPreFlop;
    }

    public int getTotalNumActionsTurn() {
        return totalNumActionsTurn;
    }

    public int getTotalNumActionsRiver() {
        return totalNumActionsRiver;
    }


    public int getNumBetRaisesTurn() {
        return numBetRaisesTurn;
    }

    public int getNumBetRaisesRiver() {
        return numBetRaisesRiver;
    }

    public int getNumBetRaisesFlop() {
        return numBetRaisesFlop;
    }

    public int getNumBetRaisesPreFlop() {
        return numBetRaisesPreFlop;
    }

    public void setNumBetRaisesTurn(int numBetRaisesTurn) {
        this.numBetRaisesTurn = numBetRaisesTurn;
    }

    public void setNumBetRaisesRiver(int numBetRaisesRiver) {
        this.numBetRaisesRiver = numBetRaisesRiver;
    }

    public void setNumBetRaisesFlop(int numBetRaisesFlop) {
        this.numBetRaisesFlop = numBetRaisesFlop;
    }

    public void setNumBetRaisesPreFlop(int preFlopNumBetRaises) {
        this.numBetRaisesPreFlop = preFlopNumBetRaises;
    }

    public int getTotalNumBetRaises() {
        return numBetRaisesTurn + numBetRaisesFlop +
                numBetRaisesRiver + numBetRaisesPreFlop;
    }

    public double getNumBetRaiseToTotalActionRatio() {
        return (double)getTotalNumBetRaises() / (double)getTotalActions();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {return name;}

    public void setWinner() {
        winner = true;
    }
    public void setLoser() {
        winner = false;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setTotalActions(int totalActions) {
        this.totalActions = totalActions;
    }

    public int getTotalActions() {
        return totalActions;
    }

    public void setTotalNumActionsFlop(int totalNumActionsFlop) {
        this.totalNumActionsFlop = totalNumActionsFlop;
    }

    public void setTotalNumActionsPreFlop(int totalNumActionsPreFlop) {
        this.totalNumActionsPreFlop = totalNumActionsPreFlop;
    }

    public void setTotalNumActionsTurn(int totalNumActionsTurn) {
        this.totalNumActionsTurn = totalNumActionsTurn;
    }

    public void setTotalNumActionsRiver(int totalNumActionsRiver) {
        this.totalNumActionsRiver = totalNumActionsRiver;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public int getFoldAtPreFlop() {
        return foldAtPreFlop;
    }

    public void setFoldAtPreFlop(int foldAtPreFlop) {
        this.foldAtPreFlop = foldAtPreFlop;
    }

    public int getFoldAtFlop() {
        return foldAtFlop;
    }

    public void setFoldAtFlop(int foldAtFlop) {
        this.foldAtFlop = foldAtFlop;
    }

    public int getFoldAtRiver() {
        return foldAtRiver;
    }

    public void setFoldAtRiver(int foldAtRiver) {
        this.foldAtRiver = foldAtRiver;
    }

    public int getFoldAtTurn() {
        return foldAtTurn;
    }

    public void setFoldAtTurn(int foldAtTurn) {
        this.foldAtTurn = foldAtTurn;
    }

    public int getReachedPreFlop() {
        return reachedPreFlop;
    }

    public void setReachedPreFlop(int reachedPreFlop) {
        this.reachedPreFlop = reachedPreFlop;
    }

    public int getReachedFlop() {
        return reachedFlop;
    }

    public void setReachedFlop(int reachedFlop) {
        this.reachedFlop = reachedFlop;
    }

    public int getReachedTurn() {
        return reachedTurn;
    }

    public void setReachedTurn(int reachedTurn) {
        this.reachedTurn = reachedTurn;
    }

    public int getReachedRiver() {
        return reachedRiver;
    }

    public void setReachedRiver(int reachedRiver) {
        this.reachedRiver = reachedRiver;
    }

}
