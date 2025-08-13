package com.budgettracker;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BudgetManagerTest {

    @Test
    void startsEmpty() {
        BudgetManager bm = new BudgetManager();
        assertEquals(0.0, bm.getTotalIncome(), 1e-6);
        assertEquals(0.0, bm.getTotalExpense(), 1e-6);
        assertEquals(0.0, bm.getBudget(), 1e-6);
    }

    @Test
    void totalsWork() {
        BudgetManager bm = new BudgetManager();
        bm.addIncome(1000, "Salary", "Pay", "2025-08-13");
        bm.addExpense(300, "Groceries", "Food", "2025-08-13");

        assertEquals(1000.0, bm.getTotalIncome(), 1e-6);
        assertEquals(300.0,  bm.getTotalExpense(), 1e-6);
        assertEquals(700.0,  bm.getBudget(), 1e-6);
    }

    @Test
    void handlesDecimals() {
        BudgetManager bm = new BudgetManager();
        bm.addIncome(99.99, "Gift", "Mom", "2025-08-13");
        bm.addExpense(0.49, "Fee", "Bank", "2025-08-13");

        assertEquals(99.99, bm.getTotalIncome(), 1e-6);
        assertEquals(0.49, bm.getTotalExpense(), 1e-6);
        assertEquals(99.50, bm.getBudget(), 1e-6);
    }

    @Test
    void rejectsBadInput() {
        BudgetManager bm = new BudgetManager();
        assertThrows(IllegalArgumentException.class,
                () -> bm.addIncome(0, "Salary", "Nope", "2025-08-13"));
    }
}