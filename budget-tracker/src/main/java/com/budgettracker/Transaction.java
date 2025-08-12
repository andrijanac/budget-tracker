package com.budgettracker;

public class Transaction {

    private int id;
    private String type;
    private double amount;
    private String category;
    private String description;
    private String date;
    
    public Transaction() {
    }

    public Transaction(int id, String type, double amount, String category, String description, String date) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction [id=" + id + ", type=" + type + ", amount=" + amount + ", category=" + category
                + ", description=" + description + ", date=" + date + "]";
    }

}