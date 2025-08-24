package com.budgettracker;

import java.util.Scanner;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class App {

    public static void main(String[] args) throws Exception {

        BudgetManager bm = new BudgetManager();
        bm.loadFromJson();

        Scanner in = new Scanner(System.in, StandardCharsets.UTF_8);

        while (true) {
            System.out.println("\n==== Budget Tracker ====");
            System.out.println("1) Add INCOME");
            System.out.println("2) Add EXPENSE");
            System.out.println("3) Show totals");
            System.out.println("4) List transactions");
            System.out.println("5) Delete by ID");
            System.out.println("6) Exit");
            System.out.print("Choose: ");

            String choice = in.nextLine().trim();

            switch (choice) {
                case "1":
                    addTransactionViaConsole(in, bm, TransactionType.INCOME);
                    break;

                case "2":
                    addTransactionViaConsole(in, bm, TransactionType.EXPENSE);
                    break;

                case "3":
                    System.out.printf("Total income : %.2f%n", bm.getTotalIncome());
                    System.out.printf("Total expense: %.2f%n", bm.getTotalExpense());
                    System.out.printf("Budget       : %.2f%n", bm.getBudget());
                    break;

                case "4":
                    if (bm.getTransactions().isEmpty()) {
                        System.out.println("No transactions yet.");
                    } else {
                        bm.getTransactions().forEach(t -> System.out.printf(
                            "#%d | %s | %.2f | %s | %s | %s%n",
                            t.getId(), t.getType(), t.getAmount(),
                            t.getCategory(), t.getDescription(), t.getDate()
                        ));
                    }
                    break;

                case "5": {
                        if (bm.getTransactions().isEmpty()) {
                            System.out.println("No transactions to delete.");
                            break;
                        }
                    
                        bm.getTransactions().forEach(t ->
                            System.out.printf("#%d | %s | %.2f | %s | %s%n",
                                t.getId(), t.getType(), t.getAmount(), t.getCategory(), t.getDate())
                        );
                    
                        System.out.print("Enter ID to delete: ");
                        int id = Integer.parseInt(in.nextLine().trim());
                    
                        boolean deleted = bm.deleteById(id);
                        if (deleted) {
                            bm.saveToJson();
                            System.out.println("Deleted and saved.");
                        } else {
                            System.out.println("ID not found.");
                        }
                        break;
                    }

                case "6":
                    System.out.println("Bye!");
                    return;

                default:
                    System.out.println("Invalid option. Please choose 1–5.");
            }
        }
    }

    private static void addTransactionViaConsole(Scanner in, BudgetManager bm, TransactionType type) {

        try {
            System.out.print("Amount: ");
            double amount = Double.parseDouble(in.nextLine().trim());

            System.out.print("Category name: ");
            String categoryName = in.nextLine().trim();

            Category category = new Category(0, categoryName);

            System.out.print("Description: ");
            String description = in.nextLine().trim();

            System.out.print("Date (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(in.nextLine().trim());

            if (type == TransactionType.INCOME) {
                bm.addIncome(amount, category, description, date);
            } else {
                bm.addExpense(amount, category, description, date);
            }

            bm.saveToJson();
            System.out.println("Transaction saved.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Invalid input: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Error while saving: " + ex.getMessage());
        }
    }
}