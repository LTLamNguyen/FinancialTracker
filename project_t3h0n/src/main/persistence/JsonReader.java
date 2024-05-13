package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.stream.Stream;

//REFERENCE: JsonSerializationDemo - Paul Carter

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads tracker from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Tracker read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTracker(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses tracker from JSON object and returns it
    private Tracker parseTracker(JSONObject jsonObject) {
        Tracker tracker = new Tracker();
        addTransactions(tracker, jsonObject);
        addBudgets(tracker, jsonObject);
        addFinancialGoals(tracker, jsonObject);
        addFinancialReminders(tracker, jsonObject);
        return tracker;
    }

    // MODIFIES: tracker
    // EFFECTS: parses transactions from JSON object and adds them to tracker
    private void addTransactions(Tracker tracker, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("transactions");
        for (Object json : jsonArray) {
            JSONObject nextTransaction = (JSONObject) json;
            addTransaction(tracker, nextTransaction);
        }
    }

    // MODIFIES: tracker
    // EFFECTS: parses transaction from JSON object and adds it to tracker
    private void addTransaction(Tracker tracker, JSONObject jsonObject) {
        String description = jsonObject.getString("description");
        double amount = jsonObject.getDouble("amount");
        LocalDate date = LocalDate.parse(jsonObject.getString("date"));
        TransactionType transactionType = TransactionType.valueOf(jsonObject.getString("transactionType"));
        Transaction transaction = new Transaction(description, amount, date, transactionType);
        tracker.addTransaction(transaction);
    }

    // MODIFIES: tracker
    // EFFECTS: parses budgets from JSON object and adds them to tracker
    private void addBudgets(Tracker tracker, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("budgets");
        for (Object json : jsonArray) {
            JSONObject nextBudget = (JSONObject) json;
            addBudget(tracker, nextBudget);
        }
    }

    // MODIFIES: tracker
    // EFFECTS: parses budget from JSON object and adds it to tracker
    private void addBudget(Tracker tracker, JSONObject jsonObject) {
        String description = jsonObject.getString("description");
        double limit = jsonObject.getDouble("limit");
        Budget budget = new Budget(description, limit);
        tracker.addBudget(budget);
    }

    // MODIFIES: tracker
    // EFFECTS: parses financialGoals from JSON object and adds it to tracker
    private void addFinancialGoals(Tracker tracker, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("financialGoals");
        for (Object json : jsonArray) {
            JSONObject nextFinancialGoals = (JSONObject) json;
            addFinancialGoal(tracker, nextFinancialGoals);
        }
    }

    // MODIFIES: tracker
    // EFFECTS: parses financialGoal from JSON object and adds it to tracker
    private void addFinancialGoal(Tracker tracker, JSONObject jsonObject) {
        String description = jsonObject.getString("description");
        double targetAmount = jsonObject.getDouble("targetAmount");
        LocalDate targetDate = LocalDate.parse(jsonObject.getString("targetDate"));
        boolean achieved = jsonObject.getBoolean("achieved");
        FinancialGoal financialGoal = new FinancialGoal(description, targetAmount, targetDate);
        tracker.addFinancialGoal(financialGoal);
    }

    // MODIFIES: tracker
    // EFFECTS: parses financialReminders from JSON object and adds it to tracker
    private void addFinancialReminders(Tracker tracker, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("financialReminders");
        for (Object json : jsonArray) {
            JSONObject nextFinancialReminders = (JSONObject) json;
            addFinancialReminder(tracker, nextFinancialReminders);
        }
    }

    // MODIFIES: tracker
    // EFFECTS: parses financialReminder from JSON object and adds it to tracker
    private void addFinancialReminder(Tracker tracker, JSONObject jsonObject) {
        String description = jsonObject.getString("description");
        double amount = jsonObject.getDouble("amount");
        LocalDate date = LocalDate.parse(jsonObject.getString("dueDate"));
        FinancialReminder financialReminder = new FinancialReminder(description, amount, date);
        tracker.addFinancialReminder(financialReminder);
    }
}
