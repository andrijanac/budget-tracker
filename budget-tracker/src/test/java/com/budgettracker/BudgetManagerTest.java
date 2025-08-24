package com.budgettracker;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

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
        Category salary = new Category(1, "Salary");
        Category groceries = new Category(2, "Groceries");

        bm.addIncome(1000, salary, "Pay", LocalDate.of(2025, 8, 13));
        bm.addExpense(300, groceries, "Food", LocalDate.of(2025, 8, 13));

        assertEquals(1000.0, bm.getTotalIncome(), 1e-6);
        assertEquals(300.0,  bm.getTotalExpense(), 1e-6);
        assertEquals(700.0,  bm.getBudget(), 1e-6);
    }

    @Test
    void handlesDecimals() {
        BudgetManager bm = new BudgetManager();
        Category gift = new Category(3, "Gift");
        Category fee = new Category(4, "Fee");

        bm.addIncome(99.99, gift, "Mom", LocalDate.of(2025, 8, 13));
        bm.addExpense(0.49, fee, "Bank", LocalDate.of(2025, 8, 13));

        assertEquals(99.99, bm.getTotalIncome(), 1e-6);
        assertEquals(0.49, bm.getTotalExpense(), 1e-6);
        assertEquals(99.50, bm.getBudget(), 1e-6);
    }

    @Test
    void rejectsBadInput() {
        BudgetManager bm = new BudgetManager();
        Category salary = new Category(1, "Salary");

        assertThrows(IllegalArgumentException.class,
                () -> bm.addIncome(0, salary, "No", LocalDate.of(2025, 8, 13)));
        assertThrows(IllegalArgumentException.class,
                () -> bm.addIncome(10, null, "No", LocalDate.of(2025, 8, 13)));
    }

    @Test
    void deleteByIdRemovesAndUpdatesTotals() {
        BudgetManager bm = new BudgetManager();
        Category salary = new Category(1, "Salary");
        Category groceries = new Category(2, "Groceries");
        Category snacks = new Category(3, "Snacks");

        bm.addIncome(1000, salary, "Pay", LocalDate.of(2025, 8, 13));
        bm.addExpense(300, groceries, "Food", LocalDate.of(2025, 8, 13));
        bm.addExpense(50, snacks, "Chips", LocalDate.of(2025, 8, 13));

        boolean removed = bm.deleteById(2);

        assertTrue(removed);
        assertEquals(1000.0, bm.getTotalIncome(), 1e-6);
        assertEquals(50.0,   bm.getTotalExpense(), 1e-6);
        assertEquals(950.0,  bm.getBudget(), 1e-6);
    }

    @Test
    void deleteByIdReturnsFalseIfNotFound() {
        BudgetManager bm = new BudgetManager();
        Category gift = new Category(3, "Gift");

        bm.addIncome(200, gift, "Mom", LocalDate.of(2025, 8, 13));

        assertFalse(bm.deleteById(999));
        assertEquals(200.0, bm.getTotalIncome(), 1e-6);
        assertEquals(0.0,   bm.getTotalExpense(), 1e-6);
        assertEquals(200.0, bm.getBudget(), 1e-6);
    }

}