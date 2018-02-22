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
import com.goldemo.beachpartner.adpters.CardAdapter;
import com.goldemo.beachpartner.adpters.ProfileAdapter;
import com.goldemo.beachpartner.models.DataModel;
import com.goldemo.beachpartner.models.SingleItemModel;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private ImageView img_bpprofile;
    private RecyclerView mRecyclerview,pRecyclerview;
    CardAdapter adapter;
    ProfileAdapter profileAdapter;
    ArrayList<DataModel> allSampleData;

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

        allSampleData = new ArrayList<DataModel>();
        createDummyData();

        img_bpprofile = (ImageView) view.findViewById(R.id.img_bpfinder);
        LinearLayoutManager layoutmnger = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        pRecyclerview = (RecyclerView) view.findViewById(R.id.rrv_topProfile);//This recycler view for top profile picture
        profileAdapter = new ProfileAdapter(getContext(),allSampleData);
        pRecyclerview.setAdapter(profileAdapter);
        pRecyclerview.setHasFixedSize(true);

        mRecyclerview = (RecyclerView)view.findViewById(R.id.rcv);//This recycler view for upcoming events
        adapter = new CardAdapter(getContext(),allSampleData);
        mRecyclerview.setAdapter(adapter);
        mRecyclerview.setLayoutManager(layoutmnger);
        mRecyclerview.setHasFixedSize(true);


    }

    public void createDummyData() {
        for (int i = 1; i <= 5; i++) {

            DataModel dm = new DataModel();

            dm.setHeaderTitle("Section " + i);

            ArrayList<SingleItemModel> singleItem = new ArrayList<SingleItemModel>();
            for (int j = 0; j <= 5; j++) {
                singleItem.add(new SingleItemModel("Item " + j, "URL " + j));
            }

            dm.setAllItemsInSection(singleItem);

            allSampleData.add(dm);

        }
    }


}

