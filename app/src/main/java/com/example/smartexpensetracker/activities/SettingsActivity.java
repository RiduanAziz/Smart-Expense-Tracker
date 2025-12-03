package com.example.smartexpensetracker.activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartexpensetracker.R;
import com.example.smartexpensetracker.database.DBHelper;

public class SettingsActivity extends AppCompatActivity {

    private Spinner spCurrency, spWeekStart;
    private Button btnSaveSettings, btnResetData;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initViews();
        dbHelper = new DBHelper(this);

        loadSettings();
        setListeners();
    }

    private void initViews() {
        spCurrency = findViewById(R.id.spCurrency);
        spWeekStart = findViewById(R.id.spWeekStart);
        btnSaveSettings = findViewById(R.id.btnSaveSettings);
        btnResetData = findViewById(R.id.btnResetData);

        String[] currencies = {"BDT", "USD", "INR", "EUR"};
        String[] weekStarts = {"Sunday", "Monday", "Saturday"};

        spCurrency.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, currencies));

        spWeekStart.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, weekStarts));
    }

    private void loadSettings() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        String currency = "BDT";
        String weekStart = "Sunday";

        try {
            cursor = db.rawQuery("SELECT key, value FROM settings", null);
            if (cursor.moveToFirst()) {
                do {
                    String key = cursor.getString(0);
                    String value = cursor.getString(1);

                    if (key.equals("currency")) currency = value;
                    if (key.equals("week_start")) weekStart = value;
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        setSpinnerSelection(spCurrency, currency);
        setSpinnerSelection(spWeekStart, weekStart);
    }

    private void setSpinnerSelection(Spinner spinner, String value) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinner.getAdapter();
        int position = adapter.getPosition(value);
        spinner.setSelection(position);
    }

    private void setListeners() {
        btnSaveSettings.setOnClickListener(v -> saveSettings());
        btnResetData.setOnClickListener(v -> resetAllData());
    }

    private void saveSettings() {
        String currency = spCurrency.getSelectedItem().toString();
        String weekStart = spWeekStart.getSelectedItem().toString();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.execSQL("UPDATE settings SET value=? WHERE key='currency'", new String[]{currency});
            db.execSQL("UPDATE settings SET value=? WHERE key='week_start'", new String[]{weekStart});
        } finally {
            db.close();
        }

        Toast.makeText(this, "Settings saved", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void resetAllData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.execSQL("DELETE FROM expenses");
        } finally {
            db.close();
        }

        Toast.makeText(this, "All expense data has been reset", Toast.LENGTH_SHORT).show();
    }
}
