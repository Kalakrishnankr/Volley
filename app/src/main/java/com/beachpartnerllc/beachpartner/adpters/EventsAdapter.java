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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.calendar.compactcalendarview.domain.Event;
import com.beachpartnerllc.beachpartner.fragments.AcceptRejectRequestFragment;
import com.beachpartnerllc.beachpartner.fragments.EventDescriptionFragment;
import com.beachpartnerllc.beachpartner.fragments.MyCalendarEvents;

import java.util.List;

/**
 * Created by seq-kala on 5/3/18.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {

    Context mContext;
    List<Event>list;
    boolean isMycalActive;
    Event model;

    public EventsAdapter(Context context, List<Event> eventList, boolean isMycal){
        this.mContext   =context;
        this.list       =eventList;
        this.isMycalActive= isMycal;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemview    = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_list_items,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(itemview);
        myViewHolder.setIsRecyclable(false);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        model = list.get(position);
        holder.tview_events.setText((CharSequence) model.getEventName());
        if(!isMycalActive){
            if (model.getEventStatus()!=null && model.getRegType()!=null) {
                //Check event status =active register type =Organizer
                if (model.getRegType().equalsIgnoreCase("Organizer")) {
                    holder.inviteStatus.setBackground(mContext.getResources().getDrawable(R.drawable.ic_send_request));

                    if(model.getEventStatus().equalsIgnoreCase("Registered")){
                        holder.event_view.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                    }
                    else if(model.getEventStatus().equalsIgnoreCase("Invited")){
                        holder.event_view.setBackgroundColor(mContext.getResources().getColor(R.color.waitin_orange));
                    }
                    else if(model.getEventStatus().equalsIgnoreCase("Active")){
                        holder.event_view.setBackgroundColor(mContext.getResources().getColor(R.color.waitin_orange));
                    }
                }
                else if(model.getRegType().equalsIgnoreCase("Invitee")){
                    holder.inviteStatus.setBackground(mContext.getResources().getDrawable(R.drawable.ic_request_receive));

                    if(model.getEventStatus().equalsIgnoreCase("Registered")){
                        holder.event_view.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                    }
                    else if(model.getEventStatus().equalsIgnoreCase("Invited")){
                        holder.event_view.setBackgroundColor(mContext.getResources().getColor(R.color.waitin_orange));
                    }
                    else if(model.getEventStatus().equalsIgnoreCase("Active")){
                        holder.event_view.setBackgroundColor(mContext.getResources().getColor(R.color.waitin_orange));
                    }

                }

            }
        }


        // int colr= Integer.parseInt(model.event_color.trim());


        //item click on individual card
//
//        holder.cardview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //Activate My calendar
//                if(isMycalActive){
//
//                    MyCalendarEvents myCalendarEvents = new MyCalendarEvents();
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("mycal_event_clicked",model);
//                    myCalendarEvents.setArguments(bundle);
//                    FragmentManager fragmentManager  =  ((FragmentActivity)mContext).getSupportFragmentManager();
//                    FragmentTransaction ctrans = fragmentManager.beginTransaction();
//                    ctrans.replace(R.id.container,myCalendarEvents);
//                    ctrans.addToBackStack(null);
//                    ctrans.commit();
//
//                }else {
//                    //Activate Master Calendar
//                    try{
//                        if(!model.getInvitationStatus().equalsIgnoreCase("Accepted") && model.getRegType().equalsIgnoreCase("Invitee")){
//                            AcceptRejectRequestFragment ARFragment =new AcceptRejectRequestFragment();
//                            Bundle bundlemodel =new Bundle();
//                            bundlemodel.putString("notAcceptedInvitee",model.getEventId());
//                            ARFragment.setArguments(bundlemodel);
//                            FragmentManager ARmanager = ((FragmentActivity)mContext).getSupportFragmentManager();
//                            FragmentTransaction ARtrans = ARmanager.beginTransaction();
//                            //ctrans.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
//                            ARtrans.replace(R.id.container,ARFragment);
//                            ARtrans.addToBackStack(null);
//                            ARtrans.commit();
//
//                        }
//                        else{
//                            EventDescriptionFragment eventDescriptionFragment = new EventDescriptionFragment();
//                            //add bundle
//                            Bundle bundle = new Bundle();
//                            bundle.putSerializable("event_clicked",model);
//                            eventDescriptionFragment.setArguments(bundle);
//                            FragmentManager manager = ((FragmentActivity)mContext).getSupportFragmentManager();
//                            FragmentTransaction ctrans = manager.beginTransaction();
//                            //ctrans.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
//                            ctrans.replace(R.id.container,eventDescriptionFragment);
//                            ctrans.addToBackStack(null);
//                            ctrans.commit();
//                        }
//
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//
//
//
//                }
//
//
//            }
//        });

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tview_events;
        View event_view;
        CardView cardview;
        ImageView inviteStatus;
        LinearLayout bg_layout;

        public MyViewHolder(View itemView) {
            super(itemView);

        tview_events    =   itemView.findViewById(R.id.event);
        event_view      =   itemView.findViewById(R.id.event_color);
        cardview        =   itemView.findViewById(R.id.cardview_item);
        inviteStatus    =   itemView.findViewById(R.id.requests_img);
        bg_layout       =   itemView.findViewById(R.id.bg_status);
        itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition(); // gets item position
            model = list.get(position);
            //Activate My calendar
            if(isMycalActive){

                MyCalendarEvents myCalendarEvents = new MyCalendarEvents();
                Bundle bundle = new Bundle();
                bundle.putSerializable("mycal_event_clicked",model);
                myCalendarEvents.setArguments(bundle);
                FragmentManager fragmentManager  =  ((FragmentActivity)mContext).getSupportFragmentManager();
                FragmentTransaction ctrans = fragmentManager.beginTransaction();
                ctrans.add(R.id.container,myCalendarEvents);
                ctrans.addToBackStack(null);
                ctrans.commit();

            }else {
                //Activate Master Calendar
                try{
                    if(!model.getInvitationStatus().equalsIgnoreCase("Accepted") && model.getRegType().equalsIgnoreCase("Invitee")){
                        AcceptRejectRequestFragment ARFragment =new AcceptRejectRequestFragment();
                        Bundle bundlemodel =new Bundle();
                        bundlemodel.putString("notAcceptedInvitee",model.getEventId());
                        ARFragment.setArguments(bundlemodel);
                        FragmentManager ARmanager = ((FragmentActivity)mContext).getSupportFragmentManager();
                        FragmentTransaction ARtrans = ARmanager.beginTransaction();
                        //ctrans.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                        ARtrans.replace(R.id.container,ARFragment);
                        ARtrans.addToBackStack(null);
                        ARtrans.commit();

                    }
                    else{
                        EventDescriptionFragment eventDescriptionFragment = new EventDescriptionFragment();
                        //add bundle
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("event_clicked",model);
                        eventDescriptionFragment.setArguments(bundle);
                        FragmentManager manager = ((FragmentActivity)mContext).getSupportFragmentManager();
                        FragmentTransaction ctrans = manager.beginTransaction();
                        //ctrans.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                        ctrans.add(R.id.container,eventDescriptionFragment);
                        ctrans.addToBackStack(null);
                        ctrans.commit();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }



            }

        }
    }
}