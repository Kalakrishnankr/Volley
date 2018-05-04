package com.beachpartnerllc.beachpartner.adpters;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
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
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TouristSpotCardAdapter extends ArrayAdapter<BpFinderModel> {

    private Context mContext;
    private Integer ageInt;
    MyInterface myInterface;


    public TouristSpotCardAdapter(Context context, MyInterface inter) {
        super(context, 0);
        this.mContext = context;
        this.myInterface = inter;

    }


    @Override
    public View getView(int position, View contentView, ViewGroup parent) {
        final ViewHolder holder;

        if (contentView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            contentView = inflater.inflate(R.layout.item_tourist_spot_card, parent, false);
            holder = new ViewHolder(contentView);
            contentView.setTag(holder);

        } else {
            holder = (ViewHolder) contentView.getTag();
        }

        final BpFinderModel spot = getItem(position);

        holder.exoPlayerView.setVisibility(View.INVISIBLE);
        holder.spinnerView.setVisibility(View.INVISIBLE);
        holder.progress.setVisibility(View.INVISIBLE);
        holder.image.setVisibility(View.VISIBLE);
        holder.progressBar.setVisibility(View.VISIBLE);
        holder.progressBar.start();

        if (spot != null) {
            if (spot.getBpf_age() != null) {
                holder.name.setText(MessageFormat.format("{0}, {1}", spot.getBpf_firstName(), spot.getBpf_age()));
            } else {
                String dobToage = spot.getBpf_dob();
                if (dobToage != null && !dobToage.isEmpty()) {
                    long millisecond = Long.parseLong(dobToage);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    Date datef = new Date(millisecond);
                    Calendar today = Calendar.getInstance();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(datef);
                    int age = today.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
                    if (today.get(Calendar.DAY_OF_YEAR) < cal.get(Calendar.DAY_OF_YEAR)) {
                        age--;
                    }
                    ageInt = new Integer(age);
                    holder.name.setText(spot.getBpf_firstName() + ", " + ageInt);
                } else {
                    holder.name.setText(spot.getBpf_firstName());
                }
            }
        }

        holder.userType.setText(spot.getBpf_userType());
        if (spot.getBpf_imageUrl() != null && !spot.getBpf_imageUrl().equals("null")) {
            holder.spinnerView.stop();
            Glide.with(getContext()).load(spot.getBpf_imageUrl()).into(holder.image);

        } else {
            holder.spinnerView.stop();
            holder.image.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.user_img));
        }


        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
        holder.exoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);
        final DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
        final ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        holder.exoPlayerView.hideController();
        holder.exoPlayerView.setControllerAutoShow(false);
        holder.exoPlayerView.setMinimumWidth(1000);
        holder.exoPlayerView.setMinimumHeight(1000);

        //  holder.exoPlayerView

        //

        holder.image.setOnTouchListener(new DoubleTapListener() {

            @Override
            public void onSingleClick(View v) {
                myInterface.onClick(spot.getBpf_id(), spot.getBpf_deviceId(), spot.getBpf_fcmToken(), spot.getBpf_topfinishes());

            }

            @Override
            public void onDoubleClick(View v) {
                if (spot.getBpf_videoUrl() != null) {
                    holder.progress.setVisibility(View.VISIBLE);
                    MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(spot.getBpf_videoUrl()), dataSourceFactory, extractorsFactory, null, null);
                    holder.exoPlayer.prepare(mediaSource);
                    playVideo(holder);
                }
            }
        });


        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.exoPlayer.stop();
                myInterface.addView(spot.getBpf_imageUrl(), spot.getBpf_firstName(),spot.getBpf_dob(),spot.getBpf_userType());
                myInterface.onClick(spot.getBpf_id(), spot.getBpf_deviceId(), spot.getBpf_fcmToken(), spot.getBpf_topfinishes());
            }
        });


        //End video tag here 8/02/2018


        return contentView;
    }

    private void playVideo(final ViewHolder holder) {

        holder.exoPlayerView.setPlayer(holder.exoPlayer);
        holder.exoPlayer.setPlayWhenReady(true);
        holder.exoPlayer.setVolume(0);
        holder.exoPlayer.addListener(new Player.DefaultEventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playWhenReady && playbackState == Player.STATE_READY) {
                    holder.exoPlayerView.setVisibility(View.VISIBLE);
                    // media actually playing
                }
            }
        });
    }


    @Override
    public long getItemId(int position) {
        return position;
    }



    private static class ViewHolder {
        public TextView name;
        public TextView userType;
        public ImageView image, img_profile;
        public VideoView videoView;
        public RotateLoading spinnerView, progressBar;
        public Button info;
        public CardView swipe_card;
        public FrameLayout frameLayout; //frameLayoutOne;
      //  public ImageView frameImage;
        public RelativeLayout relativeLayout;
        SimpleExoPlayerView exoPlayerView;
        SimpleExoPlayer exoPlayer;
        AVLoadingIndicatorView progress;

        public ViewHolder(View view) {
            name = (TextView) view.findViewById(R.id.item_tourist_spot_card_name);
            userType = (TextView) view.findViewById(R.id.item_tourist_spot_card_city);
            image = (ImageView) view.findViewById(R.id.img_view);
            progressBar = (RotateLoading) view.findViewById(R.id.prsbar);
            info = (Button) view.findViewById(R.id.btnInfo);

            swipe_card = (CardView) view.findViewById(R.id.swipe_card);
            spinnerView = (RotateLoading) view.findViewById(R.id.my_spinner);
            frameLayout = (FrameLayout) view.findViewById(R.id.placeholder);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.cardlayout);

            exoPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.exo_player_view);
            img_profile = (ImageView) view.findViewById(R.id.img_profile);

            progress = (AVLoadingIndicatorView) view.findViewById(R.id.progBar);
        }
    }


}