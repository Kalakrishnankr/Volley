package com.beachpartnerllc.beachpartner.adpters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beachpartnerllc.beachpartner.CircularImageView;
import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.models.ConnectionModel;
import com.beachpartnerllc.beachpartner.utils.TeamMakerInterface;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by seq-kala on 9/3/18.
 */

public class MyConnectionAdapter extends RecyclerView.Adapter<MyConnectionAdapter.MyViewHolder> {
    Context mContext;
    private TeamMakerInterface makerInterface;

    private ArrayList<ConnectionModel>dataModelList = new ArrayList<>();

    public MyConnectionAdapter(Context context, ArrayList<ConnectionModel>allSampleData,TeamMakerInterface teamMakerInterface) {

        this.mContext       =   context;
        this.dataModelList  =   allSampleData;
        this.makerInterface =   teamMakerInterface;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.mconnection_item,parent,false);
        MyViewHolder myViewHolder  = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        ConnectionModel model = dataModelList.get(position);
        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makerInterface.onAddListener(dataModelList.get(position),position);
            }
        });

        holder.tvname.setText(model.getConnected_firstName().trim());
        if(model.getConnected_isAvailable_ondate()){
            holder.tv_unavailable.setVisibility(View.GONE);
            holder.imgAdd.setVisibility(View.VISIBLE);

        }
        else{
            holder.invite_partner_container.setBackgroundResource(R.color.smallgrey);
            holder.imgAdd.setVisibility(View.GONE);
            holder.tv_unavailable.setVisibility(View.VISIBLE);
        }

        //image check
        if(model.getConnected_imageUrl()!=null){
            if(!model.getConnected_imageUrl().equals("null")){
                Glide.with(mContext).load(model.getConnected_imageUrl()).into(holder.imgPic);
                //holder.profilePic.setImageURI(Uri.parse(dataLists.get(position).getConnected_imageUrl()));
            }else {
                holder.imgPic.setImageResource(R.drawable.ic_person);
            }
        }
        else {
            holder.imgPic.setImageResource(R.drawable.ic_person);

        }


    }



    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvname,tv_unavailable;
        public ImageView imgAdd;
        public CircularImageView imgPic;
        private LinearLayout invite_partner_container;
        public MyViewHolder(View v) {
            super(v);
            invite_partner_container    =   (LinearLayout) v.findViewById(R.id.invite_partner_container);
            tv_unavailable              =   (TextView)v.findViewById(R.id.unavailable);
            tvname                      =   (TextView)v.findViewById(R.id.tview_name);
            imgPic                      =   (CircularImageView)v.findViewById(R.id.imgPic);
            imgAdd                      =   (ImageView)v.findViewById(R.id.addBtn);

        }
    }
}
