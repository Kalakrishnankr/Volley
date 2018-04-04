package com.goldemo.beachpartner.adpters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goldemo.beachpartner.CircularImageView;
import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.fragments.ChatFragmentPage;
import com.goldemo.beachpartner.fragments.NoteFragment;
import com.goldemo.beachpartner.models.ConnectionModel;

import java.util.ArrayList;

/**
 * Created by seq-kala on 23/2/18.
 */

public class ConnectionAdapter extends RecyclerView.Adapter<ConnectionAdapter.MyViewHolder> {

    public Context mContext;
    private ArrayList<ConnectionModel> dataLists;
    //    public static boolean isExpanded =false;

    public ExpandOrCollapse mAnimationManager;



    public ConnectionAdapter(Context context, ArrayList<ConnectionModel> allSampleData) {
        this.dataLists=allSampleData;
        this.mContext=context;
    }

    @Override
    public ConnectionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.connection_card,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }



    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        final ConnectionModel model =dataLists.get(position);

        if (dataLists != null && !dataLists.isEmpty()) {
            if (dataLists.get(position).isExpanded) {
                //mAnimationManager.expand(holder.rrHeaderTwo, 1000, 250);

                holder.rrHeaderTwo.setVisibility(View.VISIBLE);
            } else {
                //mAnimationManager.expand(holder.rrHeaderTwo, 1000, -250);

                holder.rrHeaderTwo.setVisibility(View.GONE);
            }
            holder.topIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (model.isExpanded) {
                        //Toast.makeText(mContext, "Collapse", Toast.LENGTH_SHORT).show();
                        ///mAnimationManager.collapse(holder.rrHeaderTwo, 1000, -300);

                        holder.rrHeaderTwo.setVisibility(View.GONE);
                        model.isExpanded = false;
                    } else {
                        // Toast.makeText(mContext, "Expand", Toast.LENGTH_SHORT).show();
                        //mAnimationManager.expand(holder.rrHeaderTwo, 1000, 300);
                        holder.rrHeaderTwo.setVisibility(View.VISIBLE);
                        model.isExpanded = true;
                    }

                    notifyDataSetChanged();


                }
            });

            holder.txtv_notes.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onClick(View v) {

                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    NoteFragment noteFragment =new NoteFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, noteFragment).addToBackStack(null).commit();

                }
            });

            holder.txtv_name.setText(dataLists.get(position).getConnected_firstName());
            //holder.txtv_age.setText("Age :"+dataList.get(position).getAge());
//            holder.profilePic.setImageResource(Integer.parseInt(dataLists.get(position).getConnected_imageUrl()));

            //Set OnclickListener message

            holder.txtv_message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ChatFragmentPage chatFragmentPage = new ChatFragmentPage();
                    Bundle bundle = new Bundle();
                    bundle.putString("personId", model.getConnected_uId());
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

    }



    @Override
    public int getItemCount() {
        return dataLists.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtv_name,txtv_age,txtv_block,txtv_message,txtv_notes;
        public ImageView topIcon;
        public CardView cardView;
        public RelativeLayout rrHeaderTwo,rrHeaderOne;
        public CircularImageView profilePic;

        public MyViewHolder(View view) {
            super(view);

            topIcon     = (ImageView)view.findViewById(R.id.top_icon);
            txtv_name   = (TextView)view.findViewById(R.id.name);
            txtv_age    = (TextView)view.findViewById(R.id.age);
            txtv_block  = (TextView)view.findViewById(R.id.block);
            txtv_message= (TextView)view.findViewById(R.id.message);
            txtv_notes  = (TextView)view.findViewById(R.id.notes);
            cardView    = (CardView)view.findViewById(R.id.card_view);

            profilePic  = (CircularImageView)view.findViewById(R.id.thumbnail);


            rrHeaderOne = (RelativeLayout) view.findViewById(R.id.rlHeader1);


            rrHeaderTwo = (RelativeLayout) view.findViewById(R.id.rlHeader2);
        }
    }


}