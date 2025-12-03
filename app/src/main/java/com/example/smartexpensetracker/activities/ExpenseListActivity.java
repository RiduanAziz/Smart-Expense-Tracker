package com.example.smartexpensetracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartexpensetracker.R;
import com.example.smartexpensetracker.adapters.ExpenseAdapter;
import com.example.smartexpensetracker.database.ExpenseDAO;
import com.example.smartexpensetracker.models.Expense;

import java.util.List;

public class ExpenseListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView tvEmptyMessage;

    private ExpenseDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list);

        initViews();
        dao = new ExpenseDAO(this);

        loadExpenses();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.rvExpenses);
        tvEmptyMessage = findViewById(R.id.tvEmpty);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadExpenses() {
        List<Expense> expenseList = dao.getAllExpenses();

        if (expenseList == null || expenseList.isEmpty()) {
            tvEmptyMessage.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvEmptyMessage.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            ExpenseAdapter adapter = new ExpenseAdapter(expenseList, expense -> openEditExpense(expense.getId()));
            recyclerView.setAdapter(adapter);
        }
    }

    private void openEditExpense(long id) {
        Intent intent = new Intent(this, EditExpenseActivity.class);
        intent.putExtra("expense_id", id);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadExpenses(); // Auto-refresh after editing
    }
}
