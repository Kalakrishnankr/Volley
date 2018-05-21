package com.beachpartnerllc.beachpartner.adpters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beachpartnerllc.beachpartner.CircularImageView;
import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.models.MyCalendarPartnerModel;
import com.beachpartnerllc.beachpartner.models.PartnerResultModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seq-kala on 16/5/18.
 */

public class PopupAdapter extends RecyclerView.Adapter<PopupAdapter.MyViewHolder> {
    public Context mContext;
    private List<MyCalendarPartnerModel> dataList= new ArrayList<>();

    public PopupAdapter(Context context, List<MyCalendarPartnerModel> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }
    @Override
    public PopupAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_card,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        MyCalendarPartnerModel model = dataList.get(position);
        if (model.getPartner_ImageUrl() != null) {
            if (!model.getPartner_ImageUrl().equals("null")) {
                Glide.with(mContext).load(model.getPartner_ImageUrl()).into(holder.profileImg);
            }else {
                holder.profileImg.setImageResource(R.drawable.ic_person);
            }
        }else{
            holder.profileImg.setImageResource(R.drawable.ic_person);
        }

        //holder.profileImg.setImageURI(Uri.parse(model.getPartnerImageUrl()));
        holder.txtv_name.setText(model.getpartner_Name());
        //holder.txtv_status.setText(model.getPartner_ivitationStatus());
        if(model.getpartner_role()!=null){
            if(model.getpartner_role().equalsIgnoreCase("Organizer")){
               holder.txtv_role.setText("Organizer");
            }
            else{
                holder.txtv_role.setText(model.getPartner_ivitationStatus());
            }
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CircularImageView profileImg;
        public TextView txtv_name,txtv_status,txtv_role;

        public MyViewHolder(View v) {
            super(v);

            profileImg = v.findViewById(R.id.partnerImg);
            txtv_name  = v.findViewById(R.id.partner_name);
            txtv_status = v.findViewById(R.id.partner_status);
            txtv_role   = v.findViewById(R.id.my_role);


        }
    }

    // private ArrayList<SingleItemModel> itemsList;

}
