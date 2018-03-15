package com.goldemo.beachpartner.adpters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.models.NoteDataModel;

import java.util.ArrayList;

/**
 * Created by Owner on 3/12/2018.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.deleteNotes.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                holder.removeAt(position);
                Toast.makeText(mContext, "Hi"+position , Toast.LENGTH_SHORT).show();


            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder  {
        public EditText edit_notes;
        public ImageView topIcon,noteDisabled,deleteNotes;
        public CardView cardView;


        public ViewHolder(View view) {
            super(view);


            noteDisabled= view.findViewById(R.id.note_disabled);
            edit_notes  = view.findViewById(R.id.name);
            cardView    = view.findViewById(R.id.card_view);
            deleteNotes = view.findViewById(R.id.delete_note);
            edit_notes.setVerticalScrollBarEnabled(true);



        }

        public void removeAt(int position) {
            dataList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, dataList.size());
        }
    }

}
