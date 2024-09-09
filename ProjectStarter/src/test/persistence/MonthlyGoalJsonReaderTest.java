package persistence;

import model.MonthlyExpenseGoal;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class MonthlyGoalJsonReaderTest {

    @Test
    void testReadFileNoneExists() {
        MonthlyGoalJsonReader reader = new MonthlyGoalJsonReader("./data/thisfiledoesn'texist.json");
        try {
            reader.read();
            fail("Expected IOException");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReadFileMonthlyGoalExists() {
        MonthlyGoalJsonReader reader = new MonthlyGoalJsonReader("./data/testmonthlygoal.json");
        try {
            MonthlyExpenseGoal goal = reader.read();
            assertEquals(200, goal.getGoal());
            assertEquals(5, goal.getMonth());
            assertEquals(0, goal.getCurrent().size());
        } catch (IOException e) {
            fail("IOException not expected");
        }
    }


}
