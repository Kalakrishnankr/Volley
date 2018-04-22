package com.beachpartnerllc.beachpartner.adpters;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.beachpartnerllc.beachpartner.MyInterface;
import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.models.BpFinderModel;
import com.beachpartnerllc.beachpartner.utils.DoubleTapListener;
import com.beachpartnerllc.beachpartner.utils.RotateLoading;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TouristSpotCardAdapter extends ArrayAdapter<BpFinderModel>  {

    private String YOUR_FRAGMENT_STRING_TAG;
    private Context mContext;
    private Integer ageInt;
    MyInterface myInterface;



    public TouristSpotCardAdapter(Context context,MyInterface inter) {
        super(context,0);
        this.mContext=context;
        this.myInterface=inter;

    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent) {
        final ViewHolder holder;

        if (contentView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            contentView = inflater.inflate(R.layout.item_tourist_spot_card, parent, false);
            holder = new ViewHolder(contentView);

            holder.progressBar.setVisibility(View.VISIBLE);
            holder.progressBar.start();
            contentView.setTag(holder);

        } else {
            holder = (ViewHolder) contentView.getTag();
        }

        final BpFinderModel spot = getItem(position);

        holder.videoView.stopPlayback();
        holder.videoView.setVisibility(View.GONE);
        holder.spinnerView.setVisibility(View.GONE);
        holder.image.setVisibility(View.VISIBLE);

        if (spot.getBpf_age() != null) {
            holder.name.setText(spot.getBpf_firstName()+" , "+spot.getBpf_age());
        }else {
            String dobToage = spot.getBpf_dob();
            if (!dobToage.isEmpty() && !dobToage.equals(null)) {
                long millisecond = Long.parseLong(dobToage);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date datef = new Date(millisecond);
                Calendar today = Calendar.getInstance();
                Calendar cal = Calendar.getInstance();
                cal.setTime(datef);
                int age = today.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
                if(today.get(Calendar.DAY_OF_YEAR) < cal.get(Calendar.DAY_OF_YEAR)){
                    age--;
                }
                ageInt = new Integer(age);
                holder.name.setText(spot.getBpf_firstName()+" , "+ageInt);
            }else {
                holder.name.setText(spot.getBpf_firstName());
            }
        }

        holder.userType.setText(spot.getBpf_userType());
        if (spot.getBpf_imageUrl() != null && !spot.getBpf_imageUrl().equals("null")) {
            holder.spinnerView.stop();
            Glide.with(getContext()).load(spot.getBpf_imageUrl()).into(holder.image);
            Glide.with(getContext()).load(spot.getBpf_imageUrl()).into(holder.frameImage);

        }else {
            holder.spinnerView.stop();
            Glide.with(getContext()).load(getContext().getResources().getDrawable(R.drawable.user_img)).into(holder.image);
            Glide.with(getContext()).load(getContext().getResources().getDrawable(R.drawable.user_img)).into(holder.frameImage);
        }



        holder.image.setOnTouchListener(new DoubleTapListener() {

            @Override
            public void onSingleClick(View v) {
                if (spot.getBpf_fcmToken() != null || !spot.getBpf_fcmToken().equalsIgnoreCase("NULL") || !spot.getBpf_deviceId().equalsIgnoreCase("null") || spot.getBpf_deviceId() != null) {
                    myInterface.onClick(spot.getBpf_id(),spot.getBpf_deviceId(),spot.getBpf_fcmToken());
                }

            }

            @Override
            public void onDoubleClick(View v) {
                holder.image.setVisibility(View.VISIBLE);
                //holder.progressBar.setVisibility(View.VISIBLE);
                holder.spinnerView.setVisibility(View.VISIBLE);
                holder.spinnerView.start();
                //holder.videoView.setVisibility(View.VISIBLE);
                if(spot.getBpf_videoUrl()!=null && !spot.getBpf_videoUrl().equalsIgnoreCase("null")){
                    holder.videoView.setVideoURI(Uri.parse(spot.getBpf_videoUrl()));
                    playvideo(holder);
                }
                // dialog.setMessage("Please wait");
                if (spot.getBpf_fcmToken() != null || !spot.getBpf_fcmToken().equalsIgnoreCase("null") || !spot.getBpf_deviceId().equalsIgnoreCase("null") || spot.getBpf_deviceId() != null ) {
                    myInterface.onClick(spot.getBpf_id(), spot.getBpf_deviceId(), spot.getBpf_fcmToken());
                }
            }
        });


        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spot.getBpf_fcmToken() != null && !spot.getBpf_fcmToken().equalsIgnoreCase("null") && !spot.getBpf_deviceId().equalsIgnoreCase("null") && spot.getBpf_deviceId() != null
                        && spot.getBpf_imageUrl()!=null && !spot.getBpf_imageUrl().equalsIgnoreCase("null")) {
                    myInterface.addView(spot.getBpf_imageUrl(),spot.getBpf_firstName());
                    myInterface.onClick(spot.getBpf_id(),spot.getBpf_deviceId(),spot.getBpf_fcmToken());
                    //Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //End video tag here 8/02/2018


        return contentView;
    }





    @Override
    public long getItemId(int position) {
        return position;
    }

    private void playvideo(final ViewHolder holder) {

        holder.videoView.start();
        holder.videoView.setVisibility(View.VISIBLE);
        holder.frameLayout.setVisibility(View.VISIBLE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            holder.videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mediaPlayer, int what, int extra) {
                    if (MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START == what) {
                        holder.spinnerView.setVisibility(View.GONE);

                    }
                    if (MediaPlayer.MEDIA_INFO_BUFFERING_START == what) {
                        holder.spinnerView.start();
                        holder.spinnerView.setVisibility(View.VISIBLE);
                    }
                    if (MediaPlayer.MEDIA_INFO_BUFFERING_END == what) {
                        holder.spinnerView.setVisibility(View.GONE);
                    }
                    return false;
                }
            });

            holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    holder.frameLayout.setVisibility(View.GONE);
                }
            });
        }
    }



    private static class ViewHolder {
        public TextView name;
        public TextView userType;
        public ImageView image;
        public VideoView videoView;
        public RotateLoading spinnerView,progressBar;
        public Button info;
        public CardView swipe_card;
        public FrameLayout frameLayout;
        public ImageView frameImage;
        public RelativeLayout relativeLayout;

        public ViewHolder(View view) {
            name        =   (TextView) view.findViewById(R.id.item_tourist_spot_card_name);
            userType    =   (TextView) view.findViewById(R.id.item_tourist_spot_card_city);
            image       =   (ImageView) view.findViewById(R.id.img_view);
            videoView   =   (VideoView) view.findViewById(R.id.item_tourist_spot_card_image);
            progressBar =   (RotateLoading)view.findViewById(R.id.prsbar);
            info        =   (Button)view.findViewById(R.id.btnInfo);

            swipe_card  =   (CardView) view.findViewById(R.id.swipe_card);
            spinnerView =  (RotateLoading)view.findViewById(R.id.my_spinner);
            frameLayout = (FrameLayout)view.findViewById(R.id.placeholder);
            frameImage = (ImageView) view.findViewById(R.id.frameimg_view);
            relativeLayout = (RelativeLayout)view.findViewById(R.id.cardlayout);
        }
    }



}
