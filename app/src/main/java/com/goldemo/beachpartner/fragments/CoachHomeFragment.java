package com.goldemo.beachpartner.fragments;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.adpters.CardAdapter;
import com.goldemo.beachpartner.adpters.MessageAdapter;
import com.goldemo.beachpartner.calendar.compactcalendarview.domain.Event;
import com.goldemo.beachpartner.connections.ApiService;
import com.goldemo.beachpartner.connections.PrefManager;
import com.goldemo.beachpartner.models.EventAdminModel;
import com.goldemo.beachpartner.models.PersonModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CoachHomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FrameLayout coachHomeLikesCard;
    private String user_id,user_token,userType;
    CardAdapter adapter;
    MessageAdapter messageAdapter;
    private PrefManager prefManager;
    ArrayList<PersonModel> allSampleData;
    private RecyclerView upcomingRecyclerview,coachMsgRecyclerview;
    private ArrayList<Event>myUpcomingTList = new ArrayList<>();
    private TextView txtv_notour;

    public CoachHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CoachHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CoachHomeFragment newInstance(String param1, String param2) {
        CoachHomeFragment fragment = new CoachHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        prefManager = new PrefManager(getContext());
        user_id     =  prefManager.getUserId();
        user_token  =  prefManager.getToken();
        userType    = prefManager.getUserType();

        View view=inflater.inflate(R.layout.fragment_coach_home, container, false);
        allSampleData = (ArrayList<PersonModel>) createDummyData();
        getMyTournaments();

        initView(view);
        return view;

    }


    private void initView(View view){
        upcomingRecyclerview   =   (RecyclerView)view.findViewById(R.id.rcvUpComing);          //Recycler view for upcoming events
        coachMsgRecyclerview =   (RecyclerView)view.findViewById(R.id.rcv_message);  //Recycler view for messages
        txtv_notour     =   (TextView)  view.findViewById(R.id.txtv_notour);

        LinearLayoutManager layoutmnger = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);

        adapter = new CardAdapter(getContext(),myUpcomingTList);

        upcomingRecyclerview.setAdapter(adapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(upcomingRecyclerview);
        upcomingRecyclerview.setLayoutManager(layoutmnger);
        upcomingRecyclerview.setHasFixedSize(true);


        /*Message*/

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        messageAdapter  =  new MessageAdapter(getContext(),allSampleData);
        coachMsgRecyclerview.setAdapter(messageAdapter);

        SnapHelper snaper = new PagerSnapHelper();
        snaper.attachToRecyclerView(coachMsgRecyclerview);
//        coachMsgRecyclerview.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(5), true));
//        coachMsgRecyclerview.setItemAnimator(new DefaultItemAnimator());
        coachMsgRecyclerview.setLayoutManager(layoutManager);
        coachMsgRecyclerview.setHasFixedSize(true);


    }

    private List<PersonModel> createDummyData() {

        List<PersonModel>personModelList = new ArrayList<>();
        personModelList.add(new PersonModel("Alivia Orvieto","26",R.drawable.person1));
        personModelList.add(new PersonModel("Marti McLaurin","25",R.drawable.person2));
        personModelList.add(new PersonModel("Liz Held","30",R.drawable.person3));

        personModelList.add(new PersonModel("Alivia Orvieto","26",R.drawable.person1));
        personModelList.add(new PersonModel("Marti McLaurin","25",R.drawable.person2));
        personModelList.add(new PersonModel("Liz Held","30",R.drawable.person3));

        personModelList.add(new PersonModel("Alivia Orvieto","26",R.drawable.person1));
        personModelList.add(new PersonModel("Marti McLaurin","25",R.drawable.person2));
        personModelList.add(new PersonModel("Liz Held","30",R.drawable.person3));

        return personModelList;

    }

    //Get all my tournaments
    private void getMyTournaments() {
        myUpcomingTList.clear();
        SimpleDateFormat dft= new SimpleDateFormat("dd-MM-yyyy");
        Date date       = Calendar.getInstance().getTime();
        String fromDate = dft.format(date);
        Calendar cal    = Calendar.getInstance();
        cal.add(Calendar.MONTH, 5);
        Date date1      = cal.getTime();
        String toDate   = dft.format(date1);

        JsonArrayRequest jsonArrayRequest  = new JsonArrayRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_MYUPCOMING_TOURNAMENTS + "?fromDate=" + fromDate + "&toDate=" + toDate + "&userId=" + user_id, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Success response", response.toString());
                if(response != null){
                    for(int i=0;i<response.length();i++){

                        try {
                            JSONObject object = response.getJSONObject(i);

                            JSONObject obj = object.getJSONObject("event");
                            Event event = new Event();
                            event.setEventId(obj.getString("id"));
                            event.setEventName(obj.getString("eventName"));
                            event.setEventDescription(obj.getString("eventDescription"));
                            event.setEventLocation(obj.getString("eventLocation"));
                            event.setEventVenue(obj.getString("eventVenue"));
                            event.setEventStartDate(obj.getLong("eventStartDate"));
                            event.setEventEndDate(obj.getLong("eventEndDate"));
                            event.setEventRegStartdate(obj.getLong("eventRegStartDate"));
                            event.setEventEndDate(obj.getLong("eventRegEndDate"));

                            JSONObject objectadmin = obj.getJSONObject("eventAdmin");
                            EventAdminModel adminModel = new EventAdminModel();
                            adminModel.setFirstName(objectadmin.getString("firstName"));
                            event.setEventAdmin(adminModel);
                            myUpcomingTList.add(event);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    setUpMyComingTournament();

                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String json = null;
                Log.d("error--", error.toString());
                NetworkResponse response = error.networkResponse;
                if (response != null && response.data != null) {
                    switch (response.statusCode) {
                        case 401:
                            json = new String(response.data);
                            json = trimMessage(json, "detail");
                            if (json != null) {
                                Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;

                        default:
                            break;
                    }
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + user_token);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        Log.d("Request", jsonArrayRequest.toString());
        requestQueue.add(jsonArrayRequest);
    }

    private void setUpMyComingTournament() {
        if(myUpcomingTList.size()>0){
            adapter = new CardAdapter(getContext(),myUpcomingTList);
            upcomingRecyclerview.setAdapter(adapter);
            SnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(upcomingRecyclerview);
        }else {
            txtv_notour.setVisibility(View.VISIBLE);
        }

    }
    private String trimMessage(String json, String detail) {
        String trimmedString = null;

        try {
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(detail);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }
       /* Grid item spacing and padding */

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position / spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
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
