package com.example.qualis.correlacao;

import static com.example.qualis.R.id.button_conferencias_section;
import static com.example.qualis.R.id.button_correlacao_section;
import static com.example.qualis.R.id.button_periodicos_section;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.example.qualis.conferencia.ConferenciaSection;
import com.example.qualis.periodicos.PeriodicoSection;
import com.example.qualis.R;

import java.util.List;

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

        RecyclerView recyclerView = findViewById(R.id.sections_recycler_view);
        final CorrelacaoListAdapter adapter = new CorrelacaoListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CorrelacaoViewModel correlacaoViewModel = new ViewModelProvider(this).get(CorrelacaoViewModel.class);
        correlacaoViewModel.getAllCorrelacoes().observe(this, words -> {
            // Update the cached copy of the words in the adapter.
            adapter.setCorrelacoes(words);
        });

        Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = (String) parent.getItemAtPosition(position);

                if (selectedCategory.equals("Todos")) {
                    List<Correlacao> allCorrelacao = correlacaoViewModel.getAllCorrelacoes().getValue();

                    adapter.setCorrelacoes(allCorrelacao);
                } else {
                    List<Correlacao> filteredCorrelacao = correlacaoViewModel.filterCorrelacaoByCategory(selectedCategory);

                    adapter.setCorrelacoes(filteredCorrelacao);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });



        androidx.appcompat.widget.SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle the search query here
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter the list of correlacao objects based on the search query
                // Get the selected category and search query from the spinner and search view, respectively
                String selectedCategory = (String) spinner.getSelectedItem();
                String searchQuery = searchView.getQuery().toString();

                // Filter the list of correlacao by the selected category and search query
                List<Correlacao> filteredCorrelacao = correlacaoViewModel.searchAndFilterCorrelacao(searchQuery, selectedCategory);

                // Update the list in the RecyclerView adapter with the filtered list

                // Check if the LiveData object is not null before accessing its data
                // Use the post method of the RecyclerView object to update the adapter on the main thread
                // after the background thread has finished filtering the list of correlacao
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setCorrelacoes(filteredCorrelacao);
                    }
                });


                return false;
            }
        });

    }
}