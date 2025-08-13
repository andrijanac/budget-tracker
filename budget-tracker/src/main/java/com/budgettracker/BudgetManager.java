package com.budgettracker;

import java.util.ArrayList;
import java.util.List;

public class BudgetManager {

    private final List<Transaction> transactions = new ArrayList<>();
    private int nextId = 1;

    public void addIncome(double amount, String category, String description, String date) {
        validate(amount, category);
        transactions.add(new Transaction(nextId++, "income", amount, category, description, date));
    }

    public void addExpense(double amount, String category, String description, String date) {
        validate(amount, category);
        transactions.add(new Transaction(nextId++, "expense", amount, category, description, date));
    }

    public double getTotalIncome() {
        return transactions.stream()
                .filter(t -> "income".equalsIgnoreCase(t.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getTotalExpense() {
        return transactions.stream()
                .filter(t -> "expense".equalsIgnoreCase(t.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getBudget() {
        return getTotalIncome() - getTotalExpense();
    }

    private void validate(double amount, String category) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be > 0");
        if (category == null || category.trim().isEmpty())
            throw new IllegalArgumentException("Category must not be empty");
    }
}