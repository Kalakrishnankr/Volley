package com.beachpartnerllc.beachpartner.adpters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.beachpartnerllc.beachpartner.CircularImageView;
import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.fragments.BPFinderFragment;
import com.beachpartnerllc.beachpartner.models.BpFinderModel;

import java.util.ArrayList;

/**
 * Created by seq-kala on 11/4/18.
 */

public class BlueBProfileAdapter extends RecyclerView.Adapter<BlueBProfileAdapter.ViewHolder> {
    public Context mContext;
    private ArrayList<BpFinderModel> dataList;
    private static boolean isPartner = false;

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
    public void onBindViewHolder(final ViewHolder holder, int position) {

        BpFinderModel model = dataList.get(position);
        Glide.with(mContext).load(dataList.get(position).getBpf_imageUrl()).into(holder.imv_profile);
        holder.imv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isblueBP = true;
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                BPFinderFragment bpFinderFragment =new BPFinderFragment(isblueBP,isPartner);
                Bundle bundle = new Bundle();
                //cPosition is the current positon
                bundle.putInt("cPosition", holder.getAdapterPosition());
                bundle.putSerializable("bluebplist", dataList);
                bpFinderFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, bpFinderFragment).commit();
            }
        });
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
