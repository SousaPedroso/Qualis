package com.example.qualis.conferencia;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.qualis.JsonHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class ConferenciaRepository {

    private ConferenciaDao mConferenciaDao;
    private LiveData<List<Conferencia>> mAllConferencias;

    ConferenciaRepository(Application application) {
        ConferenciaRoomDatabase db = ConferenciaRoomDatabase.getDatabase(application);
        mConferenciaDao = db.conferenciaDao();
        mAllConferencias = mConferenciaDao.getAllConferencias();
    }

    LiveData<List<Conferencia>> getAllConferencias() {
        return mAllConferencias;
    }

    public void insert (Conferencia conferencia) {
        new insertAsyncTask(mConferenciaDao).execute(conferencia);
    }

    private static class insertAsyncTask extends AsyncTask<Conferencia, Void, Void> {

        private ConferenciaDao mAsyncTaskDao;

        insertAsyncTask(ConferenciaDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Conferencia... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void update()  {
        new updateConferenciasAsyncTask(mConferenciaDao).execute();
    }

    private static class updateConferenciasAsyncTask extends AsyncTask<Void, Void, Void> {
        private ConferenciaDao mAsyncTaskDao;

        updateConferenciasAsyncTask(ConferenciaDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created

            final String urlConferencias = "https://qualis.ic.ufmt.br/qualis_conferencias_2016.json";

            try {
                JSONObject json = JsonHandler.readJsonFromUrl(urlConferencias);
                JSONArray dataList = json.getJSONArray("data");
                mAsyncTaskDao.deleteAll();
                for (int i = 0; i < dataList.length(); i++) {
                    JSONArray currentData = dataList.getJSONArray(i);
                    Conferencia conferencia = new Conferencia(
                            currentData.getString(0),
                            currentData.getString(1),
                            currentData.getString(2));
                    mAsyncTaskDao.insert(conferencia);
                }
                Log.e("debug", "ACESSOU O BANCO DE DADOS!");
                return null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }

}
