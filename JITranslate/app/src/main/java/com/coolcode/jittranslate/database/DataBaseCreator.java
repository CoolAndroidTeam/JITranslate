package com.coolcode.jittranslate.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseCreator extends SQLiteOpenHelper {

    public DataBaseCreator(Context context) {
        super(context, "DatabaseName.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("data_base", "Created Database");
        db.execSQL("CREATE TABLE IF NOT EXISTS books (id integer PRIMARY KEY AUTOINCREMENT, name text, author text, page integer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}