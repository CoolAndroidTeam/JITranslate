package com.coolcode.jittranslate.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class JITDataBase {

    private static SQLiteDatabase dbWrite;
    private static SQLiteDatabase dbRead;
    DataBaseCreator dataBaseCreator;

    public JITDataBase(Context context) {
        dataBaseCreator = new DataBaseCreator(context);
        dbWrite = dataBaseCreator.getWritableDatabase();
        dbRead = dataBaseCreator.getReadableDatabase();
    }

    public static SQLiteDatabase getDbWrite() {
        return dbWrite;
    }

    public static SQLiteDatabase getDbRead() {
        return dbRead;
    }
}
