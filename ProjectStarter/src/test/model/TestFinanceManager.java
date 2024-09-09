package model;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestFinanceManager {
    private FinanceManager testFinanceManager;
    private Expense testExpenseOne;
    private Expense testExpenseTwo;
    private Expense testExpenseThree;

    @BeforeEach
    void runBefore() {
        testExpenseOne = new Expense(20, "food", "March 5", 3);
        testExpenseTwo = new Expense(30, "coffee", "March 2", 3);
        testExpenseThree = new Expense(70, "groceries", "October 10", 10);

        testFinanceManager = new FinanceManager();
    }

    @Test
    void testConstructor() {
        assertEquals(0, testFinanceManager.getExpenses().size());
        assertEquals(0, testFinanceManager.getMonthlyExpenseGoal().getGoal());
    }

    @Test
    void testAddExpense() {
        testFinanceManager.addExpense(testExpenseOne);

        List<Expense> loe = testFinanceManager.getExpenses();

        assertEquals(1, loe.size());
        assertTrue(loe.contains(testExpenseOne));
    }

    @Test
    void testAddMultipleExpenses() {
        testFinanceManager.addExpense(testExpenseOne);
        testFinanceManager.addExpense(testExpenseTwo);
        testFinanceManager.addExpense(testExpenseThree);

        List<Expense> loe = testFinanceManager.getExpenses();

        assertEquals(3, loe.size());
        assertTrue(loe.contains(testExpenseOne));
        assertTrue(loe.contains(testExpenseTwo));
        assertTrue(loe.contains(testExpenseThree));
    }

    @Test
    void testRemoveExpense() {
        testFinanceManager.addExpense(testExpenseOne);
        testFinanceManager.addExpense(testExpenseTwo);
        testFinanceManager.addExpense(testExpenseThree);
        testFinanceManager.removeExpense(testExpenseTwo);

        List<Expense> loe = testFinanceManager.getExpenses();

        assertEquals(2, loe.size());
        assertTrue(loe.contains(testExpenseOne));
        assertFalse(loe.contains(testExpenseTwo));
        assertTrue(loe.contains(testExpenseThree));
    }

    @Test
    void testSortExpensesNoneOnDate() {
        testFinanceManager.addExpense(testExpenseOne);
        testFinanceManager.addExpense(testExpenseTwo);
        testFinanceManager.addExpense(testExpenseThree);

        List<Expense> loe = testFinanceManager.sortExpensesByDate("deuigh");

        assertEquals(0, loe.size());
    }

    @Test
    void testSortExpensesByDate() {
        testFinanceManager.addExpense(testExpenseOne);
        testFinanceManager.addExpense(testExpenseTwo);
        testFinanceManager.addExpense(testExpenseThree);

        List<Expense> loe = testFinanceManager.sortExpensesByDate("March 5");

        assertEquals(1, loe.size());
        assertTrue(loe.contains(testExpenseOne));
    }

    @Test
    void testSortExpensesByMonthNone() {
        testFinanceManager.addExpense(testExpenseOne);
        testFinanceManager.addExpense(testExpenseTwo);
        testFinanceManager.addExpense(testExpenseThree);

        List<Expense> loe = testFinanceManager.sortExpensesByMonth(12);
        
        assertEquals(0, loe.size());
    }

    @Test
    void testSortExpensesByMonth() {
        testFinanceManager.addExpense(testExpenseOne);
        testFinanceManager.addExpense(testExpenseTwo);
        testFinanceManager.addExpense(testExpenseThree);

        List<Expense> loe = testFinanceManager.sortExpensesByMonth(3);

        assertEquals(2, loe.size());
        assertTrue(loe.contains(testExpenseOne));
        assertTrue(loe.contains(testExpenseTwo));
    }

    @Test
    void testGetExpensesForGoal() {
        MonthlyExpenseGoal mg = testFinanceManager.getMonthlyExpenseGoal();
        mg.setGoal(100);
        mg.setMonth(3);

        testFinanceManager.addExpense(testExpenseOne);
        testFinanceManager.addExpense(testExpenseTwo);
        testFinanceManager.addExpense(testExpenseThree);

        List<Expense> loe = testFinanceManager.getExpensesForGoal();

        assertEquals(2, loe.size());
        assertTrue(loe.contains(testExpenseOne));
        assertTrue(loe.contains(testExpenseTwo));
    }

    @Test
    void testSetters() {
        MonthlyExpenseGoal mg = new MonthlyExpenseGoal(20);
        testFinanceManager.setMonthlyGoal(mg);
        assertEquals(20, testFinanceManager.getMonthlyExpenseGoal().getGoal());

        testFinanceManager.addExpense(testExpenseOne);
        testFinanceManager.addExpense(testExpenseTwo);
        List<Expense> loe = new ArrayList<>();
        loe.add(testExpenseThree);
        testFinanceManager.setExpenses(loe);
        List<Expense> expenses = testFinanceManager.getExpenses();
        assertEquals(1, expenses.size());
        assertTrue(expenses.contains(testExpenseThree));
    }

}
