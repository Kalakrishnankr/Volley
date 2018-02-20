package com.goldemo.beachpartner.connections;

import com.android.volley.Request;

/**
 * Created by seq-kala on 20/2/18.
 */

public class ApiService {
    public static int REQUEST_METHOD_POST   =   Request.Method.POST;
    public static int REQUEST_METHOD_GET    =   Request.Method.GET;

    public static String BASE_URL   =   "http://192.168.0.25:1901/beachpartner/";
    public static String SIGNUP     =   BASE_URL+"signup";
}
