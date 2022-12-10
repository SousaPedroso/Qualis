package com.example.qualis.periodicos;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.qualis.JsonHandler;

import org.json.JSONArray;
import org.json.JSONObject;

@Database(entities = {Periodico.class}, version = 1, exportSchema = false)
public abstract class PeriodicoRoomDatabase extends RoomDatabase {

    public abstract PeriodicoDao periodicoDao();
    private static PeriodicoRoomDatabase INSTANCE;
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    /**
     * Populate the database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final PeriodicoDao mDao;
        String[] words = {"dolphin", "crocodile", "cobra"};

        PopulateDbAsync(PeriodicoRoomDatabase db) {
            mDao = db.periodicoDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
            mDao.deleteAll();

            final String urlPeriodicos = "https://qualis.ic.ufmt.br/periodico.json";

            try {
                JSONObject json = JsonHandler.readJsonFromUrl(urlPeriodicos);
                JSONArray dataList = json.getJSONArray("data");
                for (int i = 0; i < dataList.length(); i++) {
                    JSONArray currentData = dataList.getJSONArray(i);
                    Periodico periodico = new Periodico(
                            currentData.getString(0),
                            currentData.getString(1),
                            currentData.getString(2));
                    mDao.insert(periodico);
                }
                return null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }

//            for (int i = 0; i <= words.length - 1; i++) {
//                Word word = new Word(words[i]);
//                mDao.insert(word);
//            }
//            return null;
            return null;
        }
    }


    static PeriodicoRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PeriodicoRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    PeriodicoRoomDatabase.class, "periodico_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
