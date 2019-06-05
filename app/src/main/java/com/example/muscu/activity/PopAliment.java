package com.example.muscu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.muscu.R;
import com.example.muscu.model.AlimentModel;

public class PopAliment extends Activity {

    private Button save;
    private Spinner spinnerTypeAliment;
    private EditText editNom;
    private EditText editProteine;
    private EditText editGlucide;
    private EditText editLipide;
    private CheckBox checkMatin;
    private CheckBox checkMidi;
    private CheckBox checkDiner;
    private CheckBox checkEncas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_popwindow_aliment);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8), (int) (height*.8));

        editNom = findViewById(R.id.editNom);
        editProteine = findViewById(R.id.editProteine);
        editGlucide = findViewById(R.id.editGlucide);
        editLipide = findViewById(R.id.editLipide);
        checkMatin = findViewById(R.id.checkbox_matin);
        checkMidi = findViewById(R.id.checkbox_midi);
        checkDiner = findViewById(R.id.checkbox_diner);
        checkEncas = findViewById(R.id.checkbox_encas);
        spinnerTypeAliment = (Spinner) findViewById(R.id.spinnerTypeAliment);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type_aliment_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerTypeAliment.setAdapter(adapter);

        save = findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlimentModel alimentModel = new AlimentModel();
                alimentModel.setNom(editNom.getText().toString());
                alimentModel.setProteine(Double.parseDouble(editProteine.getText().toString()));
                alimentModel.setGlucide(Double.parseDouble(editGlucide.getText().toString()));
                alimentModel.setLipide(Double.parseDouble(editLipide.getText().toString()));
                alimentModel.setTypeAliment(spinnerTypeAliment.getSelectedItem().toString());
                alimentModel.setMatin(checkMatin.isChecked());
                alimentModel.setMidi(checkMidi.isChecked());
                alimentModel.setDiner(checkDiner.isChecked());
                alimentModel.setEncas(checkEncas.isChecked());
                alimentModel.save();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",1);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }
}
