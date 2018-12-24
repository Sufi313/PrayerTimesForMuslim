package net.vorson.muhammadsufwan.prayertimesformuslim;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.widget.Toast;

import net.vorson.muhammadsufwan.prayertimesformuslim.settingsAndPreferences.Prefs;
import net.vorson.muhammadsufwan.prayertimesformuslim.util.AndroidTimeZoneProvider;
import net.vorson.muhammadsufwan.prayertimesformuslim.util.TimeZoneChangedReceiver;

import org.joda.time.DateTimeZone;

import java.util.Locale;

import io.reactivex.annotations.NonNull;


public class App extends Application  {

    private static App sApp;
    private Locale mSystemLocale;

    @NonNull
    private Handler mHandler = new Handler();

    private Thread.UncaughtExceptionHandler mDefaultUEH;
    @NonNull
    private Thread.UncaughtExceptionHandler mCaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread thread, @NonNull Throwable ex) {
//            AppRatingDialog.setInstalltionTime(0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ex.getClass().getName().contains("RemoteServiceException")) {
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


    @NonNull
    public static App get() {
        return sApp;
    }

    public static void setApp(App app) {
        sApp = app;
    }

    public App() {
        super();
        sApp = this;
    }


    @NonNull
    public Handler getHandler() {
        return mHandler;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        Stetho.initializeWithDefaults(this);
//        new OkHttpClient.Builder()
//                .addNetworkInterceptor(new StethoInterceptor())
//                .build();

        mSystemLocale = Locale.getDefault();

        DateTimeZone.setProvider(new AndroidTimeZoneProvider());
        registerReceiver(new TimeZoneChangedReceiver(), new IntentFilter(Intent.ACTION_TIMEZONE_CHANGED));

    }

    public static boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) get().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    public Locale getSystemLocale() {
        return mSystemLocale;
    }

    public static String getUserAgent(){
        return "Android/Prayer-Times com.metinkale.prayer (contact: metinkale38@gmail.com)";
    }
}
