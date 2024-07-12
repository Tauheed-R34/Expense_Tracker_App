package com.example.expensetrackerappbytauheedsayyed;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    private TextView textViewTotalExpense;
    private TextView textViewTotalIncome;
    private TextView textViewBalance;
    private ListView listViewExpenses;
    private ListView listViewIncome;
    private Button buttonAddExpense;
    private Button buttonAddIncome;

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTotalExpense = findViewById(R.id.textViewTotalExpense);
        textViewTotalIncome = findViewById(R.id.textViewTotalIncome);
        textViewBalance = findViewById(R.id.textViewBalance);
        listViewExpenses = findViewById(R.id.listViewExpenses);
        listViewIncome = findViewById(R.id.listViewIncome);
        buttonAddExpense = findViewById(R.id.buttonAddExpense);
        buttonAddIncome = findViewById(R.id.buttonAddIncome);

        db = new DatabaseHelper(this);

        loadSummary();
        loadExpenses();
        loadIncome();

        buttonAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddExpenseActivity.class));
            }
        });

        buttonAddIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddIncomeActivity.class));
            }
        });
    }

    private void loadSummary() {
        double totalExpense = db.getTotalExpense();
        double totalIncome = db.getTotalIncome();
        double balance = totalIncome - totalExpense;
        textViewTotalExpense.setText("Total Expense: ₹" + totalExpense);
        textViewTotalIncome.setText("Total Income: ₹" + totalIncome);
        textViewBalance.setText("Balance: ₹" + balance);
    }

    private void loadExpenses() {
        Cursor cursor = db.getAllExpenses();
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.item_record, cursor, new String[]{DatabaseHelper.COLUMN_DETAIL, DatabaseHelper.COLUMN_AMOUNT, DatabaseHelper.COLUMN_TIMESTAMP}, new int[]{R.id.textViewDetail, R.id.textViewAmount, R.id.textViewTimestamp}, 0) {
            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                super.bindView(view, context, cursor);
                int position = cursor.getPosition() + 1;
                TextView textViewDetail = view.findViewById(R.id.textViewDetail);
                TextView textViewAmount = view.findViewById(R.id.textViewAmount);
                TextView textViewTimestamp = view.findViewById(R.id.textViewTimestamp);
                textViewDetail.setText(position + ". " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DETAIL)));
                textViewAmount.setText("₹" + cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AMOUNT)));
                textViewTimestamp.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TIMESTAMP)));
            }
        };
        listViewExpenses.setAdapter(adapter);
    }

    private void loadIncome() {
        Cursor cursor = db.getAllIncome();
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.item_record, cursor, new String[]{DatabaseHelper.COLUMN_DETAIL, DatabaseHelper.COLUMN_AMOUNT, DatabaseHelper.COLUMN_TIMESTAMP}, new int[]{R.id.textViewDetail, R.id.textViewAmount, R.id.textViewTimestamp}, 0) {
            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                super.bindView(view, context, cursor);
                int position = cursor.getPosition() + 1;
                TextView textViewDetail = view.findViewById(R.id.textViewDetail);
                TextView textViewAmount = view.findViewById(R.id.textViewAmount);
                TextView textViewTimestamp = view.findViewById(R.id.textViewTimestamp);
                textViewDetail.setText(position + ". " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DETAIL)));
                textViewAmount.setText("₹" + cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AMOUNT)));
                textViewAmount.setTextColor(getResources().getColor(R.color.incomeColor));  // Set text color to green
                textViewTimestamp.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TIMESTAMP)));
            }
        };
        listViewIncome.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSummary();
        loadExpenses();
        loadIncome();
    }
}
