package com.sufi.prayertimes.quran;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import io.reactivex.annotations.NonNull;

public class NotificationBroadcast extends BroadcastReceiver {
    public void onReceive(Context context, @NonNull Intent intent) {
        if (StaticObjects.quranMediaPlayer != null) {
            if (intent.getAction().equals(MediaService.NOTIFY_PAUSE)) {
                StaticObjects.quranMediaPlayer.pausePlayer();
            } else if (intent.getAction().equals(MediaService.NOTIFY_NEXT)) {
                StaticObjects.quranMediaPlayer.next();
            } else if (intent.getAction().equals(MediaService.NOTIFY_DELETE)) {
                StaticObjects.quranMediaPlayer.stopPlayer();
                StaticObjects.mediaService.myNotificationManager.cancel(MediaService.NOTIFICATION_ID);
            } else if (intent.getAction().equals(MediaService.NOTIFY_PREVIOUS)) {
                StaticObjects.quranMediaPlayer.prev();
            }
        }
    }

    public String ComponentName() {
        return getClass().getName();
    }
}
