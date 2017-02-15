package com.saccarn.poker.dataprocessing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Neil on 01/02/2017.
 */
public class TarGzNameRetriever {
    private URL url = new URL("http://webdocs.cs.ualberta.ca/~games/poker/IRCdata/");

    public TarGzNameRetriever() throws MalformedURLException {
    }
    /* Reading from HTTP stream taken from Oracle JavaDocs here:
    *  http://docs.oracle.com/javase/tutorial/networking/urls/readingWriting.html
    *  */
    public List<String> getFileNamesByStringPattern(String p) throws IOException {
        List<String> listOfFileNames = new LinkedList<>();
        URLConnection urlConnection = url.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        urlConnection.getInputStream()));
        String inputLine;
        Pattern pattern = Pattern.compile(p);
        System.out.println(p);
        while ((inputLine = in.readLine()) != null) {
            Matcher m = pattern.matcher(inputLine);
            if(m.find()) {
                listOfFileNames.add(m.group(0));
            }
        }
        in.close();
        return listOfFileNames;
    }
}
