package persistence;

import model.Expense;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseJsonWriterTest {
    @Test
    void testWriterIncorrectFile() {
        try {
            ExpenseJsonWriter writer = new ExpenseJsonWriter("./data\0thisfiledoesn'texist.json");
            writer.open();
            fail("FileNotFoundException expected");
        } catch (FileNotFoundException e) {
           // pass
        }
    }

    @Test
    void testWriterEmptyExpenses() {
        List<Expense> expenses = new ArrayList<>();
        try {
            ExpenseJsonWriter writer = new ExpenseJsonWriter("./data/testexpensesempty.json");
            writer.open();
            writer.write(expenses);
            writer.close();

            ExpenseJsonReader reader = new ExpenseJsonReader("./data/testexpensesempty.json");
            List<Expense> expensesRead = reader.read();
            assertEquals(0, expensesRead.size());
        } catch (IOException e) {
            fail("Exception not expected"); 
        }
    }

    @Test
    void testWriterWriteExpenses() {
        Expense e1 = new Expense(20, "lunch", "March 3", 3);
        Expense e2 = new Expense(100, "gift", "October 5", 10);
        List<Expense> expenses = new ArrayList<>();
        expenses.add(e1);
        expenses.add(e2);
        try {
            ExpenseJsonWriter writer = new ExpenseJsonWriter("./data/testwriteexpenses.json");
            writer.open();
            writer.write(expenses);
            writer.close();

            ExpenseJsonReader reader = new ExpenseJsonReader("./data/testwriteexpenses.json");
            List<Expense> expensesRead = reader.read();
            Expense readExpense1 = expensesRead.get(0);
            Expense readExpense2 = expensesRead.get(1);

            assertEquals(2, expensesRead.size());

            assertEquals("March 3", readExpense1.getDate());
            assertEquals(20, readExpense1.getAmount());

            assertEquals("October 5", readExpense2.getDate());
            assertEquals(100, readExpense2.getAmount());
    
        } catch (IOException e) {
            fail("IOException not expected");
        }
    }
    
    
}
