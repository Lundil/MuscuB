package com.example.muscu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.example.muscu.R;
import com.example.muscu.adapter.AlimentListAdapter;
import com.example.muscu.model.AlimentModel;

import java.util.List;

public class AlimentActivity extends AppCompatActivity {

    private FloatingActionButton addAliment;
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
        listView = findViewById(R.id.listAliments);
        alimentModelList = AlimentModel.getAllAliments();
        if(alimentModelList!=null){
            alimentListAdapter = new AlimentListAdapter(this, R.layout.adapter_view_aliment_layout, alimentModelList);
            listView.setAdapter(alimentListAdapter);
        }
    }

    private void openSaisieAliment(){
        Intent intent = new Intent(this, SimpleScannerActivity.class);
        startActivityForResult(intent,1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            listView = findViewById(R.id.listAliments);
            alimentModelList = AlimentModel.getAllAliments();
            if(alimentModelList!=null){
                alimentListAdapter = new AlimentListAdapter(this, R.layout.adapter_view_aliment_layout, alimentModelList);
                listView.setAdapter(alimentListAdapter);
            }
        }
    }
}