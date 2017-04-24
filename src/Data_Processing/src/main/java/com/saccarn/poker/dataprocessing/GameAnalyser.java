package com.saccarn.poker.dataprocessing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neil on 06/02/2017.
 */
public class GameAnalyser {

    private File dir;

    public GameAnalyser(File dir0) {
        dir = dir0;
    }

    public GameAnalyser() {
        dir = new File("C:\\Data\\test\\nolimit\\");
    }

    public List<GamePlayerRecord> analyse() throws FileNotFoundException {
        File [] fs = dir.listFiles();
        List<GamePlayerRecord> gprs = new ArrayList<>();
        for(int i = 0; i < fs.length; i++) {
            GameMaker gm = new GameMaker(new File(fs[i], "hroster"));
            gprs.addAll(gm.readAllGames());
        }
        return gprs;
    }

    public List<GamePlayerRecord> analyseTest() throws FileNotFoundException {
        File f = new File(dir, "199505\\");
        GameMaker gm = new GameMaker(new File(f, "hroster"));
        return gm.readAllGames();
    }

    public static void main(String [] args) throws FileNotFoundException {
        GameAnalyser ga = new GameAnalyser();
        ga.analyse();
    }
}
