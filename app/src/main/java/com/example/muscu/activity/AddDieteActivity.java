package com.example.muscu.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.example.muscu.R;
import com.example.muscu.adapter.AlimentListAdapter;
import com.example.muscu.adapter.RepasListAdapter;
import com.example.muscu.model.AlimentModel;
import com.example.muscu.model.RepasModel;
import com.example.muscu.model.UtilisateurModel;

import java.util.ArrayList;
import java.util.List;

public class AddDieteActivity extends Activity {

    private ListView listViewPdjDispos, listViewDejDispos, listViewDinerDispos, listViewEncasDispos;
    private List<RepasModel> listPdjDispos, listDejDispos, listDinerDispos, listPdjSelected = new ArrayList<>(), listDejSelected = new ArrayList<>(), listDinerSelected = new ArrayList<>();
    private List<AlimentModel> listEncasDispos, listEncasSelected = new ArrayList<>();
    private AlimentListAdapter alimentListAdapter;
    private RepasListAdapter repasListAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_diete_layout);
        setTitle("Diete");

        listViewPdjDispos=findViewById(R.id.listPdjDisponibles);
        listViewDejDispos=findViewById(R.id.listMidiDisponibles);
        listViewDinerDispos=findViewById(R.id.listDinerDisponibles);
        listViewEncasDispos=findViewById(R.id.listEncasDisponibles);

        listEncasDispos = AlimentModel.getAlimentsByIsEncas(true);
        listPdjDispos = RepasModel.getRepasByIsMatin(true);
        listDejDispos = RepasModel.getRepasByIsMidi(true);
        listDinerDispos =RepasModel.getRepasByIsDiner(true);
        UtilisateurModel user = UtilisateurModel.getAllUtilisateurs();

        repasListAdapter = new RepasListAdapter(this, R.layout.adapter_view_aliment_layout, listPdjDispos);
        listViewPdjDispos.setAdapter(repasListAdapter);
        repasListAdapter = new RepasListAdapter(this, R.layout.adapter_view_aliment_layout, listDejDispos);
        listViewDejDispos.setAdapter(repasListAdapter);
        repasListAdapter = new RepasListAdapter(this, R.layout.adapter_view_aliment_layout, listDinerDispos);
        listViewDinerDispos.setAdapter(repasListAdapter);
        alimentListAdapter = new AlimentListAdapter(this, R.layout.adapter_view_aliment_layout, listEncasDispos);
        listViewEncasDispos.setAdapter(alimentListAdapter);


    }

    private boolean isGUIFilled(){
        return !listEncasSelected.isEmpty() && !listPdjSelected.isEmpty() && !listDejSelected.isEmpty() && !listDinerSelected.isEmpty() ;
    }

}
