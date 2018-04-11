package com.goldemo.beachpartner.adpters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.goldemo.beachpartner.CircularImageView;
import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.models.BpFinderModel;

import java.util.ArrayList;

/**
 * Created by seq-kala on 11/4/18.
 */

public class BlueBProfileAdapter extends RecyclerView.Adapter<BlueBProfileAdapter.ViewHolder> {
    public Context mContext;
    private ArrayList<BpFinderModel> dataList;
    public BlueBProfileAdapter(Context context, ArrayList<BpFinderModel> dataList) {
        this.dataList=dataList;
        this.mContext=context;
    }


    @Override
    public BlueBProfileAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.bp_item_profile,parent,false);
        BlueBProfileAdapter.ViewHolder viewHolder = new BlueBProfileAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        BpFinderModel model = dataList.get(position);
        Glide.with(mContext).load(dataList.get(position).getBpf_imageUrl()).into(holder.imv_profile);
    }



    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public CircularImageView imv_profile;
        public ViewHolder(View view) {
            super(view);

            imv_profile =   (CircularImageView)view.findViewById(R.id.imgBp);

        }
    }
}
