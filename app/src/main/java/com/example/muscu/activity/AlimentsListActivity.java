package com.example.muscu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import com.example.muscu.R;
import com.example.muscu.adapter.AlimentListAdapter;
import com.example.muscu.model.AlimentModel;

import java.util.List;

public class AlimentsListActivity extends Activity {

    private Button button;
    private FloatingActionButton addAliment;
    private ListView listView;
    private List<AlimentModel> alimentModelList;
    private AlimentListAdapter alimentListAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_aliment_list);
        String typeAliment = getIntent().getStringExtra("typeAliment");
        setTitle("Vos "+typeAliment);

        addAliment = findViewById(R.id.addAliment);
        addAliment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openScanner();
            }
        });
        listView = findViewById(R.id.listAliments);
        alimentModelList = AlimentModel.getAlimentsByType(typeAliment);
        if(alimentModelList!=null){
            alimentListAdapter = new AlimentListAdapter(this, R.layout.adapter_view_aliment_layout, alimentModelList);
            listView.setAdapter(alimentListAdapter);
        }
    }

    private void openScanner(){
        Intent intent = new Intent(this, SimpleScannerActivity.class);
        startActivity(intent);
    }

}
