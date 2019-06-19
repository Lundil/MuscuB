package com.example.muscu.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.example.muscu.R;
import com.example.muscu.model.JourModel;

public class JourActivity extends Activity {

    private TextView textViewTitreSemaine;
    private ListView listView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_jour);

        textViewTitreSemaine = findViewById(R.id.textViewTitreSemaine);

        Long idJourModelSelected = (Long) getIntent().getLongExtra("idJourModelSelected", 0L);
        if(idJourModelSelected != 0L){
            JourModel jourModel = JourModel.getJourById(idJourModelSelected);

            textViewTitreSemaine.setText(jourModel.getNom());

        }
    }
}
