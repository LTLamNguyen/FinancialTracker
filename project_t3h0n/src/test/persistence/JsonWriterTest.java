package persistence;

import model.*;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//REFERENCE: JsonSerializationDemo - Paul Carter

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Tracker tracker = new Tracker();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Tracker tracker = new Tracker();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTracker.json");
            writer.open();
            writer.write(tracker);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTracker.json");
            tracker = reader.read();
            assertEquals(0, tracker.numTransactions());
            assertEquals(0, tracker.numBudgets());
            assertEquals(0, tracker.numFinancialGoals());
            assertEquals(0, tracker.numFinancialReminders());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Tracker tracker = new Tracker();

            Transaction t1 = new Transaction("coffee", 5,
                    LocalDate.of(2023, 10, 10), TransactionType.EXPENSE);
            tracker.addTransaction(t1);

            Budget b1 = new Budget("grocery", 1000);
            tracker.addBudget(b1);

            FinancialGoal fg1 = new FinancialGoal("car", 80000,
                    LocalDate.of(2024, 9, 5));
            tracker.addFinancialGoal(fg1);

            FinancialReminder fr1 = new FinancialReminder("phone plan", 50,
                    LocalDate.of(2023, 11, 6));
            tracker.addFinancialReminder(fr1);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTracker.json");
            writer.open();
            writer.write(tracker);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTracker.json");
            tracker = reader.read();
            List<Transaction> t1s = tracker.getTransactions();
            assertEquals(1, t1s.size());
            List<Budget> b1s = tracker.getBudgets();
            assertEquals(1, b1s.size());
            List<Budget> fg1s = tracker.getBudgets();
            assertEquals(1, fg1s.size());
            List<Budget> fr1s = tracker.getBudgets();
            assertEquals(1, fr1s.size());
            checkTransaction(t1.getDescription(), t1.getAmount(), t1.getDate(), t1.getTransactionType(), t1);
            checkBudget(b1.getDescription(), b1.getLimit(), b1);
            checkFinancialGoal(fg1.getDescription(), fg1.getTargetAmount(), fg1.getTargetDate(), fg1);
            checkFinancialReminder(fr1.getDescription(), fr1.getAmount(), fr1.getDueDate(), fr1);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}