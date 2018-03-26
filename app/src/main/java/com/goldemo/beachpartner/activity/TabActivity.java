package com.goldemo.beachpartner.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.fragments.BPFinderFragment;
import com.goldemo.beachpartner.fragments.CalendarFragment;
import com.goldemo.beachpartner.fragments.ConnectionFragment;
import com.goldemo.beachpartner.fragments.HiFiveFragment;
import com.goldemo.beachpartner.fragments.HomeFragment;
import com.goldemo.beachpartner.fragments.MessageFragment;
import com.goldemo.beachpartner.fragments.ProfileFragment;

public class TabActivity extends AppCompatActivity {

    private TextView mTextMessage;
    HomeFragment homeFragment;
    private String YOUR_FRAGMENT_STRING_TAG;
    private Boolean activeMoreStatus=false;
    private Boolean moreClickedOnce=true;
    private Boolean floatMenu1Active=true;
    private Boolean floatMenu2Active=false;

    FloatingActionButton menu1;
    FloatingActionButton menu2;

    private BottomNavigationView navigation;
    private static boolean isBPActive = false;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    HomeFragment homeFragment = new HomeFragment();
                    getSupportActionBar().setTitle("Beach Partner");
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.container, homeFragment,YOUR_FRAGMENT_STRING_TAG);
                    transaction.commit();
                    disableFloatButtons();
                    return true;
                case R.id.navigation_bp:

                    BPFinderFragment bpFinderFragment = new BPFinderFragment(isBPActive);
                    getSupportActionBar().setTitle("Beach Partner Finder");
                    FragmentManager mng = getSupportFragmentManager();
                    FragmentTransaction tran = mng.beginTransaction();
                    tran.replace(R.id.container, bpFinderFragment,YOUR_FRAGMENT_STRING_TAG);
                    tran.commit();
                    disableFloatButtons();
                    return true;

                case R.id.navigation_connection:

                    ConnectionFragment connectionFragment = new ConnectionFragment();
                    getSupportActionBar().setTitle("Connections");
                    FragmentManager mngr = getSupportFragmentManager();
                    FragmentTransaction trans = mngr.beginTransaction();
                    trans.replace(R.id.container,connectionFragment,YOUR_FRAGMENT_STRING_TAG);
                    trans.commit();
                    disableFloatButtons();
                    return true;

                case R.id.navigation_calendar:

                    CalendarFragment calendarFragment   =   new CalendarFragment();
                    getSupportActionBar().setTitle("Calendar");
                    FragmentManager cmngr   =   getSupportFragmentManager();
                    FragmentTransaction ctrans = cmngr.beginTransaction();
                    ctrans.replace(R.id.container,calendarFragment,YOUR_FRAGMENT_STRING_TAG);
                    ctrans.commit();
                    disableFloatButtons();
                    return true;


                case R.id.navigation_more:
                    activeMoreStatus=!activeMoreStatus;

                    if(activeMoreStatus){
                        menu1.setVisibility(View.VISIBLE);
                        menu2.setVisibility(View.VISIBLE);
                        if(moreClickedOnce||floatMenu1Active){
                            activateMessageFragment();
                            moreClickedOnce=false;
                        }
                        else if(floatMenu2Active){
                            activateHiFiveFragment();
                        }


                        menu1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                activateMessageFragment();
                                menu1.setImageResource(R.drawable.ic_chat_bubble_active);
                                menu2.setImageResource(R.drawable.ic_highfive);

                                disableFloatButtons();
                            }
                        });

                        menu2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                activateHiFiveFragment();
                                menu1.setImageResource(R.drawable.ic_chat_bubble);
                                menu2.setImageResource(R.drawable.ic_highfive_seen);

                                disableFloatButtons();
                            }
                        });

                    }
                    else{
                        disableFloatButtons();
                    }

                    return true;

                /*case R.id.navigation_notifications:
                    return true;*/
            }
            return false;
        }
    };

    //to disable float buttons
    public void disableFloatButtons(){
        menu1.setVisibility(View.GONE);
        menu2.setVisibility(View.GONE);
        activeMoreStatus=false;
    }

    //to show messages fragment
    public void activateMessageFragment(){
        floatMenu1Active=true;
        floatMenu2Active=false;
        MessageFragment messageFragment   =   new MessageFragment();
        getSupportActionBar().setTitle("Messages");
        FragmentManager messageFManager   =   getSupportFragmentManager();
        FragmentTransaction messageTrans = messageFManager.beginTransaction();
        messageTrans.replace(R.id.container,messageFragment,YOUR_FRAGMENT_STRING_TAG);
        messageTrans.commit();
    }

    public void activateHiFiveFragment(){
        floatMenu1Active=false;
        floatMenu2Active=true;
        HiFiveFragment hiFiveFragment   =   new HiFiveFragment();
        getSupportActionBar().setTitle("High Fives");
        FragmentManager hiFiveFManager   =   getSupportFragmentManager();
        FragmentTransaction hiFiveTrans = hiFiveFManager.beginTransaction();
        hiFiveTrans.replace(R.id.container,hiFiveFragment,YOUR_FRAGMENT_STRING_TAG);
        hiFiveTrans.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        FrameLayout fg = (FrameLayout)findViewById(R.id.container);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        menu1 = findViewById(R.id.submenu1);
        menu2 = findViewById(R.id.submenu2);



        /*Load default HomeFragment*/

        HomeFragment homeFragment = new HomeFragment();
        FragmentManager manager = getSupportFragmentManager();
        getSupportActionBar().setTitle("Beach Partner");
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container, homeFragment,YOUR_FRAGMENT_STRING_TAG);
        transaction.commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_class_fragment,menu);
        return true;
    }

    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_class_fragment,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                //Toast.makeText(this, "Clicked Profile", Toast.LENGTH_SHORT).show();
                    ProfileFragment pf = new ProfileFragment();
                    getSupportActionBar().setTitle("Profile");
                    FragmentManager mang = getSupportFragmentManager();
                    FragmentTransaction trans = mang.beginTransaction();
                    trans.replace(R.id.container, pf,YOUR_FRAGMENT_STRING_TAG);
                    trans.commit();
                break;
            case R.id.about_us:
                Toast.makeText(this, "Clicked AboutUs", Toast.LENGTH_SHORT).show();
                break;
            case R.id.feedback:
                Toast.makeText(this, "Clicked Feedback", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this, "Clicked Settings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.help:
                Toast.makeText(this, "Clicked Help", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                Toast.makeText(this, "Clicked LogOut", Toast.LENGTH_SHORT).show();
                alertLogout();
                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void alertLogout() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Logout")
                        .setMessage("Would you like to logout ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //logout
                                finish();
                                //System.exit(0);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.cancel();
                            }
                        });
                AlertDialog dialog =  builder.create();
                dialog.show();

    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

}
