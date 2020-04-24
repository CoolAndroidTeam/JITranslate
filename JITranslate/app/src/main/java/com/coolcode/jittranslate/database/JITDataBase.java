package com.coolcode.jittranslate.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class JITDataBase {

    private static SQLiteDatabase db;
    DataBaseCreator dataBaseCreator;

    public JITDataBase(Context context) {
        dataBaseCreator = new DataBaseCreator(context);
        db = dataBaseCreator.getWritableDatabase();
    }

    public static SQLiteDatabase getDb() {
        return db;
    }
}
