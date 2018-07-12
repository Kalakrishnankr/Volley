package com.beachpartnerllc.beachpartner;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import com.beachpartnerllc.beachpartner.activity.TabActivity;
import com.beachpartnerllc.beachpartner.connections.PrefManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by Owner on 4/15/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    NotificationManager notificationManager;
    String ADMIN_CHANNEL_ID="BeachPartner";
    private String redirect;
    private String eventId;
    private static final String TAG = "MyFirebaseMsgService";




    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
       super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From: " + remoteMessage.getData().size()); // for the data size
        final Map<String, String> data = remoteMessage.getData();
        String title = data.get("title");
        String body = data.get("body");
        redirect = data.get("click_action");
        eventId  = data.get("event_id");
        PrefManager prefManager = new PrefManager(getApplicationContext());

        if(title!=null&&body!=null){
            if(!title.equals("") && !body.equals("")){
                if(data.get("user_id").equalsIgnoreCase(prefManager.getUserId())){
                    sendNotificationData(title, body); //send notification to user
                }
            }
        }

    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */


    private void sendNotificationData(String messageTitle,String messageBody) {
        Intent intent = new Intent(this, TabActivity.class);
        if(redirect!=null){
            if(redirect.equalsIgnoreCase("HOME")){
                intent.putExtra("reDirectPage", "HOME");
            }
            else if(redirect.equalsIgnoreCase("ACTIVE")){
                intent.putExtra("reDirectPage", "ACTIVE");
            }
            else if(redirect.equalsIgnoreCase("INVITATION")){
                intent.putExtra("reDirectPage", "INVITATION");
                intent.putExtra("event_id",eventId);
            }/*else if(redirect.equalsIgnoreCase("ACCEPTED")){
                intent.putExtra("reDirectPage", "ACCEPTED");
                intent.putExtra("event_id",eventId);
            }*/
            else{
                intent.putExtra("reDirectPage", "hifive");
            }
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0 /* request code */, intent,PendingIntent.FLAG_UPDATE_CURRENT);

        long[] pattern = {500,500,500,500,500};

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        int smallIcom = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? R.drawable.ic_bp_white_24dp :  R.mipmap.ic_launcher;
        createNotificationChannel();
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,getString(R.string.channel_id))
                .setSmallIcon(smallIcom)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setVibrate(pattern)
                .setLights(Color.BLUE,1,1)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        Notification notification = notificationBuilder.build();
        int smallIconId = getResources().getIdentifier("right_icon", "id", android.R.class.getPackage().getName());
        if (smallIconId != 0) {
            if (notification.contentView!=null)
                notification.contentView.setViewVisibility(smallIconId, View.INVISIBLE);
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 , notification);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(getString(R.string.channel_id), name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
