package ui;

// import model.Earning;
import model.Expense;
import model.MonthlyExpenseGoal;
import persistence.ExpenseJsonReader;
import persistence.ExpenseJsonWriter;
import persistence.MonthlyGoalJsonReader;
import persistence.MonthlyGoalJsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// A finance manager application which has expenses and a monthly expense goal
public class FinanceManagerConsole {

    private static String FILE_PATH_EXPENSES = "./data/expenses.json";
    private static String FILE_PATH_GOAL = "./data/monthlygoal.json";

    private List<Expense> expenses;
    private MonthlyExpenseGoal monthlyGoal;
    
    private Scanner scanner;
    private boolean running;

    private ExpenseJsonReader expenseJsonReader;
    private ExpenseJsonWriter expenseJsonWriter;
    private MonthlyGoalJsonReader monthlyJsonReader;
    private MonthlyGoalJsonWriter monthlyJsonWriter;

    //EFFECTS: creates a new instance of the entire application
    public FinanceManagerConsole() {
        createFinanceManager();
        System.out.println("Welcome to your Finance Manager!");

        while (running) {
            handleOptions();
        }
        
    }

    //MODIFIES: this
    //EFFECTS: sets the initial values of the application
    //         expenses is an empty list,
    //         the monthly goal is set to 0
    //         the program running state is set to true
    public void createFinanceManager() {
        this.expenses = new ArrayList<>();
        this.monthlyGoal = new MonthlyExpenseGoal(0);
        this.running = true;
        this.scanner = new Scanner(System.in);
        this.expenseJsonWriter = new ExpenseJsonWriter(FILE_PATH_EXPENSES);
        this.expenseJsonReader = new ExpenseJsonReader(FILE_PATH_EXPENSES);
        this.monthlyJsonReader = new MonthlyGoalJsonReader(FILE_PATH_GOAL);
        this.monthlyJsonWriter = new MonthlyGoalJsonWriter(FILE_PATH_GOAL);
    }

    //EFFECTS: menu options for the user
    public void displayOptions() {
        System.out.println(menuDivider());
        System.out.println("Input a new expense (n)");
        System.out.println("View monthly goal (g)");
        System.out.println("View all expenses (a)");
        System.out.println("View all expenses for a given day (d)");
        System.out.println("View all expenses for a given month (m)");
        System.out.println("Edit an expense (e)");
        System.out.println("Delete an expense (z)");
        System.out.println("Save current expenses to file (s)");
        System.out.println("Load expenses from file (l)");
        System.out.println("Quit (x)");
        System.out.println(menuDivider());
    }

    //EFFECTS: displays menu options and prompts user for choice
    public void handleOptions() {
        displayOptions();
        String userChoice = this.scanner.nextLine();
        processUserChoice(userChoice);
    }

    //EFFECTS: processes user choice
    @SuppressWarnings("methodlength")
    public void processUserChoice(String userChoice) {
        switch (userChoice) {
            case "n":
                addExpense();
                break;
            case "g":
                monthlyGoalMenu();
                break;
            case "a":
                viewAllExpenses();
                break;
            case "d":
                displayExpensesForDate();
                break;
            case "m":
                displayExpensesForMonth();
                break;
            case "e":
                editExpenseMenu();
                break;
            case "z":
                deleteExpenseMenu();
                break;
            case "s":
                saveExpenses();
                break;
            case "l":
                loadExpenses();
                break;
            case "x":
                this.running = false;
                break;
            default:
                System.out.println("Please enter a valid option");
        }
    }

    //EFFECTS: adds new expense to the program
    public void addExpense() {

        System.out.println("Amount spent($):");
        String amountString = this.scanner.nextLine();
        //convert to double
        double amountDouble = Double.parseDouble(amountString);

        System.out.println("\nDescription:");
        String description = this.scanner.nextLine();

        System.out.println("\nDate:");
        String date = this.scanner.nextLine();

        System.out.println("\nMonth(1-12):");
        String monthString = this.scanner.nextLine();
        int monthInt = Integer.parseInt(monthString);

        Expense expense = new Expense(amountDouble, description, date, monthInt);
        this.expenses.add(expense);
        System.out.println("Expense added");
    }

