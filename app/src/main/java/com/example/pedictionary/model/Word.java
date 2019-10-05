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

    public static final class WordBuilder {
        private String mEngWord;
        private String mPerWord;

        private WordBuilder() {
        }

        public static WordBuilder aWord() {
            return new WordBuilder();
        }

        public WordBuilder mEngWord(String mEngWord) {
            this.mEngWord = mEngWord;
            return this;
        }

        public WordBuilder mPerWord(String mPerWord) {
            this.mPerWord = mPerWord;
            return this;
        }

        public Word build() {
            Word word = new Word();
            word.mPerWord = this.mPerWord;
            word.mEngWord = this.mEngWord;
            return word;
        }
    }
}
