package model;

import org.json.JSONObject;
import persistence.Writable;

// Represent a Budget with description (e.g. purpose of spending) and limit
public class Budget implements Writable {
    private String description;                     // description of the budget
    private double limit;                           // limit of the budget

    /*REQUIRES: category not null, limit > 0;
      EFFECTS: set Budget category to category
               set Budget limit to limit
    */
    public Budget(String description, double limit) {
        this.description = description;
        this.limit = limit;
        // Log the event when a new budget is created
//        logEvent("Created a new budget: " + toString());
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

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public double getLimit() {
        return limit;
    }


    //EFFECTS: Return a string representation of budget
    @Override
    public String toString() {
        return "Category: " + description + ", Limit: " + limit;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("description", description);
        json.put("limit", limit);
        return json;
    }
}
