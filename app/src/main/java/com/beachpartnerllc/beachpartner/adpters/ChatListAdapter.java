package com.beachpartnerllc.beachpartner.adpters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beachpartnerllc.beachpartner.CircularImageView;
import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.connections.PrefManager;
import com.beachpartnerllc.beachpartner.fragments.ChatFragmentPage;
import com.beachpartnerllc.beachpartner.fragments.MessageFragment;
import com.beachpartnerllc.beachpartner.models.ConnectionModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by seq-kala on 28/3/18.
 */

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    Context mContext;
    ArrayList<ConnectionModel>list;
    MessageFragment messageFragment;
    public ChatListAdapter(MessageFragment messageFragment,Context context, ArrayList<ConnectionModel> chatList) {
        this.mContext = context;
        this.list     = chatList;
        this.messageFragment = messageFragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlayout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        //holder.person_pic.setImageResource(model.getImage());
        final ConnectionModel message = list.get(position);

        holder.txv_name.setText(message.getConnected_firstName());
        if(!list.get(position).getConnected_imageUrl().equals("null")){
            Glide.with(mContext).load(list.get(position).getConnected_imageUrl()).into(holder.person_pic);
            //holder.profilePic.setImageURI(Uri.parse(dataLists.get(position).getConnected_imageUrl()));
        }else {
            holder.person_pic.setImageResource(R.drawable.ic_person);
        }

        holder.single_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, "Clicked", Toast.LENGTH_SHORT).show();

                ChatFragmentPage chatFragmentPage = new ChatFragmentPage();
                Bundle bundle = new Bundle();
                bundle.putString("personId", message.getConnected_uId());
                bundle.putString("personName",message.getConnected_firstName());
                bundle.putString("myName",new PrefManager(mContext).getUserName());
                bundle.putString("personPic",message.getConnected_imageUrl());
                chatFragmentPage.setArguments(bundle);
                FragmentManager manager = ((FragmentActivity)mContext).getSupportFragmentManager();
                FragmentTransaction ctrans = manager.beginTransaction();
                //ctrans.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                ctrans.replace(R.id.container,chatFragmentPage);
                ctrans.addToBackStack(null);
                ctrans.commit();
                disableTabMenuButtons();
            }
        });

    }

    private void disableTabMenuButtons(){
        messageFragment.disableMenu();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout single_item;
        public TextView txv_name,txtv_msg,txtv_activeStatus,txtv_time;
        public CircularImageView person_pic;

        public ViewHolder(View itemView) {
            super(itemView);

            single_item =   (LinearLayout) itemView.findViewById(R.id.item);
            txv_name    =   (TextView)     itemView.findViewById(R.id.pname);
            //txtv_msg    =   (TextView)     itemView.findViewById(R.id.pmessage);
            //txtv_activeStatus=(TextView)   itemView.findViewById(R.id.pactive_status);
            txtv_time   =   (TextView)     itemView.findViewById(R.id.txtv_time);
            person_pic  =   (CircularImageView)    itemView.findViewById(R.id.chatPic);
        }
    }
}
