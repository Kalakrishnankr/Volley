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

    public EventsAdapter(Context context, List<Event> eventList, boolean isMycal){
        this.mContext   =context;
        this.list       =eventList;
        this.isMycalActive= isMycal;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemview    = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_list_items,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(itemview);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Event model = list.get(position);
        holder.tview_events.setText((CharSequence) model.getEventName());
       // int colr= Integer.parseInt(model.event_color.trim());
        holder.event_view.setBackgroundColor(model.getColor());

        //item click on individual card

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Activate My calendar
                if(isMycalActive){

                    MyCalendarEvents myCalendarEvents = new MyCalendarEvents();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("mycal_event_clicked",model);
                    myCalendarEvents.setArguments(bundle);
                    FragmentManager fragmentManager  =  ((FragmentActivity)mContext).getSupportFragmentManager();
                    FragmentTransaction ctrans = fragmentManager.beginTransaction();
                    ctrans.replace(R.id.container,myCalendarEvents);
                    ctrans.addToBackStack(null);
                    ctrans.commit();

                }else {
                    //Activate Master Calendar

                    EventDescriptionFragment eventDescriptionFragment = new EventDescriptionFragment();
                    //add bundle
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("event_clicked",model);
                    eventDescriptionFragment.setArguments(bundle);
                    FragmentManager manager = ((FragmentActivity)mContext).getSupportFragmentManager();
                    FragmentTransaction ctrans = manager.beginTransaction();
                    //ctrans.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                    ctrans.replace(R.id.container,eventDescriptionFragment);
                    ctrans.addToBackStack(null);
                    ctrans.commit();

                }


            }
        });

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tview_events;
        View event_view;
        CardView cardview;

        public MyViewHolder(View itemView) {
            super(itemView);

        tview_events    =   (TextView) itemView.findViewById(R.id.event);
        event_view      =   (View)itemView.findViewById(R.id.event_color);
        cardview        =   (CardView)itemView.findViewById(R.id.cardview_item);


        }
    }
}
