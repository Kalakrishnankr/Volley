package com.goldemo.beachpartner.adpters;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.goldemo.beachpartner.R;
import com.goldemo.beachpartner.models.HighFiveModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Owner on 3/22/2018.
 */

public class HiFiveAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<HighFiveModel> hiFiveList = new ArrayList<HighFiveModel>();
    private Context context;
    RelativeTimeTextView timeStamp;
    ImageView usersImg;
    CardView cardView;
    public HiFiveAdapter(ArrayList<HighFiveModel> hiFiveList, Context context) {
        this.hiFiveList=hiFiveList;
        this.context=context;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount()
    {
        return hiFiveList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.layout_card_hifives, null);

        TextView tvContact  = (TextView)convertView.findViewById(R.id.tvContact);
        final ImageView hifiveSeen  = (ImageView)convertView.findViewById(R.id.btn);
        timeStamp           =  convertView.findViewById(R.id.time_stamp);
        cardView            =  convertView.findViewById(R.id.highfiveCard);
        usersImg            =  convertView.findViewById(R.id.hifiveImg);

        tvContact.setText(hiFiveList.get(position).getName()+" Sent you a high five");
        Glide.with(getContext()).load(hiFiveList.get(position).getImage()).into(usersImg);
        timeStamp.setReferenceTime(new Date().getTime());



        cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                hifiveSeen.setBackgroundResource(R.drawable.ic_highfive_seen);
            }
        });

        return convertView;

    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }



    @Override
    public boolean isEmpty() {
        return false;
    }

    public Context getContext() {
        return context;
    }
}
