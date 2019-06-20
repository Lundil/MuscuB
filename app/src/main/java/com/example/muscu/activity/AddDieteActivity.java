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
    private List<RepasModel> listPdjDispos, listDejDispos, listDinerDispos, listPdjSelected = new ArrayList<>(), listDejSelected = new ArrayList<>(), listDinerSelected = new ArrayList<>();
    private List<AlimentModel> listEncasDispos, listEncasSelected = new ArrayList<>();
    private AlimentListAdapter alimentListAdapterDispo,alimentListAdapterSelected;
    private RepasListAdapter repasListAdapterPdjDispo,repasListAdapterMidiDispo,repasListAdapterDinerDispo,repasListAdapterPdjSelected,repasListAdapterMidiSelected,repasListAdapterDinerSelected;
    private AlimentModel alimentSelected;
    private Button createDiete;
    private String erreur = "";
    private UtilisateurModel user;
    private Double dailyNeedsKcal = 0.0, dailyNeedsProteine = 0.0, dailyNeedsLipide = 0.0, dailyNeedsGlucide = 0.0, repasNeedsKcal = 0.0, repasNeedsProteine = 0.0,
            repasDailyNeedsLipide = 0.0, repasNeedsGlucide = 0.0, dailyNeedsCompletedProteine = 0.0,dailyNeedsCompletedLipide = 0.0,dailyNeedsCompletedGlucide = 0.0,
            repasNeedsCompletedProteine = 0.0,repasNeedsCompletedLipide = 0.0,repasNeedsCompletedGlucide = 0.0;
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
        //Supprime l'ancienne diète
        List<JourModel> list = JourModel.getAllJours();
        for (JourModel jour : list) {
            jour.delete();
        }
        //Besoins caloriques, en proteine, glucide et lipide journalier
        dailyNeedsKcal = user.getUserDailyNeeds();//kcal
        dailyNeedsProteine = user.poids*2.0;//grammes
        dailyNeedsLipide = user.poids*1.25;//grammes
        dailyNeedsGlucide = (dailyNeedsKcal-dailyNeedsProteine-dailyNeedsLipide);//grammes

        repasNeedsKcal = dailyNeedsKcal / Double.parseDouble(user.getNbRepas());//kcal
        repasNeedsProteine = dailyNeedsProteine / Double.parseDouble(user.getNbRepas());//grammes
        repasDailyNeedsLipide = dailyNeedsLipide / Double.parseDouble(user.getNbRepas());//grammes
        repasNeedsGlucide = dailyNeedsGlucide / Double.parseDouble(user.getNbRepas());//grammes

        for (String day : nomJourSemaine) {
            ordre++;
            //DIstribution diversifiée des repas
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

            List<AlimentRepasModel> listAlimentRepasModel = AlimentRepasModel.getAllAlimentRepasModel();
            AlimentModel alim = null;
            for (AlimentRepasModel alimDuRepas : listAlimentRepasModel) {
                alim = AlimentModel.getAlimentById(alimDuRepas.alimentModel);
                if("Boisson".equalsIgnoreCase(alim.getTypeAliment())){
                    alimDuRepas.quantite=17.0;
                }else if("Céréales".equalsIgnoreCase(alim.getTypeAliment())){
                    alimDuRepas.quantite=100.0;
                }else if("Viande".equalsIgnoreCase(alim.getTypeAliment())){
                    alimDuRepas.quantite=125.0;
                }else if("Poisson".equalsIgnoreCase(alim.getTypeAliment())){
                    alimDuRepas.quantite=125.0;
                }else if("Féculent".equalsIgnoreCase(alim.getTypeAliment())){
                    alimDuRepas.quantite=70.0;
                }else if("Légume".equalsIgnoreCase(alim.getTypeAliment())){
                    alimDuRepas.quantite=250.0;
                }else if("Sauce".equalsIgnoreCase(alim.getTypeAliment())){
                    alimDuRepas.quantite=10.0;
                }else if("Produit laitier".equalsIgnoreCase(alim.getTypeAliment())){
                    alimDuRepas.quantite=10.0;
                }else if("Poudre".equalsIgnoreCase(alim.getTypeAliment())){
                    alimDuRepas.quantite=20.0;
                }
                //proteines obtenues
                dailyNeedsCompletedProteine += alim.getProteine();

                //lipides obtenues
                dailyNeedsCompletedLipide += alim.getLipide();

                //glucides obtenues
                dailyNeedsCompletedGlucide += alim.getGlucide();

                //SAVE
                alimDuRepas.save();
            }

            //diners.remove(jour.repasDiner);
            /*if(collations){
                for(int i =0; i < Integer.parseInt(user.nbRepas)-3 ;i++){
                    jour.collations=new ArrayList<>();
                }
            }*/
            //TODO set quantite
            //Ici on doit avoir les taux de protein, glucide et lipide récupérés
            //Si il manque des proteines, glucide ou lipide, on va en chercher dans différentes sources
            baguetteMagiqueMacros();
            //SAVE
            jour.save();
            //Besoins
            mapBesoinAtteindProteine.put(jour,dailyNeedsCompletedProteine);
            mapBesoinAtteindLipide.put(jour,dailyNeedsCompletedLipide);
            mapBesoinAtteindGlucide.put(jour,dailyNeedsCompletedGlucide);
        }
        //erreur+="Ici figureront les conseils pour bien préparer votre diète";
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

    //Set toutes les bonnes macros
    private void baguetteMagiqueMacros(){
        List<JourModel> listJourModel = JourModel.getAllJours();
        Double proteine, lipide, glucide;
        for (JourModel  jour : listJourModel) {
            //On travaille sur chaque jour
            mapBesoinAtteindProteine.get(jour);
            mapBesoinAtteindLipide.get(jour);
            mapBesoinAtteindGlucide.get(jour);
            if(dailyNeedsCompletedProteine < dailyNeedsProteine){
                //Besoin en proteine

            }
            if(dailyNeedsCompletedLipide < dailyNeedsLipide){
                //Besoin en lipide

            }
            if(dailyNeedsCompletedGlucide < dailyNeedsGlucide){
                //Besoin en glucide

            }
        }
    }

}
