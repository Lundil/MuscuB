package com.example.muscu.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.muscu.R;
import com.example.muscu.adapter.AlimentListAdapter;
import com.example.muscu.model.AlimentModel;
import com.example.muscu.model.RepasModel;

import java.util.ArrayList;
import java.util.List;

public class AddMealActivity extends Activity {

    private Button save;
    private EditText editNom;
    private EditText description;
    private CheckBox checkMatin;
    private CheckBox checkMidi;
    private CheckBox checkDiner;
    private ListView listView,listAlimentsSelected;
    private AlimentModel alimentSelected;
    private List<AlimentModel> alimentModelList, alimentModelSelected = new ArrayList<>();
    private AlimentListAdapter alimentListAdapter,alimentSelectedListAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_meal);

        listView = findViewById(R.id.listAliments);
        listAlimentsSelected = findViewById(R.id.listAlimentsSelected);
        editNom = findViewById(R.id.editNom);
        description = findViewById(R.id.editDescription);
        checkMatin = findViewById(R.id.checkbox_matin);
        checkMidi = findViewById(R.id.checkbox_midi);
        checkDiner = findViewById(R.id.checkbox_diner);
        save = findViewById(R.id.save);
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
                    RepasModel repasModel = new RepasModel();
                    repasModel.setNom(editNom.getText().toString());
                    //Calcul
                    Double totalProteine = 0.0,totalLipide = 0.0,totalGlucide = 0.0;
                    for (AlimentModel alim: alimentModelSelected) {
                        totalProteine+=alim.getProteine();
                        totalLipide+=alim.getProteine();
                        totalGlucide+=alim.getProteine();
                    }
                    repasModel.setProteineTotal(totalProteine);
                    repasModel.setGlucideTotal(totalGlucide);
                    repasModel.setLipideTotal(totalLipide);
                    repasModel.setAlimentModels(alimentModelSelected);
                    repasModel.setMatin(checkMatin.isChecked());
                    repasModel.setMidi(checkMidi.isChecked());
                    repasModel.setDiner(checkDiner.isChecked());
                    repasModel.save();
                    finish();
                }

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
                view.setSelected(true);
                alimentSelected = alimentListAdapter.getItem(position);
                alimentModelSelected.add(alimentSelected);
                alimentModelList.remove(alimentSelected);
                refreshLists();
            }
        });
        listAlimentsSelected.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
                view.setSelected(true);
                alimentSelected = alimentSelectedListAdapter.getItem(position);
                alimentModelList.add(alimentSelected);
                alimentModelSelected.remove(alimentSelected);
                refreshLists();
            }
        });

    }

    private void refreshLists(){
        alimentListAdapter = new AlimentListAdapter(this, R.layout.adapter_view_aliment_layout, alimentModelList);
        listView.setAdapter(alimentListAdapter);
        alimentSelectedListAdapter = new AlimentListAdapter(this, R.layout.adapter_view_aliment_layout, alimentModelSelected);
        listAlimentsSelected.setAdapter(alimentSelectedListAdapter);


    }

    private boolean isGUIFilled() {
        return !editNom.getText().toString().isEmpty() &&
                (checkMatin.isChecked() || checkMidi.isChecked() || checkDiner.isChecked())
                && !alimentModelSelected.isEmpty();
    }
}