    //EFFECTS: handles user input for monthly goal options
    public void processMonthlyGoalInputs(String userInput) {
        switch (userInput) {
            case "s":
                setMonthlyGoal();
                break;
            case "v":
                viewMonthlyGoal();
                break;
            case "c":
                saveMonthlyGoal();
                break;
            case "l":
                loadMonthlyGoal();
                break;
            case "e":
                editMonthlyGoal();
                break;
            case "r":
                resetMonthlyGoal();
                break;
            default:
                System.out.println("Please enter a valid option");
        }
    }   

    //EFFECTS: set a monthly goal for a particular month
    public void setMonthlyGoal() {
        System.out.println("Enter your goal($):");
        String goalString = this.scanner.nextLine();
        double goalDouble = Double.parseDouble(goalString);

        System.out.println("What month(1-12) is this goal for?");
        String monthString = this.scanner.nextLine();
        int monthInt = Integer.parseInt(monthString);

        this.monthlyGoal = new MonthlyExpenseGoal(goalDouble);
        this.monthlyGoal.setMonth(monthInt);
        System.out.println("Your goal has been set!");
       
    }

    //EFFECTS: editing monthly goal
    public void editMonthlyGoal() {
        System.out.println("Enter the new month of goal (1-12):");
        String monthString = this.scanner.nextLine();
        int month = Integer.parseInt(monthString);

        System.out.println("Enter the new amount($):");
        String amountString = this.scanner.nextLine();
        double amount = Double.parseDouble(amountString);

        this.monthlyGoal.setMonth(month);
        this.monthlyGoal.setGoal(amount);
    }

    //EFFECTS: resetting monthly goal
    public void resetMonthlyGoal() {
        System.out.println("Are you sure you want to reset your goal? (You will need to make a new one) y/n");
        String choice = this.scanner.nextLine();

        switch (choice) {
            case "y":
                this.monthlyGoal.setGoal(0);
                this.monthlyGoal.setMonth(0);
                break;
            default:
                System.out.println("Returning to menu");
                break;
        }
    }

    //EFFECTS: displays menu for monthly goal
    public void monthlyGoalMenu() {
        monthlyGoalOptions();
        String userInput = this.scanner.nextLine();
        processMonthlyGoalInputs(userInput);
    }

    //EFFECTS: after setting monthly goal, displays options
    public void monthlyGoalOptions() {
        System.out.println(menuDivider());
        System.out.println("View your current goal and progress (v)");
        System.out.println("Set monthly goal (s)");
        System.out.println("Edit monthly goal (e)");
        System.out.println("Reset monthly goal (r)");
        System.out.println("Save your current monthly goal to fie (c)");
        System.out.println("Load your saved monthly goal from file (l)");
        System.out.println(menuDivider());
    }

    //EFFECTS: displays current goal if user has not set one yet
    public void viewMonthlyGoal() {
        if (this.monthlyGoal.getMonth() == 0) {
            System.out.println("You do not have a goal currently set");
        } else {
            System.out.println("Your goal for " + translateMonth(this.monthlyGoal.getMonth()) + " is: " 
                    + this.monthlyGoal.getGoal());

            //displays all expenses for the month set
            displayExpensesForMonthlyGoal();

            //displays progress so far - if goal is passed, notifies user
            displayGoalProgressSoFar();
        }
    }

    //EFFECTS: display all expenses
    public void viewAllExpenses() {
        if (this.expenses.isEmpty()) {
            System.out.println("You currently have no expenses added");
        } else {
            displayExpenses(this.expenses);
        }
    }

    //EFFECTS: tells the user how much the've spent in relation to their goal
    //         will notify the user if their goal is reached or passed
    public void displayGoalProgressSoFar() {
        System.out.println("The total amount spent this month is $" 
                + this.monthlyGoal.calculateCurrentAmount());

        if (this.monthlyGoal.calculateCurrentAmount() >= this.monthlyGoal.getGoal()) {
            System.out.println("Stop spending, you have reached or have surpassed your limit");
        }   
    }

    //EFFECTS: display all expenses contributing to the monthly goal
    public void displayExpensesForMonthlyGoal() {
        addExpensesForMonthlyGoal();
        if (this.monthlyGoal.getCurrent().isEmpty()) {
            System.out.println("No expenses for this month");
        } else {
            displayExpenses(this.monthlyGoal.getCurrent());
        }
    }

    //EFFECTS: add expenses for the monthly goal
    public void addExpensesForMonthlyGoal() {
        for (Expense expense : this.expenses) {
            if (expense.getMonth() == this.monthlyGoal.getMonth() 
                    && !this.monthlyGoal.getCurrent().contains(expense)) {
                this.monthlyGoal.addExpense(expense);
            }
        }
    }   

