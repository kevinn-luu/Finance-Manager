package ui;

import model.FinanceManager;
import model.Event;
import model.EventLog;
import model.Expense;
import model.MonthlyExpenseGoal;
import persistence.ExpenseJsonReader;
import persistence.ExpenseJsonWriter;
import persistence.MonthlyGoalJsonReader;
import persistence.MonthlyGoalJsonWriter;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

// A class representing a FinanceManager with GUI components
public class FinanceManagerGUI extends JFrame implements ActionListener {

    private static String FILE_PATH_EXPENSES = "./data/expensesgui.json";
    private static String FILE_PATH_GOAL = "./data/monthlygoalgui.json";

    private FinanceManager financeManager;

    private ExpenseJsonReader expenseJsonReader;
    private ExpenseJsonWriter expenseJsonWriter;
    private MonthlyGoalJsonReader monthlyJsonReader;
    private MonthlyGoalJsonWriter monthlyJsonWriter;

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 1000;

    private JPanel mainPanel;
    private JPanel expensePanel;
    private JPanel allExpensesPanel;
    private JPanel monthlyGoalPanel;
    private JPanel editDeleteExpensePanel;
    private JPanel viewProgressPanel;

    private JTextField amountField;
    private JTextField descriptionField;
    private JTextField dateField;
    private JTextField monthField;

    private JTextField expenseNumber;
    private JTextField editAmountField;
    private JTextField editDescriptionField;
    private JTextField editDateField;
    private JTextField editMonthField;

    private JTextField deleteExpenseNumber;

    private JTextField sortByDate;
    private JTextField sortByMonth;

    private JTextField goalAmount;
    private JTextField goalMonth;

    private JTextArea allExpenses;
    private JTextArea monthlyGoalExpenses;
    private JTextArea editDeleteExpenseArea;

