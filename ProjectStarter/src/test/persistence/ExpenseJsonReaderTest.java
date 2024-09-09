package persistence;

import model.Expense;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseJsonReaderTest {

    @Test
    void testReadFileNoneExists() {
        ExpenseJsonReader reader = new ExpenseJsonReader("./data/thisfiledoesn'texist.json");
        try {
            List<Expense> expenses = reader.read();
            fail("Expected IO exception");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReadFileEmptyFile() {
        ExpenseJsonReader reader = new ExpenseJsonReader("./data/testexpensesempty.json");
        try {
            List<Expense> expenses = reader.read();
            assertEquals(0, expenses.size());
        } catch (IOException e) {
            fail("IOException shouldn't happen here");
        }
    }

    @Test
    void testReadFileNotEmpty() {
        ExpenseJsonReader reader = new ExpenseJsonReader("./data/testexpenses.json");
        try {
            List<Expense> expenses = reader.read();
            assertEquals(2, expenses.size());
            Expense e1 = expenses.get(0);
            Expense e2 = expenses.get(1);

            assertEquals("October 10", e1.getDate());
            assertEquals(50, e1.getAmount());
            assertEquals(10, e1.getMonth());
            assertEquals("coffee", e1.getDescription());

            assertEquals("August 8", e2.getDate());
            assertEquals(200, e2.getAmount());
            assertEquals(8, e2.getMonth());
            assertEquals("dinner", e2.getDescription());

        } catch (IOException e) {
            fail("IOException is not expected here");
        }
    }
}
