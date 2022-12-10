package com.example.qualis;

import static com.example.qualis.R.id.button_conferencias_section;
import static com.example.qualis.R.id.button_correlacao_section;
import static com.example.qualis.R.id.button_periodicos_section;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Correlacao_Section extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correlacao_section);

        Button buttonPeriodicos = findViewById(button_periodicos_section);
        buttonPeriodicos.setOnClickListener(view -> startActivity(
                new Intent(Correlacao_Section.this, PeriodicoSection.class))
        );

        Button buttonConferencias = findViewById(button_conferencias_section);
        buttonConferencias.setOnClickListener(view -> startActivity(
                new Intent(Correlacao_Section.this, ConferenciaSection.class))
        );

        Button buttonCorrelacao = findViewById(button_correlacao_section);
        buttonCorrelacao.setOnClickListener(view -> startActivity(
                new Intent(Correlacao_Section.this, ConferenciaSection.class))
        );
    }
}