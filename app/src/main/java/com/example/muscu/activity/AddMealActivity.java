package com.example.muscu.activity;

import android.app.Activity;
import android.content.Intent;
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
import java.math.RoundingMode;
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
        updateBesoins();
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
        if(repasSelected == null){
            repasSelected = new RepasModel();
        }
        repasSelected.nom=editNom.getText().toString();
        repasSelected.description=description.getText().toString();
        repasSelected.isMatin=checkMatin.isChecked();
        repasSelected.isMidi=checkMidi.isChecked();
        repasSelected.isDiner=checkDiner.isChecked();
        repasSelected.isEncas=checkEncas.isChecked();
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
                        bgProt = bgProt.divide(nbRepasBG, BigDecimal.ROUND_HALF_DOWN);
                        bgLip = bgLip.divide(nbRepasBG, BigDecimal.ROUND_HALF_DOWN);
                        bgglu = bgglu.divide(nbRepasBG, BigDecimal.ROUND_HALF_DOWN);

                        //Map aliments quantité
                        BigDecimal bgProtTmpAliment = BigDecimal.ZERO, bgLipTmpAliment = BigDecimal.ZERO, bgGluTmpAliment = BigDecimal.ZERO, bgProtAliment = BigDecimal.ZERO, bgLipAliment = BigDecimal.ZERO, bgGluAliment = BigDecimal.ZERO;
                        for (Map.Entry<AlimentModel, Double> alimentQuantite : mapAlimentQuantite.entrySet()) {
                            //Valeur en macro / 1 gramme
                            bgProtTmpAliment = BigDecimal.valueOf(alimentQuantite.getKey().getProteine()).divide(BigDecimal.valueOf(100.0));
                            bgLipTmpAliment = BigDecimal.valueOf(alimentQuantite.getKey().getLipide()).divide(BigDecimal.valueOf(100.0));
                            bgGluTmpAliment = BigDecimal.valueOf(alimentQuantite.getKey().getGlucide()).divide(BigDecimal.valueOf(100.0));
                            //Valeur multipliée par la quantité
                            bgProtAliment = bgProtAliment.add(bgProtTmpAliment.multiply(BigDecimal.valueOf(alimentQuantite.getValue())));
                            bgLipAliment = bgLipAliment.add(bgLipTmpAliment.multiply(BigDecimal.valueOf(alimentQuantite.getValue())));
                            bgGluAliment = bgGluAliment.add(bgGluTmpAliment.multiply(BigDecimal.valueOf(alimentQuantite.getValue())));
                        }
                        if(bgProtAliment.compareTo(bgProt) == -1 || bgLipAliment.compareTo(bgLip) == -1 || bgGluAliment.compareTo(bgglu) == -1){
                            Toast.makeText(getApplicationContext(), "Besoins quotidiens non remplis", Toast.LENGTH_SHORT).show();
                        }else{
                            RepasModel repasModel = null;
                            if(repasSelected != null){
                                repasModel=repasSelected;
                                repasModel.nom=editNom.getText().toString();
                                repasModel.description=description.getText().toString();
                                repasModel.isMatin=checkMatin.isChecked();
                                repasModel.isMidi=checkMidi.isChecked();
                                repasModel.isDiner=checkDiner.isChecked();
                                repasModel.isEncas=checkEncas.isChecked();
                                repasModel.proteineTotal=bgProtTmpAliment.doubleValue();
                                repasModel.lipideTotal=bgLipTmpAliment.doubleValue();
                                repasModel.glucideTotal=bgGluTmpAliment.doubleValue();
                            }else{
                                repasModel = new RepasModel(
                                        editNom.getText().toString(),
                                        description.getText().toString(),
                                        bgProtTmpAliment.doubleValue(),
                                        bgGluTmpAliment.doubleValue(),
                                        bgLipTmpAliment.doubleValue(),
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
            editNom.setText(repasSelected.nom);
            description.setText(repasSelected.description);
            checkMatin.setChecked(repasSelected.isMatin);
            checkMidi.setChecked(repasSelected.isMidi);
            checkDiner.setChecked(repasSelected.isDiner);
            checkEncas.setChecked(repasSelected.isEncas);
            if(repasSelected.getId()==null){
                //TODO
                delete.setVisibility(View.GONE);
            }
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
                    mapAlimentQuantite.put(alimRecup, alimRepas.quantite);
                    if(alimRecup!=null){
                        alimentModelSelected.add(alimRecup);
                        alimentModelList.remove(alimRecup);
                    }
                }
            }

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
        bgProt = bgProt.divide(nbRepasBG, BigDecimal.ROUND_HALF_DOWN).setScale(2, RoundingMode.HALF_DOWN);
        bgLip = bgLip.divide(nbRepasBG, BigDecimal.ROUND_HALF_DOWN).setScale(2, RoundingMode.HALF_DOWN);
        bgglu = bgglu.divide(nbRepasBG, BigDecimal.ROUND_HALF_DOWN).setScale(2, RoundingMode.HALF_DOWN);

        //Map aliments quantité
        BigDecimal bgProtTmpAliment = BigDecimal.ZERO, bgLipTmpAliment = BigDecimal.ZERO, bgGluTmpAliment = BigDecimal.ZERO, bgProtAliment = BigDecimal.ZERO, bgLipAliment = BigDecimal.ZERO, bgGluAliment = BigDecimal.ZERO;
        for (Map.Entry<AlimentModel, Double> alimentQuantite : mapAlimentQuantite.entrySet()) {
            //Valeur en macro / 1 gramme
            bgProtTmpAliment = BigDecimal.valueOf(alimentQuantite.getKey().getProteine()).divide(BigDecimal.valueOf(100.0));
            bgLipTmpAliment = BigDecimal.valueOf(alimentQuantite.getKey().getLipide()).divide(BigDecimal.valueOf(100.0));
            bgGluTmpAliment = BigDecimal.valueOf(alimentQuantite.getKey().getGlucide()).divide(BigDecimal.valueOf(100.0));
            //Valeur multipliée par la quantité
            bgProtAliment = bgProtAliment.add(bgProtTmpAliment.multiply(BigDecimal.valueOf(alimentQuantite.getValue()))).setScale(2, RoundingMode.HALF_DOWN);
            bgLipAliment = bgLipAliment.add(bgLipTmpAliment.multiply(BigDecimal.valueOf(alimentQuantite.getValue()))).setScale(2, RoundingMode.HALF_DOWN);
            bgGluAliment = bgGluAliment.add(bgGluTmpAliment.multiply(BigDecimal.valueOf(alimentQuantite.getValue()))).setScale(2, RoundingMode.HALF_DOWN);
        }

        textViewNeeds.setText("Protéines : "+bgProtAliment.doubleValue()+"/"+bgProt+"g Lipides : "+bgLipAliment.doubleValue()+"/"+bgLip+"g Glucides : "+bgGluAliment.doubleValue()+"/"+bgglu+"g");
    }
}
