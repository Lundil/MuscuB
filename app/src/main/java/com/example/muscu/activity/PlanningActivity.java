package com.example.muscu.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.muscu.R;

import java.security.AlgorithmParameterGenerator;

public class PlanningActivity extends Activity {

    private AlgorithmParameterGenerator mAgendaCalendarView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_planning);
        setTitle("Planning");



    }
}
