package com.example.qualis.periodicos;

import static com.example.qualis.R.id.button_conferencias_section;
import static com.example.qualis.R.id.button_correlacao_section;
import static com.example.qualis.R.id.button_periodicos_section;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qualis.ConferenciaSection;
import com.example.qualis.correlacao.CorrelacaoSection;
import com.example.qualis.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import java.util.List;

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
                new Intent(PeriodicoSection.this, CorrelacaoSection.class))
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

        Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = (String) parent.getItemAtPosition(position);

                if (selectedCategory.equals("Todos")) {
                    List<Periodico> allPeriodicos = periodicoViewModel.getAllPeriodicos().getValue();

                    adapter.setPeriodicos(allPeriodicos);
                } else {
                    List<Periodico> filteredPeriodicos = periodicoViewModel.filterPeriodicosByCategory(selectedCategory);

                    adapter.setPeriodicos(filteredPeriodicos);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });



        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle the search query here
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter the list of Periodico objects based on the search query
                // Get the selected category and search query from the spinner and search view, respectively
                String selectedCategory = (String) spinner.getSelectedItem();
                String searchQuery = searchView.getQuery().toString();

                // Filter the list of periodicos by the selected category and search query
                List<Periodico> filteredPeriodicos = periodicoViewModel.searchAndFilterPeriodicos(searchQuery, selectedCategory);

                // Update the list in the RecyclerView adapter with the filtered list
                adapter.setPeriodicos(filteredPeriodicos);

                // Check if the LiveData object is not null before accessing its data
                // Use the post method of the RecyclerView object to update the adapter on the main thread
                // after the background thread has finished filtering the list of periodicos
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setPeriodicos(filteredPeriodicos);
                    }
                });


                return false;
            }
        });


    }
}