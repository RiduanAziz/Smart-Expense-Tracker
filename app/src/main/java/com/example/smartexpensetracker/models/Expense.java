package com.example.smartexpensetracker.models;

public class Expense {

    private long id;
    private double amount;
    private int categoryId;
    private String date;
    private String note;

    // Constructor with ID
    public Expense(long id, double amount, int categoryId, String date, String note) {
        this.id = id;
        this.amount = amount;
        this.categoryId = categoryId;
        this.date = date;
        this.note = note;
    }

    // Constructor without ID (for inserting new expense)
    public Expense(double amount, int categoryId, String date, String note) {
        this.amount = amount;
        this.categoryId = categoryId;
        this.date = date;
        this.note = note;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
