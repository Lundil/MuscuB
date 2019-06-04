package com.example.muscu.activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.muscu.R;

import java.security.AlgorithmParameterGenerator;

public class PlanningActivity extends Activity {

    private AlgorithmParameterGenerator mAgendaCalendarView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);
        setTitle("Planning");



    }
}
