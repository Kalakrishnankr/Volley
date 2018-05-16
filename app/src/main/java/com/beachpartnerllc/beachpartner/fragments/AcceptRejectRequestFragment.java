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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.adpters.PopupAdapter;
import com.beachpartnerllc.beachpartner.adpters.SuggestionAdapter;
import com.beachpartnerllc.beachpartner.connections.ApiService;
import com.beachpartnerllc.beachpartner.connections.PrefManager;
import com.beachpartnerllc.beachpartner.models.EventResultModel;
import com.beachpartnerllc.beachpartner.models.InvitationsModel;
import com.beachpartnerllc.beachpartner.models.PartnerResultModel;
import com.beachpartnerllc.beachpartner.utils.AcceptRejectInvitationListener;
import com.beachpartnerllc.beachpartner.utils.AppConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class AcceptRejectRequestFragment extends Fragment implements AcceptRejectInvitationListener {

    private RecyclerView rcv_requests;
    private TextView eventTitle,eventStart,eventEnd;
    private SuggestionAdapter suggestionAdapter;
    private ArrayList<EventResultModel>suggestionList = new ArrayList<>();
    private LinearLayout undoLayout;
    private Paint p = new Paint();
    private int position;
    private String word,eventID,oranizerID,responseType,user_token;
    private EventResultModel eventReultModel =null;
    private ArrayList<InvitationsModel>invitationsModels = new ArrayList<>();
    private ArrayList<PartnerResultModel>partnerList = new ArrayList<>();
    private AcceptRejectInvitationListener invitationListener;
    private PrefManager prefManager;
    private static final String TAG = "AcceptRejectRequestFrag";
    public AcceptRejectRequestFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user_token  =  new PrefManager(getActivity()).getToken();
        if (getArguments() != null) {
            eventReultModel = getArguments().getParcelable(AppConstants.EVENT_OBJECT);
            if (eventReultModel != null) {
                suggestionList.clear();
                suggestionList.add(eventReultModel);
            }
        }
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

        eventTitle   = view.findViewById(R.id.event_title);
        eventStart   = view.findViewById(R.id.txtevent_start);
        eventEnd     = view.findViewById(R.id.txtevent_end);
        undoLayout   = view.findViewById(R.id.layout_undo);
        rcv_requests  = view.findViewById(R.id.rcv_requests);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        SimpleDateFormat dft = new SimpleDateFormat("MM-dd-yyyy");
        long event_startdate  = Long.parseLong(eventReultModel.getEventStartDate());
        long event_endDate = Long.parseLong(eventReultModel.getEventEndDate());
        Date date_start   = new Date(event_startdate);
        Date date_end  = new Date(event_endDate);

        eventTitle.setText(eventReultModel.getEventName());
        eventStart.setText(dft.format(date_start));
        eventEnd.setText(dft.format(date_end));
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        suggestionAdapter = new SuggestionAdapter(getContext(),suggestionList,this);
        rcv_requests.setLayoutManager(manager);
        rcv_requests.setAdapter(suggestionAdapter);
        suggestionAdapter.notifyDataSetChanged();
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
                    Log.d(TAG, "onResponse:Accept/Reject");
                    //Toast.makeText(getActivity(), "Accepted", Toast.LENGTH_SHORT).show();
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

    //api for accept invitation
   /* private void acceptInvitation() {

        JsonObjectRequest objectRequest = new JsonObjectRequest(APi)
    }*/

    @Override
    public void showPartnerDialogue() {
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

        if (suggestionList != null) {
            partnerList.clear();
            invitationsModels.clear();
            for(int i =0;i<suggestionList.get(0).getInvitationList().size();i++){
                invitationsModels.addAll(suggestionList.get(0).getInvitationList());
            }
            if (invitationsModels.size() > 0) {
                for(int j=0;j<invitationsModels.size();j++){
                   partnerList.addAll(invitationsModels.get(j).getPartnerList());
                }
            }
            if (partnerList.size() > 0) {
                PopupAdapter cardArrayAdapter = new PopupAdapter (getActivity(),partnerList);
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
