package com.example.qualis.correlacao;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CorrelacaoViewModel extends AndroidViewModel {

    private CorrelacaoRepository mRepository;

    private LiveData<List<Correlacao>> mAllCorrelacoes;

    public CorrelacaoViewModel (Application application) {
        super(application);
        mRepository = new CorrelacaoRepository(application);
        mAllCorrelacoes = mRepository.getAllCorrelacoes();
    }

    LiveData<List<Correlacao>> getAllCorrelacoes() { return mAllCorrelacoes; }

    public void insert(Correlacao correlacao) { mRepository.insert(correlacao); }
}
