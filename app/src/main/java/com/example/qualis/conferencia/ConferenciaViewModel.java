package com.example.qualis.conferencia;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.qualis.correlacao.Correlacao;

import java.util.ArrayList;
import java.util.List;

public class ConferenciaViewModel extends AndroidViewModel {

    private ConferenciaRepository mRepository;

    private LiveData<List<Conferencia>> mAllConferencias;

    public ConferenciaViewModel (Application application) {
        super(application);
        mRepository = new ConferenciaRepository(application);
        mAllConferencias = mRepository.getAllConferencias();
    }


    public List<Conferencia> filterConferenciaByCategory(String category) {
        List<Conferencia> filteredCorrelacoes= new ArrayList<>();

        List<Conferencia> allPeriodicos = mAllConferencias.getValue();

        if (allPeriodicos != null) {
            for (Conferencia correlacao : allPeriodicos) {
                if (correlacao.getExtratoCapes().contains(category)) {
                    filteredCorrelacoes.add(correlacao);
                }
            }
        }

        return filteredCorrelacoes;
    }

    public List<Conferencia> searchAndFilterConferencia(String query, String category) {
        List<Conferencia> filteredCorrelacao = new ArrayList<>();

        List<Conferencia> allCorrelacao = getAllConferencias().getValue();

        if (!category.equals("Todos")) {
            allCorrelacao = filterConferenciaByCategory(category);
        }



        query = query.toLowerCase();
        for (int i = 0; i < allCorrelacao.size(); i++) {
            Conferencia conferencia = allCorrelacao.get(i);
            String periodicoName = conferencia.getNome().toLowerCase();

            if (periodicoName.contains(query)) {
                filteredCorrelacao.add(conferencia);


            }
        }

        return filteredCorrelacao;
    }


    LiveData<List<Conferencia>> getAllConferencias() { return mAllConferencias; }

    public void insert(Conferencia conferencia) { mRepository.insert(conferencia); }
}
