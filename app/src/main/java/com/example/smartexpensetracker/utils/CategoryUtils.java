package com.example.smartexpensetracker.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryUtils {

    // Default categories
    private static final List<String> DEFAULT_CATEGORIES = Arrays.asList(
            "Food",
            "Transport",
            "Entertainment",
            "Shopping",
            "Bills",
            "Health",
            "Education",
            "Travel",
            "Others"
    );

    /**
     * Get all default categories
     */
    public static List<String> getDefaultCategories() {
        return new ArrayList<>(DEFAULT_CATEGORIES);
    }

    /**
     * Check if a category is valid (exists in default categories)
     */
    public static boolean isValidCategory(String category) {
        return DEFAULT_CATEGORIES.contains(category);
    }

    /**
     * Optional: Get a color code for a category (hex string)
     */
    public static String getCategoryColor(String category) {
        switch (category) {
            case "Food":
                return "#FF9800"; // Orange
            case "Transport":
                return "#3F51B5"; // Indigo
            case "Entertainment":
                return "#E91E63"; // Pink
            case "Shopping":
                return "#9C27B0"; // Purple
            case "Bills":
                return "#009688"; // Teal
            case "Health":
                return "#4CAF50"; // Green
            case "Education":
                return "#FFC107"; // Amber
            case "Travel":
                return "#03A9F4"; // Light Blue
            default:
                return "#607D8B"; // Grey for Others
        }
    }

    /**
     * Optional: Add a custom category to the list (not persistent)
     */
    public static void addCustomCategory(String category) {
        if (!DEFAULT_CATEGORIES.contains(category)) {
            DEFAULT_CATEGORIES.add(category);
        }
    }
}