    //EFFECTS: display all expenses for a given month
    public void displayExpensesForMonth() {
        System.out.println("Input the month(1-12) for which you would like to see your expenses:");
        String userMonthStr = this.scanner.nextLine();
        int userMonthInt = Integer.parseInt(userMonthStr);

        List<Expense> expensesForMonth = new ArrayList<>();

        for (Expense expense : this.expenses) {
            if (expense.getMonth() == userMonthInt) {
                expensesForMonth.add(expense);
            }
        }

        System.out.println("Your expenses for " + translateMonth(userMonthInt) + " are");
        displayExpenses(expensesForMonth);
        System.out.println("Total expenses: " + calculateExpenses(expensesForMonth));
    }   

    //EFFECTS: display all expenses for a specified date
    public void displayExpensesForDate() {
        System.out.println("Input the date for which you would like to see your expenses:");
        String userDate = this.scanner.nextLine();

        List<Expense> expensesForDate = new ArrayList<>();

        for (Expense expense : this.expenses) {
            if (expense.getDate().equals(userDate)) {
                expensesForDate.add(expense);
            }
        }

        System.out.println("Your expenses for " + userDate + " are");
        displayExpenses(expensesForDate);
        System.out.println("Total expenses: " + calculateExpenses(expensesForDate));

    }

    //EFFECTS: displays menu for editing expense, triggers edit specific expense function
    public void editExpenseMenu() {
        System.out.println("Here are all your current expenses: ");
        viewAllExpenses();
        System.out.println("Enter the number of the expense number to edit:");
        String userInput = this.scanner.nextLine();
        int userChoice = Integer.parseInt(userInput);

        editSpecificExpense(userChoice);
    }

    //EFFECTS: handles user input to edit a specific expense
    public void editSpecificExpense(int expenseNumber) {
        Expense selectedCard = this.expenses.get(expenseNumber - 1);
        displayEditOptions();
        String userChoice = this.scanner.nextLine();
        processEditChoice(userChoice, selectedCard);
    }

    //EFFECTS: displays options for editing an expense
    public void displayEditOptions() {
        System.out.println(menuDivider());
        System.out.println("Edit amount (a)");
        System.out.println("Edit description (d)");
        System.out.println("Edit date (t)");
        System.out.println("Edit month (m)");
        System.out.println("Edit entire expense (w)");
        System.out.println(menuDivider());
    }

    //EFFECTS: handles user input for editing an expense
    public void processEditChoice(String input, Expense expense) {
        switch (input) {
            case "a":
                editExpenseAmount(expense);
                break;
            case "d":
                editExpenseDescription(expense);
                break;
            case "t":
                editExpenseDate(expense);
                break;
            case "m":
                editExpenseMonth(expense);
                break;
            case "w":
                editWholeExpense(expense);
            default:
                break;
        }
    }

    //EFFECTS: handles user input for editing e's amount
    public void editExpenseAmount(Expense e) {
        System.out.println("Enter the new amount: ");
        String input = this.scanner.nextLine();
        Double newAmount = Double.parseDouble(input);
        e.setAmount(newAmount);
    }   

    //EFFECTS: handles user input for editing e's description
    public void editExpenseDescription(Expense e) {
        System.out.println("Enter a new description: ");
        String input = this.scanner.nextLine();
        e.setDescription(input);
    }

    //EFFECTS: handles user input for editing e's date
    public void editExpenseDate(Expense e) {
        System.out.println("Enter a new date: ");
        String input = this.scanner.nextLine();
        e.setDate(input);
    }

    //EFFECTS: handles user input for editing e's month
    public void editExpenseMonth(Expense e) {
        System.out.println("Enter a new month(1-12): ");
        String input = this.scanner.nextLine();
        int newMonth = Integer.parseInt(input);
        e.setMonth(newMonth);
    }

    //EFFECTS: handles user input for editing all fields of e
    public void editWholeExpense(Expense e) {
        System.out.println("Enter a new amount: ");
        String amountInput = this.scanner.nextLine();
        double newAmount = Double.parseDouble(amountInput);

        System.out.println("Enter a new description: ");
        String newDescription = this.scanner.nextLine();

        System.out.println("Enter a new date: ");
        String newDate = this.scanner.nextLine();

        System.out.println("Enter a new month(1-12)");
        String monthInput = this.scanner.nextLine();
        int newMonth = Integer.parseInt(monthInput);

        e.setAmount(newAmount);
        e.setDescription(newDescription);
        e.setDate(newDate);
        e.setMonth(newMonth);
    }

