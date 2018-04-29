/*
package com.goldemo.beachpartner.calendar.myowncalendar;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.calendar.compactcalendarview.CompactCalendarView;
import com.goldemo.beachpartner.calendar.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

*/
/**
 * Created by Owner on 2/19/2018.
 *//*


public class CompactCalendarTab extends Fragment {

    private static final String TAG = "CalendarActivity";
    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private SimpleDateFormat dateFormatForEventHeader = new SimpleDateFormat("dd - MMMM", Locale.getDefault());
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM - yyyy", Locale.getDefault());
    private boolean shouldShow = false;
    private CompactCalendarView compactCalendarView;
    TextView monthNameText;
    ListView bookings_listview;
    TextView eventDateHeader;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.main_tab, container, false);;

        final List<String> mutableBookings = new ArrayList<>();
        monthNameText =(TextView) v.findViewById(R.id.month_name);

        ListView bookingsListView = (ListView) v.findViewById(R.id.bookings_listview);
        eventDateHeader=(TextView) v.findViewById(R.id.event_date);
        eventDateHeader.setText("Events For "+ dateFormatForEventHeader.format(new Date()));

        final ImageButton showPreviousMonthBut = (ImageButton) v.findViewById(R.id.prev_button);
        final ImageButton showNextMonthBut = (ImageButton) v.findViewById(R.id.next_button);
//        final Button slideCalendarBut = (Button) v.findViewById(R.id.slide_calendar);
//        final Button showCalendarWithAnimationBut = (Button) v.findViewById(R.id.show_with_animation_calendar);
//        final Button setLocaleBut = (Button) v.findViewById(R.id.set_locale);
//        final Button removeAllEventsBut = (Button) v.findViewById(R.id.remove_all_events);

        final ArrayAdapter adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mutableBookings);
        bookingsListView.setAdapter(adapter);
        compactCalendarView = (CompactCalendarView) v.findViewById(R.id.compactcalendar_view);

        // below allows you to configure color for the current day in the month
        // compactCalendarView.setCurrentDayBackgroundColor(getResources().getColor(R.color.black));
        // below allows you to configure colors for the current day the user has selected
        // compactCalendarView.setCurrentSelectedDayBackgroundColor(getResources().getColor(R.color.dark_red));
        compactCalendarView.setUseThreeLetterAbbreviation(false);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);



        loadEvents();
        loadEventsForYear(2017);
        compactCalendarView.invalidate();

        logEventsByMonth(compactCalendarView);

        // below line will display Sunday as the first day of the week
        // compactCalendarView.setShouldShowMondayAsFirstDay(false);

        // disable scrolling calendar
        // compactCalendarView.shouldScrollMonth(false);

        // show days from other months as greyed out days
        // compactCalendarView.displayOtherMonthDays(true);

        // show Sunday as first day of month
        // compactCalendarView.setShouldShowMondayAsFirstDay(false);





        //set initial title
//        toolbar = ((ActionBarActivity) getActivity()).getSupportActionBar();
//        toolbar.setTitle(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));

        //set title on calendar scroll
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                eventDateHeader.setText("Events For "+ dateFormatForEventHeader.format(dateClicked));
                final List<Event> bookingsFromMap = compactCalendarView.getEvents(dateClicked);
                Log.d(TAG, "inside onclick " + dateFormatForDisplaying.format(dateClicked));
                if (bookingsFromMap != null) {
                    ListView simpleListView=(ListView) v.findViewById(R.id.bookings_listview);
                    long dateInMilli = DatetoMilli(dateClicked);
                    for(int i=0;i<bookingsFromMap.size();i++){
                        if(bookingsFromMap.get(i).getTimeInMillis()==dateInMilli){
                            //CustomAdapter customAdapter= new CustomAdapter(getContext(),bookingsFromMap);
                            //simpleListView.setAdapter(customAdapter);

                        }else {
                            simpleListView.setAdapter(null);
                        }




                    }


                    simpleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {




                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(getContext(),"Hello "+ bookingsFromMap.get(position).getData(),Toast.LENGTH_SHORT).show();
                            //EventDescriptionFragment eventDescriptionFragment = new EventDescriptionFragment();
                            //passing serializable data
                            Bundle bundle = new Bundle();
                            Event eventObject= new Event();
                            eventObject=bookingsFromMap.get(position);
                            bundle.putSerializable("event_clicked", eventObject);
                            //eventDescriptionFragment.setArguments(bundle);

                           */
/* FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                            fragmentTransaction.replace(R.id.container, eventDescriptionFragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();*//*






                        }
                    });
                    Log.d(TAG, bookingsFromMap.toString());
                    mutableBookings.clear();
                    for (Event booking : bookingsFromMap) {
                        mutableBookings.add((String) booking.getData());
                    }
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                monthNameText.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
            }
        });

        showPreviousMonthBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onItemClick(View v) {
                compactCalendarView.showPreviousMonth();
            }
        });

        showNextMonthBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onItemClick(View v) {
                compactCalendarView.showNextMonth();
            }
        });
//
//       final View.OnClickListener showCalendarOnClickLis = getCalendarShowLis();
//        slideCalendarBut.setOnClickListener(showCalendarOnClickLis);

//        final View.OnClickListener exposeCalendarListener = getCalendarExposeLis();
//        showCalendarWithAnimationBut.setOnClickListener(exposeCalendarListener);

        compactCalendarView.setAnimationListener(new CompactCalendarView.CompactCalendarAnimationListener() {
            @Override
            public void onOpened() {
            }

            @Override
            public void onClosed() {
            }
        });


//
//        removeAllEventsBut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onItemClick(View v) {
//                compactCalendarView.removeAllEvents();
//            }
//        });


        // uncomment below to show indicators above small indicator events
        // compactCalendarView.shouldDrawIndicatorsBelowSelectedDays(true);

        // uncomment below to open onCreate
        //openCalendarOnCreate(v);

        return v;
    }

    @NonNull
    private View.OnClickListener getCalendarShowLis() {
        return new View.OnClickListener() {
            @Override
            public void onItemClick(View v) {
                if (!compactCalendarView.isAnimating()) {
                    if (shouldShow) {
                        compactCalendarView.showCalendar();
                    } else {
                        compactCalendarView.hideCalendar();
                    }
                    shouldShow = !shouldShow;
                }
            }
        };
    }

    @NonNull
    private View.OnClickListener getCalendarExposeLis() {
        return new View.OnClickListener() {
            @Override
            public void onItemClick(View v) {
                if (!compactCalendarView.isAnimating()) {
                    if (shouldShow) {
                        compactCalendarView.showCalendarWithAnimation();
                    } else {
                        compactCalendarView.hideCalendarWithAnimation();
                    }
                    shouldShow = !shouldShow;
                }
            }
        };
    }

    private void openCalendarOnCreate(View v) {
        final RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.main_content);
        ViewTreeObserver vto = layout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < 16) {
                    layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                compactCalendarView.showCalendarWithAnimation();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();


        // Set to current day on resume to set calendar to latest day
        monthNameText.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));

    }



    private void loadEvents() {
        addEvents(-1, -1);
        addEvents(Calendar.DECEMBER, -1);
        addEvents(Calendar.AUGUST, -1);
    }

    private void loadEventsForYear(int year) {
        addEvents(Calendar.DECEMBER, year);
        addEvents(Calendar.AUGUST, year);
    }

    private void logEventsByMonth(CompactCalendarView compactCalendarView) {
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1);
        currentCalender.set(Calendar.MONTH, Calendar.AUGUST);
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
            return Arrays.asList(new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Beach Volley Team Tournament"));
        } else if (day > 2 && day <= 4) {
            return Arrays.asList(
                    new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Canadian Volley TechTour"),
                    new Event(Color.argb(255, 100, 68, 65), timeInMillis, "Rasis college level volley"));
        } else {
            return Arrays.asList(
                    new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Temp Coaches original volley tournament"),
                    new Event(Color.argb(255, 100, 68, 65), timeInMillis, "Breckenridge Doubles (aka Putterhead Doubles)"),
                    new Event(Color.argb(255, 70, 68, 65), timeInMillis, "Pottstown Rumble"));
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

*/
