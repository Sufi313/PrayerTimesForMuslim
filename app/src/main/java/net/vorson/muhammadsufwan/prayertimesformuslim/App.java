package net.vorson.muhammadsufwan.prayertimesformuslim;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
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
