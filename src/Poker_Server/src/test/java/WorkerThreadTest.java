import com.saccarn.poker.WorkerThread;
import com.saccarn.poker.dbprocessor.DataLoaderStrings;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * Created by Neil on 20/04/2017.
 */
public class WorkerThreadTest {

    @Test
    public void testCreateOpponentMapHasPreFlopRatioField() {
        String [] modelArray = {"2", "0", "0", "3", "3", "2"};
        Map<String, Double> opponentModelMap = WorkerThread.createOpponentMap(modelArray);
        boolean hasPreFlopRatioField = opponentModelMap.containsKey(DataLoaderStrings.PRE_FLOP_FOLDED_RATIO);
        Assert.assertEquals("The opponent model created should have PRE_FLOP_FOLDED_RATIO key.", true, hasPreFlopRatioField);
    }

    @Test
    public void testCreateOpponentMapHasFlopRatioField() {
        String [] modelArray = {"2", "0", "0", "3", "3", "2"};
        Map<String, Double> opponentModelMap = WorkerThread.createOpponentMap(modelArray);
        boolean flopRatioField = opponentModelMap.containsKey(DataLoaderStrings.FLOP_FOLDED_RATIO);
        Assert.assertEquals("The opponent model created should have PRE_FLOP_FOLDED_RATIO key.", true, flopRatioField);
    }

    @Test
    public void testCreateOpponentMapHasTurnRatioField() {
        String [] modelArray = {"2", "0", "0", "3", "3", "2"};
        Map<String, Double> opponentModelMap = WorkerThread.createOpponentMap(modelArray);
        boolean turnRatioField = opponentModelMap.containsKey(DataLoaderStrings.TURN_FOLDED_RATIO);
        Assert.assertEquals("The opponent model created should have PRE_FLOP_FOLDED_RATIO key.", true, turnRatioField);
    }

    @Test
    public void testCreateOpponentMapHasRiverRatioField() {
        String [] modelArray = {"2", "0", "0", "3", "3", "2"};
        Map<String, Double> opponentModelMap = WorkerThread.createOpponentMap(modelArray);
        boolean hasRiverRatioField = opponentModelMap.containsKey(DataLoaderStrings.RIVER_FOLDED_RATIO);
        Assert.assertEquals("The opponent model created should have PRE_FLOP_FOLDED_RATIO key.", true, hasRiverRatioField);
    }

    @Test
    public void testGetAction() {

    }
}
