package com.budgettracker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class BudgetManager {

    private final List<Transaction> transactions = new ArrayList<>();
    private int nextId = 1;

    private final Path jsonPath = Path.of("src/main/resources/data.json");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static final Type TX_LIST_TYPE =
        new com.google.gson.reflect.TypeToken<List<Transaction>>() {}.getType();

    public void addIncome(double amount, String category, String description, String date) {
        validate(amount, category);
        transactions.add(new Transaction(nextId++, TransactionType.INCOME, amount, category, description, date));
    }

    public void addExpense(double amount, String category, String description, String date) {
        validate(amount, category);
        transactions.add(new Transaction(nextId++, TransactionType.EXPENSE, amount, category, description, date));
    }

    public double getTotalIncome() {
        return transactions.stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getTotalExpense() {
        return transactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getBudget() {
        return getTotalIncome() - getTotalExpense();
    }

    public void loadFromJson() throws IOException {
        transactions.clear();
        if (!Files.exists(jsonPath)) return;
    
        try (BufferedReader br = Files.newBufferedReader(jsonPath)) {
            String content = br.lines().collect(Collectors.joining());
            if (content.isBlank()) return;
    
            List<Transaction> loaded = gson.fromJson(
                content,
                TX_LIST_TYPE
            );
            if (loaded != null) {
                transactions.addAll(loaded);
                nextId = transactions.stream().mapToInt(Transaction::getId).max().orElse(0) + 1;
            }
        }
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public boolean deleteById(int id) {
        return transactions.removeIf(t -> t.getId() == id);
    }

    public void saveToJson() throws IOException {
        String json = gson.toJson(transactions);
        try (BufferedWriter bw = Files.newBufferedWriter(
                jsonPath,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING)) {
            bw.write(json);
        }
    }

    private void validate(double amount, String category) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be > 0");
        if (category == null || category.trim().isEmpty())
            throw new IllegalArgumentException("Category must not be empty");
    }
}