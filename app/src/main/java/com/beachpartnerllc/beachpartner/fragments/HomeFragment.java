package com.beachpartnerllc.beachpartner.fragments;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.adpters.CardAdapter;
import com.beachpartnerllc.beachpartner.adpters.MessageAdapter;
import com.beachpartnerllc.beachpartner.adpters.PartnerAdapter;
import com.beachpartnerllc.beachpartner.adpters.ProfileAdapter;
import com.beachpartnerllc.beachpartner.calendar.compactcalendarview.domain.Event;
import com.beachpartnerllc.beachpartner.connections.ApiService;
import com.beachpartnerllc.beachpartner.connections.PrefManager;
import com.beachpartnerllc.beachpartner.models.BpFinderModel;
import com.beachpartnerllc.beachpartner.models.ConnectionModel;
import com.beachpartnerllc.beachpartner.models.EventAdminModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
    private TextView txt_head,txtv_notour,txtv_nomsgs,txtv_noreqsts,txtv_nobp,txtv_likes;
    private ProgressBar progressBar,progressBar_tour,progressBar_msg,progressBar_rqsts;
    private String user_id,user_token,userType,no_likes_count;
    private PrefManager prefManager;
    private LinearLayout ucoming_next,message_next,request_next;
    private LinearLayoutManager layoutManagerBluebp,layoutManagerUp,layoutManagerMsg,layoutmngerReqst;
    private ArrayList<Event>myUpcomingTList = new ArrayList<>();
    private ArrayList<BpFinderModel> bpList  = new ArrayList<BpFinderModel>();
    private ArrayList<ConnectionModel> connectionList = new ArrayList<>();
    private ArrayList<String> chatList = new ArrayList<>();
    private ArrayList<ConnectionModel> likesList = new ArrayList<>();
    private ArrayList<ConnectionModel> userList = new ArrayList<>();


    public HomeFragment() {
        // Required empty public constructor

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(getActivity());
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
        getConnections();
        getRequests();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getPeopleWhoLiked();
    }

    private void initView(View view) {

        //Next buttons
        ucoming_next    =   (LinearLayout)view.findViewById(R.id.upcome_next_button);
        message_next    =   (LinearLayout)view.findViewById(R.id.mess_next_button);
        request_next    =   (LinearLayout)view.findViewById(R.id.req_next_button);

        progressBar     =   (ProgressBar)view.findViewById(R.id.progress);
        progressBar_tour=   (ProgressBar)view.findViewById(R.id.progress_tournament);
        progressBar_msg =   (ProgressBar)view.findViewById(R.id.progress_msg);
        progressBar_rqsts=  (ProgressBar)view.findViewById(R.id.progress_request);


        //img_bpprofile   =   (ImageView) view.findViewById(R.id.img_bpfinder);
        txt_head        =   (TextView)view.findViewById(R.id.txtview_head);
        txtv_notour     =   (TextView)view.findViewById(R.id.txtv_notour_athlete);
        txtv_nomsgs     =   (TextView)view.findViewById(R.id.txtv_nomessgs);
        txtv_noreqsts   =   (TextView)view.findViewById(R.id.txtv_noreqsts);
        txtv_nobp       =   (TextView)view.findViewById(R.id.txtv_nobp);


        img_send        =   (ImageView)view.findViewById(R.id.imgview_send);
        img_received    =   (ImageView)view.findViewById(R.id.imgview_received);
        likesCard       =   (FrameLayout)view.findViewById(R.id.no_of_likes_card);
        txtv_likes      =   (TextView)view.findViewById(R.id.txtv_likes_athlete);

        pRecyclerview   =   (RecyclerView) view.findViewById(R.id.rrv_topProfile);//This recycler view for top profile picture
        mRecyclerview   =   (RecyclerView)view.findViewById(R.id.rcv);          //Recycler view for upcoming events
        msgRecyclerview =   (RecyclerView)view.findViewById(R.id.rcv_message);  //Recycler view for messages
        parRecyclerview =   (RecyclerView)view.findViewById(R.id.rcv_partners); //Recycler view for tournament requests


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
        SnapHelper snaper = new PagerSnapHelper();
        snaper.attachToRecyclerView(msgRecyclerview);
        msgRecyclerview.setLayoutManager(layoutManagerMsg);
        msgRecyclerview.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(5), true));
        msgRecyclerview.setItemAnimator(new DefaultItemAnimator());
        msgRecyclerview.setHasFixedSize(true);



        /*Tournament Requests*/

        layoutmngerReqst = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        //partnerAdapter  =   new PartnerAdapter(getContext(),allSampleData);
        SnapHelper snap = new PagerSnapHelper();
        snap.attachToRecyclerView(parRecyclerview);
        parRecyclerview.setLayoutManager(layoutmngerReqst);
        parRecyclerview.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(5), true));
        //parRecyclerview.setAdapter(partnerAdapter);
        parRecyclerview.setItemAnimator(new DefaultItemAnimator());
        parRecyclerview.setHasFixedSize(true);

        //setting the no. of likes inside the card

        if(no_likes_count==null){
            txtv_likes.setText("No");
        }
        else{
            txtv_likes.setText(no_likes_count);
        }



    }




    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.imgview_send:
                txt_head.setText("Tournament Requests Sent");
                txtv_noreqsts.setText("No requests send");
                break;

            case R.id.imgview_received:
                txt_head.setText("Tournament Requests Received");
                txtv_noreqsts.setText("No requests received");
                break;
            case R.id.no_of_likes_card:
                likesDisplay();
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


        if(userType=="Athlete"){

            boolean isblueBP=true;
            boolean isPartner=false;
            BPFinderFragment bpFinderFragment =new BPFinderFragment(isblueBP,isPartner);
            Bundle bundle = new Bundle();
            //cPosition is the current positon

            bundle.putSerializable("bluebplist", likesList);
            bpFinderFragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, bpFinderFragment).commit();
        }
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
                                    bpModel.setBpf_dob(jsonObject.getString("dob"));
                                    bpModel.setBpf_fcmToken(jsonObject.getString("fcmToken"));
                                    bpModel.setBpf_deviceId(jsonObject.getString("deviceId"));
                                    bpModel.setBpf_daysToExpireSubscription(object.getString("daysToExpireSubscription"));
                                    bpModel.setBpf_effectiveDate(object.getString("effectiveDate"));
                                    bpModel.setBpf_termDate(object.getString("termDate"));
                                    bpModel.setBpf_subscriptionType(object.getString("subscriptionType"));
                                    bpList.add(bpModel);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            if(getActivity()!=null){
                                //new PrefManager(getActivity()).savePageno(0);
                                setblueBpstrip();
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
        }else {
            txtv_nobp.setVisibility(View.VISIBLE);
        }

    }

    //Get connections
    private void getConnections() {
        connectionList.clear();
        progressBar_msg.setVisibility(View.VISIBLE);
        String user_id = new PrefManager(getContext()).getUserId();
        final String token = new PrefManager(getContext()).getToken();

        JsonArrayRequest arrayRequest = new JsonArrayRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_ALL_CONNECTIONS + user_id + "?status=Active", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject object = response.getJSONObject(i);
                            JSONObject obj = object.getJSONObject("connectedUser");
                            ConnectionModel model = new ConnectionModel();
                            model.setConnected_uId(obj.getString("id"));
                            model.setConnected_login(obj.getString("login"));
                            model.setConnected_firstName(obj.getString("firstName"));
                            model.setConnected_lastName(obj.getString("lastName"));
                            model.setConnected_email(obj.getString("email"));
                            model.setConnected_userType(obj.getString("userType"));
                            model.setConnected_imageUrl(obj.getString("imageUrl"));
                            connectionList.add(model);


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

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
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

        String user_id = new PrefManager(getContext()).getUserId();
        final String token = new PrefManager(getContext()).getToken();

        JsonArrayRequest arrayRequest = new JsonArrayRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_ALL_CONNECTIONS + user_id + "?status=New", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if (response != null) {
                    no_likes_count= String.valueOf(response.length());
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject object = response.getJSONObject(i);
                            JSONObject obj = object.getJSONObject("connectedUser");
                            ConnectionModel model = new ConnectionModel();
                            model.setConnected_uId(obj.getString("id"));
                            model.setConnected_login(obj.getString("login"));
                            model.setConnected_firstName(obj.getString("firstName"));
                            model.setConnected_lastName(obj.getString("lastName"));
                            model.setConnected_email(obj.getString("email"));
                            model.setConnected_userType(obj.getString("userType"));
                            model.setConnected_imageUrl(obj.getString("imageUrl"));
                            likesList.add(model);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;

            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        Log.d("Request", arrayRequest.toString());
        requestQueue.add(arrayRequest);


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
                if(chatList.size()>0 && chatList!=null){
                    for (int i=0;i<chatList.size();i++){
                        String chatId = chatList.get(i).split("-")[0];
                        String chatwith_id = chatList.get(i).split("-")[1];
                        if (chatwith_id != null && chatId != null) {
                            if(chatId.equals(user_id) || chatwith_id.equals(user_id)){
                                if (connectionList.size() > 0 && connectionList != null) {
                                    for (int j=0;j<connectionList.size();j++){
                                        if(chatwith_id.equals(connectionList.get(j).getConnected_uId()) || chatId.equals(connectionList.get(j).getConnected_uId())){
                                            userList.add(connectionList.get(j));
                                        }
                                    }
                                }else {
                                    txtv_nomsgs.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                    }
                    progressBar_msg.setVisibility(View.GONE);
                    messageAdapter  =  new MessageAdapter(getContext(),userList);
                    msgRecyclerview.setAdapter(messageAdapter);
                    messageAdapter.notifyDataSetChanged();
                }else {
                    progressBar_msg.setVisibility(View.GONE);
                    txtv_nomsgs.setVisibility(View.VISIBLE);
                }



            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    //
    private void getRequests() {

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

