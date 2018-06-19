package com.beachpartnerllc.beachpartner.adpters;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.models.SubscriptonPlansModel;
import com.beachpartnerllc.beachpartner.utils.SubClickInterface;

import java.util.List;

/**
 * Created by seq-kala on 1/6/18.
 */

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.MyViewHolder>{

    private Context mContext;
    private List<SubscriptonPlansModel>modelArrayList;
    private String user_subscription;
    private RadioButton lastCheckedRB = null;
    private SubClickInterface clickInterface;


    public SubscriptionAdapter(Context context, List<SubscriptonPlansModel> plansModelList, SubClickInterface subClickInterface,String subscription) {

        this.mContext=context;
        this.modelArrayList = plansModelList;
        this.clickInterface = subClickInterface;
        this.user_subscription = subscription;
    }

    @Override
    public SubscriptionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcription_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final SubscriptionAdapter.MyViewHolder holder, final int position) {

        final SubscriptonPlansModel plansModel = modelArrayList.get(position);
        if (plansModel != null) {
            holder.txt_subscibe.setText(plansModel.getPlanName());
            holder.txt_payment.setText("$"+plansModel.getMonthlyCharge()+" /month");
            holder.txt_desc.setText(plansModel.getPlanDescription());
        }
        if (user_subscription != null) {
            if (!user_subscription.equalsIgnoreCase("null")) {
                if (plansModel != null && user_subscription.equalsIgnoreCase(plansModel.getPlanName()))
                    holder.img_toggle.setChecked(true);
            }
        }
        holder.img_toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                RadioButton checked_rb = compoundButton.findViewById(compoundButton.getId());
                if (lastCheckedRB != null) {
                    lastCheckedRB.setChecked(false);
                }
                //store the clicked radiobutton
                lastCheckedRB = checked_rb;
                clickInterface.changeViews();//call interface to change the view
            }
        });
        holder.txtReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickInterface.changeSubLayout(plansModel);
            }
        });


    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_subscibe,txt_payment,txt_desc,txtReadMore;
        public AppCompatRadioButton img_toggle;
        public MyViewHolder(View itemView) {
            super(itemView);

            txt_subscibe = itemView.findViewById(R.id.txtv_subscription);
            txt_desc     = itemView.findViewById(R.id.txtv_desc);
            txt_payment  = itemView.findViewById(R.id.txtv_payment);
            img_toggle   = itemView.findViewById(R.id.img_toggle);
            txtReadMore  = itemView.findViewById(R.id.txtv_readMore);
        }
    }
}
