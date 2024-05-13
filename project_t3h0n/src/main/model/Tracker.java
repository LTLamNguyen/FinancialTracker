package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represent a Tracker with transaction list, budget list, financial goal list, and financial reminder list
public class Tracker implements Writable {
    private List<Transaction> transactions;                 // variable of transactions list
    private List<Budget> budgets;                           // variable of budgets list
    private List<FinancialGoal> financialGoals;             // variable of financial goals list
    private List<FinancialReminder> financialReminders;     // variable of financial reminders list
    private EventLog eventLog;//                              // Singleton instance of EventLog

    /*EFFECTS: create empty list of transactions
               create empty list of budgets
               create empty list of financial goals
               create empty list of financial reminders
    */
    public Tracker() {
        transactions = new ArrayList<>();
        budgets = new ArrayList<>();
        financialGoals = new ArrayList<>();
        financialReminders = new ArrayList<>();
        eventLog = EventLog.getInstance(); //
    }

    // MODIFIES: this
    // EFFECTS: log an event using the EventLog
    private void logEvent(Event event) {
        eventLog.logEvent(event);
    }

    /*
    MODIFIES: this
    EFFECTS: add a transaction to the transactions list
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);

        //EFFECTS: Log the event when a new transaction is added
        logEvent(new Event("Added a new transaction: " + transaction.toString()));//
    }

    //EFFECTS: return list of transactions
    public List<Transaction> getTransactions() {
        return transactions;
    }

    //EFFECT: return size of transactions
    public int numTransactions() {
        return transactions.size();
    }

    /*
    MODIFIES: this
    EFFECTS: add a budget to the budgets list
     */
    public void addBudget(Budget budget) {
        budgets.add(budget);

        //EFFECTS: Log the event when a new budget is added
        logEvent(new Event("Added a new budget: " + budget.toString()));//
    }

    //EFFECTS: return list of budgets
    public List<Budget> getBudgets() {
        return budgets;
    }

    //EFFECT: return size of budgets
    public int numBudgets() {
        return budgets.size();
    }

    /*
    MODIFIES: this
    EFFECTS: add a financial goal to the financial goals list
     */
    public void addFinancialGoal(FinancialGoal financialGoal) {
        financialGoals.add(financialGoal);

        //EFFECTS: Log the event when a new financial goal is added
        logEvent(new Event("Added a new financial goal: " + financialGoal.toString()));//
    }

    //EFFECTS: return list of financial goals
    public List<FinancialGoal> getFinancialGoals() {
        return financialGoals;
    }

    //EFFECT: return size of financialGoals
    public int numFinancialGoals() {
        return financialGoals.size();
    }

    /*
    MODIFIES: this
    EFFECTS: mark a goal at given index if it is valid as achieved
             not mark otherwise
     */
    public void markGoalAsAchieved(int financialGoalIndex) {
        if (financialGoalIndex >= 0 && financialGoalIndex < financialGoals.size()) {
            FinancialGoal financialGoal = financialGoals.get(financialGoalIndex);
            financialGoal.setAchieved(true);
        } else {
            System.out.println("Invalid goal index. Goal not marked as achieved.");
        }
    }

    /*
    MODIFIES: this
    EFFECTS: delete a goal at given index if it is valid
             not delete otherwise
     */
    public void deleteGoal(int financialGoalIndex) {
        if (financialGoalIndex >= 0 && financialGoalIndex < financialGoals.size()) {
            financialGoals.remove(financialGoalIndex);
            // Log the event when a financial goal is deleted
            logEvent(new Event("Goal " + financialGoalIndex + " is marked as achieved and deleted from tracker."));//
        } else {
            System.out.println("Invalid index. Goal not deleted.");
        }
    }

    /*
    MODIFIES: this
    EFFECTS: add a financial reminder to the financial reminder list
     */
    public void addFinancialReminder(FinancialReminder financialReminder) {
        financialReminders.add(financialReminder);
        //EFFECTS: Log the event when a new financial reminder is added
        logEvent(new Event("Added a new financial goal: " + financialReminder.toString()));//
    }

    //EFFECTS: return list of financial reminders
    public List<FinancialReminder> getFinancialReminders() {
        return financialReminders;
    }

    //EFFECT: return size of financialReminder
    public int numFinancialReminders() {
        return financialReminders.size();
    }

    /*
    MODIFIES: this
    EFFECTS: mark a reminder at given index if it is valid as achieved
             not mark otherwise
     */
    public void markReminderAsCompleted(int reminderIndex) {
        if (reminderIndex >= 0 && reminderIndex < financialReminders.size()) {
            FinancialReminder goal = financialReminders.get(reminderIndex);
            goal.setAchieved(true);
        } else {
            System.out.println("Invalid index. Reminder not marked as achieved.");
        }
    }

    /*
    MODIFIES: this
    EFFECTS: delete a reminder at given index if it is valid
             not delete otherwise
     */
    public void deleteReminder(int financialReminderIndex) {
        if (financialReminderIndex >= 0 && financialReminderIndex < financialReminders.size()) {
            financialReminders.remove(financialReminderIndex);
            // Log the event when a financial reminder is deleted
            logEvent(new Event("Reminder " + financialReminderIndex
                    + " is marked as achieved and deleted from tracker."));
        } else {
            System.out.println("Invalid index. Reminder not deleted.");
        }
    }

    //EFFECTS: print all events
    public void printAllEvents() {
        System.out.println("Printing all events since the application started:");

        for (Event event : eventLog) {
            System.out.println(event.toString());
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("transactions", transactionsToJson());
        json.put("budgets", budgetsToJson());
        json.put("financialGoals", financialGoalsToJson());
        json.put("financialReminders", financialRemindersToJson());
        return json;
    }

    // MODIFIES: this
    // EFFECTS: returns transactions in this tracker as a JSON array
    private JSONArray transactionsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Transaction transaction: transactions) {
            jsonArray.put(transaction.toJson());
        }
        return jsonArray;
    }

    // MODIFIES: this
    // EFFECTS: returns budgets in this tracker as a JSON array
    private JSONArray budgetsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Budget budget: budgets) {
            jsonArray.put(budget.toJson());
        }
        return jsonArray;
    }

    // MODIFIES: this
    // EFFECTS: returns financialGoals in this tracker as a JSON array
    private JSONArray financialGoalsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (FinancialGoal financialGoal: financialGoals) {
            jsonArray.put(financialGoal.toJson());
        }
        return jsonArray;
    }

    // MODIFIES: this
    // EFFECTS: returns financialReminders in this tracker as a JSON array
    private JSONArray financialRemindersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (FinancialReminder financialReminder: financialReminders) {
            jsonArray.put(financialReminder.toJson());
        }
        return jsonArray;
    }
}
