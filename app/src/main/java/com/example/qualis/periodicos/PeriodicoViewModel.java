package com.example.qualis.periodicos;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

public class PeriodicoViewModel extends AndroidViewModel {

    private PeriodicoRepository mRepository;

    private static LiveData<List<Periodico>> mAllPeriodicos;

    public void update() {mRepository.update();}

    public PeriodicoViewModel (Application application) {
        super(application);
        mRepository = new PeriodicoRepository(application);
        mAllPeriodicos = mRepository.getAllPeriodicos();
    }

    public List<Periodico> filterPeriodicosByCategory(String category) {
        List<Periodico> filteredPeriodicos = new ArrayList<>();

        List<Periodico> allPeriodicos = mAllPeriodicos.getValue();

        if (allPeriodicos != null) {
            for (Periodico periodico : allPeriodicos) {
                if (periodico.getExtratoCapes().equals(category)) {
                    filteredPeriodicos.add(periodico);
                }
            }
        }

        return filteredPeriodicos;
    }


    public List<Periodico> searchAndFilterPeriodicos(String query, String category) {
        List<Periodico> filteredPeriodicos = new ArrayList<>();

        List<Periodico> allPeriodicos = getAllPeriodicos().getValue();

        if (!category.equals("Todos")) {
            allPeriodicos = filterPeriodicosByCategory(category);
        }



        query = query.toLowerCase();
        for (int i = 0; i < allPeriodicos.size(); i++) {
            Periodico periodico = allPeriodicos.get(i);
            String periodicoName = periodico.getNome().toLowerCase();

            if (periodicoName.contains(query)) {
                filteredPeriodicos.add(periodico);
            }
        }

        return filteredPeriodicos;
    }

    LiveData<List<Periodico>> getAllPeriodicos() { return mAllPeriodicos; }

    public void insert(Periodico periodico) { mRepository.insert(periodico); }




}
