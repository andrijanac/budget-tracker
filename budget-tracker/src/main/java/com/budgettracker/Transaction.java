package com.budgettracker;

import java.time.LocalDate;

public class Transaction {

    private int id;
    private TransactionType type;
    private double amount;
    private Category category;
    private String description;
    private LocalDate date;
    
    public Transaction() {
    }

    public Transaction(int id, TransactionType type, double amount, Category category, String description, LocalDate date) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction [id=" + id + ", type=" + type.name() + ", amount=" + amount + ", category=" + category.getName()
                + ", description=" + description + ", date=" + date + "]";
    }

}