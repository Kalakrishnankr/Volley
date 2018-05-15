package com.beachpartnerllc.beachpartner.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.adpters.MyNoteAdapter;
import com.beachpartnerllc.beachpartner.calendar.compactcalendarview.domain.Event;
import com.beachpartnerllc.beachpartner.connections.PrefManager;
import com.beachpartnerllc.beachpartner.models.EventAdminModel;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MyCalendarEvents extends Fragment implements View.OnClickListener {

    private TextView myCal_eventname,myCal_location,myCal_venue,myCal_eventadmin,myCal_startDate,myCal_endDate;
    private Button btn_myCalCourt,btn_myCalBack;
    private RecyclerView rcv_mycalendar;
    private String userType;
    private String eventId;

    private MyNoteAdapter myNoteAdapter;



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

        //myNoteAdapter       =   new MyNoteAdapter(getContext(),list);
        rcv_mycalendar.setAdapter(myNoteAdapter);

        btn_myCalCourt.setOnClickListener(this);
        btn_myCalBack.setOnClickListener(this);




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
}
