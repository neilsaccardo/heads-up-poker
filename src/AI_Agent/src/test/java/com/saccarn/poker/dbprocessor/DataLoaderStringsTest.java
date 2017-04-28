package com.saccarn.poker.dbprocessor;

import com.saccarn.poker.ai.ActionStrings;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

/**
 * Created by Neil on 05/04/2017.
 */
public class DataLoaderStringsTest {

    @Test
    public void testConstructorIsPrivate() throws Exception {
        Constructor constructor = DataLoaderStrings.class.getDeclaredConstructor();
        boolean isPrivateConstructor = Modifier.isPrivate(constructor.getModifiers());
        Assert.assertEquals("The DataLoaderStrings constructor should be private", true, isPrivateConstructor);
        constructor.setAccessible(true);
        constructor.newInstance();
    }


}
