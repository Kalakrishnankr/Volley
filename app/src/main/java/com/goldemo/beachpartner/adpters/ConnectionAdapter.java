package com.goldemo.beachpartner.adpters;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.goldemo.beachpartner.fragments.NoteFragment;
import com.goldemo.beachpartner.models.PersonModel;

import java.util.ArrayList;

/**
 * Created by seq-kala on 23/2/18.
 */

public class ConnectionAdapter extends RecyclerView.Adapter<ConnectionAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<PersonModel> dataList;
    //    public static boolean isExpanded =false;

    public ExpandOrCollapse mAnimationManager;



    public ConnectionAdapter(Context context, ArrayList<PersonModel> allSampleData) {
        this.mContext=context;
        this.dataList=allSampleData;
    }

    @Override
    public ConnectionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.connection_card,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {



        PersonModel model =dataList.get(position);

        if (dataList != null && !dataList.isEmpty()) {
            if (dataList.get(position).isExpanded) {
                //mAnimationManager.expand(holder.rrHeaderTwo, 1000, 250);

                holder.rrHeaderTwo.setVisibility(View.VISIBLE);
            } else {
                //mAnimationManager.expand(holder.rrHeaderTwo, 1000, -250);

                holder.rrHeaderTwo.setVisibility(View.GONE);
            }
            holder.topIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (dataList.get(position).isExpanded) {
                        //Toast.makeText(mContext, "Collapse", Toast.LENGTH_SHORT).show();
                        ///mAnimationManager.collapse(holder.rrHeaderTwo, 1000, -300);

                        holder.rrHeaderTwo.setVisibility(View.GONE);
                        dataList.get(position).isExpanded = false;
                    } else {
                       // Toast.makeText(mContext, "Expand", Toast.LENGTH_SHORT).show();
                        //mAnimationManager.expand(holder.rrHeaderTwo, 1000, 300);
                        holder.rrHeaderTwo.setVisibility(View.VISIBLE);
                        dataList.get(position).isExpanded = true;
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

            holder.txtv_name.setText(dataList.get(position).getUname());
            holder.txtv_age.setText("Age :"+dataList.get(position).getAge());
            holder.profilePic.setImageResource(dataList.get(position).getImage());



        }

    }



    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtv_name,txtv_age,txtv_block,txtv_message,txtv_notes;
        public ImageView topIcon;
        public CardView cardView;
        public RelativeLayout rrHeaderTwo,rrHeaderOne;
        public CircularImageView profilePic;

        public ViewHolder(View view) {
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