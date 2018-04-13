package com.goldemo.beachpartner.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.adpters.ChatListAdapter;
import com.goldemo.beachpartner.connections.ApiService;
import com.goldemo.beachpartner.connections.PrefManager;
import com.goldemo.beachpartner.models.ConnectionModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MessageFragment extends Fragment {

    ArrayList<String> chatList = new ArrayList<>();
    ArrayList<ConnectionModel> userList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ChatListAdapter chatListAdapter;
    private Firebase myFirebaseRef;
    private String myId;
    private ArrayList<ConnectionModel> connectionList = new ArrayList<>();

    public MessageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        myId = new PrefManager(getContext()).getUserId();
        myFirebaseRef = new Firebase("https://beachpartner-be21e.firebaseio.com/");

        getConnections();
        initView(view);
        return view;
    }


    private void initView(View view) {


        recyclerView = (RecyclerView) view.findViewById(R.id.rcv_chat);
        //chatListAdapter =   new ChatListAdapter(getContext(),list);
        RecyclerView.LayoutManager mLayoutmanger = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutmanger);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.setAdapter(chatListAdapter);

    }

    //Method for getting chatlist from firebse
    private void setUpMessage() {
        userList.clear();
        final Firebase myFirebaseRef = new Firebase("https://beachpartner-be21e.firebaseio.com/messages");
        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    chatList.add(postSnapshot.getKey());

                    /*for (DataSnapshot pSnapshot1 : postSnapshot.getChildren()) {
                          MessageModel m = pSnapshot1.getValue(MessageModel.class);
                          if (m.getSender_id().equals(myId)) {
                              userList.add(m);
                          }
                    }*/

                }
                if(chatList.size()>0 && chatList!=null){
                    for (int i=0;i<chatList.size();i++){
                        String chatId = chatList.get(i).split("-")[0];
                        String chatwith_id = chatList.get(i).split("-")[1];
                        if(chatId.equals(myId) || chatwith_id.equals(myId)){
                            for (int j=0;j<connectionList.size();j++){
                                if(chatwith_id.equals(connectionList.get(j).getConnected_uId()) || chatId.equals(connectionList.get(j).getConnected_uId())){
                                    userList.add(connectionList.get(j));
                                }
                            }
                        }
                    }
                    chatListAdapter = new ChatListAdapter(getContext(), userList);
                    recyclerView.setAdapter(chatListAdapter);
                    chatListAdapter.notifyDataSetChanged();
                }



            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }


    private void getConnections() {
        connectionList.clear();
        String user_id = new PrefManager(getContext()).getUserId();
        final String token = new PrefManager(getContext()).getToken();

        JsonArrayRequest arrayRequest = new JsonArrayRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_ALL_CONNECTIONS + user_id + "?status=Active", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject object = response.getJSONObject(i);
                            JSONObject obj = object.getJSONObject("connectedUser");
                            ConnectionModel model = new ConnectionModel();
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
                    setUpMessage();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        Log.d("Request", arrayRequest.toString());
        requestQueue.add(arrayRequest);


    }


}
