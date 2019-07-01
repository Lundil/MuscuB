package com.example.muscu.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.example.muscu.R;
import com.example.muscu.adapter.AlimentAchatListAdapter;
import com.example.muscu.model.AlimentModel;
import com.example.muscu.model.AlimentRepasModel;
import com.example.muscu.model.JourModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoursesActivity extends Activity {

    private ListView listAlimentsAchat;
    private AlimentAchatListAdapter alimentAchatListAdapter;
    private List<String> listAlimentAchat = new ArrayList<>();
    private List<AlimentRepasModel> listAlimentRepas;
    private HashMap<AlimentModel,Integer> mapAlimentQuantite = new HashMap<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_courses);
        setTitle("Liste de courses");

        listAlimentsAchat = findViewById(R.id.listAlimentsAchat);

        //Alimentation listAlimentAchat avec les ingredients de la semaine
        List<JourModel> jours = JourModel.getAllJours();
        AlimentModel alimTmp = null;
        Integer quantTmp = null;
        for (JourModel jour : jours) {
            //
            listAlimentRepas = AlimentRepasModel.getAlimentRepasModelByRepas(jour.getRepasMatin().getId());
            for (AlimentRepasModel alimentRepas : listAlimentRepas) {
                alimTmp = AlimentModel.getAlimentById(alimentRepas.alimentModel);
                quantTmp = alimentRepas.quantite.intValue();
                if(mapAlimentQuantite.get(alimTmp) == null){
                    mapAlimentQuantite.put(alimTmp, alimentRepas.quantite.intValue());
                }else{
                    quantTmp += mapAlimentQuantite.get(alimTmp);
                    mapAlimentQuantite.put(alimTmp, quantTmp);
                }
            }

            listAlimentRepas = AlimentRepasModel.getAlimentRepasModelByRepas(jour.getRepasMidi().getId());
            for (AlimentRepasModel alimentRepas : listAlimentRepas) {
                alimTmp = AlimentModel.getAlimentById(alimentRepas.alimentModel);
                quantTmp = alimentRepas.quantite.intValue();
                if(mapAlimentQuantite.get(alimTmp) == null){
                    mapAlimentQuantite.put(alimTmp, alimentRepas.quantite.intValue());
                }else{
                    quantTmp += mapAlimentQuantite.get(alimTmp);
                    mapAlimentQuantite.put(alimTmp, quantTmp);
                }
            }
            listAlimentRepas = AlimentRepasModel.getAlimentRepasModelByRepas(jour.getRepasDiner().getId());
            for (AlimentRepasModel alimentRepas : listAlimentRepas) {
                alimTmp = AlimentModel.getAlimentById(alimentRepas.alimentModel);
                quantTmp = alimentRepas.quantite.intValue();
                if(mapAlimentQuantite.get(alimTmp) == null){
                    mapAlimentQuantite.put(alimTmp, alimentRepas.quantite.intValue());
                }else{
                    quantTmp += mapAlimentQuantite.get(alimTmp);
                    mapAlimentQuantite.put(alimTmp, quantTmp);
                }
            }
            if(jour.getRepasEncas1()!=null){
                listAlimentRepas = AlimentRepasModel.getAlimentRepasModelByRepas(jour.getRepasEncas1().getId());
                for (AlimentRepasModel alimentRepas : listAlimentRepas) {
                    alimTmp = AlimentModel.getAlimentById(alimentRepas.alimentModel);
                    quantTmp = alimentRepas.quantite.intValue();
                    if(mapAlimentQuantite.get(alimTmp) == null){
                        mapAlimentQuantite.put(alimTmp, alimentRepas.quantite.intValue());
                    }else{
                        quantTmp += mapAlimentQuantite.get(alimTmp);
                        mapAlimentQuantite.put(alimTmp, quantTmp);
                    }
                }
            }
            if(jour.getRepasEncas2()!=null){
                listAlimentRepas = AlimentRepasModel.getAlimentRepasModelByRepas(jour.getRepasEncas2().getId());
                for (AlimentRepasModel alimentRepas : listAlimentRepas) {
                    alimTmp = AlimentModel.getAlimentById(alimentRepas.alimentModel);
                    quantTmp = alimentRepas.quantite.intValue();
                    if(mapAlimentQuantite.get(alimTmp) == null){
                        mapAlimentQuantite.put(alimTmp, alimentRepas.quantite.intValue());
                    }else{
                        quantTmp += mapAlimentQuantite.get(alimTmp);
                        mapAlimentQuantite.put(alimTmp, quantTmp);
                    }
                }
            }
            if(jour.getRepasEncas3()!=null){
                listAlimentRepas = AlimentRepasModel.getAlimentRepasModelByRepas(jour.getRepasEncas3().getId());
                for (AlimentRepasModel alimentRepas : listAlimentRepas) {
                    alimTmp = AlimentModel.getAlimentById(alimentRepas.alimentModel);
                    quantTmp = alimentRepas.quantite.intValue();
                    if(mapAlimentQuantite.get(alimTmp) == null){
                        mapAlimentQuantite.put(alimTmp, alimentRepas.quantite.intValue());
                    }else{
                        quantTmp += mapAlimentQuantite.get(alimTmp);
                        mapAlimentQuantite.put(alimTmp, quantTmp);
                    }
                }
            }
        }

        for (Map.Entry<AlimentModel, Integer> entry : mapAlimentQuantite.entrySet()) {
            AlimentModel alim = entry.getKey();
            Integer quant = entry.getValue();
            String fin = ("Boisson".equalsIgnoreCase(alim.getTypeAliment())) ? "cl" : "gr";
            listAlimentAchat.add(alim.getNom() + " " + quant +" : "+fin);
        }

        alimentAchatListAdapter = new AlimentAchatListAdapter(this, R.layout.adapter_view_alimentachat_layout, listAlimentAchat);
        listAlimentsAchat.setAdapter(alimentAchatListAdapter);

    }
}
