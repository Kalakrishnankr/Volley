package com.beachpartnerllc.beachpartner.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.beachpartnerllc.beachpartner.CircularImageView;
import com.beachpartnerllc.beachpartner.R;
//import com.beachpartnerllc.beachpartner.adpters.ConnectionAdapter;
import com.beachpartnerllc.beachpartner.adpters.MyConnectionAdapter;
import com.beachpartnerllc.beachpartner.adpters.MyTeamAdapter;
import com.beachpartnerllc.beachpartner.connections.ApiService;
import com.beachpartnerllc.beachpartner.connections.PrefManager;
import com.beachpartnerllc.beachpartner.models.ConnectionModel;
import com.beachpartnerllc.beachpartner.models.PersonModel;
import com.beachpartnerllc.beachpartner.utils.TeamMakerInterface;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ConnectionsTabFragment extends Fragment implements View.OnClickListener,TeamMakerInterface {

    private RelativeLayout rlTop,rlTeam;
    private RecyclerView rcviewConn,rcviewTeam;
    private MyConnectionAdapter  mConnectionAdapter;
    private MyTeamAdapter myTeamAdapter;;
    private ImageView upDownToggle;
    private TextView txtv_noconnection;
    ArrayList<PersonModel> dummyData =new ArrayList<>();
    private ArrayList<ConnectionModel>myTeamList = new ArrayList<>();
    private ArrayList<ConnectionModel>connectionList = new ArrayList<>();
    private ArrayList<ConnectionModel>athleteList = new ArrayList<>();  //to sort out athletes from the connectionlist
    LinearLayoutManager layoutmnger;
    private String token;
    private long eventDate;
    private String eventDateToCheckAvailability;





    private BottomSheetBehavior bottomSheetBehavior;

    public ConnectionsTabFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
        eventDate= getArguments().getLong("eventDate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        token   = new PrefManager(getContext()).getToken();
        View view =inflater.inflate(R.layout.fragment_connections_tab, container, false);
        initActivity(view);



        layoutmnger = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);

        //adapter for connections

       // rcviewConn.setHasFixedSize(true);
//        dummyData =createDummyData();

        getConnections();
        //adapter for creating my team
        LinearLayoutManager mnger = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        myTeamAdapter       =   new MyTeamAdapter(getContext(),myTeamList,this);
        rcviewTeam.setAdapter(myTeamAdapter);
        rcviewTeam.setLayoutManager(mnger);
        rcviewTeam.setHasFixedSize(true);








        return view;
    }

    private void initActivity(View view) {

       // rlTop       =       (RelativeLayout)view.findViewById(R.id.rlayoutTop);
        txtv_noconnection   =       (TextView)view.findViewById(R.id.no_connections);
        rlTeam              =       (RelativeLayout)view.findViewById(R.id.rlayout_myteam);

        rcviewConn          =       (RecyclerView)view.findViewById(R.id.rview_connections);
        rcviewTeam          =       (RecyclerView)view.findViewById(R.id.rcview_myteam);

        upDownToggle        =       (ImageView)view.findViewById(R.id.upDown);


        bottomSheetBehavior = BottomSheetBehavior.from(view.findViewById(R.id.bottom_sheet));



            // Capturing the callbacks for bottom sheet
                    bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                        @Override
                        public void onStateChanged(View bottomSheet, int newState) {

                            /*if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                                bottomSheetHeading.setText(getString(R.string.text_collapse_me));
                            } else {
                                bottomSheetHeading.setText(getString(R.string.text_expand_me));
                            }*/

                            // Check Logs to see how bottom sheets behaves
                            switch (newState) {
                                case BottomSheetBehavior.STATE_COLLAPSED:
                                    Log.e("Bottom Sheet Behaviour", "STATE_COLLAPSED");
                                    break;
                                case BottomSheetBehavior.STATE_DRAGGING:
                                    Log.e("Bottom Sheet Behaviour", "STATE_DRAGGING");
                                    break;
                                case BottomSheetBehavior.STATE_EXPANDED:
                                    Log.e("Bottom Sheet Behaviour", "STATE_EXPANDED");
                                    break;
                                case BottomSheetBehavior.STATE_HIDDEN:
                                    Log.e("Bottom Sheet Behaviour", "STATE_HIDDEN");
                                    break;
                                case BottomSheetBehavior.STATE_SETTLING:
                                    Log.e("Bottom Sheet Behaviour", "STATE_SETTLING");
                                    break;
                            }
                        }


                        @Override
                        public void onSlide(View bottomSheet, float slideOffset) {

                        }
                    });
    }





    public ArrayList<PersonModel> createDummyData() {

        ArrayList<PersonModel> personlList = new ArrayList<>();
        personlList.add(new PersonModel("Alivia Orvieto","26",R.drawable.person1));
        personlList.add(new PersonModel("Marti McLaurin","25",R.drawable.person2));
        personlList.add(new PersonModel("Liz Held","30",R.drawable.person3));

        personlList.add(new PersonModel("Alivia Orvieto","26",R.drawable.person1));
        personlList.add(new PersonModel("Marti McLaurin","25",R.drawable.person2));
        personlList.add(new PersonModel("Liz Held","30",R.drawable.person3));

        personlList.add(new PersonModel("Alivia Orvieto","26",R.drawable.person1));
        personlList.add(new PersonModel("Marti McLaurin","25",R.drawable.person2));
        personlList.add(new PersonModel("Liz Held","30",R.drawable.person3));
        personlList.add(new PersonModel("Alivia Orvieto","26",R.drawable.person1));


        return personlList;
    }


    private void getConnections() {
        SimpleDateFormat dft = new SimpleDateFormat("dd-MM-yyyy");
        eventDateToCheckAvailability=dft.format(eventDate);
        Toast.makeText(getContext(), "eventClickedDate-----"+eventDateToCheckAvailability, Toast.LENGTH_SHORT).show();
        connectionList.clear();
        athleteList.clear();
        String user_id  = new PrefManager(getContext()).getUserId();
//18-05-2018
        JsonArrayRequest arrayRequest = new JsonArrayRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_ALL_CONNECTIONS + user_id +"?status=Active"+"&filterDate="+eventDateToCheckAvailability, null, new Response.Listener<JSONArray>() {
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
                            //setting the availability of a user on a particular date
                            model.setConnected_isAvailable_ondate(object.getBoolean("availableOnDate"));
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
        }){ @Override
        public Map<String, String> getHeaders()  {
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
                }
            }

            mConnectionAdapter  =   new MyConnectionAdapter(getContext(),athleteList,this);
            rcviewConn.setAdapter(mConnectionAdapter);
            mConnectionAdapter.notifyDataSetChanged();
            rcviewConn.setLayoutManager(layoutmnger);



        }else {
            txtv_noconnection.setVisibility(View.VISIBLE);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
           /* case R.id.btnExpand:
                if(BottomSheetBehavior.STATE_EXPANDED==3){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                break;
*/

            default:
                break;
        }
    }


    //adding member from connection list to my teamlist using interface
    @Override
    public void onAddListener(ConnectionModel member,int position) {
        myTeamList.add(athleteList.remove(position));
        mConnectionAdapter.notifyDataSetChanged();
        myTeamAdapter.notifyDataSetChanged();

        Toast.makeText(getActivity(), "id --->"+member.getConnected_uId()+"available-->"+member.getConnected_isAvailable_ondate()+" at position-->"+position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRemoveListener(ConnectionModel removedMember, int position) {
        removeConfirmation(myTeamList.get(position).getConnected_firstName(),position);

        //Toast.makeText(getActivity(), "You removed "+myTeamList.get(position).Connected_firstName+" from your potential partners" , Toast.LENGTH_SHORT).show();
    }

    private void removeConfirmation(String firstname, final int position){
        final AlertDialog.Builder alertadd = new AlertDialog.Builder(getActivity());

        alertadd.setTitle("Are you sure you want to remove "+firstname+" from your potential partners?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        athleteList.add(myTeamList.remove(position));
                        mConnectionAdapter.notifyDataSetChanged();
                        myTeamAdapter.notifyDataSetChanged();
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
}
