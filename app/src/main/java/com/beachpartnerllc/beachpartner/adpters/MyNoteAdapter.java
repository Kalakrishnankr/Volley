package com.beachpartnerllc.beachpartner.adpters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.models.PartnerResultModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by seq-kala on 20/3/18.
 */

public class MyNoteAdapter extends RecyclerView.Adapter<MyNoteAdapter.MyViewHolder> {
    Context mContext;
    private ArrayList<PartnerResultModel> partnerResultModel = new ArrayList<>();


    public MyNoteAdapter(Context context,ArrayList<PartnerResultModel> dataModelList) {
        this.mContext =context;
        this.partnerResultModel = dataModelList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView   = LayoutInflater.from(parent.getContext()).inflate(R.layout.myevent_note_item,parent,false);
        MyViewHolder myViewHolder    = new MyViewHolder(itemView);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if(partnerResultModel!=null){
            holder.tv_name.setText(partnerResultModel.get(position).getPartnerName());
            Glide.with(mContext).load(partnerResultModel.get(position).getPartnerImageUrl()).into(holder.my_pic);
        }
        notifyDataSetChanged();

    }



    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView my_pic;
        public TextView tv_name;

        public MyViewHolder(View itemView) {
            super(itemView);

            my_pic  =   (ImageView) itemView.findViewById(R.id.my_pic);
            tv_name =   (TextView)  itemView.findViewById(R.id.my_name);
        }
    }
}
