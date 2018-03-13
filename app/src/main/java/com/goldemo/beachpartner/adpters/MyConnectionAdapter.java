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
 * Created by seq-kala on 9/3/18.
 */

public class MyConnectionAdapter extends RecyclerView.Adapter<MyConnectionAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<DataModel>dataModelList;

    public MyConnectionAdapter(Context context, ArrayList<DataModel> allSampleData) {

        this.mContext       =   context;
        this.dataModelList  =   allSampleData;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.mconnection_item,parent,false);
        MyViewHolder myViewHolder  = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }



    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvname;
        public ImageView imgPic,imgAdd;
        public MyViewHolder(View v) {
            super(v);

            tvname      =   (TextView)v.findViewById(R.id.tview_name);
            imgPic      =   (ImageView)v.findViewById(R.id.imgPic);
            imgAdd      =   (ImageView)v.findViewById(R.id.addBtn);

        }
    }
}
