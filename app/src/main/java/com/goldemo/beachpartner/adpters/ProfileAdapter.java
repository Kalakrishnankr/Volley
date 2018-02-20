package com.goldemo.beachpartner.adpters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.models.DataModel;

import java.util.ArrayList;

/**
 * Created by seq-kala on 20/2/18.
 */

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    public Context mContext;
    private ArrayList<DataModel> dataList;
    public ProfileAdapter(Context context, ArrayList<DataModel> dataList) {
        this.dataList=dataList;
        this.mContext=context;
    }


    @Override
    public ProfileAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //holder.imv_profile.setImageDrawable();

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imv_profile;
        public ViewHolder(View view) {
            super(view);

            imv_profile =   (ImageView)view.findViewById(R.id.imgProfile);

        }
    }
}
