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
 * Created by seq-kala on 6/6/18.
 */

public class AddonAdapter extends RecyclerView.Adapter<AddonAdapter.MyViewHolder>{
    private Context mContext;
    private List<SubscriptonPlansModel>modelList;
    private SubClickInterface subClickInterface;
    private String activeAddons;
    private RadioButton lastCheckedRB = null;
    private static boolean isExpanded = false;


    public AddonAdapter(Context context, List<SubscriptonPlansModel> addonList, SubClickInterface addOnFragment,String activePlans ) {
        this.mContext = context;
        this.modelList = addonList;
        this.subClickInterface=addOnFragment;
        this.activeAddons =activePlans;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addon_item,parent,false);
        AddonAdapter.MyViewHolder myViewHolder = new AddonAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final SubscriptonPlansModel plansModel = modelList.get(position);
        holder.txtDesc.setText(plansModel.getPlanDescription());
        holder.addTitle.setText(plansModel.getPlanName());
        holder.txtPayment.setText("$"+plansModel.getMonthlyCharge()+"/day");
        holder.toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                RadioButton checked_rb = compoundButton.findViewById(compoundButton.getId());
                if (lastCheckedRB != null) {
                    lastCheckedRB.setChecked(false);
                }
                //store the clicked radiobutton
                lastCheckedRB = checked_rb;
                subClickInterface.changeViews(plansModel);//call interface to change the view
            }
        });
        holder.txtvRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plansModel.isExpanded = !plansModel.isExpanded;
                if(plansModel.isExpanded){
                    ViewGroup.LayoutParams params = holder.txtDesc.getLayoutParams();
                    params.height = holder.txtDesc.getLayoutParams().WRAP_CONTENT;
                    holder.txtDesc.setLayoutParams(params);
                    holder.txtDesc.setMaxLines(10);
                    holder.txtvRead.setText("Read less");
                }else {
                    ViewGroup.LayoutParams params = holder.txtDesc.getLayoutParams();
                    params.height = holder.txtDesc.getLayoutParams().WRAP_CONTENT;
                    holder.txtDesc.setLayoutParams(params);
                    holder.txtDesc.setMaxLines(2);
                    holder.txtvRead.setText(R.string.read_more);
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatRadioButton toggle;
        public TextView addTitle,txtPayment,txtDesc,txtvRead;

        public MyViewHolder(View itemView) {
            super(itemView);
            toggle   = itemView.findViewById(R.id.img_toggle);
            addTitle = itemView.findViewById(R.id.txtv_addonTitle);
            txtDesc  = itemView.findViewById(R.id.txtv_desc);
            txtPayment= itemView.findViewById(R.id.txtv_payment);
            txtvRead = itemView.findViewById(R.id.txtv_read);
        }
    }
}
