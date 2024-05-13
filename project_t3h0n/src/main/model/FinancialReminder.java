package model;

import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;

// Represent a Financial goal with description (e.g. what bill need to pay) with amount, dueDate
public class FinancialReminder implements Writable {
    private String description;     // description of the reminder
    private double amount;          // amount need to pay before dueDate
    private LocalDate dueDate;      // dueDate of the reminder
    private boolean achieved;       // status of the reminder

    /*REQUIRES: description not null, amount > 0, targetDate in form of LocalDate.of(YYYY-MM-DD)
      EFFECTS: set FinancialReminder description to description
               set FinancialReminder targetAmount to amount;
               set FinancialReminder targetDate to targetDate
               set FinancialReminder achieved to false as default
    */
    public FinancialReminder(String description, double amount, LocalDate dueDate) {
        this.description = description;
        this.amount = amount;
        this.dueDate = dueDate;
        this.achieved = false;

        // Log the event when a new financial reminder is created
//        logEvent("Created a new financial reminder: " + toString());
    }

    //EFFECTS Log an event using the EventLog
//    private void logEvent(String eventDescription) {
//        EventLog eventLog = EventLog.getInstance();
//        eventLog.logEvent(new Event(eventDescription));
//    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setAchieved(boolean achieved) {
        this.achieved = achieved;
    }

    public boolean isAchieved() {
        return achieved;
    }

    //EFFECTS: Return a string representation of financial reminder
    @Override
    public String toString() {
        return "Description: " + description + ", amount: " + amount
                + ", Due Date: " + dueDate + ", Status: " + achieved;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("description", description);
        json.put("amount", amount);
        json.put("dueDate", dueDate);
        json.put("achieved", achieved);
        return json;
    }
}
