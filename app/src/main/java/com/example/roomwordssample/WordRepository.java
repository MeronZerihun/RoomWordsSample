package com.example.roomwordssample;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.google.gson.Gson;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

public class WordRepository {
    private WordDao mWordDao;
    private LiveData<PagedList<Word>> mAllWords;
    static Application mApplication;

    private MutableLiveData<Long> dbInsertId = new MutableLiveData<>();


    WordRepository(Application application) {

        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = new LivePagedListBuilder<Integer, Word>(
                mWordDao.getAllWords(), 10).build();
        mApplication = application;
    }


    public WordDao getWordDao() {
        return mWordDao;
    }

    public static Application getApp(){return mApplication;}

    LiveData<PagedList<Word>> getAllWords() {
        return mAllWords;
    }



    public LiveData<Long> insertAsync(Word word){

        Executors.newSingleThreadExecutor().execute(() -> {

            long id = getWordDao().insertWord(word);
            dbInsertId.postValue(id);

        });
        return dbInsertId;


    }
}

