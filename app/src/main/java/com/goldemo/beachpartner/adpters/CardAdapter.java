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
 * Created by seq-kala on 20/2/18.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {
    public Context mContext;
    private ArrayList<DataModel> dataList;

    public CardAdapter(Context context,ArrayList<DataModel> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }
    @Override
    public CardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView);
        itemView.setMinimumWidth(parent.getMeasuredWidth());

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(CardAdapter.MyViewHolder holder, int position) {
        holder.txtv_date.setText("11/10/2018");
        holder.txtv_place.setText("America");
        holder.txtv_players.setText("Martin, David, John, Hari");

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtv_date,txtv_place,txtv_players;
        public CardView cardView;

        public MyViewHolder(View v) {
            super(v);

            txtv_date       =   (TextView) v.findViewById(R.id.txtv_date);
            txtv_place      =   (TextView) v.findViewById(R.id.txtv_place);
            txtv_players    =   (TextView) v.findViewById(R.id.txtv_players);
            cardView        =   (CardView) v.findViewById(R.id.card_view);


        }
    }

    // private ArrayList<SingleItemModel> itemsList;

}
