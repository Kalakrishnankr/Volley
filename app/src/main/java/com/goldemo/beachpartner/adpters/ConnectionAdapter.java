package com.goldemo.beachpartner.adpters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.models.DataModel;

import java.util.ArrayList;

/**
 * Created by seq-kala on 23/2/18.
 */

public class ConnectionAdapter extends RecyclerView.Adapter<ConnectionAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<DataModel> dataList;
    public static boolean isShow=false;


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
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.topIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isShow){
                    isShow=false;
                    holder.txtv_block.setVisibility(View.GONE);
                    holder.txtv_notes.setVisibility(View.GONE);
                    holder.txtv_message.setVisibility(View.GONE);
                }else {
                    showItems(holder);
                }

            }
        });

        holder.txtv_name.setText("RAMU");
        holder.txtv_age.setText("29");

    }

    private void showItems(ViewHolder holder) {
        isShow=true;
        holder.topIcon.setBackground(mContext.getResources().getDrawable(R.drawable.ic_info));
        holder.txtv_block.setVisibility(View.VISIBLE);
        holder.txtv_notes.setVisibility(View.VISIBLE);
        holder.txtv_message.setVisibility(View.VISIBLE);

        //
        holder.txtv_block.setText("BLOCK");
        holder.txtv_message.setText("MESSAGE");
        holder.txtv_notes.setText("NOTES");
    }



    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtv_name,txtv_age,txtv_block,txtv_message,txtv_notes;
        public ImageView topIcon;

        public ViewHolder(View view) {
            super(view);

            topIcon     =   (ImageView)view.findViewById(R.id.top_icon);
            txtv_name   =   (TextView)view.findViewById(R.id.name);
            txtv_age    =   (TextView)view.findViewById(R.id.age);
            txtv_block  =   (TextView)view.findViewById(R.id.block);
            txtv_message=   (TextView)view.findViewById(R.id.message);
            txtv_notes  =   (TextView)view.findViewById(R.id.notes);

        }
    }
}
