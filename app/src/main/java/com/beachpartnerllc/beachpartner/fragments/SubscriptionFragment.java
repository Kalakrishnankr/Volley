package com.beachpartnerllc.beachpartner.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.activity.TabActivity;
import com.beachpartnerllc.beachpartner.adpters.BenefitListItemAdapter;
import com.beachpartnerllc.beachpartner.adpters.SubscriptionAdapter;
import com.beachpartnerllc.beachpartner.connections.ApiService;
import com.beachpartnerllc.beachpartner.connections.PrefManager;
import com.beachpartnerllc.beachpartner.models.BenefitModel;
import com.beachpartnerllc.beachpartner.models.SingleSubscriptionModel;
import com.beachpartnerllc.beachpartner.models.SubscriptionItemsModels;
import com.beachpartnerllc.beachpartner.models.SubscriptonPlansModel;
import com.beachpartnerllc.beachpartner.utils.SubClickInterface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SubscriptionFragment extends Fragment implements SubClickInterface {


    private static final String TAG = "SubscriptionFragemnt";
    private List<SubscriptonPlansModel>userPlanList = new ArrayList<>();
    private List<SubscriptionItemsModels>userSubList = new ArrayList<>();
    private List<SubscriptionItemsModels>userAddonsList = new ArrayList<>();
    private List<SubscriptonPlansModel>plansModelList = new ArrayList<>();
    private String userToken,subscription;
    private Button btnProceed,btnCancel;
    private AVLoadingIndicatorView prgressSub;
    private SubscriptionAdapter subscriptionAdapter;

    private View layoutAlert;
    private LinearLayout topHeaderLayout;
    private TextView headerTitle,tv_price,tv_regPrice;
    private Button btnBuy;
    private RecyclerView rcview_sub_item;
    private RelativeLayout premius_header;
    private List<BenefitModel>benefitModelList = new ArrayList<>();
    private BenefitListItemAdapter benefitItemAdapter;
    private RecyclerView rc_view;
    private TabActivity tabActivity;
    SubscriptonPlansModel splanModels;
    public SubscriptionFragment() {
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
        View view= inflater.inflate(R.layout.fragment_subscription, container, false);
        userToken   = new PrefManager(getContext()).getToken();
        subscription= new PrefManager(getContext()).getSubscriptionType();
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        btnProceed    = view.findViewById(R.id.btn_proceed);
        btnCancel     = view.findViewById(R.id.btn_sub_cancel);
        prgressSub    = view.findViewById(R.id.progress_subscription);
        rc_view       = view.findViewById(R.id.rcv_subscribe);
        prgressSub.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rc_view.setLayoutManager(layoutManager);
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(splanModels!=null)
                subscriptionsModels(splanModels);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() instanceof TabActivity){
            tabActivity = (TabActivity)getActivity();
            tabActivity.getSupportActionBar().setTitle((R.string.app_name));
        }
        getuserSubscriptions();//Get single user subscriptions

    }

    //Method for getting logged user subscriptions
    private void getuserSubscriptions() {
        JsonObjectRequest request = new JsonObjectRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_USER_ACTIVE_PLANS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    Log.d(TAG, "onUserPlansResponse: ");
                    SingleSubscriptionModel subscriptionModel = new Gson().fromJson(response.toString(),SingleSubscriptionModel.class);
                    userSubList = subscriptionModel.getSubItemModel();
                    userAddonsList=subscriptionModel.getAddonsItemModel();
                    getSubscriptionPlans();//Information for all subscriptions


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
                headers.put("Authorization", "Bearer " + userToken);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        if (getActivity() != null) {
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            Log.d("SubscriptionRequest", queue.toString());
            queue.add(request);
        }

    }

    //Get all subscriptions
    private void getSubscriptionPlans() {
        plansModelList.clear();
        JsonArrayRequest arrayRequest = new JsonArrayRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_SUBSCRIPTION_PLANS, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    Log.d(TAG, "onResponse: "+response.toString());
                    Type type = new TypeToken<List<SubscriptonPlansModel>>() {}.getType();
                    plansModelList = new Gson().fromJson(response.toString(),type);
                    setAdapter();

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
                headers.put("Authorization", "Bearer " + userToken);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;

            }

        };
        if (getActivity() != null) {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            Log.d("SubscriptionRequest", arrayRequest.toString());
            requestQueue.add(arrayRequest);
        }
    }

    private void setAdapter() {
        if (plansModelList.size() > 0 && plansModelList != null) {
            prgressSub.setVisibility(View.INVISIBLE);
            subscriptionAdapter = new SubscriptionAdapter(getContext(),plansModelList, this,subscription);
            subscriptionAdapter.notifyDataSetChanged();
            rc_view.setAdapter(subscriptionAdapter);

        }
    }

