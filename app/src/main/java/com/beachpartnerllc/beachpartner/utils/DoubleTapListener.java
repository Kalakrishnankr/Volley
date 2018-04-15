package com.beachpartnerllc.beachpartner.utils;

import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by seqato on 15/04/18.
 */


public abstract class DoubleTapListener implements View.OnTouchListener {

    private static final long DOUBLE_CLICK_TIME_DELTA = 300;//milliseconds

    long lastClickTime = 0;


    @Override
    public boolean onTouch(View v, MotionEvent event) {

       // long DOUBLE_CLICK_TIME_DELTA = 300;//milliseconds

       // long lastClickTime = 0;


        long clickTime = System.currentTimeMillis();
        if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA){
            onDoubleClick(v);
            lastClickTime = 0;
        } else {
            onSingleClick(v);
        }
        lastClickTime = clickTime;


        return false;

    }





    public abstract void onSingleClick(View v);
    public abstract void onDoubleClick(View v);
}