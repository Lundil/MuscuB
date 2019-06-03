package com.example.muscu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.muscu.R;

public class NutritionActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);
        setTitle("Nutrition");

        button = findViewById(R.id.buttonAliments);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openAliments();
            }
        });

        button = findViewById(R.id.buttonRepas);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openRepas();
            }
        });
    }

    public void openRepas() {
        Intent intent = new Intent(this, RepasActivity.class);
        startActivity(intent);
    }
    public void openAliments() {
        Intent intent = new Intent(this, AlimentActivity.class);
        startActivity(intent);
    }
}
