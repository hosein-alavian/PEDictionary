package com.example.pedictionary.controller;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pedictionary.R;
import com.example.pedictionary.model.Word;

public class WordRecyclerViewHolder extends RecyclerView.ViewHolder {
    public static final String EDIT_WORD_DIALOG_FRAGMENT = "edit word dialog fragment";
    private TextView engWordTextView;
    private TextView perWordTextView;
    private Word mWord;
    Context mContext;

    public WordRecyclerViewHolder(@NonNull final View itemView, Context context,final WordRecyclerViewAdapter wordRecyclerViewAdapter) {
        super(itemView);
        engWordTextView=itemView.findViewById(R.id.engWord_textView);
        perWordTextView=itemView.findViewById(R.id.perWord_textView);

        mContext=context;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment=DialogFragment.newInstance(mWord.getId(),wordRecyclerViewAdapter);
                FragmentManager fragmentManager=((AppCompatActivity)mContext).getSupportFragmentManager();
                dialogFragment.show(fragmentManager, EDIT_WORD_DIALOG_FRAGMENT);
            }
        });
    }

    public void bind(Word word){
        engWordTextView.setText(word.getEngWord());
        perWordTextView.setText(word.getPerWord());
        mWord=word;
    }
}
