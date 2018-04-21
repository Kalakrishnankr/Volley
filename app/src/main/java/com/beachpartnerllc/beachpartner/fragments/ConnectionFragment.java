package com.beachpartnerllc.beachpartner.fragments;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.beachpartnerllc.beachpartner.adpters.ConnectionAdapter;
import com.beachpartnerllc.beachpartner.connections.ApiService;
import com.beachpartnerllc.beachpartner.connections.PrefManager;
import com.beachpartnerllc.beachpartner.models.ConnectionModel;

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
    private ArrayList<ConnectionModel> connectionList = new ArrayList<>();
    private ArrayList<ConnectionModel> coachList = new ArrayList<>();
    private ArrayList<ConnectionModel> athleteList = new ArrayList<>();
    private ArrayList<ConnectionModel> searchList = new ArrayList<>();
    private ArrayList<ConnectionModel> blockList = new ArrayList<>();
    private ArrayList<ConnectionModel> blockCoachList = new ArrayList<>();
    private ArrayList<ConnectionModel> blockAthleteList = new ArrayList<>();

    private List<ConnectionModel> mCountryModel;
    private String token, user_id;
    private ProgressBar progress;

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


    private void initActivity(View view) {

        rcv_conn = (RecyclerView) view.findViewById(R.id.rcv_connection);
        txtv_athlete = (TextView) view.findViewById(R.id.txtAthlete);
        txtv_coach = (TextView) view.findViewById(R.id.txtCoach);
        txtv_noconnection = (TextView) view.findViewById(R.id.txtv_conview);
        progress    =   (ProgressBar) view.findViewById(R.id.progress_connection);

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
            adapter = new ConnectionAdapter(getContext(), coachList, this);
            rcv_conn.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            rcv_conn.setAdapter(null);
            //rcv_conn.setVisibility(View.GONE);
            //txtv_noconnection.setVisibility(View.VISIBLE);
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
            adapter = new ConnectionAdapter(getContext(), athleteList, this);
            rcv_conn.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            rcv_conn.setAdapter(null);
            //rcv_conn.setVisibility(View.GONE);
            //txtv_noconnection.setVisibility(View.VISIBLE);
        }

    }

    //Get connection list api
    private void getConnections() {
        connectionList.clear();
        athleteList.clear();
        coachList.clear();
        blockAthleteList.clear();
        blockCoachList.clear();
        progress.setVisibility(View.VISIBLE);
        String user_id = new PrefManager(getContext()).getUserId();
        JsonArrayRequest arrayRequest = new JsonArrayRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_ALL_CONNECTIONS + user_id + "?status=Active", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject object = response.getJSONObject(i);
                            JSONObject obj = object.getJSONObject("connectedUser");
                            ConnectionModel model = new ConnectionModel();
                            model.setConnected_status(object.getString("status"));
                            model.setConnected_uId(obj.getString("id"));
                            model.setConnected_login(obj.getString("login"));
                            model.setConnected_firstName(obj.getString("firstName"));
                            model.setConnected_lastName(obj.getString("lastName"));
                            model.setConnected_email(obj.getString("email"));
                            model.setConnected_userType(obj.getString("userType"));
                            model.setConnected_imageUrl(obj.getString("imageUrl"));
                            connectionList.add(model);

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
                            json = trimMessage(json, "detail");
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
        //rcv_conn.setVisibility(View.VISIBLE);
        //txtv_noconnection.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
        if (connectionList != null && connectionList.size() > 0) {
            for (int i = 0; i < connectionList.size(); i++) {
                if (connectionList.get(i).getConnected_userType().equals("Athlete")) {
                    athleteList.add(connectionList.get(i));
                } else {
                    coachList.add(connectionList.get(i));
                }
            }
            progress.setVisibility(View.GONE);
            adapter = new ConnectionAdapter(getContext(), athleteList, this);
            rcv_conn.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        } else {
            txtv_noconnection.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void block(String personid) {

        JsonObjectRequest request = new JsonObjectRequest(ApiService.REQUEST_METHOD_POST, ApiService.BLOCK_PERSON + personid, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status_res = response.getString("status");
                    if (status_res.equals("Blocked")) {
                        Toast.makeText(getContext(), "Person Blocked", Toast.LENGTH_SHORT).show();
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
                            json = trimMessage(json, "detail");
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
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        Log.d("Request", request.toString());
        requestQueue.add(request);
    }

    @Override
    public void unblock(String personid) {
        //Toast.makeText(getActivity(), "Unblocked"+personid, Toast.LENGTH_SHORT).show();
        JsonObjectRequest  jsonObjectRequest = new JsonObjectRequest(ApiService.REQUEST_METHOD_POST, ApiService.UNBLOCK_PERSON + personid, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {
                                String unblockres_status =response.getString("status");
                                if (unblockres_status.equals("RevBlocked")) {
                                    Toast.makeText(getActivity(), "Unblocked Person Successfully", Toast.LENGTH_SHORT).show();
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
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        RequestQueue queue  = Volley.newRequestQueue(getActivity());
        Log.d("unblockRequest",queue.toString());
        queue.add(jsonObjectRequest);

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
               /* AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setMessage("Search keyword is " + query);
                alertDialog.show();*/
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (connectionList != null && connectionList.size() > 0 && athleteList.size() > 0) {
                    if (!newText.isEmpty()) {
                        final List<ConnectionModel> filteredModelList = filter(connectionList, newText);
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


    }

    private List<ConnectionModel> filter(List<ConnectionModel> models, String query) {
        query = query.toLowerCase().trim();
        final List<ConnectionModel> filteredModelList = new ArrayList<>();
        for (ConnectionModel model : models) {
            final String text = model.getConnected_firstName().toLowerCase().trim();
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
                        if (textSearch.equalsIgnoreCase(athleteList.get(i).getConnected_firstName().trim())) {
                            searchList.add(athleteList.get(i));

                        }
                    }
                }
            } else {
                searchList.clear();
                if (coachList.size() > 0) {
                    for (int i = 0; i < coachList.size(); i++) {
                        if (textSearch.equalsIgnoreCase(coachList.get(i).getConnected_firstName())) {
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
                            JSONObject obj = object.getJSONObject("connectedUser");
                            ConnectionModel cModel = new ConnectionModel();
                            cModel.setConnected_status(object.getString("status"));
                            cModel.setConnected_uId(obj.getString("id"));
                            cModel.setConnected_login(obj.getString("login"));
                            cModel.setConnected_firstName(obj.getString("firstName"));
                            cModel.setConnected_lastName(obj.getString("lastName"));
                            cModel.setConnected_email(obj.getString("email"));
                            cModel.setConnected_userType(obj.getString("userType"));
                            cModel.setConnected_imageUrl(obj.getString("imageUrl"));
                            blockList.add(cModel);

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
                if (blockList.get(j).getConnected_userType().equals("Athlete")) {
                    blockAthleteList.add(blockList.get(j));
                }else {
                    blockCoachList.add(blockList.get(j));
                }
            }
            if (blockAthleteList.size() > 0) {
                athleteList.addAll(blockAthleteList);
                adapter = new ConnectionAdapter(getContext(), athleteList, this);
                rcv_conn.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

        }

    }


}
