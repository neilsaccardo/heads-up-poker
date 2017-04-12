package com.saccarn.poker.ai.betpassdeterminer;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

/**
 * Created by Neil on 12/04/2017.
 */
public class BetPassActionValuesTest {

    @Test
    public void testConstructorIsPrivate() throws Exception {
        Constructor constructor = BetPassActionValues.class.getDeclaredConstructor();
        boolean isPrivateConstructor = Modifier.isPrivate(constructor.getModifiers());
        Assert.assertEquals("The BetPassActionValues constructor should be private", true, isPrivateConstructor);
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
