package com.example.smartexpensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartexpensetracker.activities.AddExpenseActivity;
import com.example.smartexpensetracker.activities.ExpenseListActivity;
import com.example.smartexpensetracker.activities.SummaryReportActivity;
import com.example.smartexpensetracker.activities.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    Button btnAddExpense, btnViewExpenses, btnSummary, btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setListeners();
    }

    private void initViews() {
        btnAddExpense = findViewById(R.id.btnAddExpense);
        btnViewExpenses = findViewById(R.id.btnViewExpenses);
        btnSummary = findViewById(R.id.btnSummary);
        btnSettings = findViewById(R.id.btnSettings);
    }

    private void setListeners() {

        btnAddExpense.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AddExpenseActivity.class)));

        btnViewExpenses.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ExpenseListActivity.class)));

        btnSummary.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SummaryReportActivity.class)));

        btnSettings.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SettingsActivity.class)));
    }
}
