package com.example.pedictionary.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pedictionary.R;
import com.example.pedictionary.model.Word;

import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogFragment.DialogFragmentInterface {


    private DialogFragment.DialogFragmentInterface mNotifyInteface;
    private WordRecyclerViewAdapter mWordRecyclerViewAdapter;

    public void newIntent(Activity activity) {
        Intent intent = new Intent(MainActivity.this, activity.getClass());
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container_layout, MainFragment.newInstance())
                .commit();
    }


    @Override
    public void notifyAdapter(List<Word> wordList, Context context) {

        MainFragment fragmentById = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.main_container_layout);
        mWordRecyclerViewAdapter = fragmentById.getWordRecyclerViewAdapter();
        mWordRecyclerViewAdapter.setWordsList(wordList);
        mWordRecyclerViewAdapter.setWordsListFiltered(wordList);
        mWordRecyclerViewAdapter.notifyDataSetChanged();
        fragmentById.updateTitle(context);
    }

}
