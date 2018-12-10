package net.vorson.muhammadsufwan.prayertimesformuslim.sevices;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import net.vorson.muhammadsufwan.prayertimesformuslim.PrayerViewActivity;
import net.vorson.muhammadsufwan.prayertimesformuslim.R;

import androidx.core.app.NotificationCompat;

public class FireBaseInstanceIdService extends FirebaseMessagingService {

    private static final String TAG = FireBaseInstanceIdService.class.getSimpleName();

    @Override
    public void onNewToken(String s) {
        // Get updated InstanceID token.
        super.onNewToken(s);
        Log.d(TAG, "Refreshed token: " + s);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
//        sendRegistrationToServer(refreshedToken);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG,"FROM "+remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0){
            Log.d(TAG,"Message Data "+remoteMessage.getData());
        }

        if (remoteMessage.getNotification() != null){
            Log.d(TAG,"Message Body:"+remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody());
        }

    }

    private void sendNotification(String body){

        Intent intent = new Intent(this,PrayerViewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder.setWhen(System.currentTimeMillis());
        notificationBuilder.setContentText(body);
        notificationBuilder.setSound(notificationSound);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSmallIcon(R.drawable.ic_new_make);
        notificationBuilder.setContentIntent(pi);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationBuilder.build());

    }

}
