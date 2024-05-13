package model;

import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;

// Represent a Transaction with description, amount, date, and TransactionType
public class Transaction implements Writable {
    private String description;     // description of the transaction
    private double amount;          // amount of the transaction
    private LocalDate date;         // date that the transaction conducted
    private TransactionType transactionType;   // type of transaction (INCOME / EXPENSE)

    /*REQUIRES: description can not be null
                amount > 0
                date in form of YYYY-MM-DD
                type are only INCOME or EXPENSE
      EFFECTS: Create a transaction includes description , amount , date and type
    */
    public Transaction(String description, double amount, LocalDate date, TransactionType type) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.transactionType = type;
        // Log the creation of a new transaction
//        logEvent("Created a new transaction: " + this.toString());

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

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setTransactionType(TransactionType type) {
        this.transactionType = type;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    //EFFECTS: Return a string representation of transaction
    @Override
    public String toString() {
        return "Description: " + description + ", Amount: " + amount
                + ", Date: " + date + ", Type: " + transactionType;
    }

    @Override
    public JSONObject toJson() {

        JSONObject json = new JSONObject();
        json.put("description", description);
        json.put("amount", amount);
        json.put("date", date);
        json.put("transactionType", transactionType);


        return json;
    }
}
