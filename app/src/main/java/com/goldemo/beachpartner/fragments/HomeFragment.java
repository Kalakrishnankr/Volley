package com.goldemo.beachpartner.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.adpters.CardAdapter;
import com.goldemo.beachpartner.adpters.MessageAdapter;
import com.goldemo.beachpartner.adpters.PartnerAdapter;
import com.goldemo.beachpartner.adpters.ProfileAdapter;
import com.goldemo.beachpartner.models.DataModel;
import com.goldemo.beachpartner.models.SingleItemModel;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private ImageView img_bpprofile,img_send,img_received;
    private RecyclerView mRecyclerview,pRecyclerview,msgRecyclerview,parRecyclerview;
    CardAdapter adapter;
    MessageAdapter messageAdapter;
    PartnerAdapter partnerAdapter;
    ProfileAdapter profileAdapter;
    ArrayList<DataModel> allSampleData;
    private TextView txt_head;

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

        img_bpprofile   =   (ImageView) view.findViewById(R.id.img_bpfinder);
        txt_head        =   (TextView)view.findViewById(R.id.txtview_head);

        img_send        =   (ImageView)view.findViewById(R.id.imgview_send);
        img_received    =   (ImageView)view.findViewById(R.id.imgview_received);

        img_send.setOnClickListener(this);
        img_received.setOnClickListener(this);

        SnapHelper snapHelper = new PagerSnapHelper();

        LinearLayoutManager layoutmnger = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
//        pRecyclerview = (RecyclerView) view.findViewById(R.id.rrv_topProfile);//This recycler view for top profile picture
//        profileAdapter = new ProfileAdapter(getContext(),allSampleData);
//        pRecyclerview.setAdapter(profileAdapter);
//        pRecyclerview.setHasFixedSize(true);

        mRecyclerview = (RecyclerView)view.findViewById(R.id.rcv);//This recycler view for upcoming events
        adapter = new CardAdapter(getContext(),allSampleData);
        mRecyclerview.setAdapter(adapter);
        snapHelper.attachToRecyclerView(mRecyclerview);
        mRecyclerview.setLayoutManager(layoutmnger);
        mRecyclerview.setHasFixedSize(true);


        msgRecyclerview =   (RecyclerView)view.findViewById(R.id.rcv_message);
        LinearLayoutManager layoutmngerOne = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        messageAdapter  =  new MessageAdapter(getContext(),allSampleData);
        msgRecyclerview.setAdapter(messageAdapter);
        SnapHelper snapMsg = new PagerSnapHelper();
        snapMsg.attachToRecyclerView(msgRecyclerview);
        msgRecyclerview.setLayoutManager(layoutmngerOne);
        msgRecyclerview.setHasFixedSize(true);


        parRecyclerview =  (RecyclerView)view.findViewById(R.id.rcv_partners);
        LinearLayoutManager layoutmngerTwo = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        partnerAdapter  =   new PartnerAdapter(getContext(),allSampleData);
        parRecyclerview.setAdapter(partnerAdapter);
        SnapHelper snapPartner = new PagerSnapHelper();
        snapPartner.attachToRecyclerView(parRecyclerview);
        parRecyclerview.setLayoutManager(layoutmngerTwo);
        parRecyclerview.setHasFixedSize(true);


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


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.imgview_send:
                txt_head.setText("Tournament Requests Sent");
                break;

            case R.id.imgview_received:
                txt_head.setText("Tournament Requests Received");
                break;

            default:
                break;
        }
    }
}

