package com.saccarn.poker.ai;


import com.saccarn.poker.ai.potential.StraightFlushChecker;

/**
 * Created by Neil on 05/04/2017.
 */
public class HandPotential {



    public double calculateHandPotential(String holeCardOne, String holeCardTwo, String boardCard, String boardCard1, String boardCard2) {

        return 0;
    }


    public double isStraightFlushOn(String holeCardOne, String holeCardTwo, String [] boardCards) {
        StraightFlushChecker sfc = new StraightFlushChecker(holeCardOne, holeCardTwo, boardCards);

        return 0.0;
    }
}