    // EFFECTS: Create new instance of FinanceManager with GUI
    public FinanceManagerGUI() {
        super("Finance Manager GUI");

        this.financeManager = new FinanceManager();

        setupReadersWriters();

        setSize(WIDTH, HEIGHT);

        initializePanels();

        setupPanels();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                String eventBuilder = "";
                for (Event event : EventLog.getInstance()) {
                    eventBuilder += event.getDate() + " " + event.getDescription() + "\n" + "\n";
                }
                System.out.println(eventBuilder);
                dispose();
            }
        });

        add(mainPanel);
        setVisible(true);

    }

    // EFFECTS: sets up all of the panels in the application
    private void setupPanels() {
        setupPanelMainPanel(mainPanel);
        setupPanelExpensePanel(expensePanel);
        setupAllExpensesPanel(allExpensesPanel);
        setupMonthlyGoalPanel(monthlyGoalPanel);
        setupEditDeleteExpensePanel(editDeleteExpensePanel);
        setupViewProgressPanel(viewProgressPanel);
    }

    // EFFECTS: sets up JSON readers and writers for storing and loading data
    private void setupReadersWriters() {
        expenseJsonReader = new ExpenseJsonReader(FILE_PATH_EXPENSES);
        expenseJsonWriter = new ExpenseJsonWriter(FILE_PATH_EXPENSES);
        monthlyJsonReader = new MonthlyGoalJsonReader(FILE_PATH_GOAL);
        monthlyJsonWriter = new MonthlyGoalJsonWriter(FILE_PATH_GOAL);
    }

    // EFFECTS: initializes up all the different panels in the program
    private void initializePanels() {
        this.mainPanel = new JPanel();
        this.expensePanel = new JPanel();
        this.allExpensesPanel = new JPanel();
        this.monthlyGoalPanel = new JPanel();
        this.editDeleteExpensePanel = new JPanel();
        this.viewProgressPanel = new JPanel();
    }

    // EFFECTS: sets up the panel where expenses can be edited and deleted
    private void setupEditDeleteExpensePanel(JPanel jp) {
        jp.setLayout(null);

        setupBackButton(jp);
        setupBackToAddExpenseButton(jp);

        editDeleteExpenseArea = new JTextArea(80, 40);
        editDeleteExpenseArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(editDeleteExpenseArea);
        scroll.setBounds(500, 175, 400, 400);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        setupEditExpenseComponents(jp);
        setupDeleteExpenseComponents(jp);

        jp.add(scroll);
    }

    // EFFECTS: creates fields and confirm for deleting an expense
    public void setupDeleteExpenseComponents(JPanel jp) {
        createDeleteComponent(jp);
        createConfirmDeleteButton(jp);
    }

    // EFFECTS: creates field for selecting an expense to delete
    public void createDeleteComponent(JPanel jp) {
        JLabel delete = new JLabel("Enter the expense to delete (expense number):");
        delete.setBounds(200, 475, 275, 25);

        this.deleteExpenseNumber = new JTextField(10);
        this.deleteExpenseNumber.setBounds(200, 500, 275, 25);

        jp.add(delete);
        jp.add(this.deleteExpenseNumber);
    }

    // EFFECTS: creates confirm button for deleting an expense
    public void createConfirmDeleteButton(JPanel jp) {
        JButton deleteButton = new JButton("Confirm delete");
        deleteButton.setBounds(200, 525, 275, 25);
        deleteButton.addActionListener(this);
        deleteButton.setActionCommand("Delete expense");

        jp.add(deleteButton);
    }

    // EFFECTS: sets up fields and confirm for editing expenses
    public void setupEditExpenseComponents(JPanel jp) {
        createEnterNumberComponent(jp);
        createEditAmountComponent(jp);
        createEditDescriptionComponent(jp);
        createEditDateComponent(jp);
        createEditMonthComponent(jp);
        createConfirmEditButton(jp);
    }

    // EFFECTS: sets up a button to confirm and make the edit
    public void createConfirmEditButton(JPanel jp) {
        JButton confirmEdit = new JButton("Confirm edit");
        confirmEdit.setBounds(200, 425, 275, 25);
        confirmEdit.addActionListener(this);
        confirmEdit.setActionCommand("Confirm edit");

        jp.add(confirmEdit);
    }

    // EFFECTS: sets up field for entering the new month
    public void createEditMonthComponent(JPanel jp) {
        JLabel enterMonth = new JLabel("Enter the new month (1-12)");
        enterMonth.setBounds(200, 375, 275, 25);

        this.editMonthField = new JTextField(10);
        this.editMonthField.setBounds(200, 400, 275, 25);

        jp.add(enterMonth);
        jp.add(this.editMonthField);
    }

    // EFFECTS: sets up field for entering the new date
    public void createEditDateComponent(JPanel jp) {
        JLabel enterDate = new JLabel("Enter the new date (i.e. October 5)");
        enterDate.setBounds(200, 325, 275, 25);

        this.editDateField = new JTextField(10);
        this.editDateField.setBounds(200, 350, 275, 25);

        jp.add(enterDate);
        jp.add(this.editDateField);
    }

    // EFFECTS: sets up field for entering the new description
    public void createEditDescriptionComponent(JPanel jp) {
        JLabel enterDescription = new JLabel("Enter the new description:");
        enterDescription.setBounds(200, 275, 275, 25);

        this.editDescriptionField = new JTextField(10);
        this.editDescriptionField.setBounds(200, 300, 275, 25);

        jp.add(enterDescription);
        jp.add(this.editDescriptionField);
    }

    // EFFECTS: sets up field for entering new edit amount
    public void createEditAmountComponent(JPanel jp) {
        JLabel enterAmount = new JLabel("Enter the new amount:");
        enterAmount.setBounds(200, 225, 275, 25);

        this.editAmountField = new JTextField(10);
        this.editAmountField.setBounds(200, 250, 275, 25);

        jp.add(enterAmount);
        jp.add(this.editAmountField);
    }

    // EFFECTS: sets up field for selecting an expense to edit
    public void createEnterNumberComponent(JPanel jp) {
        JLabel enterNumber = new JLabel("Enter the expense to edit (expense number):");
        enterNumber.setBounds(200, 175, 275, 25);

        this.expenseNumber = new JTextField(10);
        this.expenseNumber.setBounds(200, 200, 275, 25);

        jp.add(this.expenseNumber);
        jp.add(enterNumber);
    }

    // EFFECTS: sets up the panel where monthly goal information is managed
    public void setupMonthlyGoalPanel(JPanel jp) {
        jp.setLayout(null);

        setupBackButton(jp);
        setupBackToAddExpenseButton(jp);
        setupViewGoalProgressButton(jp);

        setupCreateGoalComponents(jp);
    }

    // EFFECTS: sets up the panel to view monthly goal progress
    public void setupViewProgressPanel(JPanel jp) {
        jp.setLayout(null);

        setupBackButton(jp);
        setupBackToMonthlyGoalButton(jp);
        setupSaveAndLoadGoal(jp);

        this.monthlyGoalExpenses = new JTextArea(80, 40);
        this.monthlyGoalExpenses.setEditable(false);
        JScrollPane scroll = new JScrollPane(this.monthlyGoalExpenses);
        scroll.setBounds(400, 175, 400, 400);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jp.add(scroll);
    }

    // EFFECTS: buttons for saving and loading monthly goal information
    public void setupSaveAndLoadGoal(JPanel jp) {
        JButton saveGoal = new JButton("Save goal");
        saveGoal.setBounds(400, 575, 200, 25);

        saveGoal.addActionListener(this);
        saveGoal.setActionCommand("Save current goal");

        JButton loadGoal = new JButton("Load goal");
        loadGoal.setBounds(600, 575, 200, 25);

        loadGoal.addActionListener(this);
        loadGoal.setActionCommand("Load goal");

        jp.add(saveGoal);
        jp.add(loadGoal);
    }

    // EFFECTS: sets up button to go back to the monthly goal page
    public void setupBackToMonthlyGoalButton(JPanel jp) {
        JButton backToGoal = new JButton("Back to goal page");
        backToGoal.setBounds(300, 0, 275, 25);

        backToGoal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPage(monthlyGoalPanel);
            }
        });

        jp.add(backToGoal);
    }

    // EFFECTS: sets up the button to view goal progress
    public void setupViewGoalProgressButton(JPanel jp) {
        JButton viewGoal = new JButton("View goal progress");
        viewGoal.setBounds(550, 0, 275, 25);

        viewGoal.addActionListener(this);
        viewGoal.setActionCommand("View goal");

        jp.add(viewGoal);
    }

    // EFFECTS: setup fields and submit for creating a monthly goal
    public void setupCreateGoalComponents(JPanel jp) {
        JLabel enterMonth = new JLabel("Enter the month for your goal (1-12):");
        enterMonth.setBounds(463, 175, 275, 25);

        this.goalMonth = new JTextField(10);
        this.goalMonth.setBounds(463, 200, 275, 25);

        JLabel enterAmount = new JLabel("Enter the limit for the month:");
        enterAmount.setBounds(463, 225, 275, 25);

        this.goalAmount = new JTextField(10);
        this.goalAmount.setBounds(463, 250, 275, 25);

        jp.add(this.goalMonth);
        jp.add(enterMonth);
        jp.add(this.goalAmount);
        jp.add(enterAmount);

        createGoalButton(jp);
    }

    // EFFECTS: creates a button for submitting goals
    public void createGoalButton(JPanel jp) {
        JButton createGoal = new JButton("Create goal");
        createGoal.setBounds(463, 275, 275, 25);

        createGoal.addActionListener(this);
        createGoal.setActionCommand("Create goal");

        jp.add(createGoal);
    }

    // EFFECTS: sets up the panel where all expenses are displayed
    //
    // scrolling bar to text area was referenced from
    // https://stackoveflow.com/questions/10177183/add-scroll-into-text-area
    public void setupAllExpensesPanel(JPanel jp) {
        jp.setLayout(null);

        setupBackButton(jp);
        setupBackToAddExpenseButton(jp);
        setupSaveAndLoadExpensesButton(jp);
        setupSortExpensesArea(jp);
        setupUndoSortingButton(jp);

        allExpenses = new JTextArea(80, 40);
        allExpenses.setEditable(false);
        JScrollPane scroll = new JScrollPane(allExpenses);
        scroll.setBounds(350, 175, 400, 400);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jp.add(scroll);

    }

    // EFFECTS: sets up the panel
    public void setupPanelMainPanel(JPanel jp) {
        jp.setLayout(null);

        setupExpenseMenuButton(jp);

        // setupMonthlyGoalMenuButton(jp);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    // EFFECTS: sets up the expense panel
    public void setupPanelExpensePanel(JPanel jp) {
        jp.setLayout(null);

        setupBackButton(jp);
        setupViewAllExpensesButton(jp);
        setupMonthlyGoalButton(jp);
        setupEditDeleteButton(jp);

        createExpenseComponents(jp);
    }

    // EFFECTS: sets up a button that takes the user to the page to edit or delete
    // expenses
    public void setupEditDeleteButton(JPanel jp) {
        JButton editDeleteExpense = new JButton("Edit and Delete Expenses");
        editDeleteExpense.setBounds(850, 0, 275, 25);
        editDeleteExpense.addActionListener(this);
        editDeleteExpense.setActionCommand("Switch to edit delete");

        jp.add(editDeleteExpense);
    }

    // EFFECTS: sets up a button that takes the user to monthly goal page when
    // clicked
    public void setupMonthlyGoalButton(JPanel jp) {
        JButton toMonthlyGoal = new JButton("Set and Manage Monthly Goal");
        toMonthlyGoal.setBounds(550, 0, 275, 25);
        toMonthlyGoal.addActionListener(this);
        toMonthlyGoal.setActionCommand("Switch to goal");

        jp.add(toMonthlyGoal);
    }

    // EFFECTS: sets up a button that undos the current sorting and displays all the
    // expenses again
    public void setupUndoSortingButton(JPanel jp) {
        JButton viewAll = new JButton("Undo sorting");
        viewAll.setBounds(775, 375, 150, 25);
        viewAll.addActionListener(this);
        viewAll.setActionCommand("Undo sorting");

        jp.add(viewAll);
    }

    // EFFECTS: sets up inputs and buttons for sorting expenses by day or month
    public void setupSortExpensesArea(JPanel jp) {
        setupSortByDate(jp);
        setupSortByMonth(jp);
    }

    // EFFECTS: sets up text field and confirm button to see all expenses for a
    // specific month
    public void setupSortByMonth(JPanel jp) {
        JLabel month = new JLabel("Sort expenses by month (1-12):");
        month.setBounds(775, 275, 275, 25);

        this.sortByMonth = new JTextField(10);
        this.sortByMonth.setBounds(775, 300, 275, 25);

        JButton sortByMonthButton = new JButton("Sort expenses");
        sortByMonthButton.setBounds(775, 325, 150, 25);
        sortByMonthButton.addActionListener(this);
        sortByMonthButton.setActionCommand("Sort by month");

        jp.add(month);
        jp.add(this.sortByMonth);
        jp.add(sortByMonthButton);
    }

    // EFFECTS: sets up text field and confirm button to see all expenses for a
    // specific date
    public void setupSortByDate(JPanel jp) {
        JLabel date = new JLabel("Sort expenses by date (i.e. October 5):");
        date.setBounds(775, 175, 275, 25);

        this.sortByDate = new JTextField(10);
        this.sortByDate.setBounds(775, 200, 275, 25);

        JButton sortByDateButton = new JButton("Sort expenses");
        sortByDateButton.setBounds(775, 225, 150, 25);
        sortByDateButton.addActionListener(this);
        sortByDateButton.setActionCommand("Sort by date");

        jp.add(date);
        jp.add(this.sortByDate);
        jp.add(sortByDateButton);
    }

    // EFFECTS: creates buttons for saving and loading expenses
    public void setupSaveAndLoadExpensesButton(JPanel jp) {
        JButton saveExpenses = new JButton("Save current expenses");
        saveExpenses.setBounds(25, 175, 275, 25);
        jp.add(saveExpenses);

        saveExpenses.addActionListener(this);
        saveExpenses.setActionCommand("Save expenses to file");

        JButton loadExpenses = new JButton("Load expenses from file");
        loadExpenses.setBounds(25, 225, 275, 25);
        jp.add(loadExpenses);

        loadExpenses.addActionListener(this);
        loadExpenses.setActionCommand("Load expenses from file");
    }

    // EFFECTS: creates button for viewing all expenses
    public void setupViewAllExpensesButton(JPanel jp) {
        JButton allExpenses = new JButton("View all expenses");
        allExpenses.setBounds(275, 0, 250, 25);
        jp.add(allExpenses);

        allExpenses.addActionListener(this);
        allExpenses.setActionCommand("View all expenses");
    }

    // EFFECTS: creates textfields for entering expense information
    public void createExpenseComponents(JPanel jp) {
        createAmountComponent(jp);
        createDescriptionComponent(jp);
        createDateComponent(jp);
        createMonthComponent(jp);
        createConfirmButton(jp);
    }

    // EFFECTS: creates an amount label and amount input field
    public void createAmountComponent(JPanel jp) {
        JLabel amount = new JLabel("Enter amount ($)");
        amount.setBounds(463, 200, 275, 25);
        jp.add(amount);

        this.amountField = new JTextField(10);
        this.amountField.setBounds(463, 225, 275, 25);
        jp.add(this.amountField);
    }

    // EFFECTS: creates a description label and description input field
    public void createDescriptionComponent(JPanel jp) {
        JLabel description = new JLabel("Enter expense description");
        description.setBounds(463, 250, 275, 25);
        jp.add(description);

        this.descriptionField = new JTextField(10);
        this.descriptionField.setBounds(463, 275, 275, 25);
        jp.add(this.descriptionField);
    }

    // EFFECTS: creates a date label and date input field
    public void createDateComponent(JPanel jp) {
        JLabel date = new JLabel("Enter the date of the expense (i.e. January 12)");
        date.setBounds(463, 300, 275, 25);
        jp.add(date);

        this.dateField = new JTextField(10);
        this.dateField.setBounds(463, 325, 275, 25);
        jp.add(this.dateField);
    }

    // EFFECTS: creates a month label and month input field
    public void createMonthComponent(JPanel jp) {
        JLabel month = new JLabel("Enter the month of the expense (1 - 12)");
        month.setBounds(463, 350, 275, 25);
        jp.add(month);

        this.monthField = new JTextField(10);
        this.monthField.setBounds(463, 375, 275, 25);
        jp.add(this.monthField);
    }

    // EFFECTS: creates a confirm button that makes an expense when clicked
    public void createConfirmButton(JPanel jp) {
        JButton createExpense = new JButton("Create Expense");
        createExpense.setBounds(463, 400, 200, 25);
        createExpense.addActionListener(this);
        createExpense.setActionCommand("Create expense");
        jp.add(createExpense);
    }

    // EFFECTS: creates a back to main panel button and adds it to jp
    public void setupBackButton(JPanel jp) {
        JButton home = new JButton("Back to main page");
        home.setBounds(0, 0, 250, 25);

        jp.add(home);

        home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPage(mainPanel);
            }
        });
    }

    // EFFECTS: creates a button which redirects the user back to where they can add
    // expenses
    public void setupBackToAddExpenseButton(JPanel jp) {
        JButton addExpenses = new JButton("Back to Add Expenses");
        addExpenses.setBounds(275, 0, 250, 25);

        jp.add(addExpenses);

        addExpenses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPage(expensePanel);
            }
        });
    }

    // EFFECTS: sets up button to view and manage expenses
    public void setupExpenseMenuButton(JPanel jp) {
        JButton expenseButton = new JButton("View and manage your expenses");
        expenseButton.setBounds(calculateXCenterPosition(expenseButton, 275),
                calculateYCenterPosition(expenseButton, 25), 275, 25);

        jp.add(expenseButton);

        expenseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPage(expensePanel);
            }
        });
    }

    // EFFECTS: set up button to view and manage monthly expense goals
    public void setupMonthlyGoalMenuButton(JPanel jp) {
        JButton monthlyGoalButton = new JButton("View and manage your expense goal");
        monthlyGoalButton.setBounds(463, 520, 275, 25);

        jp.add(monthlyGoalButton);

    }

    // EFFECTS: handles user input when submitting different buttons
    @SuppressWarnings("methodlength")
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Create expense":
                createExpense();
                resetExpenseFields();
                expenseCreatedConfirm();
                break;
            case "View all expenses":
                switchPage(allExpensesPanel);
                displayAllExpenses(this.financeManager.getExpenses());
                break;
            case "Save expenses to file":
                saveCurrentExpenses();
                break;
            case "Load expenses from file":
                loadExpenses();
                break;
            case "Sort by date":
                sortExpensesByDate();
                resetSortByDateField();
                break;
            case "Sort by month":
                sortExpensesByMonth();
                resetSortByMonthField();
                break;
            case "Undo sorting":
                displayAllExpenses(this.financeManager.getExpenses());
                break;
            case "Switch to goal":
                switchPage(monthlyGoalPanel);
                break;
            case "Switch to edit delete":
                switchPage(editDeleteExpensePanel);
                displayAllExpensesToEdit(this.financeManager.getExpenses());
                break;
            case "Confirm edit":
                confirmEditExpense(this.financeManager.getExpenses());
                resetEditFields();
                break;
            case "Delete expense":
                confirmDeleteExpense();
                resetDeleteField();
                break;
            case "Create goal":
                createMonthlyGoal();
                resetMonthlyGoalFields();
                break;
            case "View goal":
                switchPage(viewProgressPanel);
                displayExpensesForGoal(this.financeManager.getExpenses());
                break;
            case "Save current goal":
                saveCurrentGoal();
                // confirmSaveGoal();
                break;
            case "Load goal":
                loadGoal();
                displayExpensesForGoal(this.financeManager.getExpenses());
                break;
            default:
                break;
        }
    }

    // // EFFECTS: displays popup saying that expenses have been saved
    // private void confirmSaveGoal() {
    // JDialog jd = new JDialog();
    // JLabel expensesSaved = new JLabel("Your expenses have been saved!");
    // jd.setBounds(463, 600, 275, 100);

    // jd.add(expensesSaved);

    // jd.setVisible(true);
    // }

    // EFFECTS: displays popup saying that expense has been created
    private void expenseCreatedConfirm() {
        JDialog jd = new JDialog();
        JLabel expenseCreated = new JLabel("Expense has been added!");
        jd.setBounds(463, 500, 250, 100);

        jd.add(expenseCreated);

        jd.setVisible(true);
    }

    // EFFECTS: loads goal from fike
    public void loadGoal() {
        try {
            MonthlyExpenseGoal mg = monthlyJsonReader.read();
            this.financeManager.setMonthlyGoal(mg);
        } catch (IOException e) {
            System.out.println("Unable to load");
        }
    }

    // EFFECTS: saves current monthly goal to file
    public void saveCurrentGoal() {
        try {
            monthlyJsonWriter.open();
            monthlyJsonWriter.write(this.financeManager.getMonthlyExpenseGoal());
            monthlyJsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    // EFFECTS: displays current expenses that go towards monthly goal
    public void displayExpensesForGoal(List<Expense> expenses) {
        List<Expense> expensesForMonth = getExpensesForMonth(expenses);
        StringBuilder expensesDisplay = new StringBuilder();
        if (expensesForMonth.isEmpty()) {
            expensesDisplay.append("You currently have no expenses the month!");
        } else {
            double total = calculateTotal(expensesForMonth);
            helpDisplayExpenses(expensesDisplay, expensesForMonth);
            expensesDisplay.append("\n").append("Your total amount spent is $").append(total);
            MonthlyExpenseGoal mg = this.financeManager.getMonthlyExpenseGoal();
            if (total > mg.getGoal()) {
                expensesDisplay.append("\n")
                        .append("Your goal is $")
                        .append(mg.getGoal())
                        .append(" Stop spending!");
            } else {
                expensesDisplay.append("\n")
                        .append("Your goal is $")
                        .append(mg.getGoal());
            }
        }
        this.monthlyGoalExpenses.setText(expensesDisplay.toString());
    }

    // EFFECTS: helper function to setup string for expense display
    public void helpDisplayExpenses(StringBuilder sb, List<Expense> expenses) {
        for (Expense e : expenses) {
            sb.append(expenseDivider()).append("\n")
                        .append("Expense #: ").append(expenses.indexOf(e) + 1).append("\n")
                        .append("Amount: ").append(e.getAmount()).append("\n")
                        .append("Description: ").append(e.getDescription()).append("\n")
                        .append("Date: ").append(e.getDate()).append("\n")
                        .append("Month: ").append(e.getMonth()).append("\n");
        }
    }

    // EFFECTS: returns expenses that belong to the current monthly goal
    public List<Expense> getExpensesForMonth(List<Expense> expenses) {
        List<Expense> expForMonth = new ArrayList<>();

        for (Expense e : expenses) {
            if (e.getMonth() == this.financeManager.getMonthlyExpenseGoal().getMonth()) {
                expForMonth.add(e);
            }
        }

        return expForMonth;
    }

    // EFFECTS: resets monthly goal creation fields
    public void resetMonthlyGoalFields() {
        this.goalAmount.setText("");
        this.goalMonth.setText("");
    }

    // EFFECTS: creates a monthly goal object
    public void createMonthlyGoal() {
        double amount = Double.parseDouble(this.goalAmount.getText());
        MonthlyExpenseGoal newMonthlyGoal = new MonthlyExpenseGoal(amount);

        int month = Integer.parseInt(this.goalMonth.getText());
        newMonthlyGoal.setMonth(month);

        this.financeManager.setMonthlyGoal(newMonthlyGoal);
    }

    // EFFECTS: resets the delete expense field
    public void resetDeleteField() {
        this.deleteExpenseNumber.setText("");
    }

    // EFFECTS: resets the editing expense fields
    public void resetEditFields() {
        this.expenseNumber.setText("");
        this.editAmountField.setText("");
        this.editDescriptionField.setText("");
        this.editDateField.setText("");
        this.editMonthField.setText("");
    }

    // EFFECTS: confirms deleteting of an expense
    public void confirmDeleteExpense() {
        int enumber = Integer.parseInt(this.deleteExpenseNumber.getText());

        Expense e = financeManager.getExpenses().get(enumber - 1);
        financeManager.removeExpense(e);

        displayAllExpensesToEdit(financeManager.getExpenses());
    }

    // EFFECTS: confirms editing of an expense if the expense is found in expenses
    public void confirmEditExpense(List<Expense> expenses) {
        int enumber = Integer.parseInt(this.expenseNumber.getText());
        double newAmount = Double.parseDouble(this.editAmountField.getText());
        String newDesc = this.editDescriptionField.getText();
        String newDate = this.editDateField.getText();
        int newMonth = Integer.parseInt(this.editMonthField.getText());

        Expense e = expenses.get(enumber - 1);
        e.setAmount(newAmount);
        e.setDescription(newDesc);
        e.setDate(newDate);
        e.setMonth(newMonth);

        displayAllExpensesToEdit(expenses);
    }

    // EFFECTS: sorts and displays expenses given a month
    public void sortExpensesByMonth() {
        int month = Integer.parseInt(this.sortByMonth.getText());
        List<Expense> expensesForMonth = this.financeManager.sortExpensesByMonth(month);
        displayAllExpenses(expensesForMonth);
    }

    // EFFECTS: sorts and displays expenses given a date
    public void sortExpensesByDate() {
        String date = this.sortByDate.getText();
        List<Expense> expensesForDate = this.financeManager.sortExpensesByDate(date);
        displayAllExpenses(expensesForDate);
    }

    // EFFECTS: loads expenses from file
    public void loadExpenses() {
        try {
            List<Expense> loe = expenseJsonReader.read();
            this.financeManager.setExpenses(loe);
        } catch (IOException e) {
            System.out.println("Unable to read expenses from file");
        }

        displayAllExpenses(this.financeManager.getExpenses());
    }

    // EFFECTS: saves current expenses to json file
    public void saveCurrentExpenses() {
        try {
            expenseJsonWriter.open();
            expenseJsonWriter.write(this.financeManager.getExpenses());
            expenseJsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    // EFFECTS: display all expenses in this.expenses onto GUI
    public void displayAllExpenses(List<Expense> expenses) {
        StringBuilder expensesDisplay = new StringBuilder();
        if (expenses.isEmpty()) {
            expensesDisplay.append("You currently have no expenses added!");
        } else {
            double total = calculateTotal(expenses);
            helpDisplayExpenses(expensesDisplay, expenses);
            expensesDisplay.append("\n").append("Your total amount spent is $").append(total);
        }
        this.allExpenses.setText(expensesDisplay.toString());
    }

    // EFFECTS: display all expenses in this.expenses onto for editing purposes
    public void displayAllExpensesToEdit(List<Expense> expenses) {
        StringBuilder expensesDisplay = new StringBuilder();
        if (expenses.isEmpty()) {
            expensesDisplay.append("You currently have no expenses added!");
        } else {
            double total = calculateTotal(expenses);
            helpDisplayExpenses(expensesDisplay, expenses);
            expensesDisplay.append("\n").append("Your total amount spent is $").append(total);
        }
        this.editDeleteExpenseArea.setText(expensesDisplay.toString());
    }

    // EFFECTS: creates an expense objects and adds it to list of expenses.
    // Information about
    // the expense is provided by user input fields in the GUI
    public void createExpense() {
        double amount = Double.parseDouble(this.amountField.getText());
        String description = this.descriptionField.getText();
        String date = this.dateField.getText();
        int month = Integer.parseInt(this.monthField.getText());
        Expense e = new Expense(amount, description, date, month);
        this.financeManager.addExpense(e);
    }

    // EFFECTS: resets text fields to their initial state - is called after the
    // expense has been created
    public void resetExpenseFields() {
        this.amountField.setText("");
        this.descriptionField.setText("");
        this.dateField.setText("");
        this.monthField.setText("");
    }

    // EFFECTS: resets the sort date field to its initial empty state
    public void resetSortByDateField() {
        this.sortByDate.setText("");
    }

    // EFFECTS: resets the sort month field to its initial empty state
    public void resetSortByMonthField() {
        this.sortByMonth.setText("");
    }

    // EFFECTS: switches from the current panel to jp
    public void switchPage(JPanel jp) {
        getContentPane().removeAll();
        getContentPane().add(jp);
        revalidate();
        repaint();
    }

    // EFFECTS: given a button, calculate the X coordinate needed to center the
    // button
    public int calculateXCenterPosition(JButton button, int bwidth) {
        int xpos = (WIDTH - bwidth) / 2;
        return xpos;
    }

    // EFFECTS: given a button, calculate the Y coordinate needed to center the
    // button
    public int calculateYCenterPosition(JButton button, int bheight) {
        int ypos = (HEIGHT - bheight) / 2;
        return ypos;
    }

    // EFFECTS: displays a barrier to separate expenses from one another
    public String expenseDivider() {
        return "=======================";
    }

    // EFFECTS: given a list of expenses, returns the total amount spent across
    // all expenses
    public double calculateTotal(List<Expense> expenses) {
        if (expenses.isEmpty()) {
            return 0.0;
        } else {
            double total = 0.0;
            for (Expense e : expenses) {
                total += e.getAmount();
            }
            return total;
        }
    }

    // EFFECTS: returns a list of expenses of only the ones that were spent on the
    // specified date
    public List<Expense> filterExpensesByDate(String date, List<Expense> expenses) {
        List<Expense> filtered = new ArrayList<>();

        for (Expense e : expenses) {
            if (e.getDate().equals(date)) {
                filtered.add(e);
            }
        }

        return filtered;
    }

    // EFFECTS: returns a list of expenses of only the ones that were spent during
    // the specified month
    public List<Expense> filterExpensesByMonth(int month, List<Expense> expenses) {
        List<Expense> filtered = new ArrayList<>();

        for (Expense e : expenses) {
            if (e.getMonth() == month) {
                filtered.add(e);
            }
        }

        return filtered;
    }

}
