package com.sufi.prayertimes.scheduler;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;


import com.sufi.prayertimes.R;
import com.sufi.prayertimes.ShowPrayAlarmActivity;
import com.sufi.prayertimes.constantAndInterfaces.Constants;
import com.sufi.prayertimes.settingsAndPreferences.AppSettings;
import com.sufi.prayertimes.util.PrayTime;

import java.util.Calendar;
import java.util.TimeZone;

import androidx.core.app.NotificationCompat;

/**
 * This {@code IntentService} does the app's actual work.
 * {@code SampleAlarmReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class SalaatSchedulingService extends IntentService implements Constants {
  public SalaatSchedulingService() {
    super("SchedulingService");
  }

  public static final String TAG = "Scheduling Pray Time";
  // An ID used to post the notification.
  // The Google home page URL from which the app fetches content.
  // You can find a list of other Google domains with possible doodles here:
  // http://en.wikipedia.org/wiki/List_of_Google_domains
  private NotificationManager mNotificationManager;
  private NotificationCompat.Builder builder;

  @Override
  protected void onHandleIntent(Intent intent) {
    // BEGIN_INCLUDE(service_onhandle)

    String prayerName = intent.getStringExtra(EXTRA_PRAYER_NAME);

    Calendar now = Calendar.getInstance(TimeZone.getDefault());
    now.setTimeInMillis(System.currentTimeMillis());

    String formatString = "%2$tl:%2$tM %2$tp %1$s";
    if (AppSettings.getInstance(this).getTimeFormatFor(0) == PrayTime.TIME_24) {
      formatString = "%2$tk:%2$tM %1$s";
    }
    sendNotification(String.format(formatString, prayerName, now), getString(R.string.notification_body, prayerName));
    // Release the wake lock provided by the BroadcastReceiver.
    SalaatAlarmReceiver.completeWakefulIntent(intent);
    // END_INCLUDE(service_onhandle)
  }

  // Post a notification indicating whether a doodle was found.
  private void sendNotification(String title, String msg) {
    mNotificationManager = (NotificationManager)
        this.getSystemService(Context.NOTIFICATION_SERVICE);

    PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
        new Intent(this, ShowPrayAlarmActivity.class), 0);

    builder = new NotificationCompat.Builder(this,NOTIFICATION_CHANEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
            .setContentText(msg)
            .setAutoCancel(true);

    builder.setContentIntent(contentIntent);
    mNotificationManager.notify(NOTIFICATION_ID, builder.build());
  }

}
