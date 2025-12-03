package com.example.smartexpensetracker.utils;

import android.content.Context;

import com.example.smartexpensetracker.database.ExpenseDAO;
import com.example.smartexpensetracker.models.Expense;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryPieChart {

    private Context context;
    private ExpenseDAO expenseDAO;

    public CategoryPieChart(Context context) {
        this.context = context;
        expenseDAO = new ExpenseDAO(context);
    }

    /**
     * Populate a PieChart with category-wise expense data
     */
    public void showCategoryPieChart(PieChart pieChart) {
        List<Expense> expenses = expenseDAO.getAllExpenses();

        // Calculate total per category
        Map<String, Float> categoryMap = new HashMap<>();
        for (Expense expense : expenses) {
            String category = expense.getCategory();
            float amount = (float) expense.getAmount();
            if (categoryMap.containsKey(category)) {
                categoryMap.put(category, categoryMap.get(category) + amount);
            } else {
                categoryMap.put(category, amount);
            }
        }

        // Prepare PieEntries
        List<PieEntry> entries = new ArrayList<>();
        for (Map.Entry<String, Float> entry : categoryMap.entrySet()) {
            entries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }

        // Create PieDataSet
        PieDataSet dataSet = new PieDataSet(entries, "Expenses by Category");
        dataSet.setColors(new int[]{
                android.graphics.Color.parseColor("#FF9800"), // Food
                android.graphics.Color.parseColor("#3F51B5"), // Transport
                android.graphics.Color.parseColor("#E91E63"), // Entertainment
                android.graphics.Color.parseColor("#9C27B0"), // Shopping
                android.graphics.Color.parseColor("#009688"), // Bills
                android.graphics.Color.parseColor("#4CAF50"), // Health
                android.graphics.Color.parseColor("#FFC107"), // Education
                android.graphics.Color.parseColor("#03A9F4"), // Travel
                android.graphics.Color.parseColor("#607D8B")  // Others
        }, context);

        dataSet.setSliceSpace(3f);
        dataSet.setValueTextSize(12f);

        // Create PieData
        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setEntryLabelTextSize(12f);
        pieChart.setCenterText("Expenses");
        pieChart.setCenterTextSize(16f);
        pieChart.animateY(1000);

        // Configure Legend
        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);

        pieChart.invalidate(); // Refresh chart
    }
}
