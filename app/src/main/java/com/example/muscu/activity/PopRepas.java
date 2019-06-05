package com.example.muscu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.muscu.R;
import com.example.muscu.model.AlimentModel;
import com.example.muscu.model.RepasModel;

public class PopRepas extends Activity {

    private Button save;
    private EditText editNom;
    private EditText editTypeRepas;
    private EditText editProteine;
    private EditText editGlucide;
    private EditText editLipide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
