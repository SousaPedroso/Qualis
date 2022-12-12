package com.example.qualis.correlacao;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.RoomDatabase;

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
}
