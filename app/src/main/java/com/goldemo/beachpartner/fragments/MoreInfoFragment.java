package com.goldemo.beachpartner.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.goldemo.beachpartner.OnClickListener;
import com.goldemo.beachpartner.R;


public class MoreInfoFragment extends Fragment implements OnClickListener {

    private EditText editExp,editPref,editPos,editHeight,editIntrest,editPlayed,editHighest,editCBVANo,editCBVAFName,editCBVALName,editWtoTravel,editHighschool,editIndoorClub,editColgClub,editColgBeach,editColgIndoor,editPoints,editYouLinks,editBioLinks,editRank;
    private Button btnSave,btnCancel;
    private OnClickListener mclickListenerOn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        //Your callback initialization here
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_more_info, container, false);
        initActivity(view);
        return view;
    }

    private void initActivity(View view) {

        editExp         =   (EditText)view.findViewById(R.id.txtvExp);
        editPref        =   (EditText)view.findViewById(R.id.txtvPref);
        editPos         =   (EditText)view.findViewById(R.id.txtvPos);
        editHeight      =   (EditText)view.findViewById(R.id.txtvHeight);
        editIntrest     =   (EditText)view.findViewById(R.id.txtvIntrest);
        editPlayed      =   (EditText)view.findViewById(R.id.txtvPlayed);
        editHighest     =   (EditText)view.findViewById(R.id.txtvHighest);
        editCBVANo      =   (EditText)view.findViewById(R.id.txtvCBVANo);
        editCBVAFName   =   (EditText)view.findViewById(R.id.txtvCBVAFName);
        editCBVALName   =   (EditText)view.findViewById(R.id.txtvCBVALName);
        editWtoTravel   =   (EditText)view.findViewById(R.id.txtvWtoTravel);
        editHighschool  =   (EditText)view.findViewById(R.id.txtvHighschool);
        editIndoorClub  =   (EditText)view.findViewById(R.id.txtvIndoorClub);
        editColgClub    =   (EditText)view.findViewById(R.id.txtvColgClub);
        editColgBeach   =   (EditText)view.findViewById(R.id.txtvColgBeach);
        editColgIndoor  =   (EditText)view.findViewById(R.id.txtvColgIndoor);
        editPoints      =   (EditText)view.findViewById(R.id.txtvPoints);
        editYouLinks    =   (EditText)view.findViewById(R.id.txtvYouLinks);
        editBioLinks    =   (EditText)view.findViewById(R.id.txtvBioLinks);
        editRank        =   (EditText)view.findViewById(R.id.txtvRank);

        btnSave         =   (Button)view.findViewById(R.id.btn_save);
        btnCancel       =   (Button)view.findViewById(R.id.btn_cancel);
    }



    @Override
    public void click() {
        editExp.setFocusable(true);
        editPref.setFocusable(true);

    }
}
