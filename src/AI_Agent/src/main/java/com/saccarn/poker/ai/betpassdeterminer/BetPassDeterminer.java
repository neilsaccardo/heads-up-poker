package com.saccarn.poker.ai.betpassdeterminer;

/**
 * This class implements betting function
 *
 * Created by Neil on 12/04/2017.
 */
public class BetPassDeterminer {

    private double random;
    private double passProbability;
    private static final double TOTAL = 1.0;
    private static final double INVERSE_TOTAL = 0.0;
    private static final double EXPONENT = 2;

    /**
     * Full args constructor with pass prob and random number.
     *
     * @param random random value, between 0.0 and 1.0
     * @param passProbability Value from check/call function
     */
    public BetPassDeterminer(double random, double passProbability) {
        if (random > BetPassDeterminer.TOTAL || passProbability > BetPassDeterminer.TOTAL) {
            throw new IllegalArgumentException();
        }
        if (random < BetPassDeterminer.INVERSE_TOTAL || passProbability < BetPassDeterminer.INVERSE_TOTAL) {
            throw new IllegalArgumentException();
        }
        this.random = random;
        this.passProbability = passProbability;
    }

    /**
     * Returns value of function given the random number and pass prob inputs.
     * @return betting function output.
     */

    public double calculateBetFunction() {
        double inversePassProbability =  BetPassDeterminer.TOTAL - passProbability;
        double y = ((-(Math.pow(random, BetPassDeterminer.EXPONENT))) / inversePassProbability) + 1;
        return  y;
    }
}
