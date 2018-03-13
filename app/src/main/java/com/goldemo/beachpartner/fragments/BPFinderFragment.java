package com.goldemo.beachpartner.fragments;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.appyvet.materialrangebar.RangeBar;
import com.bumptech.glide.Glide;
import com.goldemo.beachpartner.MyInterface;
import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.adpters.TouristSpot;
import com.goldemo.beachpartner.adpters.TouristSpotCardAdapter;
import com.goldemo.beachpartner.calendar.compactcalendarview.CompactCalendarView;
import com.goldemo.beachpartner.cardstackview.CardStackView;
import com.goldemo.beachpartner.cardstackview.SwipeDirection;
import com.ramotion.foldingcell.FoldingCell;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;


public class BPFinderFragment extends Fragment implements MyInterface {


    private ProgressBar progressBar;
    private CardStackView cardStackView;
    private TouristSpotCardAdapter adapter;
    private RelativeLayout rr;
    private CoordinatorLayout llv;
    private ImageView imgv_profilepic,imgv_rvsecard,imgv_location,imgv_highfi,btnPlay;
    private TextView tvmonth,txtv_age,txtv_gender;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    private Spinner spinner_location;
    private RangeBar age_bar;
    public ToggleButton btnMale,btnFemale;
    private FoldingCell fc;
    private LinearLayout llvFilter;
    ArrayAdapter<String> dataAdapter;
    private ImageButton showPreviousMonthButton,showNextMonthButton;
    private Switch sCoach;
    private LinearLayout topFrameLayout;

    public static final String MY_PREFS_FILTER = "MyPrefsFile";
    ArrayList<String>Location = new ArrayList<>();
    private RelativeLayout rrvBottom;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    public boolean reverseCount=false;
    private boolean isbpActive =false;


    public BPFinderFragment() {

    }



    @SuppressLint("ValidFragment")
    public BPFinderFragment(boolean isBPActive) {
        isbpActive=isBPActive;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_bpfinder, container, false);
//       actionBar.setTitle("Beach Partner Finder");
        setUp(view);
        btnFemale.setText("Women");
        btnMale.setText("Men");
        btnFemale.setTextOff("Women");
        btnMale.setTextOff("Men");
        btnFemale.setTextOn("Women");
        btnMale.setTextOn("Men");
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
        tvmonth             =   (TextView) view.findViewById(R.id.month_name);
        ImageView toggle    =   (ImageView) view.findViewById(R.id.toggle);
        rrvBottom           =   (RelativeLayout) view.findViewById(R.id.rrv_bottomMenus);

        //Layout for filters

        llvFilter           =   (LinearLayout) view.findViewById(R.id.llFilter);
        txtv_age            =   (TextView) view.findViewById(R.id.txtv_age);
        age_bar             =   (RangeBar)  view.findViewById(R.id.rangebar);
        spinner_location    =   (Spinner) view.findViewById(R.id.spinner_location);
        txtv_gender         =   (TextView) view.findViewById(R.id.txtv_gender);

        btnMale             =   (ToggleButton) view.findViewById(R.id.btnMen);
        btnFemale           =   (ToggleButton) view.findViewById(R.id.btnWomen);
        btnPlay             =   (ImageView)view.findViewById(R.id.imgPlay);
        fc                  =   (FoldingCell)view. findViewById(R.id.folding_cell);
        topFrameLayout      =   (LinearLayout)view.findViewById(R.id.frmeOne);



        showPreviousMonthButton = (ImageButton) view.findViewById(R.id.prev_button);
        showNextMonthButton = (ImageButton) view.findViewById(R.id.next_button);

        sCoach              =   (Switch) view.findViewById(R.id.swich_coach);

        final CompactCalendarView compactCalendarView = (CompactCalendarView) view.findViewById(R.id.compactcalendar_view);
        compactCalendarView.setFirstDayOfWeek(Calendar.SUNDAY);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        collapsingToolbarLayout = (CollapsingToolbarLayout)view. findViewById(R.id.CollapsingToolbarLayout1);

        imgv_rvsecard   =   (ImageView) view.findViewById(R.id.ic_rvsecard);
        imgv_highfi     =   (ImageView) view.findViewById(R.id.ic_high);
        imgv_location   =   (ImageView) view.findViewById(R.id.ic_location);


        addLocation();

        showPreviousMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.showPreviousMonth();
            }
        });

        showNextMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.showNextMonth();
            }
        });

        //choose Location

        dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, Location);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_location.setAdapter(dataAdapter);

        String minValue= null;
        String maxValue=null;
        Set<String>fetch;
        //check shared prefvalue
        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_FILTER, MODE_PRIVATE);
        if(prefs!=null){

            int location     =   prefs.getInt("location",0);
            String Sgender   =   prefs.getString("gender",null);
            Boolean isCoach  =   prefs.getBoolean("isCoachActive",false);
            fetch    =   prefs.getStringSet("agerange",null);
            if(fetch!=null){
                ArrayList<String>data = new ArrayList<>(fetch);
                for(int i=0;i<data.size();i++){
                    minValue    = data.get(0);
                    maxValue    = data.get(1);
                }
                age_bar.setRangePinsByValue(Float.parseFloat(minValue),Float.parseFloat(maxValue));
                spinner_location.setSelection(location);
                txtv_age.setText(""+minValue+"-"+""+maxValue);
                sCoach.setChecked(isCoach);
                if(Sgender.equals("Men")){
                    txtv_gender.setText("Men");
                    btnMale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnMale.setTextColor(getResources().getColor(R.color.white));
                    btnMale.setChecked(true);
                }else if(Sgender.equals("Women")){
                    txtv_gender.setText("Women");
                    btnFemale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnFemale.setTextColor(getResources().getColor(R.color.white));
                    btnFemale.setChecked(true);

                }else {
                    btnMale.setChecked(true);
                    btnFemale.setChecked(true);
                    txtv_gender.setText("Both");
                    btnMale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnMale.setTextColor(getResources().getColor(R.color.white));
                    btnFemale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnFemale.setTextColor(getResources().getColor(R.color.white));
                    btnFemale.setChecked(true);
                    btnMale.setChecked(true);
                }
               // int position = dataAdapter.getPosition(location);
                //spinner_location.setSelection(position);
            }

        }

        //age range bar
        age_bar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {

                txtv_age.setText(""+leftPinValue+"-"+""+rightPinValue);

            }
        });


        // attach click listener to folding cell
        fc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fc.toggle(false);
            }
        });

        //button Men


        btnMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(btnFemale.isChecked()&&isChecked){
                    txtv_gender.setText("Both");
                    btnFemale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnMale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnFemale.setTextColor(getResources().getColor(R.color.white));
                    btnMale.setTextColor(getResources().getColor(R.color.white));
                }
                else if(isChecked){
                    txtv_gender.setText("Men");
                    btnMale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnMale.setTextColor(getResources().getColor(R.color.white));
                }
                else if((btnFemale.isChecked()&&!!isChecked )|| btnFemale.isChecked()){
                    txtv_gender.setText("Women");
                    btnMale.setBackground(getResources().getDrawable(R.color.imgBacgnd));
                    btnMale.setTextColor(getResources().getColor(R.color.black));
                }
                else{
                    txtv_gender.setText("  ");
                    btnMale.setBackground(getResources().getDrawable(R.color.imgBacgnd));
                    btnMale.setTextColor(getResources().getColor(R.color.black));
                }


            }
        });

        //button Women

        btnFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(btnMale.isChecked()&&isChecked){
                    txtv_gender.setText("Both");
                    btnFemale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnMale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnFemale.setTextColor(getResources().getColor(R.color.white));
                    btnMale.setTextColor(getResources().getColor(R.color.white));
                }
                else if(isChecked){
                    txtv_gender.setText("Women");
                    btnFemale.setBackground(getResources().getDrawable(R.color.menubar));
                    btnFemale.setTextColor(getResources().getColor(R.color.white));
                }
                else if((btnMale.isChecked()&&!!isChecked )|| btnMale.isChecked()){
                    txtv_gender.setText("Men");
                    btnFemale.setBackground(getResources().getDrawable(R.color.imgBacgnd));
                    btnFemale.setTextColor(getResources().getColor(R.color.black));
                }
                else{
                    txtv_gender.setText("  ");
                    btnFemale.setBackground(getResources().getDrawable(R.color.imgBacgnd));
                    btnFemale.setTextColor(getResources().getColor(R.color.black));
                }



            }
        });




        //add data to shared preference

        //play button
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Set<String> range = new HashSet<>();
                range.add(age_bar.getLeftPinValue());
                range.add(age_bar.getRightPinValue());


                SharedPreferences.Editor preferences = getActivity().getSharedPreferences(MY_PREFS_FILTER, MODE_PRIVATE).edit();
                preferences.putInt("location",spinner_location.getSelectedItemPosition());
                preferences.putStringSet("agerange",range);
                preferences.putString("gender",txtv_gender.getText().toString());
                preferences.putBoolean("isCoachActive",sCoach.isChecked());
                preferences.apply();
                preferences.commit();
                llvFilter.setVisibility(View.GONE);
                rr.setVisibility(View.VISIBLE);
                rrvBottom.setVisibility(View.VISIBLE);

               //check top bp strip **if its active only in bpfinder page(ie,BPFinder Fragment),it's disabled on calendar find Partner page
                if(isbpActive){
                    topFrameLayout.setVisibility(View.GONE);

                }else {
                    topFrameLayout.setVisibility(View.VISIBLE);

                }
            }
        });



        cardStackView.setCardEventListener(new CardStackView.CardEventListener() {
            @Override
            public void onCardDragging(float percentX, float percentY) {
                Log.d("CardStackView", "onCardDragging");
            }

            @Override
            public void onCardSwiped(SwipeDirection direction) {
                //abraham 08-03-2018
                reverseCount=true;
                imgv_rvsecard.setBackground(getResources().getDrawable(R.drawable.ic_backcard));

                Log.d("CardStackView", "onCardSwiped: " + direction.toString());
                Log.d("CardStackView", "topIndex: " + cardStackView.getTopIndex());
                if (cardStackView.getTopIndex() == adapter.getCount() - 5) {
                    Log.d("CardStackView", "Paginate: " + cardStackView.getTopIndex());
                    paginate();
                }
            }

            @Override
            public void onCardReversed() {
                reverseCount=false;
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
                rrvBottom.setVisibility(View.VISIBLE);

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
        spots.add(new TouristSpot("Renny", "Toronto", "http://seqato.com/bp/videos/1.mp4","http://seqato.com/bp/images/1.jpg"));
        spots.add(new TouristSpot("Mariyam Fenn", "Ottawa", "http://seqato.com/bp/videos/2.mp4","http://seqato.com/bp/images/2.jpg"));
        spots.add(new TouristSpot("Nancy", "Victoria", "http://seqato.com/bp/videos/3.mp4","http://seqato.com/bp/images/3.jpg"));
        spots.add(new TouristSpot("Nellie", "Thunder Bay", "http://seqato.com/bp/videos/1.mp4","http://seqato.com/bp/images/4.jpg"));
        spots.add(new TouristSpot("Elaine", "Barrie", "http://seqato.com/bp/videos/2.mp4","http://seqato.com/bp/images/5.jpg"));
        spots.add(new TouristSpot("Jane", "Kingston", "http://seqato.com/bp/videos/3.mp4","http://seqato.com/bp/images/6.jpg"));
        spots.add(new TouristSpot("Lisa", "Austin", "http://seqato.com/bp/videos/1.mp4","http://seqato.com/bp/images/7.jpg"));
        spots.add(new TouristSpot("Rachel", "Los Angeles", "http://seqato.com/bp/videos/2.mp4","http://seqato.com/bp/images/8.jpg"));
        spots.add(new TouristSpot("Sandra", "North West", "http://seqato.com/bp/videos/3.mp4","http://seqato.com/bp/images/9.jpg"));
        spots.add(new TouristSpot("Zora", "Nasville", "http://seqato.com/bp/videos/1.mp4","http://seqato.com/bp/images/10.jpg"));
        return spots;
    }

   /* private void reload() {
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
    }*/
   private void reload() {
       cardStackView.setVisibility(View.GONE);
       progressBar.setVisibility(View.VISIBLE);
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               if (getActivity() != null && cardStackView != null) {
                   if(adapter == null) {
                       adapter = createTouristSpotCardAdapter();
                   }
                   cardStackView.setAdapter(adapter);
                   adapter.notifyDataSetChanged();
                   cardStackView.setVisibility(View.VISIBLE);
                   progressBar.setVisibility(View.GONE);
               }
           }
       }, 1000);
   }

    private TouristSpotCardAdapter createTouristSpotCardAdapter() {

        adapter = new TouristSpotCardAdapter(getActivity(),this);
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

        if(reverseCount) {
            cardStackView.reverse();
            imgv_rvsecard.setBackground(getResources().getDrawable(R.drawable.ic_backcard_disable));
        }
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
