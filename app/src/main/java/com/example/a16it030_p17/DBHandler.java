package com.example.a16it030_p17;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ExpensesDB";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Expenses (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "expense TEXT," +
                "amount INTEGER, " +
                "date INTEGER)");  // date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Expenses");
        onCreate(db);
    }
}

