package com.example.muscu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.muscu.R;
import com.example.muscu.adapter.AlimentListAdapter;
import com.example.muscu.model.AlimentModel;

import java.util.List;

public class AlimentsActivity extends AppCompatActivity {

    private FloatingActionButton addAliment;
    private ListView listView;
    private List<AlimentModel> alimentModelList;
    private AlimentListAdapter alimentListAdapter;
    private AlimentModel alimentSelected;
    private CheckBox checkboxMatin;
    private CheckBox checkboxMidi;
    private CheckBox checkboxDiner;
    private CheckBox checkboxEncas;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_aliments);
        setTitle("Types d'aliments");

        checkboxMatin = findViewById(R.id.checkbox_matin);
        checkboxMidi = findViewById(R.id.checkbox_midi);
        checkboxDiner = findViewById(R.id.checkbox_diner);
        checkboxEncas = findViewById(R.id.checkbox_encas);

        checkboxMatin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                checkboxMatin.setChecked(true);
                checkboxMidi.setChecked(false);
                checkboxDiner.setChecked(false);
                checkboxEncas.setChecked(false);
                filtrer();
            }
        });
        checkboxMidi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                checkboxMatin.setChecked(false);
                checkboxMidi.setChecked(true);
                checkboxDiner.setChecked(false);
                checkboxEncas.setChecked(false);
                filtrer();
            }
        });
        checkboxDiner.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                checkboxMatin.setChecked(false);
                checkboxMidi.setChecked(false);
                checkboxDiner.setChecked(true);
                checkboxEncas.setChecked(false);
                filtrer();
            }
        });
        checkboxEncas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                checkboxMatin.setChecked(false);
                checkboxMidi.setChecked(false);
                checkboxDiner.setChecked(false);
                checkboxEncas.setChecked(true);
                filtrer();
            }
        });

        //Aliments à modifier
        listView = findViewById(R.id.listAliments);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
                view.setSelected(true);
                alimentSelected = alimentListAdapter.getItem(position);
                openSaisieAliment(alimentSelected);

            }
        });

        addAliment = findViewById(R.id.addAliment);
        addAliment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openSaisieAliment(null);
            }
        });
        filtrer();
    }

    private void openSaisieAliment(AlimentModel alimentSelected){
        Intent intent = new Intent(this, AddFoodActivity.class);
        if(alimentSelected!=null){
            intent.putExtra("idAlimentSelected", alimentSelected.getId());
        }
        startActivityForResult(intent,1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            filtrer();
        }
    }

    private void filtrer(){
        listView = findViewById(R.id.listAliments);
        if(checkboxMatin.isChecked()){
            alimentModelList = AlimentModel.getAlimentsByIsMatin(checkboxMatin.isChecked());
        }else if(checkboxMidi.isChecked()){
            alimentModelList = AlimentModel.getAlimentsByIsMidi(checkboxMidi.isChecked());
        }else if(checkboxDiner.isChecked()){
            alimentModelList = AlimentModel.getAlimentsByIsDiner(checkboxDiner.isChecked());
        }else if(checkboxEncas.isChecked()) {
            alimentModelList = AlimentModel.getAlimentsByIsEncas(checkboxEncas.isChecked());
        }
        if(alimentModelList!=null){
            alimentListAdapter = new AlimentListAdapter(this, R.layout.adapter_view_aliment_layout, alimentModelList);
            listView.setAdapter(alimentListAdapter);
        }
    }
}