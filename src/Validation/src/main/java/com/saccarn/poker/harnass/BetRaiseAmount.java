package com.saccarn.poker.harnass;

import com.saccarn.poker.ai.ActionStrings;

/**
 * Created by Neil on 24/04/2017.
 */
public class BetRaiseAmount {

    public static int calculateBetRaiseAmount(String action, int potSize, int stackSize) {
        if (actionNotBetOrRaise(action)) {
            return 0;
        }
        int baseAmount = (potSize /4) * 3;
        if (action.equals(ActionStrings.ACTION_BET1) || action.equals(ActionStrings.ACTION_RAISE1)) {
            return smallerOf(baseAmount * 1, stackSize);
        }
        if (action.equals(ActionStrings.ACTION_BET2) || action.equals(ActionStrings.ACTION_RAISE2)) {
            return smallerOf(baseAmount * 2, stackSize);
        }
        if (action.equals(ActionStrings.ACTION_BET3) || action.equals(ActionStrings.ACTION_RAISE3)) {
            return smallerOf(baseAmount * 3, stackSize);
        }
        if (action.equals(ActionStrings.ACTION_ALLIN)) {
            return stackSize;
        }
        return 0;
    }

    private static int smallerOf(int betAmount, int stackSize) {
        return Math.min(betAmount, stackSize);
    }

    private static boolean actionNotBetOrRaise(String action) {
        return action.equals(ActionStrings.ACTION_FOLD)
                || action.equals(ActionStrings.ACTION_CHECK)
                || action.equals(ActionStrings.ACTION_CALL);
    }
}
