package com.example.pedictionary.controller;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pedictionary.R;
import com.example.pedictionary.model.Word;
import com.example.pedictionary.model.WordRepository;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    public static final String ADD_WORD_DIALOG_FRAGMENT = "add word dialog fragment";
    public static final String MAIN_FRAGMNET_ON_RESUME = "main fragmnet on resume";
    private RecyclerView wordsRecyclerView;
    private WordRecyclerViewAdapter mWordRecyclerViewAdapter;
    private Toolbar mToolbar;
    private static boolean mIsSubtitleVisible=false;
    private static ActionBar mActionBar;


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
        setHasOptionsMenu(true);
        initUI(view);
        updateUI();

        return view;
    }

    private void initUI(View view) {
        wordsRecyclerView = view.findViewById(R.id.wordsList_RecyclerView);
        mToolbar = view.findViewById(R.id.toolbar);
        if (mToolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
            mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        }
    }

    private void updateUI() {
        List<Word> wordsList = WordRepository.getInstance().getWordsList();
        if (mWordRecyclerViewAdapter == null) {
            mWordRecyclerViewAdapter = new WordRecyclerViewAdapter(getContext(), wordsList);
            wordsRecyclerView.setAdapter(mWordRecyclerViewAdapter);
            wordsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            mWordRecyclerViewAdapter.notifyDataSetChanged();
        }

        updateTitle();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);


        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        if (searchView != null) {
            SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setMaxWidth(Integer.MAX_VALUE);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    mWordRecyclerViewAdapter.getFilter().filter(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    mWordRecyclerViewAdapter.getFilter().filter(query);
                    return false;
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addWord_menuItem:
                Word word = new Word();
                WordRepository.getInstance().insertWord(word);
                DialogFragment dialogFragment = DialogFragment.newInstance(word.getId(), mWordRecyclerViewAdapter);
                dialogFragment.show(getFragmentManager(), ADD_WORD_DIALOG_FRAGMENT);
                return true;

            case R.id.action_search:
                return true;

            case R.id.set_title:
                mIsSubtitleVisible=!mIsSubtitleVisible;
                getActivity().invalidateOptionsMenu();

                updateTitle();
        }
        return super.onOptionsItemSelected(item);
    }

    public static void updateTitle() {
        int wordsListSize= WordRepository.getInstance().getWordsList().size();
        String subtitle= String.format("%d Word", wordsListSize);
        if(!mIsSubtitleVisible)
            subtitle=null;
        mActionBar.setTitle(subtitle);

    }


    @Override
    public void onResume() {
        super.onResume();
        updateUI();
        Log.d(MAIN_FRAGMNET_ON_RESUME, "onResume: ");
    }
}
