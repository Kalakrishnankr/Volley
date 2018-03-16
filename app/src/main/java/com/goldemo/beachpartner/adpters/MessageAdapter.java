package com.goldemo.beachpartner.adpters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.models.DataModel;
import com.goldemo.beachpartner.models.PersonModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 25/2/18.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    public Context mContext;
    private ArrayList<PersonModel> dataList;

    public MessageAdapter(Context context, ArrayList<PersonModel> allSampleData) {
        this.dataList = allSampleData;
        this.mContext = context;
    }

    @Override
    public MessageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MessageAdapter.MyViewHolder holder, int position) {

        PersonModel model = dataList.get(position);

        holder.txtvUname.setText(model.getUname());
        holder.imgUserPic.setBackgroundResource(model.getImage());

    }






    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtvMsg,txtvUname;
        public CardView cardView;
        private ImageView imgUserPic;

        public MyViewHolder(View vi) {
            super(vi);

            imgUserPic      =   (ImageView)vi.findViewById(R.id.imgUserpic);
            txtvUname       =   (TextView) vi.findViewById(R.id.txtv_uname);


        }
    }
}
