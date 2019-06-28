package com.example.muscu.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.muscu.R;
import com.example.muscu.adapter.AlimentListAdapter;
import com.example.muscu.model.AlimentModel;
import com.example.muscu.model.AlimentRepasModel;
import com.example.muscu.model.RepasModel;
import com.example.muscu.model.UtilisateurModel;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddMealActivity extends Activity {

    private Button save, delete;
    private UtilisateurModel user;
    private EditText editNom;
    private EditText description;
    private TextView textViewNeeds;
    private CheckBox checkMatin;
    private CheckBox checkMidi;
    private CheckBox checkDiner;
    private CheckBox checkEncas;
    private ListView listView,listAlimentsSelected;
    private AlimentModel alimentSelected;
    private RepasModel repasSelected;
    private List<AlimentModel> alimentModelList, alimentModelSelected = new ArrayList<>();
    private AlimentListAdapter alimentListAdapter,alimentSelectedListAdapter;
    private Double repasProt = 0.0,repasLip = 0.0,repasGlu = 0.0;
    private HashMap<AlimentModel, Double> mapAlimentQuantite = new HashMap<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_meal);
        user = UtilisateurModel.getAllUtilisateurs();
        //FINDS
        textViewNeeds = findViewById(R.id.textViewNeeds);
        listView = findViewById(R.id.listAliments);
        listAlimentsSelected = findViewById(R.id.listAlimentsSelected);
        editNom = findViewById(R.id.editNom);
        description = findViewById(R.id.editDescription);
        checkMatin = findViewById(R.id.checkbox_matin);
        checkMidi = findViewById(R.id.checkbox_midi);
        checkDiner = findViewById(R.id.checkbox_diner);
        checkEncas = findViewById(R.id.checkbox_encas);
        save = findViewById(R.id.saveMeal);
        delete = findViewById(R.id.deleteMeal);

        setup();
    }

    private void refreshLists(){
        alimentListAdapter = new AlimentListAdapter(this, R.layout.adapter_view_aliment_layout, alimentModelList);
        listView.setAdapter(alimentListAdapter);
        alimentSelectedListAdapter = new AlimentListAdapter(this, R.layout.adapter_view_aliment_layout, alimentModelSelected);
        listAlimentsSelected.setAdapter(alimentSelectedListAdapter);
    }

    private boolean isGUIFilled() {
        return !editNom.getText().toString().isEmpty() &&
                (checkMatin.isChecked() || checkMidi.isChecked() || checkDiner.isChecked() || checkEncas.isChecked())
                && !alimentModelSelected.isEmpty();
    }

    private void openPopRepas(boolean exist){
        Intent intent = new Intent(this, PopRepasActivity.class);
        if(exist){
            intent.putExtra("quantiteExistante",mapAlimentQuantite.get(alimentSelected));
        }
        startActivityForResult(intent,1);
    }
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                //set quantite
                String quantite=data.getStringExtra("quantite");
                boolean isDelete=(boolean)data.getBooleanExtra("isDelete", false);

                if(StringUtils.isNotBlank(quantite)){
                    //Ajoute à la liste des aliments sélectionnés + ajouter à la map des aliments / quantités
                    if(mapAlimentQuantite.get(alimentSelected) != null){
                        mapAlimentQuantite.remove(alimentSelected);
                        alimentModelSelected.remove(alimentSelected);
                    }
                    mapAlimentQuantite.put(alimentSelected, Double.parseDouble(quantite));
                    alimentModelSelected.add(alimentSelected);
                    alimentModelList.remove(alimentSelected);

                }else if(isDelete){
                    //Supprimer de la liste des aliments sélectionnés + supprimer de la map des aliments / quantités
                    mapAlimentQuantite.remove(alimentSelected);
                    alimentModelList.add(alimentSelected);
                    alimentModelSelected.remove(alimentSelected);
                }else{
                    setup();
                }
            }else  if (resultCode == Activity.RESULT_CANCELED) {
                //rien
            }
            //FINDS
            setContentView(R.layout.activity_add_meal);
            textViewNeeds = findViewById(R.id.textViewNeeds);
            listView = findViewById(R.id.listAliments);
            listAlimentsSelected = findViewById(R.id.listAlimentsSelected);
            editNom = findViewById(R.id.editNom);
            description = findViewById(R.id.editDescription);
            checkMatin = findViewById(R.id.checkbox_matin);
            checkMidi = findViewById(R.id.checkbox_midi);
            checkDiner = findViewById(R.id.checkbox_diner);
            checkEncas = findViewById(R.id.checkbox_encas);
            save = findViewById(R.id.saveMeal);
            delete = findViewById(R.id.deleteMeal);

            save.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if(!isGUIFilled()){
                        Toast.makeText(getApplicationContext(), "Renseigner tous les champs", Toast.LENGTH_SHORT).show();
                    }else {
                        BigDecimal bgProt = BigDecimal.valueOf(user.getUserDailyNeedsProtein()),bgLip = BigDecimal.valueOf(user.getUserDailyNeedsLipide()),bgglu = BigDecimal.valueOf(user.getUserDailyNeedsGlucide());
                        BigDecimal nbRepasBG = BigDecimal.valueOf(Double.parseDouble(user.nbRepas));
                        bgProt = bgProt.divide(nbRepasBG, BigDecimal.ROUND_HALF_DOWN).setScale(2);
                        bgLip = bgLip.divide(nbRepasBG, BigDecimal.ROUND_HALF_DOWN).setScale(2);
                        bgglu = bgglu.divide(nbRepasBG, BigDecimal.ROUND_HALF_DOWN).setScale(2);

                        //Map aliments quantité
                        BigDecimal bgProtTmpAliment = BigDecimal.ZERO, bgLipTmpAliment = BigDecimal.ZERO, bgGluTmpAliment = BigDecimal.ZERO, bgProtAliment = BigDecimal.ZERO, bgLipAliment = BigDecimal.ZERO, bgGluAliment = BigDecimal.ZERO;
                        for (Map.Entry<AlimentModel, Double> alimentQuantite : mapAlimentQuantite.entrySet()) {
                            //Valeur en macro / 1 gramme
                            bgProtTmpAliment = BigDecimal.valueOf(alimentQuantite.getKey().getProteine()).divide(BigDecimal.valueOf(100.0)).setScale(2);
                            bgLipTmpAliment = BigDecimal.valueOf(alimentQuantite.getKey().getLipide()).divide(BigDecimal.valueOf(100.0)).setScale(2);
                            bgGluTmpAliment = BigDecimal.valueOf(alimentQuantite.getKey().getGlucide()).divide(BigDecimal.valueOf(100.0)).setScale(2);
                            //Valeur multipliée par la quantité
                            bgProtAliment = bgProtAliment.add(bgProtTmpAliment.multiply(BigDecimal.valueOf(alimentQuantite.getValue())).setScale(2));
                            bgLipAliment = bgLipTmpAliment.add(bgLipTmpAliment.multiply(BigDecimal.valueOf(alimentQuantite.getValue())).setScale(2));
                            bgGluAliment = bgGluTmpAliment.add(bgGluTmpAliment.multiply(BigDecimal.valueOf(alimentQuantite.getValue())).setScale(2));
                        }
                        if(bgProtAliment.doubleValue() < bgProt.doubleValue() || bgLipAliment.doubleValue() < bgLip.doubleValue() || bgGluAliment.doubleValue() < bgglu.doubleValue()){
                            Toast.makeText(getApplicationContext(), "Besoins quotidiens non remplis", Toast.LENGTH_SHORT).show();
                        }else{
                            //Calcul
                            Double totalProteine = 0.0,totalLipide = 0.0,totalGlucide = 0.0;
                            for (AlimentModel alim: alimentModelSelected) {
                                totalProteine+=alim.getProteine();
                                totalLipide+=alim.getProteine();
                                totalGlucide+=alim.getProteine();
                            }
                            RepasModel repasModel = null;
                            if(repasSelected != null){
                                repasModel=repasSelected;
                                repasModel.nom=editNom.getText().toString();
                                repasModel.description=description.getText().toString();
                                repasModel.isMatin=checkMatin.isChecked();
                                repasModel.isMidi=checkMidi.isChecked();
                                repasModel.isDiner=checkDiner.isChecked();
                                repasModel.isEncas=checkEncas.isChecked();
                                repasModel.proteineTotal=totalProteine;
                                repasModel.lipideTotal=totalLipide;
                                repasModel.glucideTotal=totalGlucide;
                            }else{
                                repasModel = new RepasModel(
                                        editNom.getText().toString(),
                                        description.getText().toString(),
                                        totalProteine,
                                        totalGlucide,
                                        totalLipide,
                                        checkMatin.isChecked(),
                                        checkMidi.isChecked(),
                                        checkDiner.isChecked(),
                                        checkEncas.isChecked());
                            }
                            repasModel.save();
                            //TODO
                            if(repasSelected != null){
                                AlimentRepasModel.deleteAlimentFromAlimentRepasModelByIdRepas(repasSelected.getId());
                            }
                            for (AlimentModel selected: alimentModelSelected) {
                                AlimentRepasModel alimentRepasModel = new AlimentRepasModel();
                                alimentRepasModel.alimentModel=selected.getId();
                                alimentRepasModel.repasModel=repasModel.getId();
                                alimentRepasModel.quantite=mapAlimentQuantite.get(selected);
                                alimentRepasModel.save();
                            }
                            finish();
                        }

                    }

                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    AlimentRepasModel.deleteAlimentFromAlimentRepasModelByIdRepas(repasSelected.getId());
                    RepasModel repasModel = repasSelected;
                    repasModel.delete();
                    finish();
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
                    view.setSelected(true);
                    alimentSelected = alimentListAdapter.getItem(position);
                    setContentView(R.layout.activity_add_meal_darker);
                    listView = findViewById(R.id.listAliments);
                    listAlimentsSelected = findViewById(R.id.listAlimentsSelected);
                    listView.setVisibility(View.GONE);
                    listAlimentsSelected.setVisibility(View.GONE);
                    openPopRepas(false);
                }
            });
            listAlimentsSelected.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
                    view.setSelected(true);
                    alimentSelected = alimentSelectedListAdapter.getItem(position);
                    setContentView(R.layout.activity_add_meal_darker);
                    listView = findViewById(R.id.listAliments);
                    listAlimentsSelected = findViewById(R.id.listAlimentsSelected);
                    listView.setVisibility(View.GONE);
                    listAlimentsSelected.setVisibility(View.GONE);
                    openPopRepas(true);
                }
            });
            refreshLists();
            updateBesoins();
        }
    }

    private void setup(){
        alimentModelList = AlimentModel.getAllAliments();
        if(alimentModelList!=null){
            alimentListAdapter = new AlimentListAdapter(this, R.layout.adapter_view_aliment_layout, alimentModelList);
            listView.setAdapter(alimentListAdapter);
        }
        alimentSelectedListAdapter = new AlimentListAdapter(this, R.layout.adapter_view_aliment_layout, alimentModelSelected);
        listAlimentsSelected.setAdapter(alimentSelectedListAdapter);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(!isGUIFilled()){
                    Toast.makeText(getApplicationContext(), "Renseigner tous les champs", Toast.LENGTH_SHORT).show();
                }else{
                    //Calcul
                    Double totalProteine = 0.0,totalLipide = 0.0,totalGlucide = 0.0;
                    for (AlimentModel alim: alimentModelSelected) {
                        totalProteine+=alim.getProteine();
                        totalLipide+=alim.getProteine();
                        totalGlucide+=alim.getProteine();
                    }
                    RepasModel repasModel = null;
                    if(repasSelected != null){
                        repasModel=repasSelected;
                        repasModel.nom=editNom.getText().toString();
                        repasModel.description=description.getText().toString();
                        repasModel.isMatin=checkMatin.isChecked();
                        repasModel.isMidi=checkMidi.isChecked();
                        repasModel.isDiner=checkDiner.isChecked();
                        repasModel.isEncas=checkEncas.isChecked();
                        repasModel.proteineTotal=totalProteine;
                        repasModel.lipideTotal=totalLipide;
                        repasModel.glucideTotal=totalGlucide;
                    }else{
                        repasModel = new RepasModel(
                                editNom.getText().toString(),
                                description.getText().toString(),
                                totalProteine,
                                totalGlucide,
                                totalLipide,
                                checkMatin.isChecked(),
                                checkMidi.isChecked(),
                                checkDiner.isChecked(),
                                checkEncas.isChecked());
                    }
                    repasModel.save();
                    //TODO
                    if(repasSelected != null){
                        AlimentRepasModel.deleteAlimentFromAlimentRepasModelByIdRepas(repasSelected.getId());
                    }
                    for (AlimentModel selected: alimentModelSelected) {
                        AlimentRepasModel alimentRepasModel = new AlimentRepasModel();
                        alimentRepasModel.alimentModel=selected.getId();
                        alimentRepasModel.repasModel=repasModel.getId();
                        alimentRepasModel.save();
                    }
                    finish();
                }

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
                view.setSelected(true);
                alimentSelected = alimentListAdapter.getItem(position);
                setContentView(R.layout.activity_add_meal_darker);
                listView = findViewById(R.id.listAliments);
                listAlimentsSelected = findViewById(R.id.listAlimentsSelected);
                listView.setVisibility(View.GONE);
                listAlimentsSelected.setVisibility(View.GONE);
                openPopRepas(false);
            }
        });
        listAlimentsSelected.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
                view.setSelected(true);
                alimentSelected = alimentSelectedListAdapter.getItem(position);
                setContentView(R.layout.activity_add_meal_darker);
                listView = findViewById(R.id.listAliments);
                listAlimentsSelected = findViewById(R.id.listAlimentsSelected);
                listView.setVisibility(View.GONE);
                listAlimentsSelected.setVisibility(View.GONE);
                openPopRepas(true);
            }
        });
        Long idRepasModelSelected = (Long) getIntent().getLongExtra("idRepasModelSelected", 0L);
        if(idRepasModelSelected != 0L){
            repasSelected = RepasModel.getRepasById(idRepasModelSelected);
            editNom.setText(repasSelected.nom);
            description.setText(repasSelected.description);
            checkMatin.setChecked(repasSelected.isMatin);
            checkMidi.setChecked(repasSelected.isMidi);
            checkDiner.setChecked(repasSelected.isDiner);
            checkEncas.setChecked(repasSelected.isEncas);

            //TODO
            List<AlimentRepasModel> alimentsRepasModel = AlimentRepasModel.getAlimentRepasModelByRepas(repasSelected.getId());

            if(alimentsRepasModel!=null && !alimentsRepasModel.isEmpty()){
                AlimentModel alimRecup = null;
                for (AlimentRepasModel alimRepas: alimentsRepasModel) {
                    alimRecup = AlimentModel.getAlimentById(alimRepas.alimentModel);
                    if(alimRecup!=null){
                        alimentModelSelected.add(alimRecup);
                        alimentModelList.remove(alimRecup);
                    }
                }
            }
            /*alimentRepasModel.alimentModel=alimentSelected;
            alimentRepasModel.repasModel=repasModel;
            alimentRepasModel.save();
            finish();*/

            refreshLists();
            delete.setVisibility(View.GONE);
        }else{
            delete.setVisibility(View.GONE);
        }

        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlimentRepasModel.deleteAlimentFromAlimentRepasModelByIdRepas(repasSelected.getId());
                RepasModel repasModel = repasSelected;
                repasModel.delete();
                finish();
            }
        });

        updateBesoins();
    }

    private void updateBesoins(){
        //NEEDS
        BigDecimal bgProt = BigDecimal.valueOf(user.getUserDailyNeedsProtein()),bgLip = BigDecimal.valueOf(user.getUserDailyNeedsLipide()),bgglu = BigDecimal.valueOf(user.getUserDailyNeedsGlucide());
        BigDecimal nbRepasBG = BigDecimal.valueOf(Double.parseDouble(user.nbRepas));
        bgProt = bgProt.divide(nbRepasBG, BigDecimal.ROUND_HALF_DOWN).setScale(2);
        bgLip = bgLip.divide(nbRepasBG, BigDecimal.ROUND_HALF_DOWN).setScale(2);
        bgglu = bgglu.divide(nbRepasBG, BigDecimal.ROUND_HALF_DOWN).setScale(2);

        //Map aliments quantité
        BigDecimal bgProtTmpAliment = BigDecimal.ZERO, bgLipTmpAliment = BigDecimal.ZERO, bgGluTmpAliment = BigDecimal.ZERO, bgProtAliment = BigDecimal.ZERO, bgLipAliment = BigDecimal.ZERO, bgGluAliment = BigDecimal.ZERO;
        for (Map.Entry<AlimentModel, Double> alimentQuantite : mapAlimentQuantite.entrySet()) {
            //Valeur en macro / 1 gramme
            bgProtTmpAliment = BigDecimal.valueOf(alimentQuantite.getKey().getProteine()).divide(BigDecimal.valueOf(100.0)).setScale(2);
            bgLipTmpAliment = BigDecimal.valueOf(alimentQuantite.getKey().getLipide()).divide(BigDecimal.valueOf(100.0)).setScale(2);
            bgGluTmpAliment = BigDecimal.valueOf(alimentQuantite.getKey().getGlucide()).divide(BigDecimal.valueOf(100.0)).setScale(2);
            //Valeur multipliée par la quantité
            bgProtAliment = bgProtAliment.add(bgProtTmpAliment.multiply(BigDecimal.valueOf(alimentQuantite.getValue())).setScale(2));
            bgLipTmpAliment = bgLipTmpAliment.add(bgLipTmpAliment.multiply(BigDecimal.valueOf(alimentQuantite.getValue())).setScale(2));
            bgGluTmpAliment = bgGluTmpAliment.add(bgGluTmpAliment.multiply(BigDecimal.valueOf(alimentQuantite.getValue())).setScale(2));
        }

        textViewNeeds.setText("Protéines : "+bgProtAliment.doubleValue()+"/"+bgProt+"g Lipides : "+bgLipTmpAliment.doubleValue()+"/"+bgLip+"g Glucides : "+bgGluTmpAliment.doubleValue()+"/"+bgglu+"g");
    }
}
