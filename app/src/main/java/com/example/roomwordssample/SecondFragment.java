package com.example.roomwordssample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static androidx.core.content.ContextCompat.getSystemService;

public class SecondFragment extends Fragment {


    private EditText mEditWordView;

    private WordViewModel mWordViewModel;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        mEditWordView = view.findViewById(R.id.edit_word);
        mWordViewModel = ViewModelProviders.of(getActivity()).get(WordViewModel.class);



        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button button = view.findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View myView) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(myView.getWindowToken(), 0);
                if (TextUtils.isEmpty(mEditWordView.getText())) {

                } else {
                    String word = mEditWordView.getText().toString();
                    mWordViewModel.insert(new Word(word)).observe(getViewLifecycleOwner(), new Observer<Long>() {
                        @Override
                        public void onChanged(Long id) {
                            TextView textView = view.findViewById(R.id.text_view);
                            textView.setText("id: "+id  );

                            new Handler().postDelayed(()->{
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .remove(SecondFragment.this)
                                        .commit();
                                    },1000
                            );

                        }
                    });

                }

            }
        });

    }

}