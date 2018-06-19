package com.beachpartnerllc.beachpartner.adpters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.models.BenefitModel;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by seq-kala on 4/6/18.
 */

public class BenefitListItemAdapter extends RecyclerView.Adapter<BenefitListItemAdapter.MyViewHolder>{
    private Context mContext;
    private List<BenefitModel>modelList;

    public BenefitListItemAdapter(Context context, List<BenefitModel> benefitModelList) {
        this.mContext=context;
        this.modelList=benefitModelList;
    }

    @Override
    public BenefitListItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.benefit_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(BenefitListItemAdapter.MyViewHolder holder, int position) {
        BenefitModel  benefitModel= modelList.get(position);

        holder.tv_title.setText(benefitModel.getBenefitName());
        holder.tv_itemNotes.setText(benefitModel.getUserNote());
        if (benefitModel.getBenefitStatus() != null) {
            if (!benefitModel.getBenefitStatus().equalsIgnoreCase("null")) {
                if (benefitModel.getBenefitStatus().equalsIgnoreCase("Available") ||
                        benefitModel.getBenefitStatus().equalsIgnoreCase("Limited")   ) {
                    holder.img_status.setBackground(null);
                    Glide.with(mContext).load(R.drawable.tick).into(holder.img_status);
                }else if(benefitModel.getBenefitStatus().equalsIgnoreCase("NA")) {
                    holder.img_status.setBackground(null);
                    Glide.with(mContext).load(R.drawable.wrong).into(holder.img_status);
                }else {

                }

            }
        }

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title,tv_itemNotes;
        ImageView img_status;
        public MyViewHolder(View itemView) {
            super(itemView);

            tv_title   = itemView.findViewById(R.id.tv_title);
            img_status = itemView.findViewById(R.id.img_check);
            tv_itemNotes= itemView.findViewById(R.id.tv_note);
        }
    }
}
