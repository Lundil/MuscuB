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

    private static final String TAG = "RepasListAdapter";

    private Context mContext;
    int mResource;

    public JourListAdapter(Context context, int resource, List<JourModel> objects) {
        super(context, resource, objects);
        mContext=context;
        mResource = resource;
    }
    public View getView(int position, View convertView, ViewGroup parent){

        String nom = getItem(position).nom;
        List<RepasModel> listRepas = getItem(position).collations;

        JourModel jourModel = new JourModel();
        jourModel.setNom(nom);
        jourModel.setCollations(listRepas);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvNom = (TextView) convertView.findViewById(R.id.textView_name);

        tvNom.setText(nom);

        return convertView;
    }
}
