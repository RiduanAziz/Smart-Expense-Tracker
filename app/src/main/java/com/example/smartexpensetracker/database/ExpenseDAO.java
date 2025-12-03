package com.example.smartexpensetracker.database;

import android.content.Context;

import com.example.smartexpensetracker.models.Expense;

import java.util.List;

public class ExpenseDAO {

    private final DBHelper dbHelper;

    public ExpenseDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Get all expenses
    public List<Expense> getAllExpenses() {
        return dbHelper.getAllExpenses();
    }

    // Update an expense
    public boolean updateExpense(Expense expense) {
        return dbHelper.updateExpense(expense);
    }

    // Delete an expense
    public boolean deleteExpense(long id) {
        dbHelper.deleteExpense((int) id);
        return false;
    }

    public long addExpense(Expense expense) {
        return dbHelper.addExpense(expense);
    }

    public Expense getExpenseById(long expenseId) {
        return dbHelper.getExpenseById(expenseId);
    }

}
