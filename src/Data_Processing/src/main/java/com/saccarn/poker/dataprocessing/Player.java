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

    public void setName(String name) {
        this.name = name;
    }
}
