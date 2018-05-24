package com.beachpartnerllc.beachpartner.adpters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beachpartnerllc.beachpartner.CircularImageView;
import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.models.InvitationsModel;
import com.beachpartnerllc.beachpartner.utils.AcceptRejectInvitationListener;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by seq-kala on 14/5/18.
 */

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<InvitationsModel>sList;
    private AcceptRejectInvitationListener invitationListener;


    public SuggestionAdapter(Context context, ArrayList<InvitationsModel> suggestionList, AcceptRejectInvitationListener acceptRejectInvitationListener) {
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

        final InvitationsModel eventModel = sList.get(position);
        holder.txtv_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invitationListener.showPartnerDialogue(eventModel);

            }
        });
        if (eventModel.getInviterImageUrl() != null) {
            if (!eventModel.getInviterImageUrl().equals("null")) {
                Glide.with(mContext).load(eventModel.getInviterImageUrl()).into(holder.imgv_pic);
            }else {
                holder.imgv_pic.setImageResource(R.drawable.ic_person);
            }
        }else{
            holder.imgv_pic.setImageResource(R.drawable.ic_person);
        }
        holder.txtv_name.setText(eventModel.getInviterName());
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
        public CircularImageView imgv_pic;
        public MyViewHolder(View itemView) {
            super(itemView);

            imgv_pic    = itemView.findViewById(R.id.img_pic);
            txtv_team   = itemView.findViewById(R.id.txtv_team);
            txtv_name   = itemView.findViewById(R.id.txtv_name);

        }
    }



}
