package com.beachpartnerllc.beachpartner.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.connections.PrefManager;
import com.beachpartnerllc.beachpartner.fragments.ImageFragment;

import java.util.ArrayList;

/**
 * Created by seq-kala on 17/4/18.
 */

public class WelcomeActivity extends AppCompatActivity {

    public static final String EXTRA_IS_FROM_INIT = "extra_is_from_init";
    private String YOUR_FRAGMENT_STRING_TAG;
    private ViewPager viewPager;
    public MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private Button btnSkip, btnNext;
    private PrefManager prefManager;
    private String userType,fromHelp;
    private View view;
    
    private static final int images[] = new int[]{
        R.drawable.art_home_1_various_360dp_1,
        R.drawable.art_home_2_various_360dp_2,
        R.drawable.art_home_3_various_360dp_3,
        R.drawable.art_home_4_various_360dp_4,
        R.drawable.art_bpfinder_5_various_360dp_5,
        R.drawable.art_bpfinder_6_various_360dp_6,
        R.drawable.art_eventdes_7_various_360dp_7,
        R.drawable.art_message_8_various_360dp_8,
        R.drawable.art_hifi_9_various_360dp_9
    };
    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        
        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            final boolean isFromStart = getIntent().getBooleanExtra(EXTRA_IS_FROM_INIT, false);
            
            if (position == 0 && isFromStart) {
                btnSkip.setVisibility(View.GONE);
            }
            
            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == images.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnNext.setText(getString(R.string.next));
                
                if (position == 0 && isFromStart) {
                    btnSkip.setVisibility(View.GONE);
                } else {
                    btnSkip.setVisibility(View.VISIBLE);
                }
            }
        }
        
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        
        }
        
        @Override
        public void onPageScrollStateChanged(int arg0) {
        
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userType =  new PrefManager(getApplicationContext()).getUserType().trim();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            fromHelp = bundle.getString("reDirectPage");
        }
        // Checking for first time launch - before calling setContentView()
        prefManager = new PrefManager(this);
        if (prefManager.getTips()) {
            launchHomeScreen();
            finish();
        }

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_welcome);
    
        viewPager = findViewById(R.id.view_pager);
        dotsLayout = findViewById(R.id.layoutDots);
        btnSkip = findViewById(R.id.btn_skip);
        btnNext = findViewById(R.id.btn_next);


        // layouts of all welcome sliders
        // add few more layouts if you want

        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();
    
        setupImages();
        

        final boolean isFromStart = getIntent().getBooleanExtra(EXTRA_IS_FROM_INIT, false);
        if (isFromStart) {
            btnSkip.setVisibility(View.GONE);
        }

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() == 0) {
                    if (!isFromStart) launchHomeScreen();
                    else{
                        btnSkip.setVisibility(View.INVISIBLE);
                    }
                } else {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < images.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });
    }
    
    private void setupImages() {
        ArrayList<Fragment> fragments = new ArrayList<>(images.length);
        
        for (int image : images) {
            fragments.add(ImageFragment.Companion.newInstance(image));
        }
        
        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        viewPager.setAdapter(myViewPagerAdapter);
    }
    
    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }
    
    private void launchHomeScreen() {
        prefManager.saveTips(true);
        Intent intent = new Intent(WelcomeActivity.this, TabActivity.class);
        intent.putExtra("reDirectPage", fromHelp);
        startActivity(intent);
        finish();
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[images.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends FragmentPagerAdapter {
        private final ArrayList<Fragment> mItemList;
    
        MyViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> images) {
            super(fm);
            mItemList = images;
        }
    
        @Override
        public Fragment getItem(int position) {
            return mItemList.get(position);
        }

        @Override
        public int getCount() {
            return mItemList.size();
        }
    }
}