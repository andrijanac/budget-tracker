package com.budgettracker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    private static final Path JSON_PATH = Path.of("src/main/resources/data.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) throws Exception {
        
        List<Transaction> transactions = loadTransactions();

        Scanner in = new Scanner(System.in);

        System.out.println("Add a new transaction");

        System.out.print("Type (INCOME/EXPENSE): ");
        String type = in.nextLine().trim();

        System.out.print("Amount: ");
        double amount = Double.parseDouble(in.nextLine().trim());

        System.out.print("Category: ");
        String category = in.nextLine().trim();

        System.out.print("Description: ");
        String description = in.nextLine().trim();

        System.out.print("Date (YYYY-MM-DD): ");
        String date = in.nextLine().trim();

        int nextId = transactions.stream().mapToInt(Transaction::getId).max().orElse(0) + 1;

        Transaction t = new Transaction(nextId, type, amount, category, description, date);
        transactions.add(t);

        saveTransactions(transactions);

        System.out.println("✓ Transaction saved to data.json");
    }

    private static List<Transaction> loadTransactions() throws Exception {
        if (!Files.exists(JSON_PATH)) return new ArrayList<>();
        String content = Files.readString(JSON_PATH).trim();
        if (content.isEmpty()) return new ArrayList<>();
        return GSON.fromJson(content, new TypeToken<List<Transaction>>() {}.getType());
    }

    private static void saveTransactions(List<Transaction> transactions) throws Exception {
        String json = GSON.toJson(transactions);
        Files.writeString(
                JSON_PATH,
                json,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        );
    }
}