package com.example.smartexpensetracker.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartexpensetracker.R;
import com.example.smartexpensetracker.database.ExpenseDAO;
import com.example.smartexpensetracker.models.Expense;

import java.util.ArrayList;

public class EditExpenseActivity extends AppCompatActivity {

    EditText etAmount, etDate, etNote;
    Spinner spCategory;
    Button btnUpdate, btnDelete;

    ExpenseDAO dao;
    Expense currentExpense;

    ArrayList<String> categories = new ArrayList<>();
    long expenseId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);

        initViews();
        dao = new ExpenseDAO(this);

        expenseId = getIntent().getLongExtra("expense_id", -1);

        if (expenseId == -1) {
            Toast.makeText(this, "Invalid Expense ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadCategories();
        loadExpenseData();
        setListeners();
    }

    private void initViews() {
        etAmount = findViewById(R.id.etAmount);
        etDate = findViewById(R.id.etDate);
        etNote = findViewById(R.id.etNote);
        spCategory = findViewById(R.id.spCategory);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
    }

    private void loadCategories() {
        categories.add("Food");
        categories.add("Transport");
        categories.add("Shopping");
        categories.add("Bills");
        categories.add("Entertainment");
        categories.add("Healthcare");
        categories.add("Education");
        categories.add("Others");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                categories
        );

        spCategory.setAdapter(adapter);
    }

    private void loadExpenseData() {
        currentExpense = dao.getExpenseById(expenseId);

        if (currentExpense == null) {
            Toast.makeText(this, "Expense not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        etAmount.setText(String.valueOf(currentExpense.getAmount()));
        etDate.setText(currentExpense.getDate());
        etNote.setText(currentExpense.getNote());

        // Category index = categoryId - 1
        int categoryIndex = currentExpense.getCategoryId() - 1;
        if (categoryIndex >= 0 && categoryIndex < categories.size()) {
            spCategory.setSelection(categoryIndex);
        }
    }

    private void setListeners() {
        btnUpdate.setOnClickListener(v -> updateExpense());

        btnDelete.setOnClickListener(v -> {
            boolean deleted = dao.deleteExpense(expenseId);

            if (deleted) {
                Toast.makeText(this, "Expense deleted successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to delete expense", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateExpense() {
        String amountStr = etAmount.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String note = etNote.getText().toString().trim();
        int categoryId = spCategory.getSelectedItemPosition() + 1;

        if (amountStr.isEmpty()) {
            Toast.makeText(this, "Enter amount", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount;

        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show();
            return;
        }

        currentExpense.setAmount(amount);
        currentExpense.setDate(date);
        currentExpense.setNote(note);
        currentExpense.setCategoryId(categoryId);

        boolean updated = dao.updateExpense(currentExpense);

        if (updated) {
            Toast.makeText(this, "Expense updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to update expense", Toast.LENGTH_SHORT).show();
        }
    }
}
