package com.beachpartnerllc.beachpartner.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.adpters.AddonAdapter;
import com.beachpartnerllc.beachpartner.connections.ApiService;
import com.beachpartnerllc.beachpartner.connections.PrefManager;
import com.beachpartnerllc.beachpartner.models.SubscriptonPlansModel;
import com.beachpartnerllc.beachpartner.utils.SubClickInterface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddOnFragment extends Fragment implements SubClickInterface, View.OnClickListener {


    private RecyclerView addon_rcv;
    private Button btnCacel,btnProceed;
    private String usertoken;
    private List<SubscriptonPlansModel>addonList = new ArrayList<>();
    private AddonAdapter addonAdapter;
    private AVLoadingIndicatorView progressBar;

    public AddOnFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_addon, container, false);
        initView(view);
        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        usertoken = new PrefManager(getContext()).getToken();
        getSubscribedAddons();
    }

    //Initialize views
    private void initView(View view) {
        btnProceed = view.findViewById(R.id.proceed_btn);
        btnCacel   = view.findViewById(R.id.cancel_btn);
        addon_rcv  = view.findViewById(R.id.rcv_addon);
        progressBar= view.findViewById(R.id.progress_subscription);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        addon_rcv.setLayoutManager(layoutManager);


        //button-proceed
        btnProceed.setOnClickListener(this);
        //button-cancel
        btnCacel.setOnClickListener(this);
    }
    //get Subscribed addons
    private void getSubscribedAddons() {
        progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest request = new JsonArrayRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_SUBSCRIBED_ADDONS, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    Type type = new TypeToken<List<SubscriptonPlansModel>>() {}.getType();
                    addonList = new Gson().fromJson(response.toString(),type);
                    setAddonadapter();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + usertoken);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        if (getActivity() != null) {
            RequestQueue  requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(request);
        }

    }

    private void setAddonadapter() {
        progressBar.setVisibility(View.INVISIBLE);
        if (addonList.size() > 0 && addonList != null) {
            addonAdapter = new AddonAdapter(getContext(),addonList, this,null);
            addon_rcv.setAdapter(addonAdapter);
        }
    }

    @Override
    public void changeViews(SubscriptonPlansModel plansModel) {
        btnProceed.setBackgroundColor(getResources().getColor(R.color.btn_sub));
        btnProceed.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.proceed_btn:
                break;
            case R.id.cancel_btn:
                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
                break;
            default:
                break;
        }
    }
}
