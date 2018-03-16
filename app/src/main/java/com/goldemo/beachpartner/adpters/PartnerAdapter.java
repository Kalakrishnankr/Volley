package com.goldemo.beachpartner.adpters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.models.PersonModel;

import java.util.ArrayList;

/**
 * Created by user on 25/2/18.
 */

public class PartnerAdapter extends RecyclerView.Adapter<PartnerAdapter.MyViewHolder> {
    public Context mContext;
    private ArrayList<PersonModel> dataList;

    public PartnerAdapter(Context context, ArrayList<PersonModel> allSampleData) {
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
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final PersonModel model = dataList.get(position);

        holder.txtvPartnerName.setText(model.getUname());
        holder.imgPartnerPic.setBackgroundResource(model.getImage());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Clicked "+position, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtvPartnerName;
        public ImageView imgPartnerPic;
        public CardView card;
        public MyViewHolder(View vi) {
            super(vi);

            txtvPartnerName     =   (TextView)vi.findViewById(R.id.txtPartnerName);
            imgPartnerPic       =   (ImageView)vi.findViewById(R.id.imgPartnerpic);
            card                =   (CardView)vi.findViewById(R.id.cardview);
        }
    }
}
