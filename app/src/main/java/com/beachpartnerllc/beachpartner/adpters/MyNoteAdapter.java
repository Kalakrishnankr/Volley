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
import android.widget.TextView;

import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.connections.PrefManager;
import com.beachpartnerllc.beachpartner.fragments.NoteFragment;
import com.beachpartnerllc.beachpartner.models.InvitationsModel;
import com.beachpartnerllc.beachpartner.models.MyCalendarPartnerModel;
import com.beachpartnerllc.beachpartner.models.PartnerResultModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by seq-kala on 20/3/18.
 */

public class MyNoteAdapter extends RecyclerView.Adapter<MyNoteAdapter.PartnerListHolder> {
    Context mContext;
    private ArrayList<MyCalendarPartnerModel> partnerResultModel = new ArrayList<>();
    private String user_Id;

    public MyNoteAdapter(Context context,ArrayList<MyCalendarPartnerModel> dataModelList) {
        this.mContext =context;
        this.partnerResultModel = dataModelList;

    }

    @Override
    public PartnerListHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView   = LayoutInflater.from(parent.getContext()).inflate(R.layout.myevent_note_item,parent,false);
        PartnerListHolder myViewHolder    = new PartnerListHolder(itemView);
        user_Id=new PrefManager(mContext).getUserId();

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(PartnerListHolder holder, int position) {
        final MyCalendarPartnerModel model = partnerResultModel.get(position);
        if(model!=null){
                if(model.getpartner_role().equalsIgnoreCase("Organizer")){
                    holder.tv_name.setText(model.getpartner_Name());
                    Glide.with(mContext).load(model.getPartner_ImageUrl()).into(holder.my_pic);
                    holder.tv_role.setVisibility(View.VISIBLE);
                }
                else{
                    holder.tv_name.setText(model.getpartner_Name());
                    Glide.with(mContext).load(model.getPartner_ImageUrl()).into(holder.my_pic);
                    holder.tv_role.setVisibility(View.GONE);
                }


        }


        holder.partnerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteFragment noteFragment = new NoteFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("personId",model.getpartner_Id());
                bundle.putSerializable("personName",model.getpartner_Name());
                noteFragment.setArguments(bundle);
                FragmentManager fragmentManager  =  ((FragmentActivity)mContext).getSupportFragmentManager();
                FragmentTransaction ctrans = fragmentManager.beginTransaction();
                ctrans.replace(R.id.container,noteFragment);
                ctrans.addToBackStack(null);
                ctrans.commit();
            }
        });

    }



    @Override
    public int getItemCount() {
        return partnerResultModel.size();
    }

    public class PartnerListHolder extends RecyclerView.ViewHolder {
        public ImageView my_pic;
        public TextView tv_name,tv_role;
        private CardView partnerCard;

        public PartnerListHolder(View itemView) {
            super(itemView);

            my_pic  =   (ImageView) itemView.findViewById(R.id.my_pic);
            tv_name =   (TextView)  itemView.findViewById(R.id.my_name);
            tv_role =   (TextView)  itemView.findViewById(R.id.my_role);
            partnerCard = (CardView) itemView.findViewById(R.id.cardview_item);
        }
    }
}