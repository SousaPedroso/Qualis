package com.example.qualis.correlacao;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.qualis.JsonHandler;

import org.json.JSONArray;
import org.json.JSONObject;

@Database(entities = {Correlacao.class}, version = 1, exportSchema = false)
public abstract class CorrelacaoRoomDatabase extends RoomDatabase{
    public abstract CorrelacaoDao correlacaoDao();
    private static CorrelacaoRoomDatabase INSTANCE;
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

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

        private final CorrelacaoDao mDao;

        PopulateDbAsync(CorrelacaoRoomDatabase db) {
            mDao = db.correlacaoDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
            mDao.deleteAll();

            final String urlCorrelacoes = "https://qualis.ic.ufmt.br/todos2.json";

            try {
                JSONObject json = JsonHandler.readJsonFromUrl(urlCorrelacoes);
                JSONArray dataList = json.getJSONArray("data");
                Log.e("debug", "POPULOU O BANCO DE DADOS!");
                for (int i = 0; i < dataList.length(); i++) {
                    JSONArray currentData = dataList.getJSONArray(i);
                    Correlacao correlacao = new Correlacao(
                            currentData.getString(0),
                            currentData.getString(1),
                            currentData.getString(2),
                            currentData.getString(3));
                    mDao.insert(correlacao);
                }
                return null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }


    static CorrelacaoRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CorrelacaoRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    CorrelacaoRoomDatabase.class, "correlacao_database")
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
