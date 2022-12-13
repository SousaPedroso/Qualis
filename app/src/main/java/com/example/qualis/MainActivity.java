package com.example.qualis;

import static com.example.qualis.R.id.button_conferencias_section;
import static com.example.qualis.R.id.button_correlacao_section;
import static com.example.qualis.R.id.button_periodicos_section;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.qualis.conferencia.ConferenciaSection;
import com.example.qualis.conferencia.ConferenciaViewModel;
import com.example.qualis.correlacao.CorrelacaoViewModel;
import com.example.qualis.periodicos.PeriodicoSection;
import com.example.qualis.periodicos.PeriodicoViewModel;

public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonPeriodicos = findViewById(button_periodicos_section);
        buttonPeriodicos.setOnClickListener(view -> startActivity(
                new Intent(MainActivity.this, PeriodicoSection.class))
        );

        Button buttonConferencias = findViewById(button_conferencias_section);
        buttonConferencias.setOnClickListener(view -> startActivity(
                new Intent(MainActivity.this, ConferenciaSection.class))
        );

        Button buttonCorrelacao = findViewById(button_correlacao_section);
        buttonCorrelacao.setOnClickListener(view -> startActivity(
                new Intent(MainActivity.this, ConferenciaSection.class))
        );

        Button buttonAtualizar = findViewById(R.id.button_update_database);
        buttonAtualizar.setOnClickListener( view -> {
                ConferenciaViewModel conferenciaViewModel = new ViewModelProvider(this).get(ConferenciaViewModel.class);
                CorrelacaoViewModel correlacaoViewModel = new ViewModelProvider(this).get(CorrelacaoViewModel.class);
                PeriodicoViewModel periodicoViewModel = new ViewModelProvider(this).get(PeriodicoViewModel.class);
                periodicoViewModel.update();
                correlacaoViewModel.update();
                conferenciaViewModel.update();
            }
        );
    }
}