package com.goldemo.beachpartner.fragments;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.goldemo.beachpartner.MyInterface;
import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.adpters.TouristSpotCardAdapter;
import com.goldemo.beachpartner.calendar.compactcalendarview.CompactCalendarView;
import com.goldemo.beachpartner.cardstackview.CardStackView;
import com.goldemo.beachpartner.cardstackview.SwipeDirection;
import com.goldemo.beachpartner.connections.ApiService;
import com.goldemo.beachpartner.connections.PrefManager;
import com.goldemo.beachpartner.models.BpFinderModel;
import com.goldemo.beachpartner.models.PersonModel;
import com.ramotion.foldingcell.FoldingCell;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.apptik.widget.MultiSlider;

import static android.content.Context.MODE_PRIVATE;


public class BPFinderFragment extends Fragment implements MyInterface {


    private ProgressBar progressBar;
    private CardStackView cardStackView;
    private TouristSpotCardAdapter adapter;
    private RelativeLayout rr;
    private CoordinatorLayout llv;
    private ImageView imgv_profilepic,imgv_rvsecard,imgv_location,imgv_highfi,btnPlay;
    private TextView tvmonth,tvMin,tvMax,txtv_gender;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    private AutoCompleteTextView spinner_location;
    private MultiSlider age_bar;

    public ToggleButton btnMale,btnFemale;
    private FoldingCell fc;
    private LinearLayout llvFilter;
    ArrayAdapter<String> dataAdapter;
    private ImageButton showPreviousMonthButton,showNextMonthButton;
    private Switch sCoach;
    private LinearLayout topFrameLayout;

    public static final String MY_PREFS_FILTER = "MyPrefsFile";
    ArrayList<String>stateList = new ArrayList<>();
    private RelativeLayout rrvBottom;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    public boolean reverseCount=false;
    private boolean isbpActive =false;
    private View view;
    private SharedPreferences prefs;
    private String location,sgender;
    private Boolean isCoach;
    private int minAge,maxAge;
    private String token,user_id,user_subscription,reqPersonId,deviceId;
    private List<BpFinderModel>allCardList = new ArrayList<>();
    private List<PersonModel>bluebpList = new ArrayList<>();

    public BPFinderFragment() {
    }

    @SuppressLint("ValidFragment")
    public BPFinderFragment(boolean isBPActive) {
        isbpActive=isBPActive;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(isbpActive){
            view =inflater.inflate(R.layout.fragment_bpfinder, container, false);

        }else {
            view =inflater.inflate(R.layout.fragment_bpfinder1, container, false);

        }
        token   =   new PrefManager(getContext()).getToken();
        user_id =   new PrefManager(getContext()).getUserId();
        user_subscription = new PrefManager(getContext()).getSubscription();

        //

        setUp(view);
        btnFemale.setText("Women");
        btnMale.setText("Men");
        btnFemale.setTextOff("Women");
        btnMale.setTextOff("Men");
        btnFemale.setTextOn("Women");
        btnMale.setTextOn("Men");
        reload();

        Bundle data = getArguments();
        if(data!=null){
            bluebpList = data.getParcelableArrayList("bluebplist");
            int cPosition = data.getInt("cPosition");
            for(int i=0;i<bluebpList.size();i++){
                if(bluebpList.get(i)==bluebpList.get(cPosition)){
                    //set bluebp to card
                    //Toast.makeText(getActivity(), "Set value to cards", Toast.LENGTH_SHORT).show();

                }
            }
        }

        return view;
    }

