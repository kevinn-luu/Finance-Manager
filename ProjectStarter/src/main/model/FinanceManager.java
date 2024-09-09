package model;

import java.util.*;

// Represents a FinanceManager class which holds a collection of expenses and a monthly goal
public class FinanceManager {
    private List<Expense> expenses;
    private MonthlyExpenseGoal monthlyGoal;

    // EFFECTS: constructs an instance of the FinanceManager class with an 
    //          empty list of expenses and a monthly goal set to 0 for month 0
    public FinanceManager() {
        this.expenses = new ArrayList<>();
        this.monthlyGoal = new MonthlyExpenseGoal(0);
    }

    // MODIFIES: this
    // EFFECTS: adds an expense to expenses
    public void addExpense(Expense e) {
        this.expenses.add(e);
        EventLog.getInstance().logEvent(new Event("Expense has been added."));
    }

    // REQUIRES: !this.expenses.isEmpty() and this.expenses.contains(e)
    // MODIFES: this
    // EFFECTS: removes an expense from expenses
    public void removeExpense(Expense e) {
        this.expenses.remove(e);
        EventLog.getInstance().logEvent(new Event("Expense has been removed."));
    }

    // REQUIRES: valid date string in the form of Month day i.e. October 5
    // EFFECTS: returns a new list of expenses that have the same date as date
    public List<Expense> sortExpensesByDate(String date) {
        List<Expense> expForDate = new ArrayList<>();
        
        for (Expense e : this.expenses) {
            if (e.getDate().equals(date)) {
                expForDate.add(e);
            }
        }

        EventLog.getInstance().logEvent(new Event("Expenses have been sorted by date."));
        return expForDate;
    }

    // REQUIRES: valid month number (1-12)
    // EFFECTS: returns a new list of expenses that have the same month as month
    public List<Expense> sortExpensesByMonth(int month) {
        List<Expense> expForMonth = new ArrayList<>();

        for (Expense e : this.expenses) {
            if (e.getMonth() == month) {
                expForMonth.add(e);
            }
        }

        EventLog.getInstance().logEvent(new Event("Expenses have been sorted by month"));
        return expForMonth;
    }

    // EFFECTS: returns this.expenses
    public List<Expense> getExpenses() {
        return this.expenses;
    }

    // EFFECTS: produces a list of expenses that contribute towards the current
    //          monthly goal
    public List<Expense> getExpensesForGoal() {
        int month = this.monthlyGoal.getMonth();
        List<Expense> expForGoal = new ArrayList<>();
        
        for (Expense e : this.expenses) {
            if (e.getMonth() == month) {
                expForGoal.add(e);
            }
        }

        EventLog.getInstance().logEvent(new Event("Returned all expenses for monthly goal."));
        return expForGoal;
    }

    // EFFECTS: returns the current expense goal object
    public MonthlyExpenseGoal getMonthlyExpenseGoal() {
        return this.monthlyGoal;
    }

    // EFFECTS: sets current goal to mg
    public void setMonthlyGoal(MonthlyExpenseGoal mg) {
        this.monthlyGoal = mg;
        EventLog.getInstance().logEvent(new Event("Monthly goal has been updated."));
    }

    // EFFECTS: sets current expenses to loe
    public void setExpenses(List<Expense> loe) {
        this.expenses = loe;
    }

}
