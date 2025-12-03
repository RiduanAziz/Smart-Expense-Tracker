package com.example.smartexpensetracker.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartexpensetracker.R;
import com.example.smartexpensetracker.database.DBHelper;
import com.example.smartexpensetracker.models.Expense;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText etAmount, etDate, etNote;
    private Spinner spCategory;
    private Button btnSave;

    private DBHelper dbHelper;
    private final ArrayList<String> categories = new ArrayList<>();
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        initViews();
        dbHelper = new DBHelper(this);

        loadCategories();
        setupDatePicker();
        setListeners();
    }

    private void initViews() {
        etAmount = findViewById(R.id.etAmount);
        etDate = findViewById(R.id.etDate);
        etNote = findViewById(R.id.etNote);
        spCategory = findViewById(R.id.spCategory);
        btnSave = findViewById(R.id.btnSave);

        calendar = Calendar.getInstance();
        // Set default date to today
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        etDate.setText(sdf.format(calendar.getTime()));
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

    private void setupDatePicker() {
        etDate.setOnClickListener(v -> {
            DatePickerDialog dialog = new DatePickerDialog(
                    AddExpenseActivity.this,
                    (view, year, month, dayOfMonth) -> {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        etDate.setText(sdf.format(calendar.getTime()));
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            dialog.show();
        });
    }

    private void setListeners() {
        btnSave.setOnClickListener(v -> saveExpense());
    }

    private void saveExpense() {
        String amountStr = etAmount.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String note = etNote.getText().toString().trim();
        int categoryId = spCategory.getSelectedItemPosition() + 1; // IDs start from 1

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

        Expense expense = new Expense(amount, categoryId, date, note);

        long id = dbHelper.addExpense(expense);
        if (id > 0) {
            Toast.makeText(this, "Expense added successfully", Toast.LENGTH_SHORT).show();
            finish(); // Close activity after saving
        } else {
            Toast.makeText(this, "Failed to add expense", Toast.LENGTH_SHORT).show();
        }
    }
}
