package com.example.muscu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.muscu.R;
import com.example.muscu.adapter.RepasListAdapter;
import com.example.muscu.model.AlimentModel;
import com.example.muscu.model.RepasModel;

import java.util.ArrayList;
import java.util.List;

public class RepasActivity extends Activity {

    private FloatingActionButton addRepas;
    private RepasModel repasModelSelected;
    private List<RepasModel> repasModelList;
    private ListView listView;
    private RepasListAdapter repasListAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_repas);

        addRepas = findViewById(R.id.addRepas);
        addRepas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openAddMeal(null);
            }
        });
        listView = findViewById(R.id.listRepas);
        repasModelList = RepasModel.getAllRepas();
        if (repasModelList != null) {
            repasListAdapter = new RepasListAdapter(this, R.layout.adapter_view_aliment_layout, repasModelList);
            listView.setAdapter(repasListAdapter);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
                view.setSelected(true);
                repasModelSelected = repasListAdapter.getItem(position);
                openAddMeal(repasModelSelected);
            }
        });
    }
    private void openAddMeal(RepasModel repasModelSelected){
        Intent intent = new Intent(this, AddMealActivity.class);
        if(repasModelSelected != null){
            intent.putExtra("idRepasModelSelected",repasModelSelected.getId());
        }
        startActivityForResult(intent,1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            repasModelList = RepasModel.getAllRepas();
            if (repasModelList != null) {
                repasListAdapter = new RepasListAdapter(this, R.layout.adapter_view_aliment_layout, repasModelList);
                listView.setAdapter(repasListAdapter);
            }
        }
    }
}