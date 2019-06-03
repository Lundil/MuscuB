package com.example.muscu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.muscu.R;
import com.example.muscu.model.AlimentModel;
import com.example.muscu.model.RepasModel;

public class PopRepas extends Activity {

    private Button save;
    private EditText editNom;
    private EditText editTypeRepas;
    private EditText editProteine;
    private EditText editGlucide;
    private EditText editLipide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_popwindow_repas);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8), (int) (height*.4));

        editNom = findViewById(R.id.editNom);
        editTypeRepas = findViewById(R.id.editTypeRepas);
        save = findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RepasModel repasModel = new RepasModel(editNom.getText().toString(),editTypeRepas.getText().toString(),null,null,null,null);
                repasModel.save();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",1);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }
}
