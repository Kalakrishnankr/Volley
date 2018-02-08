package com.goldemo.beachpartner.adpters;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.goldemo.beachpartner.R;

public class TouristSpotCardAdapter extends ArrayAdapter<TouristSpot> {

    public TouristSpotCardAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent) {
        final ViewHolder holder;

        if (contentView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            contentView = inflater.inflate(R.layout.item_tourist_spot_card, parent, false);
            holder = new ViewHolder(contentView);
            holder.videoView.setVisibility(View.GONE);
            holder.progressBar.setVisibility(View.GONE);
            
            contentView.setTag(holder);
        } else {
            holder = (ViewHolder) contentView.getTag();
        }

        final TouristSpot spot = getItem(position);

        holder.name.setText(spot.name);
        holder.city.setText(spot.city);
        Glide.with(getContext()).load(spot.img_url).into(holder.image);


       /* Video Tag onclick listener start*/

       /* holder.image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                holder.image.setVisibility(View.GONE);
                holder.videoView.setVisibility(View.VISIBLE);
                holder.videoView.setVideoURI(Uri.parse(spot.url));
               // dialog.setMessage("Please wait");
                holder.progressBar.setVisibility(View.VISIBLE);
                playVideo(holder);


                return false;
            }
        });
            */
        //End video tag here 8/02/2018


        //holder.image.setVisibility(View.VISIBLE);
       // holder.videoView.setVisibility(View.GONE);
        /*holder.image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.image.setVisibility(View.GONE);
                holder.videoView.setVisibility(View.VISIBLE);
                holder.videoView.setVideoURI(Uri.parse(spot.url));
                holder.videoView.start();
                return false;
            }
        });*/






        /*holder.videoView.setVideoURI(Uri.parse(spot.url));
        holder.videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                holder.videoView.start();
                return false;
            }
        });*/

        //holder.videoPlayView.setVideoUrl(spot.url);
        //holder.videoLayout.setMediaController(mediaController);
        //holder.videoLayout.setVideoURI(spot.url));
        //holder.videoView.start();
        /*try {
            holder.videoLayout.setVideoPath(String.valueOf(Uri.parse(spot.url)));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //Glide.with(getContext()).load(spot.url).into(holder.image);

        //Glide.with(getContext()).load(spot.url).into(holder.videoView);
        /*holder.videoView.setVideoURI(Uri.parse(spot.url));
        holder.videoView.setMediaController(new MediaController(getContext()));
        holder.videoView.requestFocus();*/
       /* holder.videoView.setVideoURI(Uri.parse(spot.url));
        MediaController mediaController = new MediaController(getContext());
        mediaController.setAnchorView(holder.videoView);
        holder.videoView.setMediaController(mediaController);
        holder.videoView.setVideoURI(Uri.parse(spot.url));
        holder.videoView.start();*/
        return contentView;
    }

    private void playVideo(final ViewHolder holder) {

        holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
               holder.progressBar.setVisibility(View.GONE);

              //  DisplayMetrics metrics = new DisplayMetrics(); ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE))
                        //.getDefaultDisplay().getMetrics(metrics);
               // android.widget.LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) holder.videoView.getLayoutParams();
                //params.width =  (int) (350*metrics.density);
                //params.height = (int) (250*metrics.density);
               // params.width =  metrics.widthPixels;
               // params.height = (int) (550*metrics.density);
               // holder.videoView.setLayoutParams(params);
                holder.videoView.start();
            }
        });
    }


    private static class ViewHolder {
        public TextView name;
        public TextView city;
        public ImageView image;
        public VideoView videoView;
        ProgressBar progressBar;
        //public VideoPlayView videoPlayView;
        // public FullscreenVideoLayout videoLayout;

        public ViewHolder(View view) {
            this.name = (TextView) view.findViewById(R.id.item_tourist_spot_card_name);
            this.city = (TextView) view.findViewById(R.id.item_tourist_spot_card_city);
            this.image = (ImageView) view.findViewById(R.id.img_view);
            this.videoView = (VideoView) view.findViewById(R.id.item_tourist_spot_card_image);
            this.progressBar=(ProgressBar)view.findViewById(R.id.prsbar);
            //this.videoLayout = (FullscreenVideoLayout) view.findViewById(R.id.item_tourist_spot_card_image);
            //this.image = (ImageView) view.findViewById(R.id.item_tourist_spot_card_image);
            // this.videoPlayView = (VideoPlayView) view.findViewById(R.id.picassoVideoView);
        }
    }

}

