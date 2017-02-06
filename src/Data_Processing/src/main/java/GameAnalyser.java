import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Neil on 06/02/2017.
 */
public class GameAnalyser {

    public void analyse() throws FileNotFoundException {
        File f = new File("C:\\Data\\test\\nolimit\\");
        File [] fs = f.listFiles();
        for(int i = 0; i < fs.length; i++) {
            GameMaker gm = new GameMaker(new File(fs[i], "hroster"));
            gm.readAllGames();
        }

    }

    public static void main(String [] args) throws FileNotFoundException {
        GameAnalyser ga = new GameAnalyser();
        ga.analyse();
    }
}
