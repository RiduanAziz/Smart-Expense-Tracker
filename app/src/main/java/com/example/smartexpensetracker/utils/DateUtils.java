package com.example.smartexpensetracker.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    // Default date format for storing in DB
    private static final String DB_DATE_FORMAT = "yyyy-MM-dd";

    // User-friendly display format
    private static final String DISPLAY_DATE_FORMAT = "dd MMM yyyy";

    /**
     * Convert a Date object to a DB-friendly string (yyyy-MM-dd)
     */
    public static String formatDateForDB(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DB_DATE_FORMAT, Locale.getDefault());
        return sdf.format(date);
    }

    /**
     * Convert a Date object to a user-friendly string (dd MMM yyyy)
     */
    public static String formatDateForDisplay(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DISPLAY_DATE_FORMAT, Locale.getDefault());
        return sdf.format(date);
    }

    /**
     * Parse a DB date string (yyyy-MM-dd) to Date object
     */
    public static Date parseDBDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(DB_DATE_FORMAT, Locale.getDefault());
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Parse a display date string (dd MMM yyyy) to Date object
     */
    public static Date parseDisplayDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(DISPLAY_DATE_FORMAT, Locale.getDefault());
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get current date as DB string (yyyy-MM-dd)
     */
    public static String getCurrentDateForDB() {
        return formatDateForDB(new Date());
    }

    /**
     * Get current date as display string (dd MMM yyyy)
     */
    public static String getCurrentDateForDisplay() {
        return formatDateForDisplay(new Date());
    }
}
