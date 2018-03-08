package com.goldemo.beachpartner.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.adpters.EventsAdapter;
import com.goldemo.beachpartner.calendar.compactcalendarview.CompactCalendarView;
import com.goldemo.beachpartner.calendar.compactcalendarview.domain.Event;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;


public class CalendarFragment extends Fragment implements View.OnClickListener {

    private static final String TAG ="CalendarFragment" ;
    private TextView tview_master,tview_mycalendar,tview_month,tview_date;
    private ImageButton btn_previous,btn_next;
    CompactCalendarView compactCalendarView;
    private RecyclerView rview;
    EventsAdapter eventsAdapter;
    public Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.getDefault());
    SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
    String toDayDate;
    public List<Event>eventList,sList;

    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           // mParam1 = getArguments().getString(ARG_PARAM1);
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        initActivity(view);

        return  view;
    }

    private void initActivity(View view) {

        tview_master        =   (TextView)view.findViewById(R.id.txtMaster);
        tview_mycalendar    =   (TextView)view.findViewById(R.id.txtMycalendar);

        tview_month         =   (TextView)view.findViewById(R.id.month_name);
        tview_date          =   (TextView)view.findViewById(R.id.tview_date);
        btn_previous        =   (ImageButton)view.findViewById(R.id.prev_button);
        btn_next            =   (ImageButton)view.findViewById(R.id.next_button);

        rview               =   (RecyclerView)view.findViewById(R.id.rcv_events);

        compactCalendarView = (CompactCalendarView) view.findViewById(R.id.compactcalendar);
        compactCalendarView.setFirstDayOfWeek(Calendar.SUNDAY);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        toDayDate        = DateFormat.getDateTimeInstance().format(new Date());


        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rview.setLayoutManager(manager);
        rview.setHasFixedSize(true);

        tview_master.setOnClickListener(this);
        tview_mycalendar.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        btn_previous.setOnClickListener(this);



        //load events for today date
        activeMasterTab();


        loadEventsData();
        loadEventsForYear(2018);
        compactCalendarView.invalidate();

        logEventsByMonth(compactCalendarView);

        sList   = compactCalendarView.getEventsForMonth(new Date());

        final List<Event>toDayEvents = new ArrayList<>();
        toDayEvents.clear();
        if(sList!=null){
            for(int i = 0; i<sList.size(); i++){

               if(DatetoMilli(new Date())==(sList.get(i).getTimeInMillis())){
                   toDayEvents.add(sList.get(i));
                }
            }

        }
        eventsAdapter       = new EventsAdapter(getContext(),toDayEvents);
        rview.setAdapter(eventsAdapter);



        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                tview_date.setText(dateFormat.format(dateClicked));

                List<Event> bookingsFromMap = compactCalendarView.getEvents(dateClicked);

                toDayEvents.clear();
                if(bookingsFromMap!=null){

                    for(int i=0;i<bookingsFromMap.size();i++){

                        if((DatetoMilli(dateClicked))==(bookingsFromMap.get(i).getTimeInMillis())){
                            toDayEvents.add(bookingsFromMap.get(i));
                        }
                    }
                    eventsAdapter = new EventsAdapter(getContext(),toDayEvents);
                    rview.setAdapter(eventsAdapter);
                    eventsAdapter.notifyDataSetChanged();

                }





               }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                //Log.d(TAG, "Month was scrolled to: " + firstDayOfNewMonth);
                tview_month.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
            }


        });
        tview_month.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
        tview_date.setText(dateFormat.format(new Date()));




    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.txtMaster:
                activeMasterTab();
                break;

            case R.id.txtMycalendar:
                activeMycalendarTab();
                break;

            case R.id.next_button:
                compactCalendarView.showNextMonth();
                break;

            case R.id.prev_button:
                compactCalendarView.showPreviousMonth();
                break;

            default:
                break;
        }

    }


    /*private List<EventModel> loadEvents() {

        List<EventModel>eventList = new ArrayList<>();

        eventList.add(new EventModel("RED","10/03/2018","11.00 PM","Beach Volley Team Tournament"));
        eventList.add(new EventModel("BLUE","11/03/2018","12.00 PM","Rasis college level volley"));
        eventList.add(new EventModel("GREEN","12/03/2018","11.00 PM","Temp Coaches original volley tournament"));
        eventList.add(new EventModel("YELLOW","10/03/2018","11.00 PM","Temp Coaches original volley tournament ABC"));
        eventList.add(new EventModel("RED","11/03/2018","12.00 PM","Pottstown Rumble"));
        eventList.add(new EventModel("BLUE","12/03/2018","11.00 PM","Temp Coaches original volley tournament"));
        eventList.add(new EventModel("GREEN","10/03/2018","11.00 PM","Temp Coaches original volley tournament ABC"));
        eventList.add(new EventModel("YELLOW","11/03/2018","12.00 PM","Pottstown Rumble"));
        eventList.add(new EventModel("RED","06/03/2018","11.00 PM","Temp Coaches original volley tournament"));
        eventList.add(new EventModel("BLUE","06/03/2018","11.00 PM","Temp Coaches original volley tournament ABC"));
        eventList.add(new EventModel("GREEN","06/03/2018","12.00 PM","Pottstown Rumble"));
        eventList.add(new EventModel("YELLOW","12/03/2018","11.00 PM","Temp Coaches original volley tournament"));
        eventList.add(new EventModel("RED","10/03/2018","11.00 PM","Temp Coaches original volley tournament ABC"));
        eventList.add(new EventModel("BLUE","11/03/2018","12.00 PM","Pottstown Rumble"));
        eventList.add(new EventModel("GREEN","12/03/2018","11.00 PM","Temp Coaches original volley tournament"));
        eventList.add(new EventModel("YELLOW","10/03/2018","11.00 PM","Temp Coaches original volley tournament ABC"));
        eventList.add(new EventModel("BLACK","09/03/2018","12.00 PM","Pottstown Rumble"));

        //compactCalendarView.addEvents(eventList);
        return eventList;


    }*/








    private void activeMasterTab() {
        tview_master.setTextColor(getResources().getColor(R.color.blueDark));
        tview_master.setBackgroundColor(getResources().getColor(R.color.white));
        tview_mycalendar.setBackgroundColor(0);
        tview_mycalendar.setTextColor(getResources().getColor(R.color.white));

    }

    private void activeMycalendarTab() {
        tview_mycalendar.setTextColor(getResources().getColor(R.color.blueDark));
        tview_mycalendar.setBackgroundColor(getResources().getColor(R.color.white));
        tview_master.setBackgroundColor(0);
        tview_master.setTextColor(getResources().getColor(R.color.white));

    }

    private void loadEventsData() {
        addEvents(-1, -1);
        addEvents(Calendar.MARCH, -1);
        addEvents(Calendar.APRIL, -1);
    }

    private void loadEventsForYear(int year) {
        addEvents(Calendar.DECEMBER, year);
        addEvents(Calendar.AUGUST, year);
    }

    private void logEventsByMonth(CompactCalendarView compactCalendarView) {
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1);
        List<String> dates = new ArrayList<>();
        for (Event e : compactCalendarView.getEventsForMonth(new Date())) {
            dates.add(dateFormatForDisplaying.format(e.getTimeInMillis()));
        }
        Log.d(TAG, "Events for Aug with simple date formatter: " + dates);
        Log.d(TAG, "Events for Aug month using default local and timezone: " + compactCalendarView.getEventsForMonth(currentCalender.getTime()));
    }

    private void addEvents(int month, int year) {
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = currentCalender.getTime();
        for (int i = 0; i < 6; i++) {
            currentCalender.setTime(firstDayOfMonth);
            if (month > -1) {
                currentCalender.set(Calendar.MONTH, month);
            }
            if (year > -1) {
                currentCalender.set(Calendar.ERA, GregorianCalendar.AD);
                currentCalender.set(Calendar.YEAR, year);
            }
            currentCalender.add(Calendar.DATE, i);
            setToMidnight(currentCalender);
            long timeInMillis = currentCalender.getTimeInMillis();

            List<Event> events = getEvents(timeInMillis, i);

            compactCalendarView.addEvents(events);
        }
    }


    //adding Events
    private List<Event> getEvents(long timeInMillis, int day) {
        if (day < 2) {
            return Arrays.asList(new Event(Color.argb(255, 0, 0, 0), timeInMillis, "Beach Volley Team Tournament"));
        } else if (day > 2 && day <= 4) {
            return Arrays.asList(
                    new Event(Color.argb(255, 0, 0, 255), timeInMillis, "Canadian Volley TechTour"),
                    new Event(Color.argb(255, 100, 68, 65), timeInMillis, "Rasis college level volley"));
        } else {
            return Arrays.asList(
                    new Event(Color.argb(255, 255, 255, 0), timeInMillis, "Temp Coaches original volley tournament"),
                    new Event(Color.argb(255, 0, 255, 0), timeInMillis, "Breckenridge Doubles (aka Putterhead Doubles)"),
                    new Event(Color.argb(255, 255, 0, 255), timeInMillis, "Pottstown Rumble"));
        }
    }

    private void setToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }


    public long DatetoMilli(Date dateClicked){
        Date givenDateString = dateClicked;
        long timeInMilliseconds = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        try {
            Date mDate = sdf.parse(String.valueOf(givenDateString));
            timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeInMilliseconds;
    }





}