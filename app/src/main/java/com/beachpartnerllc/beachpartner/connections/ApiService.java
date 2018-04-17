package com.beachpartnerllc.beachpartner.connections;

import com.android.volley.Request;

/**
 * Created by seq-kala on 20/2/18.
 */

public class ApiService {
    public static int REQUEST_METHOD_POST   =   Request.Method.POST;
    public static int REQUEST_METHOD_GET    =   Request.Method.GET;
    public static int REQUEST_METHOD_PUT    =   Request.Method.PUT;

    //For instagram

    public static final String CLIENT_ID    ="0c699a86d6ec4266ac7ef63a84cb6115";
    public static final String CLIENT_SECRET="9d7555b87c5d469e806f66d578db90e4";
    public static final String REDIRECT_URI ="http://beachpartner.com";

    public static String BASE_URL   =   "https://beachpartner.com/api/";
    public static String SIGNUP     =    BASE_URL+"register";
    public static String LOGIN      =    BASE_URL+"authenticate";

    public static String REQUEST_PASSWORD_RESET = BASE_URL+"account/reset-password/init";
    public static String CHANGE_PASSWORD = BASE_URL+"account/reset-password/finish";

    public static String GET_ACCOUNT_DETAILS    =  BASE_URL+"account";
    public static String SAVE_ACCOUNT_DETAILS   =  BASE_URL+"account";

    public static  String GET_SUBSCRIPTIONS     =  BASE_URL+"users/subscriptions";

    public static String GET_ALL_EVENTS         =  BASE_URL+"events/all";
    public static String GET_USER_EVENTS        =  BASE_URL+"events/user/";

    public static String GET_ALL_CONNECTIONS     =  BASE_URL+"users/connected/";
    public static String ADD_PROFILE_VIDEO_IMAGE =  BASE_URL+"storage/uploadProfileData";

    public static String  SEARCH_USER_CARD       = BASE_URL+"users/search";

    public static String  UPDATE_USER_PROFILE        = BASE_URL+"users/update-all/";
    public static String  GET_MYUPCOMING_TOURNAMENTS = BASE_URL+"events/user/between";


    public static String  EVENT_REGISTER           =   BASE_URL+"events/user/register";
    public static String  RIGHT_SWIPE_REQUEST_SEND =   BASE_URL+"users/request-friendship/";
    public static String  LEFT_SWIPE_DISLIKE       =   BASE_URL+"users/reject-friendship/";
    public static String  HIFI_SWIPE_UP            =   BASE_URL+"users/hifi/";

    public static String BLOCK_PERSON          =    BASE_URL+"users/block-user/";




}
