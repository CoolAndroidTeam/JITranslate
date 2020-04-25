package com.coolcode.jittranslate.entities;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.coolcode.jittranslate.database.JITDataBase;


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
        SQLiteDatabase dataBase = JITDataBase.getDbRead();
        String[] whereArgs = new String[]{
                this.author,
                this.name
        };
        int id = -1;
        try {
            dataBase.beginTransaction();
            String queryString =
                    "SELECT id FROM books WHERE author = ? AND name = ? ";
            Cursor rows = dataBase.rawQuery(queryString, whereArgs);
            if (rows.getCount() == 0) {
                rows.close();
                throw new Exception("empty book query");
            } else {
                rows.moveToNext();
                id = rows.getInt(0);
                rows.close();
                dataBase.setTransactionSuccessful();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dataBase.endTransaction();
        }
        return id;
    }

    public void savePage(int page) {
        System.out.println(page);
        this.page = page;
        SQLiteDatabase dataBase = JITDataBase.getDbWrite();
        try {
            dataBase.beginTransaction();
            int id = this.checkBookId();
            String[] updateArgs = new String[] {
                    this.page.toString(),
                    String.valueOf(id)
            };
            String updateString =
                    "UPDATE books SET page = ? WHERE id = ?";
            dataBase.execSQL(updateString, updateArgs);
            dataBase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dataBase.endTransaction();
        }
    }

    public int getPage() throws Exception {
        SQLiteDatabase dataBase = JITDataBase.getDbRead();
        String[] whereArgs = new String[]{
                this.author,
                this.name
        };
        int page = 0;
        try {
            dataBase.beginTransaction();
            String queryString = "SELECT page FROM books WHERE author = ? AND name = ?";
            Cursor rows = dataBase.rawQuery(queryString, whereArgs);
            if (rows.getCount() == 0) {
                rows.close();
                throw new Exception("empty book query");
            } else {
                rows.moveToNext();
                page = rows.getInt(0);
                rows.close();
                dataBase.setTransactionSuccessful();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dataBase.endTransaction();
        }
        return page;
    }

}
