package com.example.a16it030_p17;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLData;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button add, check;
    private EditText expense, amount;
    private TextView track;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new DBHandler(getApplicationContext());

        add = findViewById(R.id.add);
        check = findViewById(R.id.check);

        expense = findViewById(R.id.expense);
        amount = findViewById(R.id.amount);

        track = findViewById(R.id.track);
        track.setText(String.valueOf(todaysUsage()));

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(TextUtils.isEmpty(expense.getText().toString()) || TextUtils.isEmpty(amount.getText().toString()))) {

                    SQLiteDatabase db = dbHandler.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put("expense", expense.getText().toString());
                    values.put("amount", Integer.parseInt(amount.getText().toString()));
                    values.put("date", (new Date()).getTime());

                    db.insert("Expenses", null, values);
                    db.close();

                    track.setText(String.valueOf(todaysUsage()));

                    Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                    expense.setText("");
                    amount.setText("");
                }
                else{
                    Toast.makeText(getApplicationContext(), "Fill the fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CheckExpenses.class));
            }
        });
    }

    private int todaysUsage() {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.query(
                "Expenses",
                new String[]{"amount"},
                "date<="+(new Date()).getTime(),
                null,
                null,
                null,
                null
        );
        int sum = 0;
        if (cursor.moveToFirst()) {
            do {
                sum += cursor.getLong(0);
            } while (cursor.moveToNext());
        }
        db.close();
        return sum;
    }
}
