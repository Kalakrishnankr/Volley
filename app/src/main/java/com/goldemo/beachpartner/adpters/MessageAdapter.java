package com.goldemo.beachpartner.adpters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.models.DataModel;

import java.util.ArrayList;

/**
 * Created by user on 25/2/18.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    public Context mContext;
    private ArrayList<DataModel> dataList;

    public MessageAdapter(Context context, ArrayList<DataModel> allSampleData) {
        this.dataList = allSampleData;
        this.mContext = context;
    }

    @Override
    public MessageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MessageAdapter.MyViewHolder holder, int position) {
        holder.txtv_date.setText("11/10/2018");
        holder.txtv_place.setText("america");
        holder.txtv_players.setText("Martin,David.john,Hari");
    }






    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtv_date,txtv_place,txtv_players;
        public CardView cardView;

        public MyViewHolder(View vi) {
            super(vi);

            txtv_date       =   (TextView) vi.findViewById(R.id.txtv_date);
            txtv_place      =   (TextView) vi.findViewById(R.id.txtv_place);
            txtv_players    =   (TextView) vi.findViewById(R.id.txtv_players);
            cardView        =   (CardView) vi.findViewById(R.id.card_view);


        }
    }
}
