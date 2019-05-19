package com.wikav.voulu;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static int NOTIFICATION_ID = 1;
    public static String tokken;
    public String intent;

    public MyFirebaseMessagingService() {

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        intent = remoteMessage.getData().get("in");

        String clickAction = remoteMessage.getNotification().getClickAction();
        genereteNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle(), intent, clickAction);
    }


    private void genereteNotification(String body, String title, String in, String clickAction) {


        NotificationCompat.Builder noti = new NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_stat_name);


        Intent intent = new Intent(clickAction);
        PendingIntent resultPending =
                PendingIntent.getActivity(
                        this,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        noti.setContentIntent(resultPending);

        int mNotificationId = (int) System.currentTimeMillis();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(mNotificationId, noti.build());


    }
}
