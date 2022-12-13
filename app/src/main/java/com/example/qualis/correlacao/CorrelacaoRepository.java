package com.example.qualis.correlacao;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.RoomDatabase;

import com.example.qualis.JsonHandler;
import com.example.qualis.conferencia.Conferencia;
import com.example.qualis.conferencia.ConferenciaDao;
import com.example.qualis.conferencia.ConferenciaRepository;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class CorrelacaoRepository {

    private CorrelacaoDao mCorrelacaoDao;
    private LiveData<List<Correlacao>> mAllCorrelacoes;

    CorrelacaoRepository(Application application) {
        CorrelacaoRoomDatabase db = CorrelacaoRoomDatabase.getDatabase(application);
        mCorrelacaoDao = db.correlacaoDao();
        mAllCorrelacoes = mCorrelacaoDao.getAllCorrelacoes();
    }

    LiveData<List<Correlacao>> getAllCorrelacoes() {
        return mAllCorrelacoes;
    }

    public void insert (Correlacao correlacao) {
        new CorrelacaoRepository.insertAsyncTask(mCorrelacaoDao).execute(correlacao);
    }

    private static class insertAsyncTask extends AsyncTask<Correlacao, Void, Void> {

        private CorrelacaoDao mAsyncTaskDao;

        insertAsyncTask(CorrelacaoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Correlacao... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void update()  {
        new CorrelacaoRepository.updateCorrelacoesAsyncTask(mCorrelacaoDao).execute();
    }

    private static class updateCorrelacoesAsyncTask extends AsyncTask<Void, Void, Void> {
        private CorrelacaoDao mAsyncTaskDao;

        updateCorrelacoesAsyncTask(CorrelacaoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created

            final String urlCorrelacoes = "https://qualis.ic.ufmt.br/todos2.json";

            try {
                JSONObject json = JsonHandler.readJsonFromUrl(urlCorrelacoes);
                JSONArray dataList = json.getJSONArray("data");
                mAsyncTaskDao.deleteAll();
                for (int i = 0; i < dataList.length(); i++) {
                    JSONArray currentData = dataList.getJSONArray(i);
                    Correlacao correlacao = new Correlacao(
                            currentData.getString(0),
                            currentData.getString(1),
                            currentData.getString(2),
                            currentData.getString(3));
                    mAsyncTaskDao.insert(correlacao);
                }
                return null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }
}
