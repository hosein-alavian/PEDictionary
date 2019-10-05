package com.example.pedictionary.controller;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.example.pedictionary.R;
import com.example.pedictionary.model.Word;
import com.example.pedictionary.model.WordRepository;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private RecyclerView wordsRecyclerView;
    private WordRecyclerViewAdapter mWordRecyclerViewAdapter;


    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        wordsRecyclerView=view.findViewById(R.id.wordsList_RecyclerView);

        updateUI();

        return view;
    }

    private void updateUI() {
        List<Word> wordsList= WordRepository.getInstance().getWordsList();
        if(mWordRecyclerViewAdapter ==null) {
            mWordRecyclerViewAdapter = new WordRecyclerViewAdapter(wordsList);
            wordsRecyclerView.setAdapter(mWordRecyclerViewAdapter);
            wordsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        else {
            mWordRecyclerViewAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}
