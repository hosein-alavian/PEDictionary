package com.example.pedictionary.controller;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;

import com.example.pedictionary.R;
import com.example.pedictionary.model.Word;
import com.example.pedictionary.model.WordRepository;
import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogFragment extends androidx.fragment.app.DialogFragment {

    public static final String NOTIFY_ADAPTER_IN_DIALOG_BUTTON_PRESSED = "notify adapter in dialog button pressed";
    public static final String TAG = "notify";
    public static final String WORD_ID = "word id";
    private UUID mId;
    private Word mWord;
    private EditText englishWordET;
    private EditText persianWordET;
    private AlertDialog mAlertDialog;
    private MaterialButton mShareButton;
    private DialogFragmentInterface callBack;

    public DialogFragment() {
        // Required empty public constructor
    }


    public static DialogFragment newInstance(UUID id) {

        Bundle args = new Bundle();
        args.putSerializable(WORD_ID, id);
        DialogFragment fragment = new DialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DialogFragmentInterface)
            callBack = (DialogFragmentInterface) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callBack = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mId = (UUID) getArguments().getSerializable(WORD_ID);
            mWord = WordRepository.getInstance(getContext()).getWord(mId);
        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_dialog, null, false);

        initUI(view);
        uiListener();

        if (mWord.getPerWord() == null && mWord.getEngWord() == null)
            mAlertDialog = addWordAlertDialog(view);
        else
            mAlertDialog = editWordAlertDialog(view);


        return mAlertDialog;
    }

    private AlertDialog addWordAlertDialog(View view) {
        mShareButton.setVisibility(View.GONE);
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.add_word)
                .setIcon(R.drawable.dialog_icon_image)
                .setPositiveButton(R.string.save, null)
                .setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                .setView(view)
                .create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mWord.getEngWord() != null || mWord.getPerWord() != null) {
                            try {
                                WordRepository.getInstance(getContext()).updateWord(mWord);
                                updateUI();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            dismiss();
                        } else
                            Toast.makeText(getActivity(),
                                    "english or persian word is empty!",
                                    Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return alertDialog;
    }

    private AlertDialog editWordAlertDialog(View view) {

        englishWordET.setText(mWord.getEngWord());
        persianWordET.setText(mWord.getPerWord());

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_editword)
                .setIcon(R.drawable.dialog_icon_image)
                .setPositiveButton(R.string.save, new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            WordRepository.getInstance(getContext()).updateWord(mWord);
                            updateUI();
                            Log.d(TAG, NOTIFY_ADAPTER_IN_DIALOG_BUTTON_PRESSED);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNeutralButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            WordRepository.getInstance(getContext()).deleteWord(mWord);
                            updateUI();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                .setView(view)
                .create();
    }

    private void updateUI() {
        List<Word> wordsList = WordRepository.getInstance(getContext()).getWords();
        callBack.notifyAdapter(wordsList, getContext());

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

        mShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareTextIntent();
            }
        });
    }

    private void shareTextIntent() {
        Intent shareIntent = ShareCompat.IntentBuilder.from(getActivity())
                .setType("text/plain")
                .setSubject("Word Translation")
                .setText(getString(R.string.share_word_text, mWord.getEngWord(), mWord.getPerWord()))
                .setChooserTitle(R.string.shareWord_chooserTitle)
                .createChooserIntent();
        if (shareIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(shareIntent);
        }
    }

    private void initUI(View view) {
        englishWordET = view.findViewById(R.id.englishWord_editText);
        persianWordET = view.findViewById(R.id.persainWord_editText);
        mShareButton = view.findViewById(R.id.shareWord_button);

    }

    public interface DialogFragmentInterface {
        void notifyAdapter(List<Word> wordList, Context context);
    }

    @Override
    public void onPause() {
        super.onPause();
/*        if (mAlertDialog != null
                && !mAlertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).getText().toString()
                .equals(getString(R.string.delete))) {
            try {
                WordRepository.getInstance(getContext()).deleteWord(mWord);
                updateUI();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
    }
}
