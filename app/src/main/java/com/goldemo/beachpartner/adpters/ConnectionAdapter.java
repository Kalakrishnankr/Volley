package com.goldemo.beachpartner.adpters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goldemo.beachpartner.MyInterface;
import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.models.DataModel;

import java.util.ArrayList;

/**
 * Created by seq-kala on 23/2/18.
 */

public class ConnectionAdapter extends RecyclerView.Adapter<ConnectionAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<DataModel> dataList;
    //    public static boolean isExpanded =false;
    int minHeight;
    int height;
    MyInterface mclickListener;
    public ExpandOrCollapse mAnimationManager;



    public ConnectionAdapter(Context context, ArrayList<DataModel> allSampleData) {
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

        if (dataList != null && !dataList.isEmpty()) {
            if (dataList.get(position).isExpanded) {
                holder.rrHeaderTwo.setVisibility(View.VISIBLE);
            } else {
                holder.rrHeaderTwo.setVisibility(View.GONE);
            }
            holder.topIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (dataList.get(position).isExpanded) {
                        mAnimationManager.collapse(holder.rrHeaderTwo, 1000, -200);
                        holder.rrHeaderTwo.setVisibility(View.GONE);
                        dataList.get(position).isExpanded = false;
                    } else {
                        mAnimationManager.expand(holder.rrHeaderTwo, 1000, 200);
                        holder.rrHeaderTwo.setVisibility(View.VISIBLE);
                        dataList.get(position).isExpanded = true;
                    }

                    notifyDataSetChanged();


                }
            });

            holder.txtv_name.setText("RAMU");
            holder.txtv_age.setText("29");


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

        public ViewHolder(View view) {
            super(view);

            topIcon     = view.findViewById(R.id.top_icon);
            txtv_name   = view.findViewById(R.id.name);
            txtv_age    = view.findViewById(R.id.age);
            txtv_block  = view.findViewById(R.id.block);
            txtv_message= view.findViewById(R.id.message);
            txtv_notes  = view.findViewById(R.id.notes);
            cardView    = view.findViewById(R.id.card_view);



            rrHeaderOne = view.findViewById(R.id.rlHeader1);


            rrHeaderTwo = view.findViewById(R.id.rlHeader2);
        }
    }


}