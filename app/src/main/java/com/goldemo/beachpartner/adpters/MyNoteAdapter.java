package com.goldemo.beachpartner.adpters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldemo.beachpartner.R;

/**
 * Created by seq-kala on 20/3/18.
 */

public class MyNoteAdapter extends RecyclerView.Adapter<MyNoteAdapter.MyViewHolder> {
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView   = LayoutInflater.from(parent.getContext()).inflate(R.layout.myevent_note_item,parent,false);
        MyViewHolder myViewHolder    = new MyViewHolder(itemView);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }



    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView my_pic;
        public TextView tv_name;
        public MyViewHolder(View itemView) {
            super(itemView);

            my_pic  =   (ImageView) itemView.findViewById(R.id.my_pic);
            tv_name =   (TextView)  itemView.findViewById(R.id.my_name);
        }
    }
}
