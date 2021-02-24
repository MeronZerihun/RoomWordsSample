package com.example.roomwordssample;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "word_table")
public class Word {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;

    public Word(@NonNull String word) {this.mWord = word;}



    public String getWord(){return this.mWord;}
    public long getId() { return id; }
    public void setId(long id) {
        this.id = id;
    }


    public boolean equals( Word w){
        return (this.mWord.equals(w.mWord));
    }
}

