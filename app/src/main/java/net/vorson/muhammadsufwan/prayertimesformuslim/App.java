package net.vorson.muhammadsufwan.prayertimesformuslim;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.widget.Toast;



import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import net.vorson.muhammadsufwan.prayertimesformuslim.settingsAndPreferences.Prefs;
import net.vorson.muhammadsufwan.prayertimesformuslim.util.TimeZoneChangedReceiver;

import java.util.Locale;

import io.reactivex.annotations.NonNull;
import okhttp3.OkHttpClient;


public class App extends Application implements SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String API_URL = "http://metinkale38.github.io/prayer-times-android";
    private static App sApp;
    @NonNull
    private Handler mHandler = new Handler();

    private Thread.UncaughtExceptionHandler mDefaultUEH;
    @NonNull
    private Thread.UncaughtExceptionHandler mCaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread thread, @NonNull Throwable ex) {
//            AppRatingDialog.setInstalltionTime(0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && ex.getClass().getName().contains("RemoteServiceException")) {
                if (ex.getMessage().contains("Couldn't update icon")) {
                    Prefs.setShowOngoingNumber(false);
                    Toast.makeText(App.get(), "Crash detected. Show ongoing number disabled...", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            // This will make Crashlytics do its job
            mDefaultUEH.uncaughtException(thread, ex);
        }
    };
    private Locale mSystemLocale;


    @NonNull
    public static App get() {
        return sApp;
    }

    public static boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) get().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }
//
//    public static void setExact(@NonNull AlarmManager am, int type, long time, PendingIntent service) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
//                && type == AlarmManager.RTC_WAKEUP && Prefs.useAlarm()) {
//            AlarmManager.AlarmClockInfo info =
//                    new AlarmManager.AlarmClockInfo(time,
//                            PendingIntent.getActivity(App.get(), 0, new Intent(App.get(), VakitFragment.class), PendingIntent.FLAG_UPDATE_CURRENT));
//            am.setAlarmClock(info, service);
//        } else if (type == AlarmManager.RTC_WAKEUP && Build.VERSION.SDK_INT >= 23) {
//            am.setExactAndAllowWhileIdle(type, time, service);
//        } else if (Build.VERSION.SDK_INT >= 19) {
//            am.setExact(type, time, service);
//        } else {
//            am.set(type, time, service);
//        }
//
//    }

    public static void setApp(App app) {
        sApp = app;
    }

    @NonNull
    public Handler getHandler() {
        return mHandler;
    }

    public App() {
        super();
        sApp = this;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }


    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        mSystemLocale = Locale.getDefault();
        registerReceiver(new TimeZoneChangedReceiver(), new IntentFilter(Intent.ACTION_TIMEZONE_CHANGED));

//        try {
//            Times.getTimes();
//        } catch (Exception e) {
//            Crashlytics.logException(e);
//        }

//        Utils.init(this);
//
//        WidgetService.start(this);
//        LocationService.start(this);
//        Times.setAlarms();

    }

//    @Override
//    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//        switch (key) {
//            case "calendarIntegration":
//                MainIntentService.startCalendarIntegration(App.get());
//                break;
//            case "useAlarm":
//                Times.setAlarms();
//                break;
//            case "use12h":
//            case "ongoingIcon":
//            case "ongoingNumber":
//            case "showAltWidgetHightlight":
//            case "widget_countdown":
//            case "alternativeOngoing":
//            case "showLegacyWidgets":
//                WidgetService.start(this);
//                break;
//        }
//
//
//    }

    public static final class NotIds {
        public static final int ALARM = 1;
        public static final int ONGOING = 2;
        public static final int PLAYING = 3;
    }

//    private static class MyJobCreator implements JobCreator {
//        @Nullable
//        @Override
//        public Job create(@NonNull String tag) {
//            try {
//                if (tag.startsWith(WebTimes.SyncJob.TAG)) {
//                    Times t = Times.getTimes(Long.parseLong(tag.substring(WebTimes.SyncJob.TAG.length())));
//                    if (t instanceof WebTimes)
//                        return ((WebTimes) t).new SyncJob();
//                }
//            } catch (Exception e) {
//                Crashlytics.logException(e);
//            }
//            return null;
//        }
//    }

    public Locale getSystemLocale() {
        return mSystemLocale;
    }

    public static String getUserAgent(){
        return "Android/Prayer-Times com.metinkale.prayer (contact: metinkale38@gmail.com)";
    }
}
