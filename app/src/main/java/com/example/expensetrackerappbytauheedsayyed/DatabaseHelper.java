package com.example.expensetrackerappbytauheedsayyed;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "expense_tracker.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_EXPENSE = "expense";
    public static final String TABLE_INCOME = "income";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_DETAIL = "detail";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createExpenseTable = "CREATE TABLE " + TABLE_EXPENSE + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_AMOUNT + " REAL, " +
                COLUMN_DETAIL + " TEXT, " +
                COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(createExpenseTable);

        String createIncomeTable = "CREATE TABLE " + TABLE_INCOME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_AMOUNT + " REAL, " +
                COLUMN_DETAIL + " TEXT, " +
                COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(createIncomeTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOME);
        onCreate(db);
    }

    public void addExpense(double amount, String detail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AMOUNT, amount);
        values.put(COLUMN_DETAIL, detail);
        db.insert(TABLE_EXPENSE, null, values);
    }

    public void addIncome(double amount, String detail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AMOUNT, amount);
        values.put(COLUMN_DETAIL, detail);
        db.insert(TABLE_INCOME, null, values);
    }

    public Cursor getAllExpenses() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_EXPENSE + " ORDER BY " + COLUMN_TIMESTAMP + " ASC", null);
    }

    public Cursor getAllIncome() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_INCOME + " ORDER BY " + COLUMN_TIMESTAMP + " ASC", null);
    }

    public double getTotalExpense() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_AMOUNT + ") as total FROM " + TABLE_EXPENSE, null);
        if (cursor.moveToFirst()) {
            return cursor.getDouble(cursor.getColumnIndexOrThrow("total"));
        }
        return 0;
    }

    public double getTotalIncome() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_AMOUNT + ") as total FROM " + TABLE_INCOME, null);
        if (cursor.moveToFirst()) {
            return cursor.getDouble(cursor.getColumnIndexOrThrow("total"));
        }
        return 0;
    }
}
