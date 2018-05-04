package com.beachpartnerllc.beachpartner.adpters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;

import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.activity.TabActivity;
import com.beachpartnerllc.beachpartner.fragments.NoteFragment;
import com.beachpartnerllc.beachpartner.models.NoteDataModel;
import com.beachpartnerllc.beachpartner.utils.SaveNoteInterface;
import com.github.curioustechizen.ago.RelativeTimeTextView;

import java.util.ArrayList;

/**
 * Created by Owner on 3/12/2018.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    Context mContext;
    private ArrayList<NoteDataModel> dataList;
    private SaveNoteInterface noteInterface;
    private static boolean isEditable=false;
    TabActivity tabActivity;

    private String text,noteId;

    public NotesAdapter(Context context, ArrayList<NoteDataModel> allSampleData, NoteFragment noteFragment) {
        this.mContext = context;
        this.dataList = allSampleData;
        this.noteInterface = noteFragment;
    }

    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mContext instanceof TabActivity) {
            tabActivity = (TabActivity)mContext;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        final NoteDataModel model = dataList.get(position);
        holder.edit_notes.setFocusable(false);
        holder.edit_notes.setFocusableInTouchMode(false);
        holder.edit_notes.setText(model.getNotes().trim());
        holder.deleteNotes.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                holder.removeAt(position);
                noteInterface.removeNote(model.getNote_id());
            }
        });
        //holder.timeStamp.setReferenceTime(new Date().getTime());
        holder.edit_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.edit_notes.setFocusable(true);
                holder.edit_notes.setFocusableInTouchMode(true);
            }
        });



        holder.edit_notes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.length() != 0) {
                    holder.noteDisabled.setImageResource(R.drawable.ic_note_viewed);
                } else {
                    holder.noteDisabled.setImageResource(R.drawable.ic__note);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        holder.noteDisabled.setFocusable(true);
        holder.noteDisabled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                noteId = model.getNote_id();
                text = holder.edit_notes.getText().toString();
                if (noteId != null && text.length() != 0) {
                    noteInterface.update(text,noteId);
                    holder.noteDisabled.setImageResource(R.drawable.ic__note);
                    holder.edit_notes.setFocusable(false);
                }


            }
        });

        //delete note

        holder.edit_notes.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (keyboardShown(holder.edit_notes.getRootView())) {
                    Log.d("keyboard", "keyboard UP");
                    tabActivity.navigation.setVisibility(View.GONE);
                } else {
                    Log.d("keyboard", "keyboard Down");
                    tabActivity.navigation.setVisibility(View.VISIBLE);

                }
            }
        });





    }

    private boolean keyboardShown(View rootView) {

        final int softKeyboardHeight = 100;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int heightDiff = rootView.getBottom() - r.bottom;
        return heightDiff > softKeyboardHeight * dm.density;
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public EditText edit_notes;
        public ImageView noteDisabled, deleteNotes;
        public RelativeTimeTextView timeStamp;
        public CardView cardView;


        public ViewHolder(View view) {
            super(view);



            noteDisabled = view.findViewById(R.id.note_disabled);
            edit_notes = view.findViewById(R.id.name);
            cardView = view.findViewById(R.id.card_view);
            deleteNotes = view.findViewById(R.id.delete_note);
            timeStamp = view.findViewById(R.id.time_stamp);
            edit_notes.setVerticalScrollBarEnabled(true);


        }

//
//        public void onItemClick(View v) {
//            if(v.equals(noteDisabled)){
//                removeAt(getAdapterPosition());
//                Toast.makeText(mContext, "Hi removed at" +getAdapterPosition(), Toast.LENGTH_SHORT).show();
//            }
//        }


        public void removeAt(int position) {
            dataList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, dataList.size());
        }



    }

}
