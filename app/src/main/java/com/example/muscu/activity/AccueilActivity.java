package com.example.muscu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.muscu.R;

public class AccueilActivity extends Activity {

    private Button button;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        button = findViewById(R.id.planning);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openPlanning();
            }
        });

        button = findViewById(R.id.buttonProfil);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openInfos();
            }
        });

        button = findViewById(R.id.buttonNutrition);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openNutrition();
            }
        });

        button = findViewById(R.id.buttonWorkout);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openWorkout();
            }
        });

    }


    public void openNutrition() {
        Intent intent = new Intent(this, NutritionActivity.class);
        startActivity(intent);
    }
    public void openWorkout() {
        Intent intent = new Intent(this, WorkoutActivity.class);
        startActivity(intent);
    }
    public void openInfos() {
        Intent intent = new Intent(this, InfosActivity.class);
        startActivity(intent);
    }
    public void openPlanning() {
        Intent intent = new Intent(this, PlanningActivity.class);
        startActivity(intent);
    }
}
