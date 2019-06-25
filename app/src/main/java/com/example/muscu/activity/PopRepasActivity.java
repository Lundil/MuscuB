package com.example.muscu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.muscu.R;

import org.apache.commons.lang3.StringUtils;

public class PopRepasActivity extends Activity {

    private Button save, delete;
    private EditText editText;
    private Double quantiteExistante = 0.0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_repas_layout);

        //FINDS
        save = findViewById(R.id.buttonSave);
        delete = findViewById(R.id.buttonDelete);
        editText = findViewById(R.id.editText);

        Double quantiteExistante = (Double) getIntent().getDoubleExtra("quantiteExistante", 0.0);
        if(quantiteExistante != 0L){
            editText.setText(quantiteExistante.toString());
            delete.setVisibility(View.VISIBLE);
        }else{
            delete.setVisibility(View.GONE);
        }
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8),(int)(height*.22));

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(!isGUIFilled()){
                    Toast.makeText(getApplicationContext(), "Renseigner tous les champs", Toast.LENGTH_SHORT).show();
                }else{
                    //Calcul
                    Toast.makeText(getApplicationContext(), "Tous les champs sont renseignés", Toast.LENGTH_SHORT).show();
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("quantite", editText.getText().toString());
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(!isGUIFilled()){
                    Toast.makeText(getApplicationContext(), "Renseigner tous les champs", Toast.LENGTH_SHORT).show();
                }else{
                    //Calcul
                    Toast.makeText(getApplicationContext(), "Tous les champs sont renseignés", Toast.LENGTH_SHORT).show();
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("isDelete", true);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }

            }
        });

    }

    private boolean isGUIFilled(){
        return !StringUtils.isBlank(editText.getText());
    }
}
