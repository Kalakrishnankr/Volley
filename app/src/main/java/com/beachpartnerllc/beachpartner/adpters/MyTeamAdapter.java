package com.beachpartnerllc.beachpartner.adpters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class MyTeamAdapter extends RecyclerView.Adapter<MyTeamAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<ConnectionModel>mList;
    private TeamMakerInterface makerInterface;
    public MyTeamAdapter(Context context, ArrayList<ConnectionModel> allSampleData,TeamMakerInterface teamMakerInterface) {
        this.mContext   = context;
        this.mList      = allSampleData;
        this.makerInterface = teamMakerInterface;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView   = LayoutInflater.from(parent.getContext()).inflate(R.layout.myteam_item,parent,false);
        MyViewHolder myViewHolder   =   new MyViewHolder(itemView);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        ConnectionModel model = mList.get(position);

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
        holder.tv_name.setText(model.getConnected_firstName());

        holder.imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makerInterface.onRemoveListener(mList.get(position),position);
            }
        });


    }



    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public  TextView tv_name;
        public  ImageView imgAdd,imgRemove;
        private CircularImageView imgPic;
        public  MyViewHolder(View itemView) {
            super(itemView);

            tv_name     =   (TextView) itemView.findViewById(R.id.tview_name);
            //imgAdd      =   (ImageView)itemView.findViewById(R.id.addBtn);
            imgRemove   =   (ImageView)itemView.findViewById(R.id.removeBtn);
            imgPic      =   (CircularImageView)itemView.findViewById(R.id.imgProfilePic);

        }
    }
}
