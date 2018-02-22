package com.goldemo.beachpartner.connections;

import com.android.volley.Request;

/**
 * Created by seq-kala on 20/2/18.
 */

public class ApiService {
    public static int REQUEST_METHOD_POST   =   Request.Method.POST;
    public static int REQUEST_METHOD_GET    =   Request.Method.GET;

    //For instagram

    public static final String CLIENT_ID="7ef2349d030a43a799b913483158b83b";
    public static final String CLIENT_SECRET="b5ee7c0a6cf64a77a32ebcadde5c428c";
    public static final String REDIRECT_URI="http://35.167.128.181/";

    public static String BASE_URL   =   "http://192.168.0.25:1901/beachpartner/";
    public static String SIGNUP     =   BASE_URL+"signup";
}
