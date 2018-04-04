package com.goldemo.beachpartner.adpters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.fragments.ChatFragmentPage;
import com.goldemo.beachpartner.models.PersonModel;

import java.util.ArrayList;

/**
 * Created by seq-kala on 28/3/18.
 */

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    Context mContext;
    ArrayList<PersonModel>list;
    public ChatListAdapter(Context context, ArrayList<PersonModel> chatList) {
        this.mContext = context;
        this.list     = chatList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlayout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        PersonModel model = list.get(position);
        holder.person_pic.setImageResource(model.getImage());
        holder.txv_name.setText(model.getUname());
        holder.txtv_time.setText("5:00 pm");

        holder.single_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, "Clicked", Toast.LENGTH_SHORT).show();
                ChatFragmentPage chatFramentPage  = new ChatFragmentPage();
                FragmentManager manager = ((FragmentActivity)mContext).getSupportFragmentManager();
                FragmentTransaction ctrans = manager.beginTransaction();
                //ctrans.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                ctrans.replace(R.id.container,chatFramentPage);
                ctrans.addToBackStack(null);
                ctrans.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout single_item;
        public TextView txv_name,txtv_msg,txtv_activeStatus,txtv_time;
        public ImageView person_pic;

        public ViewHolder(View itemView) {
            super(itemView);

            single_item =   (LinearLayout) itemView.findViewById(R.id.item);
            txv_name    =   (TextView)     itemView.findViewById(R.id.pname);
            txtv_msg    =   (TextView)     itemView.findViewById(R.id.pmessage);
            txtv_activeStatus=(TextView)   itemView.findViewById(R.id.pactive_status);
            txtv_time   =   (TextView)     itemView.findViewById(R.id.txtv_time);
            person_pic  =   (ImageView)    itemView.findViewById(R.id.chatPic);
        }
    }
}
