package model;

import static org.junit.jupiter.api.Assertions.*;

// import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestExpense {
    private Expense testExpenseOne;
    private Expense testExpenseTwo;

    @BeforeEach
    void runBefore() {
        testExpenseOne = new Expense(22.0, "coffee and lunch", "July 13", 7);
        testExpenseTwo = new Expense(57.56, "weekly groceries", "July 10", 7);
    }

    @Test
    void testConstructor() {
        assertEquals(22.0, testExpenseOne.getAmount());
        assertEquals("coffee and lunch", testExpenseOne.getDescription());
        assertEquals("July 13", testExpenseOne.getDate());
        assertEquals(7, testExpenseOne.getMonth());
    }

    @Test
    void testGetters() {
        Expense expense = new Expense(10, "coffee", "August 12", 8);

        assertEquals(10, expense.getAmount());
        assertEquals("coffee", expense.getDescription());
        assertEquals("August 12", expense.getDate());
        assertEquals(8, expense.getMonth());
    }

    @Test
    void testSetters() {
        testExpenseTwo.setAmount(100);
        testExpenseTwo.setDescription("weekly groceries and cleaning supplies");
        testExpenseTwo.setDate("July 11");
        testExpenseTwo.setMonth(1);

        assertEquals(100, testExpenseTwo.getAmount());
        assertEquals("weekly groceries and cleaning supplies", testExpenseTwo.getDescription());
        assertEquals("July 11", testExpenseTwo.getDate());
        assertEquals(1, testExpenseTwo.getMonth());
    }
}
