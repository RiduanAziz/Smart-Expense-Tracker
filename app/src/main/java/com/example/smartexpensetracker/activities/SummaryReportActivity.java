package com.example.smartexpensetracker.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartexpensetracker.R;
import com.example.smartexpensetracker.database.ExpenseDAO;
import com.example.smartexpensetracker.models.Expense;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SummaryReportActivity extends AppCompatActivity {

    TextView tvTodayTotal, tvWeekTotal, tvMonthTotal, tvCategorySummary;
    ExpenseDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_report);

        initViews();
        dao = new ExpenseDAO(this);

        generateSummaryReport();
    }

    private void initViews() {
        tvTodayTotal = findViewById(R.id.tvTodayTotal);
        tvWeekTotal = findViewById(R.id.tvWeekTotal);
        tvMonthTotal = findViewById(R.id.tvMonthTotal);
        tvCategorySummary = findViewById(R.id.tvCategorySummary);
    }

    private void generateSummaryReport() {
        List<Expense> allExpenses = dao.getAllExpenses();

        double todayTotal = 0;
        double weekTotal = 0;
        double monthTotal = 0;

        HashMap<Integer, Double> categoryMap = new HashMap<>();

        String today = getTodayDate();
        int currentWeek = getCurrentWeek();
        int currentMonth = getCurrentMonth();

        for (Expense e : allExpenses) {

            // Today Total
            if (e.getDate().equals(today)) {
                todayTotal += e.getAmount();
            }

            // Week Total
            if (getWeekFromDate(e.getDate()) == currentWeek) {
                weekTotal += e.getAmount();
            }

            // Month Total
            if (getMonthFromDate(e.getDate()) == currentMonth) {
                monthTotal += e.getAmount();
            }

            // Category Summary
            double prev = categoryMap.containsKey(e.getCategoryId())
                    ? categoryMap.get(e.getCategoryId()) : 0;
            categoryMap.put(e.getCategoryId(), prev + e.getAmount());
        }

        // Set Totals
        tvTodayTotal.setText("Today: " + todayTotal + " BDT");
        tvWeekTotal.setText("This Week: " + weekTotal + " BDT");
        tvMonthTotal.setText("This Month: " + monthTotal + " BDT");

        // Category Text Summary
        StringBuilder builder = new StringBuilder();

        for (Integer catId : categoryMap.keySet()) {
            builder.append(getCategoryName(catId))
                    .append(": ")
                    .append(categoryMap.get(catId))
                    .append(" BDT\n");
        }

        tvCategorySummary.setText(builder.toString());
    }

    // --- Helper Methods ---

    private String getTodayDate() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().getTime());
    }

    private int getCurrentWeek() {
        return Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
    }

    private int getCurrentMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    private int getWeekFromDate(String dateStr) {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateStr));
            return c.get(Calendar.WEEK_OF_YEAR);
        } catch (Exception e) {
            return -1;
        }
    }

    private int getMonthFromDate(String dateStr) {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateStr));
            return c.get(Calendar.MONTH) + 1;
        } catch (Exception e) {
            return -1;
        }
    }

    private String getCategoryName(int id) {
        switch (id) {
            case 1: return "Food";
            case 2: return "Transport";
            case 3: return "Shopping";
            case 4: return "Bills";
            case 5: return "Entertainment";
            case 6: return "Healthcare";
            case 7: return "Education";
            case 8: return "Others";
            default: return "Unknown";
        }
    }
}
