package com.goldemo.beachpartner.fragments;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.adpters.ConnectionAdapter;
import com.goldemo.beachpartner.connections.ApiService;
import com.goldemo.beachpartner.connections.PrefManager;
import com.goldemo.beachpartner.models.ConnectionModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ConnectionFragment extends Fragment implements View.OnClickListener {

    private RecyclerView rcv_conn;
    private ConnectionAdapter adapter;
    private TextView txtv_coach,txtv_athlete,txtv_noconnection;

    private ArrayList<ConnectionModel>connectionList = new ArrayList<>();
    private ArrayList<ConnectionModel>coachList = new ArrayList<>();
    private ArrayList<ConnectionModel>athleteList = new ArrayList<>();

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

        getConnections();
        initActivity(view);

        return view;


    }



    private void initActivity(View view) {




        rcv_conn        =   (RecyclerView)view.findViewById(R.id.rcv_connection);
        txtv_athlete    =   (TextView)view.findViewById(R.id.txtAthlete);
        txtv_coach      =   (TextView)view.findViewById(R.id.txtCoach);
        txtv_noconnection=  (TextView)view.findViewById(R.id.txtv_conview);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        rcv_conn.setLayoutManager(layoutManager);
        //rcv_conn.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        rcv_conn.setItemAnimator(new DefaultItemAnimator());

        activeAthletTab();

        txtv_athlete.setOnClickListener(this);
        txtv_coach.setOnClickListener(this);

    }






    @Override
    public void onClick(View view) {

        switch (view.getId()){

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
        txtv_coach.setTextColor(getResources().getColor(R.color.blueDark));
        txtv_coach.setBackgroundColor(getResources().getColor(R.color.white));
        txtv_athlete.setBackgroundColor(0);
        txtv_athlete.setTextColor(getResources().getColor(R.color.white));
        if(coachList!=null && coachList.size()>0){
            adapter    =   new ConnectionAdapter(getContext(),coachList);
            rcv_conn.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

    }

    /*Method Active Athlete TAb*/

    private void activeAthletTab() {
        txtv_athlete.setTextColor(getResources().getColor(R.color.blueDark));
        txtv_athlete.setBackgroundColor(getResources().getColor(R.color.white));
        txtv_coach.setBackgroundColor(0);
        txtv_coach.setTextColor(getResources().getColor(R.color.white));
       if(athleteList!=null && athleteList.size()>0){
           adapter    =   new ConnectionAdapter(getContext(),athleteList);
           rcv_conn.setAdapter(adapter);
           adapter.notifyDataSetChanged();
       }
    }

    //Get connection list api
    private void getConnections() {
        connectionList.clear();
        String user_id  = new PrefManager(getContext()).getUserId();
        final String token    = new PrefManager(getContext()).getToken();

        JsonArrayRequest arrayRequest = new JsonArrayRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_ALL_CONNECTIONS + user_id, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if(response!=null){
                    for(int i=0;i<response.length();i++){
                        try {
                            JSONObject object = response.getJSONObject(i);
                            JSONObject obj    = object.getJSONObject("connectedUser");
                            ConnectionModel model  = new  ConnectionModel();
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
                    setConnections();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){ @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
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
        if(connectionList!=null && connectionList.size()>0){
            for(int i=0;i<connectionList.size();i++){
                if(connectionList.get(i).getConnected_userType().equals("Athlete")){
                    athleteList.add(connectionList.get(i));
                }else {
                    coachList.add(connectionList.get(i));
                }
            }

            adapter    =   new ConnectionAdapter(getContext(),athleteList);
            rcv_conn.setAdapter(adapter);
            adapter.notifyDataSetChanged();



        }else {
            txtv_noconnection.setVisibility(View.VISIBLE);
        }
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
        inflater.inflate(R.menu.menu_search,menu);
        super.onCreateOptionsMenu(menu, inflater);


        /*super.onCreateOptionsMenu(menu, inflater); menu.clear();
        inflater.inflate(R.menu.sample_menu, menu);*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_search:

                break;
            default:
                break;
        }
        return false;
    }
}
