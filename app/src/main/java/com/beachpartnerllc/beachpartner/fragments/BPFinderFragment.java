package com.beachpartnerllc.beachpartner.fragments;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.beachpartnerllc.beachpartner.MyInterface;
import com.beachpartnerllc.beachpartner.R;
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
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public ToggleButton btnMale,btnFemale;
    private FoldingCell fc;
    private LinearLayout llvFilter;
    private ArrayAdapter<String> dataAdapter;
    private ImageButton showPreviousMonthButton,showNextMonthButton;
    private Switch sCoach;
    private LinearLayout topFrameLayout;

    public static final String MY_PREFS_FILTER = "MyPrefsFile";
    private ArrayList<String>stateList = new ArrayList<>();
    private RelativeLayout rrvBottom;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    public boolean reverseCount=false;
    private boolean isbpActive =false;
    private boolean isPartner = false;
    private View view;
    private SharedPreferences prefs;
    private String location,sgender;
    private Boolean isCoach;
    private int minAge,maxAge;
    private CompactCalendarView compactCalendar;
    private RecyclerView rcv_bpProfiles;
    private BlueBProfileAdapter blueBProfileAdapter;
    private String token,user_id,user_subscription,reqPersonId,deviceId,fcmToken;
    private ArrayList<BpFinderModel>allCardList = new ArrayList<BpFinderModel>();
    private ArrayList<BpFinderModel>bluebpList = new ArrayList<>();
    private ArrayList<BpFinderModel>bluebpListSecond = new ArrayList<>();
    private String item_location;


    private List<Event>personEventList = new ArrayList<>();


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
            getBpProfiles();//Method for getting next strip
            bluebpList = (ArrayList<BpFinderModel>) data.getSerializable("bluebplist");
            int cPosition = data.getInt("cPosition");
            if(bluebpList!=null && bluebpList.size()>0) {
                adapter = new TouristSpotCardAdapter(getActivity(), this);
                for(int i=cPosition;i<bluebpList.size();i++){
                    adapter.addAll(bluebpList.get(i));
                }
                adapter.addAll(bluebpList);
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

        compactCalendar = (CompactCalendarView) view.findViewById(R.id.compactcalendar_view);
        compactCalendar.setFirstDayOfWeek(Calendar.SUNDAY);
        compactCalendar.setUseThreeLetterAbbreviation(true);
        collapsingToolbarLayout = (CollapsingToolbarLayout)view. findViewById(R.id.CollapsingToolbarLayout1);



        imgv_rvsecard   =   (ImageView) view.findViewById(R.id.ic_rvsecard);
        imgv_highfi     =   (ImageView) view.findViewById(R.id.ic_high);
        imgv_location   =   (ImageView) view.findViewById(R.id.ic_location);

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
                if (getActivity() != null) {
                    new PrefManager(getContext()).savePageno(-1);
                    getBpProfiles();
                }

                location    =   spinner_location.getText().toString().trim();
                sgender     =   txtv_gender.getText().toString();
                isCoach     =   sCoach.isChecked();
                minAge      =   Integer.parseInt(tvMin.getText().toString().trim());
                maxAge      =   Integer.parseInt(tvMax.getText().toString().trim());

                if ((minAge >= 5 && maxAge <= 100) && !(minAge == maxAge)) {
                    if (sgender.equals("Both")) {
                        sgender = "";
                    }
                    SharedPreferences.Editor preferences = getActivity().getSharedPreferences(MY_PREFS_FILTER, MODE_PRIVATE).edit();
                    preferences.putString("location", spinner_location.getText().toString().trim());
                    preferences.putInt("minAge", Integer.parseInt(tvMin.getText().toString().trim()));
                    preferences.putInt("maxAge", Integer.parseInt(tvMax.getText().toString().trim()));
                    preferences.putString("gender", sgender);
                    preferences.putBoolean("isCoachActive", sCoach.isChecked());
                    preferences.apply();
                    preferences.commit();
                    llvFilter.setVisibility(View.GONE);
                    rr.setVisibility(View.VISIBLE);
                    rrvBottom.setVisibility(View.VISIBLE);

                    //check top bp strip **if its active only in bpfinder page(ie,BPFinder Fragment),it's disabled on calendar find Partner page
                    if (isbpActive) {
                        topFrameLayout.setVisibility(View.GONE);

                    } else {
                        if (isPartner) {
                            topFrameLayout.setVisibility(View.GONE);

                        } else {
                            topFrameLayout.setVisibility(View.VISIBLE);
                            if (bluebpList != null && bluebpList.size() > 0) {
                                blueBProfileAdapter = new BlueBProfileAdapter(getContext(), bluebpList);
                                rcv_bpProfiles.setAdapter(blueBProfileAdapter);
                            }
                        }

                    }

                    getAllCards(location, sgender, isCoach, minAge, maxAge);
                }else {
                    Toast.makeText(getActivity(), "Please choose minimum age limit is greater than 5", Toast.LENGTH_SHORT).show();
                }
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
                    //r.put(reqPersonId);
                    String r=fcmToken;
                    sendMessage(r,"BeachPartner",new PrefManager(getContext()).getUserName()+"sent you a high five","");
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


                                    //finderModel.setBpf_age(jsonObject.getString("age"));


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

            public Map<String, String> getHeaders()  {
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
            public Map<String, String> getHeaders()  {
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
            public Map<String, String> getHeaders()  {
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
            public Map<String, String> getHeaders()  {
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

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        Log.d("Request", jsonArrayRqst.toString());
        requestQueue.add(jsonArrayRqst);
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
        int pref_pageno = new PrefManager(getContext()).getPageno();
        final int pageno = pref_pageno+1;
        JsonArrayRequest  jsonRequest = new JsonArrayRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_SUBSCRIPTIONS +"?subscriptionType=BlueBP&page=" +pageno+ "&size=5", null, new
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

                            if(bluebpListSecond!=null && bluebpListSecond.size()>0){
                                if(getActivity()!=null){
                                    new PrefManager(getContext()).savePageno(pageno);
                                    blueBProfileAdapter = new BlueBProfileAdapter(getContext(),bluebpListSecond);
                                    rcv_bpProfiles.setAdapter(blueBProfileAdapter);
                                }
                            }
                        }else {
                            new PrefManager(getContext()).savePageno(-1);
                            getBpProfiles();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        Log.d("Request", jsonRequest.toString());
        requestQueue.add(jsonRequest);
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
    public void onClick(String bpf_id,String bpf_deviceId,String bpf_fcmToken) {
        reqPersonId = bpf_id;
        deviceId    = bpf_deviceId;
        fcmToken    = bpf_fcmToken;
        getIndividualEvents(reqPersonId);
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
        if (user_subcription.equals("Prime")) {

            Dialog filterDialogue = new Dialog(getContext());
            //filterDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
            filterDialogue.setContentView(R.layout.popup_locations);
            filterDialogue.setCanceledOnTouchOutside(true);
            Window window = filterDialogue.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            wlp.y = 80;
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(wlp);
            filterDialogue
                    .getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

            filterDialogue.show();
            initView(filterDialogue);

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

        final ArrayAdapter<String> adapterLocation = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, regionList);
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
                    Toast.makeText(getContext(), "Message Success: " + success + "Message Failed: " + failure, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Message Failed, Unknown error occurred.", Toast.LENGTH_LONG).show();
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


}