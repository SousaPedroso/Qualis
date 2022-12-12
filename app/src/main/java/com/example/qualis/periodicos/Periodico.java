package com.example.qualis.periodicos;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "periodico_table")
public class Periodico {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "issn")
    private String issn;

    @NonNull
    @ColumnInfo(name = "nome")
    private String nome;

    @NonNull
    @ColumnInfo(name = "extrato_capes")
    private String extratoCapes;

    public Periodico(@NonNull String issn, @NonNull String nome, @NonNull String extratoCapes) {
        this.issn = issn;
        this.nome = nome;
        this.extratoCapes = extratoCapes;
    }

    public String getIssn(){return this.issn;}
    public String getNome(){return this.nome;}
    public String getExtratoCapes(){return this.extratoCapes;}
}
