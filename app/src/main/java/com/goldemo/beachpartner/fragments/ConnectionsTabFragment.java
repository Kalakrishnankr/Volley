package com.goldemo.beachpartner.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.goldemo.beachpartner.R;


public class ConnectionsTabFragment extends Fragment {



    public ConnectionsTabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConnectionsTabFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConnectionsTabFragment newInstance(String param1, String param2) {
        ConnectionsTabFragment fragment = new ConnectionsTabFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_connections_tab, container, false);
        initActivity(view);
        return view;
    }

    private void initActivity(View view) {
        Button btnExpand    =   (Button)view.findViewById(R.id.btnExpand);
        final LinearLayout  linearLayout =    (LinearLayout)view.findViewById(R.id.llBottom);
        btnExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                linearLayout.setVisibility(View.VISIBLE);

            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }




}
