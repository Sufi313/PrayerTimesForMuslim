package net.vorson.muhammadsufwan.prayertimesformuslim.sevices;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import net.vorson.muhammadsufwan.prayertimesformuslim.HomeActivity;
import net.vorson.muhammadsufwan.prayertimesformuslim.R;
import net.vorson.muhammadsufwan.prayertimesformuslim.settingsAndPreferences.AppSettings;

import androidx.core.app.NotificationCompat;

public class FireBaseInstanceIdService extends FirebaseMessagingService {

    private static final String TAG = FireBaseInstanceIdService.class.getSimpleName();

    @Override
    public void onNewToken(String s) {
        // Get updated InstanceID token.
        super.onNewToken(s);
        Log.d(TAG, "Refreshed token: " + s);
        AppSettings appSettings = AppSettings.getInstance();
        if (appSettings.getString(AppSettings.Key.DEVICE_TOKEN).equals("") && appSettings.getString(AppSettings.Key.DEVICE_TOKEN) == null){
            appSettings.set(AppSettings.Key.DEVICE_TOKEN,s);
        }

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
//        sendRegistrationToServer(refreshedToken);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG,"FROM "+remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0){
            Log.d(TAG,"Message Data size greater then 0");
        }

        if (remoteMessage.getNotification() != null){
            Log.d(TAG,"Message Body found");
            sendNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getClickAction());
        }

    }

    private void sendNotification(String title ,String message,String action){

        String actionActivity = "HomeActivity";

        if (action.equals("") || action.isEmpty()){
            action = actionActivity;
            Log.d(TAG, "sendNotification: "+action);
        }
        Intent intent = new Intent(actionActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setWhen(System.currentTimeMillis());
        notificationBuilder.setContentText(message);
        notificationBuilder.setSound(notificationSound);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationBuilder.setContentIntent(pi);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());

    }

}
