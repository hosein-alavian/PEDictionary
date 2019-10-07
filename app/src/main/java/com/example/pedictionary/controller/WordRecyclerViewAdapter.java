package com.example.pedictionary.controller;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pedictionary.R;
import com.example.pedictionary.model.Word;
import com.example.pedictionary.model.WordRepository;

import java.util.List;

public class WordRecyclerViewAdapter extends RecyclerView.Adapter<WordRecyclerViewHolder> {

    private List<Word> mWordsList;

    public WordRecyclerViewAdapter(List<Word> wordsList) {
        mWordsList = wordsList;
    }

    @NonNull
    @Override
    public WordRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WordRecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_items,
                parent,
                false),parent.getContext(),WordRecyclerViewAdapter.this);
    }

    @Override
    public void onBindViewHolder(@NonNull WordRecyclerViewHolder holder, int position) {
        holder.bind(mWordsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mWordsList == null ? 0 : mWordsList.size();
    }
}
