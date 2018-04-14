package com.goldemo.beachpartner.adpters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goldemo.beachpartner.CircularImageView;
import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.connections.PrefManager;
import com.goldemo.beachpartner.fragments.ChatFragmentPage;
import com.goldemo.beachpartner.models.ConnectionModel;

import java.util.ArrayList;

/**
 * Created by user on 25/2/18.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    public Context mContext;
    private PrefManager prefManager;
    private String userType;
    private ArrayList<ConnectionModel> dataList;
    MyViewHolder myViewHolder;

    public MessageAdapter(Context context, ArrayList<ConnectionModel> allSampleData) {
        this.dataList = allSampleData;
        this.mContext = context;
    }

    @Override
    public MessageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        prefManager = new PrefManager(mContext);
        userType    = prefManager.getUserType();
        if(userType.equalsIgnoreCase("Athlete")){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item,parent,false);
             myViewHolder = new MyViewHolder(itemView);

        }
        if(userType.equalsIgnoreCase("Coach")){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.coach_message_home_layout,parent,false);
            myViewHolder = new MyViewHolder(itemView);
        }

        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(MessageAdapter.MyViewHolder holder, final int position) {

        final ConnectionModel model = dataList.get(position);

        holder.txtvUname.setText(model.getConnected_firstName());
        if(!dataList.get(position).getConnected_imageUrl().equals("null")){
            Glide.with(mContext).load(dataList.get(position).getConnected_imageUrl()).into(holder.imgUserPic);
            //holder.profilePic.setImageURI(Uri.parse(dataLists.get(position).getConnected_imageUrl()));
        }else {
            holder.imgUserPic.setImageResource(R.drawable.ic_person);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatFragmentPage chatFragmentPage = new ChatFragmentPage();
                Bundle bundle = new Bundle();
                bundle.putString("personId", dataList.get(position).getConnected_uId());
                bundle.putString("personName",dataList.get(position).getConnected_firstName());
                bundle.putString("myName",new PrefManager(mContext).getUserName());
                chatFragmentPage.setArguments(bundle);
                FragmentManager manager = ((FragmentActivity)mContext).getSupportFragmentManager();
                FragmentTransaction ctrans = manager.beginTransaction();
                //ctrans.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                ctrans.replace(R.id.container,chatFragmentPage);
                ctrans.addToBackStack(null);
                ctrans.commit();
            }
        });

    }






    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public  TextView txtvMsg,txtvUname;
        public  CardView cardView;
        private CircularImageView imgUserPic;

        public MyViewHolder(View vi) {
            super(vi);

            cardView        =   (CardView)vi.findViewById(R.id.card);
            imgUserPic      =   (CircularImageView)vi.findViewById(R.id.imgUserpic);
            txtvUname       =   (TextView) vi.findViewById(R.id.txtv_uname);


        }
    }
}
