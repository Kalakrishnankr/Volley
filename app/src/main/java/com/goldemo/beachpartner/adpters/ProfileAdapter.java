package com.goldemo.beachpartner.adpters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.fragments.BPFinderFragment;
import com.goldemo.beachpartner.models.PersonModel;

import java.util.ArrayList;

/**
 * Created by seq-kala on 20/2/18.
 */

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    public Context mContext;
    private ArrayList<PersonModel> dataList;
    public ProfileAdapter(Context context, ArrayList<PersonModel> dataList) {
        this.dataList=dataList;
        this.mContext=context;
    }


    @Override
    public ProfileAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        PersonModel model = dataList.get(position);
        holder.imv_profile.setBackgroundResource(model.getImage());
        holder.imv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, String.valueOf(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                boolean isblueBP = true;
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                BPFinderFragment bpFinderFragment =new BPFinderFragment(isblueBP);
                Bundle bundle = new Bundle();
                //cPosition is the current positon
                bundle.putInt("cPosition", holder.getAdapterPosition());
                bundle.putParcelableArrayList("bluebplist", dataList);
                bpFinderFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, bpFinderFragment).commit();

            }
        });

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imv_profile;
        public ViewHolder(View view) {
            super(view);

            imv_profile =   (ImageView)view.findViewById(R.id.imgProfile);

        }
    }
}
