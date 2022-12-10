package com.example.qualis.periodicos;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.RoomDatabase;

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
}
