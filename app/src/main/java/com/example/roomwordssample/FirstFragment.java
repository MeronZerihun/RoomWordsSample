package com.example.roomwordssample;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

public class FirstFragment extends Fragment {
    private WordViewModel mWordViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mWordViewModel = ViewModelProviders.of(getActivity()).get(WordViewModel.class);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        Context context = getActivity().getApplicationContext();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        final WordPageAdapter pageAdapter = new WordPageAdapter(context);
        recyclerView.setAdapter(pageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));


        mWordViewModel.getAllWords().observe(getViewLifecycleOwner(), new Observer<PagedList<Word>>() {
            @Override
            public void onChanged(PagedList<Word> words) {
                pageAdapter.submitList(words);
            }
        });

        // Alternative way for doing the above command concisely
        // mWordViewModel.getAllWords().observe(getViewLifecycleOwner(), pageAdapter::submitList );

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}