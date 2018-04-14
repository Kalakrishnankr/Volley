package com.beachpartnerllc.beachpartner.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.beachpartnerllc.beachpartner.R;

public class SplashActivity extends Activity {

    private final int SPLASH_DELAY_TIME_DELAY=1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if(isNetworkAvailable()){

           new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                   Intent intent = new Intent(SplashActivity.this,TermsConditionsActivity.class);
                   startActivity(intent);
                   finish();
               }
           },SPLASH_DELAY_TIME_DELAY);

        }else {
            Toast.makeText(this, "Please Check your connection", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo !=null && activeNetworkInfo.isConnected();
    }
}
