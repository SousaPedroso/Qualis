package com.example.qualis.periodicos;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PeriodicoViewModel extends AndroidViewModel {

    private PeriodicoRepository mRepository;

    private LiveData<List<Periodico>> mAllPeriodicos;

    public PeriodicoViewModel (Application application) {
        super(application);
        mRepository = new PeriodicoRepository(application);
        mAllPeriodicos = mRepository.getAllPeriodicos();
    }

    LiveData<List<Periodico>> getAllPeriodicos() { return mAllPeriodicos; }

    public void insert(Periodico periodico) { mRepository.insert(periodico); }
}
