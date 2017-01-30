import sun.misc.Regexp;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Neil on 13/12/2016.
 */
public class HandDownloader {
//    private URL baseUrl = new URL("http://webdocs.cs.ualberta.ca/~games/poker/IRCdata/");
    private String baseStringURL = "http://webdocs.cs.ualberta.ca/~games/poker/IRCdata/";
    private Regexp fileRegularExp = new Regexp("href=\"holdem[0-9]?.[0-9]*.gtz\"");
    public HandDownloader() throws MalformedURLException {
    }

    public void getThisFile() throws MalformedURLException {

        String fileName = "holdem2.199504.tgz";
        URL url = new URL(baseStringURL + fileName);
//        FileUtils.copyURLToFile(url, File)

    }
}
