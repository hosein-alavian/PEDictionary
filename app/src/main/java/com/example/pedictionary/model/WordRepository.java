package com.example.pedictionary.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WordRepository {
    private static final WordRepository ourInstance = new WordRepository();
    private List<Word> wordsList;

    public static WordRepository getInstance() {
        return ourInstance;
    }

    private WordRepository() {
        wordsList=new ArrayList<>();
    }

    public List<Word> getWordsList() {
        return wordsList;
    }

    public void setWordsList(List<Word> wordsList) {
        this.wordsList = wordsList;
    }


    public void insertWord(Word word) {
        wordsList.add(word);
    }

    public Word getWord(UUID id){
        for (int i = 0; i < wordsList.size(); i++) {
            if(wordsList.get(i).getId()==id)
                return wordsList.get(i);
        }
        return null;
    }

    public void updateWord(Word word){
        for (int i = 0; i < wordsList.size(); i++) {
            if(wordsList.get(i).getId()==word.getId()) {
                wordsList.set(i, word);
                break;
            }
        }
    }

    public void deleteWord(Word word){
        for (int i = 0; i < wordsList.size(); i++) {
            if(wordsList.get(i).getId()==word.getId()) {
                wordsList.remove(i);
                break;
            }
        }
    }
}
