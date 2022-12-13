package com.example.qualis.conferencia;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ConferenciaViewModel extends AndroidViewModel {

    private ConferenciaRepository mRepository;

    private LiveData<List<Conferencia>> mAllConferencias;

    public ConferenciaViewModel (Application application) {
        super(application);
        mRepository = new ConferenciaRepository(application);
        mAllConferencias = mRepository.getAllConferencias();
    }

    LiveData<List<Conferencia>> getAllConferencias() { return mAllConferencias; }

    public void insert(Conferencia conferencia) { mRepository.insert(conferencia); }
}
