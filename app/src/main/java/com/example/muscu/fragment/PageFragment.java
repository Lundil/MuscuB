package com.example.muscu.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.muscu.R;
import com.example.muscu.adapter.AlimentListAdapter;
import com.example.muscu.model.AlimentModel;

import java.util.List;

public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private boolean refresh=true;
    private ListView listView;
    private List<AlimentModel> alimentModelList;
    private AlimentListAdapter alimentListAdapter;

    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        if(refresh){
            refresh=false;
            listView = getActivity().findViewById(R.id.listAliments);
            mPage = getArguments().getInt(ARG_PAGE);
            if(mPage == 1){
                alimentModelList = AlimentModel.getAlimentsByIsMatin(true);
            }else if(mPage == 2){
                alimentModelList = AlimentModel.getAlimentsByIsMidi(true);
            }else if(mPage == 3){
                alimentModelList = AlimentModel.getAlimentsByIsDiner(true);
            }else if(mPage == 4){
                alimentModelList = AlimentModel.getAlimentsByIsEncas(true);
            }
            if(alimentModelList != null){
                alimentListAdapter = new AlimentListAdapter(getContext(), R.layout.adapter_view_aliment_layout, alimentModelList);
            }

            if(alimentListAdapter!=null){
                listView.setAdapter(alimentListAdapter);
            }
        }else{
            refresh=true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        TextView textView = (TextView) view;
        listView = getActivity().findViewById(R.id.listAliments);
        return view;
    }
}
