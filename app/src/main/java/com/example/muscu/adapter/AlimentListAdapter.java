package com.example.muscu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.muscu.R;
import com.example.muscu.model.AlimentModel;

import java.util.List;

public class AlimentListAdapter extends ArrayAdapter<AlimentModel> {

    private static final String TAG = "AlimentsListAdapter";

    private Context mContext;
    int mResource;

    public AlimentListAdapter(Context context, int resource, List<AlimentModel> objects) {
        super(context, resource, objects);
        mContext=context;
        mResource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        String nom = getItem(position).nom;
        String proteine = getItem(position).proteine.toString();
        String lipide = getItem(position).lipide.toString();
        String glucide = getItem(position).glucide.toString();

        AlimentModel alimentModel = new AlimentModel();
        alimentModel.setNom(nom);
        alimentModel.setProteine(Double.parseDouble(proteine));
        alimentModel.setLipide(Double.parseDouble(lipide));
        alimentModel.setGlucide(Double.parseDouble(glucide));
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvNom = (TextView) convertView.findViewById(R.id.textView_name);
        TextView tvProteine = (TextView) convertView.findViewById(R.id.textView_protein);
        TextView tvLipide = (TextView) convertView.findViewById(R.id.textView_lipid);
        TextView tvGlucide = (TextView) convertView.findViewById(R.id.textView_glucid);

        tvNom.setText(nom);
        tvProteine.setText("Proteines pour 100gr   :  "+proteine);
        tvLipide.setText("Lipides pour 100gr       :  "+lipide);
        tvGlucide.setText("Glucides pour 100gr    :  "+glucide);

        return convertView;
    }

}
