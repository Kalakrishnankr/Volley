package com.beachpartnerllc.beachpartner.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.adpters.HiFiveAdapter;
import com.beachpartnerllc.beachpartner.connections.ApiService;
import com.beachpartnerllc.beachpartner.connections.PrefManager;
import com.beachpartnerllc.beachpartner.models.BpFinderModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link HiFiveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HiFiveFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String user_id,user_token,userType;
    private PrefManager prefManager;
    private TextView noHiFiveImage;


    private ArrayList<BpFinderModel> hiFiveList = new ArrayList<BpFinderModel>();
    ListView listView;

    public HiFiveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HiFiveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HiFiveFragment newInstance(String param1, String param2) {
        HiFiveFragment fragment = new HiFiveFragment();
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
        prefManager =  new PrefManager(getContext());
        user_id     =  prefManager.getUserId();
        user_token  =  prefManager.getToken();
        userType    = prefManager.getUserType();
        View view= inflater.inflate(R.layout.fragment_hi_five, container, false);
        initList(view);
        listView =  view.findViewById(R.id.listViewHiFives);


        return view;
    }
    private void initList(View view) {
        noHiFiveImage = view.findViewById(R.id.no_hiFives_tv);
        getHiFiList();
    }


    private void getHiFiList() {
        hiFiveList.clear();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_ALL_CONNECTIONS  + user_id + "?status=" + "Hifi" , null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if(response!=null){
                    for(int i=0;i<response.length();i++){
                        try {
                            JSONObject object = response.getJSONObject(i);
                            JSONObject obj    = object.getJSONObject("connectedUser");
                            BpFinderModel model  = new BpFinderModel();
                            model.setBpf_id(obj.getString("id"));
                            model.setBpf_firstName(obj.getString("firstName"));
                            model.setBpf_lastName(obj.getString("lastName"));
                            model.setBpf_imageUrl(obj.getString("imageUrl"));
                            model.setBpf_age(obj.getString("age"));
                            model.setBpf_videoUrl(obj.getString("videoUrl"));
                            model.setBpf_dob(obj.getString("dob"));
                            model.setBpf_gender(obj.getString("gender"));
                            model.setBpf_fcmToken(obj.getString("fcmToken"));
                            model.setBpf_location(obj.getString("location"));
                            model.setBpf_deviceId(obj.getString("deviceId"));
                            model.setBpf_userType(obj.getString("userType"));
                            model.setBpf_email(obj.getString("email"));
                            hiFiveList.add(model);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    setHifiveList();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){ @Override
        public Map<String, String> getHeaders()  {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Authorization", "Bearer " + user_token);
            //headers.put("Content-Type", "application/json; charset=utf-8");
            return headers;

        }};
        if (getActivity() != null) {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            Log.d("Request", jsonArrayRequest.toString());
            requestQueue.add(jsonArrayRequest);
        }



    }

    private void setHifiveList() {
        if(hiFiveList!=null && hiFiveList.size()>0){
            HiFiveAdapter hiFiveAdapter = new HiFiveAdapter(hiFiveList,getContext());
            listView.setAdapter(hiFiveAdapter);
            hiFiveAdapter.notifyDataSetChanged();



        }else {
            noHiFiveImage.setVisibility(View.VISIBLE);
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


}
