package com.beachpartnerllc.beachpartner.adpters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.calendar.compactcalendarview.domain.Event;
import com.beachpartnerllc.beachpartner.utils.EventClickListner;

import java.util.ArrayList;

/**
 * Created by user on 25/2/18.
 */

public class PartnerAdapter extends RecyclerView.Adapter<PartnerAdapter.MyViewHolder> {
    public Context mContext;
    private ArrayList<Event> dataList;
    private EventClickListner eventlistner;

    public PartnerAdapter(Context context, ArrayList<Event> allSampleData,EventClickListner eventClickListner) {
        this.dataList = allSampleData;
        this.mContext = context;
        this.eventlistner = eventClickListner;
    }

    @Override
    public PartnerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.partner_item,parent,false);
        PartnerAdapter.MyViewHolder myViewHolder = new PartnerAdapter.MyViewHolder(itemView);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final Event model = dataList.get(position);

        //holder.imgPartnerPic.setBackgroundResource(model.getImage());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, "Clicked "+position, Toast.LENGTH_SHORT).show();
                eventlistner.getEvent(model);

            }
        });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtvrequestDate,txtvRequestPlace,txtvCount;
        public ImageView imgPartnerPic;
        public CardView card;
        public MyViewHolder(View vi) {
            super(vi);

            txtvrequestDate     =   (TextView)vi.findViewById(R.id.txtv_date_request);
            txtvRequestPlace       =   (TextView)vi.findViewById(R.id.txtv_place_request);
            card                =   (CardView)vi.findViewById(R.id.uptournament_card);
            txtvCount           =   (TextView)vi.findViewById(R.id.count);
        }
    }
}
