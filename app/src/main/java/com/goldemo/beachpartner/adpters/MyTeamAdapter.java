package com.goldemo.beachpartner.adpters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.models.PersonModel;

import java.util.ArrayList;

/**
 * Created by seq-kala on 9/3/18.
 */

public class MyTeamAdapter extends RecyclerView.Adapter<MyTeamAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<PersonModel>mList;
    public MyTeamAdapter(Context context, ArrayList<PersonModel> allSampleData) {
        this.mContext   = context;
        this.mList      = allSampleData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView   = LayoutInflater.from(parent.getContext()).inflate(R.layout.myteam_item,parent,false);
        MyViewHolder myViewHolder   =   new MyViewHolder(itemView);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        PersonModel model = mList.get(position);

        holder.imgPic.setImageResource(model.getImage());
        holder.tv_name.setText(model.getUname());


    }



    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_name;
        public ImageView imgAdd,imgRemove,imgPic;
        public MyViewHolder(View itemView) {
            super(itemView);

            tv_name     =   (TextView) itemView.findViewById(R.id.tview_name);
            imgAdd      =   (ImageView)itemView.findViewById(R.id.addBtn);
            imgRemove   =   (ImageView)itemView.findViewById(R.id.removeBtn);
            imgPic      =   (ImageView)itemView.findViewById(R.id.imgProfilePic);

        }
    }
}
