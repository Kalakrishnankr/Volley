package com.goldemo.beachpartner.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.adpters.ChatListAdapter;
import com.goldemo.beachpartner.models.PersonModel;

import java.util.ArrayList;


public class MessageFragment extends Fragment {

    private RecyclerView recyclerView;
    private ChatListAdapter chatListAdapter;
    private ArrayList<PersonModel>chatList = new ArrayList<>();
    private ArrayList<PersonModel>list  =   new ArrayList<>();

    public MessageFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_message, container, false);
        list.clear();
        list = getChatList();
        initView(view);
        return view;
    }

    private void initView(View view) {


        recyclerView    =   (RecyclerView) view.findViewById(R.id.rcv_chat);
        chatListAdapter =   new ChatListAdapter(getContext(),list);
        RecyclerView.LayoutManager  mLayoutmanger = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutmanger);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(chatListAdapter);

    }

    private ArrayList<PersonModel> getChatList() {

        chatList.add(new PersonModel("Alivia Orvieto","26",R.drawable.person1));
        chatList.add(new PersonModel("Marti McLaurin","25",R.drawable.person2));
        chatList.add(new PersonModel("Liz Held","30",R.drawable.person3));

        return chatList;

    }


}
