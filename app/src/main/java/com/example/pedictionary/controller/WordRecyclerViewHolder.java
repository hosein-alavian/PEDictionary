package com.example.pedictionary.controller;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pedictionary.R;
import com.example.pedictionary.model.Word;

public class WordRecyclerViewHolder extends RecyclerView.ViewHolder {
    private TextView engWordTextView;
    private TextView perWordTextView;

    public WordRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        engWordTextView=itemView.findViewById(R.id.engWord_textView);
        perWordTextView=itemView.findViewById(R.id.perWord_textView);
    }

    public void bind(Word word){
        engWordTextView.setText(word.getEngWord());
        engWordTextView.setText(word.getPerWord());
    }
}
