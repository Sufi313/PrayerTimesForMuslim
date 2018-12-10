package net.vorson.muhammadsufwan.prayertimesformuslim.sevices;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;

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

}
