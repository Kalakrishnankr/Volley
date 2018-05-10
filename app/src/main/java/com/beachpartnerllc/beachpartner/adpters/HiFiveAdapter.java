package com.beachpartnerllc.beachpartner.adpters;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.beachpartnerllc.beachpartner.CircularImageView;
import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.fragments.BPFinderFragment;
import com.beachpartnerllc.beachpartner.models.BpFinderModel;
import com.beachpartnerllc.beachpartner.utils.AppConstants;
import com.bumptech.glide.Glide;
import com.github.curioustechizen.ago.RelativeTimeTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Owner on 3/22/2018.
 */

public class HiFiveAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<BpFinderModel> hiFiveList = new ArrayList<BpFinderModel>();
    private Context context;
    RelativeTimeTextView timeStamp;
    CircularImageView usersImg;
    CardView cardView;
    private static  boolean isHifi = false;
    private static  boolean isblueBP = false;
    private static  boolean isPartner = false;
    public HiFiveAdapter(ArrayList<BpFinderModel> hiFiveList, Context context) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.layout_card_hifives, null);

        TextView tvContact  = (TextView)convertView.findViewById(R.id.tvContact);
        final ImageView hifiveSeen  = (ImageView)convertView.findViewById(R.id.btn);
        timeStamp           =  convertView.findViewById(R.id.time_stamp);
        cardView            =  convertView.findViewById(R.id.highfiveCard);
        usersImg            =  convertView.findViewById(R.id.hifiveImg);

        tvContact.setText(hiFiveList.get(position).getBpf_firstName()+" sent you a high five");
        Glide.with(getContext()).load(hiFiveList.get(position).getBpf_imageUrl()).into(usersImg);
        //timeStamp.setReferenceTime(new Date().getTime());



        cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                  hifiveSeen.setBackgroundResource(R.drawable.ic_highfive_seen);
                  isHifi = true;
                  isblueBP=true;
                  AppCompatActivity activity = (AppCompatActivity) v.getContext();
                  BPFinderFragment bpFinderFragment =new BPFinderFragment(isblueBP,isPartner);
                  Bundle bundle = new Bundle();
                  bundle.putParcelable(AppConstants.BP_PROFILE, hiFiveList.get(position));
                  bpFinderFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, bpFinderFragment).commit();
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





    private String trimMessage(String json, String detail) {
        String trimmedString = null;

        try {
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(detail);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }

}
