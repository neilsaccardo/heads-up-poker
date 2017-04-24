package com.saccarn.poker.ai;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

/**
 * Created by Neil on 05/04/2017.
 */
public class PlayerClusterTest {

    @Test
    public void testConstructorIsPrivate() throws Exception {
        Constructor constructor = PlayerCluster.class.getDeclaredConstructor();
        boolean isPrivateConstructor = Modifier.isPrivate(constructor.getModifiers());
        Assert.assertEquals("The PlayerCluster constructor should be private", true, isPrivateConstructor);
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
