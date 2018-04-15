package com.beachpartnerllc.beachpartner.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.calendar.compactcalendarview.domain.Event;
import com.beachpartnerllc.beachpartner.connections.ApiService;
import com.beachpartnerllc.beachpartner.connections.PrefManager;
import com.beachpartnerllc.beachpartner.models.EventAdminModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class EventDescriptionFragment extends Fragment implements View.OnClickListener {

    private TextView tview_eventname,tview_location,tview_venue,tview_eventadmin,tview_startDate,tview_endDate,tview_regStart,tview_regClose;
    private Button btnInvitePartner,btnRegister,btnBack,btnCoachGoing,btnCoachNotGoing;
    private PrefManager prefManager;
    private LinearLayout athleteBtnLt,coachBtnLt;
    private String user_id,user_token,userType,eventName,eventId;
    private Boolean registerCompleted = false;


    public EventDescriptionFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Create","onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Create","onCreateView");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_description, container, false);
        prefManager = new PrefManager(getContext());
        user_id     =  prefManager.getUserId();
        user_token  =  prefManager.getToken();
        userType    =  prefManager.getUserType();

        initActivity(view);

        Bundle bundle = getArguments();
        if(bundle!=null){
            Event event = (Event)bundle.getSerializable("event_clicked");
            EventAdminModel eventAdminModel = event.getEventAdmin();
            eventId=event.getEventId();
            tview_eventadmin.setText(eventAdminModel.getFirstName().toString());

            tview_eventname.setText(event.getEventName().toString());
            eventName   =   event.getEventName().toString();
            tview_location.setText(event.getEventLocation());

            SimpleDateFormat dft = new SimpleDateFormat("MMM dd, yyyy");
            long event_start    = event.getEventStartDate();
            long event_enddate  = event.getEventEndDate();
            long event_regStart = event.getEventRegStartdate();
            long event_regEnd   = event.getEventRegEnddate();
            Date date   = new Date(event_start);
            Date e_end  = new Date(event_enddate);
            Date reg_startDate = new Date(event_regStart);
            Date reg_endDate   = new Date(event_regEnd);
            tview_startDate.setText(dft.format(date));
            tview_endDate.setText(dft.format(e_end));
            tview_regStart.setText(dft.format(reg_startDate));
            tview_regClose.setText(dft.format(reg_endDate));
            tview_venue.setText(event.getEventVenue());
        }

        return view;
    }

    private void initActivity(View view) {

        tview_eventname         =   (TextView)view.findViewById(R.id.event_name);
        tview_location          =   (TextView)view.findViewById(R.id.tv_location);
        tview_venue             =   (TextView)view.findViewById(R.id.tv_venue);
        tview_eventadmin        =   (TextView)view.findViewById(R.id.tv_admin);

        tview_startDate         =   (TextView)view.findViewById(R.id.tv_startDate);
        tview_endDate           =   (TextView)view.findViewById(R.id.tv_endDate);

        tview_regStart          =   (TextView)view.findViewById(R.id.start_date);
        tview_regClose          =   (TextView)view.findViewById(R.id.deadline);

        athleteBtnLt            =   (LinearLayout)view.findViewById(R.id.athleteButtonsLt);
        coachBtnLt              =   (LinearLayout)view.findViewById(R.id.coachButtonsLt);
        btnInvitePartner        =   (Button)view.findViewById(R.id.btn_invite_partner);
        btnRegister             =   (Button)view.findViewById(R.id.btn_register);
        btnBack                 =   (Button)view.findViewById(R.id.btn_back);
        btnCoachGoing           =   (Button)view.findViewById(R.id.coach_going_btn);
        btnCoachNotGoing        =   (Button)view.findViewById(R.id.coach_notgoing_btn);

        if(userType.equalsIgnoreCase("Athlete")){
            athleteBtnLt.setVisibility(View.VISIBLE);
            coachBtnLt.setVisibility(View.GONE);
        }
        if(userType.equalsIgnoreCase("Coach")){
            athleteBtnLt.setVisibility(View.GONE);
            coachBtnLt.setVisibility(View.VISIBLE);
        }


        btnInvitePartner.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnCoachNotGoing.setOnClickListener(this);
        btnCoachGoing.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_invite_partner:
                //call partner Invite Page
                PartnerInviteFragmentTab fragment   =   new PartnerInviteFragmentTab();
                FragmentTransaction transaction     =   getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container,fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.btn_register:
                registerCompleted=true;
                RegistrationFragment registrationFragment = new RegistrationFragment();
                Bundle args = new Bundle();
                args.putString("Url", "http://aaubeach.org/");
                registrationFragment.setArguments(args);
                FragmentManager mng = getActivity().getSupportFragmentManager();
                FragmentTransaction tran = mng.beginTransaction().addToBackStack(null);
                tran.replace(R.id.container, registrationFragment);
                tran.commit();
                break;
            case R.id.btn_back:
                //Back button
                getActivity().onBackPressed();

                break;
            case R.id.coach_going_btn:
                alertAddToSystemCalendarCoach();
                break;
            case R.id.coach_notgoing_btn:
                //Back button
                getActivity().onBackPressed();
                break;

                default:
                    break;
        }

    }

    private void registerEvent(JSONObject object) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(ApiService.REQUEST_METHOD_POST, ApiService.EVENT_REGISTER, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            Toast.makeText(getActivity(), "User Details Posted Successfully", Toast.LENGTH_SHORT).show();
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
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + user_token);
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        Log.d("Request", jsonObjectRequest.toString());
        requestQueue.add(jsonObjectRequest);

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

    @Override
    public void onResume() {
        super.onResume();
        if(registerCompleted){
            alertAddToSystemCalendar();
            registerCompleted=false;
        }
        Log.d("Create","onResume");


    }







    private void addToSystemCalendar(){
        Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", cal.getTimeInMillis());
        intent.putExtra("allDay", false);
//        intent.putExtra("rrule", "FREQ=DAILY");
        intent.putExtra("endTime",  cal.getTimeInMillis());
        intent.putExtra("title", eventName);
        startActivityForResult(intent,1);


    }

    private void alertAddToSystemCalendar() {
        //Register Event Api
        JSONObject registerObject =new JSONObject();
        try{
            registerObject.put("eventId",eventId);
            registerObject.put("userId",user_id);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        registerEvent(registerObject);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add to Your Calendar")
                .setMessage("Would you like to add it to your Device's calendar?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addToSystemCalendar();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.cancel();
                    }
                });
        AlertDialog dialog =  builder.create();
        registerCompleted=false;
        dialog.show();

    }


    private void alertAddToSystemCalendarCoach() {
        //Register Event Api
        JSONObject registerObject =new JSONObject();
        try{
            registerObject.put("eventId",eventId);
            registerObject.put("userId",user_id);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        registerEvent(registerObject);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add to Your Calendar")
                .setMessage("Would you like to add it to your calendar?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addToSystemCalendar();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.cancel();
                    }
                });
        AlertDialog dialog =  builder.create();
        dialog.show();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                Toast.makeText(getContext(), ""+requestCode, Toast.LENGTH_SHORT).show();


            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }




}