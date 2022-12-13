package com.example.qualis.conferencia;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.qualis.JsonHandler;

import org.json.JSONArray;
import org.json.JSONObject;

@Database(entities = {Conferencia.class}, version = 1, exportSchema = false)
public abstract class ConferenciaRoomDatabase extends RoomDatabase {

    public abstract ConferenciaDao conferenciaDao();
    private static ConferenciaRoomDatabase INSTANCE;
    private static Callback sRoomDatabaseCallback =
            new Callback(){

                @Override
                public void onCreate (@NonNull SupportSQLiteDatabase db){
                    super.onCreate(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    /**
     * Populate the database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ConferenciaDao mDao;
        String[] words = {"dolphin", "crocodile", "cobra"};

        PopulateDbAsync(ConferenciaRoomDatabase db) {
            mDao = db.conferenciaDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
            mDao.deleteAll();

            final String urlConferencias = "https://qualis.ic.ufmt.br/qualis_conferencias_2016.json";

            try {
                JSONObject json = JsonHandler.readJsonFromUrl(urlConferencias);
                JSONArray dataList = json.getJSONArray("data");
                Log.e("debug", "CRIOU O BANCO DE DADOS!");
                for (int i = 0; i < dataList.length(); i++) {
                    JSONArray currentData = dataList.getJSONArray(i);
                    Conferencia conferencia = new Conferencia(
                            currentData.getString(0),
                            currentData.getString(1),
                            currentData.getString(2));
                    mDao.insert(conferencia);
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


    static ConferenciaRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ConferenciaRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ConferenciaRoomDatabase.class, "conferencia_database")
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
