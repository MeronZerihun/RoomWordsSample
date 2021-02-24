package com.example.roomwordssample;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface WordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertWord(Word word);

    @Query("Delete from word_table")
    void deleteAll();

    @Query("SELECT * from word_table ORDER BY word ASC")
    DataSource.Factory<Integer, Word> getAllWords();


}