//    private void showSubcriptionAlert() {
//
//        LayoutInflater inflater = getLayoutInflater();
//        //View alertLayout = inflater.inflate(R.layout.popup_no_of_likes_layout, null);//Alert dialogue previous one
//        View alertLayout = inflater.inflate(R.layout.alert_subscription_layout, null);
//
//        btnProceed    = alertLayout.findViewById(R.id.btn_proceed);
//        btnCancel     = alertLayout.findViewById(R.id.btn_sub_cancel);
//        prgressSub    = alertLayout.findViewById(R.id.progress_subscription);
//        RecyclerView rc_view  = alertLayout.findViewById(R.id.rcv_subscribe);
//        prgressSub.setVisibility(View.VISIBLE);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//        rc_view.setLayoutManager(layoutManager);
//        btnProceed.setClickable(false);
//        if (plansModelList.size() > 0 && plansModelList != null) {
//            prgressSub.setVisibility(View.INVISIBLE);
//            subscriptionAdapter = new SubscriptionAdapter(getContext(),plansModelList, this,null);
//            rc_view.setAdapter(subscriptionAdapter);
//        }
//        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getContext());
//        // Initialize a new foreground color span instance
//        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.blueDark));
//        alert.setView(alertLayout);
//        alert.setCancelable(true);
//        final android.app.AlertDialog dialog = alert.create();
//        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface arg0) {
//                //dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setBackgroundColor(getResources().getColor(R.color.blueDark));
//                //dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setGravity(Gravity.CENTER);
//            }
//        });
//        dialog.show();
//        btnProceed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//    }

    @Override
    public void changeViews(SubscriptonPlansModel plansModel) {
        btnProceed.setBackgroundColor(getResources().getColor(R.color.btn_sub));
        btnProceed.setTextColor(getResources().getColor(R.color.white));
        splanModels = plansModel;
    }

    //Method for subscrptionsModels
    private void subscriptionsModels(SubscriptonPlansModel subscriptonPlansModel) {
        LayoutInflater inflater = getLayoutInflater();
        layoutAlert = inflater.inflate(R.layout.subscription_free_layout, null);
        LinearLayoutManager linearManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        topHeaderLayout = layoutAlert.findViewById(R.id.top_sub_header);
        headerTitle     = layoutAlert.findViewById(R.id.head_sub_title);
        tv_price        = layoutAlert.findViewById(R.id.tv_price);
        tv_regPrice     = layoutAlert.findViewById(R.id.tv_subPrice);
        btnBuy          = layoutAlert.findViewById(R.id.sub_buy);
        rcview_sub_item = layoutAlert.findViewById(R.id.rcview_sub);
        premius_header  = layoutAlert.findViewById(R.id.recruit_header);

        rcview_sub_item.setLayoutManager(linearManager);
        //benefitModelList.clear();
        benefitModelList =subscriptonPlansModel.getBenefitList();
        if (subscriptonPlansModel.getPlanName().equalsIgnoreCase("FREE")) {
            //layoutAlert = inflater.inflate(R.layout.subscription_free_layout, null);
            tv_price.setText("$"+subscriptonPlansModel.getMonthlyCharge());
            btnBuy.setBackgroundColor(getResources().getColor(R.color.butn_free));
            if (benefitModelList != null) {
                tv_regPrice.setText("$"+subscriptonPlansModel.getRegFee());
                benefitItemAdapter = new BenefitListItemAdapter(getContext(),benefitModelList);
                rcview_sub_item.setAdapter(benefitItemAdapter);
            }
        }else if (subscriptonPlansModel.getPlanName().equalsIgnoreCase("LITE")) {
            tv_price.setText("$"+subscriptonPlansModel.getMonthlyCharge());
            topHeaderLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.header_lite));
            headerTitle.setText(R.string.lite_sub);
            btnBuy.setBackgroundColor(getResources().getColor(R.color.butn_lite));
            if (benefitModelList != null) {
                tv_regPrice.setText("$"+subscriptonPlansModel.getRegFee()+" one-time");
                benefitItemAdapter = new BenefitListItemAdapter(getContext(),benefitModelList);
                rcview_sub_item.setAdapter(benefitItemAdapter);
            }
        } else if (subscriptonPlansModel.getPlanName().equalsIgnoreCase("STANDARD")) {
            tv_price.setText("$"+subscriptonPlansModel.getMonthlyCharge());
            topHeaderLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.header_standard));
            headerTitle.setText(R.string.standard_sub);
            btnBuy.setBackgroundColor(getResources().getColor(R.color.butn_std));
            if (benefitModelList != null) {
                tv_regPrice.setText("$"+subscriptonPlansModel.getRegFee()+" one-time");
                benefitItemAdapter = new BenefitListItemAdapter(getContext(),benefitModelList);
                rcview_sub_item.setAdapter(benefitItemAdapter);
            }
        }else {
            tv_price.setText("$"+subscriptonPlansModel.getMonthlyCharge());
            topHeaderLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.header_rec));
            headerTitle.setText(R.string.recruiting_sub);
            btnBuy.setBackgroundColor(getResources().getColor(R.color.butn_recruit));
            premius_header.setVisibility(View.VISIBLE);
            if (benefitModelList != null) {
                tv_regPrice.setText("$"+subscriptonPlansModel.getRegFee()+" one-time");
                benefitItemAdapter = new BenefitListItemAdapter(getContext(),benefitModelList);
                rcview_sub_item.setAdapter(benefitItemAdapter);
            }
        }

        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getContext());
        // Initialize a new foreground color span instance
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.blueDark));
        alert.setView(layoutAlert);
        alert.setCancelable(true);
        final android.app.AlertDialog dialog = alert.create();

        dialog.show();
    }
}
