package com.example.qualis;

import static com.example.qualis.R.id.button_periodicos_section;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonPeriodicos = findViewById(button_periodicos_section);
        buttonPeriodicos.setOnClickListener(this);
        Button buttonConferencias = findViewById(R.id.button_conferencias_section);
        buttonConferencias.setOnClickListener(this);
        Button buttonCorrelacao = findViewById(R.id.button_correlacao_section);
        buttonCorrelacao.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case button_periodicos_section:
                Intent intent = new Intent(this, PeriodicoSection.class);
                startActivity(intent);
        }
    }
}