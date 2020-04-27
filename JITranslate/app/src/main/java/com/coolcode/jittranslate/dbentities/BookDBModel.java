package com.coolcode.jittranslate.dbentities;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.coolcode.jittranslate.database.JITDataBase;

import java.util.ArrayList;


public class BookDBModel {

    private Integer id = 0;
    private String name;
    private String author;
    private Integer page = 0;

    public BookDBModel(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public static ArrayList<BookDBModel> getAllBooks() {
        SQLiteDatabase dataBase = JITDataBase.getDbRead();
        ArrayList<BookDBModel> allBooks = new ArrayList<>();
        try {
            dataBase.beginTransaction();
            String queryString =
                    "SELECT name, author FROM books";
            Cursor rows = dataBase.rawQuery(queryString, null);
            Log.d("data_base", String.valueOf(rows.getCount()));
            if (rows.getCount() != 0) {
                while (rows.moveToNext()) {
                    allBooks.add(new BookDBModel(rows.getString(0), rows.getString(1)));
                }
            }
            rows.close();
            dataBase.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dataBase.endTransaction();
        }
        return allBooks;
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dataBase.endTransaction();
        }
        return page;
    }

}
