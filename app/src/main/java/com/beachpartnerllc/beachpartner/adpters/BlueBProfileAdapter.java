package com.beachpartnerllc.beachpartner.adpters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beachpartnerllc.beachpartner.CircularImageView;
import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.fragments.BPFinderFragment;
import com.beachpartnerllc.beachpartner.models.BpFinderModel;
import com.beachpartnerllc.beachpartner.models.SwipeResultModel;
import com.beachpartnerllc.beachpartner.utils.AppConstants;
import com.beachpartnerllc.beachpartner.utils.OnRecyclerOnClickListener;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by seq-kala on 11/4/18.
 */

public class BlueBProfileAdapter extends RecyclerView.Adapter<BlueBProfileAdapter.ViewHolder> {
    public Context mContext;
    private ArrayList<SwipeResultModel> dataList;
    private static boolean isPartner = false;


    private OnRecyclerOnClickListener onRecyclerOnClickListener;

    public BlueBProfileAdapter(Context context, ArrayList<SwipeResultModel> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    public void setOnRecyclerOnClickListener(OnRecyclerOnClickListener onRecyclerOnClickListener) {
        this.onRecyclerOnClickListener = onRecyclerOnClickListener;
    }

    @Override
    public BlueBProfileAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bp_item_profile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        BpFinderModel model = dataList.get(position).getBpFinderModel();
        Glide.with(mContext).load(model.getBpf_imageUrl()).into(holder.imv_profile);
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public CircularImageView imv_profile;

        public ViewHolder(View view) {
            super(view);

            imv_profile = (CircularImageView) view.findViewById(R.id.imgBp);
            itemView.setOnClickListener(this);
        }

        public void removeAt(int position) {
            dataList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, dataList.size());
        }

        @Override
        public void onClick(View view) {
            BpFinderModel bpFinderModel = dataList.get(getAdapterPosition()).getBpFinderModel();
            if (onRecyclerOnClickListener != null) {

                onRecyclerOnClickListener.onItemClick(bpFinderModel, getAdapterPosition());
            } else {
                boolean isblueBP = true;
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                BPFinderFragment bpFinderFragment = new BPFinderFragment(isblueBP, isPartner);
                Bundle bundle = new Bundle();
                bundle.putParcelable(AppConstants.BP_PROFILE, bpFinderModel);
                bpFinderFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, bpFinderFragment).commit();
            }
        }
    }
}

