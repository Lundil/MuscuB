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

public class AlimentAchatListAdapter extends ArrayAdapter<String> {

    private static final String TAG = "AlimentAchatListAdapter";

    private Context mContext;
    int mResource;

    public AlimentAchatListAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        mContext=context;
        mResource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        String nom = getItem(position).toString();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvNom = (TextView) convertView.findViewById(R.id.textView_name);

        tvNom.setText(nom);

        return convertView;
    }


}
