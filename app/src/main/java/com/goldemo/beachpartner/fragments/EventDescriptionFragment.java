package com.goldemo.beachpartner.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.calendar.compactcalendarview.domain.Event;

import java.util.Calendar;



public class EventDescriptionFragment extends Fragment implements View.OnClickListener {

    private TextView tview_eventname,tview_location,tview_venue,tview_eventadmin,tview_startDate,tview_endDate,tview_regStart,tview_regClose;
    private Button btnInvitePartner,btnRegister,btnBack;


    public EventDescriptionFragment() {
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
        View view = inflater.inflate(R.layout.fragment_event_description, container, false);

        initActivity(view);

        Bundle bundle = getArguments();
        if(bundle!=null){
            Event event = (Event)bundle.getSerializable("event_clicked");
            tview_eventname.setText(event.getData().toString());
            tview_location.setText(event.getEventLocation());
            tview_startDate.setText(event.getEventStartDate());
            tview_endDate.setText(event.getEventEndDate());
            tview_venue.setText(event.getEventVenue());
//            tview_eventadmin.setText(event.getEventAdmin().toString());
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

        btnInvitePartner        =   (Button)view.findViewById(R.id.btn_invite_partner);
        btnRegister             =   (Button)view.findViewById(R.id.btn_register);
        btnBack                 =   (Button)view.findViewById(R.id.btn_back);



        btnInvitePartner.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnBack.setOnClickListener(this);

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
                AddToPersonalCalendar();
                break;
            case R.id.btn_back:
                //Back button
                getActivity().onBackPressed();

                break;

                default:
                    break;
        }

    }
    private void AddToPersonalCalendar(){
        Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", cal.getTimeInMillis());
        intent.putExtra("allDay", false);
//        intent.putExtra("rrule", "FREQ=DAILY");
        intent.putExtra("endTime",  cal.getTimeInMillis());
        intent.putExtra("title", "DAS");
        startActivityForResult(intent,1);


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
