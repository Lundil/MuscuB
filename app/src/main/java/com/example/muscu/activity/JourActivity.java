package com.example.muscu.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.example.muscu.R;
import com.example.muscu.adapter.AlimentListAdapter;
import com.example.muscu.adapter.AlimentQuantiteListAdapter;
import com.example.muscu.model.AlimentModel;
import com.example.muscu.model.AlimentRepasModel;
import com.example.muscu.model.JourModel;

import java.util.ArrayList;
import java.util.List;

public class JourActivity extends Activity {

    private TextView textViewTitreJour,textViewPetitDej;
    private ListView listRepasAliment;
    private List<String> listAlimentModelQuantite;
    private List<AlimentRepasModel> listAlimentRepasModel;
    private AlimentQuantiteListAdapter alimentQuantiteListAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detail_jour);

        textViewTitreJour = findViewById(R.id.textViewTitreJour);
        textViewPetitDej = findViewById(R.id.textViewPetitDej);

        listRepasAliment = findViewById(R.id.listRepasAliment);

        Long idJourModelSelected = (Long) getIntent().getLongExtra("idJourModelSelected", 0L);
        if(idJourModelSelected != 0L){
            JourModel jourModel = JourModel.getJourById(idJourModelSelected);

            textViewTitreJour.setText(jourModel.getNom());
            textViewPetitDej.setText(jourModel.getRepasMatin().nom);

            listAlimentRepasModel = AlimentRepasModel.getAlimentRepasModelByRepas(jourModel.getRepasMatin().getId());
            if(listAlimentRepasModel!=null){
                listAlimentModelQuantite = new ArrayList<>();
                AlimentModel alim = null;
                for (AlimentRepasModel alimRepas : listAlimentRepasModel) {
                    alim = AlimentModel.getAlimentById(alimRepas.alimentModel);
                    listAlimentModelQuantite.add(alim.getNom()+" "+alimRepas.quantite + " " + ("Boisson".equalsIgnoreCase(alim.getTypeAliment()) ? "cl" : "g"));
                }

                alimentQuantiteListAdapter = new AlimentQuantiteListAdapter(this, R.layout.adapter_view_aliment_quantite_layout, listAlimentModelQuantite);
                listRepasAliment.setAdapter(alimentQuantiteListAdapter);
            }
        }
    }
}
