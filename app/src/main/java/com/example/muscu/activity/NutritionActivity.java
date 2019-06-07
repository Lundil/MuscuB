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
import com.example.muscu.adapter.RepasListAdapter;
import com.example.muscu.model.AlimentModel;
import com.example.muscu.model.RepasModel;

import java.util.List;

public class NutritionActivity extends AppCompatActivity {

    private Button button;
    private boolean isAlimentPresent, isPetitDejeunerPresent;
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
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String erreur = "";
                if(isPetitDejeunerPresent){
                    erreur+="Ajouter 1 petit dejeuner avant de planifier votre diète";
                }
                if(erreur.isEmpty()){
                    openPlanning();
                }else{
                    Toast.makeText(getApplicationContext(), erreur, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openPlanning() {
        Intent intent = new Intent(this, PlanningActivity.class);
        startActivity(intent);
    }
    public void openRepas() {
        Intent intent = new Intent(this, RepasActivity.class);
        startActivity(intent);
    }
    public void openAliments() {
        Intent intent = new Intent(this, AlimentActivity.class);
        startActivity(intent);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            button = findViewById(R.id.buttonAliments);
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
        }
    }
}
