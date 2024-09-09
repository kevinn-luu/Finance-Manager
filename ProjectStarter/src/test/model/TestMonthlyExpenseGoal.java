package model;

import static org.junit.jupiter.api.Assertions.*;

// import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TestMonthlyExpenseGoal {
    private Expense testExpenseOne;
    private Expense testExpenseTwo;
    private Expense testExpenseThree;

    private MonthlyExpenseGoal testExpenseGoal;

    private List<Expense> testListOne;

    @BeforeEach
    void runBefore() {
        testExpenseOne = new Expense(20, "food", "March 5", 3);
        testExpenseTwo = new Expense(30, "coffee", "March 2", 3);
        testExpenseThree = new Expense(70, "groceries", "March 10", 3);

        testListOne = new ArrayList<>();

        testListOne.add(testExpenseOne);
        testListOne.add(testExpenseTwo);
        testListOne.add(testExpenseThree);

        testExpenseGoal = new MonthlyExpenseGoal(100);

    }

    @Test
    void testConstructor() {
        assertEquals(100, testExpenseGoal.getGoal());
        
        List<Expense> empty = new ArrayList<>();

        assertEquals(empty, testExpenseGoal.getCurrent());
    }

    @Test
    void testAddExpense() {
        //test add one item to list
        testExpenseGoal.addExpense(testExpenseOne);

        List<Expense> expectedOne = new ArrayList<>();
        expectedOne.add(testExpenseOne);
        assertEquals(expectedOne, testExpenseGoal.getCurrent());

        //test accumulative behavior
        testExpenseGoal.addExpense(testExpenseTwo);
        testExpenseGoal.addExpense(testExpenseThree);

        List<Expense> expectedTwo = new ArrayList<>();
        expectedTwo.add(testExpenseOne);
        expectedTwo.add(testExpenseTwo);
        expectedTwo.add(testExpenseThree);
        assertEquals(expectedTwo, testExpenseGoal.getCurrent());
    }

    @Test
    void testCalculateCurrentAmount() {
        //Empty list should produce 0 total sum
        assertEquals(0, testExpenseGoal.calculateCurrentAmount());

        testExpenseGoal.addExpense(testExpenseOne);
        testExpenseGoal.addExpense(testExpenseTwo);
        testExpenseGoal.addExpense(testExpenseThree);

        double currentTotal = testExpenseOne.getAmount() + testExpenseTwo.getAmount() 
                + testExpenseThree.getAmount();

        assertEquals(currentTotal, testExpenseGoal.calculateCurrentAmount());
    }

    @Test
    void testCheckIfGoalReached() {
        testExpenseGoal.addExpense(testExpenseOne);
        assertFalse(testExpenseGoal.checkIfGoalReached());

        testExpenseGoal.addExpense(testExpenseTwo);
        testExpenseGoal.addExpense(testExpenseThree);
        assertTrue(testExpenseGoal.checkIfGoalReached());
    }

    @Test
    void testSetters() {
        testExpenseGoal.setGoal(200);
        assertEquals(200, testExpenseGoal.getGoal());

        testExpenseGoal.setCurrent(testListOne);
        assertEquals(testListOne, testExpenseGoal.getCurrent());

        testExpenseGoal.setMonth(10);
        assertEquals(10, testExpenseGoal.getMonth());
    }

    @Test
    void testGetters() {
        MonthlyExpenseGoal testExpenseGoalTwo = new MonthlyExpenseGoal(50);
        testExpenseGoalTwo.setCurrent(testListOne);
        testExpenseGoalTwo.setMonth(12);

        assertEquals(50, testExpenseGoalTwo.getGoal());
        assertEquals(testListOne, testExpenseGoalTwo.getCurrent());
        assertEquals(12, testExpenseGoalTwo.getMonth());
    }


}
