package com.beachpartnerllc.beachpartner.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.beachpartnerllc.beachpartner.BuildConfig;
import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.connections.ApiService;
import com.beachpartnerllc.beachpartner.connections.PrefManager;
import com.beachpartnerllc.beachpartner.models.SingleSubscriptionModel;
import com.beachpartnerllc.beachpartner.models.SubscriptionItemsModels;
import com.beachpartnerllc.beachpartner.update.Resource;
import com.beachpartnerllc.beachpartner.update.Status;
import com.beachpartnerllc.beachpartner.update.Update;
import com.beachpartnerllc.beachpartner.update.UpdateViewModel;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {
	private static final int SPLASH_DELAY_TIME_DELAY = 1000;
    private List<SubscriptionItemsModels> userSubList = new ArrayList<>();
    private List<SubscriptionItemsModels>userAddonsList = new ArrayList<>();

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);

		if (isNetworkAvailable()) {
			UpdateViewModel vm = ViewModelProviders.of(this).get(UpdateViewModel.class);
			vm.checkForUpdated().observe(this, new Observer<Resource<Update>>() {
				@Override
				public void onChanged(@Nullable Resource<Update> update) {
					if (update.getStatus() == Status.SUCCESS) {
						Update response = update.getData();

						if (response.getMandatoryUpdate()) {
							mandatoryUpdateAvailable();
						} else {
							navigateToHome();
						}
					}else if (update.getStatus() == Status.ERROR){
						navigateToHome();
					}
				}
			});

		} else {
			Toast.makeText(this, "Please Check your connection", Toast.LENGTH_SHORT).show();
		}
	}

	private void navigateToHome() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (!isFinishing()) {
					Intent intent = new Intent(SplashActivity.this, TermsConditionsActivity.class);
					startActivity(intent);
					finish();
				}
			}
		}, SPLASH_DELAY_TIME_DELAY);
        getuserSubscriptions();//Get single user subscriptions
    }

    //Method for getting logged user subscriptions
    private void getuserSubscriptions() {
        JsonObjectRequest request = new JsonObjectRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_USER_ACTIVE_PLANS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    SingleSubscriptionModel subscriptionModel = new Gson().fromJson(response.toString(),SingleSubscriptionModel.class);
                    userSubList = subscriptionModel.getSubItemModel();
                    userAddonsList=subscriptionModel.getAddonsItemModel();
                    saveSubscription();

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
                headers.put("Authorization", "Bearer " + new PrefManager(getApplicationContext()).getToken());
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        Log.d("SubscriptionRequest", queue.toString());
        queue.add(request);


    }

	private void mandatoryUpdateAvailable() {
		new AlertDialog.Builder(SplashActivity.this)
		    .setTitle(R.string.update_available)
		    .setMessage(getString(R.string.msg_update, BuildConfig.VERSION_NAME))
		    .setCancelable(false)
		    .setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
				    try {
					    Intent store = new Intent(Intent.ACTION_VIEW)
						.setData(Uri.parse("market://details?id=" + getPackageName()));
					    startActivity(store);
				    } catch (ActivityNotFoundException e) {
					    Toast.makeText(SplashActivity.this, R.string.install_play_store, Toast
						.LENGTH_SHORT).show();
					    finish();
				    }
			    }
		    })
		    .setNegativeButton(R.string.exit, new DialogInterface
			.OnClickListener() {
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
				    finish();
			    }
		    })
		    .create()
		    .show();
	}

    private void saveSubscription() {
        if (userSubList != null && userSubList.size() > 0) {
            for (int i = 0; i < userSubList.size() ; i++) {
                new PrefManager(getApplicationContext()).saveSubscription(userSubList.get(i).getPlanName(), userSubList.get(i).getRemainingDays());
            }

        }
    }

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}
