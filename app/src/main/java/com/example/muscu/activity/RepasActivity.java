package com.example.muscu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.muscu.R;
import com.example.muscu.adapter.AlimentListAdapter;
import com.example.muscu.adapter.RepasListAdapter;
import com.example.muscu.adapter.SampleFragmentPagerAdapter;
import com.example.muscu.model.AlimentModel;
import com.example.muscu.model.RepasModel;

import java.util.List;

public class RepasActivity extends AppCompatActivity {

    private FloatingActionButton addRepas;
    private List<RepasModel> repasModelList;
    private ListView listView;
    private RepasListAdapter repasListAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repas);
        setTitle("Repas");

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                RepasActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        listView = findViewById(R.id.listRepas);
        repasModelList = RepasModel.getAllRepas();
        if(repasModelList!=null){
            repasListAdapter = new RepasListAdapter(this, R.layout.adapter_view_layout, repasModelList);
            listView.setAdapter(repasListAdapter);
        }

        addRepas = findViewById(R.id.addRepas);
        addRepas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openPopForm();
            }
        });
    }
    private void openPopForm(){
        Intent intent = new Intent(this, PopRepas.class);
        startActivityForResult(intent,1);
    }

    private void refresh(){
        repasModelList = RepasModel.getAllRepas();
        repasListAdapter = new RepasListAdapter(this, R.layout.adapter_view_layout, repasModelList);
        listView.setAdapter(repasListAdapter);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                refresh();
            }
        }
    }
}