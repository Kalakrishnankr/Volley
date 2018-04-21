package com.beachpartnerllc.beachpartner.fragments;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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


public class CoachHomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FrameLayout coachHomeLikesCard;
    private String user_id,user_token,userType,userSubscription;
    CardAdapter adapter;
    MessageAdapter messageAdapter;
    ProfileAdapter profileAdapter;
    private PrefManager prefManager;
    private RecyclerView upcomingRecyclerview,coachMsgRecyclerview,pRecyclerview;
    private ArrayList<Event>myUpcomingTList = new ArrayList<>();

    private TextView txtv_notour,txtv_nomessgs;
    private ArrayList<ConnectionModel> connectionList = new ArrayList<>();
    private ArrayList<String> chatCoachList = new ArrayList<>();
    private ArrayList<ConnectionModel> userCoachList = new ArrayList<>();
    private ArrayList<BpFinderModel> bpList  = new ArrayList<BpFinderModel>();
    private ArrayList<BpFinderModel> premiumLikesList  = new ArrayList<BpFinderModel>();



    public CoachHomeFragment() {
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
        prefManager = new PrefManager(getContext());
        user_id     =  prefManager.getUserId();
        user_token  =  prefManager.getToken();
        userType    = prefManager.getUserType();
        userSubscription = prefManager.getSubscription();

        View view=inflater.inflate(R.layout.fragment_coach_home, container, false);
        getBluebpProfiles();
        getMyTournaments();
        getConnections();

        initView(view);
        return view;

    }


    private void initView(View view){

        upcomingRecyclerview    =   (RecyclerView)view.findViewById(R.id.rcvUpComing);          //Recycler view for upcoming events
        coachMsgRecyclerview    =   (RecyclerView)view.findViewById(R.id.rcv_message);  //Recycler view for messages
        pRecyclerview           =   (RecyclerView) view.findViewById(R.id.rrv_topProfile);
        coachHomeLikesCard      =   (FrameLayout)view.findViewById(R.id.no_of_likes_card);

        txtv_notour            =   (TextView)  view.findViewById(R.id.txtv_notour);
        txtv_nomessgs          =   (TextView) view.findViewById(R.id.txtv_messgs);


        LinearLayoutManager layoutmnger = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);

        adapter = new CardAdapter(getContext(),myUpcomingTList);

        upcomingRecyclerview.setAdapter(adapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(upcomingRecyclerview);
        upcomingRecyclerview.setLayoutManager(layoutmnger);
        upcomingRecyclerview.setHasFixedSize(true);


        /*Message*/

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        SnapHelper snaper = new PagerSnapHelper();
        snaper.attachToRecyclerView(coachMsgRecyclerview);
//        coachMsgRecyclerview.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(5), true));
//        coachMsgRecyclerview.setItemAnimator(new DefaultItemAnimator());
        coachMsgRecyclerview.setLayoutManager(layoutManager);
        coachMsgRecyclerview.setHasFixedSize(true);


        //Blue BP Adapter class

        LinearLayoutManager lmnger = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        pRecyclerview.setLayoutManager(lmnger);
        pRecyclerview.setHasFixedSize(true);

        coachHomeLikesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(userSubscription.equalsIgnoreCase("null")){
                    likesDisplay();
                }
                else{
                    setLikesList();
                }

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
            public Map<String, String> getHeaders() throws AuthFailureError {
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

    //Get blue Bp profiles
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
                            //new PrefManager(getContext()).savePageno(0);
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

    private void getLikesList() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_ALL_CONNECTIONS  + user_id + "?status=" + "New" , null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if(response!=null){
                    for(int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            JSONObject obj    = jsonObject.getJSONObject("connectedUser");
                            BpFinderModel bpModel = new BpFinderModel();
                            bpModel.setBpf_id(jsonObject.getString("id"));
                            bpModel.setBpf_firstName(jsonObject.getString("firstName"));
                            bpModel.setBpf_imageUrl(jsonObject.getString("imageUrl"));
                            bpModel.setBpf_videoUrl(jsonObject.getString("videoUrl"));
                            bpModel.setBpf_userType(jsonObject.getString("userType"));
                            bpModel.setBpf_age(jsonObject.getString("age"));
                            bpModel.setBpf_daysToExpireSubscription(obj.getString("daysToExpireSubscription"));
                            bpModel.setBpf_effectiveDate(obj.getString("effectiveDate"));
                            bpModel.setBpf_termDate(obj.getString("termDate"));
                            bpModel.setBpf_subscriptionType(obj.getString("subscriptionType"));
                            premiumLikesList.add(bpModel);


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
        }){ @Override
        public Map<String, String> getHeaders()  {
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
    private void setblueBpstrip() {
        if(bpList!=null && bpList.size()>0){
            profileAdapter = new ProfileAdapter(getContext(),bpList);
            pRecyclerview.setAdapter(profileAdapter);
        }
    }
    private void setLikesList(){
        getLikesList();
        if(premiumLikesList!=null && premiumLikesList.size()>0){
            boolean isblueBP = false;
            boolean isPartner = true;
            AppCompatActivity activity = (AppCompatActivity) getContext();
            BPFinderFragment bpFinderFragment =new BPFinderFragment(isblueBP,isPartner);
            Bundle bundle = new Bundle();
            bundle.putSerializable("bluebplist", premiumLikesList);
            bpFinderFragment.setArguments(bundle);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, bpFinderFragment).commit();
        }

    }

    private void setUpMyComingTournament() {
        if(myUpcomingTList.size()>0){
            adapter = new CardAdapter(getContext(),myUpcomingTList);
            upcomingRecyclerview.setAdapter(adapter);
            SnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(upcomingRecyclerview);
        }else {
            txtv_notour.setVisibility(View.VISIBLE);
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


    //Get connections
    private void getConnections() {
        connectionList.clear();
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
                    setUpCoachMessage();
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

    private void setUpCoachMessage() {

        userCoachList.clear();
        chatCoachList.clear();
        final Firebase myFirebaseRef = new Firebase("https://beachpartner-be21e.firebaseio.com/messages");
        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    chatCoachList.add(postSnapshot.getKey());

                    /*for (DataSnapshot pSnapshot1 : postSnapshot.getChildren()) {
                          MessageModel m = pSnapshot1.getValue(MessageModel.class);
                          if (m.getSender_id().equals(myId)) {
                              userList.add(m);
                          }
                    }*/

                }
                if(chatCoachList.size()>0 && chatCoachList!=null){
                    for (int i=0;i<chatCoachList.size();i++){
                        String chatId = chatCoachList.get(i).split("-")[0];
                        String chatwith_id = chatCoachList.get(i).split("-")[1];
                        if(chatId.equals(user_id) || chatwith_id.equals(user_id)){
                            for (int j=0;j<connectionList.size();j++){
                                if(chatwith_id.equals(connectionList.get(j).getConnected_uId()) || chatId.equals(connectionList.get(j).getConnected_uId())){
                                    userCoachList.add(connectionList.get(j));
                                }
                            }
                        }
                    }
                    messageAdapter  =  new MessageAdapter(getContext(),userCoachList);
                    coachMsgRecyclerview.setAdapter(messageAdapter);
                    messageAdapter.notifyDataSetChanged();
                }else {
                    txtv_nomessgs.setVisibility(View.VISIBLE);
                }



            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

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
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
