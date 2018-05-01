package com.beachpartnerllc.beachpartner.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.beachpartnerllc.beachpartner.CustomTextView;
import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.connections.ApiService;
import com.beachpartnerllc.beachpartner.connections.PrefManager;
import com.beachpartnerllc.beachpartner.models.BpFinderModel;
import com.beachpartnerllc.beachpartner.utils.OnRecyclerOnClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.apptik.widget.MultiSlider;

/**
 * Created by allu on 29/4/18 12:25 PM.
 *
 * @author Ameen Maheen
 * Happy Coding
 */
public class BpSearchFragment extends Fragment implements OnRecyclerOnClickListener, View.OnClickListener {


    protected CustomTextView txtvMinAge;
    protected CustomTextView txtvMaxAge;
    protected MultiSlider rangebar;
    protected Spinner spinnerLocation;
    protected CustomTextView txtvGender;
    protected ToggleButton btnMen;
    protected ToggleButton btnWomen;
    protected Switch swichCoach;
    protected ImageView imgPlay;
    protected LinearLayout llFilter;

    String location;
    int minAge =5;
    int maxAge=35;

    private static final String TAG = "BpSearchFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        addLocation();

    }

    @Override
    public void onItemClick(Object item, int position) {

    }

    private void initView(View rootView) {
        txtvMinAge = (CustomTextView) rootView.findViewById(R.id.txtv_minAge);
        txtvMaxAge = (CustomTextView) rootView.findViewById(R.id.txtv_maxAge);
        rangebar = (MultiSlider) rootView.findViewById(R.id.rangebar);
        spinnerLocation = (Spinner) rootView.findViewById(R.id.spinner_location);
        txtvGender = (CustomTextView) rootView.findViewById(R.id.txtv_gender);
        btnMen = (ToggleButton) rootView.findViewById(R.id.btnMen);
        btnWomen = (ToggleButton) rootView.findViewById(R.id.btnWomen);
        swichCoach = (Switch) rootView.findViewById(R.id.swich_coach);
        imgPlay = (ImageView) rootView.findViewById(R.id.imgPlay);
        imgPlay.setOnClickListener(BpSearchFragment.this);
        llFilter = (LinearLayout) rootView.findViewById(R.id.llFilter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imgPlay) {
            initSearch();
        }
    }


    private void initSearch() {

        String sgender = txtvGender.getText().toString();
        boolean isCoach = swichCoach.isChecked();
//        minAge = Integer.parseInt(txtvMinAge.getText().toString().trim());
//        maxAge = Integer.parseInt(txtvMaxAge.getText().toString().trim());

        if (sgender.equals("Both")) {
            sgender = "";
        }

        new PrefManager(getActivity()).saveSettingData(location, sgender, isCoach, minAge, maxAge);
        getAllCards(location, sgender, isCoach, minAge, maxAge);
    }

    //GEt all cards
    private void getAllCards(String location, String sgender, Boolean isCoach, int minAge, int maxAge) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ApiService.REQUEST_METHOD_GET, ApiService.SEARCH_USER_CARD + "?includeCoach=" + isCoach + "&minAge=" + minAge + "&maxAge=" + maxAge + "&gender=" + sgender + "&hideConnectedUser=true&hideLikedUser=true&hideRejectedConnections=true&hideBlockedUsers=true", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "onResponse: " + response.toString());
                        if (response != null) {
                            for (int i = 0; i < response.length(); i++) {
                                try {

                                    JSONObject jsonObject = response.getJSONObject(i);

                                    BpFinderModel finderModel = new BpFinderModel();
                                    finderModel.setBpf_id(jsonObject.getString("id"));
                                    finderModel.setBpf_login(jsonObject.getString("login"));
                                   // finderModel.setBpf_userProfile(jsonObject.getString("userProfile"));
                                    // finderModel.setBpf_subscriptions(jsonObject.getString("subscriptions"));
                                    finderModel.setBpf_firstName(jsonObject.getString("firstName"));
                                    finderModel.setBpf_lastName(jsonObject.getString("lastName"));
                                    finderModel.setBpf_email(jsonObject.getString("email"));
                                    finderModel.setBpf_activated(jsonObject.getString("activated"));
                                    finderModel.setBpf_langKey(jsonObject.getString("langKey"));
                                    finderModel.setBpf_imageUrl(jsonObject.getString("imageUrl"));
                                    finderModel.setBpf_videoUrl(jsonObject.getString("videoUrl"));
                                    //finderModel.setBpf_resetDate(jsonObject.getString("resetDate"));
                                    finderModel.setBpf_dob(jsonObject.getString("dob"));
                                    finderModel.setBpf_gender(jsonObject.getString("gender"));
                                    finderModel.setBpf_loginType(jsonObject.getString("loginType"));
                                    finderModel.setBpf_city(jsonObject.getString("city"));
                                    finderModel.setBpf_phoneNumber(jsonObject.getString("phoneNumber"));
                                    finderModel.setBpf_deviceId(jsonObject.getString("deviceId"));
                                    finderModel.setBpf_authToken(jsonObject.getString("authToken"));
                                    finderModel.setBpf_location(jsonObject.getString("location"));
                                    finderModel.setBpf_userType(jsonObject.getString("userType"));

                                    finderModel.setBpf_fcmToken(jsonObject.getString("fcmToken"));

                                    JSONObject profileObject = jsonObject.getJSONObject("userProfile");
                                    finderModel.setBpf_topfinishes(profileObject.getString("topFinishes"));
                                    //finderModel.setBpf_age(jsonObject.getString("age"));
                                    //                   allCardList.add(finderModel);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

//                            addCards();

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
                            //   json = trimMessage(json, "detail");
                            if (json != null) {
                                Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;

                        default:
                            break;
                    }
                }

            }
        }) {

            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                //    headers.put("Authorization","Bearer "+token);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };
        if (getActivity() != null) {
            int socketTimeout = 30000; // 30 seconds. You can change it
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            Log.d("Request", jsonArrayRequest.toString());
            jsonArrayRequest.setRetryPolicy(policy);
            requestQueue.add(jsonArrayRequest);
        }


    }


    public void addLocation() {

        ArrayList<String> stateList = new ArrayList<>();

        stateList.add("Alabama");
        stateList.add("Alaska");
        stateList.add("Arizona");
        stateList.add("Arkansas");
        stateList.add("California");
        stateList.add("Colorado");
        stateList.add("Connecticut");
        stateList.add("Delaware");
        stateList.add("Florida");
        stateList.add("Georgia");
        stateList.add("Hawaii");
        stateList.add("Idaho");
        stateList.add("Illinois");
        stateList.add("Indiana");
        stateList.add("Iowa");
        stateList.add("Kansas");
        stateList.add("Kentucky");
        stateList.add("Louisiana");
        stateList.add("Maine");
        stateList.add("Maryland");
        stateList.add("Massachusetts");
        stateList.add("Michigan");
        stateList.add("Minnesota");
        stateList.add("Mississippi");
        stateList.add("Missouri");
        stateList.add("Montana");
        stateList.add("Nebraska");
        stateList.add("Nevada");
        stateList.add("New Hampshire");
        stateList.add("New Jersey");
        stateList.add("New Mexico");
        stateList.add("New York");
        stateList.add("North Carolina");
        stateList.add("North Dakota");
        stateList.add("Ohio");
        stateList.add("Oklahoma");
        stateList.add("Oregon");
        stateList.add("Pennsylvania");
        stateList.add("Rhode Island");
        stateList.add("South Carolina");
        stateList.add("South Dakota");
        stateList.add("Tennessee");
        stateList.add("Texas");
        stateList.add("Utah");
        stateList.add("Vermont");
        stateList.add("Virginia");
        stateList.add("Washington");
        stateList.add("West Virginia");
        stateList.add("Wisconsin WI");
        stateList.add("Wyoming WY");
        if (getContext() == null) return;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, stateList);
        spinnerLocation.setAdapter(adapter);
        spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                location = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
