package com.example.pedictionary.controller;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pedictionary.R;
import com.example.pedictionary.model.Word;
import com.example.pedictionary.model.WordRepository;

import java.util.UUID;
import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogFragment extends androidx.fragment.app.DialogFragment {


    public static final String WORD_ID = "word id";
    private UUID mId;
    private Word mWord;
    private EditText englishWordET;
    private EditText persianWordET;
    private WordRecyclerViewAdapter mWordRecyclerViewAdapter;
    private AlertDialog mAlertDialog;

    public DialogFragment() {
        // Required empty public constructor
    }

    public DialogFragment(WordRecyclerViewAdapter wordRecyclerViewAdapter){
        mWordRecyclerViewAdapter=wordRecyclerViewAdapter;
    }

    public static DialogFragment newInstance(UUID id,WordRecyclerViewAdapter wordRecyclerViewAdapter) {

        Bundle args = new Bundle();
        args.putSerializable(WORD_ID,id);
        DialogFragment fragment = new DialogFragment(wordRecyclerViewAdapter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null) {
            mId = (UUID) getArguments().getSerializable(WORD_ID);
            mWord = WordRepository.getInstance().getWord(mId);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater= LayoutInflater.from(getActivity());
        View view=inflater.inflate(R.layout.fragment_dialog, null, false);

        initUI(view);
        uiListener();

        if(mWord.getPerWord()==null && mWord.getEngWord()==null)
            mAlertDialog = addWordAlertDialog(view);
        else
            mAlertDialog= editWordAlertDialog(view);


        return mAlertDialog;
    }

    private AlertDialog addWordAlertDialog(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_add_word)
                .setIcon(R.drawable.dialog_icon_image)
                .setPositiveButton(R.string.save, null)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setView(view)
                .create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button=mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mWord.getEngWord() != null || mWord.getPerWord() != null) {
                            WordRepository.getInstance().updateWord(mWord);
                            mWordRecyclerViewAdapter.notifyDataSetChanged();
                            dismiss();
                        } else
                            Toast.makeText(getActivity(),
                                    "english or persian word is empty!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return alertDialog;
    }

    private AlertDialog editWordAlertDialog(View view) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_editword)
                .setIcon(R.drawable.dialog_icon_image)
                .setPositiveButton(R.string.save, new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        WordRepository.getInstance().updateWord(mWord);
                        mWordRecyclerViewAdapter.notifyDataSetChanged();
                    }
                })
                .setNeutralButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        WordRepository.getInstance().deleteWord(mWord);
                        mWordRecyclerViewAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setView(view)
                .create();
    }

    private void uiListener() {
        englishWordET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mWord.setEngWord(englishWordET.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        persianWordET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mWord.setPerWord(persianWordET.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initUI(View view) {
        englishWordET=view.findViewById(R.id.englishWord_editText);
        persianWordET=view.findViewById(R.id.persainWord_editText);

        if(mWord.getEngWord()!=null || mWord.getPerWord()!=null) {
            englishWordET.setText(mWord.getEngWord());
            persianWordET.setText(mWord.getPerWord());
        }
    }
}
