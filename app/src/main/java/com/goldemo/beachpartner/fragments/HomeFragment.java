package com.goldemo.beachpartner.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.goldemo.beachpartner.R;


public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private ImageView img_bpprofile;
    private RecyclerView mRecyclerview;


    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View  view= inflater.inflate(R.layout.fragment_home, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {

        img_bpprofile = (ImageView) view.findViewById(R.id.img_bpfinder);
        LinearLayoutManager layoutmnger = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        mRecyclerview = (RecyclerView)view.findViewById(R.id.rcv);
        mRecyclerview.setLayoutManager(layoutmnger);
    }


}
