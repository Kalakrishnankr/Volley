package com.beachpartnerllc.beachpartner.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.beachpartnerllc.beachpartner.R;


public class BasicInfoFragment extends Fragment  {


    private EditText editFname,editLname,editGender,editDob,editCity,editPhone,editPassword;
    private Button btnSave,btnCancel;

    public BasicInfoFragment() {

        // Required empty public constructor
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        //Your callback initialization here
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BasicInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BasicInfoFragment newInstance(String param1, String param2) {
        BasicInfoFragment fragment = new BasicInfoFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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
        View view=inflater.inflate(R.layout.fragment_basic_info, container, false);
        initActivity(view);
        return view;
    }

    private void initActivity(View view) {

        editFname       =   (EditText)view.findViewById(R.id.txtvFname);
        editLname       =   (EditText)view.findViewById(R.id.txtvLname);
        editGender      =   (EditText)view.findViewById(R.id.txtv_gender);
        editDob         =   (EditText)view.findViewById(R.id.txtv_dob);
        editCity        =   (EditText)view.findViewById(R.id.txtv_city);
        editPhone       =   (EditText)view.findViewById(R.id.txtv_mobileno);
        editPassword    =   (EditText)view.findViewById(R.id.txtv_password);

        btnSave         =   (Button)view.findViewById(R.id.btnsave);
        btnCancel       =   (Button)view.findViewById(R.id.btncancel);
    }



   /* @Override
    public void click() {




        editFname.setFocusable(true);
        editLname.setFocusable(true);
        editGender.setFocusable(true);
        editDob.setFocusable(true);
        editCity.setFocusable(true);
        editPhone.setFocusable(true);
        editPassword.setFocusable(true);
    }*/

    // TODO: Rename method, update argument and hook method into UI event








}
