package com.example.expensetrackerappbytauheedsayyed;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText editTextAmount;
    private EditText editTextDetail;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        editTextAmount = findViewById(R.id.editTextAmount);
        editTextDetail = findViewById(R.id.editTextDetail);
        buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount = Double.parseDouble(editTextAmount.getText().toString());
                String detail = editTextDetail.getText().toString();

                DatabaseHelper db = new DatabaseHelper(AddExpenseActivity.this);
                db.addExpense(amount, detail);

                Toast.makeText(AddExpenseActivity.this, "Expense added", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
