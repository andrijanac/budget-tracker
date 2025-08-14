package com.budgettracker;

import java.util.Scanner;

public class App {

    public static void main(String[] args) throws Exception {

        BudgetManager bm = new BudgetManager();
        bm.loadFromJson();

        Scanner in = new Scanner(System.in);

        System.out.println("\nAdd a new transaction");

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

        if ("income".equalsIgnoreCase(type)) {
            bm.addIncome(amount, category, description, date);
        } else if ("expense".equalsIgnoreCase(type)) {
            bm.addExpense(amount, category, description, date);
        } else {
            System.out.println("Invalid type, must be INCOME or EXPENSE.");
            return;
        }

        bm.saveToJson();
        System.out.println("✓ Transaction saved to data.json");
    }
}