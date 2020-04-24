package com.coolcode.jittranslate.entities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.coolcode.jittranslate.database.JITDataBase;

import java.sql.PreparedStatement;

public class Book {

    private Integer id = 0;
    private String name;
    private String author;
    private Integer page = 0;

    public Book(String name, String author) {
        this.name = name;
        this.author = author;
    }

    private int checkBookId() throws Exception {
        this.page = page;
        SQLiteDatabase dataBase = JITDataBase.getDb();
        String[] whereArgs = new String[] {
                this.author,
                this.name
        };
        String queryString =
                "SELECT id FROM books WHERE author = ? AND name = ? ";
        Cursor rows = dataBase.rawQuery(queryString, whereArgs);
        if (rows.getCount() == 0) {
            rows.close();
            throw new Exception("empty book query");
        } else {
            return rows.getInt(0);
        }
    }

    public void savePage(int page) {
        this.page = page;
        try {
            SQLiteDatabase dataBase = JITDataBase.getDb();
            int id = this.checkBookId();
            String[] updateArgs = new String[] {
                    this.page.toString(),
                    String.valueOf(id)
            };
            String updateString =
                    "UPDATE books SET page = ? WHERE id = ?";
            dataBase.execSQL(updateString, updateArgs);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getPage() throws Exception {
        SQLiteDatabase dataBase = JITDataBase.getDb();
        String[] whereArgs = new String[] {
                this.author,
                this.name
        };
        String queryString =
                "SELECT page FROM books WHERE author = ? AND name = ? ";
        Cursor rows = dataBase.rawQuery(queryString, whereArgs);
        if (rows.getCount() == 0) {
            rows.close();
            throw new Exception("empty book query");
        } else {
            return rows.getInt(0);
        }
    }
}
