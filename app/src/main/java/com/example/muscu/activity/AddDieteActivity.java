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
import com.example.muscu.adapter.AlimentListAdapter;
import com.example.muscu.adapter.RepasListAdapter;
import com.example.muscu.model.AlimentModel;
import com.example.muscu.model.JourModel;
import com.example.muscu.model.RepasModel;
import com.example.muscu.model.UtilisateurModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AddDieteActivity extends Activity {

    private ListView listViewPdjDispos, listViewDejDispos, listViewDinerDispos, listViewEncasDispos, listViewPdjSelected, listViewDejSelected, listViewDinerSelected, listViewEncasSelected;
    private RepasModel repasSelected;
    private List<RepasModel> listPdjDispos, listDejDispos, listDinerDispos, listPdjSelected = new ArrayList<>(), listDejSelected = new ArrayList<>(), listDinerSelected = new ArrayList<>();
    private List<AlimentModel> listEncasDispos, listEncasSelected = new ArrayList<>();
    private AlimentListAdapter alimentListAdapterDispo,alimentListAdapterSelected;
    private RepasListAdapter repasListAdapterPdjDispo,repasListAdapterMidiDispo,repasListAdapterDinerDispo,repasListAdapterPdjSelected,repasListAdapterMidiSelected,repasListAdapterDinerSelected;
    private AlimentModel alimentSelected;
    private Button createDiete;
    private String erreur = "";
    private UtilisateurModel user;

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

        listEncasDispos = AlimentModel.getAlimentsByIsEncas(true);
        listPdjDispos = RepasModel.getRepasByIsMatin(true);
        listDejDispos = RepasModel.getRepasByIsMidi(true);
        listDinerDispos =RepasModel.getRepasByIsDiner(true);
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
                alimentSelected = alimentListAdapterDispo.getItem(position);
                listEncasSelected.add(alimentSelected);
                listEncasDispos.remove(alimentSelected);
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
                alimentSelected = alimentListAdapterSelected.getItem(position);
                listEncasSelected.remove(alimentSelected);
                listEncasDispos.add(alimentSelected);
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
        alimentListAdapterDispo = new AlimentListAdapter(this, R.layout.adapter_view_aliment_layout, listEncasDispos);
        listViewEncasDispos.setAdapter(alimentListAdapterDispo);
        //Selected
        repasListAdapterPdjSelected = new RepasListAdapter(this, R.layout.adapter_view_aliment_layout, listPdjSelected);
        listViewPdjSelected.setAdapter(repasListAdapterPdjSelected);
        repasListAdapterMidiSelected = new RepasListAdapter(this, R.layout.adapter_view_aliment_layout, listDejSelected);
        listViewDejSelected.setAdapter(repasListAdapterMidiSelected);
        repasListAdapterDinerSelected = new RepasListAdapter(this, R.layout.adapter_view_aliment_layout, listDinerSelected);
        listViewDinerSelected.setAdapter(repasListAdapterDinerSelected);
        alimentListAdapterSelected = new AlimentListAdapter(this, R.layout.adapter_view_aliment_layout, listEncasSelected);
        listViewEncasSelected.setAdapter(alimentListAdapterSelected);
    }

    private boolean isGUIFilled(){
        return !listEncasSelected.isEmpty() && !listPdjSelected.isEmpty() && !listDejSelected.isEmpty() && !listDinerSelected.isEmpty() ;
    }

    private void creerDiete(){
        List<String> nomJourSemaine = Arrays.asList("Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi","Dimanche");
        List<RepasModel> pDJs = listPdjSelected, dejeuners = listDejSelected, diners = listDinerSelected;
        Random ran = new Random();
        boolean collations = Integer.parseInt(user.nbRepas) > 3;
        int ordre = 0;
        List<JourModel> list = JourModel.getAllJours();
        for (JourModel jour : list) {
            jour.delete();
        }
        for (String day : nomJourSemaine) {
            ordre++;
            //TODO
            JourModel jour = new JourModel();
            if(pDJs.isEmpty()){
                pDJs = listPdjSelected;
            }
            if(dejeuners.isEmpty()){
                dejeuners = listDejSelected;
            }
            if(diners.isEmpty()){
                diners = listDinerSelected;
            }
            jour.ordre=ordre;
            jour.nom=day;
            jour.repasMatin=pDJs.get(0);
            //pDJs.remove(jour.repasMatin);
            jour.repasMidi=dejeuners.get(0);
            //dejeuners.remove(jour.repasMidi);
            jour.repasDiner=diners.get(0);
            //diners.remove(jour.repasDiner);
            /*if(collations){
                for(int i =0; i < Integer.parseInt(user.nbRepas)-3 ;i++){
                    jour.collations=new ArrayList<>();
                }
            }*/
            jour.save();
        }
        erreur+="Ici figureront les conseils pour bien préparer votre diète";
        if(!erreur.isEmpty()){
            Intent intent = new Intent();
            intent.putExtra("erreur", erreur);
            setResult(Activity.RESULT_OK, intent);
            list = JourModel.getAllJours();
            for (JourModel jour : list) {
                jour.delete();
            }
        }
        finish();

    }

}
