package com.example.pedictionary.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pedictionary.R;
import com.example.pedictionary.model.database.WordCurserWrapper;
import com.example.pedictionary.model.database.WordOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.pedictionary.model.database.WordDBSchema.Word.Cols;
import static com.example.pedictionary.model.database.WordDBSchema.Word.NAME;

public class WordRepository {
    private List<Word> wordsList;
    private final Context mContext;
    private final SQLiteDatabase mDatabase;
    private static WordRepository mWordRepository;


    public static WordRepository getInstance(Context context) {
        if (mWordRepository == null) {
            mWordRepository = new WordRepository(context);
        }
        return mWordRepository;
    }

    private WordRepository(Context context) {
//        wordsList=new ArrayList<>();
        mContext = context.getApplicationContext();
        mDatabase = new WordOpenHelper(mContext).getWritableDatabase();
    }

    public Word getWord(UUID id) {
        String[] whereArgs = new String[]{id.toString()};
        String where = Cols.UUID + " = ?";
        WordCurserWrapper curserWrapper = getQuery(where, whereArgs);

        try {
            curserWrapper.moveToFirst();
//            Word word = null;

            if (curserWrapper == null || curserWrapper.getCount() == 0)
                return null;

/*            while (!curserWrapper.isAfterLast()) {
                word = curserWrapper.getWord();
                curserWrapper.moveToNext();
            }*/

            return curserWrapper.getWord();
        } finally {
            curserWrapper.close();
        }
/*        for (int i = 0; i < wordsList.size(); i++) {
            if(wordsList.get(i).getId()==id)
                return wordsList.get(i);
        }
        return null;*/
    }

    public List<Word> getWords() {
        List<Word> words = new ArrayList<>();
        WordCurserWrapper curserWrapper = getQuery(null, null);

        try {
            curserWrapper.moveToFirst();

            while (!curserWrapper.isAfterLast()) {
                words.add(curserWrapper.getWord());

                curserWrapper.moveToNext();
            }
        } finally {
            curserWrapper.close();
        }

        return words;
    }

    public void insertWord(Word word) {
        ContentValues values = getContentValues(word);
        mDatabase.insertOrThrow(NAME, null, values);
//        wordsList.add(word);
    }

    public void updateWord(Word word) throws Exception{
        if(word==null)
            throw new Exception(mContext.getString(R.string.word_not_exist));

        String where = Cols.UUID + " = ?";
        String[] whereArgs = new String[]{word.getId().toString()};
        mDatabase.update(NAME, getContentValues(word), where, whereArgs);
/*        for (int i = 0; i < wordsList.size(); i++) {
            if(wordsList.get(i).getId()==word.getId()) {
                wordsList.set(i, word);
                break;
            }
        }*/
    }

    public void deleteWord(Word word) throws Exception{
        if(word==null)
            throw new Exception(mContext.getString(R.string.word_not_exist));

        String where = Cols.UUID + " = ?";
        String[] whereArgs = new String[]{word.getId().toString()};
        mDatabase.delete(NAME, where, whereArgs);
/*        for (int i = 0; i < wordsList.size(); i++) {
            if(wordsList.get(i).getId()==word.getId()) {
                wordsList.remove(i);
                break;
            }
        }*/
    }

    private ContentValues getContentValues(Word word) {
        ContentValues values = new ContentValues();
        values.put(Cols.UUID, word.getId().toString());
        values.put(Cols.ENGLISHNAME, word.getEngWord());
        values.put(Cols.PERSIANNAME, word.getPerWord());

        return values;
    }

    private WordCurserWrapper getQuery(String where, String[] whereArgs) {
        Cursor cursor = mDatabase.query(NAME,
                null,
                where,
                whereArgs,
                null,
                null,
                null);
        return new WordCurserWrapper(cursor);
    }
}
