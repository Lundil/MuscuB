package com.example.muscu.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.muscu.R;
import com.example.muscu.model.UtilisateurModel;

import java.util.List;

public class AccueilActivity extends Activity {

    private Button button;
    private boolean isUserCreated;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_accueil);

        button = findViewById(R.id.buttonProfil);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openInfos();
            }
        });

        button = findViewById(R.id.buttonNutrition);
        List<UtilisateurModel> list = UtilisateurModel.getAllUtilisateurs();
        isUserCreated = list != null && !list.isEmpty();
        if(!isUserCreated){
            button.setBackgroundColor(Color.GRAY);
        }else{
            button.setBackgroundColor(Color.parseColor("#ff480c"));
        }
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(isUserCreated){
                    openNutrition();
                }else{
                    Toast.makeText(getApplicationContext(), "Renseignez votre profil avant de préparer votre diète", Toast.LENGTH_SHORT).show();
                }
            }
        });


        button = findViewById(R.id.buttonWorkout);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openWorkout();
            }
        });

    }

    public void openNutrition() {
        Intent intent = new Intent(this, NutritionActivity.class);
        startActivity(intent);
    }
    public void openWorkout() {
        Intent intent = new Intent(this, WorkoutActivity.class);
        startActivity(intent);
    }
    public void openInfos() {
        Intent intent = new Intent(this, InfosActivity.class);
        startActivityForResult(intent,1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            button = findViewById(R.id.buttonNutrition);
            List<UtilisateurModel> list = UtilisateurModel.getAllUtilisateurs();
            isUserCreated = list != null && !list.isEmpty();
            if(!isUserCreated){
                button.setBackgroundColor(Color.GRAY);
            }else{
                button.setBackgroundColor(Color.parseColor("#ff480c"));
            }
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(isUserCreated){
                        openNutrition();
                    }else{
                        Toast.makeText(getApplicationContext(), "Renseignez votre profil avant de préparer votre diète", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
