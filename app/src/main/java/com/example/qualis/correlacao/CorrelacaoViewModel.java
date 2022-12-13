package com.example.qualis.correlacao;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

public class CorrelacaoViewModel extends AndroidViewModel {

    private CorrelacaoRepository mRepository;

    private LiveData<List<Correlacao>> mAllCorrelacoes;

    public CorrelacaoViewModel (Application application) {
        super(application);
        mRepository = new CorrelacaoRepository(application);
        mAllCorrelacoes = mRepository.getAllCorrelacoes();
    }

    public List<Correlacao> filterCorrelacaoByCategory(String category) {
        List<Correlacao> filteredCorrelacoes= new ArrayList<>();

        List<Correlacao> allPeriodicos = mAllCorrelacoes.getValue();

        if (allPeriodicos != null) {
            for (Correlacao correlacao : allPeriodicos) {
                if (correlacao.getExtratoCapes().contains(category)) {
                    filteredCorrelacoes.add(correlacao);
                }
            }
        }

        return filteredCorrelacoes;
    }

    public List<Correlacao> searchAndFilterCorrelacao(String query, String category) {
        List<Correlacao> filteredCorrelacao = new ArrayList<>();

        List<Correlacao> allCorrelacao = getAllCorrelacoes().getValue();

        if (!category.equals("Todos")) {
            allCorrelacao = filterCorrelacaoByCategory(category);
        }



        query = query.toLowerCase();
        for (int i = 0; i < allCorrelacao.size(); i++) {
            Correlacao correlacao = allCorrelacao.get(i);
            String periodicoName = correlacao.getArea().toLowerCase();

            if (periodicoName.contains(query)) {
                filteredCorrelacao.add(correlacao);


            }
        }

        return filteredCorrelacao;
    }




    LiveData<List<Correlacao>> getAllCorrelacoes() { return mAllCorrelacoes; }

    public void insert(Correlacao correlacao) { mRepository.insert(correlacao); }
}
