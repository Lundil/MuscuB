package com.example.muscu.activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.muscu.R;
import com.example.muscu.model.UtilisateurModel;

import java.util.List;

public class InfosActivity extends AppCompatActivity {

    private UtilisateurModel user;
    private List<UtilisateurModel> users;
    private Button buttonSave;
    private Spinner spinnerFrequenceActivite;
    private ToggleButton toggleButtonMan;
    private ToggleButton toggleButtonWoman;
    private Boolean displaySave;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos);
        setTitle("Profil");

        spinnerFrequenceActivite = findViewById(R.id.spinnerFrequence);
        toggleButtonMan = findViewById(R.id.toggleMan);
        toggleButtonWoman = findViewById(R.id.toggleWoman);
        buttonSave = findViewById(R.id.buttonSave);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.frequence_activite_physique, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerFrequenceActivite.setAdapter(adapter);

        toggleButtonMan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setGUIMale();
            }
        });
        toggleButtonWoman.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setGUIFemale();
            }
        });

        users = UtilisateurModel.getAllUtilisateurs();
        if(users.size()!=0){
            displaySave=false;
            user = users.get(0);
            if("M".equalsIgnoreCase(user.getSexe())){
                setGUIMale();
            }else if("F".equalsIgnoreCase(user.getSexe())){
                setGUIFemale();
            }
        }else{
            displaySave=true;
            setGUIMale();
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Profil à jour", Toast.LENGTH_SHORT).show();
                buttonSave.setVisibility(View.GONE);
            }
        });
        if(!displaySave){
            buttonSave.setVisibility(View.GONE);
        }else{
            buttonSave.setVisibility(View.VISIBLE);
        }
    }


    private void setGUIMale(){
        buttonSave.setVisibility(View.VISIBLE);
        toggleButtonMan.setChecked(true);
        toggleButtonMan.getBackground().setColorFilter(Color.parseColor("#209CF7"), PorterDuff.Mode.DARKEN);
        toggleButtonWoman.setChecked(false);
        toggleButtonWoman.getBackground().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.DARKEN);
    }

    private void setGUIFemale(){
        buttonSave.setVisibility(View.VISIBLE);
        toggleButtonMan.setChecked(false);
        toggleButtonMan.getBackground().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.DARKEN);
        toggleButtonWoman.setChecked(true);
        toggleButtonWoman.getBackground().setColorFilter(Color.parseColor("#209CF7"), PorterDuff.Mode.DARKEN);
    }
}
