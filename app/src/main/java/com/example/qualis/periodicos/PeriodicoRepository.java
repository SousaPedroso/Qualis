package com.example.qualis.periodicos;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.RoomDatabase;

import com.example.qualis.JsonHandler;
import com.example.qualis.correlacao.Correlacao;
import com.example.qualis.correlacao.CorrelacaoDao;
import com.example.qualis.correlacao.CorrelacaoRepository;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class PeriodicoRepository {

    private PeriodicoDao mPeriodicoDao;
    private LiveData<List<Periodico>> mAllPeriodicos;

    PeriodicoRepository(Application application) {
        PeriodicoRoomDatabase db = PeriodicoRoomDatabase.getDatabase(application);
        mPeriodicoDao = db.periodicoDao();
        mAllPeriodicos = mPeriodicoDao.getAllPeriodicos();
    }

    LiveData<List<Periodico>> getAllPeriodicos() {
        return mAllPeriodicos;
    }

    public void insert (Periodico periodico) {
        new insertAsyncTask(mPeriodicoDao).execute(periodico);
    }

    private static class insertAsyncTask extends AsyncTask<Periodico, Void, Void> {

        private PeriodicoDao mAsyncTaskDao;

        insertAsyncTask(PeriodicoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Periodico... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void update()  {
        new PeriodicoRepository.updatePeriodicosAsyncTask(mPeriodicoDao).execute();
    }

    private static class updatePeriodicosAsyncTask extends AsyncTask<Void, Void, Void> {
        private PeriodicoDao mAsyncTaskDao;

        updatePeriodicosAsyncTask(PeriodicoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created

            final String urlPeriodicos = "https://qualis.ic.ufmt.br/periodico.json";

            try {
                JSONObject json = JsonHandler.readJsonFromUrl(urlPeriodicos);
                JSONArray dataList = json.getJSONArray("data");
                mAsyncTaskDao.deleteAll();
                for (int i = 0; i < dataList.length(); i++) {
                    JSONArray currentData = dataList.getJSONArray(i);
                    Periodico periodico = new Periodico(
                            currentData.getString(0),
                            currentData.getString(1),
                            currentData.getString(2));
                    mAsyncTaskDao.insert(periodico);
                }
                return null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }
}
