package com.example.muscu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import com.example.muscu.R;
import com.example.muscu.adapter.AlimentListAdapter;
import com.example.muscu.model.AlimentModel;

import java.util.List;

public class AlimentActivity extends AppCompatActivity {

    private FloatingActionButton addAliment;
    private Button button;
    private ListView listView;
    private List<AlimentModel> alimentModelList;
    private AlimentListAdapter alimentListAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_aliments);
        setTitle("Types d'aliments");

        addAliment = findViewById(R.id.addAliment);
        addAliment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openSaisieAliment();
            }
        });
        button = findViewById(R.id.buttonViande);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openListAliment("Viande");
            }
        });
        button = findViewById(R.id.buttonPoisson);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openListAliment("Poisson");
            }
        });
        button = findViewById(R.id.buttonLegume);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openListAliment("Légume");
            }
        });
        button = findViewById(R.id.buttonLaitage);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openListAliment("Laitage");
            }
        });
        button = findViewById(R.id.buttonBoisson);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openListAliment("Boisson");
            }
        });
        button = findViewById(R.id.buttonFeculent);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openListAliment("Féculent");
            }
        });
        button = findViewById(R.id.buttonFruit);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openListAliment("Fruit");
            }
        });
        button = findViewById(R.id.buttonSauce);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openListAliment("Sauce");
            }
        });
    }

    private void openListAliment(String typeAliment){
        Intent intent = new Intent(this, AlimentsListActivity.class);
        intent.putExtra("typeAliment", typeAliment);
        startActivityForResult(intent,1);
    }

    private void openSaisieAliment(){
        Intent intent = new Intent(this, SimpleScannerActivity.class);
        startActivity(intent);
    }

}