package com.example.roomwordssample;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

public class WordViewModel extends AndroidViewModel {

    private WordRepository mRepository;
    private final LiveData<PagedList<Word>> mAllWords;


    public WordViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();


    }

    LiveData<PagedList<Word>> getAllWords() { return mAllWords; }

    public LiveData<Long> insert(Word word) { return mRepository.insertAsync(word); }


}
