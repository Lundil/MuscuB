package com.example.muscu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.muscu.R;
import com.example.muscu.model.JourModel;
import com.example.muscu.model.RepasModel;

import java.util.List;

public class JourListAdapter extends ArrayAdapter<JourModel> {

    private static final String TAG = "JourListAdapter";

    private Context mContext;
    int mResource;

    public JourListAdapter(Context context, int resource, List<JourModel> objects) {
        super(context, resource, objects);
        mContext=context;
        mResource = resource;
    }
    public View getView(int position, View convertView, ViewGroup parent){

        String nom = getItem(position).nom;
        RepasModel matin = getItem(position).repasMatin;
        RepasModel midi = getItem(position).repasMidi;
        RepasModel soir = getItem(position).repasDiner;

        JourModel jourModel = new JourModel();
        jourModel.setRepasMatin(matin);
        jourModel.setRepasMidi(midi);
        jourModel.setRepasDiner(soir);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvNom = (TextView) convertView.findViewById(R.id.textView_name);
        TextView tvMatin = (TextView) convertView.findViewById(R.id.textView_matin);
        TextView tvMidi = (TextView) convertView.findViewById(R.id.textView_midi);
        TextView tvSoir = (TextView) convertView.findViewById(R.id.textView_soir);

        tvNom.setText(nom);
        tvMatin.setText(jourModel.getRepasMatin().nom);
        tvMidi.setText(jourModel.getRepasMidi().nom);
        tvSoir.setText(jourModel.getRepasDiner().nom);

        return convertView;
    }
}
