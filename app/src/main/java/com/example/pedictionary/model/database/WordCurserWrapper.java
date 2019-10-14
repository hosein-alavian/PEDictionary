package com.example.pedictionary.model.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.pedictionary.model.Word;

import java.util.UUID;

public class WordCurserWrapper extends CursorWrapper {
    public WordCurserWrapper(Cursor cursor) {
        super(cursor);
    }

    public Word getWord(){
        String strUuid=getString(getColumnIndex(WordDBSchema.Word.Cols.UUID));
        String englishWord=getString(getColumnIndex(WordDBSchema.Word.Cols.ENGLISHNAME));
        String persianWord=getString(getColumnIndex(WordDBSchema.Word.Cols.PERSIANNAME));

        Word word=new Word(UUID.fromString(strUuid));
        word.setEngWord(englishWord);
        word.setPerWord(persianWord);


        return word;
    }
}
