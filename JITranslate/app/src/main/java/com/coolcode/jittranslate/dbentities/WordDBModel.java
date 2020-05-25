package com.coolcode.jittranslate.dbentities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.coolcode.jittranslate.database.JITDataBase;

import java.util.ArrayList;


public class WordDBModel {

    private Integer id = 0;
    private String word;
    private String translate;
    private String language = LANGUAGE;
    private static String WORD_COLUMN = "word";
    private static String TRANSLATE_COLUMN = "translate";
    private static String LANGUAGE_COLUMN = "language";
    private static String TABLE = "words";
    private static String LANGUAGE = "english";
    private static Integer WRONGID = -1;

    private static final MutableLiveData<ArrayList<WordDBModel>> wordsListMutable = new MutableLiveData<>();



    public WordDBModel(String word, String translate) {
        this.word = word;
        this.translate = translate;
    }

    public String getWord() {
        return word;
    }

    public String getTranslate() {
        return translate;
    }

    public String getLanguage() {
        return language;
    }

    public static MutableLiveData<ArrayList<WordDBModel>> getAllWords() {
        getWords();
        return wordsListMutable;
    }

    private static void getWords() {
        SQLiteDatabase dataBase = JITDataBase.getDbRead();
        ArrayList<WordDBModel> allWords = new ArrayList<>();
        try {
            dataBase.beginTransaction();
            String queryString =
                    "SELECT word, translate, language FROM words";
            Cursor rows = dataBase.rawQuery(queryString, null);
            Log.d("data_base", String.valueOf(rows.getCount()));
            if (rows.getCount() != 0) {
                while (rows.moveToNext()) {
                    allWords.add(new WordDBModel(rows.getString(0), rows.getString(1)));
                }
            }
            rows.close();
            dataBase.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dataBase.endTransaction();
        }
        wordsListMutable.setValue(allWords);
    }

    public void addWord() throws Exception {
        SQLiteDatabase dataBase = JITDataBase.getDbWrite();
        try {
            int id = this.checkWordId();
            if (id != WRONGID) {
                throw new Exception("word exists");
            }
            ContentValues values = new ContentValues();
            values.put(WORD_COLUMN, this.word);
            values.put(TRANSLATE_COLUMN, this.translate);
            values.put(LANGUAGE_COLUMN, LANGUAGE);
            long newRowId = dataBase.insert(TABLE, null, values);
            Log.d("database_add_word", String.valueOf(newRowId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        getWords();
    }

    public void deleteWord() throws Exception {
        SQLiteDatabase dataBase = JITDataBase.getDbWrite();
        try {
            dataBase.beginTransaction();
            int id = this.checkWordId();
            if (id == WRONGID) {
                throw new Exception("empty word query");
            }
            String[] deleteArgs = new String[] {
                    this.word
            };
            String deleteString =
                    "DELETE FROM words WHERE word = ?";
            dataBase.execSQL(deleteString, deleteArgs);
            dataBase.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dataBase.endTransaction();
        }
//        getWords();
    }

    private int checkWordId() {
        SQLiteDatabase dataBase = JITDataBase.getDbRead();
        String[] whereArgs = new String[]{
                this.word,
                this.translate
        };
        int id = WRONGID;
        try {
            dataBase.beginTransaction();
            String queryString =
                    "SELECT id FROM words WHERE word = ? AND translate = ? ";
            Cursor rows = dataBase.rawQuery(queryString, whereArgs);
            if (rows.getCount() == 0) {
                rows.close();
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

}
