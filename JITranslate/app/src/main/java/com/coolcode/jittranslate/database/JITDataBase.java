package com.coolcode.jittranslate.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class JITDataBase {

    private static SQLiteDatabase dbWrite;
    private static SQLiteDatabase dbRead;
    JITDataBaseCreator JITDataBaseCreator;

    public JITDataBase(Context context) {
        JITDataBaseCreator = new JITDataBaseCreator(context);
        dbWrite = JITDataBaseCreator.getWritableDatabase();
        dbRead = JITDataBaseCreator.getReadableDatabase();
    }

    public static SQLiteDatabase getDbWrite() {
        return dbWrite;
    }

    public static SQLiteDatabase getDbRead() {
        return dbRead;
    }
}
