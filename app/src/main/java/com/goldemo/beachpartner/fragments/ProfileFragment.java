package com.goldemo.beachpartner.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldemo.beachpartner.CircularImageView;
import com.goldemo.beachpartner.OnClickListener;
import com.goldemo.beachpartner.R;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TabLayout tabs;
    private LinearLayout llBaseInfo,llMoreInfo;
    private ViewPager viewPager;
    private FrameLayout videoFrame;
    private ImageView imgEdit;
    private CircularImageView imgProfile;
    private TextView profileName,profileDesig;
    private OnClickListener mOnClickListener;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        initActivity(view);
        return view;
    }

    private void initActivity(View view) {

        tabs        =   (TabLayout)view.findViewById(R.id.tabs);
        viewPager   =   (ViewPager) view.findViewById(R.id.pager);
        videoFrame  =   (FrameLayout)view.findViewById(R.id.header_cover_video);
        imgEdit     =   (ImageView)view.findViewById(R.id.edit);

        imgProfile  =   (CircularImageView)view.findViewById(R.id.row_icon);
        profileName =   (TextView)view.findViewById(R.id.profile_name);
        profileDesig=   (TextView)view.findViewById(R.id.profile_designation);
        setupViewPager(viewPager);
        tabs.setupWithViewPager(viewPager);

        imgEdit.setOnClickListener(this);




    }

    private void setupViewPager(ViewPager viewPager) {

        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new BasicInfoFragment(),"Basic Information");
        adapter.addFragment(new MoreInfoFragment(),"More Information");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.edit:
                mOnClickListener.click();
                break;

                default:
                    break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mOnClickListener = (OnClickListener) getActivity();


        //Your callback initialization here
    }



    //tabHost.addTab(tabHost.newTabSpec("basicInfo").setIndicator("Basic Information").setContent());
        //tabHost.addTab(tabHost.newTabSpec("moreInfo").setIndicator("More Information").setContent());


    // TODO: Rename method, update argument and hook method into UI event




    private class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
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
