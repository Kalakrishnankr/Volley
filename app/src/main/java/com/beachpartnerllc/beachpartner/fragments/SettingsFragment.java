package com.beachpartnerllc.beachpartner.fragments;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.beachpartnerllc.beachpartner.R;

import java.util.ArrayList;

import io.apptik.widget.MultiSlider;

import static android.content.Context.MODE_PRIVATE;


public class SettingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private TextView tvMin,tvMax,txtv_gender;
    private MultiSlider age_bar;
    private AutoCompleteTextView spinner_location;
    private ToggleButton btnMale,btnFemale;
    private Button btnSave;
    public static final String MY_PREFS_FILTER = "MyPrefsFile";
    ArrayAdapter<String> dataAdapter;
    ArrayList<String> stateList = new ArrayList<>();
    private SharedPreferences prefs;

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

    private void initView(View view) {

        tvMin       = (TextView) view.findViewById(R.id.txtv_minAge);
        tvMax       = (TextView) view.findViewById(R.id.txtv_maxAge);
        age_bar     = (MultiSlider) view.findViewById(R.id.rangebarOne);
        spinner_location = (AutoCompleteTextView) view.findViewById(R.id.spinner_location);

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
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/SanFranciscoTextRegular.ttf");
        spinner_location.setTypeface(font);
        spinner_location.setAdapter(dataAdapter);

        //check shared prefvalue
        prefs = getActivity().getSharedPreferences(MY_PREFS_FILTER, MODE_PRIVATE);

        if (prefs!=null) {

            String location = prefs.getString("location", null);
            String sgender = prefs.getString("gender", null);
            int minAge = prefs.getInt("minAge", 0);
            int maxAge = prefs.getInt("maxAge", 0);


            tvMin.setText(String.valueOf(minAge));
            tvMax.setText(String.valueOf(maxAge));
            age_bar.getThumb(0).setValue(minAge).setEnabled(true);
            age_bar.getThumb(1).setValue(maxAge).setEnabled(true);
            spinner_location.setText(location);

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


                SharedPreferences.Editor preferences = getActivity().getSharedPreferences(MY_PREFS_FILTER, MODE_PRIVATE).edit();
                preferences.putString("location", spinner_location.getText().toString().trim());
                preferences.putInt("minAge", Integer.parseInt(tvMin.getText().toString().trim()));
                preferences.putInt("maxAge", Integer.parseInt(tvMax.getText().toString().trim()));
                preferences.putString("gender", txtv_gender.getText().toString());
                preferences.apply();
                getActivity().onBackPressed();
                preferences.commit();

            }

        });
    }

    public void addLocation() {
        stateList.add("Alabama (AL)");
        stateList.add("Alaska (AK)");
        stateList.add("Alberta (AB)");
        stateList.add("American Samoa (AS)");
        stateList.add("Arizona (AZ)");
        stateList.add("Arkansas (AR)");
        stateList.add("Armed Forces (AE)");
        stateList.add("Armed Forces Americas (AA)");
        stateList.add("Armed Forces Pacific (AP)");
        stateList.add("British Columbia (BC)");
        stateList.add("California (CA)");
        stateList.add("Colorado (CO)");
        stateList.add("Connecticut (CT)");
        stateList.add("Delaware (DE)");
        stateList.add("District Of Columbia (DC)");
        stateList.add("Florida (FL)");
        stateList.add("Georgia (GA)");
        stateList.add("Guam (GU)");
        stateList.add("Hawaii (HI)");
        stateList.add("Idaho (ID)");
        stateList.add("Illinois (IL)");
        stateList.add("Indiana (IN)");
        stateList.add("Iowa (IA)");
        stateList.add("Kansas (KS)");
        stateList.add("Kentucky (KY)");
        stateList.add("Louisiana (LA)");
        stateList.add("Maine (ME)");
        stateList.add("Manitoba (MB)");
        stateList.add("Maryland (MD)");
        stateList.add("Massachusetts (MA)");
        stateList.add("Michigan (MI)");
        stateList.add("Minnesota (MN)");
        stateList.add("Mississippi (MS)");
        stateList.add("Missouri (MO)");
        stateList.add("Montana (MT)");
        stateList.add("Nebraska (NE)");
        stateList.add("Nevada (NV)");
        stateList.add("New Brunswick (NB)");
        stateList.add("New Hampshire (NH)");
        stateList.add("New Jersey (NJ)");
        stateList.add("New Mexico (NM)");
        stateList.add("New York (NY)");
        stateList.add("Newfoundland (NF)");
        stateList.add("North Carolina (NC)");
        stateList.add("North Dakota (ND)");
        stateList.add("Northwest Territories (NT)");
        stateList.add("Nova Scotia (NS)");
        stateList.add("Nunavut (NU)");
        stateList.add("Ohio (OH)");
        stateList.add("Oklahoma (OK)");
        stateList.add("Ontario (ON)");
        stateList.add("Oregon (OR)");
        stateList.add("Pennsylvania (PA)");
        stateList.add("Prince Edward Island (PE)");
        stateList.add("Puerto Rico (PR)");
        stateList.add("Quebec (QC)");
        stateList.add("Rhode Island (RI)");
        stateList.add("Saskatchewan (SK)");
        stateList.add("South Carolina (SC)");
        stateList.add("South Dakota (SD)");
        stateList.add("Tennessee (TN)");
        stateList.add("Texas (TX)");
        stateList.add("Utah (UT)");
        stateList.add("Vermont (VT)");
        stateList.add("Virgin Islands (VI)");
        stateList.add("Virginia (VA)");
        stateList.add("Washington (WA)");
        stateList.add("West Virginia (WV)");
        stateList.add("Wisconsin (WI)");
        stateList.add("Wyoming (WY)");
        stateList.add("Yukon Territory (YT)");

    }






    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
