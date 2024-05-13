package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

// Financial Tracker Application
public class FinancialTrackerApp {
    private  Tracker tracker;
    private final Scanner scanner;
    private static final String JSON_STORE = "./data/tracker.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //MODIFIES: this
    //EFFECTS: initializes variable and run the application
    public FinancialTrackerApp() throws FileNotFoundException {
        tracker = new Tracker();
        scanner = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runTracker();
    }

    //MODIFIES: this
    //EFFECTS: process user choices
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void runTracker() {
        int choice;
        boolean notDone = true;
        while (notDone) {
            actionMenu();
            choice = scanner.nextInt();

            switch (choice) {
                case 1: addTransaction();
                    break;
                case 2: viewTransactions();
                    break;
                case 3: addBudget();
                    break;
                case 4: viewBudgets();
                    break;
                case 5: addFinancialGoal();
                    break;
                case 6: viewFinancialGoals();
                    break;
                case 7: markGoalAsAchievedAndDelete();
                    break;
                case 8: addFinancialReminder();
                    break;
                case 9: viewFinancialReminders();
                    break;
                case 10: markReminderAsAchievedAndDelete();
                    break;
                case 11: generateAndExportReport();
                    break;
                case 12: saveTracker();
                    break;
                case 13: loadTracker();
                    break;
                case 14:
                    notDone = false;
                    quitApplication();
//                    System.out.println("Bye");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    //EFFECTS: Display a menu with actions for the user
    private void actionMenu() {
        System.out.println("\nPersonal Finance Tracker Menu");
        System.out.println("1. Add Transaction");
        System.out.println("2. View Transactions");
        System.out.println("3. Add Budget");
        System.out.println("4. View Budgets");
        System.out.println("5. Add Financial Goal");
        System.out.println("6. View Financial Goals");
        System.out.println("7. Mark Goal as Achieved and delete");
        System.out.println("8. Add Financial Reminder");
        System.out.println("9. View Financial Reminders");
        System.out.println("10. Mark Reminder as Completed and delete");
        System.out.println("11. generate and export report to external tools");
        System.out.println("12. save tracker in this application");
        System.out.println("13. load tracker in from this application");
        System.out.println("14. Exit");
        System.out.print("Choose a number: ");
    }

    //EFFECTS: add transaction
    public void addTransaction(Transaction transaction) {
        tracker.addTransaction(transaction);
    }

    /*MODIFIES: this
      EFFECTS: prompt user to input details information (description, amount, date, type) of a transaction
               then add the transaction to the tracker.
     */
    void addTransaction() {
        System.out.print("Enter transaction description: ");
        scanner.nextLine();  // Consume newline
        String description = scanner.nextLine();
        System.out.print("Enter transaction amount: ");
        double amount = scanner.nextDouble();
        System.out.print("Enter transaction date (yyyy-mm-dd): ");
        scanner.nextLine();  // Consume newline
        LocalDate date = LocalDate.parse(scanner.nextLine());
        System.out.println("Choose transaction type (1 or 2):");
        System.out.println("1. Income");
        System.out.println("2. Expense");
        int typeChoice = scanner.nextInt();
        TransactionType type = (typeChoice == 1) ? TransactionType.INCOME : TransactionType.EXPENSE;
        Transaction transaction = new Transaction(description, amount, date, type);
        tracker.addTransaction(transaction);
        System.out.println("Transaction added successfully.");
    }


    //EFFECTS: Get list of transaction
    List<Transaction> getTransactions() {
        return tracker.getTransactions();
    }

    //EFFECTS: print all the transactions with details
    private void viewTransactions() {
        System.out.println("Transactions:");
        List<Transaction> transactions = tracker.getTransactions();
        for (Transaction transaction: transactions) {
            System.out.println(transaction.toString());
        }
    }

    //EFFECTS: add Budget
    public void addBudget(Budget budget) {
        tracker.addBudget(budget);
    }

    /*MODIFIES: this
      EFFECTS: prompt user to input details information (category, limit) of a budget
               then add the budget to the tracker.
    */
    private void addBudget() {
        System.out.print("Enter budget category: ");
        scanner.nextLine();  // Consume newline
        String category = scanner.nextLine();
        System.out.print("Enter budget limit: ");
        double limit = scanner.nextDouble();
        Budget budget = new Budget(category, limit);
        tracker.addBudget(budget);
        System.out.println("Budget added successfully.");
    }

    //EFFECTS: get list of Budgets
    public List<Budget> getBudgets() {
        return tracker.getBudgets();
    }

    //EFFECTS: print all the budgets with details
    private void viewBudgets() {
        System.out.println("Budgets:");
        List<Budget> budgets = tracker.getBudgets();
        for (Budget budget : budgets) {
            System.out.println(budget);
        }
    }

    //EFFECTS: add Financial Goal
    public void addFinancialGoal(FinancialGoal financialGoal) {
        tracker.addFinancialGoal(financialGoal);
    }

    /*MODIFIES: this
      EFFECTS: prompt user to input details information (description, amount) of a budget
               then add the goal to the tracker
    */
    private void addFinancialGoal() {
        System.out.print("Enter goal description: ");
        scanner.nextLine();  // Consume newline
        String description = scanner.nextLine();
        System.out.print("Enter target amount: ");
        double targetAmount = scanner.nextDouble();
        System.out.print("Enter target date (yyyy-mm-dd): ");
        scanner.nextLine();  // Consume newline
        LocalDate targetDate = LocalDate.parse(scanner.nextLine());
        FinancialGoal financialGoal = new FinancialGoal(description, targetAmount, targetDate);
        tracker.addFinancialGoal(financialGoal);
        System.out.println("Financial goal added successfully.");
    }

    //EFFECTS: get list of Financial Goals
    public List<FinancialGoal> getFinancialGoals() {
        return tracker.getFinancialGoals();
    }

    //EFFECTS: print all the financial goals with details
    private void viewFinancialGoals() {
        System.out.println("Financial Goals:");
        List<FinancialGoal> goals = tracker.getFinancialGoals();
        for (FinancialGoal goal : goals) {
            System.out.println(goal);
        }
    }

    /*MODIFIES: this
      EFFECTS: prompt the user to choose a financial goal to mark it as achieved
               then delete it
    */
    private void markGoalAsAchievedAndDelete() {
        System.out.print("Enter the index of the goal to mark as achieved: ");
        int goalIndex = scanner.nextInt();
        tracker.markGoalAsAchieved(goalIndex);
        tracker.deleteGoal(goalIndex);
    }

    // MODIFIES: this
// EFFECTS: Mark the financial goal at the given index as achieved and delete it
    public void markGoalAsAchievedAndDelete(int goalIndex) {
        try {
            tracker.markGoalAsAchieved(goalIndex);
            tracker.deleteGoal(goalIndex);
//            System.out.println("Goal marked as achieved and deleted successfully.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid index. Please enter a valid number.");
        }
    }

    //EFFECTS: add Financial Reminder
    public void addFinancialReminder(FinancialReminder financialReminder) {
        tracker.addFinancialReminder(financialReminder);
    }

    /*MODIFIES: this
      EFFECTS: prompt user to input details information (description, dueDate) of a reminder
               then add the reminder to the tracker
    */
    private void addFinancialReminder() {
        System.out.print("Enter reminder description: ");
        scanner.nextLine();  // Consume newline
        String description = scanner.nextLine();
        System.out.print("Enter reminder amount: ");
        double amount = scanner.nextDouble();
        System.out.print("Enter due date (yyyy-mm-dd): ");
        scanner.nextLine();
        LocalDate dueDate = LocalDate.parse(scanner.nextLine());
        FinancialReminder financialReminder = new FinancialReminder(description, amount, dueDate);
        tracker.addFinancialReminder(financialReminder);
        System.out.println("Financial reminder added successfully.");
    }

    //EFFECTS: get list of Financial Reminders
    public List<FinancialReminder> getFinancialReminders() {
        return tracker.getFinancialReminders();
    }

    //EFFECTS: print all the financial reminders with details
    private void viewFinancialReminders() {
        System.out.println("Financial Reminders:");
        List<FinancialReminder> reminders = tracker.getFinancialReminders();
        for (FinancialReminder reminder : reminders) {
            System.out.println(reminder);
        }
    }

    /*MODIFIES: this
      EFFECTS: prompt the user to choose a financial reminder to mark it as achieved
               then delete it
    */
    private void markReminderAsAchievedAndDelete() {
        System.out.print("Enter the index of the reminder to mark as completed: ");
        int reminderIndex = scanner.nextInt();
        tracker.markReminderAsCompleted(reminderIndex);
        tracker.deleteReminder(reminderIndex);

    }

    // MODIFIES: this
    // EFFECTS: Mark the financial reminder at the given index as completed and delete it
    public void markReminderAsCompletedAndDelete(int reminderIndex) {
        try {
            tracker.markReminderAsCompleted(reminderIndex);
            tracker.deleteReminder(reminderIndex);
//            System.out.println("Reminder marked as completed and deleted successfully.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid index. Please enter a valid number.");
        }
    }

    //REFERENCE: JsonSerializationDemo - Paul Carter

    // EFFECTS: save the current tracker to the application
    void saveTracker() {
        try {
            jsonWriter.open();
            jsonWriter.write(tracker);
            jsonWriter.close();
            System.out.println("Saved all transactions, budgets, financial goals "
                    + "and financial reminders to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: tracker
    // EFFECTS: load previous tracker to the application
    void loadTracker() {
        try {
            tracker = jsonReader.read();
            System.out.println("Loaded transactions, budgets, financial goals "
                    + "and financial reminders from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    //EFFECTS: print events and quit
    private void quitApplication() {
        // Print all events before quitting
        tracker.printAllEvents();

        System.out.println("Bye");

    }

    /*EFFECTS: generate and export the transactions to a destination file
               print the header and values of the transaction details (user inputs)
               print the header and values of the budget details (user inputs)
               print the header and values of the financial goal details (user inputs)
               print the header and values of the financial reminder details (user inputs)
               to the destination file
    */

    private void generateAndExportReport() {
        try {
            String filePath = getFilePathFromUser();
            FileWriter fileWriter = new FileWriter(filePath);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            writeDetails(printWriter, "Transaction Description,Transaction Amount,Transaction Date,Transaction Type",
                    tracker.getTransactions().stream().map(TransactionObjectWithDetails::new)
                            .collect(Collectors.toList())
            );

            writeDetails(printWriter, "\nBudget Category,Budget Limit",
                    tracker.getBudgets().stream().map(BudgetObjectWithDetails::new).collect(Collectors.toList())
            );

            writeDetails(printWriter, "\nFinancial Goal Description,Target Amount,Target Date,Status",
                    tracker.getFinancialGoals().stream().map(FinancialGoalObjectWithDetails::new)
                            .collect(Collectors.toList())
            );

            writeDetails(printWriter, "\nFinancial Reminder Description,Reminder Amount,Due Date,Status",
                    tracker.getFinancialReminders().stream().map(FinancialReminderObjectWithDetails::new)
                            .collect(Collectors.toList())
            );

            // Close the file
            printWriter.close();
        } catch (IOException e) {
            System.err.println("Error exporting report: " + e.getMessage());
        }
    }

    // EFFECTS: generate and export the transactions to a destination file
    public void generateAndExportReport(String filePath) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            writeTransactionDetails(printWriter);
            writeBudgetDetails(printWriter);
            writeFinancialGoalDetails(printWriter);
            writeFinancialReminderDetails(printWriter);

            // Close the file
            printWriter.close();
        } catch (IOException e) {
            System.err.println("Error exporting report: " + e.getMessage());
        }
    }

    //Helper Methods
    //Effect: Prompts the user to enter the file path for the report and returns the entered path.
    private String getFilePathFromUser() {
        Scanner fileScanner = new Scanner(System.in);
        System.out.println("Enter the file path for the report (ex: /Users/Document/name.csv)");
        return fileScanner.nextLine();
    }

    //Effect: Writes details of a transaction to the provided PrintWriter
    private void writeTransactionDetails(PrintWriter printWriter, Transaction transaction) {
        printWriter.println(
                transaction.getDescription() + "," + transaction.getAmount() + ","
                        + transaction.getDate() + "," + transaction.getTransactionType()
        );
    }

    //EFFECTS: Helper method to write transaction details to the provided PrintWriter
    private void writeTransactionDetails(PrintWriter printWriter) {
        writeDetails(printWriter, "Transaction Description,Transaction Amount,Transaction Date,Transaction Type",
                tracker.getTransactions().stream().map(TransactionObjectWithDetails::new)
                        .collect(Collectors.toList())
        );
    }

    //Effect: Writes details of a budget to the provided PrintWriter
    private void writeBudgetDetails(PrintWriter printWriter, Budget budget) {
        printWriter.println(budget.getDescription() + "," + budget.getLimit());
    }

    //EFFECTS: Helper method to write budget details to the provided PrintWriter
    private void writeBudgetDetails(PrintWriter printWriter) {
        writeDetails(printWriter, "\nBudget Category,Budget Limit",
                tracker.getBudgets().stream().map(BudgetObjectWithDetails::new).collect(Collectors.toList())
        );
    }

    //Effect: Writes details of a financial goal to the provided PrintWriter
    private void writeFinancialGoalDetails(PrintWriter printWriter, FinancialGoal goal) {
        printWriter.println(
                goal.getDescription() + "," + goal.getTargetAmount() + ","
                        + goal.getTargetDate() + "," + goal.isAchieved()
        );
    }

    //EFFECTS: Helper method to write financial goal details to the provided PrintWriter
    private void writeFinancialGoalDetails(PrintWriter printWriter) {
        writeDetails(printWriter, "\nFinancial Goal Description,Target Amount,Target Date,Status",
                tracker.getFinancialGoals().stream().map(FinancialGoalObjectWithDetails::new)
                        .collect(Collectors.toList())
        );
    }

    //Effect: Writes details of a financial reminder to the provided PrintWriter
    private void writeFinancialReminderDetails(PrintWriter printWriter, FinancialReminder reminder) {
        printWriter.println(
                reminder.getDescription() + "," + reminder.getAmount() + ","
                        + reminder.getDueDate() + "," + reminder.isAchieved()
        );
    }

    //EFFECTS: Helper method to write financial reminder details to the provided PrintWriter
    private void writeFinancialReminderDetails(PrintWriter printWriter) {
        writeDetails(printWriter, "\nFinancial Reminder Description,Reminder Amount,Due Date,Status",
                tracker.getFinancialReminders().stream().map(FinancialReminderObjectWithDetails::new)
                        .collect(Collectors.toList())
        );
    }

    /*Effect: Writes details of a list of objects that implement the ObjectWithDetails interface
              to the provided PrintWriter
    */
    private void writeDetails(PrintWriter printWriter, String header, List<? extends ObjectWithDetails> objects) {
        printWriter.println(header);
        objects.forEach(object -> object.writeDetails(printWriter));
    }

    /* Effect: Inner interface for objects
               that can provide details to be written to a PrintWriter
    */
    private interface ObjectWithDetails {
        //EFFECTS: Write details of objects
        void writeDetails(PrintWriter printWriter);
    }

    /*Effect: Inner class implement the ObjectWithDetails interface for transaction objects
              to write details
     */
    private class TransactionObjectWithDetails implements ObjectWithDetails {
        private Transaction transaction;

        //EFFECTS: Construct TransactionObjectWithDetails objects
        public TransactionObjectWithDetails(Transaction transaction) {
            this.transaction = transaction;
        }

        @Override
        public void writeDetails(PrintWriter printWriter) {
            writeTransactionDetails(printWriter, transaction);
        }
    }

    /*Effect: Inner class implement the ObjectWithDetails interface for budget objects
              to write details
     */
    private class BudgetObjectWithDetails implements ObjectWithDetails {
        private Budget budget;

        //EFFECTS: Construct BudgetObjectWithDetails objects
        public BudgetObjectWithDetails(Budget budget) {
            this.budget = budget;
        }

        @Override
        public void writeDetails(PrintWriter printWriter) {
            writeBudgetDetails(printWriter, budget);
        }
    }

    /*Effect: Inner class implement the ObjectWithDetails interface for financial goal objects
              to write details
     */
    private class FinancialGoalObjectWithDetails implements ObjectWithDetails {
        private FinancialGoal goal;

        //EFFECTS: Construct FinancialGoalObjectWithDetails objects
        public FinancialGoalObjectWithDetails(FinancialGoal goal) {
            this.goal = goal;
        }

        @Override
        public void writeDetails(PrintWriter printWriter) {
            writeFinancialGoalDetails(printWriter, goal);
        }
    }

    /*Effect: Inner class implement ObjectWithDetails interface for financial reminder objects
              to write details
     */
    private class FinancialReminderObjectWithDetails implements ObjectWithDetails {
        private FinancialReminder reminder;

        //EFFECTS: Construct FinancialReminderObjectWithDetails objects
        public FinancialReminderObjectWithDetails(FinancialReminder reminder) {
            this.reminder = reminder;
        }

        @Override
        public void writeDetails(PrintWriter printWriter) {
            writeFinancialReminderDetails(printWriter, reminder);
        }
    }


}

