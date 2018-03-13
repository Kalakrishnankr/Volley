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

import com.goldemo.beachpartner.MyInterface;
import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.fragments.NoteFragment;
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

        /*WindowManager windowmanager = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dimension = new DisplayMetrics();
        windowmanager.getDefaultDisplay().getMetrics(dimension);
         height = (int) ((dimension.heightPixels)/2.5);

        holder.cardView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {
                holder.cardView.getViewTreeObserver().removeOnPreDrawListener(this);
                minHeight = holder.cardView.getHeight();
                ViewGroup.LayoutParams layoutParams = holder.cardView.getLayoutParams();
                layoutParams.height = minHeight;
                holder.cardView.setLayoutParams(layoutParams);

                return true;
            }
        });*/

        if (dataList != null && !dataList.isEmpty()) {
            if (dataList.get(position).isExpanded) {
                holder.rrHeaderTwo.setVisibility(View.VISIBLE);
            } else {
                holder.rrHeaderTwo.setVisibility(View.GONE);
            }
//        }
            holder.topIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (dataList.get(position).isExpanded) {
                        holder.rrHeaderTwo.setVisibility(View.GONE);
                        //mAnimationManager.collapse(holder.rrHeaderTwo, 1000, -200);
                        ExpandOrCollapse.collapse(holder.rrHeaderTwo, 1000, -200);
//                    isExpanded = false;
                        dataList.get(position).isExpanded = false;
                    } else {
                        holder.rrHeaderTwo.setVisibility(View.VISIBLE);
                        ExpandOrCollapse.expand(holder.rrHeaderTwo, 1000, 200);
                        dataList.get(position).isExpanded = true;
                    }
                    //toggleCardViewnHeight(height,holder);
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

            holder.txtv_name.setText("RAMU");
            holder.txtv_age.setText("29");


        }

    }

    /*private void toggleCardViewnHeight(int height, ViewHolder holder) {
        if (holder.cardView.getHeight() == minHeight) {
            // expand

            expandView(height,holder);
            holder.txtv_block.setVisibility(View.VISIBLE);
            holder.txtv_notes.setVisibility(View.VISIBLE);
            holder.txtv_message.setVisibility(View.VISIBLE);
            holder.txtv_block.setText("BLOCK");
            holder.txtv_message.setText("MESSAGE");
            holder.txtv_notes.setText("NOTES");
            //'height' is the height of screen which we have measured already.

        } else {
            // collapse
            collapseView(holder);
            //mclickListener.clickview();

        }
    }*/

    /*private void collapseView(final ViewHolder holder) {


        ValueAnimator anim = ValueAnimator.ofInt(holder.cardView.getMeasuredHeightAndState(),
                minHeight);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = holder.cardView.getLayoutParams();
                layoutParams.height = val;
                holder.cardView.setLayoutParams(layoutParams);

            }
        });
        anim.start();

    }

    private void expandView(int height, final ViewHolder holder) {
        ValueAnimator anim = ValueAnimator.ofInt(holder.cardView.getMeasuredHeightAndState(),
                height);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = holder.cardView.getLayoutParams();
                layoutParams.height = val;
                holder.cardView.setLayoutParams(layoutParams);
            }
        });
        anim.start();

    }*/

  /*  private void showItems(ViewHolder holder) {
        isExpanded=true;
        holder.topIcon.setBackground(mContext.getResources().getDrawable(R.drawable.ic_info));
        holder.txtv_block.setVisibility(View.VISIBLE);
        holder.txtv_notes.setVisibility(View.VISIBLE);
        holder.txtv_message.setVisibility(View.VISIBLE);

        //
        holder.txtv_block.setText("BLOCK");
        holder.txtv_message.setText("MESSAGE");
        holder.txtv_notes.setText("NOTES");
    }*/



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