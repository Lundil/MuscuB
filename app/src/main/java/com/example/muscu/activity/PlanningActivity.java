package com.example.muscu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import com.example.muscu.R;
import com.example.muscu.adapter.JourListAdapter;
import com.example.muscu.model.AlimentModel;
import com.example.muscu.model.AlimentRepasModel;
import com.example.muscu.model.JourModel;
import com.example.muscu.model.RepasModel;
import com.example.muscu.model.UtilisateurModel;

import java.util.Arrays;
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
            openCreateDiete();
        }
        /*else{
            List<JourModel> list = JourModel.getAllJours();
            for (JourModel jour : list) {
                jour.delete();
            }
        }*/

        //listJour
        listView = findViewById(R.id.listJour);
        jourModelList = JourModel.getAllJours();
        if (jourModelList != null) {
            jourListAdapter = new JourListAdapter(this, R.layout.adapter_view_jour_layout, jourModelList);
            listView.setAdapter(jourListAdapter);
        }

    }

    public void openCreateDiete() {
        Intent intent = new Intent(this, AddDieteActivity.class);
        startActivityForResult(intent,1);
    }

    private void createDiete(){
        Toast.makeText(getApplicationContext(), "Création d'une diète", Toast.LENGTH_SHORT).show();
        //Prendre 1 PDJ, 1 MIDI, 1 DINER, 1 collation que lipide, 1 collation que lipide, 1 collation que glucide
        List<AlimentModel> listAlimentsEncas = AlimentModel.getAlimentsByIsEncas(true);
        RepasModel petitDejeuner = RepasModel.getRepasByIsMatin(true).get(0);
        List<RepasModel> listRepasMidi = RepasModel.getRepasByIsMidi(true);
        List<RepasModel> listRepasDiner =RepasModel.getRepasByIsDiner(true);
        UtilisateurModel user = UtilisateurModel.getAllUtilisateurs();
        //TODO user.getUserDailyNeeds();
        //Check
        List<String> nomJourSemaine = Arrays.asList("Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi","Dimanche");

        Double dailyNeedsKcal = user.getUserDailyNeeds();
        Double dailyNeedsProteine = user.poids*2.0;
        Double dailyNeedsLipide = user.poids*1.25;
        Double dailyNeedsGlucide = (dailyNeedsKcal-dailyNeedsProteine-dailyNeedsLipide)/4;

        //besoins Quotidiens calculés
        //
        List<AlimentRepasModel> listAlimentRepasModel = AlimentRepasModel.getAlimentRepasModelByRepas(petitDejeuner.getId());
        AlimentModel ingredient = null;
        for (AlimentRepasModel alimRepas: listAlimentRepasModel) {
            ingredient = AlimentModel.getAlimentById(alimRepas.alimentModel);
            dailyNeedsProteine = dailyNeedsProteine-ingredient.getProteine();
            dailyNeedsLipide = dailyNeedsLipide-ingredient.getLipide();
            dailyNeedsGlucide = dailyNeedsGlucide-ingredient.getGlucide();
        }

        dailyNeedsLipide=dailyNeedsLipide/Double.parseDouble(user.getNbRepas());
        dailyNeedsProteine=dailyNeedsProteine/Double.parseDouble(user.getNbRepas());
        dailyNeedsGlucide=dailyNeedsGlucide/Double.parseDouble(user.getNbRepas());
        dailyNeedsGlucide=dailyNeedsGlucide;

        //TODO Création midi

        for (RepasModel midi : listRepasMidi) {
            /*if("Féculent".equalsIgnoreCase(alimentSelected.getTypeAliment())){
                spinnerTypeAliment.setSelection(0);
            }else if("Laitage".equalsIgnoreCase(alimentSelected.getTypeAliment())){
                spinnerTypeAliment.setSelection(1);
            }else if("Légume".equalsIgnoreCase(alimentSelected.getTypeAliment())){
                spinnerTypeAliment.setSelection(2);
            }else if("Poisson".equalsIgnoreCase(alimentSelected.getTypeAliment())){
                spinnerTypeAliment.setSelection(3);
            }else if("Boisson".equalsIgnoreCase(alimentSelected.getTypeAliment())){
                spinnerTypeAliment.setSelection(4);
            }else if("Sauce".equalsIgnoreCase(alimentSelected.getTypeAliment())){
                spinnerTypeAliment.setSelection(5);
            }else if("Viande".equalsIgnoreCase(alimentSelected.getTypeAliment())){
                spinnerTypeAliment.setSelection(6);
            }else if("Fruit".equalsIgnoreCase(alimentSelected.getTypeAliment())){
                spinnerTypeAliment.setSelection(7);
            }*/
        }
        //TODO Création diner
        for (RepasModel diner : listRepasMidi) {

        }

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
