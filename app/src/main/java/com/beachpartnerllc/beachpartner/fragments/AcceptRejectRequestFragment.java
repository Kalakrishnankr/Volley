package com.beachpartnerllc.beachpartner.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.activity.TabActivity;
import com.beachpartnerllc.beachpartner.adpters.PopupAdapter;
import com.beachpartnerllc.beachpartner.adpters.SuggestionAdapter;
import com.beachpartnerllc.beachpartner.connections.ApiService;
import com.beachpartnerllc.beachpartner.connections.PrefManager;
import com.beachpartnerllc.beachpartner.models.EventResultModel;
import com.beachpartnerllc.beachpartner.models.InvitationsModel;
import com.beachpartnerllc.beachpartner.models.MyCalendarPartnerModel;
import com.beachpartnerllc.beachpartner.models.PartnerResultModel;
import com.beachpartnerllc.beachpartner.utils.AcceptRejectInvitationListener;
import com.beachpartnerllc.beachpartner.utils.AppConstants;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class AcceptRejectRequestFragment extends Fragment implements AcceptRejectInvitationListener {

    private RecyclerView rcv_requests;
    private TextView eventTitle,eventStart,eventEnd,eventLocation,eventVenue,eventAdmin,teamSize;
    private SuggestionAdapter suggestionAdapter;
    private ArrayList<EventResultModel>suggestionList = new ArrayList<>();
    //private LinearLayout undoLayout;
    private Paint p = new Paint();
    private int position;
    private String word,eventID,oranizerID,responseType,user_token,user_id;
    private EventResultModel eventResultModel;
    private String notAcceptedId =null;
    private ArrayList<InvitationsModel>invitationsModels = new ArrayList<>();
    private ArrayList<PartnerResultModel>partnerList = new ArrayList<>();
    private ArrayList<MyCalendarPartnerModel>partnerAdapterList = new ArrayList<>();
    private AcceptRejectInvitationListener invitationListener;
    private PrefManager prefManager;
    private String message;
    private static final String TAG = "AcceptRejectRequestFrag";


    private ArrayList<PartnerResultModel>listPartner= new ArrayList<>();
    private ArrayList<InvitationsModel>listInvitation= new ArrayList<>();
    public AcceptRejectRequestFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof TabActivity){
            TabActivity tabActivity = (TabActivity)getActivity();
            tabActivity.setActionBarTitle("Invitations");
            tabActivity.disableFloatButtons();
        }
        user_token  =  new PrefManager(getActivity()).getToken();
        user_id     =  new PrefManager(getActivity()).getUserId();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accept_reject_request, container, false);
        initviews(view);


        return view;
    }

    private void initviews(View view) {

        eventTitle      = view.findViewById(R.id.event_title);
        eventStart      = view.findViewById(R.id.txtevent_start);
        eventEnd        = view.findViewById(R.id.txtevent_end);
        eventLocation   = view.findViewById(R.id.txtv_location);
        eventVenue      = view.findViewById(R.id.textv_venue);
        eventAdmin      = view.findViewById(R.id.txtv_admin);
        teamSize        = view.findViewById(R.id.txtv_teamSize);


        //undoLayout   = view.findViewById(R.id.layout_undo);
        rcv_requests  = view.findViewById(R.id.rcv_requests);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            if(getArguments().getParcelable(AppConstants.EVENT_OBJECT)!=null){
                eventResultModel = getArguments().getParcelable(AppConstants.EVENT_OBJECT);
            }
            if(getArguments().getSerializable("notAcceptedInvitee")!=null){
                notAcceptedId   = getArguments().getSerializable("notAcceptedInvitee").toString();
            }
            if (eventResultModel != null) {
                suggestionList.clear();
                suggestionList.add(eventResultModel);
                setUpView();
            }
            else if(notAcceptedId!=null){
                requestHandler(notAcceptedId);

            }
        }


        initswipe();


       /* undoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                undoLayout.setVisibility(View.GONE);
                //suggestionAdapter.addItem();
                suggestionAdapter.notifyDataSetChanged();
            }
        });*/


    }

    private void setUpView() {
        SimpleDateFormat dft = new SimpleDateFormat("MM-dd-yyyy");
        long event_startdate  = Long.parseLong(eventResultModel.getEventStartDate());
        long event_endDate = Long.parseLong(eventResultModel.getEventEndDate());
        Date date_start   = new Date(event_startdate);
        Date date_end  = new Date(event_endDate);

        eventTitle.setText(eventResultModel.getEventName());
        eventLocation.setText(eventResultModel.getState()); // event location changed to state as it is not returning any values
        eventVenue.setText(eventResultModel.getEventVenue());
        eventAdmin.setText(eventResultModel.getEventAdmin());
        teamSize.setText(eventResultModel.getTeamSize());
        eventStart.setText(dft.format(date_start));
        eventEnd.setText(dft.format(date_end));
        if (suggestionList != null) {
            for (int i = 0; i < suggestionList.size(); i++) {
                listInvitation.addAll(suggestionList.get(i).getInvitationList());
            }

        }
        if (listInvitation != null) {
            for (int j = 0; j < listInvitation.size(); j++) {
                listPartner.addAll(listInvitation.get(j).getPartnerList());
            }
        }


        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        suggestionAdapter = new SuggestionAdapter(getContext(),listInvitation,this);
        rcv_requests.setLayoutManager(manager);
        rcv_requests.setAdapter(suggestionAdapter);
        suggestionAdapter.notifyDataSetChanged();
    }

    private void requestHandler(String notAcceptedId) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_INVITATION_LIST+notAcceptedId+"?calendarType=mastercalendar", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //progressBar_rqsts.setVisibility(View.INVISIBLE);
                if (response != null) {
                    eventResultModel = new Gson().fromJson(response.toString(),EventResultModel.class);
                    if (eventResultModel != null) {
                        suggestionList.clear();
                        suggestionList.add(eventResultModel);
                        setUpView();
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
                            json = trimMessage(json, "title");
                            if (json != null) {
                                Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;
                        case 400:
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
            public Map<String, String> getHeaders(){
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + user_token);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        if (getActivity() != null) {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            Log.d("Request", jsonObjectRequest.toString());
            requestQueue.add(jsonObjectRequest);
        }
        // return eventResultModel;

    }



    private void initswipe() {

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback= new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                EventResultModel eventModel = suggestionList.get(position);
                position =  viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.LEFT) {
                    //Toast.makeText(getActivity(), "Left swiped", Toast.LENGTH_SHORT).show();
                    responseType = "Reject";
                    JSONObject objectReject = new JSONObject();
                    try {
                        objectReject.put("eventId",eventModel.getEventId());
                        objectReject.put("orgUserId",eventModel.getInvitationList().get(position).getInviterUserId());
                        objectReject.put("responseType",responseType);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    eventHandler(objectReject);
                    suggestionAdapter.removeItem(position);
                    if (!suggestionList.isEmpty()) {
                        EventResultModel last = suggestionList.get(suggestionList.size()-1);
                    }
                    //undoLayout.setVisibility(View.VISIBLE);
                }
                if (direction == ItemTouchHelper.RIGHT) {
                    //Toast.makeText(getActivity(), "Right swiped", Toast.LENGTH_SHORT).show();
                    responseType = "Accept";
                    JSONObject object = new JSONObject();
                    try {
                        object.put("eventId",eventModel.getEventId());
                        object.put("orgUserId",eventModel.getInvitationList().get(position).getInviterUserId());
                        object.put("responseType",responseType);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    eventHandler(object);
                    //suggestionAdapter.removeItem(position);
                    //undoLayout.setVisibility(View.VISIBLE);

                }

            }


        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            Bitmap icon;
            if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                View itemView = viewHolder.itemView;
                float height = (float) itemView.getBottom() - (float) itemView.getTop();
                float width = height / 3;
                if(dX > 0){
                    p.setColor(Color.parseColor("#388E3C"));
                    RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                    c.drawRect(background,p);
                    icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_edit);
                    RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                    c.drawBitmap(icon,null,icon_dest,p);
                } else {
                    p.setColor(Color.parseColor("#D32F2F"));
                    RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                    c.drawRect(background,p);
                    icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_edit);
                    RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                    c.drawBitmap(icon,null,icon_dest,p);
                }
            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rcv_requests);
    }

    //Handle the event
    private void eventHandler(JSONObject object) {
        JsonObjectRequest objectRequest = new JsonObjectRequest(ApiService.REQUEST_METHOD_POST, ApiService.HANDLE_EVENT, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    Log.d(TAG, "onResponse:Accept/Reject"+response.toString());
                    try {
                        message = response.getString("message").trim();
                        if (getActivity() != null) {
                            HomeFragment homeFragment =  new HomeFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.container, homeFragment);
                            transaction.commit();
                        }

                        /*if (message.equalsIgnoreCase("Invitation accepted succesfully")) {
                            //Move to calendar fragment here
                            if (getActivity() != null) {
                                CalendarFragment calendarFragment = new CalendarFragment();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.replace(R.id.container, calendarFragment);
                                transaction.commit();
                            }
                        }
                        if(message.equalsIgnoreCase("Invitation rejected succesfully")){
                            if (getActivity() != null) {
                                HomeFragment homeFragment =  new HomeFragment();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.replace(R.id.container, homeFragment);
                                transaction.commit();
                            }
                        }*/

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
                            json = trimMessage(json, "title");
                            if (json != null) {
                                Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
                            }
                            break;
                        case 400:
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
            public Map<String, String> getHeaders(){
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + user_token);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };
        if (getActivity() != null) {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            Log.d(TAG, "eventHandler: "+requestQueue.toString());
            requestQueue.add(objectRequest);
        }
    }


    @Override
    public void showPartnerDialogue(InvitationsModel  model) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.partnerlist_view, null);
        alert.setView(layout);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(true);

        final RecyclerView listView = layout. findViewById(R.id.card_listView);
        final ImageView imageView       = layout.findViewById(R.id.partnerImg);
        final TextView  textView_name   = layout.findViewById(R.id.partner_name);
        final TextView  textView_status = layout.findViewById(R.id.partner_status);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        listView.setLayoutManager(layoutManager);

        partnerList.clear();
        if (suggestionList != null) {
            listInvitation.clear();
            listInvitation.add(model);
            for (int k=0;k<listInvitation.size();k++){
                partnerList.addAll(listInvitation.get(k).getPartnerList());
            }

            //invitationsModels.clear();
            partnerAdapterList.clear();
            /*for(int i =0;i<suggestionList.get(0).getInvitationList().size();i++){
                invitationsModels.addAll(suggestionList.get(0).getInvitationList());
            }*/

            /*if (invitationsModels.size() > 0) {
                for(int j=0;j<invitationsModels.size();j++){
                   partnerList.addAll(invitationsModels.get(j).getPartnerList());
                }
            }*/


            if(listInvitation!=null) {
                for(int j=0;j<listInvitation.size();j++){
                    if (!listInvitation.get(j).getInviterUserId().equalsIgnoreCase(user_id)) {
                        MyCalendarPartnerModel partnerObject1 = new MyCalendarPartnerModel();
                        partnerObject1.setpartner_Id(listInvitation.get(j).getInviterUserId());
                        partnerObject1.setPartner_ImageUrl(listInvitation.get(j).getInviterImageUrl());
                        partnerObject1.setpartner_Name(listInvitation.get(j).getInviterName());
                        partnerObject1.setpartner_role("Organizer");
                        partnerAdapterList.add(partnerObject1);//organizer
                    }
                }
            }

            for(int i=0;i<partnerList.size();i++){
                MyCalendarPartnerModel partnerObject2 =new MyCalendarPartnerModel();
                partnerObject2.setpartner_Id(partnerList.get(i).getPartnerUserId());
                partnerObject2.setPartner_ImageUrl(partnerList.get(i).getPartnerImageUrl());
                partnerObject2.setpartner_Name(partnerList.get(i).getPartnerName());
                partnerObject2.setpartner_role("Invitee");
                partnerObject2.setPartner_ivitationStatus(partnerList.get(i).getInvitationStatus());
                if(!partnerList.get(i).getPartnerUserId().equalsIgnoreCase(user_id)){
                    partnerAdapterList.add(partnerObject2);//invitees
                }
            }
            if (partnerAdapterList.size() > 0) {
                PopupAdapter cardArrayAdapter = new PopupAdapter (getActivity(),partnerAdapterList);
                listView.setAdapter(cardArrayAdapter);
            }
        }

        alertDialog.show();
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
