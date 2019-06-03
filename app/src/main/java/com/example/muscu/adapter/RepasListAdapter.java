package com.example.muscu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.muscu.R;
import com.example.muscu.model.AlimentModel;
import com.example.muscu.model.RepasModel;

import java.util.List;

public class RepasListAdapter extends ArrayAdapter<RepasModel> {

    private static final String TAG = "RepasListAdapter";

    private Context mContext;
    int mResource;

    public RepasListAdapter(Context context, int resource, List<RepasModel> objects) {
        super(context, resource, objects);
        mContext=context;
        mResource = resource;
    }
    public View getView(int position, View convertView, ViewGroup parent){

        String nom = getItem(position).nom;
        String typeRepas = getItem(position).typeRepas;
        Double proteineTotal = getItem(position).proteineTotal;
        Double glucideTotal = getItem(position).glucideTotal;
        Double lipideTotal = getItem(position).lipideTotal;
        List<AlimentModel> alimentModels = getItem(position).alimentModels;

        RepasModel repasModel = new RepasModel(nom, typeRepas, proteineTotal, glucideTotal, lipideTotal, alimentModels);

        /*LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvNom = (TextView) convertView.findViewById(R.id.textView_name);
        TextView tvProteine = (TextView) convertView.findViewById(R.id.textView_protein);
        TextView tvLipide = (TextView) convertView.findViewById(R.id.textView_lipid);
        TextView tvGlucide = (TextView) convertView.findViewById(R.id.textView_glucid);

        tvNom.setText(nom);
        tvProteine.setText("Proteines pour 100gr   :  "+proteine);
        tvLipide.setText("Lipides pour 100gr       :  "+lipide);
        tvGlucide.setText("Glucides pour 100gr    :  "+glucide);*/

        return convertView;
    }
}
