package com.example.qualis.periodicos;

import static com.example.qualis.R.id.button_conferencias_section;
import static com.example.qualis.R.id.button_correlacao_section;
import static com.example.qualis.R.id.button_periodicos_section;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qualis.ConferenciaSection;
import com.example.qualis.R;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class PeriodicoSection extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodico_section);

        Button buttonPeriodicos = findViewById(button_periodicos_section);
        buttonPeriodicos.setOnClickListener(view -> startActivity(
                new Intent(PeriodicoSection.this, PeriodicoSection.class))
        );

        Button buttonConferencias = findViewById(button_conferencias_section);
        buttonConferencias.setOnClickListener(view -> startActivity(
                new Intent(PeriodicoSection.this, ConferenciaSection.class))
        );

        Button buttonCorrelacao = findViewById(button_correlacao_section);
        buttonCorrelacao.setOnClickListener(view -> startActivity(
                new Intent(PeriodicoSection.this, ConferenciaSection.class))
        );

        RecyclerView recyclerView = findViewById(R.id.periodicos_recycler_view);
        final PeriodicoListAdapter adapter = new PeriodicoListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        PeriodicoViewModel periodicoViewModel = new ViewModelProvider(this).get(PeriodicoViewModel.class);
        periodicoViewModel.getAllPeriodicos().observe(this, words -> {
            // Update the cached copy of the words in the adapter.
            adapter.setPeriodicos(words);
        });

    }
}