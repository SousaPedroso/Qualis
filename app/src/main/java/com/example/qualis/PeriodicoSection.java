package com.example.qualis;

import static com.example.qualis.R.id.button_conferencias_section;
import static com.example.qualis.R.id.button_correlacao_section;
import static com.example.qualis.R.id.button_periodicos_section;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PeriodicoSection extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodico_section);

        Button buttonPeriodicos = findViewById(button_periodicos_section);
        buttonPeriodicos.setOnClickListener(this);
        Button buttonConferencias = findViewById(button_conferencias_section);
        buttonConferencias.setOnClickListener(this);
        Button buttonCorrelacao = findViewById(button_correlacao_section);
        buttonCorrelacao.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case button_periodicos_section:
                Intent periodicosIntent = new Intent(this, PeriodicoSection.class);
                startActivity(periodicosIntent);
            case button_conferencias_section:
                Intent conferenciasIntent = new Intent(this, ConferenciaSection.class);
                startActivity(conferenciasIntent);
            case button_correlacao_section:
                Intent correlacaoIntent = new Intent(this, ConferenciaSection.class);
                startActivity(correlacaoIntent);
        }
    }
}