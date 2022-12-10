package com.example.qualis.periodicos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PeriodicoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Periodico periodico);

    @Query("DELETE FROM periodico_table")
    void deleteAll();

    @Query("SELECT * from periodico_table")
    LiveData<List<Periodico>> getAllPeriodicos();
}
