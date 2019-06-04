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
import com.example.muscu.adapter.SampleFragmentPagerAdapter;
import com.example.muscu.model.AlimentModel;

import java.util.List;

public class AlimentActivity extends AppCompatActivity {

    private FloatingActionButton addAliment;
    private ListView listView;
    private List<AlimentModel> alimentModelList;
    private AlimentListAdapter alimentListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aliments);
        refresh();
    }

    private void openSaisieAliment(){
        Intent intent = new Intent(this, SimpleScannerActivity.class);
        startActivityForResult(intent,1);
    }

    private void refresh(){
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),AlimentActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        listView = findViewById(R.id.listAliments);
        addAliment = findViewById(R.id.addAliment);

        addAliment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openSaisieAliment();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            setContentView(R.layout.activity_aliments);
            refresh();
            if(resultCode == Activity.RESULT_OK){

            }
        }
    }
}