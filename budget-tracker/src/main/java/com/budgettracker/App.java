package com.budgettracker;

import java.util.Scanner;

public class App {

    public static void main(String[] args) throws Exception {

        BudgetManager bm = new BudgetManager();
        bm.loadFromJson();

        Scanner in = new Scanner(System.in);

        while (true) {
            System.out.println("\n==== Budget Tracker ====");
            System.out.println("1) Add INCOME");
            System.out.println("2) Add EXPENSE");
            System.out.println("3) Show totals");
            System.out.println("4) List transactions");
            System.out.println("5) Exit");
            System.out.print("Choose: ");

            String choice = in.nextLine().trim();

            switch (choice) {
                case "1":
                    addTransactionViaConsole(in, bm, "INCOME");
                    break;

                case "2":
                    addTransactionViaConsole(in, bm, "EXPENSE");
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

                case "5":
                    System.out.println("Bye!");
                    return;

                default:
                    System.out.println("Invalid option. Please choose 1–5.");
            }
        }
    }

    private static void addTransactionViaConsole(Scanner in, BudgetManager bm, String type) {

        try {
            System.out.print("Amount: ");
            double amount = Double.parseDouble(in.nextLine().trim());

            System.out.print("Category: ");
            String category = in.nextLine().trim();

            System.out.print("Description: ");
            String description = in.nextLine().trim();

            System.out.print("Date (YYYY-MM-DD): ");
            String date = in.nextLine().trim();

            if ("INCOME".equals(type)) {
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