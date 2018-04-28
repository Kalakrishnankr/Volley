package com.beachpartnerllc.beachpartner.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.activity.TabActivity;
import com.beachpartnerllc.beachpartner.connections.PrefManager;

import java.util.ArrayList;

import io.apptik.widget.MultiSlider;


public class SettingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private TextView tvMin,tvMax,txtv_gender;
    private MultiSlider age_bar;
    private Spinner spinner_location;
    private ToggleButton btnMale,btnFemale;
    private Button btnSave;
    ArrayAdapter<String> dataAdapter;
    ArrayList<String> stateList = new ArrayList<>();
    private SharedPreferences prefs;
    private String location,sgender;
    private int minAge,maxAge;
    private TabActivity tabActivity;

    public SettingsFragment() {
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
        View view= inflater.inflate(R.layout.fragment_settings, container, false);
        initView(view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() instanceof TabActivity){
            tabActivity = (TabActivity)getActivity();
            tabActivity.setActionBarTitle("Settings");
        }
    }



    private void initView(View view) {

        tvMin       = (TextView) view.findViewById(R.id.txtv_minAge);
        tvMax       = (TextView) view.findViewById(R.id.txtv_maxAge);
        age_bar     = (MultiSlider) view.findViewById(R.id.rangebarOne);
        spinner_location = (Spinner) view.findViewById(R.id.spinner_location_settings);


        txtv_gender = (TextView) view.findViewById(R.id.txtv_gender);

        btnMale     = (ToggleButton) view.findViewById(R.id.btnMen);
        btnFemale   = (ToggleButton) view.findViewById(R.id.btnWomen);
        btnSave     = (Button) view.findViewById(R.id.saveSettings);

        btnFemale.setText("Women");
        btnMale.setText("Men");
        btnFemale.setTextOff("Women");
        btnMale.setTextOff("Men");
        btnFemale.setTextOn("Women");
        btnMale.setTextOn("Men");

        addLocation();


        dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, stateList);
        spinner_location.setAdapter(dataAdapter);
        spinner_location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                location = spinner_location.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        //check shared prefvalue
        prefs = new PrefManager(getActivity()).getSettingsData();
        if (prefs != null) {
            location = prefs.getString("location", null);
            if (location != null){
                int positions = dataAdapter.getPosition(location);
                spinner_location.setSelection(positions);
            }
            sgender = prefs.getString("gender", null);
            minAge = prefs.getInt("minAge", 0);
            maxAge = prefs.getInt("maxAge", 0);
            if (minAge == 0 && maxAge == 0) {
                minAge=5;
                maxAge=30;
                age_bar.getThumb(0).setValue(minAge).setEnabled(true);
                age_bar.getThumb(1).setValue(maxAge).setEnabled(true);
                tvMin.setText(String.valueOf(minAge));
                tvMax.setText(String.valueOf(maxAge));


            }else {
                age_bar.getThumb(0).setValue(minAge).setEnabled(true);
                age_bar.getThumb(1).setValue(maxAge).setEnabled(true);
                tvMin.setText(String.valueOf(minAge));
                tvMax.setText(String.valueOf(maxAge));
            }
           // spinner_location.setText(location);

            if (sgender != null) {
                if (sgender.equals("Male")) {
                    txtv_gender.setText("Male");
                    btnMale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnMale.setTextColor(getResources().getColor(R.color.white));
                    btnMale.setChecked(true);
                } else if (sgender.equals("Female")) {
                    txtv_gender.setText("Female");
                    btnFemale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnFemale.setTextColor(getResources().getColor(R.color.white));
                    btnFemale.setChecked(true);
                } else {
                    btnMale.setChecked(true);
                    btnFemale.setChecked(true);
                    txtv_gender.setText("Both");
                    btnMale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnMale.setTextColor(getResources().getColor(R.color.white));
                    btnFemale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnFemale.setTextColor(getResources().getColor(R.color.white));
                }
            }

        }

        //age range bar
        age_bar.setOnThumbValueChangeListener(new MultiSlider.OnThumbValueChangeListener() {
            @Override
            public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {

                if (thumbIndex == 0) {
                    if (value < 5) {
                        tvMin.setText("5");

                    }else {
                        tvMin.setText(String.valueOf(value));
                    }
                } else {
                    if (5 > value) {
                        thumb.setValue(30);
                        tvMax.setText("30");
                    }else {
                        tvMax.setText(String.valueOf(value));
                    }

                }

            }
        });


        // attach click listener to folding cell


        //button Men
        btnMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btnFemale.isChecked() && isChecked) {
                    txtv_gender.setText("Both");
                    btnFemale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnMale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnFemale.setTextColor(getResources().getColor(R.color.white));
                    btnMale.setTextColor(getResources().getColor(R.color.white));
                } else if (isChecked) {
                    txtv_gender.setText("Male");
                    btnMale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnMale.setTextColor(getResources().getColor(R.color.white));
                } else if (!isChecked) {
                    txtv_gender.setText("Female");
                    btnMale.setBackground(getResources().getDrawable(R.color.imgBacgnd));
                    btnMale.setTextColor(getResources().getColor(R.color.black));
                    btnFemale.setChecked(true);
                    btnFemale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnFemale.setTextColor(getResources().getColor(R.color.white));
                }


            }
        });

        //button Women
        btnFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btnMale.isChecked() && isChecked) {
                    txtv_gender.setText("Both");
                    btnFemale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnMale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnFemale.setTextColor(getResources().getColor(R.color.white));
                    btnMale.setTextColor(getResources().getColor(R.color.white));
                } else if (isChecked) {
                    txtv_gender.setText("Female");
                    btnFemale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnFemale.setTextColor(getResources().getColor(R.color.white));
                } else if (!isChecked) {
                    txtv_gender.setText("Male");
                    btnFemale.setBackground(getResources().getDrawable(R.color.imgBacgnd));
                    btnFemale.setTextColor(getResources().getColor(R.color.black));
                    btnMale.setChecked(true);
                    btnMale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnMale.setTextColor(getResources().getColor(R.color.white));
                }


            }
        });




        //add data to shared preference
        //play button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //location    =   spinner_location.getText().toString().trim();
                sgender     =   txtv_gender.getText().toString();
                minAge      =   Integer.parseInt(tvMin.getText().toString().trim());
                maxAge      =   Integer.parseInt(tvMax.getText().toString().trim());

                new PrefManager(getActivity()).saveSettingData(location,sgender,false,minAge,maxAge);
                getActivity().onBackPressed();

            }

        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        new PrefManager(getActivity()).saveSettingData(location,sgender,false,minAge,maxAge);
    }

    public void addLocation() {
        stateList.add("Alabama");
        stateList.add("Alaska");
        stateList.add("Arizona");
        stateList.add("Arkansas");
        stateList.add("California");
        stateList.add("Colorado");
        stateList.add("Connecticut");
        stateList.add("Delaware");
        stateList.add("Florida");
        stateList.add("Georgia");
        stateList.add("Hawaii");
        stateList.add("Idaho");
        stateList.add("Illinois");
        stateList.add("Indiana");
        stateList.add("Iowa");
        stateList.add("Kansas");
        stateList.add("Kentucky");
        stateList.add("Louisiana");
        stateList.add("Maine");
        stateList.add("Maryland");
        stateList.add("Massachusetts");
        stateList.add("Michigan");
        stateList.add("Minnesota");
        stateList.add("Mississippi");
        stateList.add("Missouri");
        stateList.add("Montana");
        stateList.add("Nebraska");
        stateList.add("Nevada");
        stateList.add("New Hampshire");
        stateList.add("New Jersey");
        stateList.add("New Mexico");
        stateList.add("New York");
        stateList.add("North Carolina");
        stateList.add("North Dakota");
        stateList.add("Ohio");
        stateList.add("Oklahoma");
        stateList.add("Oregon");
        stateList.add("Pennsylvania");
        stateList.add("Rhode Island");
        stateList.add("South Carolina");
        stateList.add("South Dakota");
        stateList.add("Tennessee");
        stateList.add("Texas");
        stateList.add("Utah");
        stateList.add("Vermont");
        stateList.add("Virginia");
        stateList.add("Washington");
        stateList.add("West Virginia");
        stateList.add("Wisconsin WI");
        stateList.add("Wyoming WY");
    }

}
