package com.beachpartnerllc.beachpartner.fragments;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.beachpartnerllc.beachpartner.CircularImageView;
import com.beachpartnerllc.beachpartner.CustomTextView;
import com.beachpartnerllc.beachpartner.MyInterface;
import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.activity.TabActivity;
import com.beachpartnerllc.beachpartner.adpters.BlueBProfileAdapter;
import com.beachpartnerllc.beachpartner.adpters.TouristSpotCardAdapter;
import com.beachpartnerllc.beachpartner.calendar.compactcalendarview.CompactCalendarView;
import com.beachpartnerllc.beachpartner.calendar.compactcalendarview.domain.Event;
import com.beachpartnerllc.beachpartner.cardstackview.CardStackView;
import com.beachpartnerllc.beachpartner.cardstackview.SwipeDirection;
import com.beachpartnerllc.beachpartner.connections.ApiService;
import com.beachpartnerllc.beachpartner.connections.PrefManager;
import com.beachpartnerllc.beachpartner.models.BpFinderModel;
import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.ramotion.foldingcell.FoldingCell;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
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


public class BPFinderFragment extends Fragment implements MyInterface {


    private ProgressBar progressBar;
    private CardStackView cardStackView;
    private TouristSpotCardAdapter adapter;
    private RelativeLayout rr;
    private CoordinatorLayout llv;
    private ImageView imgv_profilepic,imgv_rvsecard,imgv_location,imgv_highfi,btnPlay;
    private TextView tvmonth,tvMin,tvMax,txtv_gender;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    private Spinner spinner_location;
    private MultiSlider age_bar;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public ToggleButton btnMale,btnFemale;
    private FoldingCell fc;
    private LinearLayout llvFilter;
    private ArrayAdapter<String> dataAdapter;
    private ImageButton showPreviousMonthButton,showNextMonthButton;
    private Switch sCoach;
    private LinearLayout topFrameLayout;
    private ArrayList<String>stateList = new ArrayList<>();
    private RelativeLayout rrvBottom;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    public boolean reverseCount=false;
    private boolean isbpActive =false;
    private boolean isPartner = false;
    private View view;
    private SharedPreferences prefs;
    private String location,sgender,profileImage,topThreeFinish;
    private Boolean isCoach;
    private int minAge,maxAge;
    private CompactCalendarView compactCalendar;
    private RecyclerView rcv_bpProfiles;
    private BlueBProfileAdapter blueBProfileAdapter;
    private CardView empty_card;
    private CircularImageView profilePic;
    private String token,user_id,user_subscription,reqPersonId,deviceId,fcmToken;
    private ArrayList<BpFinderModel>allCardList = new ArrayList<BpFinderModel>();
    private ArrayList<BpFinderModel>bluebpList = new ArrayList<BpFinderModel>();
    private ArrayList<BpFinderModel>bluebpListSecond = new ArrayList<BpFinderModel>();
    private ArrayList<BpFinderModel>hifiList = new ArrayList<BpFinderModel>();
    private ArrayList<BpFinderModel>noLikes  = new ArrayList<BpFinderModel>();
    private ArrayList<BpFinderModel>connectionList = new ArrayList<BpFinderModel>();//Connected people list
    private String item_location;
    private List<Event>personEventList = new ArrayList<Event>();
    private TabActivity tabActivity;
    private BpFinderModel cModel;
    private CustomTextView topFinishes_One,topFinishes_Two,topFinishes_Three;


