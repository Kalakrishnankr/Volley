package com.beachpartnerllc.beachpartner.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.adpters.MyNoteAdapter;
import com.beachpartnerllc.beachpartner.calendar.compactcalendarview.domain.Event;
import com.beachpartnerllc.beachpartner.connections.ApiService;
import com.beachpartnerllc.beachpartner.connections.PrefManager;
import com.beachpartnerllc.beachpartner.models.EventAdminModel;
import com.beachpartnerllc.beachpartner.models.InvitationsModel;
import com.beachpartnerllc.beachpartner.models.PartnerResultModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class MyCalendarEvents extends Fragment implements View.OnClickListener {

    private TextView myCal_eventname,myCal_location,myCal_venue,myCal_eventadmin,myCal_startDate,myCal_endDate;
    private Button btn_myCalCourt,btn_myCalBack;
    private RecyclerView rcv_mycalendar;
    private String userType;
    private String eventId;
    private String user_token;

    private MyNoteAdapter myNoteAdapter;
    private ArrayList<PartnerResultModel> model = new ArrayList<>();
    private PartnerResultModel partnerResultModel;


    public MyCalendarEvents() {
        // Required empty public constructor
    }


    public static MyCalendarEvents newInstance(String param1, String param2) {
        MyCalendarEvents fragment = new MyCalendarEvents();
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
        userType = new PrefManager(getContext()).getUserType();
        user_token  = new PrefManager(getContext()).getToken();
        View view = inflater.inflate(R.layout.fragment_mycalendar_events, container, false);
        initViews(view);
        Bundle bundle = getArguments();
        if(bundle!=null){
            Event event = (Event)bundle.getSerializable("mycal_event_clicked");
            eventId = event.getEventId();
            String eventAdmin = event.getEventAdmin();
            myCal_eventadmin.setText(eventAdmin.toString());

            myCal_eventname.setText(event.getEventName());
            myCal_location.setText(event.getEventLocation());
            myCal_venue.setText(event.getEventVenue());

            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
            long start_date = event.getEventStartDate();
            long end_date   = event.getEventEndDate();
            Date eventStart = new Date(start_date);
            Date eventEnd   = new Date(end_date);

            myCal_startDate.setText(dateFormat.format(eventStart));
            myCal_endDate.setText(dateFormat.format(eventEnd));
        }
        return view;
    }

    private void initViews(View view) {

        myCal_eventname     =   (TextView) view.findViewById(R.id.my_event_name);
        myCal_location      =   (TextView) view.findViewById(R.id.my_tv_location);
        myCal_venue         =   (TextView) view.findViewById(R.id.my_tv_venue);

        myCal_eventadmin    =   (TextView) view.findViewById(R.id.my_tv_admin);
        myCal_startDate     =   (TextView) view.findViewById(R.id.my_tv_startDate);
        myCal_endDate       =   (TextView) view.findViewById(R.id.my_tv_endDate);

        btn_myCalCourt      =   (Button) view.findViewById(R.id.my_btn_invite_partner);
        btn_myCalBack       =   (Button) view.findViewById(R.id.my_btn_back);

        rcv_mycalendar      =   (RecyclerView) view.findViewById(R.id.rcv_partner_notes);


//        Button change according to coach or athlete
        btn_myCalCourt.setText("Check In");
        btn_myCalBack.setText("Decline");

        myNoteAdapter       =   new MyNoteAdapter(getContext(),partnerResultModel);
        rcv_mycalendar.setAdapter(myNoteAdapter);

        btn_myCalCourt.setOnClickListener(this);
        btn_myCalBack.setOnClickListener(this);





    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getEvent(eventId);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.my_btn_invite_partner:
                courtAlert();
                break;
            case R.id.my_btn_back:
                getActivity().onBackPressed();
                break;

                default:
                    break;

        }

    }


    private void courtAlert() {

        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater   = getActivity().getLayoutInflater();
        View layout     = inflater.inflate(R.layout.alert_notify_court,null);
        alert.setView(layout);
        final AlertDialog alertDialog  = alert.create();

        final EditText txtv_notify  =   (EditText) layout.findViewById(R.id.edt_notify);
        final Button   btn_cancel   =   (Button) layout.findViewById(R.id.btn_ntfy_cancel);
        final Button   btn_notify   =   (Button) layout.findViewById(R.id.btn_ntfy_ok);

        /*button notify*/
        btn_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!((txtv_notify.getText().toString().trim().length()) == 0)){

                    //something do here
                    alertDialog.cancel();

                }else {
                    Toast.makeText(getActivity(), "Please enter your court number", Toast.LENGTH_SHORT).show();
                }

            }
        });

        /*button cancel*/

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.cancel();

            }
        });
        alertDialog.show();

    }

    public void getEvent(String event_Id) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_INVITATION_LIST+event_Id+"?calendarType=\"mycalendar",null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject obj = null;
                if(response!=null){
                    try{
                        InvitationsModel invitationsModel = new Gson().fromJson(response.toString(), InvitationsModel.class);
                        obj = response.getJSONObject("invitationList");
                        Type type = new TypeToken<PartnerResultModel>(){}.getType();
                            partnerResultModel = new Gson().fromJson(obj.toString(),type);

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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
}
