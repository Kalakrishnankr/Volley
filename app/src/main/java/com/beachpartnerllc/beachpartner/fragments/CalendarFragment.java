package com.beachpartnerllc.beachpartner.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.activity.TabActivity;
import com.beachpartnerllc.beachpartner.adpters.EventsAdapter;
import com.beachpartnerllc.beachpartner.calendar.compactcalendarview.CompactCalendarView;
import com.beachpartnerllc.beachpartner.calendar.compactcalendarview.domain.Event;
import com.beachpartnerllc.beachpartner.connections.ApiService;
import com.beachpartnerllc.beachpartner.connections.PrefManager;
import com.beachpartnerllc.beachpartner.filter.Filter;
import com.beachpartnerllc.beachpartner.filter.FilterViewModel;
import com.beachpartnerllc.beachpartner.utils.MyJsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;


public class CalendarFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "CalendarFragment";
    public Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    public List<Event> eventList, sList;
    public ArrayList<Event> eventModelList = new ArrayList<>();
    private ArrayList<Event> myeventModelList = new ArrayList<>();
    public List<String> eventStartDate = new ArrayList();
    CompactCalendarView compactCalendarView;
    SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.getDefault());
    SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
    String toDayDate;
    private TextView tview_master, tview_mycalendar, tview_month, tview_date;
    private ImageButton btn_previous, btn_next;
    private RecyclerView rview;
    private EventsAdapter eventsAdapter;
    private String token;
    private List<Event> toDayEvents = new ArrayList<>();
    private static boolean isMycal = false;
    private TabActivity tabActivity;
    AlertDialog b;
    private String eventType,subType,year,month,state,eventType_clear;
    private Spinner spinner_events,spinner_subEvents,spinner_year,spinner_month,tv_state,tv_region;
    private FilterViewModel filterViewModel;
    private LinearLayout top_tabs,calendar_llt,eventHeader_llt,eventRecycler_llt,container_llt;


    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        token = new PrefManager(getActivity()).getToken();
        initActivity(view);
        getAllEvents();

        return view;
    }


    private void initActivity(View view) {


        top_tabs =     (LinearLayout) view.findViewById(R.id.top_tabs_linear_layout);
        calendar_llt = (LinearLayout) view.findViewById(R.id.calendar_whole_linear_layout);
        eventHeader_llt = (LinearLayout) view.findViewById(R.id.event_header_linear_layout);
        eventRecycler_llt = (LinearLayout) view.findViewById(R.id.event_recycler_linear_layout);
        container_llt   =(LinearLayout) view.findViewById(R.id.the_container_linear_layout);

        tview_master = (TextView) view.findViewById(R.id.txtMaster);
        tview_mycalendar = (TextView) view.findViewById(R.id.txtMycalendar);

        tview_month = (TextView) view.findViewById(R.id.month_name);
        tview_date = (TextView) view.findViewById(R.id.tview_date);
        btn_previous = (ImageButton) view.findViewById(R.id.prev_button);
        btn_next = (ImageButton) view.findViewById(R.id.next_button);

        rview = (RecyclerView) view.findViewById(R.id.rcv_events);

        compactCalendarView = (CompactCalendarView) view.findViewById(R.id.compactcalendar);
        toDayDate = DateFormat.getDateTimeInstance().format(new Date());

        compactCalendarView.setFirstDayOfWeek(Calendar.SUNDAY);
        compactCalendarView.setUseThreeLetterAbbreviation(true);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rview.setLayoutManager(manager);
        rview.setHasFixedSize(true);

        tview_master.setOnClickListener(this);
        tview_mycalendar.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        btn_previous.setOnClickListener(this);


        AlertDialog.Builder dialogbar=new AlertDialog.Builder(getActivity());
        View holder=View.inflate(getActivity(), R.layout.progress_dialouge, null);
        dialogbar.setView(holder);
        //dialogbar.setMessage("please wait...");
        dialogbar.setCancelable(false);
        b = dialogbar.create();
        b.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        b.setCancelable(false);

        //load events for today date
        activeMasterTab();



        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                tview_date.setText("Events for " + dateFormat.format(dateClicked));

                toDayEvents.clear();

                List<Event> bookingsFromMap = compactCalendarView.getEvents(dateClicked);


                if (bookingsFromMap != null) {

                    for (int i = 0; i < bookingsFromMap.size(); i++) {

                       // Date date=new Date(bookingsFromMap.get(i).getTimeInMillis());
                        if ((DatetoMilli(dateClicked)) == (DatetoMilli(new Date(bookingsFromMap.get(i).getTimeInMillis())))) {
                            toDayEvents.add(bookingsFromMap.get(i));
                        }
                    }
                    eventsAdapter = new EventsAdapter(getActivity(), toDayEvents,isMycal);
                    rview.setAdapter(eventsAdapter);
                    rview.invalidate();
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
        tview_date.setText("Events for " + dateFormat.format(new Date()));


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() instanceof TabActivity){
            tabActivity = (TabActivity)getActivity();
            tabActivity.setActionBarTitle("Calendar");
            tabActivity.disableFloatButtons();
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.txtMaster:
                tabActivity.setActionBarTitle("Master Calendar");
                compactCalendarView.removeAllEvents();
                compactCalendarView.invalidate();
                if(eventsAdapter!=null){
                    eventsAdapter.notifyDataSetChanged();
                }
                activeMasterTab();
                toDayEvents.clear();
                getAllEvents();
                isMycal  = false;
                break;

            case R.id.txtMycalendar:
                tabActivity.setActionBarTitle("My Calendar");
                compactCalendarView.removeAllEvents();
                compactCalendarView.invalidate();
                if(eventsAdapter!=null){
                    eventsAdapter.notifyDataSetChanged();
                }
                activeMycalendarTab();
                toDayEvents.clear();
                getMycalendarEvents();
                isMycal = true;

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

    private void currentDateEventSetter(){
        if(dateFormat.format(compactCalendarView.getCurrentDate()).equalsIgnoreCase(dateFormat.format(new Date()))){
            toDayEvents.clear();
            List<Event> bookingsFromMap = compactCalendarView.getEvents(compactCalendarView.getCurrentDate());
            if (bookingsFromMap != null) {

                for (int i = 0; i < bookingsFromMap.size(); i++) {

                    if ((DatetoMilli(compactCalendarView.getCurrentDate())) == (DatetoMilli(new Date(bookingsFromMap.get(i).getTimeInMillis())))) {
                        toDayEvents.add(bookingsFromMap.get(i));
                    }
                }
                eventsAdapter = new EventsAdapter(getActivity(), toDayEvents, isMycal);
                rview.setAdapter(eventsAdapter);
                rview.invalidate();
                eventsAdapter.notifyDataSetChanged();
            }

        }
    }
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


    private void loadEventsForYear(int year) {
        //addEvents(Calendar.DECEMBER, year);
        //addEvents(Calendar.AUGUST, year);
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


    private void setToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
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


    /*@Override
    public void onPause() {
        Log.e("DEBUG", "OnPause of HomeFragment");
        setHasOptionsMenu(false);
        super.onPause();
    }*/


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        menu.clear();
        inflater.inflate(R.menu.menu_class_fragment, menu);
        inflater.inflate(R.menu.menu_search_filter, menu);
        super.onCreateOptionsMenu(menu, inflater);


        /*super.onCreateOptionsMenu(menu, inflater); menu.clear();
        inflater.inflate(R.menu.sample_menu, menu);*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.search_filter:
                Dialog filterDialogue = new Dialog(getContext());
                //filterDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                filterDialogue.setContentView(R.layout.popup_filter);
                filterDialogue.setCanceledOnTouchOutside(true);
                Window window = filterDialogue.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.TOP;
                wlp.y = 80;
                wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
                window.setAttributes(wlp);
                filterDialogue
                        .getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

                filterDialogue.show();
                initView(filterDialogue);

                int eventValuePos = adapterEventTypes.getPosition(filterViewModel.getFilter().getEventType());
                spinner_events.setSelection(eventValuePos);


                int eventSubValuePos = adapterSubEvents.getPosition(filterViewModel.getFilter().getSubType());
                spinner_subEvents.setSelection(eventSubValuePos);

                int yearValuePos = adapterYear.getPosition(filterViewModel.getFilter().getYear());
                spinner_year.setSelection(yearValuePos);

                int monthValuePos = adapterMonths.getPosition(filterViewModel.getFilter().getMonth());
                spinner_month.setSelection(monthValuePos);

                int stateValuePos = adapterStates.getPosition(filterViewModel.getFilter().getState());
                tv_state.setSelection(stateValuePos);


                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        filterViewModel = ViewModelProviders.of(this).get(FilterViewModel.class);
    }
    public ArrayAdapter<String> adapterEventTypes;
    public ArrayAdapter<String> adapterYear;
    public ArrayAdapter<String> adapterMonths;
    public ArrayAdapter<String> adapterStates;
    public ArrayAdapter<String> adapterSubEvents;

    List<String> eventTypes = new ArrayList<>();
    ArrayList<String> years = new ArrayList<String>();
    List<String> stateList = new ArrayList<>();
    List<String> regionList = new ArrayList<>();
    /*Month*/
    String[] Months = new String[]{"Please Select","January", "February",
            "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December"};







    private void spinnerInit(){

        years.clear();
        eventTypes.clear();
        stateList.clear();
        regionList.clear();
        /*Events*/

        eventTypes.add("Please Select");
        eventTypes.add("Junior");
        eventTypes.add("College Showcase");
        eventTypes.add("College Clinic");
        eventTypes.add("National Tournament");
        eventTypes.add("Adult");

        //List for junior

        final List<String> juniorSubEvent = new ArrayList<>();
        juniorSubEvent.add("Please Select");
        juniorSubEvent.add("10U");
        juniorSubEvent.add("12U");
        juniorSubEvent.add("13U");
        juniorSubEvent.add("14U");
        juniorSubEvent.add("15U");
        juniorSubEvent.add("16U");
        juniorSubEvent.add("17U");
        juniorSubEvent.add("18U");


        //List For adult
        final List<String> adultSubEvent = new ArrayList<>();
        adultSubEvent.add("Please Select");
        adultSubEvent.add("Unrated");
        adultSubEvent.add("B");
        adultSubEvent.add("A");
        adultSubEvent.add("AA");
        adultSubEvent.add("AAA");
        adultSubEvent.add("Open");
        adultSubEvent.add("CoEd");
        adultSubEvent.add("CoEd Unrated");
        adultSubEvent.add("CoEd B");
        adultSubEvent.add("CoEd A");
        adultSubEvent.add("CoEd AA");
        adultSubEvent.add("CoEd AAA");
        adultSubEvent.add("CoEd Open");

        final List<String> subEventsNil = new ArrayList<>();
        subEventsNil.add("Not Applicable");



        adapterEventTypes = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, eventTypes);
        spinner_events.setAdapter(adapterEventTypes);

//        if (filterViewModel.getFilter().getEventType().equals("Junior")) {
//            adapterSubEvents = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, juniorSubEvent);
//            spinner_subEvents.setAdapter(adapterSubEvents);
//        } else if (filterViewModel.getFilter().getEventType().equals("Adult")) {
//
//            adapterSubEvents = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, adultSubEvent);
//            spinner_subEvents.setAdapter(adapterSubEvents);
//        }
//        else {
            adapterSubEvents = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, subEventsNil);
            spinner_subEvents.setAdapter(adapterSubEvents);
//        }

        spinner_events.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (spinner_events.getSelectedItem().equals("Junior")) {

                    adapterSubEvents = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, juniorSubEvent);
                    spinner_subEvents.setAdapter(adapterSubEvents);

                } else if (spinner_events.getSelectedItem().equals("Adult")) {

                    adapterSubEvents = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, adultSubEvent);
                    spinner_subEvents.setAdapter(adapterSubEvents);
                }
                else {
                    adapterSubEvents = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, subEventsNil);
                    spinner_subEvents.setAdapter(adapterSubEvents);
                }
                int eventSubValuePos = adapterSubEvents.getPosition(filterViewModel.getFilter().getSubType());
                spinner_subEvents.setSelection(eventSubValuePos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        /*Year*/

        int thisYear = Calendar.getInstance().get(Calendar.YEAR);

        for (int i = 0; i <= 3; i++) {
            years.add(Integer.toString(thisYear + i));
        }
        adapterYear = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, years);
        spinner_year.setAdapter(adapterYear);



        adapterMonths = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Months);
        spinner_month.setAdapter(adapterMonths);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/SanFranciscoTextRegular.ttf");

        /*State*/

        stateList.add("Please Select");
        stateList.add("Alabama");
        stateList.add("Alaska");
        stateList.add("Arizona");
        stateList.add("Arkansas");
        stateList.add("California");
        stateList.add("Colorado");
        stateList.add("Connecticut");
        stateList.add("Delaware");
        stateList.add("Florida");
        stateList.add("Georgia");
        stateList.add("Hawaii");
        stateList.add("Idaho");
        stateList.add("Illinois");
        stateList.add("Indiana");
        stateList.add("Iowa");
        stateList.add("Kansas");
        stateList.add("Kentucky");
        stateList.add("Louisiana");
        stateList.add("Maine");
        stateList.add("Maryland");
        stateList.add("Massachusetts");
        stateList.add("Michigan");
        stateList.add("Minnesota");
        stateList.add("Mississippi");
        stateList.add("Missouri");
        stateList.add("Montana");
        stateList.add("Nebraska");
        stateList.add("Nevada");
        stateList.add("New Hampshire");
        stateList.add("New Jersey");
        stateList.add("New Mexico");
        stateList.add("New York");
        stateList.add("North Carolina");
        stateList.add("North Dakota");
        stateList.add("Ohio");
        stateList.add("Oklahoma");
        stateList.add("Oregon");
        stateList.add("Pennsylvania");
        stateList.add("Rhode Island");
        stateList.add("South Carolina");
        stateList.add("South Dakota");
        stateList.add("Tennessee");
        stateList.add("Texas");
        stateList.add("Utah");
        stateList.add("Vermont");
        stateList.add("Virginia");
        stateList.add("Washington");
        stateList.add("West Virginia");
        stateList.add("Wisconsin");
        stateList.add("Wyoming");

       adapterStates = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, stateList);

        tv_state.setAdapter(adapterStates);

        //Region
        regionList.add("Please Select");
        regionList.add("Alaska Region");
        regionList.add("Aloha Region");
        regionList.add("Arizona Region");
        regionList.add("Badger Region");
        regionList.add("Bayou Region");
        regionList.add("Carolina Region");
        regionList.add("Chesapeake Region");
        regionList.add("Columbia Empire Region");
        regionList.add("Delta Region");
        regionList.add("Evergreen Region");
        regionList.add("Florida Region");
        regionList.add("Garden Empire Region");
        regionList.add("Gateway Region");
        regionList.add("Great Lakes Region");
        regionList.add("Great Plains Region");
        regionList.add("Gulf Coast Region");
        regionList.add("Heart of America Region");
        regionList.add("Hoosier Region");
        regionList.add("Intermountain Region");
        regionList.add("Iowa Region");
        regionList.add("Iroquois Empire Region");
        regionList.add("Keystone Region");
        regionList.add("Lakeshore Region");
        regionList.add("Lone Star Region");
        regionList.add("Moku O Keawe Region");
        regionList.add("New England Region");
        regionList.add("North Country Region");
        regionList.add("North Texas Region");
        regionList.add("Northern California Region");
        regionList.add("Ohio Valley Region");
        regionList.add("Oklahoma Region");
        regionList.add("Old Dominion Region");
        regionList.add("Palmetto Region");
        regionList.add("Pioneer Region");
        regionList.add("Puget Sound Region");
        regionList.add("Rocky Mountain Region");
        regionList.add("Southern Region");
        regionList.add("Southern California Region");
        regionList.add("Sun Country Region");
        regionList.add("Western Empire Region");


        ArrayAdapter<String> adapterRegion = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, regionList);

        tv_region.setAdapter(adapterRegion);
    }

    private void initView(final Dialog filterDialogue) {
        spinner_events = (Spinner) filterDialogue.findViewById(R.id.event_spinner);
        spinner_subEvents = (Spinner) filterDialogue.findViewById(R.id.subtypes_spinner);
        spinner_year = (Spinner) filterDialogue.findViewById(R.id.year_spinner);
        spinner_month = (Spinner) filterDialogue.findViewById(R.id.month_spinner);
        final TextView clearAll     =  (TextView) filterDialogue.findViewById(R.id.clear_all_txtv);
        tv_state = (Spinner) filterDialogue.findViewById(R.id.state_List);
        tv_region = (Spinner) filterDialogue.findViewById(R.id.region_list);

        final Button btn_search = (Button) filterDialogue.findViewById(R.id.btn_invite_partner);

        spinnerInit();




        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner_events.setAdapter(null);
                spinner_events.setAdapter(adapterEventTypes);
                spinner_month.setAdapter(null);
                spinner_month.setAdapter(adapterMonths);
                spinner_year.setAdapter(null);
                spinner_year.setAdapter(adapterYear);

            }
        });


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                eventType = spinner_events.getSelectedItem().toString();
                subType = spinner_subEvents.getSelectedItem().toString();
                year = spinner_year.getSelectedItem().toString();
                month = spinner_month.getSelectedItem().toString();
                state    = tv_state.getSelectedItem().toString();
                eventType_clear = eventType.replaceAll("\\s", "");

                filterViewModel.setFilter(new Filter(eventType, subType, year, month, state));

                if(eventType.equalsIgnoreCase("Please Select")){
                    eventType="";
                }

                if(subType.equalsIgnoreCase("Please Select")){
                    subType="";
                }
                if(year.equalsIgnoreCase("Please Select")){
                    year="";
                }

                if(month.equalsIgnoreCase("Please Select")){
                    month="";
                }

                if(state.equalsIgnoreCase("Please Select")){
                    state="";
                }

                if(month!=""){
                    String dateYear = month+" "+ year;
                    Date startDate = null;
                    DateFormat df = new SimpleDateFormat("MMMM yyyy");
                    try {
                        startDate = df.parse(dateYear);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    tview_month.setText(dateFormatForMonth.format(startDate));
                    tview_date.setText("Events for " + dateFormatForMonth.format(startDate));
                    compactCalendarView.setCurrentDate(startDate);
                }

                compactCalendarView.invalidate();

                JSONObject objects = new JSONObject();
                try {
                    objects.put("eventType",eventType_clear);
                    objects.put("month",month);
                    objects.put("region","");
                    objects.put("state",state);
                    objects.put("subType",subType);
                    objects.put("year",year);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                searchEvents(objects);
                filterDialogue.dismiss();
            }
        });

    }




    //Get all Master Calendar events Api

    private void getAllEvents() {
        b.show();
        isMycal  = false;
        eventModelList.clear();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_ALL_EVENTS, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null) {
                            /*Log.d(TAG, "onResponse Get all Events: " + response.toString());
                            Type listType = new TypeToken<List<Event>>() {}.getType();
                            eventModelList = new Gson().fromJson(response.toString(), listType);
                            setupCalendar();*/
                            for (int i = 0; i < response.length(); i++) {
                                try {

                                    JSONObject object = response.getJSONObject(i);

                                    Event model = new Event();
                                    model.setEventId(object.getString("id"));
                                    model.setEventName(object.getString("eventName"));
                                    model.setData(object.getString("eventDescription"));
                                    model.setEventLocation(object.getString("state"));//eventlocation changed to state on 31/05/2018 6:59 pm abraham
                                    model.setEventVenue(object.getString("eventVenue"));
                                    model.setEventStartDate(object.getLong("eventStartDate"));
                                    model.setEventEndDate(object.getLong("eventEndDate"));
                                    model.setTimeInMillis(Long.parseLong(object.getString("eventStartDate")));
                                    model.setEventRegStartdate(object.getLong("eventRegStartDate"));
                                    model.setEventRegEnddate(object.getLong("eventRegEndDate"));
                                    model.setEventAdmin(object.getString("eventAdmin"));
                                    model.setEventTeamSizes(object.getString("teamSize"));
                                    model.setRegType(object.getString("registerType"));
                                    model.setUserMessage(object.getString("userMessage"));
                                    model.setEventStatus(object.getString("eventStatus"));
                                    model.setInvitationStatus(object.getString("invitationStatus"));
                                    model.setEventUrl(object.getString("eventurl"));
                                    JSONArray dateArray = object.getJSONArray("eventDates");
                                    long [] datearray = new long[dateArray.length()];
                                    if (dateArray != null && dateArray.length()>0 ) {
                                        for (int j = 0; j < dateArray.length() ; j++) {
                                            datearray [j]= dateArray.getLong(j);
                                        }
                                        model.setEventDates(datearray);
                                    }
                                    eventModelList.add(model);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            setupCalendar();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                b.cancel();
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
        Log.d("Request", jsonArrayRequest.toString());
        requestQueue.add(jsonArrayRequest);


    }


    //set Up Master Calendar Events
    private void setupCalendar() {
        compactCalendarView.removeAllEvents();
        compactCalendarView.invalidate();
        if (eventModelList != null) {
            for (int i = 0; i < eventModelList.size(); i++) {
                eventStartDate.add(String.valueOf(eventModelList.get(i).getEventStartDate()));
                compactCalendarView.addEvent(eventModelList.get(i));
            }
        }
        b.cancel();
        currentDateEventSetter();
    }


    //Get MyCalendar Events
    private void getMycalendarEvents() {
        b.show();
        myeventModelList.clear();
        final String userId = new PrefManager(getApplicationContext()).getUserId();
        JsonArrayRequest arrayRequest = new JsonArrayRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_USER_EVENTS+userId, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response!=null){
                            for (int i =0;i<response.length();i++){

                                try {

                                    JSONObject obj= response.getJSONObject(i);

                                    JSONObject jsonObject= obj.getJSONObject("event");
                                    Event eventModel = new Event();
                                    eventModel.setEventId(jsonObject.getString("id"));
                                    eventModel.setEventName(jsonObject.getString("eventName"));
                                    eventModel.setData(jsonObject.getString("eventDescription"));
                                    eventModel.setEventState(jsonObject.getString("state")); //changed location to state
                                    eventModel.setEventVenue(jsonObject.getString("eventVenue"));
                                    eventModel.setEventStartDate(jsonObject.getLong("eventStartDate"));
                                    eventModel.setEventTeamSizes(jsonObject.getString("teamSize"));
                                    eventModel.setTimeInMillis(Long.parseLong(jsonObject.getString("eventStartDate")));
                                    eventModel.setEventEndDate(jsonObject.getLong("eventEndDate"));
                                    eventModel.setEventAdmin(jsonObject.getString("eventAdmin"));
                                    JSONArray dateArray = obj.getJSONArray("eventDates");
                                    long [] mydatearray = new long[dateArray.length()];
                                    if (dateArray.length()>0 ) {
                                        for (int j = 0; j < dateArray.length() ; j++) {
                                            mydatearray [j]= dateArray.getLong(j);
                                        }
                                     eventModel.setEventDates(mydatearray);
                                    }
                                    myeventModelList.add(eventModel);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            setupMyCalendar();

                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                b.cancel();
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
            public Map<String, String> getHeaders()  {
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

    //Method for setUp MyCalendar Events
    private void setupMyCalendar() {
        compactCalendarView.removeAllEvents();
        compactCalendarView.invalidate();
        if (myeventModelList != null) {
            for (int i = 0; i < myeventModelList.size(); i++) {
                compactCalendarView.addEvent(myeventModelList.get(i));
            }
        }
        b.cancel();
        currentDateEventSetter();

    }

    private void setupFilterCalendar() {

        compactCalendarView.removeAllEvents();
        compactCalendarView.invalidate();
        if (eventModelList != null) {
            for (int i = 0; i < eventModelList.size(); i++) {
                eventStartDate.add(String.valueOf(eventModelList.get(i).getEventStartDate()));
                compactCalendarView.addEvent(eventModelList.get(i));
            }
        }
        toDayEvents.clear();
        List<Event> bookingsFromMap = compactCalendarView.getEventsForMonth(compactCalendarView.getCurrentDate());
        if (bookingsFromMap != null) {

            for (int i = 0; i < bookingsFromMap.size(); i++) {

                if (!toDayEvents.contains(bookingsFromMap.get(i))) {
                    toDayEvents.add(bookingsFromMap.get(i));
                }
            }
            eventsAdapter = new EventsAdapter(getActivity(), toDayEvents, isMycal);
            rview.setAdapter(eventsAdapter);
            rview.invalidate();
            eventsAdapter.notifyDataSetChanged();
        }
        b.cancel();

    }

    //Api for search events
    private void searchEvents(JSONObject obj) {
        top_tabs.setVisibility(View.GONE);
        calendar_llt.setVisibility(View.GONE);
        container_llt.setWeightSum((float) 1.2);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0, (float) 0.1);
        eventHeader_llt.setLayoutParams(param);


        b.show();
        eventModelList.clear();
        MyJsonArrayRequest arrayRequest = new MyJsonArrayRequest(ApiService.REQUEST_METHOD_POST, ApiService.SEARCH_EVENTS,obj,
                new Response.Listener<JSONArray> () {
                @Override
                public void onResponse(JSONArray response) {
                   Log.d(TAG, "onResponse Search: " + response.toString());
                    /*Type listType = new TypeToken<List<Event>>() {}.getType();
                    eventModelList = new Gson().fromJson(response.toString(), listType);
                    setupCalendar();*/
                    if (response != null) {
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject object = response.getJSONObject(i);

                                Event model = new Event();
                                model.setEventId(object.getString("id"));
                                model.setEventName(object.getString("eventName"));
                                model.setData(object.getString("eventDescription"));
                                model.setEventLocation(object.getString("eventLocation"));
                                model.setEventState(object.getString("state"));
                                model.setEventVenue(object.getString("eventVenue"));
                                model.setEventStartDate(object.getLong("eventStartDate"));
                                model.setEventEndDate(object.getLong("eventEndDate"));
                                model.setTimeInMillis(Long.parseLong(object.getString("eventStartDate")));
                                model.setEventRegStartdate(object.getLong("eventRegStartDate"));
                                model.setEventRegEnddate(object.getLong("eventRegEndDate"));
                                model.setEventAdmin(object.getString("eventAdmin"));
                                model.setRegType(object.getString("registerType"));
                                model.setEventTeamSizes(object.getString("teamSize"));
                                model.setUserMessage(object.getString("userMessage"));
                                model.setEventStatus(object.getString("eventStatus"));
                                model.setInvitationStatus(object.getString("invitationStatus"));
                                model.setEventUrl(object.getString("eventurl"));
                                JSONArray dateArray = object.getJSONArray("eventDates");
                                long [] mydatearray = new long[dateArray.length()];
                                if (dateArray.length()>0 ) {
                                    for (int j = 0; j < dateArray.length() ; j++) {
                                        mydatearray [j]= dateArray.getLong(j);
                                    }
                                    model.setEventDates(mydatearray);
                                }
                                eventModelList.add(model);

                            } catch (Exception e) {
                                    e.printStackTrace();
                            }
                        }

                    }

                    setupFilterCalendar();
                }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                b.cancel();

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
                        case 500:
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
                headers.put("Authorization", "Bearer " + token);
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        Log.d("Request", arrayRequest.toString());
        requestQueue.add(arrayRequest);
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