    public BPFinderFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(getActivity());

    }

    @SuppressLint("ValidFragment")
    public BPFinderFragment(boolean isBPActive,boolean isPartnerFinder) {
        isbpActive=isBPActive;
        isPartner = isPartnerFinder;
    }

    @Override
    public void onStart() {
        super.onStart();
        getPreferences();
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

        allCardList.clear();
        //

        setUp(view);
        getPreferences();
        btnFemale.setText("Women");
        btnMale.setText("Men");
        btnFemale.setTextOff("Women");
        btnMale.setTextOff("Men");
        btnFemale.setTextOn("Women");
        btnMale.setTextOn("Men");
        //reload card
        reload();
        //get profile pic from preference
        profileImage = new PrefManager(getActivity()).getProfilePic();

        Bundle data = getArguments();
        if(data!=null){
            getBpProfiles();//Method for getting next strip
            hifiList   = (ArrayList<BpFinderModel>) data.getSerializable("hifiList");
            bluebpList = (ArrayList<BpFinderModel>) data.getSerializable("bluebplist");
            noLikes    = (ArrayList<BpFinderModel>) data.getSerializable("noLikeslist");
            int cPosition   = data.getInt("cPosition");
            if(bluebpList!=null && bluebpList.size()>0) {
                adapter = new TouristSpotCardAdapter(getActivity(), this);
                adapter.add(bluebpList.get(cPosition));
                adapter.notifyDataSetInvalidated();

                //adapter.notifyDataSetChanged();
                //adapter.notifyDataSetInvalidated();
                //here location = null,gender = null, isCoach = false ,minage 5 and maxage=30
               // getAllCards(null, "", false, 5, 35);//This is commented here
                if (minAge == 0 && maxAge ==0) {
                    getAllCards(null, "", false, 5, 35);//This is commented here
                }else {
                    getAllCards(location, sgender, isCoach, minAge, maxAge);
                }
                /*if (bluebpList.size()>0) {
                    for (int i = cPosition; i < bluebpList.size(); i++) {
                        adapter.addAll(bluebpList.get(i));
                        if (i == bluebpList.size()) {
                            if (allCardList.size() > 0 && allCardList !=null) {
                                for (int j = 0; j <allCardList.size() ; j++) {
                                    adapter.addAll(allCardList);
                                }
                            }else {
                                getAllCards(location, sgender, isCoach, minAge, maxAge);
                            }
                        }
                    }

                }*/
            }
            //From 20+ Likes
            if (noLikes !=null && noLikes.size() > 0) {
                adapter = new TouristSpotCardAdapter(getActivity(), this);
                adapter.addAll(noLikes);
                /*for (int k = 0; k < noLikes.size() ; k++) {
                    adapter.addAll(noLikes.get(k));
                }*/
            }

            //From hifi fragment page
            int item_position= data.getInt("itemPosition");
            if (hifiList != null && hifiList.size() > 0) {
                adapter = new TouristSpotCardAdapter(getActivity(), this);
                adapter.add(hifiList.get(item_position));
                adapter.notifyDataSetInvalidated();
                if (minAge == 0 && maxAge ==0) {
                    getAllCards(null, "", false, 5, 35);//This is commented here
                }else {
                    getAllCards(location, sgender, isCoach, minAge, maxAge);
                }
               // getAllCards(location, sgender, isCoach, minAge, maxAge);


               /* if (hifiList.size()>0) {
                    for (int j =item_position; j < hifiList.size(); j++) {
                        adapter.addAll(hifiList.get(j));
                    }
                }*/
            }
            //getAllCards(location, sgender, isCoach, minAge, maxAge);

        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() instanceof TabActivity) {
            tabActivity = (TabActivity)getActivity();
            tabActivity.setActionBarTitle("Beach Partner");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getPreferences();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferences();
    }

    private void getPreferences() {
        //check shared prefvalue
        prefs = new PrefManager(getActivity()).getSettingsData();
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
            if (location != null){
                int positions = dataAdapter.getPosition(location);
                spinner_location.setSelection(positions);
            }
            sCoach.setChecked(isCoach);

            if(sgender!=null){
                if(sgender.equals("Male")){
                    txtv_gender.setText("Male");
                    btnMale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnMale.setTextColor(getResources().getColor(R.color.white));
                    btnMale.setChecked(true);
                    btnFemale.setBackground(getResources().getDrawable(R.color.imgBacgnd));
                    btnFemale.setTextColor(getResources().getColor(R.color.black));
                }else if(sgender.equals("Female")){
                    txtv_gender.setText("Female");
                    btnFemale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnFemale.setTextColor(getResources().getColor(R.color.white));
                    btnFemale.setChecked(true);
                    btnMale.setBackground(getResources().getDrawable(R.color.imgBacgnd));
                    btnMale.setTextColor(getResources().getColor(R.color.black));
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

        empty_card          =   (CardView) view.findViewById(R.id.no_cards);
        profilePic          =   (CircularImageView)view.findViewById(R.id.profilePic);

        //Layout for filters

        llvFilter           =   (LinearLayout) view.findViewById(R.id.llFilter);
        tvMin               =   (TextView) view.findViewById(R.id.txtv_minAge);
        tvMax               =   (TextView) view.findViewById(R.id.txtv_maxAge);
        age_bar             =   (MultiSlider)  view.findViewById(R.id.rangebar);
        spinner_location    =   (Spinner) view.findViewById(R.id.spinner_location);

        txtv_gender         =   (TextView) view.findViewById(R.id.txtv_gender);

        btnMale             =   (ToggleButton) view.findViewById(R.id.btnMen);
        btnFemale           =   (ToggleButton) view.findViewById(R.id.btnWomen);
        btnPlay             =   (ImageView)view.findViewById(R.id.imgPlay);
        fc                  =   (FoldingCell)view. findViewById(R.id.folding_cell);
        topFrameLayout      =   (LinearLayout)view.findViewById(R.id.frmeOne);



        showPreviousMonthButton = (ImageButton) view.findViewById(R.id.prev_button);
        showNextMonthButton = (ImageButton) view.findViewById(R.id.next_button);

        sCoach              =   (Switch) view.findViewById(R.id.swich_coach);

        compactCalendar = (CompactCalendarView) view.findViewById(R.id.compactcalendar_view);
        compactCalendar.setFirstDayOfWeek(Calendar.SUNDAY);
        compactCalendar.setUseThreeLetterAbbreviation(true);
        collapsingToolbarLayout = (CollapsingToolbarLayout)view. findViewById(R.id.CollapsingToolbarLayout1);



        imgv_rvsecard   =   (ImageView) view.findViewById(R.id.ic_rvsecard);
        imgv_highfi     =   (ImageView) view.findViewById(R.id.ic_high);
        imgv_location   =   (ImageView) view.findViewById(R.id.ic_location);

        topFinishes_One     =   (CustomTextView)view.findViewById(R.id.topOne_finishes);
        topFinishes_Two     =   (CustomTextView)view.findViewById(R.id.topTwo_finishes);
        topFinishes_Three   =   (CustomTextView)view.findViewById(R.id.topThree_finishes);


        rcv_bpProfiles  =   (RecyclerView) view.findViewById(R.id.rrv_topbpProfiles);
        //recycler for top bp profiles
        LinearLayoutManager lmnger = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rcv_bpProfiles.setLayoutManager(lmnger);
        rcv_bpProfiles.setHasFixedSize(true);

        addLocation();

        showPreviousMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendar.showPreviousMonth();
            }
        });

        showNextMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendar.showNextMonth();
            }
        });

        //choose Location
        if(getActivity()!=null) {
            dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, stateList);


            spinner_location.setAdapter(dataAdapter);
        }
        spinner_location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                location = spinner_location.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub


            }
        });



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
                    txtv_gender.setText("Male");
                    btnMale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnMale.setTextColor(getResources().getColor(R.color.white));
                }
                else if(!isChecked ){
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
                if(btnMale.isChecked()&&isChecked){
                    txtv_gender.setText("Both");
                    btnFemale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnMale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnFemale.setTextColor(getResources().getColor(R.color.white));
                    btnMale.setTextColor(getResources().getColor(R.color.white));
                }
                else if(isChecked){
                    txtv_gender.setText("Female");
                    btnFemale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnFemale.setTextColor(getResources().getColor(R.color.white));
                }
                else if(!isChecked ){
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
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null) {
                    //new PrefManager(getContext()).savePageno(-1);
                    getBpProfiles();
                }
                sgender     =   txtv_gender.getText().toString();
                isCoach     =   sCoach.isChecked();
                minAge      =   Integer.parseInt(tvMin.getText().toString().trim());
                maxAge      =   Integer.parseInt(tvMax.getText().toString().trim());

                if (sgender.equals("Both")) {
                    sgender = "";
                }



                    /*SharedPreferences.Editor preferences = getActivity().getSharedPreferences(MY_PREFS_FILTER, MODE_PRIVATE).edit();
                    preferences.putString("location", spinner_location.getText().toString().trim());
>>>>>>> 86077b96c1cac24873d38c9325bd4dce2409bea1
                    preferences.putInt("minAge", Integer.parseInt(tvMin.getText().toString().trim()));
                    preferences.putInt("maxAge", Integer.parseInt(tvMax.getText().toString().trim()));
                    preferences.putString("gender", sgender);
                    preferences.putBoolean("isCoachActive", sCoach.isChecked());
                    preferences.apply();
                    preferences.commit();*/
                new PrefManager(getActivity()).saveSettingData(location,sgender,isCoach,minAge,maxAge);
                llvFilter.setVisibility(View.GONE);
                rr.setVisibility(View.VISIBLE);
                rrvBottom.setVisibility(View.VISIBLE);

                //check top bp strip **if its active only in bpfinder page(ie,BPFinder Fragment),it's disabled on calendar find Partner page
                if (isbpActive) {
                    topFrameLayout.setVisibility(View.GONE);

                    new PrefManager(getActivity()).saveSettingData(location,sgender,isCoach,minAge,maxAge);
                    llvFilter.setVisibility(View.GONE);
                    rr.setVisibility(View.VISIBLE);


                } else {
                    if (isPartner) {
                        topFrameLayout.setVisibility(View.GONE);

                    } else {
                        topFrameLayout.setVisibility(View.VISIBLE);
                        if (bluebpList.size()!= 0) {
                            if(getActivity()!=null) {
                                blueBProfileAdapter = new BlueBProfileAdapter(getActivity(), bluebpList);
                                rcv_bpProfiles.setAdapter(blueBProfileAdapter);
                            }
                        }
                    }

                }
                progressBar.setVisibility(View.VISIBLE);
                getAllCards(location, sgender, isCoach, minAge, maxAge);

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
                imgv_rvsecard.setBackground(BPFinderFragment.this.getResources().getDrawable(R.drawable.ic_backcard));
                Log.d("CardStackView", "onCardSwiped: " + direction.toString());
                Log.d("CardStackView", "topIndex: " + cardStackView.getTopIndex());
                //Methods for swipe card kalakrishnan 06/04/2018
                if(direction.toString().equalsIgnoreCase("Right")){
                    //Toast.makeText(getActivity(), "You right swiped : "+reqPersonId, Toast.LENGTH_SHORT).show();
                    //Api for Right swipe/like
                    if (reqPersonId != null) {
                        cardRightSwiped(reqPersonId);
                    }

                }else if(direction.toString().equalsIgnoreCase("Left")){
                    //Toast.makeText(getActivity(), "You Left swiped : "+reqPersonId, Toast.LENGTH_SHORT).show();
                    if (reqPersonId != null) {
                        cardLeftSwiped(reqPersonId);
                    }

                }else if(direction.toString().equalsIgnoreCase("Top")) {
                    //Toast.makeText(getActivity(), "You Hified : "+reqPersonId, Toast.LENGTH_SHORT).show();
                    if (reqPersonId != null) {
                        cardHifiSwiped(reqPersonId);
                    }
                    //r.put(reqPersonId);
                    String r=fcmToken;
                    String uName =new PrefManager(getContext()).getUserName();
                    if (fcmToken != null && !fcmToken.equalsIgnoreCase("null") && uName != null && !uName.equalsIgnoreCase("null")) {
                        sendMessage(r,"BeachPartner",uName +" sent you a high five","");
                    }
                }

                if (cardStackView.getTopIndex() == adapter.getCount() /*- 5*/) {
                    Log.d("CardStackView", "Paginate: " + cardStackView.getTopIndex());
                    noCrads();
                    // paginate();
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

        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {


                List<Event> bookingsMap = compactCalendar.getEvents(dateClicked);


                if (bookingsMap != null) {

                    for (int i = 0; i < bookingsMap.size(); i++) {

                        // Date date=new Date(bookingsFromMap.get(i).getTimeInMillis());
                        if ((DatetoMilli(dateClicked)) == (DatetoMilli(new Date(bookingsMap.get(i).getTimeInMillis())))) {

                        }
                    }
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                //Log.d(TAG, "Month was scrolled to: " + firstDayOfNewMonth);
                tvmonth.setText(dateFormatForMonth.format(compactCalendar.getFirstDayOfCurrentMonth()));
            }


        });


        // String newFormat= "dd MMMM";
        tvmonth.setText(dateFormatForMonth.format(compactCalendar.getFirstDayOfCurrentMonth()));


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

    private void noCrads() {
        if (getActivity() != null) {
            cardStackView.setVisibility(View.INVISIBLE);
            empty_card.setVisibility(View.VISIBLE);
            Glide.with(getActivity()).load(profileImage).into(profilePic);
        }

    }



    //GEt all cards
    private void getAllCards(String location, String sgender, Boolean isCoach, int minAge, int maxAge) {
        allCardList.clear();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ApiService.REQUEST_METHOD_GET, ApiService.SEARCH_USER_CARD+"?includeCoach="+isCoach+"&minAge="+minAge+"&maxAge="+maxAge+"&gender="+sgender+"&hideConnectedUser=true&hideLikedUser=true&hideRejectedConnections=true&hideBlockedUsers=true", null,
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
                                    // finderModel.setBpf_subscriptions(jsonObject.getString("subscriptions"));
                                    finderModel.setBpf_firstName(jsonObject.getString("firstName"));
                                    finderModel.setBpf_lastName(jsonObject.getString("lastName"));
                                    finderModel.setBpf_email(jsonObject.getString("email"));
                                    finderModel.setBpf_activated(jsonObject.getString("activated"));
                                    finderModel.setBpf_langKey(jsonObject.getString("langKey"));
                                    finderModel.setBpf_imageUrl(jsonObject.getString("imageUrl"));
                                    finderModel.setBpf_videoUrl(jsonObject.getString("videoUrl"));
                                    //finderModel.setBpf_resetDate(jsonObject.getString("resetDate"));
                                    finderModel.setBpf_dob(jsonObject.getString("dob"));
                                    finderModel.setBpf_gender(jsonObject.getString("gender"));
                                    finderModel.setBpf_loginType(jsonObject.getString("loginType"));
                                    finderModel.setBpf_city(jsonObject.getString("city"));
                                    finderModel.setBpf_phoneNumber(jsonObject.getString("phoneNumber"));
                                    finderModel.setBpf_deviceId(jsonObject.getString("deviceId"));
                                    finderModel.setBpf_authToken(jsonObject.getString("authToken"));
                                    finderModel.setBpf_location(jsonObject.getString("location"));
                                    finderModel.setBpf_userType(jsonObject.getString("userType"));

                                    finderModel.setBpf_fcmToken(jsonObject.getString("fcmToken"));

                                    JSONObject profileObject = jsonObject.getJSONObject("userProfile");
                                    finderModel.setBpf_topfinishes(profileObject.getString("topFinishes"));
                                    //finderModel.setBpf_age(jsonObject.getString("age"));
                                    allCardList.add(finderModel);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            if(allCardList.size()>0){
                                progressBar.setVisibility(View.INVISIBLE);
                                paginate();
                            }else {
                                progressBar.setVisibility(View.INVISIBLE);
                                noCrads();
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

            public Map<String, String> getHeaders()  {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization","Bearer "+token);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };
        if (getActivity() != null) {
            int socketTimeout = 30000; // 30 seconds. You can change it
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            Log.d("Request", jsonArrayRequest.toString());
            jsonArrayRequest.setRetryPolicy(policy);
            requestQueue.add(jsonArrayRequest);
        }


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


    private void paginate() {

        if (getActivity() != null) {
            if (allCardList.size() != 0 && allCardList.size()>0) {
                if (adapter != null) {
                    cardStackView.setPaginationReserved();
                    //adapter.addAll(createTouristSpots());`
                    adapter.addAll(allCardList);
                    adapter.notifyDataSetChanged();
                }

            }

        }

    }

    //Method for right swipe
    private void cardRightSwiped(String reqPersonId) {

        JsonObjectRequest request = new JsonObjectRequest(ApiService.REQUEST_METHOD_POST, ApiService.RIGHT_SWIPE_REQUEST_SEND + reqPersonId, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response!=null){
                            try {
                                getBpProfiles();
                                String status = response.getString("status").toString().trim();
                                if(status.equals("New")){
                                    Log.d("request send",status);
                                }
                                if (status.equalsIgnoreCase("Active")) {

                                    JSONObject object = new JSONObject(response.getString("user"));

                                    cModel =  new BpFinderModel();
                                    cModel.setBpf_id(object.getString("id"));
                                    cModel.setBpf_firstName(object.getString("firstName"));
                                    cModel.setBpf_lastName(object.getString("lastName"));
                                    cModel.setBpf_email(object.getString("email"));
                                    cModel.setBpf_imageUrl(object.getString("imageUrl"));
                                    cModel.setBpf_videoUrl(object.getString("videoUrl"));
                                    cModel.setBpf_dob(object.getString("dob"));
                                    cModel.setBpf_gender(object.getString("gender"));
                                    cModel.setBpf_loginType(object.getString("loginType"));
                                    cModel.setBpf_city(object.getString("city"));
                                    cModel.setBpf_phoneNumber(object.getString("phoneNumber"));
                                    cModel.setBpf_deviceId(object.getString("deviceId"));
                                    cModel.setBpf_userType(object.getString("userType"));
                                    cModel.setBpf_age(object.getString("age"));
                                    cModel.setBpf_fcmToken(object.getString("fcmToken"));

                                    showAlertDialog();
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
                                //Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;
                        case 401:
                            json = new String(response.data);
                            json = trimMessage(json, "title");
                            if (json != null) {
                                //Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;
                        case 404:
                            json = new String(response.data);
                            json = trimMessage(json, "title");
                            if (json != null) {
                                //Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;

                        default:
                            break;
                    }
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders()  {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;

            }

        };
        if (getActivity() != null) {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            Log.d("RequestSend", request.toString());
            requestQueue.add(request);
        }


    }



    //Method for card left swiped
    private void cardLeftSwiped(String reqPersonId) {

        JsonObjectRequest  jrequest = new JsonObjectRequest(ApiService.REQUEST_METHOD_POST, ApiService.LEFT_SWIPE_DISLIKE + reqPersonId, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    getBpProfiles();
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
                                //Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;
                        case 401:
                            json = new String(response.data);
                            json = trimMessage(json, "title");
                            if (json != null) {
                                // Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;
                        case 404:
                            json = new String(response.data);
                            json = trimMessage(json, "title");
                            if (json != null) {
                                //Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;

                        default:
                            break;
                    }
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders()  {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;

            }

        };
        if (getActivity() != null) {
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            Log.d("RejectRequest", queue.toString());
            queue.add(jrequest);
        }

    }

    //Method for hifi

    private void cardHifiSwiped(String reqPersonId) {

        JsonObjectRequest requests = new JsonObjectRequest(ApiService.REQUEST_METHOD_POST, ApiService.HIFI_SWIPE_UP + reqPersonId, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response!=null){
                            getBpProfiles();
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
                                //Toast.makeText(getContext(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;
                        case 401:
                            json = new String(response.data);
                            json = trimMessage(json, "title");
                            if (json != null) {
                                //Toast.makeText(getContext(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;
                        case 404:
                            json = new String(response.data);
                            json = trimMessage(json, "title");
                            if (json != null) {
                                // Toast.makeText(getContext(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;

                        default:
                            break;
                    }
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders()  {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;

            }

        };
        if (getActivity() != null) {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            Log.d("RequestSend", requests.toString());
            requestQueue.add(requests);
        }


    }



    //Api for get individual events for a particular person

    public void getIndividualEvents(String reqPersonId){
        personEventList.clear();
        JsonArrayRequest jsonArrayRqst  = new JsonArrayRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_USER_EVENTS + reqPersonId, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response!=null){
                            for (int i =0;i<response.length();i++){

                                try {

                                    JSONObject obj= response.getJSONObject(i);

                                    JSONObject jsonObject= obj.getJSONObject("event");
                                    Event eventModel = new Event();
                                    eventModel.setEventId(jsonObject.getString("id"));
                                    eventModel.setEventName(jsonObject.getString("eventName"));
                                    eventModel.setData(jsonObject.getString("eventDescription"));
                                    eventModel.setEventLocation(jsonObject.getString("eventLocation"));
                                    eventModel.setEventVenue(jsonObject.getString("eventVenue"));
                                    eventModel.setEventStartDate(jsonObject.getLong("eventStartDate"));
                                    eventModel.setTimeInMillis(Long.parseLong(jsonObject.getString("eventStartDate")));
                                    eventModel.setEventEndDate(jsonObject.getLong("eventEndDate"));
                                    personEventList.add(eventModel);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            setupPersonCalendar();

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
            @Override
            public Map<String, String> getHeaders()  {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        if (getActivity() != null) {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            Log.d("Request", jsonArrayRqst.toString());
            requestQueue.add(jsonArrayRqst);
        }

    }

    //Calendar for individual persons
    private void setupPersonCalendar() {
        if(personEventList != null && personEventList.size()>0){
            for (int i = 0; i < personEventList.size(); i++) {
                compactCalendar.addEvent(personEventList.get(i));
            }
        }
    }



    //Method for getting bluebpstrips
    private void getBpProfiles() {
        bluebpListSecond.clear();
        JsonArrayRequest  jsonRequest = new JsonArrayRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_SUBSCRIPTIONS +"?subscriptionType=BlueBP&hideConnectedUser=true&hideLikedUser=true&hideRejectedConnections=true&hideBlockedUsers=true", null, new
                Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response!=null && response.length()>0){
                            for (int i=0;i<response.length();i++){
                                try {
                                    JSONObject object = response.getJSONObject(i);
                                    JSONObject jsonObject = object.getJSONObject("user");

                                    BpFinderModel bpModel = new BpFinderModel();
                                    bpModel.setBpf_id(jsonObject.getString("id"));
                                    bpModel.setBpf_firstName(jsonObject.getString("firstName"));
                                    bpModel.setBpf_imageUrl(jsonObject.getString("imageUrl"));
                                    bpModel.setBpf_videoUrl(jsonObject.getString("videoUrl"));
                                    bpModel.setBpf_userType(jsonObject.getString("userType"));
                                    bpModel.setBpf_age(jsonObject.getString("age"));
                                    bpModel.setBpf_daysToExpireSubscription(object.getString("daysToExpireSubscription"));
                                    bpModel.setBpf_effectiveDate(object.getString("effectiveDate"));
                                    bpModel.setBpf_termDate(object.getString("termDate"));
                                    bpModel.setBpf_subscriptionType(object.getString("subscriptionType"));
                                    bluebpListSecond.add(bpModel);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            setUpBlueBPStrips();
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
            @Override
            public Map<String, String> getHeaders(){
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };
        if (getActivity() != null) {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            Log.d("Request", jsonRequest.toString());
            requestQueue.add(jsonRequest);
        }

    }

    private void setUpBlueBPStrips() {
        if(bluebpListSecond!=null && bluebpListSecond.size()>0){
            if(getActivity()!=null){
                blueBProfileAdapter = new BlueBProfileAdapter(getActivity(),bluebpListSecond);
                rcv_bpProfiles.setAdapter(blueBProfileAdapter);
                blueBProfileAdapter.notifyDataSetChanged();
            }
        }
    }



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
                    //  adapter.notifyDataSetChanged();
                    cardStackView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }
        }, 1000);
    }

    private TouristSpotCardAdapter createTouristSpotCardAdapter() {

        if(allCardList!=null){
            adapter = new TouristSpotCardAdapter(getActivity().getApplicationContext(),this);
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
    public void onClick(String bpf_id,String bpf_deviceId,String bpf_fcmToken,String bpf_topFinishes) {
        reqPersonId = bpf_id;
        deviceId    = bpf_deviceId;
        fcmToken    = bpf_fcmToken;
        topThreeFinish = bpf_topFinishes;
        getIndividualEvents(reqPersonId);

        if (topThreeFinish != null && !topThreeFinish.equalsIgnoreCase("") && !topThreeFinish.equalsIgnoreCase("null")) {
            String[] values = topThreeFinish.split(",");
            if(values.length == 1){
                if (values[0] != null) {
                    topFinishes_One.setText(values[0].trim());
                }
            }
            if(values.length == 2){
                if (values[0] != null) {
                    topFinishes_One.setText(values[0].trim());
                }
                if (values[1] != null) {
                    topFinishes_Two.setText(values[1].trim());
                }
            }
            if (values.length == 3) {
                if (values[0] != null) {
                    topFinishes_One.setText(values[0].trim());
                }
                if (values[1] != null) {
                    topFinishes_Two.setText(values[1].trim());
                }
                if (values[2] != null) {
                    topFinishes_Three.setText(values[2].trim());
                }
            }


            //
            //topFinishes_Three.setText(values[2].trim());
        }
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



    public long DatetoMilli(Date dateClicked) {
        Date givenDateString = dateClicked;
        long timeInMilliseconds = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date mDate = sdf.parse(String.valueOf(givenDateString));
            mDate.setHours(0);
            mDate.setMinutes(0);
            mDate.setSeconds(0);
            timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeInMilliseconds;
    }

    //Method for getting current location
    //Conditions for show location
    //if user_subscription = PRime didn't show payment dialogue
    //if user_subscription = BlueBP show payment dialogue,once the user paid then show the location
    // user_subscription = null show the payment dialogue  ,once the user paid then show the location

    private void getLocation() {
        String user_subcription = "";
        if (!user_subcription.equals("Prime")) {


            SettingsFragment sf = new SettingsFragment();
//            getActivity().setTitle("Settings");
            Bundle arguments = new Bundle();
            arguments.putString( "prime_card","location");
            sf.setArguments(arguments);
            FragmentManager mang = getActivity().getSupportFragmentManager();
            FragmentTransaction trans = mang.beginTransaction().addToBackStack(null);
            trans.replace(R.id.container, sf);
            trans.commit();

//            Dialog filterDialogue = new Dialog(getContext());
//            //filterDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
//            filterDialogue.setContentView(R.layout.popup_locations);
//            filterDialogue.setCanceledOnTouchOutside(true);
//            Window window = filterDialogue.getWindow();
//            WindowManager.LayoutParams wlp = window.getAttributes();
//            wlp.gravity = Gravity.CENTER;
//            wlp.y = 80;
//            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
//            window.setAttributes(wlp);
//            filterDialogue
//                    .getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//
//            filterDialogue.show();
//            initView(filterDialogue);

        } else if (user_subcription.equals("BlueBP")) {
            likesDisplay();
        }else {
            likesDisplay();
        }

    }

    private void initView(final Dialog filterDialogue) {

        List<String> regionList = new ArrayList<>();
        regionList.add("Alaska Region (AK)");
        regionList.add("Aloha Region (AH)");
        regionList.add("Arizona Region (AZ)");
        regionList.add("Badger Region (BG)");
        regionList.add("Bayou Region (BY)");
        regionList.add("Carolina Region (CR)");
        regionList.add("Chesapeake Region (CH)");
        regionList.add("Columbia Empire Region (CE)");
        regionList.add("Delta Region (DE)");
        regionList.add("Evergreen Region (EV)");
        regionList.add("Florida Region (FL)");
        regionList.add("Garden Empire Region (GE)");
        regionList.add("Gateway Region (GW)");
        regionList.add("Great Lakes Region (GL)");
        regionList.add("Great Plains Region (GP)");
        regionList.add("Gulf Coast Region (GC)");
        regionList.add("Heart of America Region (HA)");
        regionList.add("Hoosier Region (HO)");
        regionList.add("Intermountain Region (IM)");
        regionList.add("Iowa Region (IA)");
        regionList.add("Iroquois Empire Region (IE)");
        regionList.add("Keystone Region (KE)");
        regionList.add("Lakeshore Region (LK)");
        regionList.add("Lone Star Region (LS)");
        regionList.add("Moku O Keawe Region (MK)");
        regionList.add("New England Region (NE)");
        regionList.add("North Country Region (NO)");
        regionList.add("North Texas Region (NT)");
        regionList.add("Northern California Region (NC)");
        regionList.add("Ohio Valley Region (OV)");
        regionList.add("Oklahoma Region (OK)");
        regionList.add("Old Dominion Region (OD)");
        regionList.add("Palmetto Region (PM)");
        regionList.add("Pioneer Region (PR)");
        regionList.add("Puget Sound Region (PS)");
        regionList.add("Rocky Mountain Region (RM)");
        regionList.add("Southern Region (SO)");
        regionList.add("Southern California Region (SC)");
        regionList.add("Sun Country Region (SU)");
        regionList.add("Western Empire Region (WE)");
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/SanFranciscoTextRegular.ttf");

        final AutoCompleteTextView tv_locations = (AutoCompleteTextView) filterDialogue.findViewById(R.id.item_locations);
        Button btn_search = (Button) filterDialogue.findViewById(R.id.btn_search);

        final ArrayAdapter<String> adapterLocation = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, regionList);
        tv_locations.setTypeface(font);
        tv_locations.setAdapter(adapterLocation);

        tv_locations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                item_location = adapterLocation.getItem(i);

            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterDialogue.dismiss();
                //Toast.makeText(getActivity(), "Location" + item_location, Toast.LENGTH_SHORT).show();
                getAllCards(item_location,sgender,isCoach,minAge,maxAge);

            }
        });
    }

    private void likesDisplay() {

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.popup_no_of_likes_layout, null);

        final Button save_btn            = (Button)   alertLayout.findViewById(R.id.purchase_btn);
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getContext());


        // Initialize a new foreground color span instance
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.blueDark));


        alert.setView(alertLayout);
        alert.setCancelable(true);



        final android.app.AlertDialog dialog = alert.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {

                dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setBackgroundColor(getResources().getColor(R.color.blueDark));
                dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setGravity(Gravity.CENTER);
            }
        });
        dialog.show();

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    //push notification

    public static final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";
    OkHttpClient mClient = new OkHttpClient();
    public void sendMessage(final String recipients, final String title, final String body, final String icon) {


        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    JSONObject root = new JSONObject();
                    JSONObject notification = new JSONObject();
                    notification.put("body", body);
                    notification.put("title", title);
                    notification.put("icon", icon);

                    root.put("data", notification);
                    root.put("to", recipients);

                    String result = postToFCM(root.toString());
                    return result;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                try {
                    JSONObject resultJson = new JSONObject(result);
                    int success, failure;
                    success = resultJson.getInt("success");
                    failure = resultJson.getInt("failure");
                    //Toast.makeText(getActivity(), "Message Success: " + success + "Message Failed: " + failure, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(getActivity(), "Message Failed, Unknown error occurred.", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    //
    String postToFCM(String bodyString) throws IOException {
        RequestBody body = RequestBody.create(JSON,bodyString);
        Request request = new Request.Builder()
                .url(FCM_MESSAGE_URL)
                .post(body)
                .addHeader("Authorization", "key=" + "AAAA_n1CZM0:APA91bEzOCp33DEXluIkYPsfheiCk7PINyo0MaxDkk7AfX-uG6PUO1CCggbEnN3IR1sljAm05WY-BGDeyeAOM7l1ciFdFnAV5z5DrnkBaYKQusqJPcvzbyKX-84t1b5SvpXPp75NjJI9")
                .build();
        com.squareup.okhttp.Response response = mClient.newCall(request).execute();
        return response.body().string();
    }

    private void showAlertDialog() {

        AlertDialog.Builder alert   = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater     = this.getLayoutInflater();
        View layout                 = inflater.inflate(R.layout.swipe_matchfound_layout,null);

        final Button btnMsg  =  layout.findViewById(R.id.sendMsg);
        final Button btnFind =  layout.findViewById(R.id.findBtn);
        final Button btnSwipe=  layout.findViewById(R.id.btnKeep);


        alert.setView(layout);



        final AlertDialog alertDialog = alert.create();

        //Button Send Message
        btnMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alertDialog.dismiss();
                if (getActivity() != null) {
                    ChatFragmentPage chatFragmentPage = new ChatFragmentPage();
                    Bundle bundle = new Bundle();
                    bundle.putString("personId",cModel.getBpf_id());
                    bundle.putString("personName",cModel.getBpf_firstName());
                    bundle.putString("myName",new PrefManager(getActivity()).getUserName());
                    bundle.putString("personPic",cModel.getBpf_imageUrl());
                    chatFragmentPage.setArguments(bundle);
                    FragmentManager manager = ((FragmentActivity)getActivity()).getSupportFragmentManager();
                    FragmentTransaction ctrans = manager.beginTransaction();
                    //ctrans.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                    ctrans.replace(R.id.container,chatFragmentPage);
                    ctrans.commit();
                }

            }
        });

        //Button Find partner
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // alertDialog.dismiss();
                //Moving to Calendar Fragment
                if (getActivity() != null) {
                    CalendarFragment calendarFragment = new CalendarFragment();
                    FragmentManager manager = ((FragmentActivity)getActivity()).getSupportFragmentManager();
                    FragmentTransaction ctrans = manager.beginTransaction();
                    //ctrans.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                    ctrans.replace(R.id.container,calendarFragment);
                    //ctrans.addToBackStack(null);
                    ctrans.commit();
                }

            }
        });

        //Button Swipe
        btnSwipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();

    }

}