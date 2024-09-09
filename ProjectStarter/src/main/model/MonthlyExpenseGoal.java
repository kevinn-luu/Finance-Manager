package model;

import java.util.*;

import org.json.JSONObject;

//Represents a monthly expense goal with a list of expenses that contribute to the goal, an
//indicator of whether or not the goal is reached, and the month (1-12) that the goal is for
public class MonthlyExpenseGoal {
    private double goal;
    private List<Expense> current;
    private int month;

    //EFFECTS: creates an expense goal object with the specified goal, an empty list of expenses, and goalReached
    //         set to false
    public MonthlyExpenseGoal(double goal) {
        this.goal = goal;
        current = new ArrayList<>();
        this.month = 0;
    }

    //MODIFIES: this
    //EFFECTS: adds expense to this.current
    public void addExpense(Expense expense) {
        this.current.add(expense);
    }

    //EFFECTS: sums up total amount of expenses in this.current
    public double calculateCurrentAmount() {
        double totalAmount = 0;
        for (Expense expense : this.current) {
            totalAmount += expense.getAmount();
        }
        return totalAmount;
    }

    //EFFECTS: returns true if calculateCurrentAmount() >= this.goal
    public boolean checkIfGoalReached() {
        if (this.calculateCurrentAmount() >= this.goal) {
            return true;
        } else {
            return false;
        }
    }

    //REQUIRES: goal > getCurrentAmount()
    //MODIFIES: this
    //EFFECTS: changes current goal to goal
    public void setGoal(double goal) {
        this.goal = goal;
    }

    //MODIFIES: this
    //EFFECTS: sets current list of expenses to expenses
    public void setCurrent(List<Expense> expenses) {
        this.current = expenses;
    }

    //MODIFIES: this
    //EFFECTS: changes month of goal to month
    public void setMonth(int month) {
        this.month = month;
    }

    //EFFECTS: gets current goal
    public double getGoal() {
        return this.goal;
    }

    //EFFECTS: gets list of current expenses 
    public List<Expense> getCurrent() {
        return this.current;
    }

    //EFFECTS: returns the current month the goal is set for
    public int getMonth() {
        return this.month;
    }

    //EFFECTS: converts this into json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("amount", goal);
        json.put("month", month);
        return json;
    }
}

