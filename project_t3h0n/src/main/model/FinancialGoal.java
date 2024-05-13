package model;

import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;

// Represent a Financial goal with description (e.g. purpose of saving) with target amount, and target date
public class FinancialGoal implements Writable {
    private String description;     // description of the goal
    private double targetAmount;    // target of the goal
    private LocalDate targetDate;   // date plan to achieve target
    private boolean achieved;       // status of the goal

    /*REQUIRES: description not null, targetAmount > 0, targetDate in form of LocalDate.of(YYYY-MM-DD)
      EFFECTS: set FinancialGoal description to description
               set FinancialGoal targetAmount to targetAmount
               set FinancialGoal targetDate to targetDate
               set FinancialGoal achieved to false as default
    */
    public FinancialGoal(String description, double targetAmount, LocalDate targetDate) {
        this.description = description;
        this.targetAmount = targetAmount;
        this.targetDate = targetDate;
        this.achieved = false;

        // Log the event when a new financial goal is created
//        logEvent("Created a new financial goal: " + toString());
    }

    //EFFECTS: Log an event using the EventLog
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

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setAchieved(boolean achieved) {
        this.achieved = achieved;
    }

    public boolean isAchieved() {
        return achieved;
    }

    //EFFECTS: Return a string representation of financial goal
    @Override
    public String toString() {
        return "Description: " + description + ", Target Amount: " + targetAmount
                + ", Target Date: " + targetDate + ", Status: " + achieved;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("description", description);
        json.put("targetAmount", targetAmount);
        json.put("targetDate", targetDate);
        json.put("achieved", achieved);
        return json;
    }
}
