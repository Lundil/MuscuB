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
import com.example.muscu.model.UtilisateurModel;

import java.util.ArrayList;
import java.util.List;

public class JourActivity extends Activity {

    private TextView textViewTitreJour, textViewPetitDej, textViewMidi, textViewDiner,  textViewEncas1, textViewEncas2, textViewEncas3;
    private ListView listViewRepasAlimentPetitDej, listViewRepasAlimentMidij, listViewRepasAlimentDiner, listRepasAlimentEncas1, listRepasAlimentEncas2, listRepasAlimentEncas3;
    private List<String> listAlimentModelQuantite;
    private List<AlimentRepasModel> listRepasAlimentPdj,listRepasAlimentMidi,listRepasAlimentDiner, repasAlimentEncas1, repasAlimentEncas2, repasAlimentEncas3;
    private AlimentQuantiteListAdapter alimentQuantiteListAdapter;
    private UtilisateurModel user = UtilisateurModel.getAllUtilisateurs();

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
        listRepasAlimentEncas1 = findViewById(R.id.listRepasAlimentEncas1);
        listRepasAlimentEncas2 = findViewById(R.id.listRepasAlimentEncas2);
        listRepasAlimentEncas3 = findViewById(R.id.listRepasAlimentEncas3);

        Long idJourModelSelected = (Long) getIntent().getLongExtra("idJourModelSelected", 0L);
        if(idJourModelSelected != 0L){
            JourModel jourModel = JourModel.getJourById(idJourModelSelected);

            textViewTitreJour.setText(jourModel.getNom());
            textViewPetitDej.setText("Matin : "+jourModel.getRepasMatin().nom);
            textViewMidi.setText("Midi : "+jourModel.getRepasMidi().nom);
            textViewDiner.setText("Soir : "+jourModel.getRepasDiner().nom);

            if(Integer.valueOf(user.getNbRepas()) > 3){
                if(jourModel.getRepasEncas1()!=null){
                    textViewEncas1.setText("Encas 10h : "+jourModel.getRepasEncas1().nom);
                    listRepasAlimentEncas1.setVisibility(View.VISIBLE);
                }else{
                    textViewEncas1.setVisibility(View.GONE);
                    listRepasAlimentEncas1.setVisibility(View.GONE);
                }
                if(jourModel.getRepasEncas2()!=null){
                    textViewEncas2.setText("Encas 16h : "+jourModel.getRepasEncas2().nom);
                    listRepasAlimentEncas2.setVisibility(View.VISIBLE);
                }else{
                    textViewEncas2.setVisibility(View.GONE);
                    listRepasAlimentEncas2.setVisibility(View.GONE);
                }
                if(jourModel.getRepasEncas3()!=null){
                    textViewEncas3.setText("Encas 22h : "+jourModel.getRepasEncas3().nom);
                    listRepasAlimentEncas3.setVisibility(View.VISIBLE);
                }else{
                    textViewEncas3.setVisibility(View.GONE);
                    listRepasAlimentEncas3.setVisibility(View.GONE);
                }
            }else{
                textViewEncas1.setVisibility(View.GONE);
                textViewEncas2.setVisibility(View.GONE);
                textViewEncas3.setVisibility(View.GONE);
                listRepasAlimentEncas1.setVisibility(View.GONE);
                listRepasAlimentEncas2.setVisibility(View.GONE);
                listRepasAlimentEncas3.setVisibility(View.GONE);
            }
            listRepasAlimentPdj = AlimentRepasModel.getAlimentRepasModelByRepas(jourModel.getRepasMatin().getId());
            if(listRepasAlimentPdj!=null){
                listAlimentModelQuantite = new ArrayList<>();
                AlimentModel alim = null;
                for (AlimentRepasModel alimRepas : listRepasAlimentPdj) {
                    alim = AlimentModel.getAlimentById(alimRepas.alimentModel);
                    listAlimentModelQuantite.add(alim.getNom()+" "+alimRepas.quantite + " " + ("Boisson".equalsIgnoreCase(alim.getTypeAliment()) ? "ml" : "g"));
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
                    listAlimentModelQuantite.add(alim.getNom()+" "+alimRepas.quantite + " " + ("Boisson".equalsIgnoreCase(alim.getTypeAliment()) ? "ml" : "g"));
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
                    listAlimentModelQuantite.add(alim.getNom()+" "+alimRepas.quantite + " " + ("Boisson".equalsIgnoreCase(alim.getTypeAliment()) ? "ml" : "g"));
                }

                alimentQuantiteListAdapter = new AlimentQuantiteListAdapter(this, R.layout.adapter_view_aliment_quantite_layout, listAlimentModelQuantite);
                listViewRepasAlimentDiner.setAdapter(alimentQuantiteListAdapter);
            }

            repasAlimentEncas1 = AlimentRepasModel.getAlimentRepasModelByRepas(jourModel.getRepasEncas1().getId());
            if(repasAlimentEncas1!=null){
                listAlimentModelQuantite = new ArrayList<>();
                AlimentModel alim = null;
                for (AlimentRepasModel alimRepas : repasAlimentEncas1) {
                    alim = AlimentModel.getAlimentById(alimRepas.alimentModel);
                    listAlimentModelQuantite.add(alim.getNom()+" "+alimRepas.quantite + " " + ("Boisson".equalsIgnoreCase(alim.getTypeAliment()) ? "ml" : "g"));
                }

                alimentQuantiteListAdapter = new AlimentQuantiteListAdapter(this, R.layout.adapter_view_aliment_quantite_layout, listAlimentModelQuantite);
                listRepasAlimentEncas1.setAdapter(alimentQuantiteListAdapter);
            }
            repasAlimentEncas2 = AlimentRepasModel.getAlimentRepasModelByRepas(jourModel.getRepasEncas1().getId());
            if(repasAlimentEncas2!=null){
                listAlimentModelQuantite = new ArrayList<>();
                AlimentModel alim = null;
                for (AlimentRepasModel alimRepas : repasAlimentEncas2) {
                    alim = AlimentModel.getAlimentById(alimRepas.alimentModel);
                    listAlimentModelQuantite.add(alim.getNom()+" "+alimRepas.quantite + " " + ("Boisson".equalsIgnoreCase(alim.getTypeAliment()) ? "ml" : "g"));
                }

                alimentQuantiteListAdapter = new AlimentQuantiteListAdapter(this, R.layout.adapter_view_aliment_quantite_layout, listAlimentModelQuantite);
                listRepasAlimentEncas2.setAdapter(alimentQuantiteListAdapter);
            }
            repasAlimentEncas3 = AlimentRepasModel.getAlimentRepasModelByRepas(jourModel.getRepasEncas1().getId());
            if(repasAlimentEncas3!=null){
                listAlimentModelQuantite = new ArrayList<>();
                AlimentModel alim = null;
                for (AlimentRepasModel alimRepas : repasAlimentEncas3) {
                    alim = AlimentModel.getAlimentById(alimRepas.alimentModel);
                    listAlimentModelQuantite.add(alim.getNom()+" "+alimRepas.quantite + " " + ("Boisson".equalsIgnoreCase(alim.getTypeAliment()) ? "ml" : "g"));
                }

                alimentQuantiteListAdapter = new AlimentQuantiteListAdapter(this, R.layout.adapter_view_aliment_quantite_layout, listAlimentModelQuantite);
                listRepasAlimentEncas3.setAdapter(alimentQuantiteListAdapter);
            }
        }
    }
}
