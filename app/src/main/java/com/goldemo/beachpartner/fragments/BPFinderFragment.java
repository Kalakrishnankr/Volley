package com.goldemo.beachpartner.fragments;

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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import java.util.List;
import java.util.Locale;


public class BPFinderFragment extends Fragment implements MyInterface {


    private ProgressBar progressBar;
    private CardStackView cardStackView;
    private TouristSpotCardAdapter adapter;
    private RelativeLayout rr;
    private CoordinatorLayout llv;
    private ImageView imgview;
    private TextView tvname,tvmonth;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());

    public BPFinderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BPFinderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BPFinderFragment newInstance(String param1, String param2) {
        BPFinderFragment fragment = new BPFinderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

        progressBar     = (ProgressBar) view.findViewById(R.id.activity_main_progress_bar);
        rr              = (RelativeLayout) view.findViewById(R.id.rr);
        llv             = (CoordinatorLayout)view.findViewById(R.id.llMoreinfo);
        imgview         = (ImageView)view.findViewById(R.id.img_profile);
        //tvname          = (TextView)view.findViewById(R.id.txt_name);
        cardStackView   = (CardStackView) view.findViewById(R.id.activity_main_card_stack_view);
        tvmonth         = (TextView) view.findViewById(R.id.txtv_month);
        final CompactCalendarView compactCalendarView = (CompactCalendarView) view.findViewById(R.id.compactcalendar_view);
        compactCalendarView.setFirstDayOfWeek(Calendar.SUNDAY);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)view. findViewById(R.id.CollapsingToolbarLayout1);
        collapsingToolbarLayout.setTitle("Collapsing Toolbar");


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


    }



    private void paginate() {

        cardStackView.setPaginationReserved();
        adapter.addAll(createTouristSpots());
        adapter.notifyDataSetChanged();
    }

    private List<TouristSpot> createTouristSpots() {
        List<TouristSpot> spots = new ArrayList<>();
        spots.add(new TouristSpot("Brooklyn Bridge", "New York", "http://clips.vorwaerts-gmbh.de/VfE_html5.mp4","https://source.unsplash.com/AWh9C-QjhE4/600x800"));
        spots.add(new TouristSpot("Fushimi Inari Shrine", "Kyoto", "http://abhiandroid-8fb4.kxcdn.com/ui/wp-content/uploads/2016/04/videoviewtestingvideo.mp4","https://source.unsplash.com/HN-5Z6AmxrM/600x800"));
        spots.add(new TouristSpot("Bamboo Forest", "Kyoto", "http://www.quirksmode.org/html5/videos/big_buck_bunny.mp4","https://source.unsplash.com/LrMWHKqilUw/600x800"));
        spots.add(new TouristSpot("Brooklyn Bridge", "New York", "http://clips.vorwaerts-gmbh.de/VfE_html5.mp4","https://source.unsplash.com/USrZRcRS2Lw/600x800"));
        spots.add(new TouristSpot("Yasaka Shrine", "Kyoto", "http://www.quirksmode.org/html5/videos/big_buck_bunny.mp4","https://source.unsplash.com/CdVAUADdqEc/600x800"));
        spots.add(new TouristSpot("Fushimi Inari Shrine", "Kyoto", "http://www.quirksmode.org/html5/videos/big_buck_bunny.mp4","https://source.unsplash.com/AWh9C-QjhE4/600x800"));
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
        Glide.with(getContext()).load(url).into(imgview);
        //imgview.setImageURI(Uri.parse(url));
//        tvname.setText(nm);

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
