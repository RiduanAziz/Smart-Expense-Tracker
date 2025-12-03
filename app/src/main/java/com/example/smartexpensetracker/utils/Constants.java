package com.example.smartexpensetracker.utils;

public class Constants {

    // Database constants
    public static final String DATABASE_NAME = "expenses_db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_EXPENSES = "expenses";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_DATE = "date";

    // Shared Preferences constants
    public static final String PREF_NAME = "smart_expense_pref";
    public static final String PREF_FIRST_RUN = "is_first_run";

    // Default currency symbol
    public static final String DEFAULT_CURRENCY = "$";

    // Default date formats
    public static final String DB_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DISPLAY_DATE_FORMAT = "dd MMM yyyy";

    // Intent extras keys
    public static final String EXTRA_EXPENSE_ID = "extra_expense_id";

    // Default values
    public static final double DEFAULT_AMOUNT = 0.0;

    private Constants() {
        // private constructor to prevent instantiation
    }
}
