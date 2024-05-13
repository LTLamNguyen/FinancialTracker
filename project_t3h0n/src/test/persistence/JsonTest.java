package persistence;

import model.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

//REFERENCE: JsonSerializationDemo - Paul Carter

public class JsonTest {
    protected void checkTransaction(String description, double amount, LocalDate date, TransactionType type,
                                    Transaction transaction) {
        assertEquals(description, transaction.getDescription());
        assertEquals(amount, transaction.getAmount());
        assertEquals(date, transaction.getDate());
        assertEquals(type, transaction.getTransactionType());
    }

    protected void checkBudget(String description, double limit,
                                    Budget budget) {
        assertEquals(description, budget.getDescription());
        assertEquals(limit, budget.getLimit());
    }

    protected void checkFinancialGoal(String description, double targetAmount, LocalDate targetDate,
                                    FinancialGoal financialGoal) {
        assertEquals(description, financialGoal.getDescription());
        assertEquals(targetAmount, financialGoal.getTargetAmount());
        assertEquals(targetDate, financialGoal.getTargetDate());
    }

    protected void checkFinancialReminder(String description, double amount, LocalDate dueDate,
                                    FinancialReminder financialReminder) {
        assertEquals(description, financialReminder.getDescription());
        assertEquals(amount, financialReminder.getAmount());
        assertEquals(dueDate, financialReminder.getDueDate());
    }

}