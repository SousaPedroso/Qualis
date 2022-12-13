package com.example.qualis.conferencia;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "conferencia_table")
public class Conferencia {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sigla")
    private String sigla;

    @NonNull
    @ColumnInfo(name = "nome")
    private String nome;

    @NonNull
    @ColumnInfo(name = "extrato_capes")
    private String extratoCapes;

    public Conferencia(@NonNull String sigla, @NonNull String nome, @NonNull String extratoCapes) {
        this.sigla = sigla;
        this.nome = nome;
        this.extratoCapes = extratoCapes;
    }

    public String getSigla(){return this.sigla;}
    public String getNome(){return this.nome;}
    public String getExtratoCapes(){return this.extratoCapes;}
}
