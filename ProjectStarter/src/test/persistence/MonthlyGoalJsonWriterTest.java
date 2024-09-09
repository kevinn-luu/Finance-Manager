package persistence;

import model.MonthlyExpenseGoal;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class MonthlyGoalJsonWriterTest {

    @Test
    void testWriterIncorrectFile() {
        try {
            MonthlyGoalJsonWriter writer = new MonthlyGoalJsonWriter("./data\0thisfiledoesn'texist.json");
            writer.open();
            fail("FileNotFoundException expected");
        } catch (FileNotFoundException e) {
            // pass
        }
    }

    @Test
    void testWriterWriteGoal() {
        MonthlyExpenseGoal mg = new MonthlyExpenseGoal(3000);
        mg.setMonth(12);
        try {
            MonthlyGoalJsonWriter writer = new MonthlyGoalJsonWriter("./data/testwritemonthlygoal.json");
            writer.open();
            writer.write(mg);
            writer.close();

            MonthlyGoalJsonReader reader = new MonthlyGoalJsonReader("./data/testwritemonthlygoal.json");
            MonthlyExpenseGoal goalRead = reader.read();

            assertEquals(3000, goalRead.getGoal());
            assertEquals(12, goalRead.getMonth());
            assertEquals(0, goalRead.getCurrent().size());
        } catch (IOException e) {
            fail("IOException not expected here");
        }
    }
}
