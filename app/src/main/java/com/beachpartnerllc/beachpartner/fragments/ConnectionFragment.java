package com.beachpartnerllc.beachpartner.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.beachpartnerllc.beachpartner.ConnectionInterface;
import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.activity.TabActivity;
import com.beachpartnerllc.beachpartner.adpters.ConnectionAdapter;
import com.beachpartnerllc.beachpartner.connections.ApiService;
import com.beachpartnerllc.beachpartner.connections.PrefManager;
import com.beachpartnerllc.beachpartner.models.BpFinderModel;
import com.beachpartnerllc.beachpartner.models.Coach.ConnectionResultModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ConnectionFragment extends Fragment implements View.OnClickListener,ConnectionInterface {

    private RecyclerView rcv_conn;
    private ConnectionAdapter adapter;
    private TextView txtv_coach, txtv_athlete, txtv_noconnection;
    private static boolean isCoachTab = false;
    private static boolean isAthleteTab = false;
    private ArrayList<ConnectionResultModel> connectionList = new ArrayList<>();
    private ArrayList<ConnectionResultModel> coachList = new ArrayList<>();
    private ArrayList<ConnectionResultModel> athleteList = new ArrayList<>();
    private ArrayList<ConnectionResultModel> searchList = new ArrayList<>();
    private ArrayList<ConnectionResultModel> blockList = new ArrayList<>();
    private ArrayList<ConnectionResultModel> blockCoachList = new ArrayList<>();
    private ArrayList<ConnectionResultModel> blockAthleteList = new ArrayList<>();
    private ArrayList<BpFinderModel> dataList = new ArrayList<>();

    private List<BpFinderModel> mCountryModel;
    private String token, user_id;
    private ProgressBar progress;
    TabActivity tabActivity;
    ProgressDialog dialog;
    public ConnectionFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_connection, container, false);
        token = new PrefManager(getContext()).getToken();
        user_id = new PrefManager(getContext()).getUserId();
        initActivity(view);

        return view;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() instanceof TabActivity){
            tabActivity = (TabActivity)getActivity();
            tabActivity.setActionBarTitle("Connections");
        }
    }

    private void initActivity(View view) {

        rcv_conn = (RecyclerView) view.findViewById(R.id.rcv_connection);
        txtv_athlete = (TextView) view.findViewById(R.id.txtAthlete);
        txtv_coach = (TextView) view.findViewById(R.id.txtCoach);
        txtv_noconnection = (TextView) view.findViewById(R.id.txtv_conview);
        progress    =   (ProgressBar) view.findViewById(R.id.progress_connection);
        dialog      = new ProgressDialog(getContext(),ProgressDialog.THEME_HOLO_DARK);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // set title and message
        dialog.setTitle("Please wait");
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        // and show it


        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        rcv_conn.setLayoutManager(layoutManager);
        //rcv_conn.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        rcv_conn.setItemAnimator(new DefaultItemAnimator());
        txtv_athlete.setOnClickListener(this);
        txtv_coach.setOnClickListener(this);
        activeAthletTab();
        getConnections();


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.txtAthlete:
                activeAthletTab();
                break;

            case R.id.txtCoach:
                activeCoachTab();
                break;

            default:
                break;


        }

    }

     /*Method for Active Coach Tab*/

    private void activeCoachTab() {
        isAthleteTab = false;
        isCoachTab = true;
        rcv_conn.setVisibility(View.VISIBLE);
        txtv_noconnection.setVisibility(View.GONE);
        txtv_coach.setTextColor(getResources().getColor(R.color.blueDark));
        txtv_coach.setBackgroundColor(getResources().getColor(R.color.white));
        txtv_athlete.setBackgroundColor(0);
        txtv_athlete.setTextColor(getResources().getColor(R.color.white));
        coachList.removeAll(blockCoachList);
        if ((coachList != null && coachList.size() > 0) || blockCoachList.size()>0 ) {
            if (blockCoachList.size() > 0) {
                coachList.addAll(blockCoachList);
            }
            if (coachList.size() > 0) {
                adapter = new ConnectionAdapter(getContext(), coachList, this);
                rcv_conn.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }else {
                txtv_noconnection.setVisibility(View.VISIBLE);
            }
        } else {
            rcv_conn.setAdapter(null);
            //rcv_conn.setVisibility(View.GONE);
            txtv_noconnection.setVisibility(View.VISIBLE);
        }

    }

    /*Method Active Athlete TAb*/

    private void activeAthletTab() {
        isAthleteTab = true;
        isCoachTab = false;
        connectionList.clear();
        rcv_conn.setVisibility(View.VISIBLE);
        txtv_noconnection.setVisibility(View.GONE);
        txtv_athlete.setTextColor(getResources().getColor(R.color.blueDark));
        txtv_athlete.setBackgroundColor(getResources().getColor(R.color.white));
        txtv_coach.setBackgroundColor(0);
        txtv_coach.setTextColor(getResources().getColor(R.color.white));
        athleteList.removeAll(blockAthleteList);
        if (athleteList != null && athleteList.size() > 0 || blockAthleteList.size()>0 )  {
            if (blockAthleteList.size() > 0) {
                athleteList.addAll(blockAthleteList);
            }
            if (athleteList.size() > 0) {
                adapter = new ConnectionAdapter(getContext(), athleteList, this);
                rcv_conn.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }else {
                txtv_noconnection.setVisibility(View.VISIBLE);
            }

        } else {
            rcv_conn.setAdapter(null);
            //rcv_conn.setVisibility(View.GONE);
            txtv_noconnection.setVisibility(View.VISIBLE);
        }

    }

    //Get connection list api
    private void getConnections() {
        connectionList.clear();
        athleteList.clear();
        coachList.clear();
        blockAthleteList.clear();
        blockCoachList.clear();
        dataList.clear();
        //progress.setVisibility(View.VISIBLE);
        dialog.show();
        String user_id = new PrefManager(getContext()).getUserId();
        JsonArrayRequest arrayRequest = new JsonArrayRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_ALL_CONNECTIONS + user_id + "?status=Active", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject object = response.getJSONObject(i);
                            //model.setConnected_status(object.getString("status"));
                            ConnectionResultModel connectionResultModel = new Gson().fromJson(object.toString(),ConnectionResultModel.class);
                            if (connectionResultModel != null) {
                                connectionList.add(connectionResultModel);
                                dataList.add(connectionResultModel.getBpFinderModel());
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    getBlockedMembers();
                    setConnections();
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
                            json = trimMessage(json, "title");
                            if (json != null) {
                                Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;
                        case 500:
                            json = new String(response.data);
                            json = trimMessage(json, "title");
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
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;

            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        Log.d("Request", arrayRequest.toString());
        requestQueue.add(arrayRequest);


    }

    private void setConnections() {
        //progress.setVisibility(View.GONE);
        dialog.cancel();
        if (connectionList != null && connectionList.size() > 0) {
            for (int i = 0; i < connectionList.size(); i++) {
                if (connectionList.get(i).getBpFinderModel().getBpf_userType().equals("Athlete")) {
                    athleteList.add(connectionList.get(i));
                } else {
                    coachList.add(connectionList.get(i));
                }
            }
            if (isAthleteTab) {
                if (athleteList.size() > 0 && athleteList != null) {
                    adapter = new ConnectionAdapter(getContext(), athleteList, this);
                    rcv_conn.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else{
                    txtv_noconnection.setVisibility(View.VISIBLE);
                }
            }else {
                if (coachList.size() > 0 && coachList != null) {
                    adapter = new ConnectionAdapter(getContext(), coachList, this);
                    rcv_conn.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else{
                txtv_noconnection.setVisibility(View.VISIBLE);
            }
            }


        } else {
            txtv_noconnection.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void block(final String personid, final String person_name) {


        final AlertDialog.Builder alertadd = new AlertDialog.Builder(getActivity());

        alertadd.setTitle("Are you sure you want to block "+person_name+" from your connection?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       blockPerson(personid,person_name);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.cancel();
                    }
                });

        AlertDialog alert=alertadd.create();
        alert.show();

    }


    //Api for block person

    private void blockPerson(String personid, final String person_name){

        JsonObjectRequest request = new JsonObjectRequest(ApiService.REQUEST_METHOD_POST, ApiService.BLOCK_PERSON + personid, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status_res = response.getString("status");
                    if (status_res.equals("Blocked")) {
                        Toast.makeText(getContext(), " You have blocked "+person_name , Toast.LENGTH_SHORT).show();
                        //adapter.notifyDataSetChanged();
                        getConnections();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
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
                            json = trimMessage(json, "title");
                            if (json != null) {
                                Toast.makeText(getContext(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;
                        case 404:
                            json = new String(response.data);
                            json = trimMessage(json, "title");
                            if (json != null) {
                                Toast.makeText(getContext(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;
                        case 500:
                            json = new String(response.data);
                            json = trimMessage(json, "title");
                            if (json != null) {
                                Toast.makeText(getContext(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;

                        default:
                            break;
                    }
                }

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        Log.d("Request", request.toString());
        requestQueue.add(request);
    }
    @Override
    public void unblock(final String personid, final String person_name) {
        //Toast.makeText(getActivity(), "Unblocked"+personid, Toast.LENGTH_SHORT).show();
        final AlertDialog.Builder alertadd = new AlertDialog.Builder(getActivity());

        alertadd.setTitle("Are you sure you want to unblock "+person_name+" from your connection?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        unBlockPerson(personid,person_name);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.cancel();
                    }
                });

        AlertDialog alert=alertadd.create();
        alert.show();
    }

    //Api for unblock person
    private void unBlockPerson(String persId, final String persName){
        JsonObjectRequest  jsonObjectRequest = new JsonObjectRequest(ApiService.REQUEST_METHOD_POST, ApiService.UNBLOCK_PERSON + persId, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {
                                String unblockres_status =response.getString("status");
                                if (unblockres_status.equals("Active")) {
                                    Toast.makeText(getActivity(), "You have unblocked "+persName, Toast.LENGTH_SHORT).show();
                                    getConnections();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
                        case 404:
                            json = new String(response.data);
                            json = trimMessage(json, "title");
                            if (json != null) {
                                Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;
                        case 500:
                            json = new String(response.data);
                            json = trimMessage(json, "title");
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
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                headers.put("Content-Type", "application/json;charset=utf-8");
                return headers;
            }
        };

        RequestQueue queue  = Volley.newRequestQueue(getActivity());
        Log.d("unblockRequest",queue.toString());
        queue.add(jsonObjectRequest);
    }

    @Override
    public void transition(Bundle bundle) {

        if (getActivity() != null) {
            //tabActivity.navigation.setSelectedItemId(R.id.navigation_more);
            //tabActivity.disableFloatButtons();
            tabActivity.navigation.setSelectedItemId(R.id.navigation_more);
            tabActivity.disableFloatButtons();
            ChatFragmentPage chatFragmentPage = new ChatFragmentPage();
            chatFragmentPage.setArguments(bundle);
            FragmentManager hiFiveFManager = getActivity().getSupportFragmentManager();
            FragmentTransaction hiFiveTrans = hiFiveFManager.beginTransaction();
            hiFiveTrans.replace(R.id.container, chatFragmentPage);
            hiFiveTrans.addToBackStack(null);
            hiFiveTrans.commit();

        }

    }

    @Override
    public void connectionToNote(Bundle bundle) {
        NoteFragment noteFragment =new NoteFragment();
        noteFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, noteFragment).addToBackStack(null).commit();
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
            int column = position % spanCount; // item column

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

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchPerson(query);
//                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
//                alertDialog.setMessage("Search keyword is " + query);
//                alertDialog.show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (dataList != null && dataList.size() > 0 ) {
                    if (!newText.isEmpty()) {
                        final List<BpFinderModel> filteredModelList = filter(dataList, newText);
                        adapter.setFilter(filteredModelList);
                        adapter.notifyDataSetChanged();

                    } else {
                        adapter = new ConnectionAdapter(getContext(), athleteList, ConnectionFragment.this);
                        rcv_conn.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
                return false;
            }
        });

        searchView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (keyboardShown(searchView.getRootView())) {
                    //Log.d("keyboard", "keyboard UP");
                    tabActivity.navigation.setVisibility(View.GONE);
                } else {
                    //Log.d("keyboard", "keyboard Down");
                    tabActivity.navigation.setVisibility(View.VISIBLE);

                }
            }
        });


    }

    private boolean keyboardShown(View rootView) {

        final int softKeyboardHeight = 100;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int heightDiff = rootView.getBottom() - r.bottom;
        return heightDiff > softKeyboardHeight * dm.density;
    }

    private List<BpFinderModel> filter(List<BpFinderModel> models, String query) {
        query = query.toLowerCase().trim();
        final List<BpFinderModel> filteredModelList = new ArrayList<>();
        for (BpFinderModel model : models) {
            final String text = model.getBpf_firstName().toLowerCase().trim();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_search:
                //searchPerson();
                break;
            default:
                break;
        }
        return false;
    }

    private void searchPerson(String query) {

        String textSearch = query;

        if (!textSearch.equals("null")) {
            if (isAthleteTab) {
                searchList.clear();
                if (athleteList.size() > 0) {
                    for (int i = 0; i < athleteList.size(); i++) {
                        if (textSearch.equalsIgnoreCase(athleteList.get(i).getBpFinderModel().getBpf_firstName().trim())) {
                            searchList.add(athleteList.get(i));

                        }
                    }
                }
            } else {
                searchList.clear();
                if (coachList.size() > 0) {
                    for (int i = 0; i < coachList.size(); i++) {
                        if (textSearch.equalsIgnoreCase(coachList.get(i).getBpFinderModel().getBpf_firstName())) {
                            searchList.add(coachList.get(i));

                        }
                    }

                }
            }
            if (searchList.size() > 0) {
                adapter = new ConnectionAdapter(getContext(), searchList, this);
                rcv_conn.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getActivity(), "User not found", Toast.LENGTH_SHORT).show();
                if (isAthleteTab) {
                    adapter = new ConnectionAdapter(getContext(), athleteList, this);
                    rcv_conn.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    adapter = new ConnectionAdapter(getContext(), coachList, this);
                    rcv_conn.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

            }

        }


    }

    //Get Blocked persons
    private void getBlockedMembers() {
        blockList.clear();
        JsonArrayRequest arrayRequest = new JsonArrayRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_ALL_CONNECTIONS + user_id + "?status=Blocked", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("response",response.toString());
                if (response != null && response.length()!=0) {
                    for(int i =0 ;i<response.length();i++){
                        try {
                            JSONObject object = response.getJSONObject(i);
                            ConnectionResultModel connectionResultModel = new Gson().fromJson(object.toString(),ConnectionResultModel.class);
                            if (connectionResultModel != null) {
                                blockList.add(connectionResultModel);
                            }

                            /*JSONObject obj = object.getJSONObject("connectedUser");
                            Type listType = new TypeToken<BpFinderModel>() {
                            }.getType();
                            BpFinderModel mModel = new Gson().fromJson(obj.toString(), listType);
                            blockList.add(mModel);*/

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    setUpPage();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        if (getActivity() != null) {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            Log.d("Request",arrayRequest.toString());
            requestQueue.add(arrayRequest);

        }


    }

    private void setUpPage() {
        blockCoachList.clear();
        blockAthleteList.clear();
        progress.setVisibility(View.GONE);
        if(blockList!=null && blockList.size()>0){
            for (int j = 0; j<blockList.size();j++){
                if (blockList.get(j).getBpFinderModel().getBpf_userType().equals("Athlete")) {
                    blockAthleteList.add(blockList.get(j));
                }else {
                    blockCoachList.add(blockList.get(j));
                }
            }
            if (isAthleteTab) {
                if (blockAthleteList.size() > 0) {
                    athleteList.addAll(blockAthleteList);
                    adapter = new ConnectionAdapter(getContext(), athleteList, this);
                    rcv_conn.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }else {
                if (blockCoachList.size() > 0) {
                    coachList.addAll(blockCoachList);
                    adapter = new ConnectionAdapter(getContext(), coachList, this);
                    rcv_conn.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }


        }

    }


}