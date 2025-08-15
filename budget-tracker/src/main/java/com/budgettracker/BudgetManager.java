package com.budgettracker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class BudgetManager {

    private final List<Transaction> transactions = new ArrayList<>();
    private int nextId = 1;

    private final Path jsonPath = Path.of("src/main/resources/data.json");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void addIncome(double amount, String category, String description, String date) {
        validate(amount, category);
        transactions.add(new Transaction(nextId++, "INCOME", amount, category, description, date));
    }

    public void addExpense(double amount, String category, String description, String date) {
        validate(amount, category);
        transactions.add(new Transaction(nextId++, "EXPENSE", amount, category, description, date));
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

    public void loadFromJson() throws Exception {
        transactions.clear();
        if (!Files.exists(jsonPath)) return;

        String content = Files.readString(jsonPath).trim();
        if (content.isEmpty()) return;

        List<Transaction> loaded = gson.fromJson(
                content,
                new TypeToken<List<Transaction>>() {}.getType()
        );
        if (loaded != null) {
            transactions.addAll(loaded);
            nextId = transactions.stream().mapToInt(Transaction::getId).max().orElse(0) + 1;
        }
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void saveToJson() throws Exception {
        String json = gson.toJson(transactions);
        Files.writeString(
                jsonPath,
                json,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        );
    }

    private void validate(double amount, String category) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be > 0");
        if (category == null || category.trim().isEmpty())
            throw new IllegalArgumentException("Category must not be empty");
    }
}