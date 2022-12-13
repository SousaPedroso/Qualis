package com.example.qualis.conferencia;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ConferenciaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Conferencia conferencia);

    @Query("DELETE FROM conferencia_table")
    void deleteAll();

    @Query("SELECT * from conferencia_table")
    LiveData<List<Conferencia>> getAllConferencias();
}
