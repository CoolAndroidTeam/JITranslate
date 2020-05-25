package com.coolcode.jittranslate.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class JITDataBaseCreator extends SQLiteOpenHelper {

    private static final String insertHelpValues = "INSERT INTO books (name, author, page) VALUES ('Tom Sawyer', 'Mark Twain', 1), ('Alice In Wonderland', 'Lewis Carroll', 1), ('Don Kihot', 'Migel Servantes', 1), ('The Thirteenth Tale', 'Diane Setterfield', 1), ('Dracula', 'Bram Stoker', 1), ('The Canterville Ghost', 'Oscar Wilde', 1);";

    public JITDataBaseCreator(Context context) {
        super(context, "NEWNAME", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("data_base", "Created Database");
        db.execSQL("CREATE TABLE IF NOT EXISTS books (id integer PRIMARY KEY AUTOINCREMENT, name text, author text, page integer);");
        db.execSQL("CREATE TABLE IF NOT EXISTS words (id integer PRIMARY KEY AUTOINCREMENT, word text, translate text, language text);");
        db.execSQL(insertHelpValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}