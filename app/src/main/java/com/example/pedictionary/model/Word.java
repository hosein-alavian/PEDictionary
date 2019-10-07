package com.example.pedictionary.model;

import java.util.UUID;

public class Word {
    private UUID mId;
    private String mEngWord;
    private String mPerWord;


    public Word() {
        mId=UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public String getEngWord() {
        return mEngWord;
    }

    public void setEngWord(String engWord) {
        mEngWord = engWord;
    }

    public String getPerWord() {
        return mPerWord;
    }

    public void setPerWord(String perWord) {
        mPerWord = perWord;
    }
}
