package com.beachpartnerllc.beachpartner;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.beachpartnerllc.beachpartner.activity.LoginActivity;

import com.beachpartnerllc.beachpartner.activity.TabActivity;
import com.beachpartnerllc.beachpartner.fragments.HiFiveFragment;
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


        if(title!=null&&body!=null){
            if(!title.equals("") && !body.equals("")){
                sendNotificationData(title, body); //send notification to user
            }
        }
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
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
            }
            else{
                intent.putExtra("reDirectPage", "hifive");
            }
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0 /* request code */, intent,PendingIntent.FLAG_UPDATE_CURRENT);

        long[] pattern = {500,500,500,500,500};

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setVibrate(pattern)
                .setLights(Color.BLUE,1,1)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 , notificationBuilder.build());
    }
}
