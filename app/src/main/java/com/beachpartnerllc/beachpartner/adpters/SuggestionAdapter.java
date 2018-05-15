package com.beachpartnerllc.beachpartner.adpters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.models.EventReultModel;
import com.beachpartnerllc.beachpartner.utils.AcceptRejectInvitationListener;

import java.util.ArrayList;

/**
 * Created by seq-kala on 14/5/18.
 */

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<EventReultModel>sList;
    private EventReultModel eventModel;
    private AcceptRejectInvitationListener invitationListener;


    public SuggestionAdapter(Context context, ArrayList<EventReultModel> suggestionList,AcceptRejectInvitationListener acceptRejectInvitationListener) {
        this.mContext = context;
        this.sList    = suggestionList;
        this.invitationListener = acceptRejectInvitationListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestions_item,parent,false);
        MyViewHolder MyViewHolder = new MyViewHolder(itemview);
        return MyViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        eventModel = sList.get(position);

        holder.txtv_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invitationListener.showPartnerDialogue();

            }
        });

        holder.txtv_name.setText(eventModel.getEventName());
    }



    @Override
    public int getItemCount() {
        return sList.size();
    }

    public void removeItem(int position) {
        sList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, sList.size());

    }

    public void addItem(String word, int position) {
        //sList.add(word);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, sList.size());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtv_team,txtv_name;
        public ImageView imgv_pic;
        public MyViewHolder(View itemView) {
            super(itemView);

            imgv_pic    = itemView.findViewById(R.id.img_pic);
            txtv_team   = itemView.findViewById(R.id.txtv_team);
            txtv_name   = itemView.findViewById(R.id.txtv_name);

        }
    }



}
