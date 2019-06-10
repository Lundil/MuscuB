package com.example.muscu.activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.text.TextWatcher;

import com.example.muscu.R;
import com.example.muscu.model.UtilisateurModel;

import java.util.List;

public class InfosActivity extends AppCompatActivity {

    private UtilisateurModel user;
    private List<UtilisateurModel> users;
    private Button buttonSave;
    private Spinner spinnerFrequenceActivite;
    private Spinner spinnerObjectif;
    private Spinner spinnerNbRepas;
    private ToggleButton toggleButtonMan;
    private ToggleButton toggleButtonWoman;
    private EditText editTextAge;
    private EditText editTextTaille;
    private EditText editTextPoids;
    private Boolean displaySave;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_infos);
        setTitle("Profil");

        spinnerFrequenceActivite = findViewById(R.id.spinnerFrequence);
        spinnerObjectif = findViewById(R.id.spinnerObjectif);
        spinnerNbRepas = findViewById(R.id.spinnerNbRepas);
        toggleButtonMan = findViewById(R.id.toggleMan);
        toggleButtonWoman = findViewById(R.id.toggleWoman);
        buttonSave = findViewById(R.id.buttonSave);
        editTextAge = findViewById(R.id.editAge);
        editTextTaille = findViewById(R.id.editTaille);
        editTextPoids = findViewById(R.id.editPoids);

        // Create an ArrayAdapter using the string array and a default spinner layout
        // Specify the layout to use when the list of choices appears
        // Apply the adapter to the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.objectifs, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerObjectif.setAdapter(adapter);
        adapter = ArrayAdapter.createFromResource(this, R.array.frequence_activite_physique, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrequenceActivite.setAdapter(adapter);
        adapter = ArrayAdapter.createFromResource(this, R.array.nombre_repas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNbRepas.setAdapter(adapter);

        //Chargement de l'utilisateur
        users = UtilisateurModel.getAllUtilisateurs();
        if(users.size()!=0){
            user = users.get(0);
        }

        toggleButtonMan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(user==null){
                    buttonSave.setVisibility(View.VISIBLE);
                }else if(!"M".equalsIgnoreCase(user.sexe)){
                    buttonSave.setVisibility(View.VISIBLE);
                }else{
                    buttonSave.setVisibility(View.GONE);
                }
                setGUIMale();
            }
        });
        toggleButtonWoman.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(user==null){
                    buttonSave.setVisibility(View.VISIBLE);
                }else if(!"F".equalsIgnoreCase(user.sexe)){
                    buttonSave.setVisibility(View.VISIBLE);
                }else{
                    buttonSave.setVisibility(View.GONE);
                }
                setGUIFemale();
            }
        });
        spinnerObjectif.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(user==null){
                    buttonSave.setVisibility(View.VISIBLE);
                }else if(hasGUIChanged()){
                    buttonSave.setVisibility(View.VISIBLE);
                }else{
                    buttonSave.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        spinnerFrequenceActivite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(user==null){
                    buttonSave.setVisibility(View.VISIBLE);
                }else if(hasGUIChanged()){
                    buttonSave.setVisibility(View.VISIBLE);
                }else{
                    buttonSave.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        spinnerNbRepas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(user==null){
                    buttonSave.setVisibility(View.VISIBLE);
                }else if(hasGUIChanged()){
                    buttonSave.setVisibility(View.VISIBLE);
                }else{
                    buttonSave.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        //Chargement de la page
        if(user!=null){
            //Affichage des infos de l'utilisateur
            //Button save hidden
            displaySave=false;
            //Sex
            if("M".equalsIgnoreCase(user.getSexe())){
                setGUIMale();
            }else if("F".equalsIgnoreCase(user.getSexe())){
                setGUIFemale();
            }
            editTextAge.setText(user.getAge().toString());
            editTextPoids.setText(user.getPoids().toString());
            editTextTaille.setText(user.getTaille().toString());
            if("Rare".equalsIgnoreCase(user.getActivitePhysique())){
                spinnerFrequenceActivite.setSelection(0);
            }else if("1-3".equalsIgnoreCase(user.getActivitePhysique())){
                spinnerFrequenceActivite.setSelection(1);
            }else if("4-5".equalsIgnoreCase(user.getActivitePhysique())){
                spinnerFrequenceActivite.setSelection(2);
            }else{
                spinnerFrequenceActivite.setSelection(3);
            }
            if("Mincir".equalsIgnoreCase(user.getObjectif())){
                spinnerObjectif.setSelection(0);
            }else if("Maintien".equalsIgnoreCase(user.getObjectif())){
                spinnerObjectif.setSelection(1);
            }else if("Muscler".equalsIgnoreCase(user.getObjectif())){
                spinnerObjectif.setSelection(2);
            }

            if("3".equalsIgnoreCase(user.getNbRepas())){
                spinnerNbRepas.setSelection(0);
            }else if("4".equalsIgnoreCase(user.getNbRepas())){
                spinnerNbRepas.setSelection(1);
            }else if("5".equalsIgnoreCase(user.getNbRepas())){
                spinnerNbRepas.setSelection(2);
            }else if("6".equalsIgnoreCase(user.getNbRepas())){
                spinnerNbRepas.setSelection(3);
            }
        }else{
            //Pas d'utilisateur
            //Button save displayed
            displaySave=true;
            //Sex male selected
            setGUIMale();
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(editTextAge.getText().toString().isEmpty() || editTextPoids.getText().toString().isEmpty() || editTextTaille.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Renseigner tous les champs", Toast.LENGTH_SHORT).show();
                }else{
                    if(user==null){
                        user = new UtilisateurModel();
                    }
                    user.setActivitePhysique(spinnerFrequenceActivite.getSelectedItem().toString());
                    user.setAge(Integer.valueOf(editTextAge.getText().toString()));
                    user.setObjectif(spinnerObjectif.getSelectedItem().toString());
                    user.setNbRepas(spinnerNbRepas.getSelectedItem().toString());
                    user.setPoids(Double.parseDouble(editTextPoids.getText().toString()));
                    user.setSexe((toggleButtonMan.isChecked()) ? "M" : "F");
                    user.setTaille(Double.parseDouble(editTextTaille.getText().toString()));
                    user.save();

                    Toast.makeText(getApplicationContext(), "Profil à jour", Toast.LENGTH_SHORT).show();
                    buttonSave.setVisibility(View.GONE);
                }

            }
        });
        editTextAge.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence cs, int s, int b, int c) {
                Log.i("Key:", cs.toString());
            }
            public void afterTextChanged(Editable editable) {
                if(user==null){
                    buttonSave.setVisibility(View.VISIBLE);
                }else if(hasGUIChanged()){
                    buttonSave.setVisibility(View.VISIBLE);
                }else{
                    buttonSave.setVisibility(View.GONE);
                }
            }
            public void beforeTextChanged(CharSequence cs, int i, int j, int k) { }
        });
        editTextPoids.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence cs, int s, int b, int c) {
                Log.i("Key:", cs.toString());
            }
            public void afterTextChanged(Editable editable) {
                if(user==null){
                    buttonSave.setVisibility(View.VISIBLE);
                }else if(hasGUIChanged()){
                    buttonSave.setVisibility(View.VISIBLE);
                }else{
                    buttonSave.setVisibility(View.GONE);
                }
            }
            public void beforeTextChanged(CharSequence cs, int i, int j, int k) { }
        });
        editTextTaille.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence cs, int s, int b, int c) {
                Log.i("Key:", cs.toString());
            }
            public void afterTextChanged(Editable editable) {
                if(user==null){
                    buttonSave.setVisibility(View.VISIBLE);
                }else if(hasGUIChanged()){
                    buttonSave.setVisibility(View.VISIBLE);
                }else{
                    buttonSave.setVisibility(View.GONE);
                }
            }
            public void beforeTextChanged(CharSequence cs, int i, int j, int
                    k) { }
        });
        if(!displaySave){
            buttonSave.setVisibility(View.GONE);
        }else{
            buttonSave.setVisibility(View.VISIBLE);
        }
    }


    private void setGUIMale(){
        toggleButtonMan.setChecked(true);
        toggleButtonMan.getBackground().setColorFilter(Color.parseColor("#209CF7"), PorterDuff.Mode.DARKEN);
        toggleButtonWoman.setChecked(false);
        toggleButtonWoman.getBackground().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.DARKEN);
    }

    private void setGUIFemale(){
        toggleButtonMan.setChecked(false);
        toggleButtonMan.getBackground().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.DARKEN);
        toggleButtonWoman.setChecked(true);
        toggleButtonWoman.getBackground().setColorFilter(Color.parseColor("#209CF7"), PorterDuff.Mode.DARKEN);
    }

    private boolean hasGUIChanged(){
        return  (user.getAge() != Integer.valueOf(editTextAge.getText().toString())) ||
                !(user.getPoids() == Double.parseDouble(editTextPoids.getText().toString())) ||
                !(user.getTaille() == Double.parseDouble(editTextTaille.getText().toString())) ||
                !(user.getActivitePhysique().equalsIgnoreCase(spinnerFrequenceActivite.getSelectedItem().toString())) ||
                !(user.getObjectif().equalsIgnoreCase(spinnerObjectif.getSelectedItem().toString())) ||
                !(user.getNbRepas().equalsIgnoreCase(spinnerNbRepas.getSelectedItem().toString()));
    }
}
