package com.saccarn.poker.dataprocessing.unit;

import com.saccarn.poker.dataprocessing.TarGzNameRetriever;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Neil on 15/02/2017.
 */
public class TarGzNameRetrieverTest {

    @Ignore @Test
    public void testGetFileNamesByStringPatternUsingNoLimitPattern() throws IOException {
        TarGzNameRetriever tgnr = new TarGzNameRetriever();
        List actualList = tgnr.getFileNamesByStringPattern("nolimit\\.\\d{6}\\.tgz");
        String [] expectedListAsArray = {"nolimit.199505.tgz", "nolimit.199506.tgz", "nolimit.199507.tgz"
                , "nolimit.199508.tgz", "nolimit.199509.tgz", "nolimit.199510.tgz", "nolimit.199511.tgz"
                , "nolimit.199512.tgz", "nolimit.199601.tgz", "nolimit.199612.tgz", "nolimit.199701.tgz" };

        List expectedList = Arrays.asList(expectedListAsArray);
        Assert.assertEquals(actualList, expectedList);
    }
    @Ignore @Test
    public void testGetFileNamesByStringPatternUsingNonsensePattern() throws IOException {
        TarGzNameRetriever tgnr = new TarGzNameRetriever();
        List actualEmptyList = tgnr.getFileNamesByStringPattern("incorrectvalue");
        List expectedEmptyList = new LinkedList();
        Assert.assertEquals(actualEmptyList.size(), expectedEmptyList.size());
    }
}
