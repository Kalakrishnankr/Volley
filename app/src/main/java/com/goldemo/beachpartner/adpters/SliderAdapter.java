package com.goldemo.beachpartner.adpters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.goldemo.beachpartner.fragments.BPFinderFragment;
import com.goldemo.beachpartner.fragments.ConnectionsTabFragment;

/**
 * Created by seq-kala on 7/3/18.
 */

public class SliderAdapter extends FragmentStatePagerAdapter {

    CharSequence titles[];
    int numberOfTabs;
    public SliderAdapter(FragmentManager fragmentManager, CharSequence[] titles, int numberOfTabs) {
        super(fragmentManager);

        this.titles =titles;
        this.numberOfTabs=numberOfTabs;

    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
    @Override
    public Fragment getItem(int position) {
        if(position==0){

            ConnectionsTabFragment connectionsTabFragment = new ConnectionsTabFragment();
            return connectionsTabFragment;

        }else {
            BPFinderFragment bpFinderFragment = new BPFinderFragment();
            return bpFinderFragment;
        }


    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
