package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrackerTest {
    private Transaction t1, t2;
    private Budget b1, b2;
    private FinancialGoal fg1, fg2;
    private FinancialReminder fr1, fr2;
    private Tracker tracker;

    @BeforeEach
    void setUp() {
        t1 = new Transaction("cake", 20, LocalDate.of(2023,11,17), TransactionType.EXPENSE);
        t2 = new Transaction("salary", 15000, LocalDate.of(2022,1,20), TransactionType.INCOME);
        b1 = new Budget("grocery September", 500);
        b2 = new Budget("ETFs", 10000);
        fg1 = new FinancialGoal("New Car", 80000, LocalDate.of(2024,8,21));
        fg2 = new FinancialGoal("Savings", 5000, LocalDate.of(2023, 12, 25));
        fr1 = new FinancialReminder("Rent", 1500, LocalDate.of(2023, 12, 25));
        fr2 = new FinancialReminder("Phone Plan", 55, LocalDate.of(2023,10,11));
        tracker = new Tracker();
    }

    @Test
    void testAddTransaction() {
        tracker.addTransaction(t1);
        List<Transaction> transactions = tracker.getTransactions();

        assertEquals(1, transactions.size());
        assertEquals(t1, transactions.get(0));
        assertEquals("cake", t1.getDescription());
        assertEquals(20, t1.getAmount());
        assertEquals(LocalDate.of(2023,11,17), t1.getDate());
        assertEquals(TransactionType.EXPENSE, t1.getTransactionType());

        Transaction t11 = transactions.get(0);
        t11.setDescription("drinks");
        t11.setAmount(150);
        t11.setDate(LocalDate.of(2023, 11, 16));
        t11.setTransactionType(TransactionType.EXPENSE);
        assertEquals("Description: drinks, Amount: 150.0, " +
                "Date: 2023-11-16, Type: EXPENSE" , t11.toString());
    }

    @Test
    void testGetTransactions() {
        tracker.addTransaction(t1);
        tracker.addTransaction(t2);
        List<Transaction> transactions = tracker.getTransactions();
        assertEquals(2, transactions.size());
        assertEquals(t1, transactions.get(0));
        assertEquals(t2, transactions.get(1));

    }

    @Test
    void testAddBudget() {
        tracker.addBudget(b1);
        List<Budget> budgets = tracker.getBudgets();

        assertEquals(1, budgets.size());
        assertEquals("grocery September", b1.getDescription() );
        assertEquals(500, b1.getLimit());

        Budget b11 = budgets.get(0);
        b11.setDescription("grocery October");
        b11.setLimit(450);
        assertEquals("Category: grocery October, Limit: 450.0", b11.toString());
    }

    @Test
    void testGetBudgets() {
        tracker.addBudget(b1);
        tracker.addBudget(b2);
        List<Budget> budgets = tracker.getBudgets();
        assertEquals(2, budgets.size());
        assertEquals(b1, budgets.get(0));
        assertEquals(b2, budgets.get(1));
    }

    @Test
    void testAddFinancialGoal() {
        tracker.addFinancialGoal(fg1);
        List<FinancialGoal> financialGoals = tracker.getFinancialGoals();

        assertEquals(1, financialGoals.size());
        assertEquals("New Car", fg1.getDescription());
        assertEquals(80000, fg1.getTargetAmount());
        assertEquals(LocalDate.of(2024,8,21), fg1.getTargetDate());

        FinancialGoal fg11 = financialGoals.get(0);
        fg11.setDescription("New Jacket");
        fg11.setTargetAmount(100);
        fg11.setTargetDate(LocalDate.of(2023, 12, 26));
        fg11.setAchieved(true);
        assertEquals("Description: New Jacket, Target Amount: 100.0, " +
                "Target Date: 2023-12-26, Status: true", fg11.toString());
    }

    @Test
    void testGetFinancialGoals() {
        tracker.addFinancialGoal(fg1);
        tracker.addFinancialGoal(fg2);
        List<FinancialGoal> financialGoals = tracker.getFinancialGoals();
        assertEquals(2, financialGoals.size());
        assertEquals(fg1, financialGoals.get(0));
        assertEquals(fg2, financialGoals.get(1));
    }

    @Test
    void testMarkGoalAsAchieved() {
        tracker.addFinancialGoal(fg1);
        tracker.addFinancialGoal(fg2);
        tracker.markGoalAsAchieved(0);
        tracker.markGoalAsAchieved(3);
        List<FinancialGoal> financialGoals = tracker.getFinancialGoals();
        FinancialGoal fg11 = financialGoals.get(0);
        FinancialGoal fg22 = financialGoals.get(1);
        assertEquals(2, financialGoals.size());
        assertTrue(fg11.isAchieved());
        assertFalse(fg22.isAchieved());
    }

    @Test
    public void testMarkGoalAsAchievedInvalidIndex() {
        tracker.markGoalAsAchieved(-1);

        List<FinancialGoal> goals = tracker.getFinancialGoals();
        assertTrue(goals.isEmpty());
    }

    @Test
    void testDeleteGoal() {
        tracker.addFinancialGoal(fg1);
        tracker.addFinancialGoal(fg2);
        tracker.deleteGoal(0);
        tracker.deleteGoal(3);
        List<FinancialGoal> financialGoals = tracker.getFinancialGoals();
        assertEquals(1, financialGoals.size());
        assertEquals(fg2, financialGoals.get(0));
        tracker.deleteReminder(3);
        assertEquals(fg2, financialGoals.get(0));


    }

    @Test
    public void testDeleteGoalInvalidIndex() {
        tracker.deleteGoal(-1);

        List<FinancialGoal> goals = tracker.getFinancialGoals();
        assertTrue(goals.isEmpty());
    }

    @Test
    void testAddFinancialReminder() {
        tracker.addFinancialReminder(fr1);
        List<FinancialReminder> financialReminders = tracker.getFinancialReminders();
        assertEquals(1, financialReminders.size());

        FinancialReminder fr11 = financialReminders.get(0);
        assertEquals("Rent", fr11.getDescription());
        assertEquals(1500, fr11.getAmount());
        assertEquals(LocalDate.of(2023, 12, 25), fr11.getDueDate());

        fr11.setDescription("Insurance");
        fr11.setAmount(200);
        fr11.setDueDate(LocalDate.of(2023,10,10));
        fr11.setAchieved(true);
        assertEquals("Description: Insurance, amount: 200.0"
                 + ", Due Date: 2023-10-10, Status: true", fr11.toString());

    }

    @Test
    void testGetFinancialReminder() {
        tracker.addFinancialReminder(fr1);
        tracker.addFinancialReminder(fr2);
        List<FinancialReminder> financialReminders = tracker.getFinancialReminders();
        assertEquals(2, financialReminders.size());
        assertEquals(fr1, financialReminders.get(0));
        assertEquals(fr2, financialReminders.get(1));
    }

    @Test
    void testMarkReminderAsAchieved() {
        tracker.addFinancialReminder(fr1);
        tracker.addFinancialReminder(fr2);
        tracker.markReminderAsCompleted(0);
        tracker.markReminderAsCompleted(3);
        List<FinancialReminder> financialReminders = tracker.getFinancialReminders();
        FinancialReminder fr11 = financialReminders.get(0);
        FinancialReminder fr22 = financialReminders.get(1);
        assertEquals(2, financialReminders.size());
        assertTrue(fr11.isAchieved());
        assertFalse(fr22.isAchieved());
    }

    @Test
    public void testMarkReminderAsAchievedInvalidIndex() {
        tracker.markReminderAsCompleted(-1);

        List<FinancialReminder> reminders = tracker.getFinancialReminders();
        assertTrue(reminders.isEmpty());
    }
    @Test
    void testDeleteReminder() {
        tracker.addFinancialReminder(fr1);
        tracker.addFinancialReminder(fr2);
        tracker.deleteReminder(0);
        List<FinancialReminder> financialReminders = tracker.getFinancialReminders();
        assertEquals(1, financialReminders.size());
        assertEquals(fr2, financialReminders.get(0));
        tracker.deleteReminder(3);
        assertEquals(fr2, financialReminders.get(0));
    }

    @Test
    public void testDeleteReminderInvalidIndex() {
        tracker.deleteReminder(-1);

        List<FinancialReminder> reminders = tracker.getFinancialReminders();
        assertTrue(reminders.isEmpty());
    }

}