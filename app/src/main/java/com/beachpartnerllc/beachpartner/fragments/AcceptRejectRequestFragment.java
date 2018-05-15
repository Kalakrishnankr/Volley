package com.beachpartnerllc.beachpartner.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.adpters.SuggestionAdapter;

import java.util.ArrayList;


public class AcceptRejectRequestFragment extends Fragment {

    private RecyclerView rcv_requests;
    private TextView eventTitle,eventStart,eventEnd;
    private SuggestionAdapter suggestionAdapter;
    private ArrayList<String>suggestionList = new ArrayList<>();
    private LinearLayout undoLayout;
    private Paint p = new Paint();
    private int position;
    private String word;


    public AcceptRejectRequestFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accept_reject_request, container, false);
        initviews(view);
        return view;
    }

    private void initviews(View view) {

        eventTitle   = view.findViewById(R.id.event_title);
        eventStart   = view.findViewById(R.id.txtevent_start);
        eventEnd     = view.findViewById(R.id.txtevent_end);
        undoLayout   = view.findViewById(R.id.layout_undo);
        rcv_requests  = view.findViewById(R.id.rcv_requests);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        suggestionList.add("Australia");
        suggestionList.add("India");
        suggestionList.add("United States of America");
        suggestionList.add("Germany");
        suggestionList.add("Russia");

        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        suggestionAdapter = new SuggestionAdapter(getContext(),suggestionList);
        rcv_requests.setLayoutManager(manager);
        rcv_requests.setAdapter(suggestionAdapter);
        suggestionAdapter.notifyDataSetChanged();
        initswipe();


        undoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                undoLayout.setVisibility(View.GONE);
                //suggestionAdapter.addItem();
                suggestionAdapter.notifyDataSetChanged();
            }
        });


    }

    private void initswipe() {

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback= new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                position =  viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.LEFT) {
                    //Toast.makeText(getActivity(), "Left swiped", Toast.LENGTH_SHORT).show();
                    suggestionAdapter.removeItem(position);
                    undoLayout.setVisibility(View.VISIBLE);
                }
                if (direction == ItemTouchHelper.RIGHT) {
                    //Toast.makeText(getActivity(), "Right swiped", Toast.LENGTH_SHORT).show();
                    suggestionAdapter.removeItem(position);
                    undoLayout.setVisibility(View.VISIBLE);

                }

            }


        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            Bitmap icon;
            if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                View itemView = viewHolder.itemView;
                float height = (float) itemView.getBottom() - (float) itemView.getTop();
                float width = height / 3;

                if(dX > 0){
                    p.setColor(Color.parseColor("#388E3C"));
                    RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                    c.drawRect(background,p);
                    icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_edit);
                    RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                    c.drawBitmap(icon,null,icon_dest,p);
                } else {
                    p.setColor(Color.parseColor("#D32F2F"));
                    RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                    c.drawRect(background,p);
                    icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_edit);
                    RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                    c.drawBitmap(icon,null,icon_dest,p);
                }
            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rcv_requests);
    }

}
