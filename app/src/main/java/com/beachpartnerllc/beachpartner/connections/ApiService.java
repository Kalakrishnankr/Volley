package com.beachpartnerllc.beachpartner.connections;

import com.android.volley.Request;

/**
 * Created by seq-kala on 20/2/18.
 */

public class ApiService {
    public static int REQUEST_METHOD_POST   =   Request.Method.POST;
    public static int REQUEST_METHOD_GET    =   Request.Method.GET;
    public static int REQUEST_METHOD_PUT    =   Request.Method.PUT;
    public static int REQUEST_METHOD_DELETE =   Request.Method.DELETE;

    //For instagram

    public static final String CLIENT_ID    ="0c699a86d6ec4266ac7ef63a84cb6115";
    public static final String CLIENT_SECRET=" 9d7555b87c5d469e806f66d578db90e4";
    public static final String REDIRECT_URI ="http://beachpartner.com";

    public static String BASE_URL   =   "https://beachpartner.com/api/";
  // public static String BASE_URL   =    "http://192.168.0.34:8080/api/" ;
    public static String SIGNUP     =    BASE_URL+"register";
    public static String LOGIN      =    BASE_URL+"authenticate";

    public static String LOGIN_WITH_SOCIAL_MEDIA =  BASE_URL+"authenticate-with-token";

    public static String REQUEST_PASSWORD_RESET = BASE_URL+"account/reset-password/init";
    public static String CHANGE_PASSWORD = BASE_URL+"account/reset-password/finish";

    public static String GET_ACCOUNT_DETAILS    =  BASE_URL+"account";
    public static String UPDATE_USER_PROFILE    =  BASE_URL+"users/update-all/";
    public static String SAVE_ACCOUNT_DETAILS   =  BASE_URL+"account";

    public static  String GET_SUBSCRIPTIONS     =  BASE_URL+"users/subscriptions";

    public static String GET_ALL_EVENTS         =  BASE_URL+"events/all";
    public static String GET_USER_EVENTS        =  BASE_URL+"events/user/";
    public static String GET_INVITATION_LIST    =  BASE_URL+"events/user/invitations/";
    public static String GET_ALL_SENDORRECIVE_REQUEST = BASE_URL +"events/user/invitations";
    public static String HANDLE_EVENT           =  BASE_URL+"events/user/invitationResponse";
    public static String COURT_NOTIFY           =  BASE_URL + "events/user/notifyCourtNumber";
    public static String SEARCH_EVENTS          =  BASE_URL +"events/search";


    public static String GET_ALL_CONNECTIONS     =  BASE_URL+"users/connected/";
    public static String GET_ALL_LIKES           =  BASE_URL+"users/connected-count/";
    public static String ADD_PROFILE_VIDEO_IMAGE =  BASE_URL+"storage/uploadProfileData";

    public static String  SEARCH_USER_CARD        =  BASE_URL+"users/search";
    public static String  GET_MYUPCOMING_TOURNAMENTS = BASE_URL+"events/user/between";


    public static String  EVENT_REGISTER           =   BASE_URL+"events/user/register";
    public static String  RIGHT_SWIPE_REQUEST_SEND =   BASE_URL+"users/request-friendship/";
    public static String  LEFT_SWIPE_DISLIKE       =   BASE_URL+"users/reject-friendship/";
    public static String  HIFI_SWIPE_UP            =   BASE_URL+"users/hifi/";
    public static String  REVERSE_SWIPE_CARD       =   BASE_URL+"users/undo-request/";


    public static String BLOCK_PERSON              =    BASE_URL+"users/block-user/";
    public static String UNBLOCK_PERSON            =    BASE_URL+"users/unblock-user/";

    public static String CREATE_NOTE               =   BASE_URL+"notes/";
    public static String GETALL_NOTE_FROM          =   BASE_URL+"notes/from/";
    public static String DELETE_NOTE               =   BASE_URL+"notes/";
    public static String UPDATE_NOTE               =   BASE_URL+"notes/";
    public static String TERMS_AND_CONDITION       =   "https://www.beachpartner.com/terms.html";

    public static String GET_ALL_BLUEBP            =    BASE_URL+"users/search?includeCoach=false&hideLikedUser=true&hideConnectedUser=true&hideRejectedConnections=true&hideBlockedUsers=true&subscriptionType=BlueBP";

    public static String ABOUT_US_PAGE             =   "https://www.beachpartner.com/about_us.html";

    public static String FEEDBACK_PAGE             =   "https://www.beachpartner.com/feedback.html";


}
