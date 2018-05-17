package com.beachpartnerllc.beachpartner.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.activity.TabActivity;
import com.beachpartnerllc.beachpartner.adpters.ChatListAdapter;
import com.beachpartnerllc.beachpartner.connections.ApiService;
import com.beachpartnerllc.beachpartner.connections.PrefManager;
import com.beachpartnerllc.beachpartner.models.BpFinderModel;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class MessageFragment extends Fragment {


    ArrayList<String> chatList = new ArrayList<>();
    ArrayList<BpFinderModel> userList = new ArrayList<>();
    TabActivity tabActivity;
    private RecyclerView recyclerView;
    private ChatListAdapter chatListAdapter;
    private Firebase myFirebaseRef;
    private String myId;
    private ProgressBar progressBar;
    private TextView tv_nomsgs;
    private ArrayList<BpFinderModel> connectionList = new ArrayList<>();

    public MessageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            Firebase.setAndroidContext(getActivity());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        myId = new PrefManager(getContext()).getUserId();
        myFirebaseRef = new Firebase("https://beachpartner-6cd7a.firebaseio.com/");
        chatListAdapter = null;
        userList.clear();
        initView(view);
        return view;
    }


    private void initView(View view) {


        recyclerView = (RecyclerView) view.findViewById(R.id.rcv_chat);
        progressBar = (ProgressBar) view.findViewById(R.id.msg_progress);
        tv_nomsgs = (TextView) view.findViewById(R.id.txtv_nomsgs);
        //chatListAdapter =   new ChatListAdapter(getContext(),list);
        RecyclerView.LayoutManager mLayoutmanger = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutmanger);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.setAdapter(chatListAdapter);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() instanceof TabActivity) {
            tabActivity = (TabActivity) getActivity();
            tabActivity.setActionBarTitle("Messages");
        }
        getConnections();
    }

    public void disableMenu() {
        tabActivity.disableFloatButtons();
    }

    private void getConnections() {
        connectionList.clear();
        progressBar.setVisibility(View.VISIBLE);
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
                            Type type = new TypeToken<BpFinderModel>() {
                            }.getType();
                            BpFinderModel finderModel = new Gson().fromJson(obj.toString(), type);
                            connectionList.add(finderModel);


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

    //Method for getting chatlist from firebse
    private void setUpMessage() {
        userList.clear();
        chatList.clear();
        progressBar.setVisibility(View.VISIBLE);
        final Firebase myFirebaseRef = new Firebase("https://beachpartner-6cd7a.firebaseio.com/messages");
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

                HashSet<String> hashSet = new HashSet<String>();
                hashSet.addAll(chatList);
                chatList.clear();
                userList.clear();
                chatList.addAll(hashSet);
                if (connectionList != null && connectionList.size() > 0) {
                    if (chatList.size() > 0 && chatList != null) {
                        for (int i = 0; i < chatList.size(); i++) {
                            String chatId = chatList.get(i).split("-")[0];
                            String chatwith_id = chatList.get(i).split("-")[1];
                            if (chatwith_id != null && chatId != null) {
                                if (chatId.equals(myId) || chatwith_id.equals(myId)) {
                                    if (connectionList.size() > 0 && connectionList != null) {
                                        for (int j = 0; j < connectionList.size(); j++) {
                                            if (chatwith_id.equals(connectionList.get(j).getBpf_id()) || chatId.equals(connectionList.get(j).getBpf_id())) {
                                                userList.add(connectionList.get(j));
                                            }
                                        }
                                    } else {
                                        tv_nomsgs.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        }
                        progressBar.setVisibility(View.GONE);
                        chatListAdapter = new ChatListAdapter(MessageFragment.this, getContext(), userList);
                        recyclerView.setAdapter(chatListAdapter);
                        recyclerView.invalidate();
                        chatListAdapter.notifyDataSetChanged();
                    }

                } else {
                    progressBar.setVisibility(View.GONE);
                    tv_nomsgs.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }


}
