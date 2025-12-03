package com.example.smartexpensetracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.smartexpensetracker.models.Expense;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "expenses_db";
    private static final int DATABASE_VERSION = 1;

    // Expense table
    private static final String TABLE_EXPENSES = "expenses";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_CATEGORY_ID = "category_id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_NOTE = "note";

    // Settings table
    private static final String TABLE_SETTINGS = "settings";
    private static final String COLUMN_KEY = "key";
    private static final String COLUMN_VALUE = "value";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create expenses table
        String CREATE_EXPENSES_TABLE = "CREATE TABLE " + TABLE_EXPENSES + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_AMOUNT + " REAL, "
                + COLUMN_CATEGORY_ID + " INTEGER, "
                + COLUMN_DATE + " TEXT, "
                + COLUMN_NOTE + " TEXT"
                + ")";
        db.execSQL(CREATE_EXPENSES_TABLE);

        // Create settings table
        String CREATE_SETTINGS_TABLE = "CREATE TABLE " + TABLE_SETTINGS + " ("
                + COLUMN_KEY + "TEXT PRIMARY KEY, "
                + COLUMN_VALUE + " TEXT"
                + ")";
        db.execSQL(CREATE_SETTINGS_TABLE);

        // Insert default settings
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_KEY, "currency");
        cv.put(COLUMN_VALUE, "BDT");
        db.insert(TABLE_SETTINGS, null, cv);

        cv.put(COLUMN_KEY, "week_start");
        cv.put(COLUMN_VALUE, "Sunday");
        db.insert(TABLE_SETTINGS, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS);
        onCreate(db);
    }

    // ----------------- Expense Methods -----------------

    public long addExpense(Expense expense) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AMOUNT, expense.getAmount());
        values.put(COLUMN_CATEGORY_ID, expense.getCategoryId());
        values.put(COLUMN_DATE, expense.getDate());
        values.put(COLUMN_NOTE, expense.getNote());

        long id = db.insert(TABLE_EXPENSES, null, values);
        db.close();
        return id;
    }

    public List<Expense> getAllExpenses() {
        List<Expense> expenses = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_EXPENSES, null, null, null, null, null, COLUMN_DATE + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Expense expense = new Expense(
                        cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE))
                );
                expenses.add(expense);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return expenses;
    }

    public boolean updateExpense(Expense expense) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AMOUNT, expense.getAmount());
        values.put(COLUMN_CATEGORY_ID, expense.getCategoryId());
        values.put(COLUMN_DATE, expense.getDate());
        values.put(COLUMN_NOTE, expense.getNote());

        int rows = db.update(TABLE_EXPENSES, values, COLUMN_ID + " = ?", new String[]{String.valueOf(expense.getId())});
        db.close();
        return rows > 0;
    }

    public boolean deleteExpense(long id) {
        SQLiteDatabase db = getWritableDatabase();
        int rows = db.delete(TABLE_EXPENSES, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rows > 0;
    }

    public Expense getExpenseById(long id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_EXPENSES, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Expense expense = new Expense(
                    cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE))
            );
            cursor.close();
            db.close();
            return expense;
        }

        if (cursor != null) cursor.close();
        db.close();
        return null;
    }

    public double getTotalExpenses() {
        double total = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_AMOUNT + ") AS total FROM " + TABLE_EXPENSES, null);

        if (cursor.moveToFirst()) {
            total = cursor.getDouble(cursor.getColumnIndexOrThrow("total"));
        }

        cursor.close();
        db.close();
        return total;
    }

    public void resetAllExpenses() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_EXPENSES);
        db.close();
    }

    // ----------------- Settings Methods -----------------

    public String getSetting(String key) {
        String value = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_SETTINGS, new String[]{COLUMN_VALUE}, COLUMN_KEY + "=?", new String[]{key}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            value = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VALUE));
            cursor.close();
        }
        db.close();
        return value;
    }

    public void setSetting(String key, String value) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_VALUE, value);
        db.update(TABLE_SETTINGS, values, COLUMN_KEY + "=?", new String[]{key});
        db.close();
    }
}
