package com.goldemo.beachpartner.fragments;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.appyvet.materialrangebar.RangeBar;
import com.bumptech.glide.Glide;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.goldemo.beachpartner.MyInterface;
import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.adpters.TouristSpot;
import com.goldemo.beachpartner.adpters.TouristSpotCardAdapter;
import com.goldemo.beachpartner.cardstackview.CardStackView;
import com.goldemo.beachpartner.cardstackview.SwipeDirection;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;


public class BPFinderFragment extends Fragment implements MyInterface {


    private ProgressBar progressBar;
    private CardStackView cardStackView;
    private TouristSpotCardAdapter adapter;
    private RelativeLayout rr;
    private CoordinatorLayout llv;
    private ImageView imgv_profilepic,imgv_rvsecard,imgv_location,imgv_highfi,btnPlay;
    private TextView tvname,tvmonth,txtv_age,txtv_gender;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    private Spinner spinner_location;
    private RangeBar age_bar;
    public Button btnMale,btnFemale;

    private LinearLayout llvFilter;

    ArrayList<String>Location = new ArrayList<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RelativeLayout rrvBottom;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());

    public BPFinderFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_bpfinder, container, false);
        setUp(view);
        reload();


        return view;
    }

    public void setUp(final View view) {

        progressBar         =   (ProgressBar) view.findViewById(R.id.activity_main_progress_bar);
        rr                  =   (RelativeLayout) view.findViewById(R.id.rr);
        llv                 =   (CoordinatorLayout)view.findViewById(R.id.llMoreinfo);
        imgv_profilepic     =   (ImageView)view.findViewById(R.id.img_profile);
        //tvname          = (TextView)view.findViewById(R.id.txt_name);
        cardStackView       =   (CardStackView) view.findViewById(R.id.activity_main_card_stack_view);
        tvmonth             =   (TextView) view.findViewById(R.id.txtv_month);
        ImageView toggle    =   (ImageView) view.findViewById(R.id.toggle);
        rrvBottom           =   (RelativeLayout) view.findViewById(R.id.rrv_bottomMenus);

        //Layout for filters

        llvFilter           =   (LinearLayout) view.findViewById(R.id.llFilter);
        txtv_age            =   (TextView) view.findViewById(R.id.txtv_age);
        age_bar             =   (RangeBar)  view.findViewById(R.id.rangebar);
        spinner_location    =   (Spinner) view.findViewById(R.id.spinner_location);
        txtv_gender         =   (TextView) view.findViewById(R.id.txtv_gender);

        btnMale             =   (Button)view.findViewById(R.id.btnMen);
        btnFemale           =   (Button)view.findViewById(R.id.btnWomen);
        btnPlay             =   (ImageView)view.findViewById(R.id.imgPlay);


        final CompactCalendarView compactCalendarView = (CompactCalendarView) view.findViewById(R.id.compactcalendar_view);
        compactCalendarView.setFirstDayOfWeek(Calendar.SUNDAY);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        collapsingToolbarLayout = (CollapsingToolbarLayout)view. findViewById(R.id.CollapsingToolbarLayout1);

        imgv_rvsecard   =   (ImageView) view.findViewById(R.id.ic_rvsecard);
        imgv_highfi     =   (ImageView) view.findViewById(R.id.ic_high);
        imgv_location   =   (ImageView) view.findViewById(R.id.ic_location);


        addLocation();

        //play button
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llvFilter.setVisibility(View.GONE);
                rr.setVisibility(View.VISIBLE);
                rrvBottom.setVisibility(View.VISIBLE);
            }
        });


        //age range bar
        age_bar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {

                txtv_age.setText(""+leftPinValue+"-"+""+rightPinValue);

            }
        });

        //button male

        btnMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtv_gender.setText("MALE");
            }
        });

        //button Female

        btnFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtv_gender.setText("FEMALE");
            }
        });

        //choose Location

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, Location);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_location.setAdapter(dataAdapter);


        cardStackView.setCardEventListener(new CardStackView.CardEventListener() {
            @Override
            public void onCardDragging(float percentX, float percentY) {
                Log.d("CardStackView", "onCardDragging");
            }

            @Override
            public void onCardSwiped(SwipeDirection direction) {
                Log.d("CardStackView", "onCardSwiped: " + direction.toString());
                Log.d("CardStackView", "topIndex: " + cardStackView.getTopIndex());
                if (cardStackView.getTopIndex() == adapter.getCount() - 5) {
                    Log.d("CardStackView", "Paginate: " + cardStackView.getTopIndex());
                    paginate();
                }
            }

            @Override
            public void onCardReversed() {
                Log.d("CardStackView", "onCardReversed");
            }

            @Override
            public void onCardMovedToOrigin() {
                Log.d("CardStackView", "onCardMovedToOrigin");
            }

            @Override
            public void onCardClicked(int index) {
                Log.d("CardStackView", "onCardClicked: " + index);
            }
        });

        //Calendar

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rr.setVisibility(View.VISIBLE);
                llv.setVisibility(View.GONE);
            }
        });

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {



               // long dateInMilli = DatetoMilli(dateClicked);
                //ListView simpleListView=(ListView) view.findViewById(R.id.List1);

               /* for(int i=0;i<eventLists.size();i++){
                    if(eventLists.get(i).getTimeInMilli()==dateInMilli){
                        CustomAdapter customAdapter= new CustomAdapter(getActivity(),eventLists);
                        simpleListView.setAdapter(customAdapter);

                    }else {
                        simpleListView.setAdapter(null);

                    }




                }*/



//                simpleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                        Toast.makeText(getContext(),"Hello "+ eventLists.get(position).getData(),Toast.LENGTH_SHORT).show();
//
//
//
//
//                    }
//                });

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                //Log.d(TAG, "Month was scrolled to: " + firstDayOfNewMonth);
                tvmonth.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
            }


        });


       // String newFormat= "dd MMMM";
        tvmonth.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));


        //Card reverse onclick
        imgv_rvsecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reverse();
            }
        });

        //High fi
        imgv_highfi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipeUp();
            }
        });

        //Get Location

        imgv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });

    }

    private void addLocation() {
        Location.add("India");
        Location.add("China");
        Location.add("Bahamas");
        Location.add("Cambodia");
        Location.add("American Samoa");
        Location.add("Anguilla");
        Location.add("Benin");
        Location.add("Bangladesh");
        Location.add("Brazil");
        Location.add("Canada");
        Location.add("Denmark");
        Location.add("Dominica");
        Location.add("Egypt");
        Location.add("Greenland");
        Location.add("Iceland");
        Location.add("Indonesia");
        Location.add("Japan");
        Location.add("Malaysia");
        Location.add("Morocco");

    }


    private void paginate() {

        cardStackView.setPaginationReserved();
        adapter.addAll(createTouristSpots());
        adapter.notifyDataSetChanged();
    }

    private List<TouristSpot> createTouristSpots() {
        List<TouristSpot> spots = new ArrayList<>();
        spots.add(new TouristSpot("Brooklyn Bridge", "New York", "http://clips.vorwaerts-gmbh.de/VfE_html5.mp4","https://source.unsplash.com/AWh9C-QjhE4/600x800"));
        spots.add(new TouristSpot("Fushimi Inari Shrine", "Kyoto", "http://clips.vorwaerts-gmbh.de/VfE_html5.mp4","https://source.unsplash.com/HN-5Z6AmxrM/600x800"));
        spots.add(new TouristSpot("Bamboo Forest", "Kyoto", "http://clips.vorwaerts-gmbh.de/VfE_html5.mp4","https://source.unsplash.com/LrMWHKqilUw/600x800"));
        spots.add(new TouristSpot("Brooklyn Bridge", "New York", "http://clips.vorwaerts-gmbh.de/VfE_html5.mp4","https://source.unsplash.com/USrZRcRS2Lw/600x800"));
        spots.add(new TouristSpot("Yasaka Shrine", "Kyoto", "http://clips.vorwaerts-gmbh.de/VfE_html5.mp4","https://source.unsplash.com/CdVAUADdqEc/600x800"));
        spots.add(new TouristSpot("Fushimi Inari Shrine", "Kyoto", "http://clips.vorwaerts-gmbh.de/VfE_html5.mp4","https://source.unsplash.com/AWh9C-QjhE4/600x800"));
        return spots;
    }

    private void reload() {
        cardStackView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter = createTouristSpotCardAdapter();
                cardStackView.setAdapter(adapter);
                cardStackView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        }, 1000);
    }

    private TouristSpotCardAdapter createTouristSpotCardAdapter() {
        final TouristSpotCardAdapter adapter = new TouristSpotCardAdapter(getContext(),this);
        adapter.addAll(createTouristSpots());
        return adapter;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void addView(String url,String nm) {
        rr.setVisibility(View.GONE);
        llv.setVisibility(View.VISIBLE);
        Glide.with(getContext()).load(url).into(imgv_profilepic);
        collapsingToolbarLayout.setTitle(nm);
        rrvBottom.setVisibility(View.GONE);
        //imgview.setImageURI(Uri.parse(url));
//        tvname.setText(nm);

    }

    //Method for card reverse

    private void reverse() {
        cardStackView.reverse();
    }

    private LinkedList<TouristSpot> extractRemainingTouristSpots() {
        LinkedList<TouristSpot> spots = new LinkedList<>();
        for (int i = cardStackView.getTopIndex(); i < adapter.getCount(); i++) {
            spots.add(adapter.getItem(i));
        }
        return spots;
    }

    //Method for SwipeUp

    private void swipeUp() {

        List<TouristSpot> spots = extractRemainingTouristSpots();
        if (spots.isEmpty()) {
            return;
        }

        View target = cardStackView.getTopView();

        ValueAnimator rotation = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("rotation", 0f));
        rotation.setDuration(200);
        ValueAnimator translateX = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationX", 0f, 0f));
        ValueAnimator translateY = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationY", 0f, -2000f));
        translateX.setStartDelay(100);
        translateY.setStartDelay(100);
        translateX.setDuration(500);
        translateY.setDuration(500);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(rotation, translateX, translateY);

        cardStackView.swipe(SwipeDirection.Top, set);
    }

    //Method for getting current location

    private void getLocation() {


    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
