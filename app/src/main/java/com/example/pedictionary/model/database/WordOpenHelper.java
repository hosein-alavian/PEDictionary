package com.example.pedictionary.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.pedictionary.model.database.WordDBSchema.Word;

import static com.example.pedictionary.model.database.WordDBSchema.NAME;

public class WordOpenHelper extends SQLiteOpenHelper {

    public static final int VERSION = 1;

    public WordOpenHelper(@Nullable Context context) {
        super(context, NAME,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + Word.NAME + "(" +
                Word.Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + "," +
                Word.Cols.UUID + "," +
                Word.Cols.ENGLISHNAME + "," +
                Word.Cols.PERSIANNAME + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
