package com.beachpartnerllc.beachpartner.adpters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.beachpartnerllc.beachpartner.fragments.BPFinderFragment;
import com.beachpartnerllc.beachpartner.fragments.ConnectionsTabFragment;

/**
 * Created by seq-kala on 7/3/18.
 */

public class SliderAdapter extends FragmentStatePagerAdapter {

    CharSequence titles[];
    int numberOfTabs;
    private static boolean isBPActive = false;
    private static boolean isPartner = false;

    public  SliderAdapter(FragmentManager fragmentManager, CharSequence[] titles, int numberOfTabs) {
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
            isBPActive =false;
            isPartner =true;
            BPFinderFragment bpFinderFragment = new BPFinderFragment(isBPActive,isPartner);
            return bpFinderFragment;
        }


    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }

}
