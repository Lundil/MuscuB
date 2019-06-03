package com.example.muscu.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.example.muscu.R;
import com.facebook.stetho.Stetho;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends Activity {

    private Button button;
    private ZXingScannerView zXingScannerView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.buttonWorkout);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openWorkout();
            }
        });

        button = findViewById(R.id.buttonNutrition);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openNutrition();
            }
        });

        ActiveAndroid.initialize(this);
        Stetho.initializeWithDefaults(this);
    }
    public void openNutrition() {
        Intent intent = new Intent(this, NutritionActivity.class);
        startActivity(intent);
    }
    public void openWorkout() {
        Intent intent = new Intent(this, WorkoutActivity.class);
        startActivity(intent);
    }

}
