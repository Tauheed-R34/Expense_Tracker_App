package com.example.expensetrackerappbytauheedsayyed;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddIncomeActivity extends AppCompatActivity {

    private EditText editTextAmountIncome;
    private EditText editTextDetailIncome;
    private Button buttonSaveIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);

        editTextAmountIncome = findViewById(R.id.editTextAmountIncome);
        editTextDetailIncome = findViewById(R.id.editTextDetailIncome);
        buttonSaveIncome = findViewById(R.id.buttonSaveIncome);

        buttonSaveIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount = Double.parseDouble(editTextAmountIncome.getText().toString());
                String detail = editTextDetailIncome.getText().toString();

                DatabaseHelper db = new DatabaseHelper(AddIncomeActivity.this);
                db.addIncome(amount, detail);

                Toast.makeText(AddIncomeActivity.this, "Income added", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
