package com.budgettracker;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class App {
    public static void main(String[] args) throws Exception {

        Path jsonPath = Path.of("src/main/resources/data.json");
        String content = Files.readString(jsonPath);

        Gson gson = new Gson();
        List<Transaction> transactions = gson.fromJson(
            content,
            new TypeToken<List<Transaction>>() {}.getType()
        );

        System.out.println("Total transactions: " + transactions.size());
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }
}