package com.goldemo.beachpartner.adpters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.models.DataModel;

import java.util.ArrayList;

/**
 * Created by user on 25/2/18.
 */

public class PartnerAdapter extends RecyclerView.Adapter<PartnerAdapter.MyViewHolder> {
    public Context mContext;
    private ArrayList<DataModel> dataList;

    public PartnerAdapter(Context context, ArrayList<DataModel> allSampleData) {
        this.dataList = allSampleData;
        this.mContext = context;
    }

    @Override
    public PartnerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.partner_item,parent,false);
        PartnerAdapter.MyViewHolder myViewHolder = new PartnerAdapter.MyViewHolder(itemView);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.txtvPartnerName.setText("Nick");
        holder.txtvPartnerMsg.setText("Text message");

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtvPartnerName,txtvPartnerMsg;
        public ImageView imgPartnerPic;

        public MyViewHolder(View vi) {
            super(vi);

            txtvPartnerName     =   (TextView)vi.findViewById(R.id.txtPartnerName);
            txtvPartnerMsg      =   (TextView)vi.findViewById(R.id.txtPartnerMsg);
            imgPartnerPic       =   (ImageView)vi.findViewById(R.id.imgPartnerpic);

        }
    }
}
