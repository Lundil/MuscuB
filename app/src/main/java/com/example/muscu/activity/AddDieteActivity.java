package com.example.muscu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.muscu.R;
import com.example.muscu.adapter.RepasListAdapter;
import com.example.muscu.model.AlimentModel;
import com.example.muscu.model.AlimentRepasModel;
import com.example.muscu.model.JourModel;
import com.example.muscu.model.RepasModel;
import com.example.muscu.model.UtilisateurModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class AddDieteActivity extends Activity {

    private ListView listViewPdjDispos, listViewDejDispos, listViewDinerDispos, listViewEncasDispos, listViewPdjSelected, listViewDejSelected, listViewDinerSelected, listViewEncasSelected;
    private RepasModel repasSelected;
    private List<RepasModel> listPdjDispos, listDejDispos, listDinerDispos, listEncasDispos,
            listPdjSelected = new ArrayList<>(), listDejSelected = new ArrayList<>(), listDinerSelected = new ArrayList<>(), listEncasSelected = new ArrayList<>();
    private RepasListAdapter repasListAdapterPdjDispo,repasListAdapterMidiDispo,repasListAdapterDinerDispo,repasListAdapterEncasDispo, repasListAdapterPdjSelected,repasListAdapterMidiSelected,repasListAdapterDinerSelected,repasListAdapterEncasSelected;
    private Button createDiete;
    private String erreur = "";
    private UtilisateurModel user;
    private Double dailyNeedsKcal = 0.0, dailyNeedsProteine = 0.0, dailyNeedsLipide = 0.0, dailyNeedsGlucide = 0.0, repasNeedsKcal = 0.0, repasNeedsProteine = 0.0,
            repasDailyNeedsLipide = 0.0, repasNeedsGlucide = 0.0, dailyNeedsCompletedProteine = 0.0,dailyNeedsCompletedLipide = 0.0,dailyNeedsCompletedGlucide = 0.0;
    private HashMap<JourModel,Double> mapBesoinAtteindProteine = new HashMap<>(), mapBesoinAtteindGlucide = new HashMap<>(), mapBesoinAtteindLipide = new HashMap<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_diete_layout);
        setTitle("Diete");

        listViewPdjDispos=findViewById(R.id.listPdjDisponibles);
        listViewDejDispos=findViewById(R.id.listMidiDisponibles);
        listViewDinerDispos=findViewById(R.id.listDinerDisponibles);
        listViewEncasDispos=findViewById(R.id.listEncasDisponibles);
        listViewPdjSelected=findViewById(R.id.listPdjSelected);
        listViewDejSelected=findViewById(R.id.listMidiSelected);
        listViewDinerSelected=findViewById(R.id.listDinerSelected);
        listViewEncasSelected=findViewById(R.id.listEncasSelected);
        createDiete = findViewById(R.id.createDiete);

        listPdjDispos = RepasModel.getRepasByIsMatin(true);
        listDejDispos = RepasModel.getRepasByIsMidi(true);
        listDinerDispos =RepasModel.getRepasByIsDiner(true);
        listEncasDispos = RepasModel.getRepasByIsEncas(true);
        user = UtilisateurModel.getAllUtilisateurs();

        refreshLists();

        //Dispos

        listViewPdjDispos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);
                repasSelected = repasListAdapterPdjDispo.getItem(position);
                repasListAdapterPdjSelected.add(repasSelected);
                repasListAdapterPdjDispo.remove(repasSelected);
                refreshLists();
            }
        });
        listViewDejDispos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);
                repasSelected = repasListAdapterMidiDispo.getItem(position);
                repasListAdapterMidiSelected.add(repasSelected);
                repasListAdapterMidiDispo.remove(repasSelected);
                refreshLists();
            }
        });
        listViewDinerDispos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);
                repasSelected = repasListAdapterDinerDispo.getItem(position);
                repasListAdapterDinerSelected.add(repasSelected);
                repasListAdapterDinerDispo.remove(repasSelected);
                refreshLists();
            }
        });
        listViewEncasDispos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);
                repasSelected = repasListAdapterEncasDispo.getItem(position);
                listEncasSelected.add(repasSelected);
                listEncasDispos.remove(repasSelected);
                refreshLists();
            }
        });

        //Selected
        listViewPdjSelected.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);
                repasSelected = repasListAdapterPdjSelected.getItem(position);
                listPdjSelected.remove(repasSelected);
                listPdjDispos.add(repasSelected);
                refreshLists();
            }
        });
        listViewDejSelected.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);
                repasSelected = repasListAdapterMidiSelected.getItem(position);
                listDejSelected.remove(repasSelected);
                listDejDispos.add(repasSelected);
                refreshLists();
            }
        });
        listViewDinerSelected.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);
                repasSelected = repasListAdapterDinerSelected.getItem(position);
                listDinerSelected.remove(repasSelected);
                listDinerDispos.add(repasSelected);
                refreshLists();
            }
        });
        listViewEncasSelected.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);
                repasSelected = repasListAdapterEncasSelected.getItem(position);
                listEncasSelected.remove(repasSelected);
                listEncasDispos.add(repasSelected);
                refreshLists();
            }
        });

        createDiete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(isGUIFilled()){
                    creerDiete();
                    Toast.makeText(getApplicationContext(), "Création de votre diète", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Renseignez tout les champs avant de préparer votre diète", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void refreshLists(){
        //Dispos
        repasListAdapterPdjDispo = new RepasListAdapter(this, R.layout.adapter_view_aliment_layout, listPdjDispos);
        listViewPdjDispos.setAdapter(repasListAdapterPdjDispo);
        repasListAdapterMidiDispo = new RepasListAdapter(this, R.layout.adapter_view_aliment_layout, listDejDispos);
        listViewDejDispos.setAdapter(repasListAdapterMidiDispo);
        repasListAdapterDinerDispo = new RepasListAdapter(this, R.layout.adapter_view_aliment_layout, listDinerDispos);
        listViewDinerDispos.setAdapter(repasListAdapterDinerDispo);
        repasListAdapterEncasDispo = new RepasListAdapter(this, R.layout.adapter_view_aliment_layout, listEncasDispos);
        listViewEncasDispos.setAdapter(repasListAdapterEncasDispo);
        //Selected
        repasListAdapterPdjSelected = new RepasListAdapter(this, R.layout.adapter_view_aliment_layout, listPdjSelected);
        listViewPdjSelected.setAdapter(repasListAdapterPdjSelected);
        repasListAdapterMidiSelected = new RepasListAdapter(this, R.layout.adapter_view_aliment_layout, listDejSelected);
        listViewDejSelected.setAdapter(repasListAdapterMidiSelected);
        repasListAdapterDinerSelected = new RepasListAdapter(this, R.layout.adapter_view_aliment_layout, listDinerSelected);
        listViewDinerSelected.setAdapter(repasListAdapterDinerSelected);
        repasListAdapterEncasSelected = new RepasListAdapter(this, R.layout.adapter_view_aliment_layout, listEncasSelected);
        listViewEncasSelected.setAdapter(repasListAdapterEncasSelected);
    }

    private boolean isGUIFilled(){
        return !listEncasSelected.isEmpty() && !listPdjSelected.isEmpty() && !listDejSelected.isEmpty() && !listDinerSelected.isEmpty() ;
    }

    private void creerDiete(){
        List<String> nomJourSemaine = Arrays.asList("Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi","Dimanche");
        List<RepasModel> pDJs = new ArrayList<>(), dejeuners = new ArrayList<>(), diners = new ArrayList<>(), encas = new ArrayList<>();
        for (RepasModel clone : listPdjSelected) {
            pDJs.add(clone);
        }
        for (RepasModel clone : listDejSelected) {
            dejeuners.add(clone);
        }
        for (RepasModel clone : listDinerSelected) {
            diners.add(clone);
        }
        for (RepasModel clone : listEncasSelected) {
            encas.add(clone);
        }
        Random rand = new Random();
        boolean collations = Integer.parseInt(user.nbRepas) > 3;
        int ordre = 0;
        //Supprime l'ancienne diète
        List<JourModel> list = JourModel.getAllJours();
        for (JourModel jour : list) {
            jour.delete();
        }
        for (String day : nomJourSemaine) {
            ordre++;
            //Distribution diversifiée des repas
            JourModel jour = new JourModel();
            jour.ordre=ordre;
            jour.nom=day;
            //Jours
            if(pDJs.isEmpty()){
                for (RepasModel clone : listPdjSelected) {
                    pDJs.add(clone);
                }
            }
            if(dejeuners.isEmpty()){
                for (RepasModel clone : listDejSelected) {
                    dejeuners.add(clone);
                }
            }
            if(diners.isEmpty()){
                for (RepasModel clone : listDinerSelected) {
                    diners.add(clone);
                }
            }
            if(encas.isEmpty()){
                for (RepasModel clone : listEncasSelected) {
                    encas.add(clone);
                }
            }
            jour.repasMatin=pDJs.get(rand.nextInt(pDJs.size()));
            jour.repasMidi=dejeuners.get(rand.nextInt(dejeuners.size()));
            jour.repasDiner=diners.get(rand.nextInt(diners.size()));
            jour.repasEncas1=encas.get(rand.nextInt(encas.size()));
            encas.remove(jour.repasEncas1);
            jour.repasEncas2=encas.get(rand.nextInt(encas.size()));
            encas.remove(jour.repasEncas2);
            jour.repasEncas3=encas.get(rand.nextInt(encas.size()));
            encas.remove(jour.repasEncas3);
            jour.save();
            //Variété des repas
            pDJs.remove(jour.repasMatin);
            dejeuners.remove(jour.repasMidi);
            diners.remove(jour.repasDiner);
        }
        finish();

    }
}
