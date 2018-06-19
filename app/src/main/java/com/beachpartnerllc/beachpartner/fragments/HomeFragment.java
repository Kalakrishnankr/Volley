package com.beachpartnerllc.beachpartner.fragments;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.activity.TabActivity;
import com.beachpartnerllc.beachpartner.adpters.BenefitListItemAdapter;
import com.beachpartnerllc.beachpartner.adpters.CardAdapter;
import com.beachpartnerllc.beachpartner.adpters.MessageAdapter;
import com.beachpartnerllc.beachpartner.adpters.PartnerAdapter;
import com.beachpartnerllc.beachpartner.adpters.ProfileAdapter;
import com.beachpartnerllc.beachpartner.adpters.SubscriptionAdapter;
import com.beachpartnerllc.beachpartner.calendar.compactcalendarview.domain.Event;
import com.beachpartnerllc.beachpartner.connections.ApiService;
import com.beachpartnerllc.beachpartner.connections.PrefManager;
import com.beachpartnerllc.beachpartner.models.BenefitModel;
import com.beachpartnerllc.beachpartner.models.BpFinderModel;
import com.beachpartnerllc.beachpartner.models.EventDetailsModel;
import com.beachpartnerllc.beachpartner.models.EventResultModel;
import com.beachpartnerllc.beachpartner.models.InvitationResponseModel;
import com.beachpartnerllc.beachpartner.models.InvitationsModel;
import com.beachpartnerllc.beachpartner.models.SingleSubscriptionModel;
import com.beachpartnerllc.beachpartner.models.SubscriptionItemsModels;
import com.beachpartnerllc.beachpartner.models.SubscriptonPlansModel;
import com.beachpartnerllc.beachpartner.models.SwipeResultModel;
import com.beachpartnerllc.beachpartner.utils.AppConstants;
import com.beachpartnerllc.beachpartner.utils.EventClickListner;
import com.beachpartnerllc.beachpartner.utils.SubClickInterface;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment implements View.OnClickListener,EventClickListner,SubClickInterface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private ImageView img_bpprofile,img_send,img_received;
    private FrameLayout likesCard;
    private RecyclerView mRecyclerview,pRecyclerview,msgRecyclerview,parRecyclerview,rcview_sub_item;
    CardAdapter adapter;
    MessageAdapter messageAdapter;
    PartnerAdapter partnerAdapter;
    ProfileAdapter profileAdapter;
    SubscriptionAdapter subscriptionAdapter;
    BenefitListItemAdapter benefitItemAdapter;
    private Button btnProceed,btnCancel,btnBuy;
    private TabActivity tabActivity;
    private TextView txt_head,txtv_notour,txtv_nomsgs,txtv_noreqsts,txtv_nobp,txtv_likes,headerTitle,tv_price,tv_regPrice;
    private AVLoadingIndicatorView progressBar,progressBar_tour,progressBar_msg,progressBar_rqsts,prgressSub;
    private String user_id,user_token,usertype,no_likes_count,subScriptions,event_Id;
    private PrefManager prefManager;
    private LinearLayout ucoming_next,message_next,request_next,topHeaderLayout;
    private LinearLayoutManager layoutManagerBluebp,layoutManagerUp,layoutManagerMsg,layoutmngerReqst;
    private static  boolean isblueBP = false;
    private static boolean isPartner = false;
    private ArrayList<Event>myUpcomingTList = new ArrayList<>();
    private ArrayList<SwipeResultModel> bpList  = new ArrayList<>();
    private ArrayList<BpFinderModel> noLikes = new ArrayList<BpFinderModel>();
    private ArrayList<BpFinderModel> connectionList = new ArrayList<>();
    private ArrayList<String> chatList = new ArrayList<>();
    private ArrayList<BpFinderModel> likesList = new ArrayList<>();
    private ArrayList<BpFinderModel> userList = new ArrayList<>();
    private List<EventDetailsModel>sendInvitationList = new ArrayList<>();
    private List<EventDetailsModel>receiveInvitationList = new ArrayList<>();
    private List<SubscriptonPlansModel>plansModelList = new ArrayList<>();
    private List<SubscriptonPlansModel>userPlanList = new ArrayList<>();
    private List<SubscriptionItemsModels>userSubList = new ArrayList<>();
    private List<SubscriptionItemsModels>userAddonsList = new ArrayList<>();
    private List<BenefitModel>benefitModelList = new ArrayList<>();
    View ln_layout_tournamentrequestheader;
    View ln_layout_tournamentrequestcontent;
    private boolean isRequestSend = false;
    private boolean isRequestReceive =false;
    private View layoutAlert;
    private RelativeLayout premius_header;
    private static final String TAG = "HomeFragment";
    private List<InvitationsModel>inviteList = new ArrayList<InvitationsModel>();


    public HomeFragment() {
        // Required empty public constructor

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            Firebase.setAndroidContext(getActivity());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View  view= inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        prefManager =  new PrefManager(getContext());
        subScriptions= prefManager.getSubscriptionType();
        user_id     =  prefManager.getUserId();
        user_token  =  prefManager.getToken();
        usertype    =  prefManager.getUserType();
        return view;
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() instanceof TabActivity){
            tabActivity = (TabActivity)getActivity();
            tabActivity.getSupportActionBar().setTitle((R.string.app_name));
        }
        getuserSubscriptions();//Get single user subscriptions
        getBluebpProfiles();//Method for getting all bluebp profiles
        getMyTournaments();//Method for getting my upcoming tournamentslist(upcoming tournament current date to future 5 months)
        getConnections();//Method for getting all blocked/unblocked connections
        getNumberLike();//Method for getting number of likes
        setViewBasedOnUserType();
        getAllSendOrReceiveInvitation();//Method for getting send/Receive invitations
        //setUpMessage();

        /*getRequests();
        getPeopleWhoLiked();*/

    }




    private void setViewBasedOnUserType(){
        if (prefManager.getUserType().equals(AppConstants.USER_TYPE_ATHLETE)){
        }else {
            ln_layout_tournamentrequestcontent.setVisibility(View.GONE);
            ln_layout_tournamentrequestheader.setVisibility(View.GONE);
        }
    }
    private void initView(View view) {

        //Next buttons
        ucoming_next = view.findViewById(R.id.upcome_next_button);
        message_next = view.findViewById(R.id.mess_next_button);
        request_next = view.findViewById(R.id.req_next_button);

        progressBar = view.findViewById(R.id.progress);
        progressBar_tour = view.findViewById(R.id.progress_tournament);
        progressBar_msg = view.findViewById(R.id.progress_msg);
        progressBar_rqsts = view.findViewById(R.id.progress_request);


        //img_bpprofile   =   (ImageView) view.findViewById(R.id.img_bpfinder);
        txt_head = view.findViewById(R.id.txtview_head);
        txtv_notour = view.findViewById(R.id.txtv_notour_athlete);
        txtv_nomsgs = view.findViewById(R.id.txtv_nomessgs);
        txtv_noreqsts = view.findViewById(R.id.txtv_noreqsts);
        txtv_nobp = view.findViewById(R.id.txtv_nobp);


        img_send = view.findViewById(R.id.imgview_send);
        img_received = view.findViewById(R.id.imgview_received);
        likesCard = view.findViewById(R.id.no_of_likes_card);
        txtv_likes = view.findViewById(R.id.txtv_likes_athlete);

        pRecyclerview = view.findViewById(R.id.rrv_topProfile);//This recycler view for top profile picture
        mRecyclerview = view.findViewById(R.id.rcv);          //Recycler view for upcoming events
        msgRecyclerview = view.findViewById(R.id.rcv_message);  //Recycler view for messages
        parRecyclerview = view.findViewById(R.id.rcv_partners); //Recycler view for tournament requests


        ln_layout_tournamentrequestheader = view.findViewById(R.id.tournament_request_header);
        ln_layout_tournamentrequestcontent = view.findViewById(R.id.tournament_request_content);

        img_send.setOnClickListener(this);
        img_received.setOnClickListener(this);
        likesCard.setOnClickListener(this);

        ucoming_next.setOnClickListener(this);
        message_next.setOnClickListener(this);
        request_next.setOnClickListener(this);

        //Blue BP Adapter class
        layoutManagerBluebp = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        pRecyclerview.setLayoutManager(layoutManagerBluebp);
        pRecyclerview.setHasFixedSize(true);

        /*My upcoming tournaments*/
        layoutManagerUp = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        mRecyclerview.setLayoutManager(layoutManagerUp);
        mRecyclerview.setHasFixedSize(true);

        /*Message*/
        layoutManagerMsg = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        SnapHelper snaper = new LinearSnapHelper();
        snaper.attachToRecyclerView(msgRecyclerview);
        msgRecyclerview.setLayoutManager(layoutManagerMsg);
        msgRecyclerview.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(1), true));
        msgRecyclerview.setItemAnimator(new DefaultItemAnimator());
        msgRecyclerview.setHasFixedSize(true);

        /*Tournament Requests*/
        layoutmngerReqst =  new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        SnapHelper snap =   new LinearSnapHelper();
        snap.attachToRecyclerView(parRecyclerview);
        parRecyclerview.setLayoutManager(layoutmngerReqst);
        parRecyclerview.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(5), true));
        parRecyclerview.setItemAnimator(new DefaultItemAnimator());
        parRecyclerview.setHasFixedSize(true);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.imgview_received:
                txt_head.setText("Tournament Requests Received");
                receiveInvitationTab();
                break;

            case R.id.imgview_send:
                if (!usertype.equalsIgnoreCase(AppConstants.USER_TYPE_COACH)) {
                    if (subScriptions != null) {
                        if (subScriptions.equalsIgnoreCase(AppConstants.SUBSCIPTION_TYPE_ONE)) {
                            showSubcriptionAlert();
                        }else {
                            txt_head.setText("Tournament Requests Sent");
                            sendInvitationTab();
                        }
                    }
                }
                /*if (subScriptions != null) {
                    if (CheckPlan.isPlanAvailable(plansModelList, subScriptions, AppConstants.BENIFIT_CODE_B12)) {
                        userPlanList.clear();
                        //Toast.makeText(tabActivity, "Selecetd index"+selectedIndex, Toast.LENGTH_SHORT).show();
                        if (plansModelList.get(selectedIndex).getPlanName().equalsIgnoreCase(subScriptions)) {
                            txt_head.setText("Tournament Requests Sent");
                            sendInvitationTab();
                        } else {
                            if (plansModelList.get(2).getPlanName().equalsIgnoreCase(subScriptions)) {
                                txt_head.setText("Tournament Requests Sent");
                                sendInvitationTab();
                            } else if (plansModelList.get(3).getPlanName().equalsIgnoreCase(subScriptions)) {
                                txt_head.setText("Tournament Requests Sent");
                                sendInvitationTab();
                            } else {
                                showSubcriptionAlert();
                            }
                        }
                        for (int i = selectedIndex; i < plansModelList.size(); i++) {
                            userPlanList.add(plansModelList.get(i));
                        }
                    }
                }*/
                break;
            case R.id.no_of_likes_card:
                  likesDisplay();
                //getNumberLike();
                break;
            case R.id.upcome_next_button:
                //Toast.makeText(getActivity(), "Clicked Tour", Toast.LENGTH_SHORT).show();
                mRecyclerview.getLayoutManager().scrollToPosition(layoutManagerUp.findLastVisibleItemPosition() + 1);
                break;
            case R.id.mess_next_button:
                //Toast.makeText(getActivity(), "Clicked message", Toast.LENGTH_SHORT).show();
                msgRecyclerview.getLayoutManager().scrollToPosition(layoutManagerMsg.findLastVisibleItemPosition()+1);
                break;
            case R.id.req_next_button:
                //Toast.makeText(getActivity(), "Clicked requests", Toast.LENGTH_SHORT).show();
                parRecyclerview.getLayoutManager().scrollToPosition(layoutmngerReqst.findLastVisibleItemPosition()+1);
                break;

            default:
                break;
        }
    }

    //Getting the number of likes
    private void likesDisplay() {

        if (subScriptions.equalsIgnoreCase("Prime") && subScriptions != null) {
            //Api for getting
            getPeopleWhoLiked();

        }else {
            showSubcriptionAlert();
        }

       /* if(userType=="Athlete"){
            boolean isblueBP=true;
            boolean isPartner=false;
            BPFinderFragment bpFinderFragment =new BPFinderFragment(isblueBP,isPartner);
            Bundle bundle = new Bundle();
            //cPosition is the current positon

            bundle.putSerializable("bluebplist", likesList);
            bpFinderFragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, bpFinderFragment).commit();
        }*/
    }

    private void showSubcriptionAlert() {

        LayoutInflater inflater = getLayoutInflater();
        //View alertLayout = inflater.inflate(R.layout.popup_no_of_likes_layout, null);//Alert dialogue previous one
        View alertLayout = inflater.inflate(R.layout.alert_subscription_layout, null);

        btnProceed    = alertLayout.findViewById(R.id.btn_proceed);
        btnCancel     = alertLayout.findViewById(R.id.btn_sub_cancel);
        prgressSub    = alertLayout.findViewById(R.id.progress_subscription);
        RecyclerView rc_view  = alertLayout.findViewById(R.id.rcv_subscribe);
        prgressSub.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rc_view.setLayoutManager(layoutManager);
        btnProceed.setClickable(false);
        userPlanList.clear();
        if (plansModelList.size() > 0 && plansModelList != null) {
            for (int i = 0; i <plansModelList.size() ; i++) {
                if (!plansModelList.get(i).getPlanName().equalsIgnoreCase(subScriptions)) {
                    userPlanList.add(plansModelList.get(i));
                }
            }
            prgressSub.setVisibility(View.INVISIBLE);
            subscriptionAdapter = new SubscriptionAdapter(getContext(),userPlanList,this,subScriptions);
            rc_view.setAdapter(subscriptionAdapter);
        }
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getContext());
        // Initialize a new foreground color span instance
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.blueDark));
        alert.setView(alertLayout);
        alert.setCancelable(true);
        final android.app.AlertDialog dialog = alert.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                //dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setBackgroundColor(getResources().getColor(R.color.blueDark));
                //dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setGravity(Gravity.CENTER);
            }
        });
        dialog.show();
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    //view all event invitation list api //15-05-2018
    @Override
    public void getEvent(String eventID,String sendCount ) {

        if (isRequestReceive) {
            receiveRequetHandler(eventID,sendCount);
        }else {
            sendRequestHandler(eventID,sendCount);
        }


    }

    //Handle the receive requests here
    private void receiveRequetHandler(String event_Id,String count_no) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_INVITATION_LIST+event_Id+"?calendarType=mastercalendar", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar_rqsts.setVisibility(View.INVISIBLE);
                if (response != null) {
                    EventResultModel eventResultModel = new Gson().fromJson(response.toString(),EventResultModel.class);
                    if (eventResultModel != null) {
                        if (getActivity() != null) {
                            Bundle bundle = new Bundle();
                            bundle.putParcelable(AppConstants.EVENT_OBJECT,eventResultModel);
                            AcceptRejectRequestFragment rejectRequestFragment = new AcceptRejectRequestFragment();
                            rejectRequestFragment.setArguments(bundle);
                            //getActivity().getActionBar().setTitle("Accept/Reject");
                            FragmentManager mangFeed = getActivity().getSupportFragmentManager();
                            FragmentTransaction transFeed = mangFeed.beginTransaction();
                            transFeed.replace(R.id.container, rejectRequestFragment);
                            transFeed.commit();
                        }

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
                            json = trimMessage(json, "title");
                            if (json != null) {
                                Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;
                        case 400:
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
                else{
                    Toast.makeText(tabActivity, R.string.no_internet, Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders(){
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + user_token);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        if (getActivity() != null) {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            Log.d("Request", jsonObjectRequest.toString());
            requestQueue.add(jsonObjectRequest);
        }
    }

    //Handle the send requests here
    private void sendRequestHandler(String event_Id, final String send_count){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_INVITATION_LIST+event_Id+"?calendarType=mastercalendar", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    Event eventModel = new Gson().fromJson(response.toString(),Event.class);
                    try {
                        eventModel.setEventState(response.getString("state"));
                        eventModel.setEventTeamSize(response.getString("teamSize"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (eventModel != null) {
                        if (getActivity() != null) {
                            Bundle bundle = new Bundle();
                            bundle.putString(AppConstants.REQUEST_SEND_COUNT,send_count);
                            bundle.putParcelable(AppConstants.EVENT_DETAIL,eventModel);
                            tabActivity.navigation.setSelectedItemId(R.id.navigation_calendar);
                            EventDescriptionFragment eventDescriptionFragment = new EventDescriptionFragment();
                            eventDescriptionFragment.setArguments(bundle);
                            FragmentManager manager = getActivity().getSupportFragmentManager();
                            FragmentTransaction ctrans = manager.beginTransaction();
                            //ctrans.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                            ctrans.replace(R.id.container,eventDescriptionFragment);
                            //ctrans.addToBackStack(null);
                            ctrans.commit();
                        }
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
                            json = trimMessage(json, "title");
                            if (json != null) {
                                Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;
                        case 400:
                            json = new String(response.data);
                            json = trimMessage(json, "title");
                            if (json != null) {
                                Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;
                        default:
                            break;
                    }
                }else{
                    Toast.makeText(tabActivity, R.string.no_internet, Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders(){
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + user_token);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        if (getActivity() != null) {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            Log.d("Request", jsonObjectRequest.toString());
            requestQueue.add(jsonObjectRequest);
        }
    }

    @Override
    public void changeViews() {
        btnProceed.setBackgroundColor(getResources().getColor(R.color.btn_sub));
        btnProceed.setTextColor(getResources().getColor(R.color.white));
    }

    /*Method for displaying subscriptons*/
    @Override
    public void changeSubLayout(SubscriptonPlansModel subscriptonPlansModel){

        LayoutInflater inflater = getLayoutInflater();
        layoutAlert = inflater.inflate(R.layout.subscription_free_layout, null);
        LinearLayoutManager linearManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        topHeaderLayout = layoutAlert.findViewById(R.id.top_sub_header);
        headerTitle     = layoutAlert.findViewById(R.id.head_sub_title);
        tv_price        = layoutAlert.findViewById(R.id.tv_price);
        tv_regPrice     = layoutAlert.findViewById(R.id.tv_subPrice);
        btnBuy          =  layoutAlert.findViewById(R.id.sub_buy);
        rcview_sub_item = layoutAlert.findViewById(R.id.rcview_sub);
        premius_header  = layoutAlert.findViewById(R.id.recruit_header);

        rcview_sub_item.setLayoutManager(linearManager);
        //benefitModelList.clear();
        benefitModelList =subscriptonPlansModel.getBenefitList();
        if (subscriptonPlansModel.getPlanName().equalsIgnoreCase("FREE")) {
            //layoutAlert = inflater.inflate(R.layout.subscription_free_layout, null);
            tv_price.setText("$"+subscriptonPlansModel.getMonthlyCharge());
            btnBuy.setBackgroundColor(getResources().getColor(R.color.butn_free));
            if (benefitModelList != null) {
                tv_regPrice.setText("$"+subscriptonPlansModel.getRegFee());
                benefitItemAdapter = new BenefitListItemAdapter(getContext(),benefitModelList);
                rcview_sub_item.setAdapter(benefitItemAdapter);
            }
        }else if (subscriptonPlansModel.getPlanName().equalsIgnoreCase("LITE")) {
            tv_price.setText("$"+subscriptonPlansModel.getMonthlyCharge());
            topHeaderLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.header_lite));
            headerTitle.setText(R.string.lite_sub);
            btnBuy.setBackgroundColor(getResources().getColor(R.color.butn_lite));
            if (benefitModelList != null) {
                tv_regPrice.setText("$"+subscriptonPlansModel.getRegFee()+" one-time");
                benefitItemAdapter = new BenefitListItemAdapter(getContext(),benefitModelList);
                rcview_sub_item.setAdapter(benefitItemAdapter);
            }
        } else if (subscriptonPlansModel.getPlanName().equalsIgnoreCase("STANDARD")) {
            tv_price.setText("$"+subscriptonPlansModel.getMonthlyCharge());
            topHeaderLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.header_standard));
            headerTitle.setText(R.string.standard_sub);
            btnBuy.setBackgroundColor(getResources().getColor(R.color.butn_std));
            if (benefitModelList != null) {
                tv_regPrice.setText("$"+subscriptonPlansModel.getRegFee()+" one-time");
                benefitItemAdapter = new BenefitListItemAdapter(getContext(),benefitModelList);
                rcview_sub_item.setAdapter(benefitItemAdapter);
            }
        }else {
            tv_price.setText("$"+subscriptonPlansModel.getMonthlyCharge());
            topHeaderLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.header_rec));
            headerTitle.setText(R.string.recruiting_sub);
            btnBuy.setBackgroundColor(getResources().getColor(R.color.butn_recruit));
            premius_header.setVisibility(View.VISIBLE);
            if (benefitModelList != null) {
                tv_regPrice.setText("$"+subscriptonPlansModel.getRegFee()+" one-time");
                benefitItemAdapter = new BenefitListItemAdapter(getContext(),benefitModelList);
                rcview_sub_item.setAdapter(benefitItemAdapter);
            }
        }

        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getContext());
        // Initialize a new foreground color span instance
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.blueDark));
        alert.setView(layoutAlert);
        alert.setCancelable(true);
        final android.app.AlertDialog dialog = alert.create();

        dialog.show();
    }


   /* Grid item spacing and padding */

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position / spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    //Api for get all blue bp profiles
    private void getBluebpProfiles() {
        bpList.clear();
        progressBar.setVisibility(View.VISIBLE);

        JsonArrayRequest  jsonRequest = new JsonArrayRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_SUBSCRIPTIONS +"?subscriptionType=BlueBP&hideConnectedUser=true&hideLikedUser=true&hideRejectedConnections=true&hideBlockedUsers=true", null, new
                Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d(TAG, "onResponse() called with: response = [" + response.toString() + "]");
                        if(response!=null){
                            Type listType = new TypeToken<List<SwipeResultModel>>() {
                            }.getType();
                            bpList = new Gson().fromJson(response.toString(), listType);

                         setblueBpstrip();//Set BlueBP views


                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
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
                headers.put("Authorization", "Bearer " + user_token);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        Log.d("Request", jsonRequest.toString());
        requestQueue.add(jsonRequest);

    }



    //Get all my tournaments
    private void getMyTournaments() {
        progressBar_tour.setVisibility(View.VISIBLE);
        inviteList.clear();
        myUpcomingTList.clear();
        SimpleDateFormat dft= new SimpleDateFormat("dd-MM-yyyy");
        Date date       = Calendar.getInstance().getTime();
        String fromDate = dft.format(date);
        Calendar cal    = Calendar.getInstance();
        cal.add(Calendar.MONTH, 5);
        Date date1      = cal.getTime();
        String toDate   = dft.format(date1);

        JsonArrayRequest jsonArrayRequest  = new JsonArrayRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_MYUPCOMING_TOURNAMENTS + "?fromDate=" + fromDate + "&toDate=" + toDate + "&userId=" + user_id, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Success response", response.toString());
                if(response != null){
                    for(int i=0;i<response.length();i++){

                        try {
                            JSONObject object = response.getJSONObject(i);
                            JSONObject obj = object.getJSONObject("event");
                            Event event = new Event();
                            event.setEventId(obj.getString("id"));
                            event.setEventName(obj.getString("eventName"));
                            event.setEventDescription(obj.getString("eventDescription"));
//                            event.setEventLocation(obj.getString("eventLocation"));
                            event.setEventState(obj.getString("state"));  // changed as location is returning null and state value can be used instead
                            event.setEventVenue(obj.getString("eventVenue"));
                            event.setEventTeamSizes(obj.getString("teamSize"));
                            event.setEventStartDate(obj.getLong("eventStartDate"));
                            event.setEventEndDate(obj.getLong("eventEndDate"));
                            event.setEventRegStartdate(obj.getLong("eventRegStartDate"));
                            event.setEventEndDate(obj.getLong("eventRegEndDate"));
                            event.setEventAdmin(obj.getString("eventAdmin"));

                            JSONArray contacts = new JSONArray(object.getString("invitationList"));
                            Type type = new TypeToken<List<InvitationsModel>>(){}.getType();
                            inviteList = new Gson().fromJson(contacts.toString(), type);
                            event.setInvitationList(inviteList);


//                                    -------------event admin changed from object to string change noted on 5/9/2018--------------
//                            JSONObject objectadmin = obj.getJSONObject("eventAdmin");
//                            EventAdminModel adminModel = new EventAdminModel();
//                            adminModel.setFirstName(objectadmin.getString("firstName"));
//                            event.setEventAdmin(adminModel);

                            myUpcomingTList.add(event);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    setUpMyComingTournament();

                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String json = null;
                progressBar_tour.setVisibility(View.GONE);
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
        }){
            @Override
            public Map<String, String> getHeaders(){
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + user_token);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        Log.d("Request", jsonArrayRequest.toString());
        requestQueue.add(jsonArrayRequest);
    }

    private void setUpMyComingTournament() {
        progressBar_tour.setVisibility(View.GONE);
        if(myUpcomingTList.size()>0){
            adapter = new CardAdapter(getContext(),myUpcomingTList);
            mRecyclerview.setAdapter(adapter);
            SnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(mRecyclerview);
        }else {
            mRecyclerview.setVisibility(View.GONE);
            txtv_notour.setVisibility(View.VISIBLE);
        }

    }


    private void setblueBpstrip() {
        progressBar.setVisibility(View.GONE);
        if(bpList!=null && bpList.size()>0){
            profileAdapter = new ProfileAdapter(getContext(),bpList);
            pRecyclerview.setAdapter(profileAdapter);
            profileAdapter.notifyDataSetChanged();
        }else {
            txtv_nobp.setVisibility(View.VISIBLE);
        }

    }


    //Get 20+ Likes
    private void getNumberLike() {
        noLikes.clear();
        JsonObjectRequest arrayRequest = new JsonObjectRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_ALL_LIKES + user_id + "?status=New&showReceived=true", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    try {
                        no_likes_count= response.getString("list_count");
                        if(no_likes_count==null || no_likes_count.equals("0")){
                            txtv_likes.setText("No");
                        }
                        else{
                            txtv_likes.setText(no_likes_count);
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
                        case 401:
                            json = new String(response.data);
                            json = trimMessage(json, "title");
                            if (json != null) {
                                Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;
                        case 400:
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
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + user_token);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        if (getActivity() != null) {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            Log.d("Request", arrayRequest.toString());
            requestQueue.add(arrayRequest);
        }

    }

    //Get all user invitation sendor receive
    private void getAllSendOrReceiveInvitation() {
        progressBar_rqsts.setVisibility(View.VISIBLE);
        JsonObjectRequest objectRequest = new JsonObjectRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_ALL_SENDORRECIVE_REQUEST, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    Log.d(TAG, "onResponse: "+response.toString());
                    InvitationResponseModel responseModel = new Gson().fromJson(response.toString(),InvitationResponseModel.class);
                    sendInvitationList = responseModel.getInvitationSendModels();
                    receiveInvitationList = responseModel.getInvitationReceivedModels();
                }
                isRequestReceive = true;//Default activate received requests
                setInvitationAdpater();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar_rqsts.setVisibility(View.INVISIBLE);
                String json = null;
                Log.d("error--", error.toString());
                NetworkResponse response = error.networkResponse;
                if (response != null && response.data != null) {
                    switch (response.statusCode) {
                        case 401:
                            json = new String(response.data);
                            json = trimMessage(json, "title");
                            if (json != null) {
                                Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;
                        case 400:
                            json = new String(response.data);
                            json = trimMessage(json, "title");
                            if (json != null) {
                                Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;
                        default:
                            break;
                    }
                }else{
                    Toast.makeText(tabActivity, R.string.no_internet, Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + user_token);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        if (getActivity() != null) {
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            Log.d("Request Send/Receive", objectRequest.toString());
            queue.add(objectRequest);
        }
    }

    private void setInvitationAdpater() {
        progressBar_rqsts.setVisibility(View.GONE);
        //Send Invitation
        if (isRequestSend) {
            sendInvitationTab();
        }else {
            receiveInvitationTab();
        }


    }

    //Receive Invitation Tab
    private void receiveInvitationTab() {
        isRequestReceive=true;
        isRequestSend=false;
        txtv_noreqsts.setVisibility(View.GONE);
        parRecyclerview.setVisibility(View.VISIBLE);
        if (receiveInvitationList != null && receiveInvitationList.size() > 0) {
            partnerAdapter = new PartnerAdapter(getContext(),(ArrayList<EventDetailsModel>) receiveInvitationList,this,isRequestReceive,isRequestSend);
            parRecyclerview.setAdapter(partnerAdapter);
        }else {
            parRecyclerview.setVisibility(View.GONE);
            txtv_noreqsts.setText(R.string.no_tournament_requests_received);
            txtv_noreqsts.setVisibility(View.VISIBLE);
        }
    }
    //Send Invitation Tab
    private void sendInvitationTab() {
        isRequestSend=true;
        isRequestReceive=false;
        txtv_noreqsts.setVisibility(View.GONE);
        parRecyclerview.setVisibility(View.VISIBLE);
        if (sendInvitationList != null && sendInvitationList.size() > 0) {
            partnerAdapter  =  new PartnerAdapter(getContext(), (ArrayList<EventDetailsModel>) sendInvitationList,this,isRequestReceive,isRequestSend);
            parRecyclerview.setAdapter(partnerAdapter);
        }else {
            parRecyclerview.setVisibility(View.GONE);
            txtv_noreqsts.setText(R.string.no_tournament_requests_sent);
            txtv_noreqsts.setVisibility(View.VISIBLE);
        }
    }

    //Get all blocked/unblocked connections
    private void getConnections() {
        connectionList.clear();
        progressBar_msg.setVisibility(View.VISIBLE);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_ALL_CONNECTIONS + user_id + "?status=Active", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject object = response.getJSONObject(i);
                            JSONObject obj = object.getJSONObject("connectedUser");

                            Type type = new TypeToken<BpFinderModel>(){}.getType();
                            BpFinderModel finderModel = new Gson().fromJson(obj.toString(),type);
                            connectionList.add(finderModel);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    setUpMessage();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String json = null;
                progressBar_msg.setVisibility(View.GONE);
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
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + user_token);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;

            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        Log.d("Request", arrayRequest.toString());
        requestQueue.add(arrayRequest);


    }

    //Get connections
    private void getPeopleWhoLiked() {
        likesList.clear();
        JsonArrayRequest arrayRequest = new JsonArrayRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_ALL_CONNECTIONS + user_id + "?status=New&showReceived=true", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject object = response.getJSONObject(i);
                            JSONObject obj = object.getJSONObject("connectedUser");
                            Type mtype = new TypeToken<BpFinderModel>(){}.getType();
                            BpFinderModel mModel = new Gson().fromJson(obj.toString(),mtype);
                            likesList.add(mModel);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    moveToCard();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String json = null;
                progressBar_msg.setVisibility(View.GONE);
                Log.d("error--", error.toString());
                NetworkResponse response = error.networkResponse;
                if (response != null && response.data != null) {
                    switch (response.statusCode) {
                        case 400:
                            json = new String(response.data);
                            json = trimMessage(json, "detail");
                            if (json != null) {
                                Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;

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
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + user_token);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;

            }

        };
        if (getActivity() != null) {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            Log.d("Request", arrayRequest.toString());
            requestQueue.add(arrayRequest);
        }
    }


    private void setUpMessage() {
        userList.clear();
        chatList.clear();
        final Firebase myFirebaseRef = new Firebase("https://beachpartner-6cd7a.firebaseio.com/messages");
        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    chatList.add(postSnapshot.getKey());

                    /*for (DataSnapshot pSnapshot1 : postSnapshot.getChildren()) {
                          MessageModel m = pSnapshot1.getValue(MessageModel.class);
                          if (m.getSender_id().equals(myId)) {
                              userList.add(m);
                          }
                    }*/

                }
                HashSet<String> hashSet = new HashSet<String>();
                hashSet.addAll(chatList);
                chatList.clear();
                userList.clear();
                chatList.addAll(hashSet);

                if (connectionList != null && connectionList.size() > 0) {
                    if (chatList.size() > 0 && chatList != null) {
                        for (int i = 0; i < chatList.size() ; i++) {
                            String chatId = chatList.get(i).split("-")[0];
                            String chatwith_id = chatList.get(i).split("-")[1];
                            if (chatwith_id != null && chatId != null) {
                                if(chatId.equals(user_id) || chatwith_id.equals(user_id)){
                                    if (connectionList.size() > 0 && connectionList != null) {
                                        for (int j=0;j<connectionList.size();j++){
                                            if(chatwith_id.equals(connectionList.get(j).getBpf_id()) || chatId.equals(connectionList.get(j).getBpf_id())){
                                                userList.add(connectionList.get(j));
                                            }
                                        }
                                    }else {
                                        txtv_nomsgs.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        }
                    }
                    progressBar_msg.setVisibility(View.GONE);
                    if (userList.size() > 0) {
                        messageAdapter  =  new MessageAdapter(getContext(),userList);
                        msgRecyclerview.setAdapter(messageAdapter);
                        messageAdapter.notifyDataSetChanged();
                    }else {
                        txtv_nomsgs.setVisibility(View.VISIBLE);
                    }

                }else {
                    progressBar_msg.setVisibility(View.GONE);
                    txtv_nomsgs.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("Firebase ",firebaseError.toString());
                progressBar_msg.setVisibility(View.GONE);
            }
        });

    }

    // GOTO BP FINDER PAGE
    private void moveToCard() {
        if (likesList.size() > 0) {
            if (getActivity() != null) {
                isblueBP = true;
                BPFinderFragment bpFinderFragment =new BPFinderFragment(isblueBP,isPartner);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(AppConstants.NO_LIKES_LIST, likesList);
                bpFinderFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, bpFinderFragment).commit();
            }
        }

    }

    //Method for getting logged user subscriptions
    private void getuserSubscriptions() {
        JsonObjectRequest request = new JsonObjectRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_USER_ACTIVE_PLANS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    Log.d(TAG, "onUserPlansResponse: ");
                    SingleSubscriptionModel subscriptionModel = new Gson().fromJson(response.toString(),SingleSubscriptionModel.class);
                    userSubList = subscriptionModel.getSubItemModel();
                    userAddonsList=subscriptionModel.getAddonsItemModel();
                    saveSubscription();
                    getSubscriptionPlans();//Information for all subscriptions

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + user_token);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        if (getActivity() != null) {
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            Log.d("SubscriptionRequest", queue.toString());
            queue.add(request);
        }

    }



    private void saveSubscription() {
        if (userSubList != null && userSubList.size() > 0) {
            for (int i = 0; i < userSubList.size() ; i++) {
                if (getActivity() != null) {
                    new PrefManager(getContext()).saveSubscription(userSubList.get(i).getPlanName(), userSubList.get(i).getRemainingDays());
                }
            }

        }
    }

    //Get all subscriptions
    private void getSubscriptionPlans() {
        plansModelList.clear();
        JsonArrayRequest arrayRequest = new JsonArrayRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_SUBSCRIPTION_PLANS, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    Log.d(TAG, "onResponse: "+response.toString());
                    Type type = new TypeToken<List<SubscriptonPlansModel>>() {}.getType();
                    plansModelList = new Gson().fromJson(response.toString(),type);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + user_token);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;

            }

        };
        if (getActivity() != null) {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            Log.d("SubscriptionRequest", arrayRequest.toString());
            requestQueue.add(arrayRequest);
        }
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
}

