package com.example.qualis.correlacao;

import static com.example.qualis.R.id.button_conferencias_section;
import static com.example.qualis.R.id.button_correlacao_section;
import static com.example.qualis.R.id.button_periodicos_section;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.qualis.conferencia.ConferenciaSection;
import com.example.qualis.periodicos.PeriodicoSection;
import com.example.qualis.R;

public class CorrelacaoSection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correlacao_section);

        Button buttonPeriodicos = findViewById(button_periodicos_section);
        buttonPeriodicos.setOnClickListener(view -> startActivity(
                new Intent(CorrelacaoSection.this, PeriodicoSection.class))
        );

        Button buttonConferencias = findViewById(button_conferencias_section);
        buttonConferencias.setOnClickListener(view -> startActivity(
                new Intent(CorrelacaoSection.this, ConferenciaSection.class))
        );

        Button buttonCorrelacao = findViewById(button_correlacao_section);
        buttonCorrelacao.setOnClickListener(view -> startActivity(
                new Intent(CorrelacaoSection.this, CorrelacaoSection.class))
        );

        RecyclerView recyclerView = findViewById(R.id.correlacoes_recycler_view);
        final CorrelacaoListAdapter adapter = new CorrelacaoListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CorrelacaoViewModel correlacaoViewModel = new ViewModelProvider(this).get(CorrelacaoViewModel.class);
        correlacaoViewModel.getAllCorrelacoes().observe(this, words -> {
            // Update the cached copy of the words in the adapter.
            adapter.setCorrelacoes(words);
        });
    }
}