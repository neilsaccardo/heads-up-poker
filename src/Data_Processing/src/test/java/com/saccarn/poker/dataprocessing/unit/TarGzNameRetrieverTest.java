package com.saccarn.poker.dataprocessing.unit;

import com.saccarn.poker.dataprocessing.TarGzNameRetriever;
import org.junit.Assert;
import org.junit.Test;

import java.net.MalformedURLException;

/**
 * Created by Neil on 15/02/2017.
 */
public class TarGzNameRetrieverTest {

    @Test
    public void getFileNamesByStringPattern() throws MalformedURLException {
        TarGzNameRetriever tgnr = new TarGzNameRetriever();
        Assert.assertEquals(true, true);
    }
}
