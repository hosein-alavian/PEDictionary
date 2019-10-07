package com.example.pedictionary.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pedictionary.R;
import com.example.pedictionary.model.Word;

import java.util.ArrayList;
import java.util.List;

public class WordRecyclerViewAdapter extends RecyclerView.Adapter<WordRecyclerViewAdapter.WordRecyclerViewHolder> implements Filterable {

    private Context mContext;
    private List<Word> mWordsList;
    private List<Word> mWordsListFiltered;
    private Boolean mEnglishWordSearched=true;

    public WordRecyclerViewAdapter(Context context, List<Word> wordsList) {

        this.mContext = context;
        this.mWordsList = wordsList;
        this.mWordsListFiltered = wordsList;
    }

    @NonNull
    @Override
    public WordRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WordRecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_items,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull WordRecyclerViewHolder holder, int position) {
        holder.bind(mWordsListFiltered.get(position));
    }

    @Override
    public int getItemCount() {
        return mWordsListFiltered == null ? 0 : mWordsListFiltered.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString();

                List<Word> filtered = new ArrayList<>();

                if (query.isEmpty())
                    filtered = mWordsList;
                else
                    for (Word word : mWordsList) {
                        if (word.getEngWord().toLowerCase().equals(query.toLowerCase())) {
                            mEnglishWordSearched = true;
                            filtered.add(word);
                        } else if (word.getPerWord().toLowerCase().equals(query.toLowerCase())) {
                            mEnglishWordSearched = false;
                            filtered.add(word);
                        }
                    }
                FilterResults results = new FilterResults();
                results.count = filtered.size();
                results.values = filtered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                mWordsListFiltered = (List<Word>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    public class WordRecyclerViewHolder extends RecyclerView.ViewHolder {
        public static final String EDIT_WORD_DIALOG_FRAGMENT = "edit word dialog fragment";
        private TextView mFirstTextView;
        private TextView mSecondTextView;
        private Word mWord;

        public WordRecyclerViewHolder(@NonNull final View itemView) {
            super(itemView);
            mFirstTextView = itemView.findViewById(R.id.engWord_textView);
            mSecondTextView = itemView.findViewById(R.id.perWord_textView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment dialogFragment = DialogFragment.newInstance(mWord.getId(), WordRecyclerViewAdapter.this);
                    FragmentManager fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();
                    dialogFragment.show(fragmentManager, EDIT_WORD_DIALOG_FRAGMENT);
                }
            });
        }

        public void bind(Word word) {
            if (mEnglishWordSearched) {
                mFirstTextView.setText(word.getEngWord());
                mSecondTextView.setText(word.getPerWord());
            } else {
                mFirstTextView.setText(word.getPerWord());
                mSecondTextView.setText(word.getEngWord());
            }
            mWord = word;
        }
    }
}
