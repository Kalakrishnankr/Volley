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

import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.models.NoteDataModel;

import java.util.ArrayList;

/**
 * Created by Owner on 3/12/2018.
 */

public class NotesAdapter extends RecyclerView.Adapter {
    private ArrayList<NoteDataModel> dataList;
    Context mContext;
    public NotesAdapter(Context context, ArrayList<NoteDataModel> allSampleData) {
        this.mContext=context;
        this.dataList=allSampleData;

    }

    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_card,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

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
