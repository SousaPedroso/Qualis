package com.example.qualis.correlacao;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "correlacao_table")
public class Correlacao {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "issn")
    private String issn;

    @NonNull
    @ColumnInfo(name = "area")
    private String area;

    @NonNull
    @ColumnInfo(name ="nome_periodico")
    private String nomePeriodico;

    @NonNull
    @ColumnInfo(name = "extrato_capes")
    private String extratoCapes;

    public Correlacao(@NonNull String issn, @NonNull String area, @NonNull String nomePeriodico, @NonNull String extratoCapes) {
        this.issn = issn;
        this.area = area;
        this.nomePeriodico = nomePeriodico;
        this.extratoCapes = extratoCapes;
    }

    public String getIssn(){return this.issn;}
    public String getArea(){return this.area;}
    public String getExtratoCapes(){return this.extratoCapes;}
    public String getNomePeriodico() {return this.nomePeriodico;}
}
