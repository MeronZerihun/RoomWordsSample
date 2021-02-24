package com.example.roomwordssample;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {

    private static Context mContext;

    public abstract WordDao wordDao();

    private static WordRoomDatabase INSTANCE;

    public static WordRoomDatabase getDatabase(final Context context) {
        mContext = context;
        if (INSTANCE == null) {
            synchronized (WordRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class, "word_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    //populateDb(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    /*private static void populateDb() {
        WorkRequest populateRequest =
                new OneTimeWorkRequest.Builder(populateDBWorker.class)
                        .build();

        WorkManager
                .getInstance(mContext)
                .enqueue(populateRequest);

    }*/

    /**
     * Populate the database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WordDao mDao;
        String[] words = {"dolphin", "crocodile", "cobra", "bee", "bird"};

        PopulateDbAsync(WordRoomDatabase db) {
            mDao = db.wordDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
            mDao.deleteAll();

            for (int i = 0; i <= words.length - 1; i++) {
                Word word = new Word(words[i]);
                mDao.insertWord(word);
            }
            return null;
        }
    }




}

/*public class populateDBWorker extends Worker {

    private WordDao mDao;
    String[] words = {"dolphin", "crocodile", "cobra"};

    populateDBWorker(@NonNull Context context,
                     @NonNull WorkerParameters params) {
        super(context, params);
        mDao = INSTANCE.wordDao();
    }

    @NonNull
    @Override
    public Result doWork() {
        mDao.deleteAll();

        for (int i = 0; i <= words.length - 1; i++) {
            Word word = new Word(words[i]);
            mDao.insertWord(word);
        }

        return null;
    }
}
*/