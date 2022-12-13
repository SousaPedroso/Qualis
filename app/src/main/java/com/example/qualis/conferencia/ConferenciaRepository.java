package com.example.qualis.conferencia;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

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
}
