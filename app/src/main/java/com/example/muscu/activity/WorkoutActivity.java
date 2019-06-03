package com.example.muscu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.muscu.R;

public class WorkoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        setTitle("Workout");
    }
}
