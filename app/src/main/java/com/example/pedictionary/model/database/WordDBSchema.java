package com.example.pedictionary.model.database;

public class WordDBSchema {
    public static final String NAME="word.db";

    public static final class Word{
        public static final String NAME="Word";

        public static final class Cols{
            public static final String _ID="_id";
            public static final String UUID="uuid";
            public static final String ENGLISHNAME="english_name";
            public static final String PERSIANNAME="persian_name";
        }
    }
}
