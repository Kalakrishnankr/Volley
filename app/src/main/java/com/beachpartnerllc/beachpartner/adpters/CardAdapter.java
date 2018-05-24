package com.beachpartnerllc.beachpartner.adpters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.calendar.compactcalendarview.domain.Event;
import com.beachpartnerllc.beachpartner.fragments.MyCalendarEvents;
import com.beachpartnerllc.beachpartner.models.InvitationsModel;
import com.beachpartnerllc.beachpartner.models.PartnerResultModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by seq-kala on 20/2/18.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {
    public Context mContext;
    private ArrayList<Event> dataList;
    private List<PartnerResultModel>partnerList = new ArrayList<>();
    private List<InvitationsModel> invitationList;
    private  String partners;


    public CardAdapter(Context context, ArrayList<Event> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }
    @Override
    public CardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView);
        itemView.setMinimumWidth(parent.getMeasuredWidth());

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(CardAdapter.MyViewHolder holder, int position) {

        final Event model = dataList.get(position);

        SimpleDateFormat dft = new SimpleDateFormat("MM-dd-yyyy");
        long esDate  = model.getEventStartDate();
        String date = dft.format(esDate);
        holder.txtv_date.setText(date);
        holder.txtv_place.setText(model.getEventVenue());
        if (model.getInvitationList() != null) {
            partners="";
            partnerList.clear();
            invitationList = model.getInvitationList();
            if (invitationList!=null) {
                for(int i=0;i<invitationList.size();i++){
                    if(invitationList.get(i).getInviterName()!=null){
                        if(!invitationList.get(i).getInviterName().equalsIgnoreCase("")||!invitationList.get(i).getInviterName().equalsIgnoreCase("null")){
                            partners+=(invitationList.get(i).getInviterName()+",");
                        }
                    }
                    partnerList.addAll(invitationList.get(i).getPartnerList());
                }
            }
            if(partnerList.size()>0){
                for(int i=0;i<partnerList.size();i++){
                    if(i!=partnerList.size()){
                        partners+=(partnerList.get(i).getPartnerName());

                    }
                    else{
                        partners+=(partnerList.get(i).getPartnerName()+",");
                    }

                }

            }

            holder.txtv_players.setText(partners);
            partners="";
        }else{
            holder.txtv_players.setText("");
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyCalendarEvents myCalendarEvents = new MyCalendarEvents();
                Bundle bundle = new Bundle();
                bundle.putSerializable("mycal_event_clicked",model);
                myCalendarEvents.setArguments(bundle);
                FragmentManager fragmentManager  =  ((FragmentActivity)mContext).getSupportFragmentManager();
                FragmentTransaction ctrans = fragmentManager.beginTransaction();
                ctrans.replace(R.id.container,myCalendarEvents);
                ctrans.addToBackStack(null);
                ctrans.commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtv_date,txtv_place,txtv_players;
        public CardView cardView;

        public MyViewHolder(View v) {
            super(v);

            txtv_date       =   (TextView) v.findViewById(R.id.txtv_date);
            txtv_place      =   (TextView) v.findViewById(R.id.txtv_place);
            txtv_players    =   (TextView) v.findViewById(R.id.txtv_players);
            cardView        =   (CardView) v.findViewById(R.id.uptournament_card);


        }
    }

    // private ArrayList<SingleItemModel> itemsList;

}
