package com.beachpartnerllc.beachpartner.adpters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beachpartnerllc.beachpartner.CircularImageView;
import com.beachpartnerllc.beachpartner.ConnectionInterface;
import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.fragments.ConnectionFragment;
import com.beachpartnerllc.beachpartner.models.BpFinderModel;
import com.beachpartnerllc.beachpartner.models.Coach.ConnectionResultModel;
import com.beachpartnerllc.beachpartner.utils.AppConstants;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seq-kala on 23/2/18.
 */

public class ConnectionAdapter extends RecyclerView.Adapter<ConnectionAdapter.MyViewHolder> {

    public Context mContext;
    private ArrayList<ConnectionResultModel> dataLists;
    public ConnectionInterface connectionInterface;
    private ArrayList<BpFinderModel> mCountryModel;

    //    public static boolean isExpanded =false;

    public ExpandOrCollapse mAnimationManager;



    public ConnectionAdapter(Context context, ArrayList<ConnectionResultModel> allSampleData, ConnectionFragment connectionFragment) {
        this.dataLists=allSampleData;
        this.mContext=context;
        this.connectionInterface=connectionFragment;
    }

    @Override
    public ConnectionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.connection_card,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }



    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        final ConnectionResultModel model =dataLists.get(position);

        if (dataLists != null && !dataLists.isEmpty()) {
            if (dataLists.get(position).isExpanded) {
                //mAnimationManager.expand(holder.rrHeaderTwo, 1000, 250);

                holder.rrHeaderTwo.setVisibility(View.VISIBLE);
            } else {
                //mAnimationManager.expand(holder.rrHeaderTwo, 1000, -250);

                holder.rrHeaderTwo.setVisibility(View.GONE);
            }
            holder.topIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (model.isExpanded) {
                        //Toast.makeText(mContext, "Collapse", Toast.LENGTH_SHORT).show();
                        ///mAnimationManager.collapse(holder.rrHeaderTwo, 1000, -300);

                        holder.rrHeaderTwo.setVisibility(View.GONE);
                        model.isExpanded = false;
                    } else {
                        // Toast.makeText(mContext, "Expand", Toast.LENGTH_SHORT).show();
                        //mAnimationManager.expand(holder.rrHeaderTwo, 1000, 300);
                        holder.rrHeaderTwo.setVisibility(View.VISIBLE);
                        model.isExpanded = true;
                    }

                    notifyDataSetChanged();


                }
            });

            holder.txtv_notes.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onClick(View v) {

                    Bundle bundle = new Bundle();
                    bundle.putString("personId",model.getBpFinderModel().getBpf_id());
                    bundle.putString("personName",model.getBpFinderModel().getBpf_firstName());
                    connectionInterface.connectionToNote(bundle);



                }
            });

            holder.txtv_name.setText(dataLists.get(position).getBpFinderModel().getBpf_firstName().trim());

            if(dataLists.get(position).getBpFinderModel().getBpf_imageUrl() != null){
                if(!dataLists.get(position).getBpFinderModel().getBpf_imageUrl().equalsIgnoreCase("NULL"))
                    Glide.with(mContext).load(dataLists.get(position).getBpFinderModel().getBpf_imageUrl()).into(holder.profilePic);
                //holder.profilePic.setImageURI(Uri.parse(dataLists.get(position).getConnected_imageUrl()));
            }else {
                holder.profilePic.setImageResource(R.drawable.ic_person);
            }

            //Set OnclickListener message
            holder.txtv_message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle = new Bundle();
                    bundle.putParcelable(AppConstants.CHAT_USER,model.getBpFinderModel());
                    connectionInterface.transition(bundle);

                }
            });

            final String active_status = model.getStatus() ;
            if (!active_status.equals("null") && !active_status.isEmpty()) {
                if(active_status.equalsIgnoreCase("Blocked")){
                    holder.txtv_block.setText("UNBLOCK");
                    holder.viewOne.setVisibility(View.GONE);
                    holder.txtv_message.setVisibility(View.GONE);
                    holder.cardView.setBackgroundResource(R.color.lightGrey);

                }else  {
                    holder.txtv_block.setText("BLOCK");
                    holder.viewOne.setVisibility(View.VISIBLE);
                    holder.txtv_message.setVisibility(View.VISIBLE);
                    holder.cardView.setBackgroundResource(R.color.white);
                }
            }

            //set onclicklister block
            holder.txtv_block.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //unblock api
                    if (!active_status.isEmpty() && active_status.equalsIgnoreCase("Blocked")) {
                        String personid = model.getBpFinderModel().getBpf_id();
                        connectionInterface.unblock(personid,model.getBpFinderModel().getBpf_firstName());
                        holder.ItemChanged(position);
                        //block
                    }else if(!active_status.isEmpty() && active_status.equalsIgnoreCase("Active")) {
                        String personid = model.getBpFinderModel().getBpf_id();
                        connectionInterface.block(personid,model.getBpFinderModel().getBpf_firstName());
                        holder.reMovePosition(position);
                    }

                }
            });



        }

    }

    @Override
    public int getItemCount() {
        return dataLists.size();
    }

    public void setFilter(List<BpFinderModel> countryModels) {
        mCountryModel = new ArrayList<>();
        mCountryModel.addAll(countryModels);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtv_name,txtv_age,txtv_block,txtv_message,txtv_notes;
        public ImageView topIcon;
        public CardView cardView;
        public RelativeLayout rrHeaderTwo,rrHeaderOne;
        public CircularImageView profilePic;
        public View viewOne;

        public MyViewHolder(View view) {
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
            viewOne     = (View)view.findViewById(R.id.viewOne);
        }

        public void reMovePosition(int position) {
            dataLists.remove(position);
            notifyItemChanged(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, dataLists.size());
        }

        public void ItemChanged(int position) {
            dataLists.remove(position);
            notifyItemChanged(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, dataLists.size());
        }
    }


}