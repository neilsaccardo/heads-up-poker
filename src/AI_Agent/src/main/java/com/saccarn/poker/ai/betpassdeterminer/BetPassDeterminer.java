package com.saccarn.poker.ai.betpassdeterminer;

/**
 * Created by Neil on 12/04/2017.
 */
public class BetPassDeterminer {

    private double random;
    private double passProbability;
    private static final double TOTAL = 1.0;
    private static final double INVERSE_TOTAL = 0.0;
    private static final double EXPONENT = 2;

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

    public double calculateBetFunction() {
        double inversePassProbability =  BetPassDeterminer.TOTAL - passProbability;
        double y = ((-(Math.pow(random, BetPassDeterminer.EXPONENT))) / inversePassProbability) + 1;
        return  y;
    }
}
