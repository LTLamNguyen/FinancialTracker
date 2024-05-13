package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;


//Create Graphical User Interface for FinancialTrackerApp
public class FinancialTrackerGUI extends JFrame {
    private FinancialTrackerApp financialTrackerApp;
    private JLabel statusLabel;

    //EFFECTS: Create constructor for FinancialTrackerGUI
    public FinancialTrackerGUI() throws FileNotFoundException {
        financialTrackerApp = createFinancialTrackerApp();
        if (financialTrackerApp != null) {
            SwingUtilities.invokeLater(this::initializeGUI);
        } else {
            showErrorDialog("Unable to create FinancialTrackerApp.");
        }

    }

    //EFFECTS: Create FinancialTrackerApp
    private FinancialTrackerApp createFinancialTrackerApp() {
        try {
            return new FinancialTrackerApp();
        } catch (FileNotFoundException e) {
            showErrorDialog("Error: File not found");
            return null;
        }
    }

    //EFFECTS: create Frame
    private JFrame createFrame(String title) {
        return new JFrame(title);
    }

    //EFFECTS: create Panel
    private JPanel createPanel() {
        return new JPanel();
    }

    //EFFECTS: create TextField
    private JTextField createTextField(int columns) {
        return new JTextField(columns);
    }

    //EFFECTS: create ComboBox
    private JComboBox<String> createComboBox(String[] items) {
        return new JComboBox<>(items);
    }

