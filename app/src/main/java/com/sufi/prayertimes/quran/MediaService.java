package com.sufi.prayertimes.quran;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RemoteControlClient;
import android.os.Build.VERSION;
import android.widget.RemoteViews;

import com.sufi.prayertimes.App;
import com.sufi.prayertimes.R;


import java.util.Locale;

import io.reactivex.annotations.NonNull;

public class MediaService {
    public static int NOTIFICATION_ID = 111177800;
    public static final String NOTIFY_DELETE = "com.isysway.free.alquran.delete";
    public static final String NOTIFY_NEXT = "com.isysway.free.alquran.next";
    public static final String NOTIFY_PAUSE = "com.isysway.free.alquran.pause";
    public static final String NOTIFY_PLAY = "com.isysway.free.alquran.play";
    public static final String NOTIFY_PREVIOUS = "com.isysway.free.alquran.previous";
    private static boolean currentVersionSupportBigNotification = false;
    private final Context context;
    public NotificationManager myNotificationManager;
    private ComponentName remoteComponentName;
    private RemoteControlClient remoteControlClient;

    public MediaService(Context context) {
        this.context = context;
        currentVersionSupportBigNotification = currentVersionSupportBigNotification();
    }

    @SuppressLint({"NewApi"})
    public void newNotification(String str, String str2) {
        CharSequence charSequence = Locale.getDefault().getLanguage().equals("ar") ? "القرآن (مجاني)" : "Al-Quran (Free)";
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.media_normal_notification);
        RemoteViews remoteViews2 = new RemoteViews(context.getPackageName(), R.layout.media_extended_notification);
        remoteViews.setInt(R.layout.media_normal_notification, "setBackgroundColor", App.getCurrentThemeColors()[0]);
        remoteViews2.setInt(R.layout.media_extended_notification, "setBackgroundColor", App.getCurrentThemeColors()[0]);
        Notification build = new Notification.Builder(context).setSmallIcon(R.drawable.ic_muslim).setContentTitle(str2).build();
        setListeners(remoteViews);
        setListeners(remoteViews2);
        build.contentView = remoteViews;
        if (currentVersionSupportBigNotification) {
            build.bigContentView = remoteViews2;
        }
        try {
            Bitmap decodeResource = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_muslim);
            if (decodeResource != null) {
                build.contentView.setImageViewBitmap(R.id.imageViewAlbumArt, decodeResource);
                if (currentVersionSupportBigNotification) {
                    build.bigContentView.setImageViewBitmap(R.id.imageViewAlbumArt, decodeResource);
                }
            } else {
                build.contentView.setImageViewResource(R.id.imageViewAlbumArt, R.drawable.ic_muslim);
                if (currentVersionSupportBigNotification) {
                    build.bigContentView.setImageViewResource(R.id.imageViewAlbumArt, R.drawable.ic_muslim);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (str == NOTIFY_PLAY) {
            build.contentView.setViewVisibility(R.id.btnPause, 8);
            build.contentView.setViewVisibility(R.id.btnPlay, 0);
            if (currentVersionSupportBigNotification) {
                build.bigContentView.setViewVisibility(R.id.btnPause, 8);
                build.bigContentView.setViewVisibility(R.id.btnPlay, 0);
            }
        } else {
            build.contentView.setViewVisibility(R.id.btnPause, 0);
            build.contentView.setViewVisibility(R.id.btnPlay, 8);
            if (currentVersionSupportBigNotification) {
                build.bigContentView.setViewVisibility(R.id.btnPause, 0);
                build.bigContentView.setViewVisibility(R.id.btnPlay, 8);
            }
        }
        build.contentView.setTextViewText(R.id.textSongName, str2);
        build.contentView.setTextViewText(R.id.textAlbumName, charSequence);
        if (currentVersionSupportBigNotification) {
            build.bigContentView.setTextViewText(R.id.textSongName, str2);
            build.bigContentView.setTextViewText(R.id.textAlbumName, charSequence);
        }
        build.flags |= 2;
        myNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        myNotificationManager.notify(NOTIFICATION_ID, build);
    }

    public void setListeners(@NonNull RemoteViews remoteViews) {
        Intent intent = new Intent(context, NotificationBroadcast.class);
        intent.setAction(NOTIFY_PREVIOUS);
        Intent intent2 = new Intent(context, NotificationBroadcast.class);
        intent2.setAction(NOTIFY_DELETE);
        Intent intent3 = new Intent(context, NotificationBroadcast.class);
        intent3.setAction(NOTIFY_PAUSE);
        Intent intent4 = new Intent(context, NotificationBroadcast.class);
        intent4.setAction(NOTIFY_NEXT);
        Intent intent5 = new Intent(context, NotificationBroadcast.class);
        intent5.setAction(NOTIFY_PLAY);
        remoteViews.setOnClickPendingIntent(R.id.btnPrevious, PendingIntent.getBroadcast(context, 0, intent, 0));
        remoteViews.setOnClickPendingIntent(R.id.btnDelete, PendingIntent.getBroadcast(context, 0, intent2, 0));
        remoteViews.setOnClickPendingIntent(R.id.btnPause, PendingIntent.getBroadcast(context, 0, intent3, 0));
        remoteViews.setOnClickPendingIntent(R.id.btnNext, PendingIntent.getBroadcast(context, 0, intent4, 0));
        remoteViews.setOnClickPendingIntent(R.id.btnPlay, PendingIntent.getBroadcast(context, 0, intent5, 0));
    }

    public void removeNotification() {
        myNotificationManager.cancel(NOTIFICATION_ID);
    }

    public boolean currentVersionSupportBigNotification() {
        return VERSION.SDK_INT >= 21;
    }

    public boolean currentVersionSupportLockScreenControls() {
        return VERSION.SDK_INT >= 14;
    }
}
