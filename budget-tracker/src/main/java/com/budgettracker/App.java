package com.budgettracker;

import java.nio.file.Files;
import java.nio.file.Path;

public class App {
    public static void main(String[] args) throws Exception {

        Path json = Path.of("src/main/resources/data.json");
        String content = Files.readString(json);
        System.out.println("=== data.json ===");
        System.out.println(content);
        System.out.println("=================");
        System.out.println("File length: " + content.length() + " characters");

    }
}