    public void setUp(final View view) {

        progressBar         =   (ProgressBar) view.findViewById(R.id.activity_main_progress_bar);
        rr                  =   (RelativeLayout) view.findViewById(R.id.rr);
        llv                 =   (CoordinatorLayout)view.findViewById(R.id.llMoreinfo);
        imgv_profilepic     =   (ImageView)view.findViewById(R.id.img_profile);

        cardStackView       =   (CardStackView) view.findViewById(R.id.activity_main_card_stack_view);
        tvmonth             =   (TextView) view.findViewById(R.id.month_name);
        ImageView toggle    =   (ImageView) view.findViewById(R.id.toggle);
        rrvBottom           =   (RelativeLayout) view.findViewById(R.id.rrv_bottomMenus);

        //Layout for filters

        llvFilter           =   (LinearLayout) view.findViewById(R.id.llFilter);
        tvMin               =   (TextView) view.findViewById(R.id.txtv_minAge);
        tvMax               =   (TextView) view.findViewById(R.id.txtv_maxAge);
        age_bar             =   (MultiSlider)  view.findViewById(R.id.rangebar);
        spinner_location    =   (AutoCompleteTextView) view.findViewById(R.id.spinner_location);

        txtv_gender         =   (TextView) view.findViewById(R.id.txtv_gender);

        btnMale             =   (ToggleButton) view.findViewById(R.id.btnMen);
        btnFemale           =   (ToggleButton) view.findViewById(R.id.btnWomen);
        btnPlay             =   (ImageView)view.findViewById(R.id.imgPlay);
        fc                  =   (FoldingCell)view. findViewById(R.id.folding_cell);
        topFrameLayout      =   (LinearLayout)view.findViewById(R.id.frmeOne);



        showPreviousMonthButton = (ImageButton) view.findViewById(R.id.prev_button);
        showNextMonthButton = (ImageButton) view.findViewById(R.id.next_button);

        sCoach              =   (Switch) view.findViewById(R.id.swich_coach);

        final CompactCalendarView compactCalendarView = (CompactCalendarView) view.findViewById(R.id.compactcalendar_view);
        compactCalendarView.setFirstDayOfWeek(Calendar.SUNDAY);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        collapsingToolbarLayout = (CollapsingToolbarLayout)view. findViewById(R.id.CollapsingToolbarLayout1);

        imgv_rvsecard   =   (ImageView) view.findViewById(R.id.ic_rvsecard);
        imgv_highfi     =   (ImageView) view.findViewById(R.id.ic_high);
        imgv_location   =   (ImageView) view.findViewById(R.id.ic_location);


        addLocation();

        showPreviousMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.showPreviousMonth();
            }
        });

        showNextMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.showNextMonth();
            }
        });

        //choose Location

        dataAdapter     = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, stateList);
        Typeface font   = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/SanFranciscoTextRegular.ttf");
        spinner_location.setTypeface(font);
        spinner_location.setAdapter(dataAdapter);

        //check shared prefvalue
        prefs = getActivity().getSharedPreferences(MY_PREFS_FILTER, MODE_PRIVATE);

        if(prefs!=null){

            location  =   prefs.getString("location",null);
            sgender   =   prefs.getString("gender",null);
            isCoach   =   prefs.getBoolean("isCoachActive",false);
            minAge    =   prefs.getInt("minAge",0);
            maxAge    =   prefs.getInt("maxAge",0);


            tvMin.setText(String.valueOf(minAge));
            tvMax.setText(String.valueOf(maxAge));
            age_bar.getThumb(0).setValue(minAge).setEnabled(true);
            age_bar.getThumb(1).setValue(maxAge).setEnabled(true);
            spinner_location.setText(location);
            sCoach.setChecked(isCoach);

            if(sgender!=null){
                if(sgender.equals("Men")){
                    txtv_gender.setText("Men");
                    btnMale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnMale.setTextColor(getResources().getColor(R.color.white));
                    btnMale.setChecked(true);
                }else if(sgender.equals("Women")){
                    txtv_gender.setText("Women");
                    btnFemale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnFemale.setTextColor(getResources().getColor(R.color.white));
                    btnFemale.setChecked(true);
                }else {
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
                    tvMin.setText(String.valueOf(value));
                } else {
                    tvMax.setText(String.valueOf(value));
                }

            }
        });


        // attach click listener to folding cell
        fc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fc.toggle(false);
            }
        });


        //button Men
        btnMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(btnFemale.isChecked()&&isChecked){
                    txtv_gender.setText("Both");
                    btnFemale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnMale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnFemale.setTextColor(getResources().getColor(R.color.white));
                    btnMale.setTextColor(getResources().getColor(R.color.white));
                }
                else if(isChecked){
                    txtv_gender.setText("Men");
                    btnMale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnMale.setTextColor(getResources().getColor(R.color.white));
                }
                else if(!isChecked ){
                    txtv_gender.setText("Women");
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
                if(btnMale.isChecked()&&isChecked){
                    txtv_gender.setText("Both");
                    btnFemale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnMale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnFemale.setTextColor(getResources().getColor(R.color.white));
                    btnMale.setTextColor(getResources().getColor(R.color.white));
                }
                else if(isChecked){
                    txtv_gender.setText("Women");
                    btnFemale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnFemale.setTextColor(getResources().getColor(R.color.white));
                }
                else if(!isChecked ){
                    txtv_gender.setText("Men");
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
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                location    =   spinner_location.getText().toString().trim();
                sgender     =   txtv_gender.getText().toString();
                isCoach     =   sCoach.isChecked();
                minAge      =   Integer.parseInt(tvMin.getText().toString().trim());
                maxAge      =   Integer.parseInt(tvMax.getText().toString().trim());
                if(sgender.equals("Both")){
                    sgender="";
                }
                SharedPreferences.Editor preferences = getActivity().getSharedPreferences(MY_PREFS_FILTER, MODE_PRIVATE).edit();
                preferences.putString("location",spinner_location.getText().toString().trim());
                preferences.putInt("minAge", Integer.parseInt(tvMin.getText().toString().trim()));
                preferences.putInt("maxAge", Integer.parseInt(tvMax.getText().toString().trim()));
                preferences.putString("gender",sgender);
                preferences.putBoolean("isCoachActive",sCoach.isChecked());
                preferences.apply();
                preferences.commit();
                llvFilter.setVisibility(View.GONE);
                rr.setVisibility(View.VISIBLE);
                rrvBottom.setVisibility(View.VISIBLE);

                //check top bp strip **if its active only in bpfinder page(ie,BPFinder Fragment),it's disabled on calendar find Partner page
                if(isbpActive){
                    topFrameLayout.setVisibility(View.GONE);

                }else {
                    topFrameLayout.setVisibility(View.VISIBLE);
                }

                getAllCards(location,sgender,isCoach,minAge,maxAge);
            }
        });


        //Methods for card swipe

        cardStackView.setCardEventListener(new CardStackView.CardEventListener() {
            @Override
            public void onCardDragging(float percentX, float percentY) {
                Log.d("CardStackView", "onCardDragging");
            }

            @Override
            public void onCardSwiped(SwipeDirection direction) {
                //abraham 08-03-2018
                reverseCount=true;
                imgv_rvsecard.setBackground(getResources().getDrawable(R.drawable.ic_backcard));
                Log.d("CardStackView", "onCardSwiped: " + direction.toString());
                Log.d("CardStackView", "topIndex: " + cardStackView.getTopIndex());

                //Methods for swipe card kalakrishnan 06/04/2018
                if(direction.toString().equals("Right")){
                    //Toast.makeText(getActivity(), "You right swiped :"+reqPersonId, Toast.LENGTH_SHORT).show();
                    //Api for Right swipe/like
                    cardRightSwiped(reqPersonId);

                }else if(direction.toString().equals("Left")){
                    //Toast.makeText(getActivity(), "You Left swiped", Toast.LENGTH_SHORT).show();
                    cardLeftSwiped(reqPersonId);

                }else {
                    //Toast.makeText(getActivity(), "HIFI", Toast.LENGTH_SHORT).show();
                    cardHifiSwiped(reqPersonId);
                }

                if (cardStackView.getTopIndex() == adapter.getCount() - 5) {
                    Log.d("CardStackView", "Paginate: " + cardStackView.getTopIndex());
                    paginate();
                }
            }

            @Override
            public void onCardReversed() {
                reverseCount=false;
                Log.d("CardStackView", "onCardReversed");
            }

            @Override
            public void onCardMovedToOrigin() {
                Log.d("CardStackView", "onCardMovedToOrigin");
            }

            @Override
            public void onCardClicked(int index) {
                Log.d("CardStackView", "onCardClicked: " + index);
            }
        });

        //Calendar

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rr.setVisibility(View.VISIBLE);
                llv.setVisibility(View.GONE);
                rrvBottom.setVisibility(View.VISIBLE);

            }
        });

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {



                // long dateInMilli = DatetoMilli(dateClicked);
                //ListView simpleListView=(ListView) view.findViewById(R.id.List1);

               /* for(int i=0;i<eventLists.size();i++){
                    if(eventLists.get(i).getTimeInMilli()==dateInMilli){
                        CustomAdapter customAdapter= new CustomAdapter(getActivity(),eventLists);
                        simpleListView.setAdapter(customAdapter);

                    }else {
                        simpleListView.setAdapter(null);

                    }




                }*/



//                simpleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                        Toast.makeText(getContext(),"Hello "+ eventLists.get(position).getData(),Toast.LENGTH_SHORT).show();
//
//
//
//
//                    }
//                });

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                //Log.d(TAG, "Month was scrolled to: " + firstDayOfNewMonth);
                tvmonth.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
            }


        });


        // String newFormat= "dd MMMM";
        tvmonth.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));


        //Card reverse onclick
        imgv_rvsecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reverse();
            }
        });

        //Button Click for hifi
        imgv_highfi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipeUp();
            }
        });

        //Get Location

        imgv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });

    }




    //GEt all cards
    private void getAllCards(String location, String sgender, Boolean isCoach, int minAge, int maxAge) {
        allCardList.clear();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ApiService.REQUEST_METHOD_GET, ApiService.SEARCH_USER_CARD+"?includeCoach="+isCoach+"&minAge="+minAge+"&maxAge="+maxAge+"&gender="+sgender, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response!=null){
                            for (int i=0;i<response.length();i++){
                                try {

                                    JSONObject jsonObject = response.getJSONObject(i);

                                    BpFinderModel finderModel = new BpFinderModel();
                                    finderModel.setBpf_id(jsonObject.getString("id"));
                                    finderModel.setBpf_login(jsonObject.getString("login"));
                                    finderModel.setBpf_userProfile(jsonObject.getString("userProfile"));
                                    finderModel.setBpf_subscriptions(jsonObject.getString("subscriptions"));
                                    finderModel.setBpf_firstName(jsonObject.getString("firstName"));
                                    finderModel.setBpf_lastName(jsonObject.getString("lastName"));
                                    finderModel.setBpf_email(jsonObject.getString("email"));
                                    finderModel.setBpf_activated(jsonObject.getString("activated"));
                                    finderModel.setBpf_langKey(jsonObject.getString("langKey"));
                                    finderModel.setBpf_imageUrl(jsonObject.getString("imageUrl"));
                                    finderModel.setBpf_videoUrl(jsonObject.getString("videoUrl"));
                                    finderModel.setBpf_resetDate(jsonObject.getString("resetDate"));
                                    finderModel.setBpf_dob(jsonObject.getString("dob"));
                                    finderModel.setBpf_gender(jsonObject.getString("gender"));
                                    finderModel.setBpf_loginType(jsonObject.getString("loginType"));
                                    finderModel.setBpf_city(jsonObject.getString("city"));
                                    finderModel.setBpf_phoneNumber(jsonObject.getString("phoneNumber"));
                                    finderModel.setBpf_deviceId(jsonObject.getString("deviceId"));
                                    finderModel.setBpf_authToken(jsonObject.getString("authToken"));
                                    finderModel.setBpf_location(jsonObject.getString("location"));
                                    finderModel.setBpf_userType(jsonObject.getString("userType"));
                                    finderModel.setBpf_age(jsonObject.getString("age"));

                                    allCardList.add(finderModel);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            paginate();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                String json = null;
                Log.d("error--", error.toString());
                NetworkResponse response = error.networkResponse;
                if (response != null && response.data != null) {
                    switch (response.statusCode) {
                        case 401:
                            json = new String(response.data);
                            json = trimMessage(json, "detail");
                            if (json != null) {
                                Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;

                        default:
                            break;
                    }
                }

            }
        }) {

            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization","Bearer "+token);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        Log.d("Request", jsonArrayRequest.toString());
        requestQueue.add(jsonArrayRequest);

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


    private void paginate() {

        cardStackView.setPaginationReserved();
        //adapter.addAll(createTouristSpots());
        adapter.addAll(allCardList);
        adapter.notifyDataSetChanged();
    }

    //Method for right swipe
    private void cardRightSwiped(String reqPersonId) {

        JsonObjectRequest request = new JsonObjectRequest(ApiService.REQUEST_METHOD_POST, ApiService.RIGHT_SWIPE_REQUEST_SEND + reqPersonId, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response!=null){
                            try {
                                String status = response.getString("status").toString().trim();
                                if(status.equals("New")){
                                    Log.d("request send",status);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                String json = null;
                Log.d("error--", error.toString());
                NetworkResponse response = error.networkResponse;
                if (response != null && response.data != null) {
                    switch (response.statusCode) {
                        case 400:
                            json = new String(response.data);
                            json = trimMessage(json, "title");
                            if (json != null) {
                                Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;
                        case 401:
                            json = new String(response.data);
                            json = trimMessage(json, "title");
                            if (json != null) {
                                Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;
                        case 404:
                            json = new String(response.data);
                            json = trimMessage(json, "title");
                            if (json != null) {
                                Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;

                        default:
                            break;
                    }
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;

            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        Log.d("RequestSend", request.toString());
        requestQueue.add(request);

    }

    //Method for card left swiped
    private void cardLeftSwiped(String reqPersonId) {

        JsonObjectRequest  jrequest = new JsonObjectRequest(ApiService.REQUEST_METHOD_PUT, ApiService.LEFT_SWIPE_DISLIKE + reqPersonId, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status").toString().trim();
                    if(status.equals("New")){
                        Log.d("request send",status);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String json = null;
                Log.d("error--", error.toString());
                NetworkResponse response = error.networkResponse;
                if (response != null && response.data != null) {
                    switch (response.statusCode) {
                        case 400:
                            json = new String(response.data);
                            json = trimMessage(json, "title");
                            if (json != null) {
                                Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;
                        case 401:
                            json = new String(response.data);
                            json = trimMessage(json, "title");
                            if (json != null) {
                                Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;
                        case 404:
                            json = new String(response.data);
                            json = trimMessage(json, "title");
                            if (json != null) {
                                Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;

                        default:
                            break;
                    }
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;

            }

        };
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        Log.d("RejectRequest", queue.toString());
        queue.add(jrequest);
    }

    //Method for hifi

    private void cardHifiSwiped(String reqPersonId) {

        JsonObjectRequest requests = new JsonObjectRequest(ApiService.REQUEST_METHOD_POST, ApiService.HIFI_SWIPE_UP + reqPersonId, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response!=null){
                            try {
                                String status = response.getString("status").toString().trim();
                                if(status.equals("New")){
                                    Log.d("request send",status);


                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                String json = null;
                Log.d("error--", error.toString());
                NetworkResponse response = error.networkResponse;
                if (response != null && response.data != null) {
                    switch (response.statusCode) {
                        case 400:
                            json = new String(response.data);
                            json = trimMessage(json, "title");
                            if (json != null) {
                                Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;
                        case 401:
                            json = new String(response.data);
                            json = trimMessage(json, "title");
                            if (json != null) {
                                Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;
                        case 404:
                            json = new String(response.data);
                            json = trimMessage(json, "title");
                            if (json != null) {
                                Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;

                        default:
                            break;
                    }
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;

            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        Log.d("RequestSend", requests.toString());
        requestQueue.add(requests);

    }

    /*private List<TouristSpot> createTouristSpots() {
        List<TouristSpot> spots = new ArrayList<>();
        spots.add(new TouristSpot("Marti McLaurin", "Athlete", "http://seqato.com/bp/videos/2.mp4","http://seqato.com/bp/images/2.jpg"));
        spots.add(new TouristSpot("Alivia Orvieto", "Athlete", "http://seqato.com/bp/videos/1.mp4","http://seqato.com/bp/images/1.jpg"));
        spots.add(new TouristSpot("Liz Held", "Athlete", "http://seqato.com/bp/videos/3.mp4","http://seqato.com/bp/images/3.jpg"));
        spots.add(new TouristSpot("Marti McLaurin", "Athlete", "http://seqato.com/bp/videos/2.mp4","http://seqato.com/bp/images/5.jpg"));
        spots.add(new TouristSpot("Alivia Orvieto", "Athlete", "http://seqato.com/bp/videos/1.mp4","http://seqato.com/bp/images/4.jpg"));
        spots.add(new TouristSpot("Liz Held", "Athlete", "http://seqato.com/bp/videos/3.mp4","http://seqato.com/bp/images/6.jpg"));
        spots.add(new TouristSpot("Marti McLaurin", "Athlete", "http://seqato.com/bp/videos/2.mp4","http://seqato.com/bp/images/8.jpg"));
        spots.add(new TouristSpot("Alivia Orvieto", "Athlete", "http://seqato.com/bp/videos/1.mp4","http://seqato.com/bp/images/7.jpg"));
        spots.add(new TouristSpot("Liz Held", "Athlete", "http://seqato.com/bp/videos/3.mp4","http://seqato.com/bp/images/9.jpg"));
        return spots;
    }*/

    /* private void reload() {
         cardStackView.setVisibility(View.GONE);
         progressBar.setVisibility(View.VISIBLE);
         new Handler().postDelayed(new Runnable() {
             @Override
             public void run() {
                 adapter = createTouristSpotCardAdapter();
                 cardStackView.setAdapter(adapter);
                 cardStackView.setVisibility(View.VISIBLE);
                 progressBar.setVisibility(View.GONE);
             }
         }, 1000);
     }*/
    private void reload() {
        cardStackView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getActivity() != null && cardStackView != null) {
                    if(adapter == null) {
                        adapter = createTouristSpotCardAdapter();
                    }
                    cardStackView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    cardStackView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }
        }, 1000);
    }

    private TouristSpotCardAdapter createTouristSpotCardAdapter() {

        if(allCardList!=null){
            adapter = new TouristSpotCardAdapter(getActivity(),this);
            adapter.addAll(allCardList);

        }
        return adapter;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
    @Override
    public void addView(String url,String nm) {
        rr.setVisibility(View.GONE);
        llv.setVisibility(View.VISIBLE);
        Glide.with(getContext()).load(url).into(imgv_profilepic);
        collapsingToolbarLayout.setTitle(nm);
        rrvBottom.setVisibility(View.GONE);
        //imgview.setImageURI(Uri.parse(url));
//        tvname.setText(nm);

    }

    @Override
    public void onClick(String bpf_id,String bpf_deviceId) {
        reqPersonId = bpf_id;
        deviceId    = bpf_deviceId;
    }

    //Method for card reverse

    private void reverse() {

        if(reverseCount) {
            cardStackView.reverse();
            imgv_rvsecard.setBackground(getResources().getDrawable(R.drawable.ic_backcard_disable));
        }
    }

    private LinkedList<BpFinderModel> extractRemainingTouristSpots() {
        LinkedList<BpFinderModel> spots = new LinkedList<>();
        for (int i = cardStackView.getTopIndex(); i < adapter.getCount(); i++) {
            spots.add(adapter.getItem(i));
        }
        return spots;
    }

    //Method for SwipeUp

    private void swipeUp() {

        List<BpFinderModel> spots = extractRemainingTouristSpots();
        if (spots.isEmpty()) {
            return;
        }
        if(spots.size()>0){
            reqPersonId= spots.get(0).getBpf_id().trim();
        }
        View target = cardStackView.getTopView();

        ValueAnimator rotation = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("rotation", 0f));
        rotation.setDuration(200);
        ValueAnimator translateX = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationX", 0f, 0f));
        ValueAnimator translateY = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationY", 0f, -2000f));
        translateX.setStartDelay(100);
        translateY.setStartDelay(100);
        translateX.setDuration(500);
        translateY.setDuration(500);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(rotation, translateX, translateY);

        cardStackView.swipe(SwipeDirection.Top, set);
    }

    private String trimMessage(String json, String detail) {
        String trimmedString = null;

        try {
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(detail);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }
    //Method for getting current location

    private void getLocation() {


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

}