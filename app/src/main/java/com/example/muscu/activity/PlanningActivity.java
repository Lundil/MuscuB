package com.example.muscu.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import com.example.muscu.R;
import com.example.muscu.adapter.JourListAdapter;
import com.example.muscu.model.JourModel;

import java.util.List;

public class PlanningActivity extends Activity {

    private List<JourModel> jourModelList;
    private ListView listView;
    private JourListAdapter jourListAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_planning);
        setTitle("Planning");

        if(JourModel.getAllJours().isEmpty()){
            createDiete();
        }

        //listJour
        listView = findViewById(R.id.listJour);
        jourModelList = JourModel.getAllJours();
        if (jourModelList != null) {
            jourListAdapter = new JourListAdapter(this, R.layout.adapter_view_aliment_layout, jourModelList);
            listView.setAdapter(jourListAdapter);
        }

    }

    private void createDiete(){
        Toast.makeText(getApplicationContext(), "Création d'une diète", Toast.LENGTH_SHORT).show();
        //Prendre 1 PDJ, 1 MIDI, 1 DINER, 1 collation que lipide, 1 collation que lipide, 1 collation que glucide
        /*List<AlimentModel> listAlimentsEncas = AlimentModel.getAlimentsByIsEncas(true);
        RepasModel petitDejeuner = RepasModel.getRepasByIsMatin(true).get(0);
        List<RepasModel> listRepasMidi = RepasModel.getRepasByIsMidi(true);
        List<RepasModel> listRepasDiner =RepasModel.getRepasByIsDiner(true);
        UtilisateurModel user = UtilisateurModel.getAllUtilisateurs().get(0);*/
        //TODO user.getUserDailyNeeds();
        //Check
        /*List<String> nomJourSemaine = Arrays.asList("Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi","Dimanche");

        Double dailyNeedsKcal = user.getUserDailyNeeds();
        Double dailyNeedsProteine = user.poids*2.0;
        Double dailyNeedsLipide = user.poids*1.25;
        Double dailyNeedsGlucide = (dailyNeedsKcal-dailyNeedsProteine-dailyNeedsLipide)/4;

        //besoins Quotidiens calculés
        for (AlimentModel ingredient: petitDejeuner.getAlimentModels()) {
            ingredient.getProteine();
            ingredient.getLipide();
            ingredient.getGlucide();
        }

        dailyNeedsProteine=dailyNeedsProteine/Double.parseDouble(user.getNbRepas());
        dailyNeedsLipide=dailyNeedsLipide/Double.parseDouble(user.getNbRepas());
        dailyNeedsProteine=dailyNeedsProteine/Double.parseDouble(user.getNbRepas());
        dailyNeedsGlucide=dailyNeedsGlucide/Double.parseDouble(user.getNbRepas());
        dailyNeedsGlucide=dailyNeedsGlucide;*/
        /*for (String nomJour: nomJourSemaine) {
        //On a le nom, on prend le petit déjeuner
        JourModel jourModel = new JourModel();
        jourModel.setRepasMatin(pDJ);
        jourModel.save();
        //

        //Collations
        }*/

    }


}
