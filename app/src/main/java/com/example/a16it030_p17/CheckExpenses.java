package com.example.a16it030_p17;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

public class CheckExpenses extends AppCompatActivity {

    private ExpenseAdapter mAdapter;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_expenses);

        final DBHandler dbHandler = new DBHandler(this);
        db = dbHandler.getWritableDatabase();

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ExpenseAdapter(this, getAllExpenses());
        recyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                deleteExpense((int)viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);
    }

    public Cursor getAllExpenses() {

        return db.query(
                "Expenses",
                null,
                null,
                null,
                null,
                null,
                "date DESC"
        );
    }

    public void deleteExpense(int id){
        db.delete("Expenses","id="+id, null);
        Toast.makeText(getApplicationContext(), "Removed", Toast.LENGTH_SHORT).show();
        mAdapter.swapCursor(getAllExpenses());

    }
}
