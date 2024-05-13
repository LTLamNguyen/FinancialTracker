package persistence;

import model.*;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//REFERENCE: JsonSerializationDemo - Paul Carter

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Tracker tracker = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTracker.json");
        try {
            Tracker tracker = reader.read();
            assertEquals(0, tracker.numTransactions());
            assertEquals(0, tracker.numBudgets());
            assertEquals(0, tracker.numFinancialGoals());
            assertEquals(0, tracker.numFinancialReminders());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTracker.json");
        Transaction t1 = new Transaction("coffee", 5,
                LocalDate.of(2023, 10, 10), TransactionType.EXPENSE);

        Budget b1 = new Budget("grocery", 1000);

        FinancialGoal fg1 = new FinancialGoal("car", 80000,
                LocalDate.of(2024, 9, 5));

        FinancialReminder fr1 = new FinancialReminder("phone plan", 50,
                LocalDate.of(2023, 11, 6));
        try {

            Tracker tracker = reader.read();
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
            fail("Couldn't read from file");
        }
    }
}