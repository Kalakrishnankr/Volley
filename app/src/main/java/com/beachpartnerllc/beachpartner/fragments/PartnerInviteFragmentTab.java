package com.beachpartnerllc.beachpartner.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.adpters.SliderAdapter;
import com.beachpartnerllc.beachpartner.calendar.myowncalendar.SlidingTab;


public class PartnerInviteFragmentTab extends Fragment {

    private ViewPager pager;
    private SlidingTab tabs;
    private SliderAdapter slideAdapter;
    private int numberOfTabs = 2;
    private CharSequence titles[] = {"Connections","Find Partners"};
    private long eventDateToCheck;
    public PartnerInviteFragmentTab() {
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
        eventDateToCheck= getArguments().getLong("eventDate");
        View view   =   inflater.inflate(R.layout.fragment_partner_invite, container, false);
        initActivity(view);
        return view;
    }

    private void initActivity(View view) {

        pager           =  (ViewPager) view.findViewById(R.id.pager);
        tabs            =  (SlidingTab) view.findViewById(R.id.tabs);
        slideAdapter    =  new SliderAdapter(getFragmentManager(), titles, numberOfTabs,eventDateToCheck);

        pager.setAdapter(slideAdapter);

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        tabs.setViewPager(pager);
        slideAdapter.notifyDataSetChanged();
        pager.setAdapter(slideAdapter);

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here

        inflater.inflate(R.menu.menu_search,menu);
        super.onCreateOptionsMenu(menu, inflater);


        /*super.onCreateOptionsMenu(menu, inflater); menu.clear();
        inflater.inflate(R.menu.sample_menu, menu);*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_search:

                break;
            default:
                break;
        }
        return false;
    }

}
