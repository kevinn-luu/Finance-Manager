package model;

import org.json.JSONObject;

// import java.util.*;

//represents an expense with information about the amount, what was purchased, the date of purchase, 
//and month of purchase
public class Expense {
    private double amount;
    private String description;
    private String date;
    private int month;

    //EFFECTS: creates instance of Expense class with provided amount, description, date, and month
    public Expense(double amount, String description, String date, int month) {
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.month = month;
    }

    //EFFECTS: returns the expense amount
    public double getAmount() {
        return this.amount;
    }

    //EFFECTS: returns the expense description
    public String getDescription() {
        return this.description;
    }

    //EFFECTS: returns the expense date
    public String getDate() {
        return this.date;
    }

    //EFFECTS: returns the expense month
    public int getMonth() {
        return this.month;
    }

    //REQUIRES: amount >= 0
    //MODIFIES: this
    //EFFECTS: changes amount of money to the specified amount
    public void setAmount(double amount) {
        this.amount = amount;
        
    }

    //MODIFIES: this
    //EFFECTS: changes description to the inputed description
    public void setDescription(String description) {
        this.description = description;
    }

    //MODIFIES: this
    //EFFECTS: changes date to the inputed date
    public void setDate(String date) {
        this.date = date;
    }

    //REQUIRES: 1 <= month <= 12
    //MODIFIES: this
    //EFFECTS: changes month to the inputed month
    public void setMonth(int month) {
        this.month = month;
    }

    //EFFECTS: converts expense into json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("amount", amount);
        json.put("description", description);
        json.put("date", date);
        json.put("month", month);
        return json;
    }
}
