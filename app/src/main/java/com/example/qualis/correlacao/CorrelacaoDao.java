package com.example.qualis.correlacao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CorrelacaoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Correlacao correlacao);

    @Query("DELETE FROM correlacao_table")
    void deleteAll();

    @Query("SELECT * from correlacao_table")
    LiveData<List<Correlacao>> getAllCorrelacoes();
}
