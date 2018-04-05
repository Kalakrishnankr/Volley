package com.goldemo.beachpartner.fragments;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.adpters.EventsAdapter;
import com.goldemo.beachpartner.calendar.compactcalendarview.CompactCalendarView;
import com.goldemo.beachpartner.calendar.compactcalendarview.domain.Event;
import com.goldemo.beachpartner.connections.ApiService;
import com.goldemo.beachpartner.connections.PrefManager;
import com.goldemo.beachpartner.models.EventAdminModel;

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
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
    String toDayDate;
    private TextView tview_master, tview_mycalendar, tview_month, tview_date;
    private ImageButton btn_previous, btn_next;
    private RecyclerView rview;
    private EventsAdapter eventsAdapter;
    private String token;
    private List<Event> toDayEvents = new ArrayList<>();
    private static boolean isMycal = false;
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
        getAllEvents();
        initActivity(view);


        return view;
    }


    private void initActivity(View view) {

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
                    eventsAdapter = new EventsAdapter(getContext(), toDayEvents,isMycal);
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
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.txtMaster:
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
                wlp.y = 120;
                wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
                window.setAttributes(wlp);
                filterDialogue.show();
                initView(filterDialogue);
                break;
            default:
                break;
        }
        return false;
    }

    private void initView(Dialog filterDialogue) {

        final Spinner spinner_events = (Spinner) filterDialogue.findViewById(R.id.event_spinner);
        final Spinner spinner_subEvents = (Spinner) filterDialogue.findViewById(R.id.subtypes_spinner);
        Spinner spinner_year = (Spinner) filterDialogue.findViewById(R.id.year_spinner);
        Spinner spinner_month = (Spinner) filterDialogue.findViewById(R.id.month_spinner);

        AutoCompleteTextView tv_state = (AutoCompleteTextView) filterDialogue.findViewById(R.id.state_List);
        AutoCompleteTextView tv_region = (AutoCompleteTextView) filterDialogue.findViewById(R.id.region_list);

        Button btn_search = (Button) filterDialogue.findViewById(R.id.btn_invite_partner);


        /*Events*/

        List<String> eventTypes = new ArrayList<>();
        eventTypes.add("Junior");
        eventTypes.add("College Showcase");
        eventTypes.add("College Clinic");
        eventTypes.add("National Tournament");
        eventTypes.add("Adult");

        //List for junior

        final List<String> juniorSubEvent = new ArrayList<>();
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


        ArrayAdapter<String> adapterEventTypes = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, eventTypes);
        spinner_events.setAdapter(adapterEventTypes);

        spinner_events.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (spinner_events.getSelectedItem().equals("Junior")) {

                    ArrayAdapter<String> adapterSubEvents = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, juniorSubEvent);
                    spinner_subEvents.setAdapter(adapterSubEvents);
                } else if (spinner_events.getSelectedItem().equals("Adult")) {

                    ArrayAdapter<String> adapterSubEvents = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, adultSubEvent);
                    spinner_subEvents.setAdapter(adapterSubEvents);
                } else {
                    ArrayAdapter<String> adapterSubEvents = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, subEventsNil);
                    spinner_subEvents.setAdapter(adapterSubEvents);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        /*Year*/

        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear; i <= 2020; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, years);
        spinner_year.setAdapter(adapter);

        /*Month*/
        String[] Months = new String[]{"January", "February",
                "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December"};

        ArrayAdapter<String> adapterMonths = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Months);
        spinner_month.setAdapter(adapterMonths);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/SanFranciscoTextRegular.ttf");

        /*State*/

        List<String> stateList = new ArrayList<>();
        stateList.add("Alabama (AL)");
        stateList.add("Alaska (AK)");
        stateList.add("Alberta (AB)");
        stateList.add("American Samoa (AS)");
        stateList.add("Arizona (AZ)");
        stateList.add("Arkansas (AR)");
        stateList.add("Armed Forces (AE)");
        stateList.add("Armed Forces Americas (AA)");
        stateList.add("Armed Forces Pacific (AP)");
        stateList.add("British Columbia (BC)");
        stateList.add("California (CA)");
        stateList.add("Colorado (CO)");
        stateList.add("Connecticut (CT)");
        stateList.add("Delaware (DE)");
        stateList.add("District Of Columbia (DC)");
        stateList.add("Florida (FL)");
        stateList.add("Georgia (GA)");
        stateList.add("Guam (GU)");
        stateList.add("Hawaii (HI)");
        stateList.add("Idaho (ID)");
        stateList.add("Illinois (IL)");
        stateList.add("Indiana (IN)");
        stateList.add("Iowa (IA)");
        stateList.add("Kansas (KS)");
        stateList.add("Kentucky (KY)");
        stateList.add("Louisiana (LA)");
        stateList.add("Maine (ME)");
        stateList.add("Manitoba (MB)");
        stateList.add("Maryland (MD)");
        stateList.add("Massachusetts (MA)");
        stateList.add("Michigan (MI)");
        stateList.add("Minnesota (MN)");
        stateList.add("Mississippi (MS)");
        stateList.add("Missouri (MO)");
        stateList.add("Montana (MT)");
        stateList.add("Nebraska (NE)");
        stateList.add("Nevada (NV)");
        stateList.add("New Brunswick (NB)");
        stateList.add("New Hampshire (NH)");
        stateList.add("New Jersey (NJ)");
        stateList.add("New Mexico (NM)");
        stateList.add("New York (NY)");
        stateList.add("Newfoundland (NF)");
        stateList.add("North Carolina (NC)");
        stateList.add("North Dakota (ND)");
        stateList.add("Northwest Territories (NT)");
        stateList.add("Nova Scotia (NS)");
        stateList.add("Nunavut (NU)");
        stateList.add("Ohio (OH)");
        stateList.add("Oklahoma (OK)");
        stateList.add("Ontario (ON)");
        stateList.add("Oregon (OR)");
        stateList.add("Pennsylvania (PA)");
        stateList.add("Prince Edward Island (PE)");
        stateList.add("Puerto Rico (PR)");
        stateList.add("Quebec (QC)");
        stateList.add("Rhode Island (RI)");
        stateList.add("Saskatchewan (SK)");
        stateList.add("South Carolina (SC)");
        stateList.add("South Dakota (SD)");
        stateList.add("Tennessee (TN)");
        stateList.add("Texas (TX)");
        stateList.add("Utah (UT)");
        stateList.add("Vermont (VT)");
        stateList.add("Virgin Islands (VI)");
        stateList.add("Virginia (VA)");
        stateList.add("Washington (WA)");
        stateList.add("West Virginia (WV)");
        stateList.add("Wisconsin (WI)");
        stateList.add("Wyoming (WY)");
        stateList.add("Yukon Territory (YT)");

        ArrayAdapter<String> adapterStates = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, stateList);
        tv_state.setTypeface(font);
        tv_state.setAdapter(adapterStates);

        //Region
        List<String> regionList = new ArrayList<>();
        regionList.add("Alaska Region (AK)");
        regionList.add("Aloha Region (AH)");
        regionList.add("Arizona Region (AZ)");
        regionList.add("Badger Region (BG)");
        regionList.add("Bayou Region (BY)");
        regionList.add("Carolina Region (CR)");
        regionList.add("Chesapeake Region (CH)");
        regionList.add("Columbia Empire Region (CE)");
        regionList.add("Delta Region (DE)");
        regionList.add("Evergreen Region (EV)");
        regionList.add("Florida Region (FL)");
        regionList.add("Garden Empire Region (GE)");
        regionList.add("Gateway Region (GW)");
        regionList.add("Great Lakes Region (GL)");
        regionList.add("Great Plains Region (GP)");
        regionList.add("Gulf Coast Region (GC)");
        regionList.add("Heart of America Region (HA)");
        regionList.add("Hoosier Region (HO)");
        regionList.add("Intermountain Region (IM)");
        regionList.add("Iowa Region (IA)");
        regionList.add("Iroquois Empire Region (IE)");
        regionList.add("Keystone Region (KE)");
        regionList.add("Lakeshore Region (LK)");
        regionList.add("Lone Star Region (LS)");
        regionList.add("Moku O Keawe Region (MK)");
        regionList.add("New England Region (NE)");
        regionList.add("North Country Region (NO)");
        regionList.add("North Texas Region (NT)");
        regionList.add("Northern California Region (NC)");
        regionList.add("Ohio Valley Region (OV)");
        regionList.add("Oklahoma Region (OK)");
        regionList.add("Old Dominion Region (OD)");
        regionList.add("Palmetto Region (PM)");
        regionList.add("Pioneer Region (PR)");
        regionList.add("Puget Sound Region (PS)");
        regionList.add("Rocky Mountain Region (RM)");
        regionList.add("Southern Region (SO)");
        regionList.add("Southern California Region (SC)");
        regionList.add("Sun Country Region (SU)");
        regionList.add("Western Empire Region (WE)");


        ArrayAdapter<String> adapterRegion = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, regionList);
        tv_region.setTypeface(font);
        tv_region.setAdapter(adapterRegion);


    }


    //Get all Master Calendar events Api

    private void getAllEvents() {
        isMycal  = false;
        eventModelList.clear();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_ALL_EVENTS, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null) {
                            for (int i = 0; i < response.length(); i++) {
                                try {

                                    JSONObject object = response.getJSONObject(i);

                                    Event model = new Event();
                                    model.setEventId(object.getString("id"));
                                    model.setEventName(object.getString("eventName"));
                                    model.setData(object.getString("eventDescription"));
                                    model.setEventLocation(object.getString("eventLocation"));
                                    model.setEventVenue(object.getString("eventVenue"));
                                    model.setEventStartDate(object.getLong("eventStartDate"));
                                    model.setEventEndDate(object.getLong("eventEndDate"));
                                    model.setTimeInMillis(Long.parseLong(object.getString("eventStartDate")));
                                    model.setEventRegStartdate(object.getLong("eventRegStartDate"));
                                    model.setEventRegEnddate(object.getLong("eventRegEndDate"));

                                    JSONObject adminObject = object.getJSONObject("eventAdmin");

                                    EventAdminModel eventAdminModel = new EventAdminModel();
                                    eventAdminModel.setFirstName(adminObject.getString("firstName"));

                                    /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
                                    try {
                                        String mDate = object.getString("eventStartDate");
                                        Date date = sdf.parse(mDate);
                                        date.setHours(0);
                                        date.setMinutes(0);
                                        date.setSeconds(0);
                                        long timeInMilliseconds = date.getTime();
                                        System.out.println("Date in milli :: " + timeInMilliseconds);
                                        model.setTimeInMillis(timeInMilliseconds);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }*/
                                    model.setEventAdmin(eventAdminModel);
                                    eventModelList.add(model);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            setupCalendar();
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
    }


    //Get MyCalendar Events
    private void getMycalendarEvents() {
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
                                    eventModel.setEventLocation(jsonObject.getString("eventLocation"));
                                    eventModel.setEventVenue(jsonObject.getString("eventVenue"));
                                    eventModel.setEventStartDate(jsonObject.getLong("eventStartDate"));
                                    eventModel.setTimeInMillis(Long.parseLong(jsonObject.getString("eventStartDate")));
                                    eventModel.setEventEndDate(jsonObject.getLong("eventEndDate"));

                                    JSONObject object = jsonObject.getJSONObject("eventAdmin");

                                    EventAdminModel adminModel = new EventAdminModel();
                                    adminModel.setFirstName(object.getString("firstName"));
                                    eventModel.setEventAdmin(adminModel);
                                    /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
                                    try {
                                        String mDate = jsonObject.getString("eventStartDate");
                                        Date date = sdf.parse(mDate);
                                        long timeInMilliseconds = date.getTime();
                                        System.out.println("Date in milli :: " + timeInMilliseconds);
                                        eventModel.setTimeInMillis(timeInMilliseconds);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }*/
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

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
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
