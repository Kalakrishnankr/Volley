package com.goldemo.beachpartner.fragments;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.adpters.CardAdapter;
import com.goldemo.beachpartner.adpters.MessageAdapter;
import com.goldemo.beachpartner.adpters.PartnerAdapter;
import com.goldemo.beachpartner.adpters.ProfileAdapter;
import com.goldemo.beachpartner.calendar.compactcalendarview.domain.Event;
import com.goldemo.beachpartner.connections.ApiService;
import com.goldemo.beachpartner.connections.PrefManager;
import com.goldemo.beachpartner.models.BpFinderModel;
import com.goldemo.beachpartner.models.EventAdminModel;
import com.goldemo.beachpartner.models.PersonModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private ImageView img_bpprofile,img_send,img_received;
    private FrameLayout likesCard;
    private RecyclerView mRecyclerview,pRecyclerview,msgRecyclerview,parRecyclerview;
    CardAdapter adapter;
    MessageAdapter messageAdapter;
    PartnerAdapter partnerAdapter;
    ProfileAdapter profileAdapter;
    ArrayList<PersonModel> allSampleData;
    private TextView txt_head,txtv_notour;
    private String user_id,user_token,userType;
    private PrefManager prefManager;
    private ArrayList<Event>myUpcomingTList = new ArrayList<>();
    private ArrayList<BpFinderModel> bpList  = new ArrayList<BpFinderModel>();

    public HomeFragment() {
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
        View  view= inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        prefManager =  new PrefManager(getContext());
        user_id     =  prefManager.getUserId();
        user_token  =  prefManager.getToken();
        userType    =  prefManager.getUserType();
        //getBlueBP profes
        getBluebpProfiles();
        getMyTournaments();

        return view;
    }




    private void initView(View view) {

        allSampleData = (ArrayList<PersonModel>) createDummyData();


        //img_bpprofile   =   (ImageView) view.findViewById(R.id.img_bpfinder);
        txt_head        =   (TextView)  view.findViewById(R.id.txtview_head);
        txtv_notour     =   (TextView)  view.findViewById(R.id.txtv_notour);


        img_send        =   (ImageView)view.findViewById(R.id.imgview_send);
        img_received    =   (ImageView)view.findViewById(R.id.imgview_received);
        likesCard       =   (FrameLayout)view.findViewById(R.id.no_of_likes_card);

        pRecyclerview   =   (RecyclerView) view.findViewById(R.id.rrv_topProfile);//This recycler view for top profile picture
        mRecyclerview   =   (RecyclerView)view.findViewById(R.id.rcv);          //Recycler view for upcoming events
        msgRecyclerview =   (RecyclerView)view.findViewById(R.id.rcv_message);  //Recycler view for messages
        parRecyclerview =   (RecyclerView)view.findViewById(R.id.rcv_partners); //Recycler view for tournament requests


        img_send.setOnClickListener(this);
        img_received.setOnClickListener(this);
        likesCard.setOnClickListener(this);



        //Blue BP Adapter class

        LinearLayoutManager lmnger = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        pRecyclerview.setLayoutManager(lmnger);
        pRecyclerview.setHasFixedSize(true);


        /*My upcoming tournaments*/

        LinearLayoutManager layoutmnger = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        mRecyclerview.setLayoutManager(layoutmnger);
        mRecyclerview.setHasFixedSize(true);


        /*Message*/

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        messageAdapter  =  new MessageAdapter(getContext(),allSampleData);
        SnapHelper snaper = new PagerSnapHelper();
        snaper.attachToRecyclerView(msgRecyclerview);
        msgRecyclerview.setLayoutManager(layoutManager);
        msgRecyclerview.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(5), true));
        msgRecyclerview.setItemAnimator(new DefaultItemAnimator());
        msgRecyclerview.setAdapter(messageAdapter);
        msgRecyclerview.setHasFixedSize(true);



        /*Tournament Requests*/

        LinearLayoutManager layoutmngerTwo = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        partnerAdapter  =   new PartnerAdapter(getContext(),allSampleData);
        SnapHelper snap = new PagerSnapHelper();
        snap.attachToRecyclerView(parRecyclerview);
        parRecyclerview.setLayoutManager(layoutmngerTwo);
        parRecyclerview.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(5), true));
        parRecyclerview.setAdapter(partnerAdapter);
        msgRecyclerview.setItemAnimator(new DefaultItemAnimator());
        parRecyclerview.setHasFixedSize(true);


    }

    private List<PersonModel> createDummyData() {

        List<PersonModel>personModelList = new ArrayList<>();
        personModelList.add(new PersonModel("Alivia Orvieto","26",R.drawable.person1));
        personModelList.add(new PersonModel("Marti McLaurin","25",R.drawable.person2));
        personModelList.add(new PersonModel("Liz Held","30",R.drawable.person3));

        personModelList.add(new PersonModel("Alivia Orvieto","26",R.drawable.person1));
        personModelList.add(new PersonModel("Marti McLaurin","25",R.drawable.person2));
        personModelList.add(new PersonModel("Liz Held","30",R.drawable.person3));

        personModelList.add(new PersonModel("Alivia Orvieto","26",R.drawable.person1));
        personModelList.add(new PersonModel("Marti McLaurin","25",R.drawable.person2));
        personModelList.add(new PersonModel("Liz Held","30",R.drawable.person3));

        return personModelList;

    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.imgview_send:
                txt_head.setText("Tournament Requests Sent");
                break;

            case R.id.imgview_received:
                txt_head.setText("Tournament Requests Received");
                break;
            case R.id.no_of_likes_card:
                likesDisplay();
                break;

            default:
                break;
        }
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
        JsonArrayRequest  jsonRequest = new JsonArrayRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_SUBSCRIPTIONS +"?subscriptionType=BlueBP", null, new
                Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response!=null){
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
                                    bpList.add(bpModel);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            setblueBpstrip();
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
                            event.setEventLocation(obj.getString("eventLocation"));
                            event.setEventVenue(obj.getString("eventVenue"));
                            event.setEventStartDate(obj.getLong("eventStartDate"));
                            event.setEventEndDate(obj.getLong("eventEndDate"));
                            event.setEventRegStartdate(obj.getLong("eventRegStartDate"));
                            event.setEventEndDate(obj.getLong("eventRegEndDate"));

                            JSONObject objectadmin = obj.getJSONObject("eventAdmin");
                            EventAdminModel adminModel = new EventAdminModel();
                            adminModel.setFirstName(objectadmin.getString("firstName"));
                            event.setEventAdmin(adminModel);
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
        if(myUpcomingTList.size()>0){
            adapter = new CardAdapter(getContext(),myUpcomingTList);
            mRecyclerview.setAdapter(adapter);
            SnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(mRecyclerview);
        }else {
            txtv_notour.setVisibility(View.VISIBLE);
        }

    }


    private void setblueBpstrip() {
        if(bpList!=null && bpList.size()>0){
            profileAdapter = new ProfileAdapter(getContext(),bpList);
            pRecyclerview.setAdapter(profileAdapter);
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

