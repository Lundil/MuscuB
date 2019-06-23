package com.example.muscu.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.example.muscu.R;
import com.example.muscu.adapter.AlimentQuantiteListAdapter;
import com.example.muscu.model.AlimentModel;
import com.example.muscu.model.AlimentRepasModel;
import com.example.muscu.model.JourModel;

import java.util.ArrayList;
import java.util.List;

public class JourActivity extends Activity {

    private TextView textViewTitreJour, textViewPetitDej, textViewMidi, textViewDiner,  textViewEncas1, textViewEncas2, textViewEncas3;
    private ListView listViewRepasAlimentPetitDej, listViewRepasAlimentMidij, listViewRepasAlimentDiner;
    private List<String> listAlimentModelQuantite;
    private List<AlimentRepasModel> listRepasAlimentPdj,listRepasAlimentMidi,listRepasAlimentDiner;
    private AlimentQuantiteListAdapter alimentQuantiteListAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detail_jour);

        textViewTitreJour = findViewById(R.id.textViewTitreJour);
        textViewPetitDej = findViewById(R.id.textViewPetitDej);
        textViewMidi = findViewById(R.id.textViewMidi);
        textViewDiner = findViewById(R.id.textViewDiner);
        textViewEncas1 = findViewById(R.id.textViewEncas1);
        textViewEncas2 = findViewById(R.id.textViewEncas2);
        textViewEncas3 = findViewById(R.id.textViewEncas3);

        listViewRepasAlimentPetitDej = findViewById(R.id.listRepasAlimentPetitDej);
        listViewRepasAlimentMidij = findViewById(R.id.listRepasAlimentMidi);
        listViewRepasAlimentDiner = findViewById(R.id.listRepasAlimentDiner);

        Long idJourModelSelected = (Long) getIntent().getLongExtra("idJourModelSelected", 0L);
        if(idJourModelSelected != 0L){
            JourModel jourModel = JourModel.getJourById(idJourModelSelected);

            textViewTitreJour.setText(jourModel.getNom());
            textViewPetitDej.setText(jourModel.getRepasMatin().nom);
            textViewMidi.setText(jourModel.getRepasMidi().nom);
            textViewDiner.setText(jourModel.getRepasDiner().nom);
            /*if(){
                if(){
                    textViewEncas1.setText(jourModel.getRepasEncas1().nom);
                }else{
                    textViewEncas1.setVisibility(View.GONE);
                }

            }*/
            listRepasAlimentPdj = AlimentRepasModel.getAlimentRepasModelByRepas(jourModel.getRepasMatin().getId());
            if(listRepasAlimentPdj!=null){
                listAlimentModelQuantite = new ArrayList<>();
                AlimentModel alim = null;
                for (AlimentRepasModel alimRepas : listRepasAlimentPdj) {
                    alim = AlimentModel.getAlimentById(alimRepas.alimentModel);
                    listAlimentModelQuantite.add(alim.getNom()+" "+alimRepas.quantite + " " + ("Boisson".equalsIgnoreCase(alim.getTypeAliment()) ? "cl" : "g"));
                }

                alimentQuantiteListAdapter = new AlimentQuantiteListAdapter(this, R.layout.adapter_view_aliment_quantite_layout, listAlimentModelQuantite);
                listViewRepasAlimentPetitDej.setAdapter(alimentQuantiteListAdapter);
            }

            listRepasAlimentMidi = AlimentRepasModel.getAlimentRepasModelByRepas(jourModel.getRepasMidi().getId());
            if(listRepasAlimentMidi!=null){
                listAlimentModelQuantite = new ArrayList<>();
                AlimentModel alim = null;
                for (AlimentRepasModel alimRepas : listRepasAlimentMidi) {
                    alim = AlimentModel.getAlimentById(alimRepas.alimentModel);
                    listAlimentModelQuantite.add(alim.getNom()+" "+alimRepas.quantite + " " + ("Boisson".equalsIgnoreCase(alim.getTypeAliment()) ? "cl" : "g"));
                }

                alimentQuantiteListAdapter = new AlimentQuantiteListAdapter(this, R.layout.adapter_view_aliment_quantite_layout, listAlimentModelQuantite);
                listViewRepasAlimentMidij.setAdapter(alimentQuantiteListAdapter);
            }

            listRepasAlimentDiner = AlimentRepasModel.getAlimentRepasModelByRepas(jourModel.getRepasDiner().getId());
            if(listRepasAlimentDiner!=null){
                listAlimentModelQuantite = new ArrayList<>();
                AlimentModel alim = null;
                for (AlimentRepasModel alimRepas : listRepasAlimentDiner) {
                    alim = AlimentModel.getAlimentById(alimRepas.alimentModel);
                    listAlimentModelQuantite.add(alim.getNom()+" "+alimRepas.quantite + " " + ("Boisson".equalsIgnoreCase(alim.getTypeAliment()) ? "cl" : "g"));
                }

                alimentQuantiteListAdapter = new AlimentQuantiteListAdapter(this, R.layout.adapter_view_aliment_quantite_layout, listAlimentModelQuantite);
                listViewRepasAlimentDiner.setAdapter(alimentQuantiteListAdapter);
            }
        }
    }
}
