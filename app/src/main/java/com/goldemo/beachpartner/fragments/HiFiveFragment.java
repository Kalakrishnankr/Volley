package com.goldemo.beachpartner.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.adpters.HiFiveAdapter;
import com.goldemo.beachpartner.models.HighFiveModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HiFiveFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HiFiveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HiFiveFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ArrayList<HighFiveModel> hiFiveList = new ArrayList<HighFiveModel>();
    ListView listView;

    public HiFiveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HiFiveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HiFiveFragment newInstance(String param1, String param2) {
        HiFiveFragment fragment = new HiFiveFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_hi_five, container, false);
        initList();
        listView =  view.findViewById(R.id.listViewHiFives);
        HiFiveAdapter hiFiveAdapter = new HiFiveAdapter( hiFiveList,getContext());
        listView.setAdapter(hiFiveAdapter);
        return view;
    }
    private void initList() {
        // We populate the planets



        hiFiveList.add(new HighFiveModel("Alivia Orvieto","http://seqato.com/bp/images/1.jpg"));
        hiFiveList.add(new HighFiveModel("Marti McLaurin","http://seqato.com/bp/images/2.jpg"));
        hiFiveList.add(new HighFiveModel("Liz Held","http://seqato.com/bp/images/3.jpg"));
        hiFiveList.add(new HighFiveModel("Alivia Orvieto","http://seqato.com/bp/images/4.jpg"));
        hiFiveList.add(new HighFiveModel("Marti McLaurin","http://seqato.com/bp/images/5.jpg"));
        hiFiveList.add(new HighFiveModel("Liz Held","http://seqato.com/bp/images/6.jpg"));
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
