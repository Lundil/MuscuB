package com.example.muscu.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.muscu.R;
import com.example.muscu.model.AlimentModel;
import com.example.muscu.model.RepasModel;

import java.util.List;

public class NutritionActivity extends AppCompatActivity {

    private Button button;
    private boolean isAlimentPresent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_nutrition);
        setTitle("Nutrition");

        button = findViewById(R.id.buttonAliments);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openAliments();
            }
        });

        button = findViewById(R.id.buttonRepas);
        List<AlimentModel> list = AlimentModel.getAllAliments();
        isAlimentPresent = list != null && !list.isEmpty();
        list = AlimentModel.getAlimentsByIsMatin(true);
        isAlimentPresent = list != null && !list.isEmpty();
        if(!isAlimentPresent){
            button.setBackgroundColor(Color.GRAY);
        }else{
            button.setBackgroundColor(Color.parseColor("#77d11d"));
        }
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            if(isAlimentPresent){
                openRepas();
            }else{
                Toast.makeText(getApplicationContext(), "Ajouter des aliments avant de préparer vos repas", Toast.LENGTH_SHORT).show();
            }
            }
        });
        button = findViewById(R.id.buttonPlanning);
        String erreur = isConfitionPlanningOK();
        if(erreur.isEmpty()){
            button.setBackgroundColor(Color.parseColor("#77d11d"));
        }else{
            button.setBackgroundColor(Color.GRAY);
        }
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String erreur = isConfitionPlanningOK();
                if(erreur.isEmpty()){
                    openPlanning();
                    button.setBackgroundColor(Color.parseColor("#77d11d"));
                }else{
                    button.setBackgroundColor(Color.GRAY);
                    Toast.makeText(getApplicationContext(), erreur, Toast.LENGTH_SHORT).show();
                }
            }
        });
        button = findViewById(R.id.buttonRepas);
        List<AlimentModel> listAliment = AlimentModel.getAllAliments();
        isAlimentPresent = listAliment != null && !listAliment.isEmpty();
        if(!isAlimentPresent){
            button.setBackgroundColor(Color.GRAY);
        }else{
            button.setBackgroundColor(Color.parseColor("#77d11d"));
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(isAlimentPresent){
                        openRepas();
                    }else{
                        Toast.makeText(getApplicationContext(), "Ajouter des aliments avant de préparer vos repas", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void openPlanning() {
        Intent intent = new Intent(this, PlanningActivity.class);
        startActivity(intent);
    }
    public void openRepas() {
        Intent intent = new Intent(this, RepasActivity.class);
        startActivityForResult(intent,2);
    }
    public void openAliments() {
        Intent intent = new Intent(this, AlimentsActivity.class);
        startActivityForResult(intent,1);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            button = findViewById(R.id.buttonRepas);
            List<AlimentModel> list = AlimentModel.getAllAliments();
            isAlimentPresent = list != null && !list.isEmpty();
            if(!isAlimentPresent){
                button.setBackgroundColor(Color.GRAY);
            }else{
                button.setBackgroundColor(Color.parseColor("#77d11d"));
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if(isAlimentPresent){
                            openRepas();
                        }else{
                            Toast.makeText(getApplicationContext(), "Ajouter des aliments avant de préparer vos repas", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }else if(requestCode == 2){
            button = findViewById(R.id.buttonPlanning);
            String erreur = isConfitionPlanningOK();
            if(erreur.isEmpty()){
                button.setBackgroundColor(Color.parseColor("#77d11d"));
            }else{
                button.setBackgroundColor(Color.GRAY);
            }
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String erreur = isConfitionPlanningOK();
                    if(erreur.isEmpty()){
                        openPlanning();
                        button.setBackgroundColor(Color.parseColor("#77d11d"));
                    }else{
                        button.setBackgroundColor(Color.GRAY);
                        Toast.makeText(getApplicationContext(), erreur, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private String isConfitionPlanningOK(){
        String erreur = "";
        //Tester 1 PDJ, 1 MIDI, 1 DINER, 1 collation que lipide, 1 collation que lipide, 1 collation que glucide
        List<RepasModel> listRepasMatin = RepasModel.getRepasByIsMatin(true);
        List<RepasModel> listRepasMidi = RepasModel.getRepasByIsMidi(true);
        List<RepasModel> listRepasDiner =RepasModel.getRepasByIsDiner(true);
        List<RepasModel> listRepasEncas =RepasModel.getRepasByIsEncas(true);
        //Check
        if(listRepasMatin.isEmpty()){
            erreur+="Manque un petit déjeuner\n";

        }
        if(listRepasMidi.isEmpty()){
            erreur+="Manque un déjeuner\n";

        }
        if(listRepasDiner.isEmpty()){
            erreur+="Manque un diner\n";
        }
        if(listRepasEncas.isEmpty()){
            erreur+="Manque un encas\n";
        }
        return erreur.trim();
    }
}
