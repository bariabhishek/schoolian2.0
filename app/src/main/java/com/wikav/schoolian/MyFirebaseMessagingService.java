package com.wikav.schoolian;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.wikav.schoolian.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static int NOTIFICATION_ID = 1;
    public static String tokken;
    public String intent;
    String clickAction;

    public MyFirebaseMessagingService() {

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        intent = remoteMessage.getData().get("in");
  clickAction = remoteMessage.getNotification().getClickAction();
        genereteNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle(), intent, clickAction);
    }


    private void genereteNotification(String body, String title, String in, String clickAction) {
        Log.d("mynoti","ok noti"+in);


        NotificationCompat.Builder noti = new NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_stat_name);
        Intent intent=new Intent(clickAction);
        intent.putExtra("intent",in);

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
