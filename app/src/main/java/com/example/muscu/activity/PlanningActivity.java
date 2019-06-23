package com.example.muscu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.muscu.R;
import com.example.muscu.adapter.JourListAdapter;
import com.example.muscu.model.JourModel;

import java.util.List;

public class PlanningActivity extends Activity {

    private List<JourModel> jourModelList;
    private ListView listView;
    private JourListAdapter jourListAdapter;
    private JourModel jourSelected;
    private Button buttonCreateDiete;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_planning);
        setTitle("Planning");
        buttonCreateDiete = findViewById(R.id.buttonCreateDiete);
        buttonCreateDiete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openCreateDiete();
            }
        });

        //listJour
        listView = findViewById(R.id.listJour);
        jourModelList = JourModel.getAllJours();
        if (jourModelList != null && !jourModelList.isEmpty()) {
            jourListAdapter = new JourListAdapter(this, R.layout.adapter_view_jour_layout, jourModelList);
            listView.setAdapter(jourListAdapter);
        }else{
            buttonCreateDiete.setText("Créér votre diète");
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);
                jourSelected = jourListAdapter.getItem(position);
                openDetailJour();
            }
        });

    }

    public void openDetailJour(){
        Intent intent = new Intent(this, JourActivity.class);
        intent.putExtra("idJourModelSelected", jourSelected.getId());
        startActivityForResult(intent,1);
    }

    public void openCreateDiete() {
        Intent intent = new Intent(this, AddDieteActivity.class);
        startActivityForResult(intent,1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == 1) {
            //Refresh
            listView = findViewById(R.id.listJour);
            jourModelList = JourModel.getAllJours();
            if (jourModelList != null && !jourModelList.isEmpty()) {
                jourListAdapter = new JourListAdapter(this, R.layout.adapter_view_jour_layout, jourModelList);
                listView.setAdapter(jourListAdapter);
                buttonCreateDiete.setText("Changer de diète");
            }
        }
    }

}