    //EFFECTS: Create Graphical User Interface
    private void initializeGUI() {
        JFrame frame = new JFrame("Financial Tracker");
        JPanel panel = new JPanel();

        addButtonToPanel(panel, "Add Transaction", this::addTransaction);
        addButtonToPanel(panel, "View Transactions", this::viewTransaction);
        addButtonToPanel(panel, "Add Budget", this::addBudget);
        addButtonToPanel(panel, "View Budgets", this::viewBudgets);
        addButtonToPanel(panel, "Add Financial Goal", this::addFinancialGoal);
        addButtonToPanel(panel, "View Financial Goals", this::viewFinancialGoals);
        addButtonToPanel(panel, "Mark Goal as Achieved", this::markGoalAsAchievedAndDelete);
        addButtonToPanel(panel, "Add Financial Reminder", this::addFinancialReminder);
        addButtonToPanel(panel, "View Financial Reminders", this::viewFinancialReminders);
        addButtonToPanel(panel, "Mark Reminder as Completed", this::markReminderAsCompletedAndDelete);
        addButtonToPanel(panel, "Generate and Export Report", this::generateAndExportReport);
        addButtonToPanel(panel, "Save Tracker", this::saveTracker);
        addButtonToPanel(panel, "Load Tracker", this::loadTracker);
        addButtonToPanel(panel, "Exit", this::handleExit);

        statusLabel = new JLabel("Status: ");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.getContentPane().add(statusLabel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }

    // EFFECTS: Create a button and add it to the specified panel
    private void addButtonToPanel(JPanel panel, String buttonText, ActionListener actionListener) {
        JButton button = createButton(buttonText, actionListener);
        panel.add(button);
    }

    // EFFECTS: Create a JButton with the given text and ActionListener
    private JButton createButton(String buttonText, ActionListener actionListener) {
        JButton button = new JButton(buttonText);
        button.addActionListener(actionListener);
        return button;
    }

    //EFFECTS: GUI for AddTransaction
    private void addTransaction(ActionEvent e) {
        JFrame addTransactionFrame = createFrame("Add Transaction");
        JPanel addTransactionPanel = createPanel();

        JTextField descriptionField = createTextField(20);
        JTextField amountField = createTextField(10);
        JTextField dateField = createTextField(10);
        JComboBox<String> typeComboBox = createComboBox(new String[]{"Income", "Expense"});

        JButton addButton = createButton("Add", event -> {
            handleAddTransactionButtonClick(descriptionField, amountField, dateField, typeComboBox);
            addTransactionFrame.dispose();
        });

        // Add components to the panel
        addTransactionPanel.add(new JLabel("Description:"));
        addTransactionPanel.add(descriptionField);
        addTransactionPanel.add(new JLabel("Amount:"));
        addTransactionPanel.add(amountField);
        addTransactionPanel.add(new JLabel("Date (yyyy-mm-dd):"));
        addTransactionPanel.add(dateField);
        addTransactionPanel.add(new JLabel("Type:"));
        addTransactionPanel.add(typeComboBox);
        addTransactionPanel.add(addButton);

        addTransactionFrame.getContentPane().add(addTransactionPanel);
        addTransactionFrame.setSize(300, 200);
        addTransactionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addTransactionFrame.setVisible(true);
    }

    //EFFECTS: handle when buttons are clicked
    private void handleAddTransactionButtonClick(JTextField descriptionField, JTextField amountField,
                                                 JTextField dateField, JComboBox<String> typeComboBox) {
        String description = descriptionField.getText();
        double amount = Double.parseDouble(amountField.getText());
        LocalDate date = LocalDate.parse(dateField.getText());
        String type = (String) typeComboBox.getSelectedItem();
        TransactionType transactionType = type.equals("Income") ? TransactionType.INCOME : TransactionType.EXPENSE;
        financialTrackerApp.addTransaction(new Transaction(description, amount, date, transactionType));

        //update the UI or display a message
        JOptionPane.showMessageDialog(null,
                "Transaction added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        // Print to console
//        System.out.println("Transaction added: " + description + ", Amount: " + amount + ", Type: " + type);
    }

    //EFFECTS: GUI for viewTransaction
    private void viewTransaction(ActionEvent e) {
        JFrame transactionListFrame = createFrame("Transaction List");
        JPanel transactionListPanel = createPanel();

        JTextArea transactionTextArea = new JTextArea(20, 40);
        transactionTextArea.setEditable(false);

        // Obtain transactions from FinancialTrackerApp
        List<Transaction> transactions = financialTrackerApp.getTransactions();

        // Display transactions in JTextArea
        for (Transaction transaction : transactions) {
            transactionTextArea.append(transaction.toString() + "\n");
        }

        JScrollPane scrollPane = new JScrollPane(transactionTextArea);
        transactionListPanel.add(scrollPane);

        transactionListFrame.getContentPane().add(transactionListPanel);
        transactionListFrame.setSize(500, 400);
        transactionListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        transactionListFrame.setVisible(true);
        updateStatusLabel("Transactions:");
    }

    // Add Budget
    private void addBudget(ActionEvent e) {
        JFrame addBudgetFrame = createFrame("Add Budget");
        JPanel addBudgetPanel = createPanel();

        JTextField categoryField = createTextField(20);
        JTextField limitField = createTextField(10);

        JButton addButton = createButton("Add", event -> {
            handleAddBudgetButtonClick(categoryField, limitField);
            addBudgetFrame.dispose();
        });

        // Add components to the panel
        addBudgetPanel.add(new JLabel("Category:"));
        addBudgetPanel.add(categoryField);
        addBudgetPanel.add(new JLabel("Limit:"));
        addBudgetPanel.add(limitField);
        addBudgetPanel.add(addButton);

        addBudgetFrame.getContentPane().add(addBudgetPanel);
        addBudgetFrame.setSize(300, 200);
        addBudgetFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addBudgetFrame.setVisible(true);
    }

    // Handle Add Budget Button Click
    private void handleAddBudgetButtonClick(JTextField categoryField, JTextField limitField) {
        String category = categoryField.getText();
        double limit = Double.parseDouble(limitField.getText());

        Budget budget = new Budget(category, limit);
        financialTrackerApp.addBudget(budget);

        // Update the UI or display a message
        JOptionPane.showMessageDialog(null,
                "Budget added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    // View Budgets
    private void viewBudgets(ActionEvent e) {
        JFrame budgetListFrame = createFrame("Budget List");
        JPanel budgetListPanel = createPanel();

        JTextArea budgetTextArea = new JTextArea(20, 40);
        budgetTextArea.setEditable(false);

        // Obtain budgets from FinancialTrackerApp
        List<Budget> budgets = financialTrackerApp.getBudgets();

        // Display budgets in JTextArea
        for (Budget budget : budgets) {
            budgetTextArea.append(budget.toString() + "\n");
        }

        JScrollPane scrollPane = new JScrollPane(budgetTextArea);
        budgetListPanel.add(scrollPane);

        budgetListFrame.getContentPane().add(budgetListPanel);
        budgetListFrame.setSize(500, 400);
        budgetListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        budgetListFrame.setVisible(true);
        updateStatusLabel("Budgets:");
    }

    // Add Financial Goal
    private void addFinancialGoal(ActionEvent e) {
        JFrame addGoalFrame = createFrame("Add Financial Goal");
        JPanel addGoalPanel = createPanel();

        JTextField descriptionField = createTextField(20);
        JTextField targetAmountField = createTextField(10);
        JTextField targetDateField = createTextField(10);

        JButton addButton = createButton("Add", event -> {
            handleAddFinancialGoalButtonClick(descriptionField, targetAmountField, targetDateField);
            addGoalFrame.dispose();
        });

        // Add components to the panel
        addGoalPanel.add(new JLabel("Description:"));
        addGoalPanel.add(descriptionField);
        addGoalPanel.add(new JLabel("Target Amount:"));
        addGoalPanel.add(targetAmountField);
        addGoalPanel.add(new JLabel("Target Date (yyyy-mm-dd):"));
        addGoalPanel.add(targetDateField);
        addGoalPanel.add(addButton);

        addGoalFrame.getContentPane().add(addGoalPanel);
        addGoalFrame.setSize(300, 200);
        addGoalFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addGoalFrame.setVisible(true);
    }

    // Handle Add Goal Button Click
    private void handleAddFinancialGoalButtonClick(JTextField descriptionField, JTextField targetAmountField,
                                                   JTextField targetDateField) {
        String description = descriptionField.getText();
        double targetAmount = Double.parseDouble(targetAmountField.getText());
        LocalDate targetDate = LocalDate.parse(targetDateField.getText());

        FinancialGoal financialGoal = new FinancialGoal(description, targetAmount, targetDate);
        financialTrackerApp.addFinancialGoal(financialGoal);

        // Update the UI or display a message
        JOptionPane.showMessageDialog(null,
                "Financial goal added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    // View Financial Reminders
    // View Financial Goals
    private void viewFinancialGoals(ActionEvent e) {
        JFrame goalsListFrame = createFrame("Financial Goals List");
        JPanel goalsListPanel = createPanel();

        JTextArea goalsTextArea = new JTextArea(20, 40);
        goalsTextArea.setEditable(false);

        // Obtain financial goals from FinancialTrackerApp
        List<FinancialGoal> goals = financialTrackerApp.getFinancialGoals();

        // Display financial goals in JTextArea
        for (FinancialGoal goal : goals) {
            goalsTextArea.append(goal.toString() + "\n");
        }

        JScrollPane scrollPane = new JScrollPane(goalsTextArea);
        goalsListPanel.add(scrollPane);

        goalsListFrame.getContentPane().add(goalsListPanel);
        goalsListFrame.setSize(500, 400);
        goalsListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        goalsListFrame.setVisible(true);
        updateStatusLabel("Financial Goals:");
    }

    // Mark Goal as Achieved and delete
    private void markGoalAsAchievedAndDelete(ActionEvent e) {
        JFrame markGoalFrame = createFrame("Mark Goal as Achieved");
        JPanel markGoalPanel = createPanel();

        JLabel indexLabel = new JLabel("Enter the index of the goal to mark as achieved:");
        JTextField indexField = createTextField(5);

        JButton markGoalButton = createButton("Mark Goal as Achieved", event -> {
            handleMarkGoalButtonClick(indexField);
            markGoalFrame.dispose();
        });

        markGoalPanel.add(indexLabel);
        markGoalPanel.add(indexField);
        markGoalPanel.add(markGoalButton);

        markGoalFrame.getContentPane().add(markGoalPanel);
        markGoalFrame.setSize(300, 150);
        markGoalFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        markGoalFrame.setVisible(true);
    }

    // Effect: Handles the click event for marking a financial goal as achieved and deleting it
    private void handleMarkGoalButtonClick(JTextField indexField) {
        try {
            int goalIndex = Integer.parseInt(indexField.getText());
            financialTrackerApp.markGoalAsAchievedAndDelete(goalIndex);
            updateStatusLabel("Goal marked as achieved and deleted successfully.");
        } catch (NumberFormatException e) {
            showErrorDialog("Invalid index. Please enter a valid number.");
        }
    }

    // Add Financial Reminder
    private void addFinancialReminder(ActionEvent e) {
        JFrame addReminderFrame = createFrame("Add Financial Reminder");
        JPanel addReminderPanel = createPanel();

        JTextField descriptionField = createTextField(20);
        JTextField amountField = createTextField(10);
        JTextField dueDateField = createTextField(10);

        JButton addButton = createButton("Add", event -> {
            handleAddReminderButtonClick(descriptionField, amountField, dueDateField);
            addReminderFrame.dispose();
        });

        // Add components to the panel
        addReminderPanel.add(new JLabel("Description:"));
        addReminderPanel.add(descriptionField);
        addReminderPanel.add(new JLabel("Amount:"));
        addReminderPanel.add(amountField);
        addReminderPanel.add(new JLabel("Due Date (yyyy-mm-dd):"));
        addReminderPanel.add(dueDateField);
        addReminderPanel.add(addButton);

        addReminderFrame.getContentPane().add(addReminderPanel);
        addReminderFrame.setSize(300, 200);
        addReminderFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addReminderFrame.setVisible(true);
    }

    // Handle Add Reminder Button Click
    private void handleAddReminderButtonClick(JTextField descriptionField, JTextField amountField,
                                              JTextField dueDateField) {
        String description = descriptionField.getText();
        double amount = Double.parseDouble(amountField.getText());
        LocalDate dueDate = LocalDate.parse(dueDateField.getText());

        FinancialReminder financialReminder = new FinancialReminder(description, amount, dueDate);
        financialTrackerApp.addFinancialReminder(financialReminder);

        // Update the UI or display a message
        JOptionPane.showMessageDialog(null,
                "Financial reminder added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    // View Financial Reminders
    private void viewFinancialReminders(ActionEvent e) {
        JFrame reminderListFrame = createFrame("Financial Reminders List");
        JPanel reminderListPanel = createPanel();

        JTextArea reminderTextArea = new JTextArea(20, 40);
        reminderTextArea.setEditable(false);

        // Obtain financial reminders from FinancialTrackerApp
        List<FinancialReminder> reminders = financialTrackerApp.getFinancialReminders();

        // Display financial reminders in JTextArea
        for (FinancialReminder reminder : reminders) {
            reminderTextArea.append(reminder.toString() + "\n");
        }

        JScrollPane scrollPane = new JScrollPane(reminderTextArea);
        reminderListPanel.add(scrollPane);

        reminderListFrame.getContentPane().add(reminderListPanel);
        reminderListFrame.setSize(500, 400);
        reminderListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        reminderListFrame.setVisible(true);
        updateStatusLabel("Financial Reminders:");
    }

    // Mark Reminder as Completed and delete
    private void markReminderAsCompletedAndDelete(ActionEvent e) {
        JFrame markReminderFrame = createFrame("Mark Reminder as Completed");
        JPanel markReminderPanel = createPanel();

        JLabel indexLabel = new JLabel("Enter the index of the reminder to mark as completed:");
        JTextField indexField = createTextField(5);

        JButton markReminderButton = createButton("Mark Reminder as Completed", event -> {
            handleMarkReminderButtonClick(indexField);
            markReminderFrame.dispose();
        });

        markReminderPanel.add(indexLabel);
        markReminderPanel.add(indexField);
        markReminderPanel.add(markReminderButton);

        markReminderFrame.getContentPane().add(markReminderPanel);
        markReminderFrame.setSize(300, 150);
        markReminderFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        markReminderFrame.setVisible(true);
    }

    // EFFECTS: handle the button click to mark a financial reminder as completed and delete it
    private void handleMarkReminderButtonClick(JTextField indexField) {
        try {
            int reminderIndex = Integer.parseInt(indexField.getText());
            financialTrackerApp.markReminderAsCompletedAndDelete(reminderIndex);
            updateStatusLabel("Reminder marked as completed and deleted successfully.");
        } catch (NumberFormatException e) {
            showErrorDialog("Invalid index. Please enter a valid number.");
        }
    }

    // Generate and Export Report
    private void generateAndExportReport(ActionEvent e) {
        JFrame exportReportFrame = createFrame("Generate and Export Report");
        JPanel exportReportPanel = createPanel();

        JLabel filePathLabel = new JLabel("Enter the file path for the report (ex: /Users/Document/name.csv):");
        JTextField filePathField = createTextField(50);

        JButton exportReportButton = createButton("Generate and Export Report", event -> {
            handleExportReportButtonClick(filePathField);
            exportReportFrame.dispose();
        });

        exportReportPanel.add(filePathLabel);
        exportReportPanel.add(filePathField);
        exportReportPanel.add(exportReportButton);

        exportReportFrame.getContentPane().add(exportReportPanel);
        exportReportFrame.setSize(500, 150);
        exportReportFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        exportReportFrame.setVisible(true);
    }

    // Effect: Handles the click event for generating and exporting a report
    private void handleExportReportButtonClick(JTextField filePathField) {
        String filePath = filePathField.getText();
        financialTrackerApp.generateAndExportReport(filePath);
        updateStatusLabel("Report generated and exported successfully.");
    }

    //EFFECTS: GUI for Save Tracker option
    private void saveTracker(ActionEvent e) {
        financialTrackerApp.saveTracker();
        updateStatusLabel("Tracker saved successfully.");
    }

    //EFFECTS: GUI for Load Tracker option
    private void loadTracker(ActionEvent e) {
        financialTrackerApp.loadTracker();
        updateStatusLabel("Tracker loaded successfully.");
    }

    //EFFECTS: GUI for Exit option
    private void handleExit(ActionEvent e) {
        System.exit(0);
    }

    //EFFECTS: displays error message when incorrect situation
    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    //EFFECTS: display update message of user actions
    private void updateStatusLabel(String message) {
        statusLabel.setText("Status: " + message);
    }
}

