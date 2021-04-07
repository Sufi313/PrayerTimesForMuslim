package com.sufi.prayertimes.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sufi.prayertimes.R;
import com.sufi.prayertimes.settingsAndPreferences.AppSettings;

import com.sufi.prayertimes.HomeActivity;



public class FireBaseNotificationService extends FirebaseMessagingService {

    private static final String TAG = FireBaseNotificationService.class.getSimpleName();
    public static int count = 0;
    private LocalBroadcastManager broadcaster;

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d(TAG, "onNewToken: " + s);
        AppSettings.getInstance(getApplicationContext()).set(AppSettings.Key.DEVICE_TOKEN, s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            sendNotification(remoteMessage);
            count++;
        }
    }

    private void sendNotification(RemoteMessage remoteMessage) {


        broadcaster = LocalBroadcastManager.getInstance(getBaseContext());
        Intent intent2 = new Intent("PushNotificationService.REQUEST_ACCEPT");
        broadcaster.sendBroadcast(intent2);


        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);


//        String messageTitle = remoteMessage.getNotification().getTitle();
//        String messageBody = remoteMessage.getNotification().getBody();
        String messageTitle = remoteMessage.getData().get("title");
        String messageBody = remoteMessage.getData().get("text");

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT
                        | PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "SimpleNotification");
        notificationBuilder.setSmallIcon(R.drawable.ic_muslim)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
}