    //EFFECTS: displays options for deleting an expense
    public void deleteExpenseMenu() {
        System.out.println("Here are all your current expenses: ");
        viewAllExpenses();
        System.out.println("Enter the number of the expense number to delete:");
        String userInput = this.scanner.nextLine();
        int userChoice = Integer.parseInt(userInput);

        deleteSpecificExpense(userChoice);
    }

    //EFFECTS: displays options and handles input for deleting expenses by number
    public void deleteSpecificExpense(int expenseNumber) {
        Expense selectedExpense = this.expenses.get(expenseNumber - 1);
        System.out.println("This is the expense you have selected:");
        System.out.println(expenseDivider());
        displayExpense(selectedExpense);
        System.out.println(expenseDivider());
        System.out.println("Are you sure you want to delete? (y/n)");
        String choice = this.scanner.nextLine();

        switch (choice) {
            case "y":
                this.expenses.remove(expenseNumber - 1);
                System.out.println("Expense successfully deleted");
                break;
            
            default:
                System.out.println("Returning to menu");
                break;
        }
    }

    //EFFECTS: prints out a line to separate expense instances from each other when expenses are being viewed
    public String expenseDivider() {
        return "=======================";
    }

    //EFFECTS: prints out a line to contain the menu
    public String menuDivider() {
        return "-----------------------";
    }

    //EFFECTS: ui component to display expenses
    public void displayExpenses(List<Expense> expenses) {
        for (Expense expense : expenses) {
            System.out.println(expenseDivider());
            System.out.println("Expense #: " + (expenses.indexOf(expense) + 1));
            displayExpense(expense);
            System.out.println(expenseDivider());
        }
    }

    //EFFECTS: displays one expense
    public void displayExpense(Expense e) {
        System.out.println("Amount spent($): " + e.getAmount());
        System.out.println("Description: " + e.getDescription());
        System.out.println("Date: " + e.getDate());
        System.out.println("Month: " + translateMonth(e.getMonth()));
    }

    //REQUIRES: 1 <= month <= 12
    //EFFECTS: returns the corressponding month 
    public String translateMonth(int month) {
        List<String> months = new ArrayList<>(Arrays.asList(
                "January", "February", "March", "April", "May", "June", "July",
                "August", "Septemeber", "October", "November", "December"
        ));

        return months.get(month - 1);
    }

    //EFFECTS: calculates total amount of expenses
    public double calculateExpenses(List<Expense> expenses) {
        if (expenses.isEmpty()) {
            return 0.0;
        } else {
            double total = 0.0;
            for (Expense expense : expenses) {
                total += expense.getAmount();
            }
            return total;
        }
    }   

    // EFFECTS: saves expenses currently in manager to file
    private void saveExpenses() {
        try {
            expenseJsonWriter.open();
            expenseJsonWriter.write(expenses);
            expenseJsonWriter.close();
            System.out.println("Saved all expenses to " + FILE_PATH_EXPENSES);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save expenses, file not found");
        }
    }

    // EFFECTS: loads expenses from file to manager
    private void loadExpenses() {
        try {
            expenses = expenseJsonReader.read();
            System.out.println("Loaded expenses from " + FILE_PATH_EXPENSES);
        } catch (IOException e) {
            System.out.println("Unable to read expenses from file " + FILE_PATH_EXPENSES);
        }
    }

    // EFFECTS: saves current monthly goal amount and month to file
    private void saveMonthlyGoal() {
        try {
            if (this.monthlyGoal.getMonth() == 0) {
                System.out.println("You do not have a goal currently set");
            } else {
                monthlyJsonWriter.open();
                monthlyJsonWriter.write(monthlyGoal);
                monthlyJsonWriter.close();
                System.out.println("Saved goal to " + FILE_PATH_GOAL);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save goal, file not found");
        }
    }   

    // EFFECTS: loads monthly goal from month to file
    private void loadMonthlyGoal() {
        try {
            monthlyGoal = monthlyJsonReader.read();
            System.out.println("Loaded goal from " + FILE_PATH_GOAL);
        } catch (IOException e) {
            System.out.println("unable to read goal from file " + FILE_PATH_GOAL);
        }
    }
}
