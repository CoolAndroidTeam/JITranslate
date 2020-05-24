package com.coolcode.jittranslate.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class JITDataBase {

    private static JITDataBaseCreator JITDataBaseCreator;

    public JITDataBase(Context context) {
        JITDataBaseCreator = new JITDataBaseCreator(context);
    }

    public static SQLiteDatabase getDbWrite() {
        return JITDataBaseCreator.getWritableDatabase();
    }

    public static SQLiteDatabase getDbRead(){
        return JITDataBaseCreator.getReadableDatabase();
    }
}
